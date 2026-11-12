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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.mensajeria.dto.GrupoDTO;
import co.edu.unbosque.mensajeria.dto.MiembroGrupoDTO;
import co.edu.unbosque.mensajeria.entity.Grupo;
import co.edu.unbosque.mensajeria.service.GrupoService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los grupos de chat,
 * tales como creación, actualización, gestión de miembros, roles de administrador y listados.
 * Expone endpoints bajo el prefijo {@code /grupos}.
 */
@RestController
@RequestMapping("/grupos")
public class GrupoController {

	/**
	 * Servicio de lógica de negocio encargado de procesar la persistencia, 
	 * validaciones y reglas de negocio asociadas a los grupos de chat y sus miembros.
	 */
	private final GrupoService grupoService;

	/**
	 * Constructor para la inyección de dependencias del servicio de grupos.
	 * 
	 * @param grupoService el servicio de grupos que será inyectado por Spring.
	 */
	public GrupoController(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	/**
	 * Crea un nuevo grupo en el sistema, permitiendo adjuntar opcionalmente una imagen de perfil
	 * y validando las restricciones del nombre y el tamaño del archivo multimedia.
	 * 
	 * @param grupoDTO objeto DTO que contiene la información general del grupo a crear.
	 * @param foto archivo multimedia opcional que servirá como imagen de perfil del grupo.
	 * @return un {@link ResponseEntity} con el estado HTTP y mensaje correspondiente según el código de resultado del servicio.
	 */
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

	/**
	 * Agrega un nuevo miembro a un grupo específico, validando las restricciones del usuario origen.
	 * 
	 * @param miembroGrupoDTO objeto DTO con la información de la relación entre el grupo y el usuario a agregar.
	 * @param idOrigen el identificador único del usuario que realiza la acción de agregar al miembro.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si se agrega con éxito, o HTTP 409 (CONFLICT) si no es posible.
	 */
	@PostMapping("/agregarMiembros/{idOrigen}")
	public ResponseEntity<?> agregarMiembro(@RequestBody MiembroGrupoDTO miembroGrupoDTO, @PathVariable Long idOrigen) {

		boolean agregado = grupoService.agregarMiembro(miembroGrupoDTO, idOrigen);

		if (!agregado) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(Map.of("mensaje", "No se puede agregar a este usuario"));
		}

		return ResponseEntity.ok().build();
	}

	/**
	 * Elimina un grupo del sistema de forma permanente, validando que sea solicitado por su creador.
	 * 
	 * @param idGrupo el identificador único del grupo que se desea eliminar.
	 * @param idCreador el identificador único del usuario creador que autoriza la eliminación.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si la eliminación se completa correctamente.
	 */
	@DeleteMapping("/eliminarGrupo/{idGrupo}/{idCreador}")
	public ResponseEntity<?> eliminarGrupo(@PathVariable Long idGrupo, @PathVariable Long idCreador) {

		grupoService.eliminarGrupo(idGrupo, idCreador);

		return ResponseEntity.ok().build();
	}

	/**
	 * Actualiza la información de un grupo existente (como su nombre o imagen), 
	 * validando que sea ejecutado por el creador y verificando las restricciones del archivo.
	 * 
	 * @param grupoDTO objeto DTO con los nuevos datos del grupo.
	 * @param idCreador el identificador único del usuario creador autorizado para actualizar.
	 * @param foto archivo de imagen opcional para actualizar la foto de perfil del grupo.
	 * @return un {@link ResponseEntity} con el resultado de la actualización y su respectivo código de estado HTTP.
	 */
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

	/**
	 * Permite a un usuario salir voluntariamente de un grupo al que pertenece.
	 * 
	 * @param miembroGrupoDTO objeto DTO que especifica el usuario y el grupo del cual se va a retirar.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si la acción se procesa correctamente.
	 */
	@DeleteMapping("/salirGrupo")
	public ResponseEntity<?> salirGrupo(@RequestBody MiembroGrupoDTO miembroGrupoDTO) {

		grupoService.salirGrupo(miembroGrupoDTO);

		return ResponseEntity.ok().build();
	}

	/**
	 * Elimina a un miembro específico de un grupo, validando los permisos del usuario origen.
	 * 
	 * @param miembroGrupoDTO objeto DTO que identifica al miembro y grupo objetivo de la expulsión.
	 * @param idOrigen el identificador único del usuario que ejecuta la acción de eliminar al miembro.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si el miembro es eliminado con éxito.
	 */
	@DeleteMapping("/eliminarMiembroGrupo/{idOrigen}")
	public ResponseEntity<?> eliminarMiembroGrupo(@RequestBody MiembroGrupoDTO miembroGrupoDTO,
			@PathVariable Long idOrigen) {

		grupoService.eliminarMiembroGrupo(miembroGrupoDTO, idOrigen);

		return ResponseEntity.ok().build();
	}

	/**
	 * Obtiene la lista de todos los grupos a los que pertenece un usuario específico.
	 * 
	 * @param idUsuario el identificador único del usuario del cual se consultarán sus grupos.
	 * @return un {@link ResponseEntity} que contiene una lista de objetos {@link Grupo}.
	 */
	@GetMapping("/listaGrupos/{idUsuario}")
	public ResponseEntity<List<Grupo>> listarGruposPorUsuario(
	        @PathVariable Long idUsuario) {

	    return ResponseEntity.ok(grupoService.listarGruposPorUsuario(idUsuario));
	}
	
	/**
	 * Obtiene la lista de grupos asociados a un usuario incluyendo el rol que desempeña en cada uno de ellos.
	 * 
	 * @param idUsuario el identificador único del usuario a consultar.
	 * @return un {@link ResponseEntity} con una lista de mapas que representan los datos del grupo y su respectivo rol.
	 */
	@GetMapping("/usuario/{idUsuario}/con-rol")
	public ResponseEntity<List<Map<String, Object>>> listarGruposConRol(
	        @PathVariable Long idUsuario) {

	    return ResponseEntity.ok(
	            grupoService.listarGruposConRol(idUsuario)
	    );
	}
	
	/**
	 * Obtiene la lista detallada de todos los miembros que pertenecen a un grupo específico.
	 * 
	 * @param idGrupo el identificador único del grupo del cual se listarán los miembros.
	 * @return un {@link ResponseEntity} que contiene una lista de mapas con la información de los miembros del grupo.
	 */
	@GetMapping("/{idGrupo}/miembros")
	public ResponseEntity<List<Map<String, Object>>> listarMiembrosGrupo(@PathVariable Long idGrupo) {

	    return ResponseEntity.ok(grupoService.listarMiembrosGrupo(idGrupo));
	}
	
	/**
	 * Asigna el rol de administrador a un miembro dentro de un grupo, validando los permisos del usuario origen.
	 * 
	 * @param miembroGrupoDTO objeto DTO que contiene el identificador del grupo y del usuario al que se le modificará el rol.
	 * @param idOrigen el identificador único del usuario que autoriza y ejecuta el cambio de rol.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si el cambio de rol se efectúa correctamente.
	 */
	@PutMapping("/cambiarRolAdmin/{idOrigen}")
	public ResponseEntity<?> cambiarRolAdmin(@RequestBody MiembroGrupoDTO miembroGrupoDTO,  @PathVariable Long idOrigen) {

	    grupoService.cambiarRolAdmin(miembroGrupoDTO.getGrupo(), miembroGrupoDTO.getUsuario(), idOrigen);

	    return ResponseEntity.ok().build();
	}
	
}