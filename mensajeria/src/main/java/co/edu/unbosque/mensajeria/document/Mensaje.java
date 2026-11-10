package co.edu.unbosque.mensajeria.document;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Document(collection = "Mensaje")
public class Mensaje {
	
	@Id private String id;
	
	private Long idEmisor;
	private Long idReceptor;
	private Long idGrupo;
	
	private String tipo;
	private String texto;
	private String contenidoUrl;
	private String nombreArchivo;
	
	private Date fecha = new Date();
	private boolean leido = false;
	
	public Mensaje() {
		// TODO Auto-generated constructor stub
	}

	public Mensaje(String id, Long idEmisor, Long idReceptor, Long idGrupo, String tipo, String texto,
			String contenidoUrl, String nombreArchivo, Date fecha, boolean leido) {
		super();
		this.id = id;
		this.idEmisor = idEmisor;
		this.idReceptor = idReceptor;
		this.idGrupo = idGrupo;
		this.tipo = tipo;
		this.texto = texto;
		this.contenidoUrl = contenidoUrl;
		this.nombreArchivo = nombreArchivo;
		this.fecha = fecha;
		this.leido = leido;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
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
