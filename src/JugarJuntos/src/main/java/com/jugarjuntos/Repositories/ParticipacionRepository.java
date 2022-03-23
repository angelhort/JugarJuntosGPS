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
	
	@Query(value = "SELECT * FROM participacion WHERE anuncio_id = ?1 AND usuario_id = ?2", nativeQuery = true)
	 public Participacion findParticipacionById(long anuncioId, long usuarioId);
	
	//SI DESDE EL FRONTEND QUEREIS QUE EL ESTADO NO SE LLAME ACEPTADO CAMBIADLO
	@Query(value = "UPDATE participacion SET estado_partida = 'aceptado' WHERE anuncio_id = ?1 AND usuario_id = ?2", nativeQuery = true)
	public void changeEstadoSolicitudAprobada(long anuncioId, long usuarioId);
	
	@Query(value = "UPDATE participacion SET estado_partida = 'denegado' WHERE anuncio_id = ?1 AND usuario_id = ?2", nativeQuery = true)
	public void changeEstadoSolicitudDenegada(long id_anuncio, long id_usuario);
}
