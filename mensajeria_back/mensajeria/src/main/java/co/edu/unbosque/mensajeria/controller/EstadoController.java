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

import co.edu.unbosque.mensajeria.dto.EstadoDTO;
import co.edu.unbosque.mensajeria.entity.Estado;
import co.edu.unbosque.mensajeria.service.EstadoService;
@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	private final EstadoService estadoService;

	public EstadoController(EstadoService estadoService) {
		this.estadoService = estadoService;
	}
	
	@PostMapping("/crearEstado")
	public ResponseEntity<?> crearEstado(@RequestBody EstadoDTO estadoDTO) {

		estadoService.crearEstado(estadoDTO);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/eliminarEstado/{idEstado}")
	public ResponseEntity<?> eliminarEstado(@PathVariable Long idEstado) {

		estadoService.eliminarEstado(idEstado);

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/listaEstados/{idUsuario}")
	public ResponseEntity<List<Estado>> listarEstadosPorContactos(
	        @PathVariable Long idUsuario) {

	    return ResponseEntity.ok(estadoService.listarEstadosPorContactos(idUsuario));
	}


}
