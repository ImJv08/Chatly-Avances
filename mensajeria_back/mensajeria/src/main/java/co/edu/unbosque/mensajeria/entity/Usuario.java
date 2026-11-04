package co.edu.unbosque.mensajeria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	@Column(name = "primer_nombre", nullable = false, length = 50)
	private String primerNombre;
	@Column(name = "segundo_nombre", length = 50)
	private String segundoNombre;

	@Column(name = "primer_apellido", nullable = false, length = 50)
	private String primerApellido;
	@Column(name = "segundo_apellido", length = 50)
	private String segundoApellido;
	@Column(name = "numero_telefonico", nullable = false,  length = 50)
	private String numeroTelefonico;
	@Column(name = "fecha_registro", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	@CreationTimestamp 
	private Date fechaRegistro;
	@Column(name = "foto_perfil_url", length = 255)
    private String fotoPerfilUrl;
	@Column(name = "contrasenia", nullable = false, length = 250)
	private String contrasenia;
	

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(Long id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
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
