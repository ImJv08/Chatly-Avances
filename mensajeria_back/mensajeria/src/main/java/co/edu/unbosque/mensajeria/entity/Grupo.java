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
@Table(name = "grupos")
public class Grupo {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	@ManyToOne
	@JoinColumn(name = "id_creador", nullable = false)
	private Usuario creador;

	@Column(name = "nombre", nullable = false)
	private String nombreGrupo;

	@Column(name = "foto_perfil_url", length = 255)
	private String fotoPerfilUrl;

	@Column(name = "fecha_registro", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date fechaCreacion;

	public Grupo() {
		// TODO Auto-generated constructor stub
	}

	public Grupo(Long id, Usuario creador, String nombreGrupo, String fotoPerfilUrl, Date fechaCreacion) {
		super();
		this.id = id;
		this.creador = creador;
		this.nombreGrupo = nombreGrupo;
		this.fotoPerfilUrl = fotoPerfilUrl;
		this.fechaCreacion = fechaCreacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getFotoPerfilUrl() {
		return fotoPerfilUrl;
	}

	public void setFotoPerfilUrl(String fotoPerfilUrl) {
		this.fotoPerfilUrl = fotoPerfilUrl;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "Grupo [id=" + id + ", creador=" + creador + ", nombreGrupo=" + nombreGrupo + ", fotoPerfilUrl="
				+ fotoPerfilUrl + ", fechaCreacion=" + fechaCreacion + "]";
	}
	
	

}
