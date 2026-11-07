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
import org.springframework.http.HttpStatus;

import co.edu.unbosque.mensajeria.security.JwtUtil;
import co.edu.unbosque.mensajeria.service.UsuarioService;
import co.edu.unbosque.mensajeria.dto.AuthResponse;
import co.edu.unbosque.mensajeria.dto.UsuarioDTO;
import co.edu.unbosque.mensajeria.entity.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200" })
@Tag(name = "Autenticación", description = "Controlador que gestiona el inicio de sesión y registro de usuarios mediante JWT.")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UsuarioService usuarioService;

	@Value("${app.base-url}")
	private String baseUrl;

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


	@PostMapping("/foto")
	public ResponseEntity<String> subirFoto(
	        @RequestParam("archivo") MultipartFile archivo) throws IOException {
	
	    String tipo = archivo.getContentType();
	
	    if (archivo.isEmpty() || tipo == null || !tipo.startsWith("image/")) {
	        return ResponseEntity.badRequest().body("Archivo inválido");
	    }
	
	    String ext = tipo.substring(tipo.indexOf('/') + 1);
	    String nombre = UUID.randomUUID() + "." + ext;
	
	    Path directorio = Paths.get(
	        System.getProperty("user.dir"),
	        "uploads",
	        "perfiles"
	    );
	
	    Files.createDirectories(directorio);
	
	    Path destino = directorio.resolve(nombre);
	
	    Files.copy(archivo.getInputStream(), destino);
	
	    return ResponseEntity.ok(baseUrl + "/uploads/perfiles/" + nombre);
	}


}
