package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.entity.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	@Procedure(procedureName = "SP_CREAR_GRUPO")
	public void crearGrupo(@Param("P_ID_CREADOR") Long idCreador, @Param("P_NOMBRE") String nombre,
			@Param("P_URL_FOTO_PERFIL") String urlFoto);

	@Procedure(procedureName = "SP_AGREGAR_MIEMBRO")
	public void agregarMiembro(@Param("P_ID_GRUPO") Long idGrupo, @Param("P_ID_USUARIO") Long idUsuario,
			@Param("P_ID_ORIGEN") Long idOrigen);

	@Procedure(procedureName = "SP_ELIMINAR_GRUPO")
	public void eliminarGrupo(@Param("P_ID_GRUPO") Long idGrupo, @Param("P_ID_CREADOR") Long idCreador);

	@Procedure(procedureName = "SP_ACTUALIZAR_GRUPO")
	public void actualizarGrupo(@Param("P_ID_GRUPO") Long idGrupo, @Param("P_ID_ORIGEN") Long idOrigen,
			@Param("P_NOMBRE") String nombre, @Param("P_URL_FOTO_PERFIL") String urlFoto);

	@Procedure(procedureName = "SP_SALIR_GRUPO")
	public void salirGrupo(@Param("P_ID_GRUPO") Long idGrupo, @Param("P_ID_USUARIO") Long idUsuario);

	@Procedure(procedureName = "SP_ELIMINAR_MIEMBRO_GRUPO")
	public void elimiarMiembroGrupo(@Param("P_ID_GRUPO") Long idGrupo, @Param("P_ID_USUARIO") Long idUsuario,
			@Param("P_ID_ORIGEN") Long idOrigen);

	@Query(value = "SELECT * FROM FN_LISTAR_GRUPOS_USUARIO(:idUsuario)", nativeQuery = true)
	public List<Grupo> listarGruposPorUsuario(@Param("idUsuario") Long idUsuario);

	@Query(value = "SELECT * FROM fn_listar_grupos_usuario_rol(:idUsuario)", nativeQuery = true)
	List<Object[]> listarGruposConRolPorUsuario(@Param("idUsuario") Long idUsuario);
}
