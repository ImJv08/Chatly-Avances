package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.entity.Grupo;

/**
 * Repositorio JPA para la entidad {@link Grupo}, que gestiona las operaciones de persistencia
 * de los grupos de chat y ejecuta múltiples procedimientos almacenados y funciones PL/pgSQL nativas
 * para la administración de miembros, roles y consultas relacionales.
 */
@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	/**
	 * Ejecuta el procedimiento almacenado para crear y registrar un nuevo grupo en el sistema.
	 * 
	 * @param idCreador identificador del usuario que crea y administra inicialmente el grupo.
	 * @param nombre nombre o título asignado al grupo.
	 * @param urlFoto URL de acceso a la foto de perfil o icono del grupo.
	 */
	@Procedure(procedureName = "SP_CREAR_GRUPO")
	public void crearGrupo(@Param("P_ID_CREADOR") Long idCreador, @Param("P_NOMBRE") String nombre,
			@Param("P_URL_FOTO_PERFIL") String urlFoto);

	/**
	 * Ejecuta el procedimiento almacenado para agregar un nuevo miembro a un grupo existente.
	 * 
	 * @param idGrupo identificador del grupo al cual se añadirá el usuario.
	 * @param idUsuario identificador del usuario que va a ser agregado.
	 * @param idOrigen identificador del usuario origen que realiza la acción de agregar.
	 */
	@Procedure(procedureName = "SP_AGREGAR_MIEMBRO")
	public void agregarMiembro(@Param("P_ID_GRUPO") Long idGrupo, @Param("P_ID_USUARIO") Long idUsuario,
			@Param("P_ID_ORIGEN") Long idOrigen);

	/**
	 * Ejecuta el procedimiento almacenado para eliminar un grupo de forma definitiva.
	 * 
	 * @param idGrupo identificador del grupo que se va a eliminar.
	 * @param idCreador identificador del usuario creador que autoriza la eliminación.
	 */
	@Procedure(procedureName = "SP_ELIMINAR_GRUPO")
	public void eliminarGrupo(@Param("P_ID_GRUPO") Long idGrupo, @Param("P_ID_CREADOR") Long idCreador);

	/**
	 * Ejecuta el procedimiento almacenado para actualizar la información básica de un grupo.
	 * 
	 * @param idGrupo identificador del grupo que se va a actualizar.
	 * @param idOrigen identificador del usuario origen que realiza la modificación.
	 * @param nombre nuevo nombre a asignar al grupo.
	 * @param urlFoto nueva URL de la foto de perfil del grupo.
	 */
	@Procedure(procedureName = "SP_ACTUALIZAR_GRUPO")
	public void actualizarGrupo(@Param("P_ID_GRUPO") Long idGrupo, @Param("P_ID_ORIGEN") Long idOrigen,
			@Param("P_NOMBRE") String nombre, @Param("P_URL_FOTO_PERFIL") String urlFoto);

	/**
	 * Ejecuta el procedimiento almacenado para permitir que un usuario abandone un grupo.
	 * 
	 * @param idGrupo identificador del grupo que se desea abandonar.
	 * @param idUsuario identificador del usuario que sale del grupo.
	 */
	@Procedure(procedureName = "SP_SALIR_GRUPO")
	public void salirGrupo(@Param("P_ID_GRUPO") Long idGrupo, @Param("P_ID_USUARIO") Long idUsuario);

	/**
	 * Ejecuta el procedimiento almacenado para eliminar a un miembro específico de un grupo.
	 * 
	 * @param idGrupo identificador del grupo.
	 * @param idUsuario identificador del usuario que será eliminado del grupo.
	 * @param idOrigen identificador del usuario origen con privilegios que ejecuta la expulsión.
	 */
	@Procedure(procedureName = "SP_ELIMINAR_MIEMBRO_GRUPO")
	public void elimiarMiembroGrupo(@Param("P_ID_GRUPO") Long idGrupo, @Param("P_ID_USUARIO") Long idUsuario,
			@Param("P_ID_ORIGEN") Long idOrigen);

	/**
	 * Lista todos los grupos a los cuales pertenece un usuario específico mediante una función PL/pgSQL nativa.
	 * 
	 * @param idUsuario identificador del usuario miembro.
	 * @return una lista de entidades {@link Grupo} asociadas al usuario.
	 */
	@Query(value = "SELECT * FROM FN_LISTAR_GRUPOS_USUARIO(:idUsuario)", nativeQuery = true)
	public List<Grupo> listarGruposPorUsuario(@Param("idUsuario") Long idUsuario);

	/**
	 * Lista los grupos de un usuario junto con el rol que desempeña en cada uno,
	 * mediante una función PL/pgSQL nativa que retorna arreglos de objetos.
	 * 
	 * @param idUsuario identificador del usuario consultado.
	 * @return una lista de arreglos de objetos con los datos de los grupos y sus roles.
	 */
	@Query(value = "SELECT * FROM fn_listar_grupos_usuario_rol(:idUsuario)", nativeQuery = true)
	List<Object[]> listarGruposConRolPorUsuario(@Param("idUsuario") Long idUsuario);
	
	/**
	 * Lista los miembros pertenecientes a un grupo específico mediante una función PL/pgSQL nativa.
	 * 
	 * @param idGrupo identificador del grupo.
	 * @return una lista de arreglos de objetos con la información de los miembros del grupo.
	 */
	@Query(value = "SELECT * FROM FN_LISTAR_MIEMBROS_GRUPO(:idGrupo)", nativeQuery = true)
	List<Object[]> listarMiembrosGrupo(@Param("idGrupo") Long idGrupo);
	
	/**
	 * Ejecuta el procedimiento almacenado para cambiar el rol administrativo de un miembro dentro de un grupo.
	 * 
	 * @param idGrupo identificador del grupo.
	 * @param idUsuario identificador del usuario al que se le cambiará el rol.
	 * @param idOrigen identificador del usuario origen con permisos administrativos que autoriza el cambio.
	 */
	@Procedure(procedureName = "sp_cambiar_rol_admin")
	public void cambiarRolAdmin(@Param("p_id_grupo") Long idGrupo, @Param("p_id_usuario") Long idUsuario, @Param("p_id_origen") Long idOrigen);
	
	/**
	 * Verifica mediante una función PL/pgSQL nativa si un usuario es miembro activo de un grupo determinado.
	 * 
	 * @param idGrupo identificador del grupo.
	 * @param idUsuario identificador del usuario.
	 * @return {@code true} si el usuario pertenece al grupo, {@code false} en caso contrario.
	 */
	@Query(value = "SELECT * FROM fn_es_miembro_grupo(:g, :u)", nativeQuery = true)
	public boolean esMiembro(@Param("g") Long idGrupo, @Param("u") Long idUsuario);
}
