package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información 
 * correspondiente a los mensajes intercambiados en la plataforma (tanto en chats privados como grupales).
 */
public class MensajeDTO {

	/**
	 * Identificador único del mensaje.
	 */
	private Long id;

	/**
	 * Identificador único del usuario emisor que envía el mensaje.
	 */
	private Long idEmisor;

	/**
	 * Identificador único del usuario receptor (en caso de mensajes directos).
	 */
	private Long idReceptor;

	/**
	 * Identificador único del grupo de destino (en caso de mensajes grupales).
	 */
	private Long idGrupo;

	/**
	 * Tipo de contenido del mensaje (por ejemplo: TEXTO, IMAGEN, ARCHIVO).
	 */
	private String tipo;

	/**
	 * Contenido textual del mensaje.
	 */
	private String texto;

	/**
	 * URL de acceso público al archivo multimedia o documento adjunto.
	 */
	private String contenidoUrl;

	/**
	 * Nombre original o generado del archivo adjunto.
	 */
	private String nombreArchivo;

	/**
	 * Fecha y hora en la que se registró o transmitió el mensaje.
	 */
	private Date fecha;

	/**
	 * Indicador booleano que señala si el mensaje ha sido leído por el destinatario.
	 */
	private boolean leido;

	/**
	 * Constructor vacio de {@link MensajeDTO}.
	 */
	public MensajeDTO() {
		
	}

	/**
	 * Obtiene el identificador único del mensaje.
	 * 
	 * @return el ID del mensaje.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del mensaje.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario emisor.
	 * 
	 * @return el ID del emisor.
	 */
	public Long getIdEmisor() {
		return idEmisor;
	}

	/**
	 * Establece el identificador del usuario emisor.
	 * 
	 * @param idEmisor el nuevo ID de emisor a asignar.
	 */
	public void setIdEmisor(Long idEmisor) {
		this.idEmisor = idEmisor;
	}

	/**
	 * Obtiene el nombre del archivo adjunto.
	 * 
	 * @return el nombre del archivo.
	 */
	public String getNombreArchivo() {
	    return nombreArchivo;
	}

	/**
	 * Establece el nombre del archivo adjunto.
	 * 
	 * @param nombreArchivo el nuevo nombre de archivo a asignar.
	 */
	public void setNombreArchivo(String nombreArchivo) {
	    this.nombreArchivo = nombreArchivo;
	}

	/**
	 * Obtiene el identificador del usuario receptor.
	 * 
	 * @return el ID del receptor.
	 */
	public Long getIdReceptor() {
		return idReceptor;
	}

	/**
	 * Establece el identificador del usuario receptor.
	 * 
	 * @param idReceptor el nuevo ID de receptor a asignar.
	 */
	public void setIdReceptor(Long idReceptor) {
		this.idReceptor = idReceptor;
	}

	/**
	 * Obtiene el identificador del grupo de chat.
	 * 
	 * @return el ID del grupo.
	 */
	public Long getIdGrupo() {
		return idGrupo;
	}

	/**
	 * Establece el identificador del grupo de chat.
	 * 
	 * @param idGrupo el nuevo ID de grupo a asignar.
	 */
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * Obtiene el tipo de contenido del mensaje.
	 * 
	 * @return el tipo de mensaje.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de contenido del mensaje.
	 * 
	 * @param tipo el nuevo tipo a asignar.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene el contenido de texto del mensaje.
	 * 
	 * @return el texto del mensaje.
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * Establece el contenido de texto del mensaje.
	 * 
	 * @param texto el nuevo texto a asignar.
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * Obtiene la URL pública del recurso adjunto.
	 * 
	 * @return la URL de contenido.
	 */
	public String getContenidoUrl() {
		return contenidoUrl;
	}

	/**
	 * Establece la URL pública del recurso adjunto.
	 * 
	 * @param contenidoUrl la nueva URL a asignar.
	 */
	public void setContenidoUrl(String contenidoUrl) {
		this.contenidoUrl = contenidoUrl;
	}

	/**
	 * Obtiene la fecha de emisión del mensaje.
	 * 
	 * @return la fecha del mensaje.
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha de emisión del mensaje.
	 * 
	 * @param fecha la nueva fecha a asignar.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Indica si el mensaje ha sido leído por el destinatario.
	 * 
	 * @return {@code true} si está leído, {@code false} en caso contrario.
	 */
	public boolean isLeido() {
		return leido;
	}

	/**
	 * Establece el estado de lectura del mensaje.
	 * 
	 * @param leido el nuevo estado de lectura a asignar.
	 */
	public void setLeido(boolean leido) {
		this.leido = leido;
	}
	
}
