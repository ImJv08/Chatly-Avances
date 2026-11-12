package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información 
 * detallada de los usuarios registrados en la plataforma de mensajeria.
 */
public class UsuarioDTO {
	
	/**
	 * Identificador único del usuario.
	 */
	private Long id;

	/**
	 * Primer nombre del usuario.
	 */
	private String primerNombre;

	/**
	 * Segundo nombre opcional del usuario.
	 */
	private String segundoNombre;

	/**
	 * Primer apellido del usuario.
	 */
	private String primerApellido;

	/**
	 * Segundo apellido opcional del usuario.
	 */
	private String segundoApellido;

	/**
	 * Número telefónico único asociado a la cuenta del usuario.
	 */
	private String numeroTelefonico;

	/**
	 * Fecha y hora en la que se registró el usuario en el sistema.
	 */
	private Date fechaRegistro;

    /**
     * URL pública de acceso a la foto de perfil del usuario.
     */
    private String fotoPerfilUrl;

    /**
     * Contraseña cifrada o de acceso del usuario.
     */
    private String contrasenia;
	
	/**
	 * Constructor por defecto sin argumentos para la creación de instancias vacías de {@link UsuarioDTO}.
	 */
	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con todos los campos para la inicialización completa de un objeto {@link UsuarioDTO}.
	 * 
	 * @param id identificador único del usuario.
	 * @param primerNombre primer nombre del usuario.
	 * @param segundoNombre segundo nombre del usuario.
	 * @param primerApellido primer apellido del usuario.
	 * @param segundoApellido segundo apellido del usuario.
	 * @param numeroTelefonico número telefónico del usuario.
	 * @param contrasenia contraseña de acceso.
	 * @param fechaRegistro fecha de registro en el sistema.
	 * @param fotoPerfilUrl URL de la foto de perfil.
	 */
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
	 * Retorna una representación en cadena de texto con los atributos principales del objeto {@link UsuarioDTO}.
	 * 
	 * @return una cadena formateada con los datos del usuario.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", primerNombre=" + primerNombre + ", segundoNombre=" + segundoNombre
				+ ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", numeroTelefonico="
				+ numeroTelefonico + ", fechaRegistro=" + fechaRegistro + ", fotoPerfilUrl=" + fotoPerfilUrl + "]";
	}
	
}