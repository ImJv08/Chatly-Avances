package co.edu.unbosque.mensajeria.dto;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información 
 * de los miembros dentro de los grupos, incluyendo su rol asignado.
 */
public class MiembroGrupoDTO {

	/**
	 * Identificador único del miembro.
	 */
	private Long id;

	/**
	 * Identificador único del usuario miembro del grupo.
	 */
	private Long usuario;

	/**
	 * Identificador único del grupo al cual pertenece el usuario.
	 */
	private Long grupo;

	/**
	 * Rol del usuario dentro del grupo (ADMINISTRADOR, MIEMBRO).
	 */
	private String rol;

	/**
	 * Constructor vacio de {@link MiembroGrupoDTO}.
	 */
	public MiembroGrupoDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link MiembroGrupoDTO}.
	 * 
	 * @param id identificador único del miembro.
	 * @param usuario identificador del usuario.
	 * @param grupo identificador del grupo.
	 * @param rol rol asignado al usuario dentro del grupo.
	 */
	public MiembroGrupoDTO(Long id, Long usuario, Long grupo, String rol) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.grupo = grupo;
		this.rol = rol;
	}

	/**
	 * Obtiene el identificador único del miembro.
	 * 
	 * @return el ID de la membresía.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del miembro.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario.
	 * 
	 * @return el ID del usuario.
	 */
	public Long getUsuario() {
		return usuario;
	}

	/**
	 * Establece el identificador del usuario.
	 * 
	 * @param usuario el nuevo ID de usuario a asignar.
	 */
	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el identificador del grupo.
	 * 
	 * @return el ID del grupo.
	 */
	public Long getGrupo() {
		return grupo;
	}

	/**
	 * Establece el identificador del grupo.
	 * 
	 * @param grupo el nuevo ID de grupo a asignar.
	 */
	public void setGrupo(Long grupo) {
		this.grupo = grupo;
	}

	/**
	 * Obtiene el rol del usuario en el grupo.
	 * 
	 * @return el rol asignado.
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Establece el rol del usuario en el grupo.
	 * 
	 * @param rol el nuevo rol a asignar.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link MiembroGrupoDTO}.
	 * 
	 * @return una cadena formateada con los datos de la membresía.
	 */
	@Override
	public String toString() {
		return "Miembrogrupo [id=" + id + ", usuario=" + usuario + ", grupo=" + grupo + ", rol=" + rol + "]";
	}
}
