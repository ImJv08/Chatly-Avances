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

@Entity
@Table(name = "notificaciones")
public class Notificacion {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;// Quien recibe la notificación

	@ManyToOne
	@JoinColumn(name = "id_usuario_origen")
	private Usuario usuarioOrigen; // Quien provoca la notificación

	@Column(name = "leido", nullable = false)
	private boolean fueLeido = false;

	@Column(name = "tipo", length = 50, nullable = false)
	private String tipo;

	@Column(name = "fecha", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date fecha;

	public Notificacion() {
		// TODO Auto-generated constructor stub
	}

	public Notificacion(Long id, Usuario usuario, Usuario usuarioOrigen, boolean fueLeido, String tipo, Date fecha) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.usuarioOrigen = usuarioOrigen;
		this.fueLeido = fueLeido;
		this.tipo = tipo;
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioOrigen() {
		return usuarioOrigen;
	}

	public void setUsuarioOrigen(Usuario usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}

	public boolean isFueLeido() {
		return fueLeido;
	}

	public void setFueLeido(boolean fueLeido) {
		this.fueLeido = fueLeido;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Notificacion [id=" + id + ", usuario=" + usuario + ", usuarioOrigen=" + usuarioOrigen + ", fueLeido="
				+ fueLeido + ", tipo=" + tipo + ", fecha=" + fecha + "]";
	}

}
