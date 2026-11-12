package co.edu.unbosque.mensajeria.document;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

/**
 * Representa la entidad de documento mapeada en la base de datos NoSQL MongoDB 
 * para gestionar el almacenamiento y la estructura de los mensajes de texto, multimedia y archivos.
 */
@Document(collection = "Mensaje")
public class Mensaje {
	
	/**
	 * Identificador único del mensaje en MongoDB.
	 */
	@Id private String id;
	
	/**
	 * Identificador único del usuario emisor que envía el mensaje.
	 */
	private Long idEmisor;
	
	/**
	 * Identificador del usuario receptor (en caso de chat privado).
	 */
	private Long idReceptor;
	
	/**
	 * Identificador del grupo (en caso de mensajes dirigidos a chats grupales).
	 */
	private Long idGrupo;
	
	/**
	 * Tipo de contenido del mensaje (por ejemplo: TEXTO, IMAGEN, ARCHIVO).
	 */
	private String tipo;
	
	/**
	 * Contenido de texto principal del mensaje.
	 */
	private String texto;
	
	/**
	 * URL de acceso público al recurso multimedia o archivo adjunto asociado al mensaje.
	 */
	private String contenidoUrl;
	
	/**
	 * Nombre original o generado del archivo adjunto transmitido en el mensaje.
	 */
	private String nombreArchivo;
	
	/**
	 * Fecha y hora exacta en la que se generó y envió el mensaje. Por defecto se inicializa con la fecha actual.
	 */
	private Date fecha = new Date();
	
	/**
	 * Indicador booleano que señala si el mensaje ya ha sido leído por el destinatario.
	 */
	private boolean leido = false;
	
	/**
	 * Indicador booleano que señala si el contenido del mensaje ha sido modificado con posterioridad.
	 */
	private boolean editado = false;
	
	/**
	 * Constructor por defecto sin argumentos para la creación de instancias vacías de {@link Mensaje}.
	 */
	public Mensaje() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link Mensaje}.
	 * 
	 * @param id identificador único del mensaje.
	 * @param idEmisor identificador del usuario que envía el mensaje.
	 * @param idReceptor identificador del usuario receptor.
	 * @param idGrupo identificador del grupo de destino.
	 * @param tipo tipo de contenido del mensaje.
	 * @param texto texto descriptivo o contenido del mensaje.
	 * @param contenidoUrl URL pública del archivo o recurso adjunto.
	 * @param nombreArchivo nombre del archivo adjunto.
	 * @param fecha fecha y hora de emisión del mensaje.
	 * @param leido estado de lectura del mensaje.
	 * @param editado indicador de si el mensaje ha sido editado.
	 */
	public Mensaje(String id, Long idEmisor, Long idReceptor, Long idGrupo, String tipo, String texto,
			String contenidoUrl, String nombreArchivo, Date fecha, boolean leido, boolean editado) {
		super();
		this.id = id;
		this.idEmisor = idEmisor;
		this.idReceptor = idReceptor;
		this.idGrupo = idGrupo;
		this.tipo = tipo;
		this.texto = texto;
		this.contenidoUrl = contenidoUrl;
		this.nombreArchivo = nombreArchivo;
		this.fecha = fecha;
		this.leido = leido;
		this.editado = editado;
	}

	
	/**
	 * Obtiene el identificador único del mensaje.
	 * 
	 * @return el ID del mensaje.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador único del mensaje.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(String id) {
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
	 * @param idEmisor el nuevo ID del emisor a asignar.
	 */
	public void setIdEmisor(Long idEmisor) {
		this.idEmisor = idEmisor;
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
	 * @param idReceptor el nuevo ID del receptor a asignar.
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
	 * @param idGrupo el nuevo ID del grupo a asignar.
	 */
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * Obtiene el tipo de mensaje.
	 * 
	 * @return el tipo de contenido.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de mensaje.
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
	 * Obtiene la fecha y hora de envío del mensaje.
	 * 
	 * @return la fecha del mensaje.
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha y hora de envío del mensaje.
	 * 
	 * @param fecha la nueva fecha a asignar.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Indica si el mensaje ya ha sido leído.
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
	
	/**
	 * Indica si el mensaje ha sido editado.
	 * 
	 * @return {@code true} si ha sido editado, {@code false} en caso contrario.
	 */
	public boolean isEditado() { 
		return editado; 
	}
	
	/**
	 * Establece el indicador de edición del mensaje.
	 * 
	 * @param editado el nuevo valor booleano de edición a asignar.
	 */
	public void setEditado(boolean editado) { 
		this.editado = editado; 
	}
	
}
