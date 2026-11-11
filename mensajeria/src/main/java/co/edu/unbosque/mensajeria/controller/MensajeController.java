package co.edu.unbosque.mensajeria.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import co.edu.unbosque.mensajeria.document.Mensaje;
import co.edu.unbosque.mensajeria.dto.MensajeDTO;
import co.edu.unbosque.mensajeria.service.MensajeService;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

	private final MensajeService mensajeService;
	private static final Map<String, String> EXTENSIONES = Map.of(
		    "image/jpeg", ".jpg",
		    "image/png", ".png",
		    "image/gif", ".gif",
		    "image/webp", ".webp"
		);
	private static final Set<String> EXTENSIONES_ARCHIVO =
		    Set.of("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "csv", "zip");
	
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
		case 11:
		    return ResponseEntity.badRequest().body("Falta el archivo");
		case 12:
		    return ResponseEntity.badRequest().body("Tipo de mensaje no válido");
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
	
	

	@PostMapping("/subir-imagen")
	public ResponseEntity<?> subirImagen(@RequestParam("archivo") MultipartFile archivo) throws IOException {
	    if (archivo.isEmpty()) {
	        return ResponseEntity.badRequest().body("El archivo está vacío");
	    }

	    String extension = EXTENSIONES.get(archivo.getContentType());
	    if (extension == null) {
	        return ResponseEntity.badRequest().body("Solo se permiten imágenes JPG, PNG, GIF o WEBP");
	    }

	    String nombreGuardado = UUID.randomUUID() + extension;

	    Path carpeta = Paths.get(System.getProperty("user.dir"), "uploads", "mensajes");
	    Files.createDirectories(carpeta);
	    Files.copy(archivo.getInputStream(), carpeta.resolve(nombreGuardado));

	    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
	            .path("/uploads/mensajes/").path(nombreGuardado).toUriString();

	    return ResponseEntity.ok(Map.of(
	        "url", url,
	        "nombreArchivo", archivo.getOriginalFilename() == null ? nombreGuardado : archivo.getOriginalFilename(),
	        "tipo", "IMAGEN"
	    ));
	}
	
	@PostMapping("/subir-archivo")
	public ResponseEntity<?> subirArchivo(@RequestParam("archivo") MultipartFile archivo) throws IOException {
	    if (archivo.isEmpty()) {
	        return ResponseEntity.badRequest().body("El archivo está vacío");
	    }

	    String original = archivo.getOriginalFilename() == null
	            ? "archivo" : archivo.getOriginalFilename().replaceAll("[\\\\/]", "_");

	    String tipo;
	    String extension;

	    String extensionImagen = EXTENSIONES.get(archivo.getContentType());
	    if (extensionImagen != null) {
	        tipo = "IMAGEN";
	        extension = extensionImagen;
	    } else {
	        int punto = original.lastIndexOf('.');
	        String ext = punto >= 0 ? original.substring(punto + 1).toLowerCase() : "";
	        if (!EXTENSIONES_ARCHIVO.contains(ext)) {
	            return ResponseEntity.badRequest().body("Tipo de archivo no permitido");
	        }
	        tipo = "ARCHIVO";
	        extension = "." + ext;
	    }

	    String nombreGuardado = UUID.randomUUID() + extension;
	    Path carpeta = Paths.get(System.getProperty("user.dir"), "uploads", "mensajes");
	    Files.createDirectories(carpeta);
	    Files.copy(archivo.getInputStream(), carpeta.resolve(nombreGuardado));

	    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
	            .path("/uploads/mensajes/").path(nombreGuardado).toUriString();

	    return ResponseEntity.ok(Map.of(
	        "url", url,
	        "nombreArchivo", original,
	        "tipo", tipo
	    ));
	}
	
	@GetMapping("/no-leidos/{idUsuario}")
	public Map<Long, Long> noLeidos(@PathVariable Long idUsuario) {
	    return mensajeService.noLeidos(idUsuario);
	}
	
	@DeleteMapping("/eliminar/{idMensaje}/{idUsuario}")
	public ResponseEntity<?> eliminar(@PathVariable String idMensaje, @PathVariable Long idUsuario) {
	    if (mensajeService.eliminarMensaje(idMensaje, idUsuario)) {
	        return ResponseEntity.ok("Mensaje eliminado");
	    }
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El mensaje no existe o no es tuyo");
	}
}
