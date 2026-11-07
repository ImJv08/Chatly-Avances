package co.edu.unbosque.mensajeria.dto;

import java.util.Date;


public class ContactoDTO {
	
	private Long id;

	private Long idUsuario; // Dueño de la lista de contactos
	private Long idUsuarioContacto; // Persona agregada como contacto
	private Date fechaAgregado;

	public ContactoDTO() {
		// TODO Auto-generated constructor stub
	}

	public ContactoDTO(Long id, Long idUsuario, Long idUsuarioContacto, Date fechaAgregado) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.idUsuarioContacto = idUsuarioContacto;
		this.fechaAgregado = fechaAgregado;
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

	public Long getIdUsuarioContacto() {
		return idUsuarioContacto;
	}

	public void setIdUsuarioContacto(Long idUsuarioContacto) {
		this.idUsuarioContacto = idUsuarioContacto;
	}

	public Date getFechaAgregado() {
		return fechaAgregado;
	}

	public void setFechaAgregado(Date fechaAgregado) {
		this.fechaAgregado = fechaAgregado;
	}

	@Override
	public String toString() {
		return "ContactoDTO [id=" + id + ", idUsuario=" + idUsuario + ", idUsuarioContacto=" + idUsuarioContacto
				+ ", fechaAgregado=" + fechaAgregado + "]";
	}

	


}
