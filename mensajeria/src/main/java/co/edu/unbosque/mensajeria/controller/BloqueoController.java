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

@RestController
@RequestMapping("/bloqueos")
public class BloqueoController {

	private final BloqueoService bloqueoService;

	public BloqueoController(BloqueoService bloqueoService) {
		this.bloqueoService = bloqueoService;
	}

	// Body: { "idUsuario": 1, "idUsuarioBloqueado": 2 }
	@PostMapping("/bloquear")
	public ResponseEntity<?> bloquearUsuario(@RequestBody BloqueoDTO bloqueoDTO) {
		bloqueoService.bloquearUsuario(bloqueoDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/desbloquear/{idUsuario}/{idUsuarioBloqueado}")
	public ResponseEntity<?> desbloquearUsuario(@PathVariable Long idUsuario, @PathVariable Long idUsuarioBloqueado) {
		bloqueoService.desbloquearUsuario(idUsuario, idUsuarioBloqueado);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/listaBloqueados/{idUsuario}")
	public ResponseEntity<List<BloqueoDTO>> listarBloqueados(@PathVariable Long idUsuario) {
		return ResponseEntity.ok(bloqueoService.listarBloqueados(idUsuario));
	}
}	