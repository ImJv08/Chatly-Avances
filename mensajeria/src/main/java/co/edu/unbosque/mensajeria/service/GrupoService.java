package co.edu.unbosque.mensajeria.service;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.GrupoDTO;
import co.edu.unbosque.mensajeria.dto.MiembroGrupoDTO;
import co.edu.unbosque.mensajeria.repository.GrupoRepository;
import jakarta.transaction.Transactional;
import co.edu.unbosque.mensajeria.entity.Grupo;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@Service
public class GrupoService {

	private final GrupoRepository grupoRepository;

	public GrupoService(GrupoRepository grupoRepository) {
		this.grupoRepository = grupoRepository;
	}

	@Transactional
	public void crearGrupo(GrupoDTO grupoDTO) {

		grupoRepository.crearGrupo(grupoDTO.getIdCreador(), grupoDTO.getNombreGrupo(), grupoDTO.getFotoPerfilUrl());
	}

	@Transactional
	public void agregarMiembro(MiembroGrupoDTO grupoMiembroDTO, Long idOrigen) {
		grupoRepository.agregarMiembro(grupoMiembroDTO.getGrupo(), grupoMiembroDTO.getUsuario(), idOrigen);
	}
	
	@Transactional
	public void eliminarGrupo( Long idGrupo, Long idCreador) {
		grupoRepository.eliminarGrupo(idGrupo, idCreador);
	}
	
	
	@Transactional
	public void actualizarGrupo( GrupoDTO grupoDTO, Long idCreador) {
		grupoRepository.actualizarGrupo(grupoDTO.getId(),idCreador, grupoDTO.getNombreGrupo(), grupoDTO.getFotoPerfilUrl()  );
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

}
