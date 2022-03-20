package com.jugarjuntos.entitymanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;

public class EntityManagerSingletonImp extends EntityManagerSingleton{
	
	@Autowired
	private EntityManagerFactory ef;
	
	
	public EntityManager getEntityManager() {
		return ef.createEntityManager();
	}
	

}
