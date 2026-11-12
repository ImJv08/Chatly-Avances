package co.edu.unbosque.mensajeria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Entidad JPA que representa los miembros dentro de los grupos de chat 
 * en la base de datos relacional, mapeada a la tabla {@code miembros_grupo} con una 
 * restricción de unicidad para evitar duplicados del mismo usuario en un mismo grupo.
 */
@Entity
@Table(name = "miembros_grupo", uniqueConstraints = @UniqueConstraint(columnNames = { "id_usuario", "id_grupo" }))
public class Miembrogrupo {

	/**
	 * Identificador único del miembro.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_miembro")
	private Long id;

	/**
	 * Usuario asociado a la membresía del grupo.
	 */
	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	/**
	 * Grupo al cual pertenece el usuario.
	 */
	@ManyToOne
	@JoinColumn(name = "id_grupo", nullable = false)
	private Grupo grupo;

	/**
	 * Rol asignado al usuario dentro del grupo (ADMINISTRADOR, MIEMBRO).
	 */
	@Column(name = "rol", nullable = false, length = 20)
	private String rol;

	/**
	 * Constructor vacio de {@link Miembrogrupo}.
	 */
	public Miembrogrupo() {
		
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link Miembrogrupo}.
	 * 
	 * @param id identificador único de la membresía.
	 * @param usuario usuario miembro del grupo.
	 * @param grupo grupo al que se asocia el usuario.
	 * @param rol rol o privilegios del usuario en el grupo.
	 */
	public Miembrogrupo(Long id, Usuario usuario, Grupo grupo, String rol) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.grupo = grupo;
		this.rol = rol;
	}

	/**
	 * Obtiene el identificador del miembro.
	 * 
	 * @return el ID del miembro.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador del miembro.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el usuario miembro del grupo.
	 * 
	 * @return el usuario miembro del grupo.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Establece el usuario miembro del grupo.
	 * 
	 * @param usuario el nuevo usuario a asignar.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el grupo al cual pertenece el usuario.
	 * 
	 * @return el grupo asociado.
	 */
	public Grupo getGrupo() {
		return grupo;
	}

	/**
	 * Establece el grupo al cual pertenece el usuario.
	 * 
	 * @param grupo el nuevo grupo a asignar.
	 */
	public void setGrupo(Grupo grupo) {
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
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link Miembrogrupo}.
	 * 
	 * @return una cadena formateada con los datos de la entidad.
	 */
	@Override
	public String toString() {
		return "Miembrogrupo [id=" + id + ", usuario=" + usuario + ", grupo=" + grupo + ", rol=" + rol + "]";
	}

}