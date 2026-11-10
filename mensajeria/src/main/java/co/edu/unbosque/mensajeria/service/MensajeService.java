package co.edu.unbosque.mensajeria.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.document.Mensaje;
import co.edu.unbosque.mensajeria.dto.MensajeDTO;
import co.edu.unbosque.mensajeria.repository.GrupoRepository;
import co.edu.unbosque.mensajeria.repository.MensajeRepository;
import co.edu.unbosque.mensajeria.repository.UsuarioRepository;

@Service
public class MensajeService {
	
	private final MensajeRepository mensajeRepo;
	private final UsuarioRepository usuarioRepo;
	private final GrupoRepository grupoRepo;
	
	public MensajeService(MensajeRepository mensajeRepo, UsuarioRepository usuarioRepo, 
			GrupoRepository grupoRepo) {
		super();
		this.mensajeRepo = mensajeRepo;
		this.usuarioRepo = usuarioRepo;
		this.grupoRepo = grupoRepo;
	}
	

	
	public int enviarMensaje(MensajeDTO dto) {
		

		if (dto.getIdEmisor() == null || !usuarioRepo.existsById(dto.getIdEmisor())) {
			return 3;
		}
		if (dto.getTexto() == null || dto.getTexto().trim().isEmpty()) {
			return 2;
		}
		if (dto.getTexto().trim().length() > 2000) {
			return 7;
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
		m.setTipo("TEXTO");
		m.setTexto(dto.getTexto().trim());
		m.setFecha(new Date());
		m.setLeido(false);
		mensajeRepo.insert(m);
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
	
	
}
