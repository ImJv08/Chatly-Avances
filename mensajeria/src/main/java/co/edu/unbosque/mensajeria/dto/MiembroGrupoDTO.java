package co.edu.unbosque.mensajeria.dto;

public class MiembroGrupoDTO {

	private Long id;

	private Long usuario;

	private Long grupo;

	private String rol;

	public MiembroGrupoDTO() {
		// TODO Auto-generated constructor stub
	}

	public MiembroGrupoDTO(Long id, Long usuario, Long grupo, String rol) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.grupo = grupo;
		this.rol = rol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Long getGrupo() {
		return grupo;
	}

	public void setGrupo(Long grupo) {
		this.grupo = grupo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

@Override
public String toString() {
	return "Miembrogrupo [id=" + id + ", usuario=" + usuario + ", grupo=" + grupo + ", rol=" + rol + "]";
}
}
