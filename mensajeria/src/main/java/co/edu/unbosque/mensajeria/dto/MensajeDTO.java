package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

public class MensajeDTO {

	private Long id;
	private Long idEmisor;
	private Long idReceptor;
	private Long idGrupo;
	private String tipo;
	private String texto;
	private String contenidoUrl;
	private Date fecha;
	private boolean leido;

	public MensajeDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEmisor() {
		return idEmisor;
	}

	public void setIdEmisor(Long idEmisor) {
		this.idEmisor = idEmisor;
	}

	public Long getIdReceptor() {
		return idReceptor;
	}

	public void setIdReceptor(Long idReceptor) {
		this.idReceptor = idReceptor;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getContenidoUrl() {
		return contenidoUrl;
	}

	public void setContenidoUrl(String contenidoUrl) {
		this.contenidoUrl = contenidoUrl;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	
}
