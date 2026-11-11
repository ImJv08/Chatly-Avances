package co.edu.unbosque.mensajeria.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unbosque.mensajeria.dto.EstadoDTO;
import co.edu.unbosque.mensajeria.entity.Estado;
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

@Service
public class EstadoService {

	private final EstadoRepository estadoRepository;

	@Value("${app.base-url}")
	private String baseUrl;
	
	public EstadoService(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}
	
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

	@Transactional
	public void eliminarEstado(Long idEstado) {
		estadoRepository.eliminarEstado(idEstado);
	}
}