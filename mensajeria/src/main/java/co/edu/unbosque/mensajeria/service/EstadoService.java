package co.edu.unbosque.mensajeria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.EstadoDTO;
import co.edu.unbosque.mensajeria.entity.Estado;
import co.edu.unbosque.mensajeria.repository.EstadoRepository;
import jakarta.transaction.Transactional;

@Service
public class EstadoService {

	private final EstadoRepository estadoRepository;

	public EstadoService(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}

	@Transactional
	public void crearEstado(EstadoDTO estadoDTO) {
		estadoRepository.crearEstado(estadoDTO.getIdUsuario(), estadoDTO.getTipo(), estadoDTO.getContenidoUrl(),
				estadoDTO.getDescripcion());
	}

	@Transactional
	public void eliminarEstado(Long idEstado) {
		estadoRepository.eliminarEstado(idEstado);
	}

	@Transactional
	public List<Estado> listarEstadosPorContactos(Long idUsuario) {
		return estadoRepository.listarEstadosPorContactos(idUsuario);
	}
}