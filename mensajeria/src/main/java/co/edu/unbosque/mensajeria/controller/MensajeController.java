package co.edu.unbosque.mensajeria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.mensajeria.document.Mensaje;
import co.edu.unbosque.mensajeria.dto.MensajeDTO;
import co.edu.unbosque.mensajeria.service.MensajeService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

	private final MensajeService mensajeService;
	
	public MensajeController(MensajeService mensajeService) {
		this.mensajeService = mensajeService;
	}
	
	@PostMapping("/enviar")
	public ResponseEntity<?> enviar(@RequestBody MensajeDTO dto) {
		try {
		switch (mensajeService.enviarMensaje(dto)) {
		case 0: 
			return ResponseEntity.status(HttpStatus.CREATED).body("Mensaje enviado");
		case 2: 
			return ResponseEntity.badRequest().body("El mensaje está vacío");
		case 3: 
			return ResponseEntity.badRequest().body("El emisor no existe");
		case 4: 
			return ResponseEntity.badRequest().body("El destinatario no existe");
		case 5: 
			return ResponseEntity.badRequest().body("Indica un destinatario o un grupo");
		case 6:  
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No perteneces a este grupo");
		case 7:  
			return ResponseEntity.badRequest().body("El mensaje supera los 2000 caracteres");
		case 8:  
			return ResponseEntity.badRequest().body("No puedes enviarte mensajes a ti mismo");
		case 9:  
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes escribir a este usuario");
		case 10: 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El grupo no existe");
		default: 
			return ResponseEntity.internalServerError().build();
		}
		
		} catch (Exception e) {
		    e.printStackTrace();
		    return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@GetMapping("/conversacion/{idUsuario}/{idContacto}")
	public List<Mensaje> conversacion(@PathVariable Long idUsuario, @PathVariable Long idContacto) {
		return mensajeService.conversacion(idUsuario, idContacto);
	}

	@GetMapping("/grupo/{idGrupo}")
	public List<Mensaje> grupo(@PathVariable Long idGrupo) {
		return mensajeService.deGrupo(idGrupo);
	}

	@PutMapping("/leer/{idUsuario}/{idContacto}")
	public ResponseEntity<?> leer(@PathVariable Long idUsuario, @PathVariable Long idContacto) {
		return ResponseEntity.ok(mensajeService.marcarLeidos(idUsuario, idContacto));
	}
	
	
}
