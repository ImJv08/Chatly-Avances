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

/**
 * Entidad JPA que representa a los usuarios registrados en la base de datos relacional,
 * mapeada a la tabla {@code usuarios}.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {
	
	/**
	 * Identificador único y clave primaria del usuario.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/**
	 * Primer nombre del usuario.
	 */
	@Column(name = "primer_nombre", nullable = false, length = 50)
	private String primerNombre;

	/**
	 * Segundo nombre opcional del usuario.
	 */
	@Column(name = "segundo_nombre", length = 50)
	private String segundoNombre;

	/**
	 * Primer apellido del usuario.
	 */
	@Column(name = "primer_apellido", nullable = false, length = 50)
	private String primerApellido;

	/**
	 * Segundo apellido opcional del usuario.
	 */
	@Column(name = "segundo_apellido", length = 50)
	private String segundoApellido;

	/**
	 * Número telefónico único asociado a la cuenta del usuario.
	 */
	@Column(name = "numero_telefonico", nullable = false,  length = 50)
	private String numeroTelefonico;

	/**
	 * Fecha y hora exacta en la que se registró el usuario (generada automáticamente al crear).
	 */
	@Column(name = "fecha_registro", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	@CreationTimestamp 
	private Date fechaRegistro;

	/**
	 * URL de acceso público a la foto de perfil del usuario.
	 */
	@Column(name = "foto_perfil_url", length = 255)
    private String fotoPerfilUrl;

	/**
	 * Contraseña cifrada de acceso del usuario.
	 */
	@Column(name = "contrasenia", nullable = false, length = 250)
	private String contrasenia;
	
	/**
	 * Constructor vacio de {@link Usuario}.
	 */
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link Usuario}.
	 * 
	 * @param id identificador único del usuario.
	 * @param primerNombre primer nombre del usuario.
	 * @param segundoNombre segundo nombre del usuario.
	 * @param primerApellido primer apellido del usuario.
	 * @param segundoApellido segundo apellido del usuario.
	 * @param numeroTelefonico número telefónico del usuario.
	 * @param contrasenia contraseña de acceso cifrada.
	 * @param fechaRegistro fecha de registro en el sistema.
	 * @param fotoPerfilUrl URL de la foto de perfil.
	 */
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

	/**
	 * Obtiene el identificador único del usuario.
	 * 
	 * @return el ID del usuario.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del usuario.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el primer nombre del usuario.
	 * 
	 * @return el primer nombre.
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * Establece el primer nombre del usuario.
	 * 
	 * @param primerNombre el nuevo primer nombre a asignar.
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * Obtiene el segundo nombre del usuario.
	 * 
	 * @return el segundo nombre.
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * Establece el segundo nombre del usuario.
	 * 
	 * @param segundoNombre el nuevo segundo nombre a asignar.
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * Obtiene el primer apellido del usuario.
	 * 
	 * @return el primer apellido.
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Establece el primer apellido del usuario.
	 * 
	 * @param primerApellido el nuevo primer apellido a asignar.
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * Obtiene el segundo apellido del usuario.
	 * 
	 * @return el segundo apellido.
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Establece el segundo apellido del usuario.
	 * 
	 * @param segundoApellido el nuevo segundo apellido a asignar.
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * Obtiene el número telefónico del usuario.
	 * 
	 * @return el número telefónico.
	 */
	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}

	/**
	 * Establece el número telefónico del usuario.
	 * 
	 * @param numeroTelefonico el nuevo número telefónico a asignar.
	 */
	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	/**
	 * Obtiene la contraseña del usuario.
	 * 
	 * @return la contraseña.
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Establece la contraseña del usuario.
	 * 
	 * @param contrasenia la nueva contraseña a asignar.
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	/**
	 * Obtiene la fecha de registro del usuario.
	 * 
	 * @return la fecha de registro.
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Establece la fecha de registro del usuario.
	 * 
	 * @param fechaRegistro la nueva fecha de registro a asignar.
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * Obtiene la URL de la foto de perfil del usuario.
	 * 
	 * @return la URL de la foto de perfil.
	 */
	public String getFotoPerfilUrl() {
		return fotoPerfilUrl;
	}

	/**
	 * Establece la URL de la foto de perfil del usuario.
	 * 
	 * @param fotoPerfilUrl la nueva URL de foto a asignar.
	 */
	public void setFotoPerfilUrl(String fotoPerfilUrl) {
		this.fotoPerfilUrl = fotoPerfilUrl;
	}

	/**
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link Usuario}.
	 * 
	 * @return una cadena formateada con los datos de la entidad.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", primerNombre=" + primerNombre + ", segundoNombre=" + segundoNombre
				+ ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", numeroTelefonico="
				+ numeroTelefonico + ", fechaRegistro=" + fechaRegistro + ", fotoPerfilUrl=" + fotoPerfilUrl + "]";
	}

}