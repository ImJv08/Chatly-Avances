package co.edu.unbosque.mensajeria.dto;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula el conteo de notificaciones 
 * no leídas asociadas a un usuario.
 */
public class ConteoNoLeidosDTO {
	
	/**
	 * Identificador único.
	 */
	private Long id; 
	
	/**
	 * Cantidad total de notificaciones no leídas.
	 */
	private long total;

	
	/**
	 * Obtiene el identificador.
	 * 
	 * @return el ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador.
	 * 
	 * @param id el nuevo ID a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene la cantidad total de notificaciones no leídas.
	 * 
	 * @return el total acumulado.
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * Establece la cantidad total de notificaciones no leídas.
	 * 
	 * @param total el nuevo valor total a asignar.
	 */
	public void setTotal(long total) {
		this.total = total;
	}
}
