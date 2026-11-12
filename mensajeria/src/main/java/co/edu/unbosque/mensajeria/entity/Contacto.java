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
import jakarta.persistence.UniqueConstraint;

/**
 * Entidad JPA que representa la agenda de contactos de los usuarios en la base de datos relacional,
 * mapeada a la tabla {@code contactos} con una restricción de unicidad para evitar duplicados 
 * entre el propietario y su contacto.
 */
@Entity
@Table(name = "contactos", uniqueConstraints = @UniqueConstraint(columnNames = { "id_usuario", "id_usuario_contacto" }))
public class Contacto {

	/**
	 * Identificador único y clave primaria del registro de contacto.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/**
	 * Usuario propietario que posee y gestiona la lista de contactos.
	 */
	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario; 

	/**
	 * Usuario que ha sido agregado como contacto en la agenda.
	 */
	@ManyToOne
	@JoinColumn(name = "id_usuario_contacto", nullable = false)
	private Usuario usuarioContacto; 

	/**
	 * Fecha y hora exacta en la que se añadió el contacto (generada automáticamente al crear).
	 */
	@Column(name = "fecha_agregado", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date fechaAgregado;
	
	/**
	 * Nombre o alias personalizado asignado por el usuario a su contacto.
	 */
	@Column(name = "nombre_personalizado", length = 100)
	private String nombrePersonalizado;

	/**
	 * Constructor vacio de {@link Contacto}.
	 */
	public Contacto() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con parámetros para la inicialización de un objeto {@link Contacto} (sin ID primario).
	 * 
	 * @param usuario usuario propietario de la agenda.
	 * @param usuarioContacto usuario que es agregado como contacto.
	 * @param fechaAgregado fecha en que se agregó el contacto.
	 * @param nombrePersonalizado nombre personalizado asignado al contacto.
	 */
	public Contacto(Usuario usuario, Usuario usuarioContacto, Date fechaAgregado, String nombrePersonalizado) {
		super();
		this.usuario = usuario;
		this.usuarioContacto = usuarioContacto;
		this.fechaAgregado = fechaAgregado;
		this.nombrePersonalizado = nombrePersonalizado;
	}

	/**
	 * Obtiene el identificador único del registro de contacto.
	 * 
	 * @return el ID del contacto.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del registro de contacto.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el usuario propietario de la agenda de contactos.
	 * 
	 * @return el usuario propietario.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Establece el usuario propietario de la agenda de contactos.
	 * 
	 * @param usuario el nuevo usuario propietario a asignar.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el usuario que figura como contacto.
	 * 
	 * @return el usuario de contacto.
	 */
	public Usuario getUsuarioContacto() {
		return usuarioContacto;
	}

	/**
	 * Establece el usuario que figura como contacto.
	 * 
	 * @param usuarioContacto el nuevo usuario de contacto a asignar.
	 */
	public void setUsuarioContacto(Usuario usuarioContacto) {
		this.usuarioContacto = usuarioContacto;
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
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link Contacto}.
	 * 
	 * @return una cadena formateada con los datos de la entidad.
	 */
	@Override
	public String toString() {
		return "Contacto [id=" + id + ", usuario=" + usuario + ", usuarioContacto=" + usuarioContacto
				+ ", fechaAgregado=" + fechaAgregado + ", nombrePersonalizado=" + nombrePersonalizado + "]";
	}

}