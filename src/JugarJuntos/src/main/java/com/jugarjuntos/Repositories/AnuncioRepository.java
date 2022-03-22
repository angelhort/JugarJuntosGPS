package com.jugarjuntos.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jugarjuntos.Entities.Anuncio;

@Repository
public interface AnuncioRepository extends CrudRepository<Anuncio, Long>{
	@Query(value = "SELECT * FROM anuncio WHERE juego LIKE %?1%", nativeQuery = true)
	public List<Anuncio> findAllByJuego(String juego);
	public List<Anuncio> findAll();
}
