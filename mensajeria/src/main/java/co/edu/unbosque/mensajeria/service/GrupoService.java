package co.edu.unbosque.mensajeria.service;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.GrupoDTO;
import co.edu.unbosque.mensajeria.dto.MiembroGrupoDTO;
import co.edu.unbosque.mensajeria.repository.GrupoRepository;
import jakarta.transaction.Transactional;
import co.edu.unbosque.mensajeria.entity.Grupo;
import java.util.List;

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

}
