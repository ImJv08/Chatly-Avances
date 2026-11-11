package co.edu.unbosque.mensajeria.dto;

public class ContactoRespuestaDTO {

    private Long idUsuario;
    private Long idUsuarioContacto;
    private String nombrePersonalizado;
    private String telefono;
    private String urlFotoPerfil;

    public ContactoRespuestaDTO() {

    }

    public ContactoRespuestaDTO(Long idUsuario, Long idUsuarioContacto,
            String nombrePersonalizado, String telefono, String urlFotoPerfil) {


        this.idUsuario = idUsuario;
        this.idUsuarioContacto = idUsuarioContacto;
        this.nombrePersonalizado = nombrePersonalizado;
        this.telefono = telefono;
        this.urlFotoPerfil = urlFotoPerfil;
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

    public String getNombrePersonalizado() {
        return nombrePersonalizado;
    }

    public void setNombrePersonalizado(String nombrePersonalizado) {
        this.nombrePersonalizado = nombrePersonalizado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }
}