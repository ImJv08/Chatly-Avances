package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.entity.HistorialEstado;

/**
 * Repositorio de solo consulta del historial de estados. Los registros los crea
 * y actualiza la base de datos mediante triggers, no esta aplicación.
 */
@Repository
public interface HistorialEstadoRepository extends JpaRepository<HistorialEstado, Long> {

	/**
	 * Lista el historial de estados de un usuario llamando a la función
	 * {@code fn_listar_historial_estados}.
	 *
	 * @param idUsuario identificador del usuario
	 * @return registros del historial, del más reciente al más antiguo
	 */
	@Query(value = "SELECT * FROM fn_listar_historial_estados(:idUsuario)", nativeQuery = true)
	List<HistorialEstado> listarHistorial(@Param("idUsuario") Long idUsuario);
}
