package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.entity.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

	@Procedure(procedureName = "SP_CREAR_ESTADO")
	public void crearEstado(@Param("P_ID_USUARIO") Long idUsuario, @Param("P_TIPO") String tipo,
			@Param("P_contenido_url") String contenidoUrl, @Param("P_descripcion") String descripcion);

	@Procedure(procedureName = "SP_ELIMINAR_ESTADO")
	public void eliminarEstado(@Param("P_ID_ESTADO") Long idEstado);

	@Query(value = "SELECT * FROM FN_LISTAR_ESTADOS_CONTACTOS(:idUsuario)", nativeQuery = true)
	public List<Estado> listarEstadosPorContactos(@Param("idUsuario") Long idUsuario);
}