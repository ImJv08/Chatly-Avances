package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.entity.Estado;

/**
 * Repositorio JPA para la entidad {@link Estado}, que gestiona la persistencia
 * de los estados multimedia y ejecuta procedimientos almacenados y funciones PL/pgSQL nativas.
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

	/**
	 * Ejecuta el procedimiento almacenado para crear y registrar un nuevo estado en el sistema.
	 * 
	 * @param idUsuario identificador del usuario propietario que publica el estado.
	 * @param tipo tipo de contenido multimedia (por ejemplo: imagen, video).
	 * @param contenidoUrl URL de acceso al recurso multimedia.
	 * @param descripcion descripción textual opcional que acompaña al estado.
	 */
	@Procedure(procedureName = "SP_CREAR_ESTADO")
	public void crearEstado(@Param("P_ID_USUARIO") Long idUsuario, @Param("P_TIPO") String tipo,
			@Param("P_contenido_url") String contenidoUrl, @Param("P_descripcion") String descripcion);

	/**
	 * Ejecuta el procedimiento almacenado para eliminar un estado existente del sistema.
	 * 
	 * @param idEstado identificador único del estado que se va a eliminar.
	 */
	@Procedure(procedureName = "SP_ELIMINAR_ESTADO")
	public void eliminarEstado(@Param("P_ID_ESTADO") Long idEstado);

	/**
	 * Lista los estados publicados por los contactos de un usuario específico 
	 * mediante una función PL/pgSQL nativa que retorna arreglos de objetos.
	 * 
	 * @param idUsuario identificador del usuario consultante.
	 * @return una lista de arreglos de objetos con los datos de los estados de los contactos.
	 */
	@Query(value = "SELECT * FROM FN_LISTAR_ESTADOS_CONTACTOS(:idUsuario)", nativeQuery = true)
	List<Object[]> listarEstadosPorContactos(@Param("idUsuario") Long idUsuario);
}