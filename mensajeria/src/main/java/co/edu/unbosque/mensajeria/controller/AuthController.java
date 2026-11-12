package co.edu.unbosque.mensajeria.controller;

import java.nio.file.Files;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.mensajeria.security.JwtUtil;
import co.edu.unbosque.mensajeria.service.UsuarioService;
import co.edu.unbosque.mensajeria.dto.AuthResponse;
import co.edu.unbosque.mensajeria.dto.UsuarioDTO;
import co.edu.unbosque.mensajeria.entity.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controlador REST que gestiona las operaciones de autenticación, registro de usuarios 
 * y carga de imágenes de perfil. Expone endpoints bajo el prefijo {@code /auth} 
 * permitiendo solicitudes desde el origen configurado de Angular.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200" })
@Tag(name = "Autenticación", description = "Controlador que gestiona el inicio de sesión y registro de usuarios mediante JWT.")
public class AuthController {

	/**
	 * Administrador de autenticación de Spring Security encargado de procesar 
	 * las credenciales de acceso.
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Utilidad para la generación, firma y validación de tokens JSON Web Token (JWT).
	 */
	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * Servicio de lógica de negocio para la gestión y persistencia de usuarios.
	 */
	@Autowired
	private UsuarioService usuarioService;

	/**
	 * URL base de la aplicación inyectada desde las propiedades de configuración,
	 * utilizada para construir las rutas públicas de los recursos multimedia.
	 */
	@Value("${app.base-url}")
	private String baseUrl;

	/**
	 * Autentica a un usuario en el sistema utilizando su número telefónico y contraseña.
	 * Si las credenciales son válidas, genera un token JWT y retorna la respuesta con el token y el ID del usuario.
	 * 
	 * @param loginRequest objeto DTO que contiene las credenciales de acceso (número telefónico y contraseña).
	 * @return un {@link ResponseEntity} con la respuesta de autenticación {@link AuthResponse} o un código de error HTTP 401 si falla.
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UsuarioDTO loginRequest) {
	    try {
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                loginRequest.getNumeroTelefonico(),
	                loginRequest.getContrasenia()
	            )
	        );

	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

	        String jwt = jwtUtil.generateToken(userDetails);

	        Usuario usuario = usuarioService.buscarPorNumero(loginRequest.getNumeroTelefonico())
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        return ResponseEntity.ok(new AuthResponse(jwt, usuario.getId()));

	    } catch (AuthenticationException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	            .body(e.getClass().getSimpleName());
	    }
	}

	/**
	 * Registra un nuevo usuario en el sistema a partir de los datos proporcionados.
	 * Evalúa el código de resultado del servicio para devolver el estado HTTP adecuado
	 * (creación exitosa, conflicto por número duplicado o errores de validación en datos/contraseña).
	 * 
	 * @param registerRequest objeto DTO con la información completa del usuario a registrar.
	 * @return un {@link ResponseEntity} indicando el resultado del proceso de registro mediante mensajes de texto y códigos de estado.
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsuarioDTO registerRequest) {
		int result = usuarioService.crearUsuario(registerRequest);

		switch (result) {
		case 0:
			return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente");

		case 1:
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario con ese número telefonico");

		case 2:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos incompletos");

		case 4:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("La contraseña debe tener mínimo 7 caracteres, una mayúscula y un carácter especial");

		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
		}
	}


	/**
	 * Sube y almacena una imagen de perfil enviada mediante una solicitud multipart.
	 * Valida que el archivo no esté vacío y que corresponda a un formato de imagen válido,
	 * guardándolo en el directorio local de perfiles y retornando la URL pública de acceso.
	 * 
	 * @param archivo el archivo de imagen recibido en la petición a través del parámetro multipart.
	 * @return un {@link ResponseEntity} con la URL pública de la foto guardada o un error 400 si el archivo es inválido.
	 * @throws IOException si ocurre un error durante la creación de directorios o la copia del archivo en el sistema de ficheros.
	 */
	@PostMapping("/foto")
	public ResponseEntity<String> subirFoto(@RequestParam("archivo") MultipartFile archivo) throws IOException {

		String tipo = archivo.getContentType();
		String ext = null;
		if ("image/jpeg".equals(tipo)) ext = "jpg";
		else if ("image/png".equals(tipo)) ext = "png";

		if (archivo.isEmpty() || ext == null || archivo.getSize() > 5L * 1024 * 1024
				|| javax.imageio.ImageIO.read(archivo.getInputStream()) == null) {
			return ResponseEntity.badRequest().body("Solo se permiten imágenes JPG o PNG de máximo 5 MB");
		}

		String nombre = UUID.randomUUID() + "." + ext;
		Path directorio = Paths.get(System.getProperty("user.dir"), "uploads", "perfiles");
		Files.createDirectories(directorio);
		Files.copy(archivo.getInputStream(), directorio.resolve(nombre));

		return ResponseEntity.ok(baseUrl + "/uploads/perfiles/" + nombre);
	}


}
