package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

import co.edu.unbosque.mensajeria.entity.Usuario;

public class GrupoDTO {
	
	private Long id;

	private Long idCreador;
	
	private String nombreGrupo;

	private String fotoPerfilUrl;

	private Date fechaCreacion;

	public GrupoDTO() {
		// TODO Auto-generated constructor stub
	}

	public GrupoDTO(Long id, Long idCreador, String nombreGrupo, String fotoPerfilUrl, Date fechaCreacion) {
		super();
		this.id = id;
		this.idCreador = idCreador;
		this.nombreGrupo = nombreGrupo;
		this.fotoPerfilUrl = fotoPerfilUrl;
		this.fechaCreacion = fechaCreacion;
	}



	public Long getIdCreador() {
		return idCreador;
	}



	public void setIdCreador(Long idCreador) {
		this.idCreador = idCreador;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "GrupoDTO [id=" + id + ", idCreador=" + idCreador + ", nombreGrupo=" + nombreGrupo + ", fotoPerfilUrl="
				+ fotoPerfilUrl + ", fechaCreacion=" + fechaCreacion + "]";
	}

	
}
