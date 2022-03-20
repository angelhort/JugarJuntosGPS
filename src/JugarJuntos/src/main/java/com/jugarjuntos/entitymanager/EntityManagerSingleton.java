package com.jugarjuntos.entitymanager;

import javax.persistence.EntityManager;

public abstract class EntityManagerSingleton {
	
	private static EntityManagerSingletonImp em;
	
	public static EntityManagerSingleton getInstance() {
		if( em == null) em =new EntityManagerSingletonImp();
		return em;
	}
	
	public abstract EntityManager getEntityManager(); 

}
