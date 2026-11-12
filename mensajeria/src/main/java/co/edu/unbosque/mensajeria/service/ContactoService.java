package co.edu.unbosque.mensajeria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ContactoDTO;
import co.edu.unbosque.mensajeria.dto.ContactoRespuestaDTO;
import co.edu.unbosque.mensajeria.repository.ContactoRepository;
import jakarta.transaction.Transactional;

/**
 * Servicio de lógica de negocio para la gestión de la agenda de contactos de los usuarios,
 * encargado de procesar la creación, eliminación, actualización de alias y búsquedas personalizadas
 * mediante la interacción con el repositorio de contactos.
 */
@Service
public class ContactoService {
	
	/**
	 * Repositorio para la persistencia, procedimientos y consultas de contactos.
	 */
	private final ContactoRepository contactoRepository;
	
	/**
	 * Constructor para la inyección de dependencias del repositorio de contactos.
	 * 
	 * @param contactoRepository repositorio de contactos.
	 */
	public ContactoService(ContactoRepository contactoRepository) {
		this.contactoRepository = contactoRepository;
	}
	
	/**
	 * Registra un nuevo contacto del usuario utilizando un objeto de transferencia de datos.
	 * 
	 * @param contactoDTO objeto con los datos del usuario propietario, usuario contacto y nombre personalizado.
	 */
	public void crearContacto(ContactoDTO contactoDTO) {
		contactoRepository.crearContacto(contactoDTO.getIdUsuario(), contactoDTO.getIdUsuarioContacto(), contactoDTO.getNombrePersonalizado());
	}
	
	/**
	 * Elimina un contacto del usuario utilizando los identificadores proporcionados.
	 * 
	 * @param contactoDTO objeto con los datos del usuario propietario y el contacto a eliminar.
	 */
	public void eliminarContacto(ContactoDTO contactoDTO) {
		contactoRepository.eliminarContacto(contactoDTO.getIdUsuario(), contactoDTO.getIdUsuarioContacto());
	}

	/**
	 * Lista todos los contactos asociados a un usuario específico.
	 * 
	 * @param idUsuario identificador del usuario propietario de la agenda.
	 * @return una lista de objetos {@link ContactoRespuestaDTO} con los detalles de los contactos.
	 */
	@Transactional
	public List<ContactoRespuestaDTO> listarContactosPorUsuario(Long idUsuario) {
		return contactoRepository.listarContactosPorUsuario(idUsuario);
	}
	
	/**
	 * Actualiza el nombre o alias personalizado asignado a un contacto existente de un usuario.
	 * 
	 * @param idUsuario identificador del usuario propietario.
	 * @param idUsuarioContacto identificador del usuario de contacto cuyo nombre se actualizará.
	 * @param nombre nuevo nombre o alias personalizado a asignar.
	 * @throws RuntimeException si no se encuentra el registro del contacto entre ambos usuarios.
	 */
	@Transactional
	public void actualizarNombreContacto(Long idUsuario, Long idUsuarioContacto, String nombre) {
	    Long idContacto = contactoRepository.obtenerIdContacto(idUsuario, idUsuarioContacto);

	    System.out.println("ID de contacto encontrado: " + idContacto);

	    if (idContacto == null) {
	        throw new RuntimeException("No se encontró el contacto");
	    }

	    contactoRepository.actualizarNombreContacto(idContacto, nombre);

	    System.out.println("Nombre actualizado correctamente");
	}
	
	/**
	 * Busca contactos de un usuario coincidiendo con un texto de búsqueda por nombre o teléfono.
	 * 
	 * @param idUsuario identificador del usuario propietario de la agenda.
	 * @param texto cadena de texto o filtro de búsqueda.
	 * @return una lista de {@link ContactoRespuestaDTO} que coinciden con el criterio.
	 */
	public List<ContactoRespuestaDTO> buscarContactosPorNombreOTelefono(Long idUsuario, String texto) {
	    return contactoRepository.buscarContactosPorUsuario(idUsuario, texto);
	}
	
	/**
	 * Agrega un contacto del usuario de forma automática si aún no existe, sin un nombre personalizado.
	 * 
	 * @param idUsuario identificador del usuario propietario.
	 * @param idContacto identificador del usuario que se añadirá.
	 */
	public void agregarSiNoExiste(Long idUsuario, Long idContacto) {
	    contactoRepository.crearContacto(idUsuario, idContacto, null);
	}
}
