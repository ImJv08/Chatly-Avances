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

@Entity
@Table(name = "estados")
public class Estado {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name = "tipo", length = 50)
	private String tipo; //si es imagen,video
	
	@Column(name = "contenido_url", length = 255)
    private String contenidoUrl; // url si es imagen o video
	
	@Column(name = "descripcion", length = 500)
    private String descripcion;
	
	@Column(name = "fecha_publicacion", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	@CreationTimestamp 
	private Date fechaPublicacion;
	
	@Column(name = "fecha_expiracion", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date fechaExpiracion;

	public Estado() {
		// TODO Auto-generated constructor stub
	}

	

	public Estado(Long id, Usuario usuario, String tipo, String contenidoUrl, String descripcion, Date fechaPublicacion,
			Date fechaExpiracion) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.tipo = tipo;
		this.contenidoUrl = contenidoUrl;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.fechaExpiracion = fechaExpiracion;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getContenidoUrl() {
		return contenidoUrl;
	}

	public void setContenidoUrl(String contenidoUrl) {
		this.contenidoUrl = contenidoUrl;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(Date fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}
	

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	@Override
	public String toString() {
		return "Estado [id=" + id + ", usuario=" + usuario + ", tipo=" + tipo + ", contenidoUrl=" + contenidoUrl
				+ ", descripcion=" + descripcion + ", fechaPublicacion=" + fechaPublicacion + ", fechaExpiracion="
				+ fechaExpiracion + "]";
	}

	

	
	
	
}
