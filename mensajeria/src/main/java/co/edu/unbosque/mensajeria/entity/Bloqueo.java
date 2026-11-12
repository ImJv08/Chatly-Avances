package co.edu.unbosque.mensajeria.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Entidad JPA que representa la relación de bloqueo entre usuarios dentro de la base de datos relacional,
 * mapeada a la tabla {@code bloqueos}.
 */
@Entity
@Table(name = "bloqueos")
public class Bloqueo {

	/**
	 * Identificador único y clave primaria de la relación de bloqueo.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bloqueo")
	private Long idBloqueo;

	/**
	 * Usuario que realiza la acción de bloqueo.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false)
	@JsonIgnoreProperties({ "bloqueosRealizados", "bloqueosRecibidos" })
	private Usuario usuarioQueBloquea;

	/**
	 * Usuario que ha sido bloqueado.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_bloqueado", nullable = false)
	@JsonIgnoreProperties({ "bloqueosRealizados", "bloqueosRecibidos" })
	private Usuario usuarioBloqueado;

	/**
	 * Fecha y hora en la que se registró el bloqueo (generada automáticamente al crear).
	 */
	@Column(name = "fecha", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date fecha;

	/**
	 * Constructor vacio de {@link Bloqueo}.
	 */
	public Bloqueo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link Bloqueo}.
	 * 
	 * @param idBloqueo identificador único del bloqueo.
	 * @param usuarioQueBloquea usuario emisor del bloqueo.
	 * @param usuarioBloqueado usuario receptor del bloqueo.
	 * @param fecha fecha en que se registró el bloqueo.
	 */
	public Bloqueo(Long idBloqueo, Usuario usuarioQueBloquea, Usuario usuarioBloqueado, Date fecha) {
		super();
		this.idBloqueo = idBloqueo;
		this.usuarioQueBloquea = usuarioQueBloquea;
		this.usuarioBloqueado = usuarioBloqueado;
		this.fecha = fecha;
	}

	/**
	 * Obtiene el identificador único del bloqueo.
	 * 
	 * @return el ID del bloqueo.
	 */
	public Long getIdBloqueo() {
		return idBloqueo;
	}

	/**
	 * Establece el identificador único del bloqueo.
	 * 
	 * @param idBloqueo el nuevo ID a asignar.
	 */
	public void setIdBloqueo(Long idBloqueo) {
		this.idBloqueo = idBloqueo;
	}

	/**
	 * Obtiene el usuario que realiza el bloqueo.
	 * 
	 * @return el usuario bloqueador.
	 */
	public Usuario getUsuarioQueBloquea() {
		return usuarioQueBloquea;
	}

	/**
	 * Establece el usuario que realiza el bloqueo.
	 * 
	 * @param usuarioQueBloquea el nuevo usuario bloqueador a asignar.
	 */
	public void setUsuarioQueBloquea(Usuario usuarioQueBloquea) {
		this.usuarioQueBloquea = usuarioQueBloquea;
	}

	/**
	 * Obtiene el usuario que ha sido bloqueado.
	 * 
	 * @return el usuario bloqueado.
	 */
	public Usuario getUsuarioBloqueado() {
		return usuarioBloqueado;
	}

	/**
	 * Establece el usuario que ha sido bloqueado.
	 * 
	 * @param usuarioBloqueado el nuevo usuario bloqueado a asignar.
	 */
	public void setUsuarioBloqueado(Usuario usuarioBloqueado) {
		this.usuarioBloqueado = usuarioBloqueado;
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
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link Bloqueo}.
	 * 
	 * @return una cadena formateada con los datos de la entidad.
	 */
	@Override
	public String toString() {
		return "Bloqueo [idBloqueo=" + idBloqueo + ", usuarioQueBloquea=" + usuarioQueBloquea + ", usuarioBloqueado="
				+ usuarioBloqueado + ", fecha=" + fecha + "]";
	}
}
