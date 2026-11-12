package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información 
 * de las notificaciones del sistema enviadas a los usuarios.
 */
public class NotificacionDTO {

	/**
	  * Identificador único de la notificación.
	  */
	 private Long id;
	 /**
	  * Identificador único del usuario receptor de la notificación.
	  */
	 private Long usuario;
	 
	 /**
	  * Identificador único del usuario origen que desencadenó la notificación.
	  */
	 private Long usuarioOrigen;
	 
	 /**
	  * Indicador booleano que señala si la notificación ya ha sido leída.
	  */
	 private boolean fueLeido;
	 
	 /**
	  * Tipo o categoría de la notificación (por ejemplo: MENSAJE, CONTACTO, GRUPO).
	  */
	 private String tipo;
	 
	 /**
	  * Fecha y hora exacta en la que se generó la notificación.
	  */
	 private Date fecha;
	 
	 /**
	  * Constructor vacio de {@link NotificacionDTO}.
	  */
	 public NotificacionDTO() {
		// TODO Auto-generated constructor stub
	}

	 /**
	  * Constructor con todos los campos para la inicialización completa de un objeto {@link NotificacionDTO}.
	  * 
	  * @param id identificador único de la notificación.
	  * @param idUsuario identificador del usuario receptor.
	  * @param idUsuarioOrigen identificador del usuario origen.
	  * @param leido estado de lectura de la notificación.
	  * @param tipo categoría o tipo de notificación.
	  * @param fecha fecha y hora de la notificación.
	  */
	 public NotificacionDTO(Long id, Long idUsuario, Long idUsuarioOrigen, boolean leido, String tipo, Date fecha) {
		super();
		this.id = id;
		this.usuario = idUsuario;
		this.usuarioOrigen = idUsuarioOrigen;
		this.fueLeido = leido;
		this.tipo = tipo;
		this.fecha = fecha;
	 }

	 /**
	  * Obtiene el identificador único de la notificación.
	  * 
	  * @return el ID de la notificación.
	  */
	 public Long getId() {
		 return id;
	 }

	 /**
	  * Establece el identificador único de la notificación.
	  * 
	  * @param id el nuevo ID a asignar.
	  */
	 public void setId(Long id) {
		 this.id = id;
	 }

	 /**
	  * Obtiene el identificador del usuario receptor.
	  * 
	  * @return el ID del usuario receptor.
	  */
	 public Long getIdUsuario() {
		 return usuario;
	 }

	 /**
	  * Establece el identificador del usuario receptor.
	  * 
	  * @param idUsuario el nuevo ID de usuario receptor a asignar.
	  */
	 public void setIdUsuario(Long idUsuario) {
		 this.usuario = idUsuario;
	 }

	 /**
	  * Obtiene el identificador del usuario origen.
	  * 
	  * @return el ID del usuario origen.
	  */
	 public Long getIdUsuarioOrigen() {
		 return usuarioOrigen;
	 }

	 /**
	  * Establece el identificador del usuario origen.
	  * 
	  * @param idUsuarioOrigen el nuevo ID de usuario origen a asignar.
	  */
	 public void setIdUsuarioOrigen(Long idUsuarioOrigen) {
		 this.usuarioOrigen = idUsuarioOrigen;
	 }

	 /**
	  * Indica si la notificación ha sido leída.
	  * 
	  * @return {@code true} si está leída, {@code false} en caso contrario.
	  */
	 public boolean isLeido() {
		 return fueLeido;
	 }

	 /**
	  * Establece el estado de lectura de la notificación.
	  * 
	  * @param leido el nuevo estado de lectura a asignar.
	  */
	 public void setLeido(boolean leido) {
		 this.fueLeido = leido;
	 }

	 /**
	  * Obtiene el tipo o categoría de la notificación.
	  * 
	  * @return el tipo de notificación.
	  */
	 public String getTipo() {
		 return tipo;
	 }

	 /**
	  * Establece el tipo o categoría de la notificación.
	  * 
	  * @param tipo el nuevo tipo a asignar.
	  */
	 public void setTipo(String tipo) {
		 this.tipo = tipo;
	 }

	 /**
	  * Obtiene la fecha de creación de la notificación.
	  * 
	  * @return la fecha de la notificación.
	  */
	 public Date getFecha() {
		 return fecha;
	 }

	 /**
	  * Establece la fecha de creación de la notificación.
	  * 
	  * @param fecha la nueva fecha a asignar.
	  */
	 public void setFecha(Date fecha) {
		 this.fecha = fecha;
	 }
}
