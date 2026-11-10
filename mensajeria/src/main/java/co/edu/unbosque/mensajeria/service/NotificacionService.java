package co.edu.unbosque.mensajeria.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.NotificacionDTO;
import co.edu.unbosque.mensajeria.repository.NotificacionRepository;
import jakarta.transaction.Transactional;

@Service
public class NotificacionService {

	private final NotificacionRepository notificacionRepository;
	
	public NotificacionService(NotificacionRepository notificacionRepository) {
		this.notificacionRepository = notificacionRepository;
	}
	

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
	
	@Transactional
	public void marcarLeida(Long id) {
	    notificacionRepository.marcarLeida(id);
	}
	
	public Long contarNoLeidas(Long idUsuario) {
	    Number n = notificacionRepository.contarNoLeidas(idUsuario);
	    return n == null ? 0L : n.longValue();
	}
	
	@Transactional
	public void marcarTodasLeidas(Long idUsuario) {
	    notificacionRepository.marcarTodasLeidas(idUsuario);
	}
}
