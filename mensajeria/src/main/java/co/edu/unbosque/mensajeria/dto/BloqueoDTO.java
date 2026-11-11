package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

public class BloqueoDTO {

	private Long id;
	private Long idUsuario; // Quien bloquea
	private Long idUsuarioBloqueado; // Quien es bloqueado
	private String nombreBloqueado;
	private String numeroTelefonicoBloqueado;
	private String fotoPerfilUrlBloqueado;
	private Date fecha;

	public BloqueoDTO() {
	}

	public BloqueoDTO(Long id, Long idUsuario, Long idUsuarioBloqueado, String nombreBloqueado,
			String numeroTelefonicoBloqueado, String fotoPerfilUrlBloqueado, Date fecha) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.idUsuarioBloqueado = idUsuarioBloqueado;
		this.nombreBloqueado = nombreBloqueado;
		this.numeroTelefonicoBloqueado = numeroTelefonicoBloqueado;
		this.fotoPerfilUrlBloqueado = fotoPerfilUrlBloqueado;
		this.fecha = fecha;
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

	public Long getIdUsuarioBloqueado() {
		return idUsuarioBloqueado;
	}

	public void setIdUsuarioBloqueado(Long idUsuarioBloqueado) {
		this.idUsuarioBloqueado = idUsuarioBloqueado;
	}

	public String getNombreBloqueado() {
		return nombreBloqueado;
	}

	public void setNombreBloqueado(String nombreBloqueado) {
		this.nombreBloqueado = nombreBloqueado;
	}

	public String getNumeroTelefonicoBloqueado() {
		return numeroTelefonicoBloqueado;
	}

	public void setNumeroTelefonicoBloqueado(String numeroTelefonicoBloqueado) {
		this.numeroTelefonicoBloqueado = numeroTelefonicoBloqueado;
	}

	public String getFotoPerfilUrlBloqueado() {
		return fotoPerfilUrlBloqueado;
	}

	public void setFotoPerfilUrlBloqueado(String fotoPerfilUrlBloqueado) {
		this.fotoPerfilUrlBloqueado = fotoPerfilUrlBloqueado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "BloqueoDTO [id=" + id + ", idUsuario=" + idUsuario + ", idUsuarioBloqueado=" + idUsuarioBloqueado
				+ ", nombreBloqueado=" + nombreBloqueado + ", fecha=" + fecha + "]";
	}

}