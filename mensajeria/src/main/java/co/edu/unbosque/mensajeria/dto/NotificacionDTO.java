package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

public class NotificacionDTO {

	 private Long id;
	 private Long usuario;
	 private Long usuarioOrigen;
	 private boolean fueLeido;
	 private String tipo;
	 private Date fecha;
	 
	 public NotificacionDTO() {
		// TODO Auto-generated constructor stub
	}

	 public NotificacionDTO(Long id, Long idUsuario, Long idUsuarioOrigen, boolean leido, String tipo, Date fecha) {
		super();
		this.id = id;
		this.usuario = idUsuario;
		this.usuarioOrigen = idUsuarioOrigen;
		this.fueLeido = leido;
		this.tipo = tipo;
		this.fecha = fecha;
	 }

	 public Long getId() {
		 return id;
	 }

	 public void setId(Long id) {
		 this.id = id;
	 }

	 public Long getIdUsuario() {
		 return usuario;
	 }

	 public void setIdUsuario(Long idUsuario) {
		 this.usuario = idUsuario;
	 }

	 public Long getIdUsuarioOrigen() {
		 return usuarioOrigen;
	 }

	 public void setIdUsuarioOrigen(Long idUsuarioOrigen) {
		 this.usuarioOrigen = idUsuarioOrigen;
	 }

	 public boolean isLeido() {
		 return fueLeido;
	 }

	 public void setLeido(boolean leido) {
		 this.fueLeido = leido;
	 }

	 public String getTipo() {
		 return tipo;
	 }

	 public void setTipo(String tipo) {
		 this.tipo = tipo;
	 }

	 public Date getFecha() {
		 return fecha;
	 }

	 public void setFecha(Date fecha) {
		 this.fecha = fecha;
	 } 
	 
	 
	 
	 
}
