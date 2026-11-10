package co.edu.unbosque.mensajeria.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.mensajeria.dto.GrupoDTO;
import co.edu.unbosque.mensajeria.dto.MiembroGrupoDTO;
import co.edu.unbosque.mensajeria.repository.BloqueoRepository;
import co.edu.unbosque.mensajeria.repository.GrupoRepository;
import jakarta.transaction.Transactional;
import co.edu.unbosque.mensajeria.entity.Grupo;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

@Service
public class GrupoService {

	private final GrupoRepository grupoRepository;
	private final BloqueoRepository bloqueoRepository;
	
	@Value("${app.base-url}")
	private String baseUrl;

	public GrupoService(GrupoRepository grupoRepository, BloqueoRepository bloqueoRepository) {
		this.grupoRepository = grupoRepository;
		this.bloqueoRepository = bloqueoRepository;
	}

	@Transactional
	public int crearGrupo(GrupoDTO dto, MultipartFile foto) {
		if (dto.getNombreGrupo() == null || dto.getNombreGrupo().trim().isEmpty()) {
			return 2;
		}
		if (foto != null && !foto.isEmpty()) {
			String tipo = foto.getContentType();
			if (tipo == null || !tipo.startsWith("image/") || foto.getSize() > 5 * 1024 * 1024) {
				return 5;
			}
			dto.setFotoPerfilUrl(guardarFoto(foto));
		}
		grupoRepository.crearGrupo(dto.getIdCreador(), dto.getNombreGrupo().trim(), dto.getFotoPerfilUrl());
		return 0;
	}

	private String guardarFoto(MultipartFile foto) {
		try {
			String ext = foto.getContentType().substring(foto.getContentType().indexOf('/') + 1);
			String nombre = UUID.randomUUID() + "." + ext;
			Path dir = Paths.get(System.getProperty("user.dir"), "uploads", "grupos");
			Files.createDirectories(dir);
			Files.copy(foto.getInputStream(), dir.resolve(nombre), StandardCopyOption.REPLACE_EXISTING);
			return baseUrl + "/uploads/grupos/" + nombre;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Transactional
	public boolean agregarMiembro(MiembroGrupoDTO grupoMiembroDTO, Long idOrigen) {

		if (bloqueoRepository.estaBloqueado(grupoMiembroDTO.getUsuario(), idOrigen)) {
			return false;
		}

		grupoRepository.agregarMiembro(grupoMiembroDTO.getGrupo(), grupoMiembroDTO.getUsuario(), idOrigen);
		return true;
	}
	
	
	@Transactional
	public void eliminarGrupo( Long idGrupo, Long idCreador) {
		grupoRepository.eliminarGrupo(idGrupo, idCreador);
	}
	
	
	@Transactional
	public int actualizarGrupo( GrupoDTO grupoDTO, Long idCreador, MultipartFile foto) {
		if(grupoDTO.getNombreGrupo() == null || grupoDTO.getNombreGrupo().trim().isEmpty()) {
			return 2;
		}
		
		if(foto != null && !foto.isEmpty()) {
			if(foto.getContentType() == null || !foto.getContentType().startsWith("image/")
					|| foto.getSize() > 5 * 1024 * 1024){
				return 5;
			}
			grupoDTO.setFotoPerfilUrl(guardarFoto(foto));
		}
		grupoRepository.actualizarGrupo(grupoDTO.getId(),idCreador, grupoDTO.getNombreGrupo(), 
				grupoDTO.getFotoPerfilUrl());
		return 0;
	}
	
	@Transactional
	public void salirGrupo( MiembroGrupoDTO grupoMiembroDTO) {
		grupoRepository.salirGrupo(grupoMiembroDTO.getGrupo(), grupoMiembroDTO.getUsuario());
	}
	
	@Transactional
	public void eliminarMiembroGrupo( MiembroGrupoDTO grupoMiembroDTO, Long idOrigen) {
		grupoRepository.elimiarMiembroGrupo(grupoMiembroDTO.getGrupo(), grupoMiembroDTO.getUsuario(), idOrigen);
	}
	
	@Transactional
	public List<Grupo> listarGruposPorUsuario(Long idUsuario) {
		return grupoRepository.listarGruposPorUsuario(idUsuario);
	}
	
	public List<Map<String, Object>> listarGruposConRol(Long idUsuario) {
	    List<Object[]> resultados =
	            grupoRepository.listarGruposConRolPorUsuario(idUsuario);

	    List<Map<String, Object>> grupos = new ArrayList<>();

	    for (Object[] fila : resultados) {
	        Map<String, Object> grupo = new HashMap<>();

	        grupo.put("id", fila[0]);
	        grupo.put("idCreador", fila[1]);
	        grupo.put("nombre", fila[2]);
	        grupo.put("fotoPerfilUrl", fila[3]);
	        grupo.put("fechaRegistro", fila[4]);
	        grupo.put("rol", fila[5]);

	        grupos.add(grupo);
	    }

	    return grupos;
	}
	
	@Transactional
	public void cambiarRolAdmin(Long idGrupo, Long idUsuario, Long idOrigen) {
	    grupoRepository.cambiarRolAdmin(idGrupo, idUsuario, idOrigen);
	}
	
	public List<Map<String, Object>> listarMiembrosGrupo(Long idGrupo) {

	    List<Object[]> resultados = grupoRepository.listarMiembrosGrupo(idGrupo);

	    List<Map<String, Object>> miembros = new ArrayList<>();

	    for (Object[] fila : resultados) {
	        Map<String, Object> miembro = new HashMap<>();

	        miembro.put("idUsuario", fila[0]);
	        miembro.put("nombreCompleto", fila[1] + " " + fila[2]);
	        miembro.put("numeroTelefonico", fila[3]);
	        miembro.put("fotoPerfilUrl", fila[4]);
	        miembro.put("rol", fila[5]);

	        miembros.add(miembro);
	    }

	    return miembros;
	}

}
