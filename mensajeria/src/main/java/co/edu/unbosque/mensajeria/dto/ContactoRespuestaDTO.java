package co.edu.unbosque.mensajeria.dto;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información de respuesta 
 * detallada sobre los contactos de un usuario, incluyendo datos de perfil y números telefónicos.
 */
public class ContactoRespuestaDTO {

	/**
     * Identificador único del usuario propietario de la agenda.
     */
    private Long idUsuario;
    
    /**
     * Identificador único del usuario que figura como contacto.
     */
    private Long idUsuarioContacto;
    
    /**
     * Nombre o alias personalizado asignado por el usuario al contacto.
     */
    private String nombrePersonalizado;
    
    /**
     * Número telefónico asociado al contacto.
     */
    private String telefono;
    
    /**
     * URL pública de la foto de perfil del usuario de contacto.
     */
    private String urlFotoPerfil;

    /**
     * Constructor vacio de {@link ContactoRespuestaDTO}.
     */
    public ContactoRespuestaDTO() {

    }

    /**
     * Constructor con todos los campos para la inicialización completa de un objeto {@link ContactoRespuestaDTO}.
     * 
     * @param idUsuario identificador del usuario propietario de la agenda.
     * @param idUsuarioContacto identificador del usuario de contacto.
     * @param nombrePersonalizado nombre personalizado asignado al contacto.
     * @param telefono número telefónico del contacto.
     * @param urlFotoPerfil URL de la foto de perfil del contacto.
     */
    public ContactoRespuestaDTO(Long idUsuario, Long idUsuarioContacto,
            String nombrePersonalizado, String telefono, String urlFotoPerfil) {


        this.idUsuario = idUsuario;
        this.idUsuarioContacto = idUsuarioContacto;
        this.nombrePersonalizado = nombrePersonalizado;
        this.telefono = telefono;
        this.urlFotoPerfil = urlFotoPerfil;
    }

    /**
     * Obtiene el identificador del usuario propietario de la agenda.
     * 
     * @return el ID del usuario propietario.
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador del usuario propietario de la agenda.
     * 
     * @param idUsuario el nuevo ID de usuario propietario a asignar.
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el identificador del usuario de contacto.
     * 
     * @return el ID del usuario de contacto.
     */
    public Long getIdUsuarioContacto() {
        return idUsuarioContacto;
    }

    /**
     * Establece el identificador del usuario de contacto.
     * 
     * @param idUsuarioContacto el nuevo ID de usuario de contacto a asignar.
     */
    public void setIdUsuarioContacto(Long idUsuarioContacto) {
        this.idUsuarioContacto = idUsuarioContacto;
    }

    /**
     * Obtiene el nombre personalizado asignado al contacto.
     * 
     * @return el nombre personalizado.
     */
    public String getNombrePersonalizado() {
        return nombrePersonalizado;
    }

    /**
     * Establece el nombre personalizado asignado al contacto.
     * 
     * @param nombrePersonalizado el nuevo nombre personalizado a asignar.
     */
    public void setNombrePersonalizado(String nombrePersonalizado) {
        this.nombrePersonalizado = nombrePersonalizado;
    }

    /**
     * Obtiene el número telefónico del contacto.
     * 
     * @return el número telefónico.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número telefónico del contacto.
     * 
     * @param telefono el nuevo número telefónico a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la URL de la foto de perfil del contacto.
     * 
     * @return la URL de la foto de perfil.
     */
    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    /**
     * Establece la URL de la foto de perfil del contacto.
     * 
     * @param urlFotoPerfil la nueva URL de foto a asignar.
     */
    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }
}