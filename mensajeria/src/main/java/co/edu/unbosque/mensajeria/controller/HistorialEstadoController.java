package co.edu.unbosque.mensajeria.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.mensajeria.entity.HistorialEstado;
import co.edu.unbosque.mensajeria.service.HistorialEstadoService;

/**
 * Expone el historial de estados publicados por un usuario.
 */
@RestController
@RequestMapping("/historial-estados")
public class HistorialEstadoController {

	/** Servicio del historial de estados. */
	private final HistorialEstadoService historialService;

	/**
	 * Constructor para inicializar los objetos.
	 *
	 * @param historialService servicio del historial de estados
	 */
	public HistorialEstadoController(HistorialEstadoService historialService) {
		this.historialService = historialService;
	}

	/**
	 * Lista el historial de estados de un usuario.
	 *
	 * @param idUsuario identificador del usuario
	 * @return 200 con la lista de registros del historial
	 */
	@GetMapping("/{idUsuario}")
	public ResponseEntity<List<HistorialEstado>> listar(@PathVariable Long idUsuario) {
		return ResponseEntity.ok(historialService.listar(idUsuario));
	}
}
