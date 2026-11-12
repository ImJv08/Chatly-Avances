package co.edu.unbosque.mensajeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.mensajeria.service.NotificacionService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con las notificaciones de los usuarios,
 * tales como listado, conteo de no leídas, marcado individual como leída y marcado general de todas como leídas.
 * Expone endpoints bajo el prefijo {@code /notificaciones}.
 */
@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
	
	/**
	 * Servicio de lógica de negocio encargado de procesar la persistencia, consulta
	 * y actualización del estado de las notificaciones de los usuarios.
	 */
	@Autowired
	private NotificacionService notificacionService;
	
	/**
	 * Obtiene la lista completa de notificaciones asociadas a un usuario específico.
	 * 
	 * @param idUsuario el identificador único del usuario del cual se consultarán las notificaciones.
	 * @return un {@link ResponseEntity} que contiene la lista de notificaciones del usuario.
	 */
	@GetMapping("/{idUsuario}")
	public ResponseEntity<?> listarNotificaciones(@PathVariable Long idUsuario) {
	    return ResponseEntity.ok(notificacionService.listarNotificaciones(idUsuario));
	}
	
	/**
	 * Marca una notificación específica como leída a partir de su identificador.
	 * 
	 * @param id el identificador único de la notificación que se actualizará.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si la operación se completa correctamente.
	 */
	@PutMapping("/{id}/leer")
	public ResponseEntity<?> marcarLeida(@PathVariable Long id) {
	    notificacionService.marcarLeida(id);return ResponseEntity.ok().build();
	}
	
	/**
	 * Cuenta la cantidad de notificaciones que se encuentran pendientes por leer para un usuario.
	 * 
	 * @param idUsuario el identificador único del usuario del cual se contará el número de notificaciones no leídas.
	 * @return un {@link ResponseEntity} que contiene la cantidad total de notificaciones no leídas de tipo {@link Long}.
	 */
	@GetMapping("/{idUsuario}/no-leidas")
	public ResponseEntity<Long> contarNoLeidas(@PathVariable Long idUsuario) {
	    return ResponseEntity.ok(notificacionService.contarNoLeidas(idUsuario));
	}
	
	/**
	 * Marca todas las notificaciones pendientes de un usuario como leídas en una sola operación.
	 * 
	 * @param idUsuario el identificador único del usuario cuyas notificaciones se marcarán como leídas.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si la acción se efectúa correctamente.
	 */
	@PutMapping("/usuario/{idUsuario}/leer-todas")
	public ResponseEntity<?> marcarTodasLeidas(@PathVariable Long idUsuario) {
	    notificacionService.marcarTodasLeidas(idUsuario);
	    return ResponseEntity.ok().build();
	}
}
