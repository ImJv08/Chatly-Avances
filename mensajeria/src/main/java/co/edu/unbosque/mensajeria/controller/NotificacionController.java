package co.edu.unbosque.mensajeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
