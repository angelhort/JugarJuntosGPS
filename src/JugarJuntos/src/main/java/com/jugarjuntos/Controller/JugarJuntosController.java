package com.jugarjuntos.Controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Repositories.UserRepository;

@Controller
public class JugarJuntosController {

	
	@Autowired 
	private EntityManager em;
	/**
	 * Web index
	 * 
	 * @Param 	model 	model attribute holder
	 * @return 	index view
	 */
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	

	@GetMapping("/test")
	@Transactional
	public void altaUsuario() {
		
	 Usuario nuevoUsuario = new Usuario();
	 nuevoUsuario.setNombre("Prueba");
	 nuevoUsuario.setCorreo("joselito@gmail.com");
	 nuevoUsuario.setPassword("Javajavita69");
	 nuevoUsuario.setEstado("En proceso");
	 nuevoUsuario.setDiscord("test");
	 em.persist(nuevoUsuario);
	 
	 Anuncio anuncio = new Anuncio();
	 anuncio.setJuego("Lol");
	 anuncio.setPersonas_actuales(1);
	 anuncio.setMax_personas(5);
	 anuncio.setEstado("Pendiente");
	 em.persist(anuncio);
	 
	  
	}
}
