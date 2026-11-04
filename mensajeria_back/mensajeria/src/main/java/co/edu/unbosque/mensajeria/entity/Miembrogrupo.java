package co.edu.unbosque.mensajeria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "	", uniqueConstraints = @UniqueConstraint(columnNames = { "id_usuario", "id_grupo" }))
public class Miembrogrupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_miembro")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "		", nullable = false)
	private Grupo grupo;

	@Column(name = "rol", nullable = false, length = 20)
	private String rol;

	public Miembrogrupo() {
		// TODO Auto-generated constructor stub
	}

	public Miembrogrupo(Long id, Usuario usuario, Grupo grupo, String rol) {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
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