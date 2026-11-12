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
 * Entidad JPA que representa las notificaciones del sistema dirigidas a los usuarios 
 * en la base de datos relacional, mapeada a la tabla {@code notificaciones}.
 */
@Entity
@Table(name = "notificaciones")
public class Notificacion {

	/**
	 * Identificador único y clave primaria de la notificación.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/**
	 * Usuario receptor que recibe la notificación.
	 */
	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	/**
	 * Usuario de origen que desencadena o provoca la notificación.
	 */
	@ManyToOne
	@JoinColumn(name = "id_usuario_origen")
	private Usuario usuarioOrigen; 

	/**
	 * Indicador booleano que señala si la notificación ha sido leída (por defecto {@code false}).
	 */
	@Column(name = "leido", nullable = false)
	private boolean fueLeido = false;

	/**
	 * Tipo o categoría de la notificación (por ejemplo: mensaje, contacto, grupo).
	 */
	@Column(name = "tipo", length = 50, nullable = false)
	private String tipo;

	/**
	 * Fecha y hora exacta en la que se generó la notificación (generada automáticamente al crear).
	 */
	@Column(name = "fecha", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date fecha;

	/**
	 * Constructor vacio de {@link Notificacion}.
	 */
	public Notificacion() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link Notificacion}.
	 * 
	 * @param id identificador único de la notificación.
	 * @param usuario usuario receptor de la notificación.
	 * @param usuarioOrigen usuario que provoca la notificación.
	 * @param fueLeido estado de lectura de la notificación.
	 * @param tipo categoría o tipo de notificación.
	 * @param fecha fecha y hora de la notificación.
	 */
	public Notificacion(Long id, Usuario usuario, Usuario usuarioOrigen, boolean fueLeido, String tipo, Date fecha) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.usuarioOrigen = usuarioOrigen;
		this.fueLeido = fueLeido;
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
	 * Obtiene el usuario receptor de la notificación.
	 * 
	 * @return el usuario receptor.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Establece el usuario receptor de la notificación.
	 * 
	 * @param usuario el nuevo usuario receptor a asignar.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el usuario que provoca la notificación.
	 * 
	 * @return el usuario origen.
	 */
	public Usuario getUsuarioOrigen() {
		return usuarioOrigen;
	}

	/**
	 * Establece el usuario que provoca la notificación.
	 * 
	 * @param usuarioOrigen el nuevo usuario origen a asignar.
	 */
	public void setUsuarioOrigen(Usuario usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}

	/**
	 * Indica si la notificación ha sido leída.
	 * 
	 * @return {@code true} si está leída, {@code false} en caso contrario.
	 */
	public boolean isFueLeido() {
		return fueLeido;
	}

	/**
	 * Establece el estado de lectura de la notificación.
	 * 
	 * @param fueLeido el nuevo estado de lectura a asignar.
	 */
	public void setFueLeido(boolean fueLeido) {
		this.fueLeido = fueLeido;
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

	/**
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link Notificacion}.
	 * 
	 * @return una cadena formateada con los datos de la entidad.
	 */
	@Override
	public String toString() {
		return "Notificacion [id=" + id + ", usuario=" + usuario + ", usuarioOrigen=" + usuarioOrigen + ", fueLeido="
				+ fueLeido + ", tipo=" + tipo + ", fecha=" + fecha + "]";
	}

}