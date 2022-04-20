package com.jugarjuntos.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jugarjuntos.Entities.Anuncio;

@Repository
public interface AnuncioRepository extends CrudRepository<Anuncio, Long>{
	@Query(value = "SELECT * FROM anuncio WHERE juego LIKE %?1% and estado = 'pendiente'", nativeQuery = true)
	public List<Anuncio> findAllByJuego(String juego);
	
	@Query(value = "SELECT * FROM anuncio WHERE estado = 'pendiente'", nativeQuery = true)
	public List<Anuncio> findAllPend();
	
	public List<Anuncio> findAll();
	
	
	public Anuncio findById(long id);
	
	@Query(value = "UPDATE anuncio SET personas_actuales = personas_actuales + 1 WHERE id = ?1", nativeQuery = true)
	public void incrementPersonasActuales(long id);
	
	@Query(value = "SELECT * FROM anuncio WHERE anunciante_id = ?1", nativeQuery = true)
	public List<Anuncio> findAllByAnunciante(long id_usr);
	
	@Query(value = "SELECT * FROM anuncio WHERE anunciante_id = ?1 and estado = 'pendiente'", nativeQuery = true)
	public List<Anuncio> findAllByAnunciantePend(long id_usr);
}
