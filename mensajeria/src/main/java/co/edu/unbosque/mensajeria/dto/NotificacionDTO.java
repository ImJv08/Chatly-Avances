package co.edu.unbosque.mensajeria.dto;

import java.util.Date;

public class NotificacionDTO {

	 private Long id;
	 private Long idUsuario;
	 private Long idUsuarioOrigen;
	 private boolean leido;
	 private String tipo;
	 private Date fecha;
	 
	 public NotificacionDTO() {
		// TODO Auto-generated constructor stub
	}

	 public NotificacionDTO(Long id, Long idUsuario, Long idUsuarioOrigen, boolean leido, String tipo, Date fecha) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.idUsuarioOrigen = idUsuarioOrigen;
		this.leido = leido;
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
		 return idUsuario;
	 }

	 public void setIdUsuario(Long idUsuario) {
		 this.idUsuario = idUsuario;
	 }

	 public Long getIdUsuarioOrigen() {
		 return idUsuarioOrigen;
	 }

	 public void setIdUsuarioOrigen(Long idUsuarioOrigen) {
		 this.idUsuarioOrigen = idUsuarioOrigen;
	 }

	 public boolean isLeido() {
		 return leido;
	 }

	 public void setLeido(boolean leido) {
		 this.leido = leido;
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
