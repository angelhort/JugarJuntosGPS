package com.jugarjuntos.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAParticipacion;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TUsuario;

@Controller
public class JugarJuntosController {

	
	@Autowired
	SAAnuncio saAnuncio;
	
	@Autowired 
	SAUsuario saUsuario;
	
	@Autowired 
	SAParticipacion saParticipacion;
	
	/**
	 * Web index
	 * 
	 * @Param 	model 	model attribute holder
	 * @return 	index view
	 */
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("anuncios", saAnuncio.getAllAnuncios());
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
	public String crearAnuncio(Model model, RedirectAttributes redirAttrs,@RequestParam String juego, @RequestParam String max_personas) {
		TAnuncio tAnuncio = new TAnuncio();
		tAnuncio.setJuego(juego);
		tAnuncio.setMax_personas(Integer.parseInt(max_personas));
		tAnuncio.setEstado("Pendiente");
		tAnuncio.setPersonas_actuales(0);
		long res = saAnuncio.altaAnuncio(tAnuncio);
		
		if (res > 0)
			redirAttrs.addFlashAttribute("success", "Anuncio dado de alta correctamente.");
		else
			redirAttrs.addFlashAttribute("error", "Error a la hora de crear el anuncio");
			
		return "redirect:/";
	}
	
	// BUSCAR ANUNCIOS POR NOMBRE DEL JUEGO
	@GetMapping("/getAnunciosPorNombre")
	public String getAnunciosPorNombre(Model model, @RequestParam String juego) {
		model.addAttribute("anunciosBusqueda", saAnuncio.getAnunciosByNombreJuego(juego));
		return "index";
	}

	@GetMapping("/getAnuncios")
	public String getAnuncios(Model model) {
		model.addAttribute("anuncios", saAnuncio.getAllAnuncios());
		return "index";
	}
	
	@GetMapping("/registro")
	public String crearFormRegistro(Model model) {
		model.addAttribute("usuario", new TUsuario());
		
		return "registro";
	}
	
	@PostMapping("/procesarAltaUsuario")
	public String crearUsuario(TUsuario usuario) {
		saUsuario.altaUsuario(usuario);
		return "redirect:/";
	}
	@GetMapping("/verSolicitudesDeAcceso")
	public String verSolicitudes(Model model, @RequestParam long id){
		model.addAttribute("solicitudes", saParticipacion.solicitudesPendientes(id));
		return "solicitudes";
	}
	

}
