package co.edu.unbosque.mensajeria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import co.edu.unbosque.mensajeria.entity.Contacto;
import co.edu.unbosque.mensajeria.service.ContactoService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/contactos")
public class ContactoContoller {
    
	private final ContactoService contactoService;

	public ContactoContoller(ContactoService contactoService) {
		this.contactoService = contactoService;
	}
	
	@PostMapping("/crearContacto")
	public ResponseEntity<?> crearContacto(@RequestBody ContactoDTO contactoDTO) {
		contactoService.crearContacto(contactoDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/eliminarContacto")
	public ResponseEntity<?> eliminarContacto(@RequestBody ContactoDTO contactoDTO) {
		contactoService.eliminarContacto(contactoDTO);	
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/listaContactos/{idUsuario}")
	public ResponseEntity<List<ContactoRespuestaDTO>> listarContactosPorUsuario(
	        @PathVariable Long idUsuario) {

	    return ResponseEntity.ok(contactoService.listarContactosPorUsuario(idUsuario));
	}
	
	@PutMapping("/actualizarNombre/{idUsuario}/{idUsuarioContacto}")
	public ResponseEntity<?> actualizarNombre(@PathVariable Long idUsuario,@PathVariable Long idUsuarioContacto, @RequestParam String nombre) {

	    contactoService.actualizarNombreContacto(idUsuario, idUsuarioContacto, nombre);

	    return ResponseEntity.ok().build();
	}
}
