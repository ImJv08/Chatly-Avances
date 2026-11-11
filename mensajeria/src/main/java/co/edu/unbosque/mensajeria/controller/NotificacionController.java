package co.edu.unbosque.mensajeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.mensajeria.service.NotificacionService;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

	@Autowired
	private NotificacionService notificacionService;
	
	@GetMapping("/{idUsuario}")
	public ResponseEntity<?> listarNotificaciones(@PathVariable Long idUsuario) {
	    return ResponseEntity.ok(notificacionService.listarNotificaciones(idUsuario));
	}
	
	@PutMapping("/{id}/leer")
	public ResponseEntity<?> marcarLeida(@PathVariable Long id) {
	    notificacionService.marcarLeida(id);return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{idUsuario}/no-leidas")
	public ResponseEntity<Long> contarNoLeidas(@PathVariable Long idUsuario) {
	    return ResponseEntity.ok(notificacionService.contarNoLeidas(idUsuario));
	}
	
	@PutMapping("/usuario/{idUsuario}/leer-todas")
	public ResponseEntity<?> marcarTodasLeidas(@PathVariable Long idUsuario) {
	    notificacionService.marcarTodasLeidas(idUsuario);
	    return ResponseEntity.ok().build();
	}
}
