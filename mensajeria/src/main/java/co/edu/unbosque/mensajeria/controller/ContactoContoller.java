package co.edu.unbosque.mensajeria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.mensajeria.dto.ContactoDTO;
import co.edu.unbosque.mensajeria.dto.ContactoRespuestaDTO;
import co.edu.unbosque.mensajeria.service.ContactoService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador REST que gestiona las operaciones relacionadas con la agenda de contactos 
 * de los usuarios, tales como creación, eliminación, listado, búsqueda y actualización de nombres.
 * Expone endpoints bajo el prefijo {@code /contactos}.
 */
@RestController
@RequestMapping("/contactos")
public class ContactoContoller {
    
	/**
	 * Servicio de lógica de negocio encargado de procesar las operaciones y persistencia 
	 * asociadas a los contactos de los usuarios.
	 */
	private final ContactoService contactoService;

	/**
	 * Constructor para la inyección de dependencias del servicio de contactos.
	 * 
	 * @param contactoService el servicio de contactos que será inyectado por Spring.
	 */
	public ContactoContoller(ContactoService contactoService) {
		this.contactoService = contactoService;
	}
	
	/**
	 * Crea y registra un nuevo contacto asociado a un usuario.
	 * 
	 * @param contactoDTO objeto DTO que contiene la información necesaria para crear el contacto.
	 * @return un {@link ResponseEntity} con estado HTTP 201 (CREATED) si el contacto se crea con éxito.
	 */
	@PostMapping("/crearContacto")
	public ResponseEntity<?> crearContacto(@RequestBody ContactoDTO contactoDTO) {
		contactoService.crearContacto(contactoDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	/**
	 * Elimina un contacto existente de un usuario.
	 * 
	 * @param contactoDTO objeto DTO con los datos que identifican el contacto a eliminar.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si la eliminación se completa correctamente.
	 */
	@PostMapping("/eliminarContacto")
	public ResponseEntity<?> eliminarContacto(@RequestBody ContactoDTO contactoDTO) {
		contactoService.eliminarContacto(contactoDTO);	
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Obtiene la lista completa de contactos asociados a un usuario específico.
	 * 
	 * @param idUsuario el identificador único del usuario del cual se desea consultar la lista de contactos.
	 * @return un {@link ResponseEntity} que contiene una lista de objetos {@link ContactoRespuestaDTO} con la información de los contactos.
	 */
	@GetMapping("/listaContactos/{idUsuario}")
	public ResponseEntity<List<ContactoRespuestaDTO>> listarContactosPorUsuario(
	        @PathVariable Long idUsuario) {

	    return ResponseEntity.ok(contactoService.listarContactosPorUsuario(idUsuario));
	}
	
	/**
	 * Busca contactos de un usuario filtrando por nombre o número de teléfono.
	 * 
	 * @param idUsuario el identificador único del usuario que realiza la búsqueda.
	 * @param texto el texto o término de búsqueda (nombre o teléfono) a consultar.
	 * @return un {@link ResponseEntity} con la lista de contactos que coinciden con el criterio de búsqueda.
	 */
	@GetMapping("/buscarPorNombreOTelefono/{idUsuario}")
	public ResponseEntity<List<ContactoRespuestaDTO>> buscarContactosPorNombreOTelefono(@PathVariable Long idUsuario, @RequestParam String texto) {

	    return ResponseEntity.ok(
	        contactoService.buscarContactosPorNombreOTelefono(idUsuario, texto)
	    );
	}
	
	/**
	 * Actualiza el nombre personalizado con el que un usuario ha guardado a un contacto en su agenda.
	 * 
	 * @param idUsuario el identificador único del usuario propietario de la agenda.
	 * @param idUsuarioContacto el identificador único del contacto al cual se le actualizará el nombre.
	 * @param nombre el nuevo nombre que se asignará al contacto.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si la actualización se efectúa correctamente.
	 */
	@PutMapping("/actualizarNombre/{idUsuario}/{idUsuarioContacto}")
	public ResponseEntity<?> actualizarNombre(@PathVariable Long idUsuario,@PathVariable Long idUsuarioContacto, @RequestParam String nombre) {

	    contactoService.actualizarNombreContacto(idUsuario, idUsuarioContacto, nombre);

	    return ResponseEntity.ok().build();
	}
}
