package co.edu.unbosque.mensajeria.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Entidad JPA que representa los estados (historias) publicados por los usuarios 
 * en la base de datos relacional, mapeada a la tabla {@code estados}.
 */
@Entity
@Table(name = "estados")
public class Estado {

	/**
	 * Identificador único y clave primaria del estado.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/**
	 * Usuario propietario que publica el estado (relación muchos a uno).
	 */
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	/**
	 * Tipo de contenido multimedia del estado (por ejemplo: imagen, video).
	 */
	@Column(name = "tipo", length = 50)
	private String tipo; //si es imagen,video
	
	/**
	 * URL de acceso al recurso multimedia (imagen o video) adjunto en el estado.
	 */
	@Column(name = "contenido_url", length = 255)
    private String contenidoUrl; // url si es imagen o video
	
	/**
	 * Texto descriptivo opcional que acompaña al estado.
	 */
	@Column(name = "descripcion", length = 500)
    private String descripcion;
	
	/**
	 * Fecha y hora exacta en la que se publicó el estado (generada automáticamente al crear).
	 */
	@Column(name = "fecha_publicacion", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	@CreationTimestamp 
	private Date fechaPublicacion;
	
	/**
	 * Fecha y hora en la que expirará el estado y dejará de estar disponible.
	 */
	@Column(name = "fecha_expiracion", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date fechaExpiracion;

	/**
	 * Constructor vacio de {@link Estado}.
	 */
	public Estado() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link Estado}.
	 * 
	 * @param id identificador único del estado.
	 * @param usuario usuario propietario del estado.
	 * @param tipo tipo de contenido multimedia.
	 * @param contenidoUrl URL del recurso multimedia.
	 * @param descripcion descripción textual del estado.
	 * @param fechaPublicacion fecha de publicación del estado.
	 * @param fechaExpiracion fecha de expiración del estado.
	 */
	public Estado(Long id, Usuario usuario, String tipo, String contenidoUrl, String descripcion, Date fechaPublicacion,
			Date fechaExpiracion) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.tipo = tipo;
		this.contenidoUrl = contenidoUrl;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.fechaExpiracion = fechaExpiracion;
	}

	/**
	 * Obtiene el identificador único del estado.
	 * 
	 * @return el ID del estado.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del estado.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el usuario propietario del estado.
	 * 
	 * @return el usuario propietario.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Establece el usuario propietario del estado.
	 * 
	 * @param usuario el nuevo usuario propietario a asignar.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el tipo de contenido multimedia del estado.
	 * 
	 * @return el tipo de contenido.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de contenido multimedia del estado.
	 * 
	 * @param tipo el nuevo tipo a asignar.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene la URL del recurso multimedia del estado.
	 * 
	 * @return la URL de contenido.
	 */
	public String getContenidoUrl() {
		return contenidoUrl;
	}

	/**
	 * Establece la URL del recurso multimedia del estado.
	 * 
	 * @param contenidoUrl la nueva URL a asignar.
	 */
	public void setContenidoUrl(String contenidoUrl) {
		this.contenidoUrl = contenidoUrl;
	}

	/**
	 * Obtiene la fecha de publicación del estado.
	 * 
	 * @return la fecha de publicación.
	 */
	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	/**
	 * Establece la fecha de publicación del estado.
	 * 
	 * @param fechaPublicacion la nueva fecha de publicación a asignar.
	 */
	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	/**
	 * Obtiene la fecha de expiración del estado.
	 * 
	 * @return la fecha de expiración.
	 */
	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}

	/**
	 * Establece la fecha de expiración del estado.
	 * 
	 * @param fechaExpiracion la nueva fecha de expiración a asignar.
	 */
	public void setFechaExpiracion(Date fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}
	
	/**
	 * Obtiene la descripción textual del estado.
	 * 
	 * @return la descripción.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción textual del estado.
	 * 
	 * @param descripcion la nueva descripción a asignar.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link Estado}.
	 * 
	 * @return una cadena formateada con los datos de la entidad.
	 */
	@Override
	public String toString() {
		return "Estado [id=" + id + ", usuario=" + usuario + ", tipo=" + tipo + ", contenidoUrl=" + contenidoUrl
				+ ", descripcion=" + descripcion + ", fechaPublicacion=" + fechaPublicacion + ", fechaExpiracion="
				+ fechaExpiracion + "]";
	}
}

