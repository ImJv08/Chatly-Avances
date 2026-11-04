package co.edu.unbosque.mensajeria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import co.edu.unbosque.mensajeria.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Procedure(procedureName = "SP_CREAR_USUARIO")
	public void crearUsuario(@Param("P_PRIMER_NOMBRE") String primerNombre,
			@Param("P_SEGUNDO_NOMBRE") String segundoNombre, @Param("P_PRIMER_APELLIDO") String primerApellido,
			@Param("P_SEGUNDO_APELLIDO") String segundoApellido, @Param("P_NUMERO_TELEFONICO") String numeroTelefonico,
			@Param("P_FOTO_PERFIL_URL") String fotoPerfilUrl, @Param("P_CONTRASENIA") String contrasenia);
	
	@Procedure(procedureName = "SP_ELIMINAR_USUARIO")
	public void eliminarUsuario(@Param("P_ID_USUARIO") Long idUsuario);
	
	@Procedure(procedureName = "SP_ACTUALIZAR_USUARIO")
	public void actualizarUsuario(@Param("P_ID_USUARIO") Long idUsuario, @Param("P_PRIMER_NOMBRE") String primerNombre,
			@Param("P_SEGUNDO_NOMBRE") String segundoNombre, @Param("P_PRIMER_APELLIDO") String primerApellido,
			@Param("P_SEGUNDO_APELLIDO") String segundoApellido, @Param("P_NUMERO_TELEFONICO") String numeroTelefonico,
			@Param("P_FOTO_PERFIL_URL") String fotoPerfilUrl, @Param("P_CONTRASENIA") String contrasenia);
	
	@Query(value = "SELECT * FROM public.fn_buscar_por_numero(:numero)", nativeQuery = true)
	public Optional<Usuario> buscarPorNumero(@Param("numero") String numero);
	

	
}
