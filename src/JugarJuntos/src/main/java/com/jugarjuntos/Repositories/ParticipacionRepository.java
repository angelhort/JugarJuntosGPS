package com.jugarjuntos.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.ParticipacionId;

@Repository
public interface ParticipacionRepository extends CrudRepository<Participacion , ParticipacionId> {
	@Query(value = "SELECT * FROM participacion WHERE anuncio_id = ?1", nativeQuery = true)
	 public List<Participacion> findAllByIdAnuncio_id(long id);
}
