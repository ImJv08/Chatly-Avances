
package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import co.edu.unbosque.mensajeria.entity.Usuario;


public class EstadoDTO {
	
	private Long id;

	
	private Long idUsuario;
	private String tipo; //si es imagen,video
    private String contenidoUrl; // url si es imagen o video
    private String descripcion;
	private Date fechaPublicacion;
	private Date fechaExpiracion;

	public EstadoDTO() {
		// TODO Auto-generated constructor stub
	}


	public EstadoDTO(Long id, Long idUsuario, String tipo, String contenidoUrl, String descripcion,
			Date fechaPublicacion, Date fechaExpiracion) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
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

	public Long getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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
		return "Estado [id=" + id + ", usuario=" + idUsuario + ", tipo=" + tipo + ", contenidoUrl=" + contenidoUrl
				+ ", descripcion=" + descripcion + ", fechaPublicacion=" + fechaPublicacion + ", fechaExpiracion="
				+ fechaExpiracion + "]";
	}

}
