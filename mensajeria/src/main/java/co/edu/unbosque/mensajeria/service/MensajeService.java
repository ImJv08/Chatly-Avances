package co.edu.unbosque.mensajeria.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.document.Mensaje;
import co.edu.unbosque.mensajeria.dto.ConteoNoLeidosDTO;
import co.edu.unbosque.mensajeria.dto.MensajeDTO;
import co.edu.unbosque.mensajeria.repository.GrupoRepository;
import co.edu.unbosque.mensajeria.repository.MensajeRepository;
import co.edu.unbosque.mensajeria.repository.UsuarioRepository;

@Service
public class MensajeService {
	
	private final MensajeRepository mensajeRepo;
	private final UsuarioRepository usuarioRepo;
	private final GrupoRepository grupoRepo;
	private final ContactoService contactoService;
	
	public MensajeService(MensajeRepository mensajeRepo, UsuarioRepository usuarioRepo, 
			GrupoRepository grupoRepo,ContactoService contactoService) {
		super();
		this.mensajeRepo = mensajeRepo;
		this.usuarioRepo = usuarioRepo;
		this.grupoRepo = grupoRepo;
		this.contactoService = contactoService;
	}
	

	
	public int enviarMensaje(MensajeDTO dto) {
		

		if (dto.getIdEmisor() == null || !usuarioRepo.existsById(dto.getIdEmisor())) {
			return 3;
		}
		String tipo = dto.getTipo() == null ? "TEXTO" : dto.getTipo().toUpperCase();

		if (!List.of("TEXTO", "IMAGEN", "ARCHIVO").contains(tipo)) return 12;

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
	
	public List<Mensaje> conversacion(Long idUsuario, Long idContacto) {
		return mensajeRepo.conversacion(idUsuario, idContacto);
	}

	public List<Mensaje> deGrupo(Long idGrupo) {
		return mensajeRepo.findByIdGrupoOrderByFechaAsc(idGrupo);
	}

	public long marcarLeidos(Long idUsuario, Long idContacto) {
		return mensajeRepo.marcarLeidos(idUsuario, idContacto);
	}
	
	private boolean esMiembro(Long idGrupo, Long idUsuario) {
		return grupoRepo.esMiembro(idGrupo, idUsuario);
	}
	
	public Map<Long, Long> noLeidos(Long idUsuario) {
	    Map<Long, Long> resultado = new HashMap<>();
	    for (ConteoNoLeidosDTO c : mensajeRepo.contarNoLeidos(idUsuario)) {
	        resultado.put(c.getId(), c.getTotal());
	    }
	    return resultado;
	}
	
	public boolean eliminarMensaje(String idMensaje, Long idUsuario) {
	    return mensajeRepo.eliminarMensaje(idMensaje, idUsuario) > 0;
	}
	
}
