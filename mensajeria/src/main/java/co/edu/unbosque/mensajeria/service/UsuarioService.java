package co.edu.unbosque.mensajeria.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unbosque.mensajeria.dto.UsuarioDTO;
import co.edu.unbosque.mensajeria.entity.Usuario;
import co.edu.unbosque.mensajeria.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

/**
 * Servicio de lógica de negocio para la gestión de usuarios, 
 * encargado de procesar el registro con cifrado de contraseñas, validación de autenticación y seguridad,
 * actualización de perfiles, almacenamiento local de fotos y consultas de usuarios por número telefónico.
 */
@Service
public class UsuarioService {

	/**
	 * Codificador para el cifrado seguro de contraseñas (BCrypt).
	 */
	private final PasswordEncoder passwordEncoder;

	/**
	 * Repositorio para la persistencia y procedimientos de usuarios.
	 */
	@Autowired
	private final UsuarioRepository usuarioRepository;

	/**
	 * URL base de la aplicación inyectada para la construcción de rutas públicas de archivos de perfil.
	 */
	@Value("${app.base-url}")
	private String baseUrl;

	/**
	 * Constructor para la inyección de dependencias del repositorio de usuarios y el codificador de contraseñas.
	 * 
	 * @param usuarioRepository repositorio de usuarios.
	 * @param passwordEncoder codificador de contraseñas.
	 */
	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Valida, cifra la contraseña y registra un nuevo usuario en el sistema.
	 * 
	 * @param data objeto con los datos de registro del usuario (teléfono, nombres, apellidos y contraseña).
	 * @return código de resultado numérico (0 si es exitoso, 1 si el número ya está registrado, 2 si hay campos vacíos, 4 si la contraseña no cumple el patrón de seguridad).
	 */
	@Transactional
	public int crearUsuario(UsuarioDTO data) {
		if (vacio(data.getNumeroTelefonico()) || vacio(data.getPrimerNombre()) || vacio(data.getPrimerApellido())
				|| vacio(data.getContrasenia())) {
			return 2;
		}
		if (!data.getContrasenia().matches("^(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{7,}$")) {
			return 4;
		}
		if (findUsernameAlreadyTaken(data.getNumeroTelefonico())) {
			return 1;
		}
		usuarioRepository.crearUsuario(data.getPrimerNombre(), data.getSegundoNombre(), data.getPrimerApellido(),
				data.getSegundoApellido(), data.getNumeroTelefonico(), data.getFotoPerfilUrl(),
				passwordEncoder.encode(data.getContrasenia()));
		return 0;
	}

	/**
	 * Método auxiliar para verificar si una cadena de texto está vacía o en blanco.
	 * 
	 * @param s cadena de texto a evaluar.
	 * @return {@code true} si es nula o vacía, {@code false} en caso contrario.
	 */
	private boolean vacio(String s) {
		return s == null || s.trim().isEmpty();
	}

	/**
	 * Verifica si un número telefónico ya se encuentra registrado por otro usuario.
	 * 
	 * @param numeroTelefono número telefónico a consultar.
	 * @return {@code true} si ya existe, {@code false} en caso contrario.
	 */
	public boolean findUsernameAlreadyTaken(String numeroTelefono) {
		Optional<Usuario> found = usuarioRepository.buscarPorNumero(numeroTelefono);
		return found.isPresent();
	}

