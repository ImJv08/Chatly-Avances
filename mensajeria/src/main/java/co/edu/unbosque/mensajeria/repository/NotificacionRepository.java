package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.entity.Notificacion;

/**
 * Repositorio JPA para la entidad {@link Notificacion}, que gestiona la persistencia
 * de las notificaciones del sistema y ejecuta procedimientos almacenados y funciones PL/pgSQL nativas
 * para el control de lectura y conteo.
 */
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{

	/**
	 * Lista todas las notificaciones dirigidas a un usuario específico 
	 * mediante una función PL/pgSQL nativa que retorna arreglos de objetos.
	 * 
	 * @param idUsuario identificador del usuario receptor de las notificaciones.
	 * @return una lista de arreglos de objetos con los detalles de las notificaciones.
	 */
	@Query(value = "SELECT * FROM FN_LISTAR_NOTIFICACIONES_USUARIO(:idUsuario)", nativeQuery = true)
	public List<Object[]> listarNotificaciones(@Param("idUsuario") Long idUsuario);
	
	/**
	 * Ejecuta el procedimiento almacenado para marcar una notificación específica como leída.
	 * 
	 * @param id identificador único de la notificación que se actualizará.
	 */
	@Procedure(procedureName = "SP_MARCAR_NOTIFICACION_LEIDA")
	public void marcarLeida(@Param("P_ID") Long id);
	
	/**
	 * Cuenta la cantidad total de notificaciones no leídas de un usuario 
	 * mediante una función PL/pgSQL nativa.
	 * 
	 * @param idUsuario identificador del usuario consultado.
	 * @return un valor numérico con el total de notificaciones pendientes de lectura.
	 */
	@Query(value = "SELECT public.fn_contar_notificaciones_no_leidas(:idUsuario)", nativeQuery = true)
	public Number contarNoLeidas(@Param("idUsuario") Long idUsuario);
	
	/**
	 * Ejecuta el procedimiento almacenado para marcar todas las notificaciones de un usuario como leídas.
	 * 
	 * @param idUsuario identificador del usuario cuyas notificaciones se actualizarán.
	 */
	@Procedure(procedureName = "SP_MARCAR_TODAS_LEIDAS")
	void marcarTodasLeidas(@Param("P_ID_USUARIO") Long idUsuario);

}
