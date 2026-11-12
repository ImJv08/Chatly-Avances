package co.edu.unbosque.mensajeria.entity;

import java.util.Date;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/**
 * Registro de auditoría de un estado publicado. Lo llena el trigger
 * {@code TGR_AI_ESTADOS} al publicar y lo actualiza {@code TGR_AD_ESTADOS}
 * al eliminar el estado antes de vencer. Es de solo lectura desde Java.
 */
@Entity
@Immutable
@Table(name = "historial_estados")
public class HistorialEstado {

	/** Identificador del registro del historial. */
	@Id
	private Long id;

	/** Identificador del estado original; no tiene llave foránea. */
	@Column(name = "id_estado")
	private Long idEstado;

	/** Identificador del usuario dueño del estado. */
	@Column(name = "id_usuario")
	private Long idUsuario;

	/** Tipo de contenido: IMAGEN o VIDEO. */
	private String tipo;

	/** URL del archivo en el servidor. */
	@Column(name = "contenido_url")
	private String contenidoUrl;

	/** Descripción escrita por el usuario. */
	private String descripcion;

	/** Fecha y hora de publicación. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_publicacion")
	private Date fechaPublicacion;

	/** Fecha y hora en que el estado vence (24 horas después). */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_expiracion")
	private Date fechaExpiracion;

	/** Fecha en que el usuario lo eliminó antes de vencer; {@code null} si no ocurrió. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_eliminacion")
	private Date fechaEliminacion;

	/**
	 * Constructor vacio de {@link HistorialEstado}.
	 */
	public HistorialEstado() {}

	
	/**
	 * Crea un registro del historial con todos sus datos.
	 *
	 * @param id identificador del registro
	 * @param idEstado identificador del estado original
	 * @param idUsuario identificador del usuario dueño del estado
	 * @param tipo tipo de contenido (IMAGEN o VIDEO)
	 * @param contenidoUrl URL del archivo en el servidor
	 * @param descripcion descripción escrita por el usuario
	 * @param fechaPublicacion fecha y hora de publicación
	 * @param fechaExpiracion fecha y hora de vencimiento
	 * @param fechaEliminacion fecha en que se eliminó antes de vencer, o {@code null}
	 */
	public HistorialEstado(Long id, Long idEstado, Long idUsuario, String tipo,
	        String contenidoUrl, String descripcion, Date fechaPublicacion,
	        Date fechaExpiracion, Date fechaEliminacion) {

	    this.id = id;
	    this.idEstado = idEstado;
	    this.idUsuario = idUsuario;
	    this.tipo = tipo;
	    this.contenidoUrl = contenidoUrl;
	    this.descripcion = descripcion;
	    this.fechaPublicacion = fechaPublicacion;
	    this.fechaExpiracion = fechaExpiracion;
	    this.fechaEliminacion = fechaEliminacion;
	}

	/**
	 * Obtiene el identificador del registro.
	 *
	 * @return identificador del registro
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Cambia el identificador del registro.
	 *
	 * @param id identificador del registro
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del estado original.
	 *
	 * @return identificador del estado
	 */
	public Long getIdEstado() {
		return idEstado;
	}

	/**
	 * Cambia el identificador del estado original.
	 *
	 * @param idEstado identificador del estado
	 */
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 * Obtiene el identificador del usuario dueño del estado.
	 *
	 * @return identificador del usuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Cambia el identificador del usuario dueño del estado.
	 *
	 * @param idUsuario identificador del usuario
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el tipo de contenido.
	 *
	 * @return IMAGEN o VIDEO
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Cambia el tipo de contenido.
	 *
	 * @param tipo IMAGEN o VIDEO
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene la URL del archivo.
	 *
	 * @return URL del contenido
	 */
	public String getContenidoUrl() {
		return contenidoUrl;
	}

	/**
	 * Cambia la URL del archivo.
	 *
	 * @param contenidoUrl URL del contenido
	 */
	public void setContenidoUrl(String contenidoUrl) {
		this.contenidoUrl = contenidoUrl;
	}

	/**
	 * Obtiene la descripción del estado.
	 *
	 * @return descripción
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Cambia la descripción del estado.
	 *
	 * @param descripcion descripción
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene la fecha de publicación.
	 *
	 * @return fecha y hora de publicación
	 */
	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	/**
	 * Cambia la fecha de publicación.
	 *
	 * @param fechaPublicacion fecha y hora de publicación
	 */
	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	/**
	 * Obtiene la fecha de vencimiento.
	 *
	 * @return fecha y hora de vencimiento
	 */
	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}

	/**
	 * Cambia la fecha de vencimiento.
	 *
	 * @param fechaExpiracion fecha y hora de vencimiento
	 */
	public void setFechaExpiracion(Date fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	/**
	 * Obtiene la fecha de eliminación anticipada.
	 *
	 * @return fecha de eliminación, o {@code null} si no fue eliminado antes de vencer
	 */
	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}

	/**
	 * Cambia la fecha de eliminación anticipada.
	 *
	 * @param fechaEliminacion fecha de eliminación, o {@code null}
	 */
	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}
	
}
