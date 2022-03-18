package com.jugarjuntos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Repositories.UserRepository;

@SpringBootApplication
public class Main implements CommandLineRunner {
	
	@Autowired 
	private UserRepository repo;
	
	@Override
	 public void run(String... args){
	 
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
