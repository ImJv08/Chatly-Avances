package co.edu.unbosque.mensajeria.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.document.Mensaje;
import co.edu.unbosque.mensajeria.dto.ConteoNoLeidosDTO;
import co.edu.unbosque.mensajeria.dto.MensajeDTO;
import co.edu.unbosque.mensajeria.repository.GrupoRepository;
import co.edu.unbosque.mensajeria.repository.MensajeRepository;
import co.edu.unbosque.mensajeria.repository.UsuarioRepository;

/**
 * Servicio de lógica de negocio para la gestión de mensajes de chat (tanto privados como grupales),
 * encargado de procesar validaciones de envío, tipos de contenido, restricciones de bloqueo,
 * edición, marcado de lectura y consultas en la base de datos NoSQL de MongoDB.
 */
@Service
public class MensajeService {
	
	/**
	 * Repositorio para la persistencia y consultas de mensajes.
	 */
	private final MensajeRepository mensajeRepo;
	
	/**
	 * Repositorio para validaciones de existencia y bloqueos de usuarios.
	 */
	private final UsuarioRepository usuarioRepo;
	
	/**
	 * Repositorio para validaciones de existencia y membresía en grupos.
	 */
	private final GrupoRepository grupoRepo;
	
	/**
	 * Servicio de contactos para la gestión automática de ellos.
	 */
	private final ContactoService contactoService;
	
	/**
	 * Constructor para la inyección de dependencias de los repositorios y servicios asociados.
	 * 
	 * @param mensajeRepo repositorio de mensajes en MongoDB.
	 * @param usuarioRepo repositorio de usuarios.
	 * @param grupoRepo repositorio de grupos.
	 * @param contactoService servicio de contactos.
	 */
	public MensajeService(MensajeRepository mensajeRepo, UsuarioRepository usuarioRepo, 
			GrupoRepository grupoRepo,ContactoService contactoService) {
		super();
		this.mensajeRepo = mensajeRepo;
		this.usuarioRepo = usuarioRepo;
		this.grupoRepo = grupoRepo;
		this.contactoService = contactoService;
	}
	
	/**
	 * Valida y procesa el envío de un nuevo mensaje, aplicando reglas de negocio sobre tipos de contenido,
	 * existencia de emisores/receptores/grupos, bloqueos activos y registro automático de contactos.
	 * 
	 * @param dto objeto de transferencia con los datos del mensaje a enviar.
	 * @return un código de resultado numérico (0 si es exitoso, o códigos de error específicos del 2 al 12).
	 */
	public int enviarMensaje(MensajeDTO dto) {
		

		if (dto.getIdEmisor() == null || !usuarioRepo.existsById(dto.getIdEmisor())) {
			return 3;
		}
		String tipo = dto.getTipo() == null ? "TEXTO" : dto.getTipo().toUpperCase();

		if (!List.of("TEXTO", "IMAGEN", "ARCHIVO").contains(tipo)) {
			return 12;
		}

		if (tipo.equals("TEXTO")) {
		    if (dto.getTexto() == null || dto.getTexto().isBlank()) 
		    	return 2;
		    if (dto.getTexto().length() > 2000) 
		    	return 7;
		} else if (dto.getContenidoUrl() == null || dto.getContenidoUrl().isBlank()) {
		    return 11;
		
		    
		}
		if ((dto.getIdReceptor() == null) == (dto.getIdGrupo() == null)) {
			return 5;
		}

		if (dto.getIdReceptor() != null) {
			if (dto.getIdReceptor().equals(dto.getIdEmisor())) {
				return 8;
			}
			if (!usuarioRepo.existsById(dto.getIdReceptor())) {
				return 4;
			}
			if (usuarioRepo.hayBloqueo(dto.getIdEmisor(), dto.getIdReceptor())) {
				return 9;
			}
		}

		if (dto.getIdGrupo() != null) {
			if (!grupoRepo.existsById(dto.getIdGrupo())) {
				return 10;
			}
			if (!esMiembro(dto.getIdGrupo(), dto.getIdEmisor())) {
				return 6;
			}
		}

		Mensaje m = new Mensaje();
		m.setIdEmisor(dto.getIdEmisor());
		m.setIdReceptor(dto.getIdReceptor());
		m.setIdGrupo(dto.getIdGrupo());
		m.setTipo(tipo);
		m.setTexto(dto.getTexto());
		m.setContenidoUrl(dto.getContenidoUrl());
		m.setNombreArchivo(dto.getNombreArchivo());
		m.setFecha(new Date());
		m.setLeido(false);
		mensajeRepo.insert(m);
		
		if (dto.getIdGrupo() == null) {
		    try {
		    	contactoService.agregarSiNoExiste(dto.getIdReceptor(), dto.getIdEmisor());
		    } catch (Exception e) {
		        e.printStackTrace();  
		    }
		}

		return 0;
		
	}
	
