package com.jugarjuntos.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.ParticipacionId;

@Repository
public interface ParticipacionRepository extends CrudRepository<Participacion , ParticipacionId> {
	@Query(value = "SELECT * FROM participacion WHERE anuncio_id = ?1 AND estado_solicitud = 'pendiente'", nativeQuery = true)
	 public List<Participacion> findAllByIdAnuncio_idPendientes(long id);
	
	@Query(value = "SELECT * FROM participacion WHERE anuncio_id = ?1 AND usuario_id = ?2", nativeQuery = true)
	 public List<Participacion> findParticipacionById(long anuncioId, long usuarioId);

	@Query(value = "SELECT * FROM participacion WHERE usuario_id = ?1", nativeQuery = true)
	public Participacion findParticipacionByIdUsuario(long usuarioId);
	
	@Modifying
	@Query(value = "INSERT INTO participacion VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
	@Transactional
	public void aniadirSolicitud(String string, String string2, long l, long m);
	
	@Query(value = "DELETE from participacion WHERE usuario_id = ?1 AND anuncio_id = ?2", nativeQuery = true)
	public void eliminarUsuarioParticipacion(long usuarioId , long anuncioId);
	
	@Query(value = "UPDATE participacion SET estado_solicitud = 'aceptado' WHERE anuncio_id = ?1 AND usuario_id = ?2", nativeQuery = true)
	public void cambiarEstadoAceptado(long anuncio_id , long usuario_id);
}
