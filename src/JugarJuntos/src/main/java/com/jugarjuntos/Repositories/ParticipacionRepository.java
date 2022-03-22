package com.jugarjuntos.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.ParticipacionId;

public interface ParticipacionRepository extends CrudRepository<Participacion , ParticipacionId> {
	 public List<Participacion> findAllByIdAnuncio_id(long id);
}
