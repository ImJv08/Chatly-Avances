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
 * Entidad JPA que representa los grupos de chat en la base de datos relacional,
 * mapeada a la tabla {@code grupos}.
 */
@Entity
@Table(name = "grupos")
public class Grupo {

	/**
	 * Identificador único y clave primaria del grupo.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/**
	 * Usuario creador y administrador inicial del grupo (relación muchos a uno).
	 */
	@ManyToOne
	@JoinColumn(name = "id_creador", nullable = false)
	private Usuario creador;

	/**
	 * Nombre o título asignado al grupo.
	 */
	@Column(name = "nombre", nullable = false)
	private String nombreGrupo;

	/**
	 * URL de acceso público a la foto de perfil o icono representativo del grupo.
	 */
	@Column(name = "foto_perfil_url", length = 255)
	private String fotoPerfilUrl;

	/**
	 * Fecha y hora exacta en la que se creó el grupo (generada automáticamente al registrar).
	 */
	@Column(name = "fecha_registro", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date fechaCreacion;

	/**
	 * Constructor vacio de {@link Grupo}.
	 */
	public Grupo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link Grupo}.
	 * 
	 * @param id identificador único del grupo.
	 * @param creador usuario creador del grupo.
	 * @param nombreGrupo nombre del grupo.
	 * @param fotoPerfilUrl URL de la foto de perfil del grupo.
	 * @param fechaCreacion fecha de creación del grupo.
	 */
	public Grupo(Long id, Usuario creador, String nombreGrupo, String fotoPerfilUrl, Date fechaCreacion) {
		super();
		this.id = id;
		this.creador = creador;
		this.nombreGrupo = nombreGrupo;
		this.fotoPerfilUrl = fotoPerfilUrl;
		this.fechaCreacion = fechaCreacion;
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
	 * Obtiene el usuario creador del grupo.
	 * 
	 * @return el usuario creador.
	 */
	public Usuario getCreador() {
		return creador;
	}

	/**
	 * Establece el usuario creador del grupo.
	 * 
	 * @param creador el nuevo usuario creador a asignar.
	 */
	public void setCreador(Usuario creador) {
		this.creador = creador;
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
	 * @param nombreGrupo el nuevo nombre a asignar.
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
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link Grupo}.
	 * 
	 * @return una cadena formateada con los datos de la entidad.
	 */
	@Override
	public String toString() {
		return "Grupo [id=" + id + ", creador=" + creador + ", nombreGrupo=" + nombreGrupo + ", fotoPerfilUrl="
				+ fotoPerfilUrl + ", fechaCreacion=" + fechaCreacion + "]";
	}
}