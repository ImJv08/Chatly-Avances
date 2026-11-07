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

@Service
public class UsuarioService {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	private final UsuarioRepository usuarioRepository;

	@Value("${app.base-url}")
	private String baseUrl;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

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

	private boolean vacio(String s) {
		return s == null || s.trim().isEmpty();
	}

	public boolean findUsernameAlreadyTaken(String numeroTelefono) {
		Optional<Usuario> found = usuarioRepository.buscarPorNumero(numeroTelefono);
		return found.isPresent();
	}

	/**
	 * Verifica que el id recibido pertenezca al usuario del token JWT. El username
	 * del token es el número telefónico.
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

	@Transactional
	public void eliminarUsuario(Long idUsuario) {
		validarUsuarioAutenticado(idUsuario);
		// El procedimiento sale de los grupos, elimina estados y demás dependencias
		usuarioRepository.eliminarUsuario(idUsuario);
	}

	@Transactional
	public int actualizarUsuario(UsuarioDTO d, MultipartFile foto) {
		if (d.getId() == null || vacio(d.getNumeroTelefonico()) || vacio(d.getPrimerNombre())
				|| vacio(d.getPrimerApellido())) {
			return 2;
		}

		String hash = null;
		if (!vacio(d.getContrasenia())) {
			if (!d.getContrasenia().matches("^(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{7,}$")) {
				return 4;
			}
			hash = passwordEncoder.encode(d.getContrasenia());
		}

		boolean numeroDeOtro = usuarioRepository.buscarPorNumero(d.getNumeroTelefonico())
				.filter(u -> !u.getId().equals(d.getId())).isPresent();
		if (numeroDeOtro) {
			return 1;
		}

		if (foto != null && !foto.isEmpty()) {
			if (foto.getContentType() == null || !foto.getContentType().startsWith("image/")
					|| foto.getSize() > 5 * 1024 * 1024) {
				return 5; // imagen inválida
			}
			d.setFotoPerfilUrl(guardarFoto(foto));
		}

		usuarioRepository.actualizarUsuario(d.getId(), d.getPrimerNombre(), d.getSegundoNombre(),
				d.getPrimerApellido(), d.getSegundoApellido(), d.getNumeroTelefonico(), d.getFotoPerfilUrl(), hash);
		return 0;
	}

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

}
