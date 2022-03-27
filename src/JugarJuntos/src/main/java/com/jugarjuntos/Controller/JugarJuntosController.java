package com.jugarjuntos.Controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAParticipacion;
import com.jugarjuntos.Transfers.TAnuncio;
import com.jugarjuntos.Transfers.TParticipacion;

@Controller
public class JugarJuntosController {

	@Autowired
	SAAnuncio saAnuncio;
	
	@Autowired 
	SAParticipacion saParticipacion;
	
	/**
	 * Web index
	 * 
	 * @Param 	model 	model attribute holder
	 * @return 	index view
	 */
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
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
	
	@PostMapping("/formAnuncio")
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
	
	@GetMapping("/verSolicitudesDeAcceso")
	public String verSolicitudes(Model model, @RequestParam long id){
		model.addAttribute("solicitudes", saParticipacion.solicitudesPendientes(id));
		return "solicitudes";
	}
	
	@GetMapping("/detalles")
	public String detalles(Model model, @RequestParam int id) {
		model.addAttribute("anuncio", saAnuncio.getAnuncioByID(id));
		return "detallesAnuncio.html";
	}
	
	@PostMapping("/enviarSolicitud")
	public String enviarSolicitud(RedirectAttributes redirAttrs, Model model,
			@RequestParam long id_usuario, @RequestParam long id_anuncio, @RequestParam String estado) {
		TParticipacion participacion= new TParticipacion(id_usuario, id_anuncio, estado);
		try {
			saParticipacion.enviarSolicitud(participacion);
		} catch (BusinessException e) {
			model.addAttribute("exception", e.toString());
			return "detallesAnuncio";
		}
		return "detallesAnuncio";
	}
	
	@PostMapping("/aceptarSolicitud")
	public String aceptarSolicitud(Model model, @RequestParam TParticipacion participacion) {
		try {
			saParticipacion.aceptarSolicitud(participacion);
		} catch (BusinessException e) {
			model.addAttribute("excepcion", e.toString());
			return "index";
		}
		//TODO cambiar la pagina devuelta a la que querais recibir en el frontend
		return "index";
	}
	
	@PostMapping("/rechazarSolicitud")
	public String rechazarSolicitud(Model model,@RequestParam TParticipacion participacion) {
		try {
			saParticipacion.rechazarSolicitud(participacion);
		} catch (BusinessException e) {
			model.addAttribute("excepcion", e.toString());
			return "index";
		}
		return "index";
	}
	
	@GetMapping("/lobbyAnuncio")
	public String irALobby(Model model) {
		
		return "lobbyAnuncio";
	}
	
	

}