	/**
	 * Valida y edita el texto de un mensaje existente enviado por el usuario.
	 * 
	 * @param idMensaje identificador único del mensaje a editar.
	 * @param idUsuario identificador del usuario que solicita la edición.
	 * @param nuevoTexto el nuevo contenido de texto del mensaje.
	 * @return un código de resultado numérico (0 si es exitoso, o valores de error 2, 3, 4, 5, 7).
	 */
	public int editarMensaje(String idMensaje, Long idUsuario, String nuevoTexto) {
		if (nuevoTexto == null || nuevoTexto.trim().isEmpty()) return 2;
		if (nuevoTexto.trim().length() > 2000) return 7;

		Mensaje m = mensajeRepo.findById(idMensaje).orElse(null);
		if (m == null) {
			return 3;
		}
		if (!idUsuario.equals(m.getIdEmisor())) {
			return 4;
		}
		if (!"TEXTO".equals(m.getTipo())) {
			return 5;
		}

		m.setTexto(nuevoTexto.trim());
		m.setEditado(true);
		mensajeRepo.save(m);
		return 0;
	}
	
	/**
	 * Obtiene la conversación privada completa entre dos usuarios, ordenada cronológicamente.
	 * 
	 * @param idUsuario identificador del primer usuario.
	 * @param idContacto identificador del segundo usuario.
	 * @return una lista de documentos {@link Mensaje} que conforman la conversación.
	 */
	public List<Mensaje> conversacion(Long idUsuario, Long idContacto) {
		return mensajeRepo.conversacion(idUsuario, idContacto);
	}

	/**
	 * Obtiene todos los mensajes pertenecientes a un grupo específico ordenados por fecha ascendente.
	 * 
	 * @param idGrupo identificador único del grupo de chat.
	 * @return una lista de documentos {@link Mensaje} del grupo.
	 */
	public List<Mensaje> deGrupo(Long idGrupo) {
		return mensajeRepo.findByIdGrupoOrderByFechaAsc(idGrupo);
	}

	/**
	 * Actualiza el estado de lectura de los mensajes enviados por un contacto específico al usuario receptor.
	 * 
	 * @param idUsuario identificador del usuario receptor que marca los mensajes como leídos.
	 * @param idContacto identificador del usuario emisor cuyos mensajes se actualizarán.
	 * @return la cantidad de documentos modificados.
	 */
	public long marcarLeidos(Long idUsuario, Long idContacto) {
		return mensajeRepo.marcarLeidos(idUsuario, idContacto);
	}
	
	/**
	 * Método auxiliar para verificar si un usuario es miembro activo de un grupo.
	 * 
	 * @param idGrupo identificador del grupo.
	 * @param idUsuario identificador del usuario.
	 * @return {@code true} si pertenece al grupo, {@code false} en caso contrario.
	 */
	private boolean esMiembro(Long idGrupo, Long idUsuario) {
		return grupoRepo.esMiembro(idGrupo, idUsuario);
	}
	
	/**
	 * Cuenta y mapea la cantidad de mensajes no leídos por emisor para un usuario receptor específico.
	 * 
	 * @param idUsuario identificador del usuario receptor.
	 * @return un mapa donde la clave es el ID del emisor y el valor es el total de mensajes no leídos.
	 */
	public Map<Long, Long> noLeidos(Long idUsuario) {
	    Map<Long, Long> resultado = new HashMap<>();
	    for (ConteoNoLeidosDTO c : mensajeRepo.contarNoLeidos(idUsuario)) {
	        resultado.put(c.getId(), c.getTotal());
	    }
	    return resultado;
	}
	
	/**
	 * Elimina un mensaje específico de la base de datos asegurando que pertenezca al usuario emisor autorizado.
	 * 
	 * @param idMensaje identificador único del mensaje a eliminar.
	 * @param idUsuario identificador del usuario emisor que solicita la eliminación.
	 * @return {@code true} si se eliminó correctamente, {@code false} en caso contrario.
	 */
	public boolean eliminarMensaje(String idMensaje, Long idUsuario) {
	    return mensajeRepo.eliminarMensaje(idMensaje, idUsuario) > 0;
	}
	
}
