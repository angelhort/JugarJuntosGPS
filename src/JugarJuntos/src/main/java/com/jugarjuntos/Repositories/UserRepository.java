package com.jugarjuntos.Repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jugarjuntos.Entities.Usuario;

@Repository
public class UserRepository {
 
    @Autowired
    private EntityManager entityManager;
    
    
    @Transactional
    public void altaUsuario(Usuario usuario) {
        entityManager.persist(usuario);
    
        
    }
     
}