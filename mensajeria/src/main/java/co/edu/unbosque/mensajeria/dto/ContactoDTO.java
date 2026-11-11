package co.edu.unbosque.mensajeria.dto;

import java.util.Date;


public class ContactoDTO {
	
	private Long id;

	private Long idUsuario; 
	private Long idUsuarioContacto; 
	private Date fechaAgregado;
	private String nombrePersonalizado;

	public ContactoDTO() {
		// TODO Auto-generated constructor stub
	}

	
	public ContactoDTO(Long idUsuario, Long idUsuarioContacto, Date fechaAgregado, String nombrePersonalizado) {
		super();
		this.idUsuario = idUsuario;
		this.idUsuarioContacto = idUsuarioContacto;
		this.fechaAgregado = fechaAgregado;
		this.nombrePersonalizado = nombrePersonalizado;
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


	public String getNombrePersonalizado() {
		return nombrePersonalizado;
	}


	public void setNombrePersonalizado(String nombrePersonalizado) {
		this.nombrePersonalizado = nombrePersonalizado;
	}


	@Override
	public String toString() {
		return "ContactoDTO [id=" + id + ", idUsuario=" + idUsuario + ", idUsuarioContacto=" + idUsuarioContacto
				+ ", fechaAgregado=" + fechaAgregado + ", nombrePersonalizado=" + nombrePersonalizado + "]";
	}

	
	


}
