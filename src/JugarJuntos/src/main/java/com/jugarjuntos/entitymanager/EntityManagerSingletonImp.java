package com.jugarjuntos.entitymanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityManagerSingletonImp extends EntityManagerSingleton{
	
	@Autowired
	@PersistenceContext
	private EntityManagerFactory ef;
	
	
	public EntityManager getEntityManager() {

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        return em;
	}
	

}