	/**
	 * Valida que el usuario actual autenticado en el contexto de seguridad coincida con el identificador solicitado,
	 * lanzando excepciones HTTP en caso de no estar autenticado o no tener permisos.
	 * 
	 * @param idUsuario identificador del usuario que se desea validar.
	 * @throws ResponseStatusException con estado 401 (UNAUTHORIZED) si no hay sesión, o 403 (FORBIDDEN) si no coincide el usuario.
	 */
	public void validarUsuarioAutenticado(Long idUsuario) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth.getName() == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
		}
		Long idAutenticado = usuarioRepository.buscarPorNumero(auth.getName()).map(Usuario::getId).orElse(null);
		if (idAutenticado == null || !idAutenticado.equals(idUsuario)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes realizar esta acción por otro usuario");
		}
	}

	/**
	 * Valida la autenticación y elimina un usuario del sistema de forma definitiva.
	 * 
	 * @param idUsuario identificador único del usuario a eliminar.
	 */
	@Transactional
	public void eliminarUsuario(Long idUsuario) {
		validarUsuarioAutenticado(idUsuario);
		usuarioRepository.eliminarUsuario(idUsuario);
	}

	/**
	 * Valida y actualiza la información de un usuario existente, incluyendo la opción de cambiar contraseña y foto de perfil.
	 * 
	 * @param usuarioDTO objeto con los datos actualizados del usuario.
	 * @param foto nuevo archivo de imagen de perfil opcional.
	 * @return código de resultado numérico (0 si es exitoso, 1 si el teléfono pertenece a otro usuario, 2 si hay campos obligatorios vacíos, 4 si la nueva contraseña es inválida, 5 si la imagen es inválida).
	 */
	@Transactional
	public int actualizarUsuario(UsuarioDTO usuarioDTO, MultipartFile foto) {
		
		if (usuarioDTO.getId() == null || vacio(usuarioDTO.getNumeroTelefonico()) || vacio(usuarioDTO.getPrimerNombre())
				|| vacio(usuarioDTO.getPrimerApellido())) {
			return 2;
		}

		String hash = null;
		if (!vacio(usuarioDTO.getContrasenia())) {
			if (!usuarioDTO.getContrasenia().matches("^(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{7,}$")) {
				return 4;
			}
			hash = passwordEncoder.encode(usuarioDTO.getContrasenia());
		}

		boolean numeroDeOtro = usuarioRepository.buscarPorNumero(usuarioDTO.getNumeroTelefonico())
				.filter(u -> !u.getId().equals(usuarioDTO.getId())).isPresent();
		if (numeroDeOtro) {
			return 1;
		}

		if (foto != null && !foto.isEmpty()) {
			if (foto.getContentType() == null || !foto.getContentType().startsWith("image/")
					|| foto.getSize() > 5 * 1024 * 1024) {
				return 5; 
			}
			usuarioDTO.setFotoPerfilUrl(guardarFoto(foto));
		}

		usuarioRepository.actualizarUsuario(usuarioDTO.getId(), usuarioDTO.getPrimerNombre(), usuarioDTO.getSegundoNombre(),
				usuarioDTO.getPrimerApellido(), usuarioDTO.getSegundoApellido(), usuarioDTO.getNumeroTelefonico(), usuarioDTO.getFotoPerfilUrl(), hash);
		return 0;
	}

	/**
	 * Guarda físicamente la foto de perfil de un usuario en el directorio de almacenamiento local del servidor.
	 * 
	 * @param foto archivo multipart de imagen.
	 * @return la URL pública de acceso al recurso guardado.
	 * @throws UncheckedIOException si ocurre un error de E/S durante el almacenamiento.
	 */
	private String guardarFoto(MultipartFile foto) {
		try {
			String tipo = foto.getContentType();
			String ext = tipo.substring(tipo.indexOf('/') + 1);
			String nombre = UUID.randomUUID() + "." + ext;
			Path dir = Paths.get(System.getProperty("user.dir"), "uploads", "perfiles");
			Files.createDirectories(dir);
			Files.copy(foto.getInputStream(), dir.resolve(nombre), StandardCopyOption.REPLACE_EXISTING);
			return baseUrl + "/uploads/perfiles/" + nombre;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Busca un usuario por su número telefónico y lo mapea a un objeto {@link UsuarioDTO}.
	 * 
	 * @param numTelefono número telefónico del usuario a buscar.
	 * @return un {@link UsuarioDTO} con los datos del usuario, o {@code null} si no es encontrado.
	 */
	@Transactional
	public UsuarioDTO getByNumeroTelefonico(String numTelefono) {
		Optional<Usuario> optionalUsuario = usuarioRepository.buscarPorNumero(numTelefono);
		if (optionalUsuario.isEmpty()) {
			return null;
		}
		Usuario entity = optionalUsuario.get();
		UsuarioDTO dto = new UsuarioDTO();

		dto.setId(entity.getId());
		dto.setPrimerNombre(entity.getPrimerNombre());
		dto.setSegundoNombre(entity.getSegundoNombre());
		dto.setPrimerApellido(entity.getPrimerApellido());
		dto.setSegundoApellido(entity.getSegundoApellido());
		dto.setFotoPerfilUrl(entity.getFotoPerfilUrl());
		dto.setNumeroTelefonico(entity.getNumeroTelefonico());

		return dto;
	}
	
	/**
	 * Busca y retorna la entidad {@link Usuario} envuelta en un {@link Optional} a partir de su número telefónico.
	 * 
	 * @param numeroTelefonico número telefónico de búsqueda.
	 * @return un {@link Optional} con la entidad {@link Usuario} correspondiente.
	 */
	public Optional<Usuario> buscarPorNumero(String numeroTelefonico) {
	    return usuarioRepository.buscarPorNumero(numeroTelefonico);
	}

}
