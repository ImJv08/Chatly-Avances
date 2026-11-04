package co.edu.unbosque.mensajeria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.mensajeria.dto.GrupoDTO;
import co.edu.unbosque.mensajeria.dto.MiembroGrupoDTO;
import co.edu.unbosque.mensajeria.entity.Grupo;
import co.edu.unbosque.mensajeria.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	private final GrupoService grupoService;

	public GrupoController(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	@PostMapping("/crearGrupo")
	public ResponseEntity<?> crearGrupo(@RequestBody GrupoDTO grupoDTO) {

		grupoService.crearGrupo(grupoDTO);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/agregarMiembros/{idOrigen}")
	public ResponseEntity<?> agregarMiembro(@RequestBody MiembroGrupoDTO miembroGrupoDTO, @PathVariable Long idOrigen) {

		grupoService.agregarMiembro(miembroGrupoDTO, idOrigen);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/eliminarGrupo/{idGrupo}/{idCreador}")
	public ResponseEntity<?> eliminarGrupo(@PathVariable Long idGrupo, @PathVariable Long idCreador) {

		grupoService.eliminarGrupo(idGrupo, idCreador);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/actualizarGrupo/{idCreador}")
	public ResponseEntity<?> actualizarGrupo(@RequestBody GrupoDTO grupoDTO, @PathVariable Long idCreador) {

		grupoService.actualizarGrupo(grupoDTO, idCreador);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/salirGrupo")
	public ResponseEntity<?> salirGrupo(@RequestBody MiembroGrupoDTO miembroGrupoDTO) {

		grupoService.salirGrupo(miembroGrupoDTO);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/eliminarMiembroGrupo/{idOrigen}")
	public ResponseEntity<?> eliminarMiembroGrupo(@RequestBody MiembroGrupoDTO miembroGrupoDTO,
			@PathVariable Long idOrigen) {

		grupoService.eliminarMiembroGrupo(miembroGrupoDTO, idOrigen);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/listaGrupos/{idUsuario}")
	public ResponseEntity<List<Grupo>> listarGruposPorUsuario(
	        @PathVariable Long idUsuario) {

	    return ResponseEntity.ok(grupoService.listarGruposPorUsuario(idUsuario));
	}
	
}