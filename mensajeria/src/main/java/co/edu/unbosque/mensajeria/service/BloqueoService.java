package co.edu.unbosque.mensajeria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.BloqueoDTO;
import co.edu.unbosque.mensajeria.entity.Bloqueo;
import co.edu.unbosque.mensajeria.entity.Usuario;
import co.edu.unbosque.mensajeria.repository.BloqueoRepository;
import jakarta.transaction.Transactional;

@Service
public class BloqueoService {

	private final BloqueoRepository bloqueoRepository;
	private final UsuarioService usuarioService;

	public BloqueoService(BloqueoRepository bloqueoRepository, UsuarioService usuarioService) {
		this.bloqueoRepository = bloqueoRepository;
		this.usuarioService = usuarioService;
	}

	@Transactional
	public void bloquearUsuario(BloqueoDTO bloqueoDTO) {
		usuarioService.validarUsuarioAutenticado(bloqueoDTO.getIdUsuario());
		bloqueoRepository.bloquearUsuario(bloqueoDTO.getIdUsuario(), bloqueoDTO.getIdUsuarioBloqueado());
	}

	@Transactional
	public void desbloquearUsuario(Long idUsuario, Long idUsuarioBloqueado) {
		usuarioService.validarUsuarioAutenticado(idUsuario);
		bloqueoRepository.desbloquearUsuario(idUsuario, idUsuarioBloqueado);
	}

	@Transactional
	public List<BloqueoDTO> listarBloqueados(Long idUsuario) {
		usuarioService.validarUsuarioAutenticado(idUsuario);
		return bloqueoRepository.listarBloqueosPorUsuario(idUsuario).stream().map(this::convertirADTO).toList();
	}

	private BloqueoDTO convertirADTO(Bloqueo b) {
		Usuario bloqueado = b.getUsuarioBloqueado();
		BloqueoDTO dto = new BloqueoDTO();
		dto.setId(b.getIdBloqueo());
		dto.setIdUsuario(b.getUsuarioQueBloquea().getId());
		dto.setIdUsuarioBloqueado(bloqueado.getId());
		dto.setNombreBloqueado(bloqueado.getPrimerNombre() + " " + bloqueado.getPrimerApellido());
		dto.setNumeroTelefonicoBloqueado(bloqueado.getNumeroTelefonico());
		dto.setFotoPerfilUrlBloqueado(bloqueado.getFotoPerfilUrl());
		dto.setFecha(b.getFecha());
		return dto;
	}
}