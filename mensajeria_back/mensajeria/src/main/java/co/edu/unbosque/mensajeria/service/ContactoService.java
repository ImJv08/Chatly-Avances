package co.edu.unbosque.mensajeria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ContactoDTO;
import co.edu.unbosque.mensajeria.entity.Contacto;
import co.edu.unbosque.mensajeria.repository.ContactoRepository;
import jakarta.transaction.Transactional;
@Service
public class ContactoService {
	
	@Autowired
	private final ContactoRepository contactoRepository;
	
	public ContactoService(ContactoRepository contactoRepository) {
		this.contactoRepository = contactoRepository;
	}
	
	public void crearContacto(ContactoDTO contactoDTO) {
		contactoRepository.crearContacto(contactoDTO.getIdUsuario(), contactoDTO.getIdUsuarioContacto());
	}
	
	public void eliminarContacto(ContactoDTO contactoDTO) {
		contactoRepository.eliminarContacto(contactoDTO.getIdUsuario(), contactoDTO.getIdUsuarioContacto());
	}

	@Transactional
	public List<Contacto> listarContactosPorUsuario(Long idUsuario) {
		return contactoRepository.listarContactosPorUsuario(idUsuario);
	}
}
