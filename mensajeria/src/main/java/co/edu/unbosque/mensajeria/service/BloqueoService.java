package co.edu.unbosque.mensajeria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.BloqueoDTO;
import co.edu.unbosque.mensajeria.entity.Bloqueo;
import co.edu.unbosque.mensajeria.entity.Usuario;
import co.edu.unbosque.mensajeria.repository.BloqueoRepository;
import jakarta.transaction.Transactional;

/**
 * Servicio de lógica de negocio para la gestión de bloqueos de usuarios, 
 * encargado de procesar la validación de identidad, ejecución de bloqueos/desbloqueos 
 * y transformación de entidades a objetos de transferencia de datos (DTO).
 */
@Service
public class BloqueoService {

	/**
	 * Repositorio para la persistencia y consultas de bloqueos.
	 */
	private final BloqueoRepository bloqueoRepository;
	
	/**
	 * Servicio de usuarios para validaciones de autenticación.
	 */
	private final UsuarioService usuarioService;

	/**
	 * Constructor para la inyección de dependencias de los repositorios y servicios asociados.
	 * 
	 * @param bloqueoRepository repositorio de bloqueos.
	 * @param usuarioService servicio de gestión de usuarios.
	 */
	public BloqueoService(BloqueoRepository bloqueoRepository, UsuarioService usuarioService) {
		this.bloqueoRepository = bloqueoRepository;
		this.usuarioService = usuarioService;
	}

	/**
	 * Valida al usuario autenticado y ejecuta el bloqueo de otro usuario utilizando un DTO de transferencia.
	 * 
	 * @param bloqueoDTO objeto con los identificadores del usuario que bloquea y el usuario bloqueado.
	 */
	@Transactional
	public void bloquearUsuario(BloqueoDTO bloqueoDTO) {
		usuarioService.validarUsuarioAutenticado(bloqueoDTO.getIdUsuario());
		bloqueoRepository.bloquearUsuario(bloqueoDTO.getIdUsuario(), bloqueoDTO.getIdUsuarioBloqueado());
	}

	/**
	 * Valida al usuario autenticado y ejecuta el desbloqueo de un usuario previamente bloqueado.
	 * 
	 * @param idUsuario identificador del usuario que realiza el desbloqueo.
	 * @param idUsuarioBloqueado identificador del usuario que será desbloqueado.
	 */
	@Transactional
	public void desbloquearUsuario(Long idUsuario, Long idUsuarioBloqueado) {
		usuarioService.validarUsuarioAutenticado(idUsuario);
		bloqueoRepository.desbloquearUsuario(idUsuario, idUsuarioBloqueado);
	}

	/**
	 * Valida al usuario autenticado y lista todos los usuarios bloqueados por él, 
	 * convirtiendo las entidades resultantes a formato {@link BloqueoDTO}.
	 * 
	 * @param idUsuario identificador del usuario propietario de los bloqueos.
	 * @return una lista de {@link BloqueoDTO} con los detalles de los usuarios bloqueados.
	 */
	@Transactional
	public List<BloqueoDTO> listarBloqueados(Long idUsuario) {
		usuarioService.validarUsuarioAutenticado(idUsuario);
		return bloqueoRepository.listarBloqueosPorUsuario(idUsuario).stream().map(this::convertirADTO).toList();
	}
	
	/**
	 * Valida al usuario autenticado y lista los registros donde ha sido bloqueado por otros usuarios, 
	 * convirtiendo las entidades a {@link BloqueoDTO}.
	 * 
	 * @param idUsuario identificador del usuario consultado.
	 * @return una lista de {@link BloqueoDTO} con los datos de quiénes lo han bloqueado.
	 */
	public List<BloqueoDTO> listarQuienMeBloqueo(Long idUsuario){
		usuarioService.validarUsuarioAutenticado(idUsuario);
		return bloqueoRepository.listarQuienMeBloqueo(idUsuario).stream().map(this::convertirADTO).toList();
	}
	

	/**
	 * Método auxiliar para convertir una entidad {@link Bloqueo} en un objeto {@link BloqueoDTO}.
	 * 
	 * @param b entidad de bloqueo a transformar.
	 * @return el objeto {@link BloqueoDTO} mapeado con la información detallada.
	 */
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