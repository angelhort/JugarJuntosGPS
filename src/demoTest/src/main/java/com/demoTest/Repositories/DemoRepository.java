package com.demoTest.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demoTest.Entities.User;

// CRUDREPOSITORY --> User es la entidad asociada a esa tabla
//				  --> String es el tipo de la clave de la entidad
@Repository
public interface DemoRepository extends CrudRepository<User, String>{
	public List<User> findAll();
}
