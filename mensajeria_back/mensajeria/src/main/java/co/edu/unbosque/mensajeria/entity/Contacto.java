package co.edu.unbosque.mensajeria.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

@Entity
@Table(name = "contactos", uniqueConstraints = @UniqueConstraint(columnNames = { "id_usuario", "id_usuario_contacto" }))
public class Contacto {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario; // Dueño de la lista de contactos

	@ManyToOne
	@JoinColumn(name = "id_usuario_contacto", nullable = false)
	private Usuario usuarioContacto; // Persona agregada como contacto

	@Column(name = "fecha_agregado", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date fechaAgregado;

	public Contacto() {
		// TODO Auto-generated constructor stub
	}

	public Contacto(Long id, Usuario usuario, Usuario usuarioContacto, Date fechaAgregado) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.usuarioContacto = usuarioContacto;
		this.fechaAgregado = fechaAgregado;
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

	public Usuario getUsuarioContacto() {
		return usuarioContacto;
	}

	public void setUsuarioContacto(Usuario usuarioContacto) {
		this.usuarioContacto = usuarioContacto;
	}

	public Date getFechaAgregado() {
		return fechaAgregado;
	}

	public void setFechaAgregado(Date fechaAgregado) {
		this.fechaAgregado = fechaAgregado;
	}

	@Override
	public String toString() {
		return "Contacto [id=" + id + ", usuario=" + usuario + ", usuarioContacto=" + usuarioContacto
				+ ", fechaAgregado=" + fechaAgregado + "]";
	}

}
