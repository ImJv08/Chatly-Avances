package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.entity.Bloqueo;

/**
 * Repositorio JPA para la entidad {@link Bloqueo}, que provee operaciones de persistencia
 * y consultas personalizadas mediante procedimientos almacenados y funciones PL/pgSQL sobre los bloqueos de usuarios.
 */
@Repository
public interface BloqueoRepository extends JpaRepository<Bloqueo, Long> {

	/**
	 * Ejecuta el procedimiento almacenado para bloquear a un usuario en el sistema.
	 * 
	 * @param idUsuario identificador del usuario que realiza el bloqueo.
	 * @param idUsuarioBloqueado identificador del usuario que va a ser bloqueado.
	 */
	@Procedure(procedureName = "SP_BLOQUEAR_USUARIO")
	public void bloquearUsuario(@Param("P_ID_USUARIO") Long idUsuario,
			@Param("P_ID_USUARIO_BLOQUEADO") Long idUsuarioBloqueado);

	/**
	 * Ejecuta el procedimiento almacenado para desbloquear a un usuario previamente bloqueado.
	 * 
	 * @param idUsuario identificador del usuario que realiza el desbloqueo.
	 * @param idUsuarioBloqueado identificador del usuario que va a ser desbloqueado.
	 */
	@Procedure(procedureName = "SP_DESBLOQUEAR_USUARIO")
	public void desbloquearUsuario(@Param("P_ID_USUARIO") Long idUsuario,
			@Param("P_ID_USUARIO_BLOQUEADO") Long idUsuarioBloqueado);

	/**
	 * Lista todos los bloqueos realizados por un usuario específico invocando una función PL/pgSQL nativa.
	 * 
	 * @param idUsuario identificador del usuario propietario de los bloqueos.
	 * @return una lista de entidades {@link Bloqueo} con los registros de usuarios bloqueados.
	 */
	@Query(value = "SELECT * FROM FN_LISTAR_BLOQUEOS_USUARIO(:idUsuario)", nativeQuery = true)
	public List<Bloqueo> listarBloqueosPorUsuario(@Param("idUsuario") Long idUsuario);
	
	/**
	 * Lista los registros de bloqueo donde un usuario específico ha sido bloqueado por otros,
	 * invocando una función PL/pgSQL nativa.
	 * 
	 * @param idUsuario identificador del usuario a consultar.
	 * @return una lista de entidades {@link Bloqueo} con los usuarios que lo han bloqueado.
	 */
	@Query(value = "SELECT * FROM FN_LISTAR_QUIEN_ME_BLOQUEO(:idUsuario)", nativeQuery = true)
	public List<Bloqueo> listarQuienMeBloqueo(@Param("idUsuario") Long idUsuario);
	
	/**
	 * Verifica mediante una función PL/pgSQL nativa si un usuario se encuentra bloqueado por otro.
	 * 
	 * @param idBloqueador identificador del usuario que podría haber realizado el bloqueo.
	 * @param idBloqueado identificador del usuario que podría estar bloqueado.
	 * @return {@code true} si existe el bloqueo, {@code false} en caso contrario.
	 */
	@Query(value = "SELECT * FROM FN_ESTA_BLOQUEADO(:idBloqueador, :idBloqueado)", nativeQuery = true)
	public boolean estaBloqueado(@Param("idBloqueador") Long idBloqueador,
	        @Param("idBloqueado") Long idBloqueado);
}