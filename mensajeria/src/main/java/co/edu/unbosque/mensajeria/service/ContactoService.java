package co.edu.unbosque.mensajeria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ContactoDTO;
import co.edu.unbosque.mensajeria.dto.ContactoRespuestaDTO;
import co.edu.unbosque.mensajeria.entity.Contacto;
import co.edu.unbosque.mensajeria.repository.ContactoRepository;
import jakarta.transaction.Transactional;
@Service
public class ContactoService {
	
	private final ContactoRepository contactoRepository;
	
	public ContactoService(ContactoRepository contactoRepository) {
		this.contactoRepository = contactoRepository;
	}
	
	public void crearContacto(ContactoDTO contactoDTO) {
		contactoRepository.crearContacto(contactoDTO.getIdUsuario(), contactoDTO.getIdUsuarioContacto(), contactoDTO.getNombrePersonalizado());
	}
	
	public void eliminarContacto(ContactoDTO contactoDTO) {
		contactoRepository.eliminarContacto(contactoDTO.getIdUsuario(), contactoDTO.getIdUsuarioContacto());
	}

	@Transactional
	public List<ContactoRespuestaDTO> listarContactosPorUsuario(Long idUsuario) {
		return contactoRepository.listarContactosPorUsuario(idUsuario);
	}
	
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
	
	public List<ContactoRespuestaDTO> buscarContactosPorNombreOTelefono(Long idUsuario, String texto) {
	    return contactoRepository.buscarContactosPorUsuario(idUsuario, texto);
	}
	
	public void agregarSiNoExiste(Long idUsuario, Long idContacto) {
	    contactoRepository.crearContacto(idUsuario, idContacto, null);
	}
}
