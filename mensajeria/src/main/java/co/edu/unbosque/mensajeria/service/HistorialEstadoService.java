package co.edu.unbosque.mensajeria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.entity.HistorialEstado;
import co.edu.unbosque.mensajeria.repository.HistorialEstadoRepository;

/**
 * Consulta el historial de estados publicados por un usuario. Solo lee: el
 * historial se llena automáticamente al publicar o eliminar un estado.
 */
@Service
public class HistorialEstadoService {

	/** Repositorio del historial de estados. */
	private final HistorialEstadoRepository historialRepo;

	/** Repositorio del historial de estados. */
	public HistorialEstadoService(HistorialEstadoRepository historialRepo) {
		this.historialRepo = historialRepo;
	}
	
	/**
	 * Crea el servicio.
	 *
	 * @param historialRepo repositorio del historial de estados
	 */
	public List<HistorialEstado> listar(Long idUsuario) {
		return historialRepo.listarHistorial(idUsuario);
	}
}
