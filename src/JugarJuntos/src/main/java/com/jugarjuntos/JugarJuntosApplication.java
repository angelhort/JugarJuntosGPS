package com.jugarjuntos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Repositories.UserRepository;

@SpringBootApplication
public class JugarJuntosApplication {
	
	@Autowired 
	private UserRepository repo;
	
	

	public static void main(String[] args) {
		SpringApplication.run(JugarJuntosApplication.class, args);
	}
	
	
	 public void run(String[] args) throws Exception {
	 
	        createUser();
	    }
	 
	    private void createUser() {
	        Usuario nuevoUsuario = new Usuario();
	        nuevoUsuario.setNombre("Prueba");
	        nuevoUsuario.setCorreo("joselito@gmail.com");
	        nuevoUsuario.setPassword("Javajavita69");
	        nuevoUsuario.setEstado("En proceso");
	        nuevoUsuario.setDiscord("test");
	        
	        
	         
	        repo.altaUsuario(nuevoUsuario);     
	    }
	

}
