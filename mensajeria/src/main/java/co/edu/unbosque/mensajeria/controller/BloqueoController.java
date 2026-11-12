package co.edu.unbosque.mensajeria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.mensajeria.dto.BloqueoDTO;
import co.edu.unbosque.mensajeria.service.BloqueoService;

/**
 * Controlador REST que gestiona las operaciones de bloqueo y desbloqueo de usuarios 
 * dentro de la plataforma de mensajeria. Expone endpoints bajo el prefijo {@code /bloqueos}.
 */

@RestController
@RequestMapping("/bloqueos")
public class BloqueoController {

	/**
	 * Servicio de lógica de negocio encargado de procesar las reglas y persistencia 
	 * asociadas a los bloqueos entre usuarios.
	 */
	private final BloqueoService bloqueoService;

	/**
	 * Constructor para la inyección de dependencias del servicio de bloqueos.
	 * 
	 * @param bloqueoService el servicio de bloqueos que será inyectado por Spring.
	 */
	public BloqueoController(BloqueoService bloqueoService) {
		this.bloqueoService = bloqueoService;
	}

	
	/**
	 * Bloquea a un usuario a partir de los datos proporcionados en el cuerpo de la solicitud.
	 * 
	 * @param bloqueoDTO objeto DTO que contiene el ID del usuario que realiza el bloqueo (`idUsuario`) 
	 *                   y el ID del usuario que va a ser bloqueado (`idUsuarioBloqueado`).
	 * @return un {@link ResponseEntity} con estado HTTP 201 (CREATED) si el bloqueo se registra con éxito.
	 */
	@PostMapping("/bloquear")
	public ResponseEntity<?> bloquearUsuario(@RequestBody BloqueoDTO bloqueoDTO) {
		bloqueoService.bloquearUsuario(bloqueoDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * Desbloquea a un usuario previamente bloqueado.
	 * 
	 * @param idUsuario el identificador único del usuario que desea desbloquear.
	 * @param idUsuarioBloqueado el identificador único del usuario que va a ser desbloqueado.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si la operación se completa correctamente.
	 */
	@DeleteMapping("/desbloquear/{idUsuario}/{idUsuarioBloqueado}")
	public ResponseEntity<?> desbloquearUsuario(@PathVariable Long idUsuario, @PathVariable Long idUsuarioBloqueado) {
		bloqueoService.desbloquearUsuario(idUsuario, idUsuarioBloqueado);
		return ResponseEntity.ok().build();
	}

	/**
	 * Obtiene la lista de usuarios que han sido bloqueados por un usuario específico.
	 * 
	 * @param idUsuario el identificador único del usuario del cual se quiere consultar su lista de bloqueados.
	 * @return un {@link ResponseEntity} que contiene una lista de objetos {@link BloqueoDTO} con los usuarios bloqueados.
	 */
	@GetMapping("/listaBloqueados/{idUsuario}")
	public ResponseEntity<List<BloqueoDTO>> listarBloqueados(@PathVariable Long idUsuario) {
		return ResponseEntity.ok(bloqueoService.listarBloqueados(idUsuario));
	}
	
	/**
	 * Obtiene la lista de usuarios que han bloqueado a un usuario específico.
	 * 
	 * @param idUsuario el identificador único del usuario del cual se quiere saber quién lo ha bloqueado.
	 * @return un {@link ResponseEntity} que contiene una lista de objetos {@link BloqueoDTO} con los usuarios que realizaron el bloqueo.
	 */
	@GetMapping("/quienMeBloqueo/{idUsuario}")
	public ResponseEntity<List<BloqueoDTO>> listarQuienMeBloqueo(
	        @PathVariable Long idUsuario) {

	    return ResponseEntity.ok(bloqueoService.listarQuienMeBloqueo(idUsuario));
	}
}	