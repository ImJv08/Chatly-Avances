package co.edu.unbosque.mensajeria.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.mensajeria.dto.GrupoDTO;
import co.edu.unbosque.mensajeria.dto.MiembroGrupoDTO;
import co.edu.unbosque.mensajeria.repository.BloqueoRepository;
import co.edu.unbosque.mensajeria.repository.GrupoRepository;
import jakarta.transaction.Transactional;
import co.edu.unbosque.mensajeria.entity.Grupo;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * Servicio de lógica de negocio para la gestión de grupos de chat, 
 * encargado de procesar la creación, actualización, eliminación de grupos, administración de miembros,
 * control de roles, validaciones de bloqueos y almacenamiento local de fotos de perfil.
 */
@Service
public class GrupoService {

	/**
	 * Repositorio para la persistencia y procedimientos de grupos.
	 */
	private final GrupoRepository grupoRepository;
	
	/**
	 * Repositorio para la verificación de bloqueos entre usuarios.
	 */
	private final BloqueoRepository bloqueoRepository;
	
	/**
	 * URL base de la aplicación inyectada para la construcción de rutas públicas de archivos.
	 */
	@Value("${app.base-url}")
	private String baseUrl;

	/**
	 * Constructor para la inyección de dependencias de los repositorios de grupos y bloqueos.
	 * 
	 * @param grupoRepository repositorio de grupos.
	 * @param bloqueoRepository repositorio de bloqueos.
	 */
	public GrupoService(GrupoRepository grupoRepository, BloqueoRepository bloqueoRepository) {
		this.grupoRepository = grupoRepository;
		this.bloqueoRepository = bloqueoRepository;
	}

	/**
	 * Valida, procesa y registra un nuevo grupo de chat con opción de foto de perfil.
	 * 
	 * @param dto objeto con los datos del grupo.
	 * @param foto archivo de imagen multimedia para el perfil del grupo.
	 * @return código de resultado numérico (0 si es exitoso, 2 si el nombre está vacío, 5 si el archivo es inválido o excede el tamaño).
	 */
	@Transactional
	public int crearGrupo(GrupoDTO dto, MultipartFile foto) {
		if (dto.getNombreGrupo() == null || dto.getNombreGrupo().trim().isEmpty()) {
			return 2;
		}
		if (foto != null && !foto.isEmpty()) {
			String tipo = foto.getContentType();
			if (tipo == null || !tipo.startsWith("image/") || foto.getSize() > 5 * 1024 * 1024) {
				return 5;
			}
			dto.setFotoPerfilUrl(guardarFoto(foto));
		}
		grupoRepository.crearGrupo(dto.getIdCreador(), dto.getNombreGrupo().trim(), dto.getFotoPerfilUrl());
		return 0;
	}

