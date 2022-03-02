package com.demoTest.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demoTest.Entities.User;

@Repository
public interface DemoRepository extends CrudRepository<User, String>{
	public List<User> findAll();
}
