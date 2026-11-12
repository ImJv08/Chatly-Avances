
package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información 
 * de los estados (historias) publicados por los usuarios en la plataforma.
 */
public class EstadoDTO {
	
	/**
	 * Identificador único del estado.
	 */
	private Long id;	
	
	/**
	 * Identificador único del usuario propietario que publica el estado.
	 */
	private Long idUsuario;
	
	/**
	 * Tipo de contenido multimedia del estado (por ejemplo: IMAGEN, VIDEO).
	 */
	private String tipo; 
	
	/**
     * URL pública de acceso al recurso multimedia adjunto en el estado.
     */
    private String contenidoUrl; 
    
    /**
     * Texto o descripción opcional que acompaña al estado.
     */
    private String descripcion;
    
    /**
     * Nombre del usuario que realiza la publicación.
     */
    private String nombreUsuario;
    
    /**
	 * Fecha y hora exacta en la que se publicó el estado.
	 */
	private Date fechaPublicacion;
	
	/**
	 * Fecha y hora en la que expirará y dejará de estar visible el estado.
	 */
	private Date fechaExpiracion;

	/**
	 * Constructor vacio de {@link EstadoDTO}.
	 */
	public EstadoDTO() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link EstadoDTO}.
	 * 
	 * @param id identificador único del estado.
	 * @param idUsuario identificador del usuario propietario.
	 * @param tipo tipo de contenido multimedia.
	 * @param contenidoUrl URL del recurso multimedia.
	 * @param descripcion descripción textual del estado.
	 * @param nombreUsuario nombre del usuario publicador.
	 * @param fechaPublicacion fecha de publicación del estado.
	 * @param fechaExpiracion fecha de expiración del estado.
	 */
	public EstadoDTO(Long id, Long idUsuario, String tipo, String contenidoUrl, String descripcion,
			String nombreUsuario, Date fechaPublicacion, Date fechaExpiracion) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.tipo = tipo;
		this.contenidoUrl = contenidoUrl;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.fechaExpiracion = fechaExpiracion;
		this.nombreUsuario = nombreUsuario;
	}
	
	

	/**
	 * Obtiene el nombre del usuario publicador.
	 * 
	 * @return el nombre del usuario.
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Establece el nombre del usuario publicador.
	 * 
	 * @param nombreUsuario el nuevo nombre de usuario a asignar.
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
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
	 * Obtiene el identificador del usuario propietario.
	 * 
	 * @return el ID del usuario.
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario propietario.
	 * 
	 * @param idUsuario el nuevo ID de usuario a asignar.
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link EstadoDTO}.
	 * 
	 * @return una cadena formateada con los datos del estado.
	 */
	@Override
	public String toString() {
		return "Estado [id=" + id + ", usuario=" + idUsuario + ", tipo=" + tipo + ", contenidoUrl=" + contenidoUrl
				+ ", descripcion=" + descripcion + ", fechaPublicacion=" + fechaPublicacion + ", fechaExpiracion="
				+ fechaExpiracion + "]";
	}
}
