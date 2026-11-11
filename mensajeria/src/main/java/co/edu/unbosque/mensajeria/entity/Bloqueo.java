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

@Entity
@Table(name = "bloqueos")
public class Bloqueo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bloqueo")
	private Long idBloqueo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false)
	@JsonIgnoreProperties({ "bloqueosRealizados", "bloqueosRecibidos" })
	private Usuario usuarioQueBloquea;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_bloqueado", nullable = false)
	@JsonIgnoreProperties({ "bloqueosRealizados", "bloqueosRecibidos" })
	private Usuario usuarioBloqueado;

	@Column(name = "fecha", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date fecha;

	public Bloqueo() {
		// TODO Auto-generated constructor stub
	}

	
	public Bloqueo(Long idBloqueo, Usuario usuarioQueBloquea, Usuario usuarioBloqueado, Date fecha) {
		super();
		this.idBloqueo = idBloqueo;
		this.usuarioQueBloquea = usuarioQueBloquea;
		this.usuarioBloqueado = usuarioBloqueado;
		this.fecha = fecha;
	}


	public Long getIdBloqueo() {
		return idBloqueo;
	}

	public void setIdBloqueo(Long idBloqueo) {
		this.idBloqueo = idBloqueo;
	}

	public Usuario getUsuarioQueBloquea() {
		return usuarioQueBloquea;
	}

	public void setUsuarioQueBloquea(Usuario usuarioQueBloquea) {
		this.usuarioQueBloquea = usuarioQueBloquea;
	}

	public Usuario getUsuarioBloqueado() {
		return usuarioBloqueado;
	}

	public void setUsuarioBloqueado(Usuario usuarioBloqueado) {
		this.usuarioBloqueado = usuarioBloqueado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	@Override
	public String toString() {
		return "Bloqueo [idBloqueo=" + idBloqueo + ", usuarioQueBloquea=" + usuarioQueBloquea + ", usuarioBloqueado="
				+ usuarioBloqueado + ", fecha=" + fecha + "]";
	}

	
}