	/**
	 * Guarda físicamente la foto de perfil de un grupo en el directorio de almacenamiento local del servidor.
	 * 
	 * @param foto archivo multipart de imagen.
	 * @return la URL pública de acceso al recurso guardado.
	 * @throws UncheckedIOException si ocurre un error de E/S durante el almacenamiento.
	 */
	private String guardarFoto(MultipartFile foto) {
		try {
			String ext = foto.getContentType().substring(foto.getContentType().indexOf('/') + 1);
			String nombre = UUID.randomUUID() + "." + ext;
			Path dir = Paths.get(System.getProperty("user.dir"), "uploads", "grupos");
			Files.createDirectories(dir);
			Files.copy(foto.getInputStream(), dir.resolve(nombre), StandardCopyOption.REPLACE_EXISTING);
			return baseUrl + "/uploads/grupos/" + nombre;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Agrega un miembro a un grupo verificando previamente que no existan restricciones por bloqueos activos.
	 * 
	 * @param grupoMiembroDTO objeto que contiene el identificador del grupo y del usuario a agregar.
	 * @param idOrigen identificador del usuario origen que realiza la acción.
	 * @return {@code true} si el miembro fue agregado con éxito, {@code false} si el usuario está bloqueado.
	 */
	@Transactional
	public boolean agregarMiembro(MiembroGrupoDTO grupoMiembroDTO, Long idOrigen) {

		if (bloqueoRepository.estaBloqueado(grupoMiembroDTO.getUsuario(), idOrigen)) {
			return false;
		}

		grupoRepository.agregarMiembro(grupoMiembroDTO.getGrupo(), grupoMiembroDTO.getUsuario(), idOrigen);
		return true;
	}
	
	/**
	 * Elimina un grupo de forma definitiva del sistema.
	 * 
	 * @param idGrupo identificador único del grupo a eliminar.
	 * @param idCreador identificador del usuario creador que autoriza la acción.
	 */
	@Transactional
	public void eliminarGrupo( Long idGrupo, Long idCreador) {
		grupoRepository.eliminarGrupo(idGrupo, idCreador);
	}
	
	/**
	 * Actualiza los datos de un grupo existente (nombre e imagen de perfil).
	 * 
	 * @param grupoDTO objeto con los nuevos datos del grupo.
	 * @param idCreador identificador del usuario origen/creador con permisos.
	 * @param foto nuevo archivo de imagen de perfil opcional.
	 * @return código de resultado numérico (0 si es exitoso, 2 si el nombre está vacío, 5 si la imagen es inválida).
	 */
	@Transactional
	public int actualizarGrupo( GrupoDTO grupoDTO, Long idCreador, MultipartFile foto) {
		if(grupoDTO.getNombreGrupo() == null || grupoDTO.getNombreGrupo().trim().isEmpty()) {
			return 2;
		}
		
		if(foto != null && !foto.isEmpty()) {
			if(foto.getContentType() == null || !foto.getContentType().startsWith("image/")
					|| foto.getSize() > 5 * 1024 * 1024){
				return 5;
			}
			grupoDTO.setFotoPerfilUrl(guardarFoto(foto));
		}
		grupoRepository.actualizarGrupo(grupoDTO.getId(),idCreador, grupoDTO.getNombreGrupo(), 
				grupoDTO.getFotoPerfilUrl());
		return 0;
	}
	
	/**
	 * Permite que un usuario abandone un grupo específico.
	 * 
	 * @param grupoMiembroDTO objeto que contiene el grupo y el usuario que sale.
	 */
	@Transactional
	public void salirGrupo( MiembroGrupoDTO grupoMiembroDTO) {
		grupoRepository.salirGrupo(grupoMiembroDTO.getGrupo(), grupoMiembroDTO.getUsuario());
	}
	
	/**
	 * Elimina a un miembro específico de un grupo por medio de un usuario autorizado.
	 * 
	 * @param grupoMiembroDTO objeto con los datos del grupo y el usuario a eliminar.
	 * @param idOrigen identificador del usuario origen con privilegios administrativos.
	 */
	@Transactional
	public void eliminarMiembroGrupo( MiembroGrupoDTO grupoMiembroDTO, Long idOrigen) {
		grupoRepository.elimiarMiembroGrupo(grupoMiembroDTO.getGrupo(), grupoMiembroDTO.getUsuario(), idOrigen);
	}
	
	/**
	 * Lista todos los grupos a los cuales pertenece un usuario.
	 * 
	 * @param idUsuario identificador del usuario miembro.
	 * @return una lista de entidades {@link Grupo}.
	 */
	@Transactional
	public List<Grupo> listarGruposPorUsuario(Long idUsuario) {
		return grupoRepository.listarGruposPorUsuario(idUsuario);
	}
	
	/**
	 * Lista los grupos de un usuario estructurados en un formato de mapa clave-valor, 
	 * incluyendo el rol que desempeña en cada uno.
	 * 
	 * @param idUsuario identificador del usuario consultado.
	 * @return una lista de mapas con los atributos de los grupos y sus roles.
	 */
	public List<Map<String, Object>> listarGruposConRol(Long idUsuario) {
	    List<Object[]> resultados =
	            grupoRepository.listarGruposConRolPorUsuario(idUsuario);

	    List<Map<String, Object>> grupos = new ArrayList<>();

	    for (Object[] fila : resultados) {
	        Map<String, Object> grupo = new HashMap<>();

	        grupo.put("id", fila[0]);
	        grupo.put("idCreador", fila[1]);
	        grupo.put("nombre", fila[2]);
	        grupo.put("fotoPerfilUrl", fila[3]);
	        grupo.put("fechaRegistro", fila[4]);
	        grupo.put("rol", fila[5]);

	        grupos.add(grupo);
	    }

	    return grupos;
	}
	
	/**
	 * Cambia el rol administrativo de un miembro dentro de un grupo.
	 * 
	 * @param idGrupo identificador del grupo.
	 * @param idUsuario identificador del usuario al que se le modificará el rol.
	 * @param idOrigen identificador del usuario origen administrador que autoriza el cambio.
	 */
	@Transactional
	public void cambiarRolAdmin(Long idGrupo, Long idUsuario, Long idOrigen) {
	    grupoRepository.cambiarRolAdmin(idGrupo, idUsuario, idOrigen);
	}
	
	/**
	 * Lista los miembros pertenecientes a un grupo específico en un formato estructurado de mapas clave-valor.
	 * 
	 * @param idGrupo identificador del grupo.
	 * @return una lista de mapas con los detalles de cada miembro y su rol.
	 */
	public List<Map<String, Object>> listarMiembrosGrupo(Long idGrupo) {

	    List<Object[]> resultados = grupoRepository.listarMiembrosGrupo(idGrupo);

	    List<Map<String, Object>> miembros = new ArrayList<>();

	    for (Object[] fila : resultados) {
	        Map<String, Object> miembro = new HashMap<>();

	        miembro.put("idUsuario", fila[0]);
	        miembro.put("nombreCompleto", fila[1] + " " + fila[2]);
	        miembro.put("numeroTelefonico", fila[3]);
	        miembro.put("fotoPerfilUrl", fila[4]);
	        miembro.put("idGrupo", fila[5]);
	        miembro.put("rol", fila[6]);   

	        miembros.add(miembro);
	    }

	    return miembros;
	}

}
