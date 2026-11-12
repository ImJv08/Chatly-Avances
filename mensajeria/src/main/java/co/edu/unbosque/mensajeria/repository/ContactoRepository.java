package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.dto.ContactoRespuestaDTO;
import co.edu.unbosque.mensajeria.entity.Contacto;

/**
 * Repositorio JPA para la entidad {@link Contacto}, que gestiona las operaciones de persistencia
 * de la agenda de contactos y ejecuta procedimientos almacenados y funciones PL/pgSQL nativas.
 */
@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

	/**
	 * Ejecuta el procedimiento almacenado para crear y registrar un nuevo contacto en la agenda de un usuario.
	 * 
	 * @param idUsuario identificador del usuario propietario de la agenda.
	 * @param idUsuarioContacto identificador del usuario que se va a agregar como contacto.
	 * @param p_nombre_personalizado nombre o alias personalizado asignado al contacto.
	 */
	@Procedure(procedureName = "SP_CREAR_CONTACTO")
	public void crearContacto(@Param("P_id_usuario") Long idUsuario, @Param("P_id_usuario_contacto") Long idUsuarioContacto, @Param("p_nombre_personalizado") String p_nombre_personalizado);
	
	/**
	 * Ejecuta el procedimiento almacenado para eliminar un contacto de la agenda de un usuario.
	 * 
	 * @param idUsuario identificador del usuario propietario de la agenda.
	 * @param idUsuarioContacto identificador del usuario de contacto que se va a eliminar.
	 */
	@Procedure(procedureName = "SP_ELIMINAR_CONTACTO")
	public void eliminarContacto(@Param("P_id_usuario") Long idUsuario, @Param("P_id_usuario_contacto") Long idUsuarioContacto);
	
	/**
	 * Lista todos los contactos asociados a un usuario específico mediante una función PL/pgSQL nativa.
	 * 
	 * @param idUsuario identificador del usuario propietario.
	 * @return una lista de objetos {@link ContactoRespuestaDTO} con los detalles de los contactos.
	 */
	@Query(value = "SELECT * FROM FN_LISTAR_CONTACTOS_USUARIO(:idUsuario)", nativeQuery = true)
	public List<ContactoRespuestaDTO> listarContactosPorUsuario(@Param("idUsuario") Long idUsuario);
	
	/**
	 * Ejecuta el procedimiento almacenado para actualizar el nombre o alias personalizado de un contacto.
	 * 
	 * @param id identificador único del registro de contacto.
	 * @param nombre nuevo nombre personalizado a asignar.
	 */
	@Procedure(procedureName = "SP_ACTUALIZAR_NOMBRE_CONTACTO")
	public void actualizarNombreContacto(@Param("p_id") Long id, @Param("p_nombre_personalizado") String nombre);
	
	/**
	 * Obtiene el identificador único del registro de contacto entre dos usuarios mediante una función PL/pgSQL nativa.
	 * 
	 * @param idUsuario identificador del usuario propietario.
	 * @param idUsuarioContacto identificador del usuario de contacto.
	 * @return el ID único del contacto, o {@code null} si no existe la relación.
	 */
	@Query(value = "SELECT * FROM FN_OBTENER_ID_CONTACTO(:idUsuario, :idUsuarioContacto)", nativeQuery = true)
	public Long obtenerIdContacto(@Param("idUsuario") Long idUsuario, @Param("idUsuarioContacto") Long idUsuarioContacto);
	
	/**
	 * Busca contactos en la agenda de un usuario que coincidan con un texto de búsqueda 
	 * mediante una función PL/pgSQL nativa.
	 * 
	 * @param idUsuario identificador del usuario propietario de la agenda.
	 * @param texto cadena de texto o filtro de búsqueda.
	 * @return una lista de {@link ContactoRespuestaDTO} que coinciden con el criterio.
	 */
	@Query(value = "SELECT * FROM FN_BUSCAR_CONTACTOS_USUARIO(:idUsuario, :texto)", nativeQuery = true)
	public List<ContactoRespuestaDTO> buscarContactosPorUsuario(@Param("idUsuario") Long idUsuario, @Param("texto") String texto);
}
