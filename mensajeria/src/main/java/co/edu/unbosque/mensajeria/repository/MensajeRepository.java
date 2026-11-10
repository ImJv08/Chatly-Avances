package co.edu.unbosque.mensajeria.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import co.edu.unbosque.mensajeria.document.Mensaje;

public interface MensajeRepository extends MongoRepository<Mensaje, String> {

	@Query(value = "{ $or: [ { idEmisor: ?0, idReceptor: ?1 }, { idEmisor: ?1, idReceptor: ?0 } ] }", sort = "{ fecha: 1 }")
	List<Mensaje> conversacion(Long a, Long b);
	
	List<Mensaje> findByIdGrupoOrderByFechaAsc(Long idGrupo);
	
	@Query("{ idReceptor: ?0, idEmisor: ?1, leido: false }")
	@Update("{ $set: { leido: true } }")
	long marcarLeidos(Long idReceptor, Long idEmisor);
	
}
