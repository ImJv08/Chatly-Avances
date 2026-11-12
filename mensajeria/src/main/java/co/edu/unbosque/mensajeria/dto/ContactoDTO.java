package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información 
 * relacionada con los contactos de los usuarios en la plataforma.
 */
public class ContactoDTO {
	
	/**
	 * Identificador único del registro del contacto.
	 */
	private Long id;

	/**
	 * Identificador único del usuario propietario de la agenda de contactos.
	 */
	private Long idUsuario; 
	
	/**
	 * Identificador único del usuario que ha sido agregado como contacto.
	 */
	private Long idUsuarioContacto; 
	
	/**
	 * Fecha y hora en la que se añadió el contacto
	 */
	private Date fechaAgregado;
	
	/**
	 * Nombre o alias personalizado asignado por el usuario a su contacto.
	 */
	private String nombrePersonalizado;

	/**
	 * Constructor vacio de {@link ContactoDTO}.
	 */
	public ContactoDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con parámetros para la inicialización de un objeto {@link ContactoDTO} (sin ID primario).
	 * 
	 * @param idUsuario identificador del usuario propietario de la agenda.
	 * @param idUsuarioContacto identificador del usuario de contacto.
	 * @param fechaAgregado fecha en que se agregó el contacto.
	 * @param nombrePersonalizado nombre personalizado asignado al contacto.
	 */
	public ContactoDTO(Long idUsuario, Long idUsuarioContacto, Date fechaAgregado, String nombrePersonalizado) {
		super();
		this.idUsuario = idUsuario;
		this.idUsuarioContacto = idUsuarioContacto;
		this.fechaAgregado = fechaAgregado;
		this.nombrePersonalizado = nombrePersonalizado;
	}


	/**
	 * Obtiene el identificador único del registro del contacto.
	 * 
	 * @return el ID del contacto.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del registro del contacto.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario propietario de la agenda.
	 * 
	 * @return el ID del usuario propietario.
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario propietario de la agenda.
	 * 
	 * @param idUsuario el nuevo ID de usuario propietario a asignar.
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el identificador del usuario de contacto.
	 * 
	 * @return el ID del usuario de contacto.
	 */
	public Long getIdUsuarioContacto() {
		return idUsuarioContacto;
	}

	/**
	 * Establece el identificador del usuario de contacto.
	 * 
	 * @param idUsuarioContacto el nuevo ID de usuario de contacto a asignar.
	 */
	public void setIdUsuarioContacto(Long idUsuarioContacto) {
		this.idUsuarioContacto = idUsuarioContacto;
	}

	/**
	 * Obtiene la fecha en que se agregó el contacto.
	 * 
	 * @return la fecha de agregado.
	 */
	public Date getFechaAgregado() {
		return fechaAgregado;
	}

	/**
	 * Establece la fecha en que se agregó el contacto.
	 * 
	 * @param fechaAgregado la nueva fecha a asignar.
	 */
	public void setFechaAgregado(Date fechaAgregado) {
		this.fechaAgregado = fechaAgregado;
	}

	/**
	 * Obtiene el nombre personalizado asignado al contacto.
	 * 
	 * @return el nombre personalizado.
	 */
	public String getNombrePersonalizado() {
		return nombrePersonalizado;
	}

	/**
	 * Establece el nombre personalizado asignado al contacto.
	 * 
	 * @param nombrePersonalizado el nuevo nombre personalizado a asignar.
	 */
	public void setNombrePersonalizado(String nombrePersonalizado) {
		this.nombrePersonalizado = nombrePersonalizado;
	}

	/**
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link ContactoDTO}.
	 * 
	 * @return una cadena formateada con los datos del contacto.
	 */
	@Override
	public String toString() {
		return "ContactoDTO [id=" + id + ", idUsuario=" + idUsuario + ", idUsuarioContacto=" + idUsuarioContacto
				+ ", fechaAgregado=" + fechaAgregado + ", nombrePersonalizado=" + nombrePersonalizado + "]";
	}
}
