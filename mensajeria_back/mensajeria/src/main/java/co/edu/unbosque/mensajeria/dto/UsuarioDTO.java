package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

public class UsuarioDTO {
	private Long id;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String numeroTelefonico;
	private Date fechaRegistro;
    private String fotoPerfilUrl;
    private String contrasenia;
	
	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioDTO(Long id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
			String numeroTelefonico, String contrasenia, Date fechaRegistro, String fotoPerfilUrl) {
		super();
		this.id = id;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.numeroTelefonico = numeroTelefonico;
		this.fechaRegistro = fechaRegistro;
		this.fotoPerfilUrl = fotoPerfilUrl;
		this.contrasenia = contrasenia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}

	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}
	

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFotoPerfilUrl() {
		return fotoPerfilUrl;
	}

	public void setFotoPerfilUrl(String fotoPerfilUrl) {
		this.fotoPerfilUrl = fotoPerfilUrl;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", primerNombre=" + primerNombre + ", segundoNombre=" + segundoNombre
				+ ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", numeroTelefonico="
				+ numeroTelefonico + ", fechaRegistro=" + fechaRegistro + ", fotoPerfilUrl=" + fotoPerfilUrl + "]";
	}
	

}
