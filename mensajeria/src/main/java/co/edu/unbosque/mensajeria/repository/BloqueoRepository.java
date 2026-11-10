package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.entity.Bloqueo;

@Repository
public interface BloqueoRepository extends JpaRepository<Bloqueo, Long> {

	@Procedure(procedureName = "SP_BLOQUEAR_USUARIO")
	public void bloquearUsuario(@Param("P_ID_USUARIO") Long idUsuario,
			@Param("P_ID_USUARIO_BLOQUEADO") Long idUsuarioBloqueado);

	@Procedure(procedureName = "SP_DESBLOQUEAR_USUARIO")
	public void desbloquearUsuario(@Param("P_ID_USUARIO") Long idUsuario,
			@Param("P_ID_USUARIO_BLOQUEADO") Long idUsuarioBloqueado);

	@Query(value = "SELECT * FROM FN_LISTAR_BLOQUEOS_USUARIO(:idUsuario)", nativeQuery = true)
	public List<Bloqueo> listarBloqueosPorUsuario(@Param("idUsuario") Long idUsuario);
	
	@Query(value = "SELECT * FROM FN_ESTA_BLOQUEADO(:idBloqueador, :idBloqueado)", nativeQuery = true)
	public boolean estaBloqueado(@Param("idBloqueador") Long idBloqueador,
	        @Param("idBloqueado") Long idBloqueado);
}