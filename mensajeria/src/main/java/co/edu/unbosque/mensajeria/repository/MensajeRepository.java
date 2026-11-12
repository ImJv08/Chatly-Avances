package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import co.edu.unbosque.mensajeria.document.Mensaje;
import co.edu.unbosque.mensajeria.dto.ConteoNoLeidosDTO;

/**
 * Repositorio de MongoDB para el documento {@link Mensaje}, que gestiona la persistencia
 * y las consultas especializadas de chat (conversaciones privadas, grupales, conteo de no leídos y operaciones de actualización/eliminación)
 * en la base de datos NoSQL.
 */
public interface MensajeRepository extends MongoRepository<Mensaje, String> {

	/**
	 * Busca y retorna la conversación completa entre dos usuarios (privada), ordenada cronológicamente por fecha ascendente.
	 * 
	 * @param a identificador del primer usuario (emisor).
	 * @param b identificador del segundo usuario (receptor).
	 * @return una lista de documentos {@link Mensaje} que conforman la conversación.
	 */
	@Query(value = "{ $or: [ { idEmisor: ?0, idReceptor: ?1 }, { idEmisor: ?1, idReceptor: ?0 } ] }", sort = "{ fecha: 1 }")
	public List<Mensaje> conversacion(Long a, Long b);
	
	/**
	 * Busca y lista todos los mensajes pertenecientes a un grupo específico, ordenados por fecha de forma ascendente.
	 * 
	 * @param idGrupo identificador único del grupo de chat.
	 * @return una lista de documentos {@link Mensaje} del grupo.
	 */
	public List<Mensaje> findByIdGrupoOrderByFechaAsc(Long idGrupo);
	
	/**
	 * Actualiza el estado de lectura de los mensajes enviados por un emisor específico a un receptor,
	 * marcándolos como leídos.
	 * 
	 * @param idReceptor identificador del usuario receptor que marca los mensajes como leídos.
	 * @param idEmisor identificador del usuario emisor cuyos mensajes se actualizarán.
	 * @return la cantidad de documentos modificados.
	 */
	@Query("{ idReceptor: ?0, idEmisor: ?1, leido: false }")
	@Update("{ $set: { leido: true } }")
	public long marcarLeidos(Long idReceptor, Long idEmisor);
	
	/**
	 * Cuenta la cantidad de mensajes no leídos agrupados por emisor para un receptor específico,
	 * utilizando un pipeline de agregación en MongoDB.
	 * 
	 * @param idReceptor identificador del usuario receptor de los mensajes.
	 * @return una lista de objetos {@link ConteoNoLeidosDTO} con el total de mensajes no leídos por cada emisor.
	 */
	@Aggregation(pipeline = {
		    "{ $match: { idReceptor: ?0, leido: false } }",
		    "{ $group: { _id: '$idEmisor', total: { $sum: 1 } } }"
		})
		List<ConteoNoLeidosDTO> contarNoLeidos(Long idReceptor);
	
	@Query(value = "{ _id: ?0, idEmisor: ?1 }", delete = true)
	public long eliminarMensaje(String idMensaje, Long idEmisor);
	
}
	