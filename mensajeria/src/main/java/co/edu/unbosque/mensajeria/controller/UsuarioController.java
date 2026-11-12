package co.edu.unbosque.mensajeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.mensajeria.dto.UsuarioDTO;
import co.edu.unbosque.mensajeria.service.UsuarioService;
import org.springframework.http.MediaType;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los usuarios del sistema,
 * tales como eliminación de cuentas, actualización de datos con validaciones de archivos e imágenes de perfil,
 * y consulta de información por número telefónico. Expone endpoints bajo el prefijo {@code /usuarios}.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
  
	/**
	 * Servicio de lógica de negocio encargado de procesar la persistencia, validaciones
	 * y reglas de negocio asociadas a los usuarios.
	 */
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Constructor para la inyección de dependencias del servicio de usuarios.
	 * 
	 * @param usuarioService el servicio de usuarios que será inyectado por Spring.
	 */
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	/**
	 * Elimina un usuario del sistema de forma permanente a partir de su identificador único.
	 * 
	 * @param idUsuario el identificador único del usuario que se desea eliminar.
	 * @return un {@link ResponseEntity} con estado HTTP 200 (OK) si la eliminación se efectúa correctamente.
	 */
	@DeleteMapping("{idUsuario}/eliminarUsuario")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long idUsuario) {
		usuarioService.eliminarUsuario(idUsuario);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Actualiza los datos de un usuario existente, permitiendo además actualizar su foto de perfil opcionalmente
	 * y validando restricciones de formato, tamaño de imagen, número telefónico duplicado o contraseñas.
	 * 
	 * @param dto objeto DTO que contiene la información actualizada del usuario.
	 * @param foto archivo de imagen opcional para actualizar la foto de perfil.
	 * @return un {@link ResponseEntity} con el estado HTTP y mensaje correspondiente según el código devuelto por el servicio.
	 */
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
	
	/**
	 * Busca y obtiene la información de un usuario a partir de su número telefónico.
	 * 
	 * @param numeroTelefonico el número telefónico del usuario a consultar.
	 * @return un {@link ResponseEntity} que contiene el objeto {@link UsuarioDTO} si es encontrado, o un estado HTTP 404 (NOT FOUND) en caso contrario.
	 */
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
