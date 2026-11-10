package co.edu.unbosque.mensajeria.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

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

	@PostMapping(value = "/crearGrupo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> crearGrupo(@RequestPart("grupo") GrupoDTO grupoDTO,
			@RequestPart(value = "foto", required = false) MultipartFile foto) {
		int r = grupoService.crearGrupo(grupoDTO, foto);
		switch (r) {
		case 0: 
			return ResponseEntity.status(HttpStatus.CREATED).build();
		case 2: 
			return ResponseEntity.badRequest().body("El grupo necesita un nombre");
		case 5: 
			return ResponseEntity.badRequest().body("La imagen debe ser un archivo de imagen de máximo 5 MB");
		default: 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
		}
	}

	@PostMapping("/agregarMiembros/{idOrigen}")
	public ResponseEntity<?> agregarMiembro(@RequestBody MiembroGrupoDTO miembroGrupoDTO, @PathVariable Long idOrigen) {

		boolean agregado = grupoService.agregarMiembro(miembroGrupoDTO, idOrigen);

		if (!agregado) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(Map.of("mensaje", "No se puede agregar a este usuario"));
		}

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/eliminarGrupo/{idGrupo}/{idCreador}")
	public ResponseEntity<?> eliminarGrupo(@PathVariable Long idGrupo, @PathVariable Long idCreador) {

		grupoService.eliminarGrupo(idGrupo, idCreador);

		return ResponseEntity.ok().build();
	}

	@PutMapping(value = "/actualizarGrupo/{idCreador}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> actualizarGrupo(@RequestPart("grupo") GrupoDTO grupoDTO,
			@PathVariable Long idCreador, @RequestPart(value = "foto", required = false) MultipartFile foto) {

		switch(grupoService.actualizarGrupo(grupoDTO, idCreador, foto)) {
		case 0:
			return ResponseEntity.ok("Grupo actualiazdo");
		case 2:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe ponerle un nombre al grupo");
		case 5:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La imageb debe ser un archivo de imagen de máximo 5 MB");
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
		}

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
	
	@GetMapping("/usuario/{idUsuario}/con-rol")
	public ResponseEntity<List<Map<String, Object>>> listarGruposConRol(
	        @PathVariable Long idUsuario) {

	    return ResponseEntity.ok(
	            grupoService.listarGruposConRol(idUsuario)
	    );
	}
	
	@GetMapping("/{idGrupo}/miembros")
	public ResponseEntity<List<Map<String, Object>>> listarMiembrosGrupo(@PathVariable Long idGrupo) {

	    return ResponseEntity.ok(grupoService.listarMiembrosGrupo(idGrupo));
	}
	
	@PutMapping("/cambiarRolAdmin/{idOrigen}")
	public ResponseEntity<?> cambiarRolAdmin(@RequestBody MiembroGrupoDTO miembroGrupoDTO,  @PathVariable Long idOrigen) {

	    grupoService.cambiarRolAdmin(miembroGrupoDTO.getGrupo(), miembroGrupoDTO.getUsuario(), idOrigen);

	    return ResponseEntity.ok().build();
	}
	
}