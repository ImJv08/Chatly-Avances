package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.entity.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{

	@Query(value = "SELECT * FROM FN_LISTAR_NOTIFICACIONES_USUARIO(:idUsuario)", nativeQuery = true)
	List<Object[]> listarNotificaciones(@Param("idUsuario") Long idUsuario);
	
	@Procedure(procedureName = "SP_MARCAR_NOTIFICACION_LEIDA")
	void marcarLeida(@Param("P_ID") Long id);
	
	@Query(value = "SELECT public.fn_contar_notificaciones_no_leidas(:idUsuario)", nativeQuery = true)
	Number contarNoLeidas(@Param("idUsuario") Long idUsuario);
	
	@Procedure(procedureName = "SP_MARCAR_TODAS_LEIDAS")
	void marcarTodasLeidas(@Param("P_ID_USUARIO") Long idUsuario);

}
