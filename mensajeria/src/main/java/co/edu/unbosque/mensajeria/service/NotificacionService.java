package co.edu.unbosque.mensajeria.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.NotificacionDTO;
import co.edu.unbosque.mensajeria.repository.NotificacionRepository;
import jakarta.transaction.Transactional;

/**
 * Servicio de lógica de negocio para la gestión de notificaciones del sistema,
 * encargado de procesar la consulta y mapeo de notificaciones por usuario, 
 * el marcado de lectura individual o masivo, y el conteo de notificaciones pendientes.
 */
@Service
public class NotificacionService {

	/**
	 * Repositorio para la persistencia, procedimientos y funciones nativas de notificaciones.
	 */
	private final NotificacionRepository notificacionRepository;
	
	/**
	 * Constructor para la inyección de dependencias del repositorio de notificaciones.
	 * 
	 * @param notificacionRepository repositorio de notificaciones.
	 */
	public NotificacionService(NotificacionRepository notificacionRepository) {
		this.notificacionRepository = notificacionRepository;
	}
	
	/**
	 * Lista todas las notificaciones dirigidas a un usuario específico mediante una función SQL nativa,
	 * transformando los arreglos de objetos resultantes en una lista de objetos {@link NotificacionDTO}.
	 * 
	 * @param idUsuario identificador del usuario receptor de las notificaciones.
	 * @return una lista de objetos {@link NotificacionDTO} mapeados con los detalles de cada notificación.
	 */
	public List<NotificacionDTO> listarNotificaciones(Long idUsuario) {
		
	    return notificacionRepository.listarNotificaciones(idUsuario)
	        .stream()
	        .map(n -> new NotificacionDTO(
	            ((Number) n[0]).longValue(),
	            ((Number) n[1]).longValue(),
	            ((Number) n[2]).longValue(),
	            (Boolean) n[3],
	            (String) n[4],
	            Date.from(
	                ((LocalDateTime) n[5])
	                    .atZone(ZoneId.systemDefault())
	                    .toInstant()
	            )
	        ))
	        .toList();
	}
	
	/**
	 * Marca una notificación específica como leída a través de un procedimiento almacenado.
	 * 
	 * @param id identificador único de la notificación que se actualizará.
	 */
	@Transactional
	public void marcarLeida(Long id) {
	    notificacionRepository.marcarLeida(id);
	}
	
	/**
	 * Cuenta la cantidad total de notificaciones no leídas para un usuario específico.
	 * 
	 * @param idUsuario identificador del usuario consultado.
	 * @return el número total de notificaciones pendientes de lectura, o {@code 0L} si no hay ninguna.
	 */
	public Long contarNoLeidas(Long idUsuario) {
	    Number n = notificacionRepository.contarNoLeidas(idUsuario);
	    return n == null ? 0L : n.longValue();
	}
	
	/**
	 * Marca todas las notificaciones de un usuario como leídas mediante un procedimiento almacenado.
	 * 
	 * @param idUsuario identificador del usuario cuyas notificaciones se actualizarán de forma masiva.
	 */
	@Transactional
	public void marcarTodasLeidas(Long idUsuario) {
	    notificacionRepository.marcarTodasLeidas(idUsuario);
	}
}
