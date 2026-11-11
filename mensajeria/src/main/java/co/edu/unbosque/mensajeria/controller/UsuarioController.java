package co.edu.unbosque.mensajeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.mensajeria.dto.UsuarioDTO;
import co.edu.unbosque.mensajeria.entity.Usuario;
import co.edu.unbosque.mensajeria.service.UsuarioService;
import org.springframework.http.MediaType;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
  
	@Autowired
	private UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@DeleteMapping("{idUsuario}/eliminarUsuario")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long idUsuario) {
		usuarioService.eliminarUsuario(idUsuario);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value = "/actualizarUsuario", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> actualizarUsuario(@RequestPart("usuario") UsuarioDTO dto,
			@RequestPart(value = "foto", required = false) MultipartFile foto) {
		switch (usuarioService.actualizarUsuario(dto, foto)) {
		case 0:
			return ResponseEntity.ok("Usuario actualizado");

		case 1:
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario con ese número telefónico");

		case 2:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos incompletos");

		case 4:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("La contraseña debe tener mínimo 7 caracteres, una mayúscula y un carácter especial");

		case 5:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La imagen debe ser un archivo de imagen de máximo 5 MB");

		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");

		}
	}
	
	
	@GetMapping("/obtener/{numeroTelefonico}")
	public ResponseEntity<UsuarioDTO> obtenerUsuarioPorNumeroTelefonico(
			@PathVariable String numeroTelefonico){
		UsuarioDTO usuario = usuarioService.getByNumeroTelefonico(numeroTelefonico);
		if(usuario != null) {
			return ResponseEntity.ok(usuario);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	
	
	
}
