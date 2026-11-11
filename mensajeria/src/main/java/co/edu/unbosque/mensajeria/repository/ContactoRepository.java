package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.mensajeria.dto.ContactoRespuestaDTO;
import co.edu.unbosque.mensajeria.entity.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

	@Procedure(procedureName = "SP_CREAR_CONTACTO")
	public void crearContacto(@Param("P_id_usuario") Long idUsuario, @Param("P_id_usuario_contacto") Long idUsuarioContacto, @Param("p_nombre_personalizado") String p_nombre_personalizado);
	@Procedure(procedureName = "SP_ELIMINAR_CONTACTO")
	public void eliminarContacto(@Param("P_id_usuario") Long idUsuario, @Param("P_id_usuario_contacto") Long idUsuarioContacto);
	
	@Query(value = "SELECT * FROM FN_LISTAR_CONTACTOS_USUARIO(:idUsuario)", nativeQuery = true)
	public List<ContactoRespuestaDTO> listarContactosPorUsuario(@Param("idUsuario") Long idUsuario);
	
	@Procedure(procedureName = "SP_ACTUALIZAR_NOMBRE_CONTACTO")
	void actualizarNombreContacto(@Param("p_id") Long id, @Param("p_nombre_personalizado") String nombre);
	
	@Query(value = "SELECT * FROM FN_OBTENER_ID_CONTACTO(:idUsuario, :idUsuarioContacto)", nativeQuery = true)
	Long obtenerIdContacto(@Param("idUsuario") Long idUsuario, @Param("idUsuarioContacto") Long idUsuarioContacto);
	
	@Query(value = "SELECT * FROM FN_BUSCAR_CONTACTOS_USUARIO(:idUsuario, :texto)", nativeQuery = true)
	List<ContactoRespuestaDTO> buscarContactosPorUsuario(@Param("idUsuario") Long idUsuario, @Param("texto") String texto);
}
