package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información 
 * general de los grupos de chat en la plataforma, incluyendo datos de creación y multimedia.
 */
public class GrupoDTO {
	
	/**
	 * Identificador único del grupo.
	 */
	private Long id;

	/**
	 * Identificador único del usuario creador y administrador inicial del grupo.
	 */
	private Long idCreador;
	
	/**
	 * Nombre o título asignado al grupo de chat.
	 */
	private String nombreGrupo;

	/**
	 * URL pública de acceso a la foto de perfil o icono representativo del grupo.
	 */
	private String fotoPerfilUrl;

	/**
	 * Fecha y hora exacta en la que se creó y registró el grupo en el sistema.
	 */
	private Date fechaCreacion;

	/**
	 * Constructor vacio de {@link GrupoDTO}.
	 */
	public GrupoDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link GrupoDTO}.
	 * 
	 * @param id identificador único del grupo.
	 * @param idCreador identificador del usuario creador del grupo.
	 * @param nombreGrupo nombre o título del grupo.
	 * @param fotoPerfilUrl URL de la foto de perfil del grupo.
	 * @param fechaCreacion fecha en que se creó el grupo.
	 */
	public GrupoDTO(Long id, Long idCreador, String nombreGrupo, String fotoPerfilUrl, Date fechaCreacion) {
		super();
		this.id = id;
		this.idCreador = idCreador;
		this.nombreGrupo = nombreGrupo;
		this.fotoPerfilUrl = fotoPerfilUrl;
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Obtiene el identificador del usuario creador del grupo.
	 * 
	 * @return el ID del creador.
	 */
	public Long getIdCreador() {
		return idCreador;
	}

	/**
	 * Establece el identificador del usuario creador del grupo.
	 * 
	 * @param idCreador el nuevo ID de creador a asignar.
	 */
	public void setIdCreador(Long idCreador) {
		this.idCreador = idCreador;
	}

	/**
	 * Obtiene el identificador único del grupo.
	 * 
	 * @return el ID del grupo.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del grupo.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del grupo.
	 * 
	 * @return el nombre del grupo.
	 */
	public String getNombreGrupo() {
		return nombreGrupo;
	}

	/**
	 * Establece el nombre del grupo.
	 * 
	 * @param nombreGrupo el nuevo nombre de grupo a asignar.
	 */
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	/**
	 * Obtiene la URL de la foto de perfil del grupo.
	 * 
	 * @return la URL de la foto de perfil.
	 */
	public String getFotoPerfilUrl() {
		return fotoPerfilUrl;
	}

	/**
	 * Establece la URL de la foto de perfil del grupo.
	 * 
	 * @param fotoPerfilUrl la nueva URL de foto a asignar.
	 */
	public void setFotoPerfilUrl(String fotoPerfilUrl) {
		this.fotoPerfilUrl = fotoPerfilUrl;
	}

	/**
	 * Obtiene la fecha de creación del grupo.
	 * 
	 * @return la fecha de creación.
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Establece la fecha de creación del grupo.
	 * 
	 * @param fechaCreacion la nueva fecha de creación a asignar.
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link GrupoDTO}.
	 * 
	 * @return una cadena formateada con los datos del grupo.
	 */
	@Override
	public String toString() {
		return "GrupoDTO [id=" + id + ", idCreador=" + idCreador + ", nombreGrupo=" + nombreGrupo + ", fotoPerfilUrl="
				+ fotoPerfilUrl + ", fechaCreacion=" + fechaCreacion + "]";
	}
}
