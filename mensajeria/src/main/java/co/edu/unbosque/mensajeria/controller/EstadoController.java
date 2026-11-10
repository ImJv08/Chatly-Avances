package co.edu.unbosque.mensajeria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@PostMapping(value = "/crearEstado", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> crearEstado(@RequestPart("estado") EstadoDTO dto,
			@RequestPart("archivo") MultipartFile archivo) {
		switch (estadoService.crearEstado(dto, archivo)) {
		case 0: 
			return ResponseEntity.status(HttpStatus.CREATED).body("Estado publicado");
		case 2: 
			return ResponseEntity.badRequest().body("Debes elegir una imagen o un video");
		case 5: 
			return ResponseEntity.badRequest().body("El archivo debe ser una imagen o un video");
		case 6: 
			return ResponseEntity.badRequest().body("Imagen máximo 5 MB, video máximo 30 MB");
		case 7: 
			return ResponseEntity.badRequest().body("La descripción admite máximo 500 caracteres");
		default: 
			return ResponseEntity.internalServerError().body("Error desconocido");
		}
	}
	
	@DeleteMapping("/eliminarEstado/{idEstado}")
	public ResponseEntity<?> eliminarEstado(@PathVariable Long idEstado) {

		estadoService.eliminarEstado(idEstado);

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/listaEstados/{idUsuario}")
	public ResponseEntity<List<EstadoDTO>> listar(@PathVariable Long idUsuario) {
		return ResponseEntity.ok(estadoService.listarEstadosPorContactos(idUsuario));
	}	


}
