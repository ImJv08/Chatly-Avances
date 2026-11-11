package co.edu.unbosque.mensajeria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.UsuarioDTO;
import co.edu.unbosque.mensajeria.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
@Service
public class UsuarioService {

	@Autowired
	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Transactional
	public void crearUsuario(UsuarioDTO usuarioDTO) {
		usuarioRepository.crearUsuario(usuarioDTO.getPrimerNombre(), usuarioDTO.getSegundoNombre(),
				usuarioDTO.getPrimerApellido(), usuarioDTO.getSegundoApellido(), usuarioDTO.getNumeroTelefonico(),
				usuarioDTO.getFotoPerfilUrl());
	}

	public void eliminarUsuario(Long idUsuario) {
		usuarioRepository.eliminarUsuario(idUsuario);
	}

	public void actualizarUsuario(UsuarioDTO usuarioDTO) {
		usuarioRepository.actualizarUsuario(usuarioDTO.getId(), usuarioDTO.getPrimerNombre(),
				usuarioDTO.getSegundoNombre(), usuarioDTO.getPrimerApellido(), usuarioDTO.getSegundoApellido(),
				usuarioDTO.getNumeroTelefonico(), usuarioDTO.getFotoPerfilUrl());
	}

}
