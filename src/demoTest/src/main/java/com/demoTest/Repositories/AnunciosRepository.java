package com.demoTest.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demoTest.Entities.Anuncio;

@Repository
public interface AnunciosRepository extends CrudRepository<Anuncio, String>{
	public List<Anuncio> findAll();
}
