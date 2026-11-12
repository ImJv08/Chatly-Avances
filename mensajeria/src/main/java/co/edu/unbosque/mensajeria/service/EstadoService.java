package co.edu.unbosque.mensajeria.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.mensajeria.dto.EstadoDTO;
import co.edu.unbosque.mensajeria.repository.EstadoRepository;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Servicio de lógica de negocio para la gestión de estados multimedia, 
 * encargado de validar archivos multimedia (imágenes y videos), gestionar su almacenamiento local,
 * procesar la creación y eliminación en la base de datos, y transformar los resultados en objetos DTO.
 */
@Service
public class EstadoService {

	/**
	 * Repositorio para la persistencia y consultas de estados.
	 */
	private final EstadoRepository estadoRepository;

	/**
	 * URL base de la aplicación inyectada desde la configuración para construir rutas de acceso a archivos.
	 */
	@Value("${app.base-url}")
	private String baseUrl;
	
	/**
	 * Constructor para la inyección de dependencias del repositorio de estados.
	 * 
	 * @param estadoRepository repositorio de estados.
	 */
	public EstadoService(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}
	
	/**
	 * Valida, procesa y almacena un archivo multimedia para crear un nuevo estado en el sistema.
	 * 
	 * @param dto objeto con los datos del estado (ID de usuario y descripción opcional).
	 * @param archivo archivo multimedia adjunto (imagen o video).
	 * @return un código de estado numérico de resultado (0 si es exitoso, o códigos de error específicos como 2, 5, 6, 7).
	 */
	@Transactional
	public int crearEstado(EstadoDTO dto, MultipartFile archivo) {
		if (archivo == null || archivo.isEmpty()) {
			return 2;
		}
		
		if (dto.getDescripcion() != null && dto.getDescripcion().length() > 500) {
			return 7;
		}

		String contArchivo = archivo.getContentType();
		String tipo;
		
		long max;
		
		if (contArchivo != null && contArchivo.startsWith("image/")) { 
			
			tipo = "IMAGEN"; 
			max = 5L * 1024 * 1024; 
			
		} else if (contArchivo != null && contArchivo.startsWith("video/")) { 
			
			tipo = "VIDEO"; 
			max = 30L * 1024 * 1024; 
			
		} else {
			
			return 5;
		}
		
		if (archivo.getSize() > max) {
			return 6;
		}

		String url = guardarArchivo(archivo);
		estadoRepository.crearEstado(dto.getIdUsuario(), tipo, url, dto.getDescripcion());
		return 0;
	}

	/**
	 * Guarda físicamente un archivo multimedia en el directorio de almacenamiento local del servidor.
	 * 
	 * @param f archivo multipart a almacenar.
	 * @return la URL pública de acceso al recurso guardado.
	 * @throws UncheckedIOException si ocurre un error de E/S durante la creación de directorios o copia de archivos.
	 */
	private String guardarArchivo(MultipartFile f) {
		try {
			String ext = f.getContentType().substring(f.getContentType().indexOf('/') + 1);
			String nombre = UUID.randomUUID() + "." + ext;
			Path dir = Paths.get(System.getProperty("user.dir"), "uploads", "estados");
			Files.createDirectories(dir);
			Files.copy(f.getInputStream(), dir.resolve(nombre), StandardCopyOption.REPLACE_EXISTING);
			return baseUrl + "/uploads/estados/" + nombre;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Lista los estados publicados por los contactos de un usuario específico 
	 * y mapea el resultado de la consulta nativa a una lista de objetos {@link EstadoDTO}.
	 * 
	 * @param idUsuario identificador del usuario consultante.
	 * @return una lista de objetos {@link EstadoDTO} con los detalles de los estados.
	 */
	public List<EstadoDTO> listarEstadosPorContactos(Long idUsuario) {
		return estadoRepository.listarEstadosPorContactos(idUsuario).stream().map(r -> {
			EstadoDTO d = new EstadoDTO();
			d.setId(((Number) r[0]).longValue());
			d.setIdUsuario(((Number) r[1]).longValue());
			d.setNombreUsuario((String) r[2]);
			d.setTipo((String) r[3]);
			d.setContenidoUrl((String) r[4]);
			d.setDescripcion((String) r[5]);
			d.setFechaPublicacion(aFecha(r[6]));
			d.setFechaExpiracion(aFecha(r[7]));
			return d;
		}).toList();
	}

	/**
	 * Convierte diferentes tipos de objetos de fecha y hora obtenidos de la base de datos a un objeto estándar {@link Date}.
	 * 
	 * @param o objeto de fecha original (puede ser {@link LocalDateTime}, {@link Timestamp}, {@link OffsetDateTime} o {@link Date}).
	 * @return la instancia convertida de {@link Date}.
	 */
	private Date aFecha(Object o) {
		if (o instanceof LocalDateTime ldt) {
			return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		}
		if (o instanceof Timestamp ts) {
			return new Date(ts.getTime());
		}
		if (o instanceof OffsetDateTime odt) {
			return Date.from(odt.toInstant());
		}
		return (Date) o;
	}

	/**
	 * Elimina un estado existente del sistema a partir de su identificador.
	 * 
	 * @param idEstado identificador único del estado que se va a eliminar.
	 */
	@Transactional
	public void eliminarEstado(Long idEstado) {
		estadoRepository.eliminarEstado(idEstado);
	}
}