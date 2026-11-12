package co.edu.unbosque.mensajeria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import co.edu.unbosque.mensajeria.entity.Usuario;

/**
 * Repositorio JPA para la entidad {@link Usuario}, que gestiona las operaciones de persistencia
 * de los usuarios del sistema y ejecuta procedimientos almacenados y funciones PL/pgSQL nativas
 * para la creación, actualización, búsqueda y control de bloqueos.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * Ejecuta el procedimiento almacenado para registrar y crear un nuevo usuario en la base de datos.
	 * 
	 * @param primerNombre primer nombre del usuario.
	 * @param segundoNombre segundo nombre opcional del usuario.
	 * @param primerApellido primer apellido del usuario.
	 * @param segundoApellido segundo apellido opcional del usuario.
	 * @param numeroTelefonico número telefónico único de la cuenta.
	 * @param fotoPerfilUrl URL de acceso a la foto de perfil.
	 * @param contrasenia contraseña de acceso cifrada.
	 */
	@Procedure(procedureName = "SP_CREAR_USUARIO")
	public void crearUsuario(@Param("P_PRIMER_NOMBRE") String primerNombre,
			@Param("P_SEGUNDO_NOMBRE") String segundoNombre, @Param("P_PRIMER_APELLIDO") String primerApellido,
			@Param("P_SEGUNDO_APELLIDO") String segundoApellido, @Param("P_NUMERO_TELEFONICO") String numeroTelefonico,
			@Param("P_FOTO_PERFIL_URL") String fotoPerfilUrl, @Param("P_CONTRASENIA") String contrasenia);
	
	/**
	 * Ejecuta el procedimiento almacenado para eliminar un usuario del sistema de forma definitiva.
	 * 
	 * @param idUsuario identificador único del usuario que se va a eliminar.
	 */
	@Procedure(procedureName = "SP_ELIMINAR_USUARIO")
	public void eliminarUsuario(@Param("P_ID_USUARIO") Long idUsuario);
	
	/**
	 * Ejecuta el procedimiento almacenado para actualizar la información de un usuario existente.
	 * 
	 * @param idUsuario identificador único del usuario a actualizar.
	 * @param primerNombre nuevo primer nombre.
	 * @param segundoNombre nuevo segundo nombre.
	 * @param primerApellido nuevo primer apellido.
	 * @param segundoApellido nuevo segundo apellido.
	 * @param numeroTelefonico nuevo número telefónico.
	 * @param fotoPerfilUrl nueva URL de la foto de perfil.
	 * @param contrasenia nueva contraseña de acceso.
	 */
	@Procedure(procedureName = "SP_ACTUALIZAR_USUARIO")
	public void actualizarUsuario(@Param("P_ID_USUARIO") Long idUsuario, @Param("P_PRIMER_NOMBRE") String primerNombre,
			@Param("P_SEGUNDO_NOMBRE") String segundoNombre, @Param("P_PRIMER_APELLIDO") String primerApellido,
			@Param("P_SEGUNDO_APELLIDO") String segundoApellido, @Param("P_NUMERO_TELEFONICO") String numeroTelefonico,
			@Param("P_FOTO_PERFIL_URL") String fotoPerfilUrl, @Param("P_CONTRASENIA") String contrasenia);
	
	/**
	 * Busca un usuario por su número telefónico mediante una función PL/pgSQL nativa, 
	 * retornando un contenedor opcional.
	 * 
	 * @param numero número telefónico por el cual se realizará la búsqueda.
	 * @return un {@link Optional} que contiene la entidad {@link Usuario} si es encontrada, o vacío en caso contrario.
	 */
	@Query(value = "SELECT * FROM public.fn_buscar_por_numero(:numero)", nativeQuery = true)
	public Optional<Usuario> buscarPorNumero(@Param("numero") String numero);
	
	/**
	 * Verifica mediante una función PL/pgSQL nativa si existe un bloqueo mutuo o unidireccional entre dos usuarios.
	 * 
	 * @param a identificador del primer usuario.
	 * @param b identificador del segundo usuario.
	 * @return {@code true} si existe algún bloqueo entre ambos, {@code false} en caso contrario.
	 */	
	@Query(value = "SELECT public.fn_hay_bloqueo(:a, :b)", nativeQuery = true)
	boolean hayBloqueo(@Param("a") Long a, @Param("b") Long b);
	
}
