package com.jugarjuntos.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.Transfers.TAnuncio;

@Controller
public class JugarJuntosController {

	
	@Autowired
	SAAnuncio saAnuncio;

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
	
	/**
	 * New add view
	 * 
	 * @Param 	model 	model attribute holder
	 * @return 	new add view
	 */
	@GetMapping("/formAnuncio")
	public String crearForm(Model model) {
		return "crearAnuncio.html";
	}
	
	@PostMapping("/procesarAlta")
	public String crearAnuncio(Model model, @RequestParam String juego, @RequestParam String max_personas) {
		TAnuncio tAnuncio = new TAnuncio();
		tAnuncio.setJuego(juego);
		tAnuncio.setMax_personas(Integer.parseInt(max_personas));
		tAnuncio.setEstado("Pendiente");
		tAnuncio.setPersonas_actuales(0);
		saAnuncio.altaAnuncio(tAnuncio);
		
		return "index";
	}
	
	// BUSCAR ANUNCIOS POR NOMBRE DEL JUEGO
	@GetMapping("/getAnunciosPorNombre")
	public String getAnunciosPorNombre(Model model, @RequestParam String juego) {
		model.addAllAttributes(saAnuncio.getAnunciosByNombreJuego(juego));
		return "listarAnuncios";
	}

	@GetMapping("/getAnuncios")
	public String getAnuncios(Model model) {
		model.addAllAttributes(saAnuncio.getAllAnuncios());
		return "index";
	}
	

}
