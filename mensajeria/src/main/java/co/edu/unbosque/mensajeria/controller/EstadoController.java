package co.edu.unbosque.mensajeria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.mensajeria.dto.EstadoDTO;
import co.edu.unbosque.mensajeria.service.EstadoService;

/**
 * Controlador REST que gestiona las operaciones asociadas a los estados multimedia (historias) 
 * de los usuarios, tales como publicación con validaciones de archivos, eliminación y listado 
 * por contactos. Expone endpoints bajo el prefijo {@code /estados}.
 */
@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	/**
	 * Servicio de lógica de negocio encargado de procesar la creación, persistencia,
	 * validación de archivos multimedia y consulta de estados.
	 */
	private final EstadoService estadoService;

	/**
	 * Constructor para la inyección de dependencias del servicio de estados.
	 * 
	 * @param estadoService el servicio de estados que será inyectado por Spring.
	 */
	public EstadoController(EstadoService estadoService) {
		this.estadoService = estadoService;
	}
	
	/**
	 * Crea y publica un nuevo estado multimedia asociado a un usuario, validando las restricciones 
	 * de tipo de archivo, tamaño y longitud de la descripción.
	 * 
	 * @param dto objeto DTO que contiene la información del estado (como ID de usuario y descripción).
	 * @param archivo el archivo multimedia adjunto (imagen o video) que conforma el contenido del estado.
	 * @return un {@link ResponseEntity} con el estado HTTP y mensaje correspondiente según el código de validación del servicio.
	 */
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
	
	/**
	 * Elimina un estado existente del sistema a partir de su identificador único.
	 * 
	 * @param idEstado el identificador único del estado que se desea eliminar.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si la eliminación se efectúa correctamente.
	 */
	@DeleteMapping("/eliminarEstado/{idEstado}")
	public ResponseEntity<?> eliminarEstado(@PathVariable Long idEstado) {

		estadoService.eliminarEstado(idEstado);

		return ResponseEntity.ok().build();
	}
	
	/**
	 * Obtiene la lista de estados publicados por los contactos de un usuario específico.
	 * 
	 * @param idUsuario el identificador único del usuario del cual se desean consultar los estados de sus contactos.
	 * @return un {@link ResponseEntity} que contiene una lista de objetos {@link EstadoDTO} con los estados activos.
	 */
	@GetMapping("/listaEstados/{idUsuario}")
	public ResponseEntity<List<EstadoDTO>> listar(@PathVariable Long idUsuario) {
		return ResponseEntity.ok(estadoService.listarEstadosPorContactos(idUsuario));
	}	


}
