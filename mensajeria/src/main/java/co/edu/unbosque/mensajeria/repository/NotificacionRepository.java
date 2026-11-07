package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.entity.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{

	@Query(value = "SELECT * FROM FN_LISTAR_NOTIFICACIONES_USUARIO(:idUsuario)", nativeQuery = true)
	List<Object[]> listarNotificaciones(@Param("idUsuario") Long idUsuario);
}
