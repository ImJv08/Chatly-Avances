package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información 
 * relacionada con el bloqueo de usuarios dentro de la plataforma.
 */
public class BloqueoDTO {

	/**
	 * Identificador único del registro de bloqueo.
	 */
	private Long id;
	
	/**
	 * Identificador único del usuario que realiza la acción de bloqueo.
	 */
	private Long idUsuario; 
	
	/**
	 * Identificador único del usuario que ha sido bloqueado.
	 */
	private Long idUsuarioBloqueado; 
	
	/**
	 * Nombre o alias del usuario bloqueado para su visualización.
	 */
	private String nombreBloqueado;
	
	/**
	 * Número telefónico asociado al usuario bloqueado.
	 */
	private String numeroTelefonicoBloqueado;
	
	/**
	 * URL pública de la foto de perfil correspondiente al usuario bloqueado.
	 */
	private String fotoPerfilUrlBloqueado;
	
	/**
	 * Fecha y hora exacta en la que se registró el bloqueo.
	 */
	private Date fecha;
	
	/**
	 * Constructor vacio de {@link BloqueoDTO}.
	 */
	public BloqueoDTO() {
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link BloqueoDTO}.
	 * 
	 * @param id identificador único del registro de bloqueo.
	 * @param idUsuario identificador del usuario que bloquea.
	 * @param idUsuarioBloqueado identificador del usuario bloqueado.
	 * @param nombreBloqueado nombre del usuario bloqueado.
	 * @param numeroTelefonicoBloqueado número telefónico del usuario bloqueado.
	 * @param fotoPerfilUrlBloqueado URL de la foto de perfil del usuario bloqueado.
	 * @param fecha fecha en que se efectuó el bloqueo.
	 */
	public BloqueoDTO(Long id, Long idUsuario, Long idUsuarioBloqueado, String nombreBloqueado,
			String numeroTelefonicoBloqueado, String fotoPerfilUrlBloqueado, Date fecha) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.idUsuarioBloqueado = idUsuarioBloqueado;
		this.nombreBloqueado = nombreBloqueado;
		this.numeroTelefonicoBloqueado = numeroTelefonicoBloqueado;
		this.fotoPerfilUrlBloqueado = fotoPerfilUrlBloqueado;
		this.fecha = fecha;
	}

	/**
	 * Obtiene el identificador único del registro de bloqueo.
	 * 
	 * @return el ID del bloqueo.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del registro de bloqueo.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario que realiza el bloqueo.
	 * 
	 * @return el ID del usuario bloqueador.
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario que realiza el bloqueo.
	 * 
	 * @param idUsuario el nuevo ID del usuario bloqueador a asignar.
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el identificador del usuario bloqueado.
	 * 
	 * @return el ID del usuario bloqueado.
	 */
	public Long getIdUsuarioBloqueado() {
		return idUsuarioBloqueado;
	}

	/**
	 * Establece el identificador del usuario bloqueado.
	 * 
	 * @param idUsuarioBloqueado el nuevo ID del usuario bloqueado a asignar.
	 */
	public void setIdUsuarioBloqueado(Long idUsuarioBloqueado) {
		this.idUsuarioBloqueado = idUsuarioBloqueado;
	}

	/**
	 * Obtiene el nombre del usuario bloqueado.
	 * 
	 * @return el nombre del usuario bloqueado.
	 */
	public String getNombreBloqueado() {
		return nombreBloqueado;
	}

	/**
	 * Establece el nombre del usuario bloqueado.
	 * 
	 * @param nombreBloqueado el nuevo nombre a asignar.
	 */
	public void setNombreBloqueado(String nombreBloqueado) {
		this.nombreBloqueado = nombreBloqueado;
	}

	/**
	 * Obtiene el número telefónico del usuario bloqueado.
	 * 
	 * @return el número telefónico.
	 */
	public String getNumeroTelefonicoBloqueado() {
		return numeroTelefonicoBloqueado;
	}

	/**
	 * Establece el número telefónico del usuario bloqueado.
	 * 
	 * @param numeroTelefonicoBloqueado el nuevo número telefónico a asignar.
	 */
	public void setNumeroTelefonicoBloqueado(String numeroTelefonicoBloqueado) {
		this.numeroTelefonicoBloqueado = numeroTelefonicoBloqueado;
	}

	/**
	 * Obtiene la URL de la foto de perfil del usuario bloqueado.
	 * 
	 * @return la URL de la foto de perfil.
	 */
	public String getFotoPerfilUrlBloqueado() {
		return fotoPerfilUrlBloqueado;
	}

	/**
	 * Establece la URL de la foto de perfil del usuario bloqueado.
	 * 
	 * @param fotoPerfilUrlBloqueado la nueva URL de foto a asignar.
	 */
	public void setFotoPerfilUrlBloqueado(String fotoPerfilUrlBloqueado) {
		this.fotoPerfilUrlBloqueado = fotoPerfilUrlBloqueado;
	}

	/**
	 * Obtiene la fecha en que se registró el bloqueo.
	 * 
	 * @return la fecha del bloqueo.
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha en que se registró el bloqueo.
	 * 
	 * @param fecha la nueva fecha a asignar.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link BloqueoDTO}.
	 * 
	 * @return una cadena formateada con los datos del bloqueo.
	 */
	@Override
	public String toString() {
		return "BloqueoDTO [id=" + id + ", idUsuario=" + idUsuario + ", idUsuarioBloqueado=" + idUsuarioBloqueado
				+ ", nombreBloqueado=" + nombreBloqueado + ", fecha=" + fecha + "]";
	}

}