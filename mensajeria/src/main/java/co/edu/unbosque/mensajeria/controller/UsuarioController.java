package co.edu.unbosque.mensajeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.mensajeria.dto.UsuarioDTO;
import co.edu.unbosque.mensajeria.service.UsuarioService;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
  
	@Autowired
	private UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@PostMapping("/crearUsuario")
	public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		
		usuarioService.crearUsuario(usuarioDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("{idUsuario}/eliminarUsuario")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long idUsuario) {
		usuarioService.eliminarUsuario(idUsuario);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/actualizarUsuario")
	public ResponseEntity<?> actualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		usuarioService.actualizarUsuario(usuarioDTO);
		return ResponseEntity.ok().build();
	}
	
	
	
	
	
	
}
