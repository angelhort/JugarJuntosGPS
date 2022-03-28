package com.jugarjuntos.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.UsuarioDetalles.CustomUserDetails;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.Transfers.TAnuncio;

@Controller
public class AdController {

	@Autowired
	SAAnuncio saAnuncio;

	@GetMapping("/formAnuncio")
	public String crearForm(Model model) {
		return "crearAnuncio.html";
	}
	
	@PostMapping("/formAnuncio")
	public String crearAnuncio(Model model, RedirectAttributes redirAttrs, @RequestParam String juego, @RequestParam String max_personas) {
		TAnuncio tAnuncio = new TAnuncio();
		tAnuncio.setJuego(juego);
		tAnuncio.setMax_personas(Integer.parseInt(max_personas));
		tAnuncio.setEstado("Pendiente");
		tAnuncio.setPersonas_actuales(0);
		tAnuncio.setId_Usuario(-1L);
		long res = saAnuncio.altaAnuncio(tAnuncio);
		
		if (res > 0)
			redirAttrs.addFlashAttribute("success", "Anuncio dado de alta correctamente.");
		else
			redirAttrs.addFlashAttribute("error", "Error a la hora de crear el anuncio");
			
		return "redirect:/";
	}
	
	@GetMapping("/getAnuncios")
	public String getAnuncios(Model model) {
		model.addAttribute("anuncios", saAnuncio.getAllAnuncios());
		return "index";
	}
	
	@GetMapping("/detalles")
	public String detalles(Model model, @RequestParam int id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long idUsuario = -1L;
		try {
			idUsuario = ((CustomUserDetails) principal).getId();
		}catch(Exception e) {
			
		}
		
		model.addAttribute("idUsuario", idUsuario);
		Anuncio anuncio = saAnuncio.getAnuncioByID(id);
		model.addAttribute("anuncio", anuncio);
		List<Long> participantes = new ArrayList<>();
		for(Participacion a : anuncio.getParticipacion()) {
			participantes.add(a.getUsuario().getId());
		}
		model.addAttribute("listaParticipantes", participantes);
		return "detallesAnuncio.html";
	}

	@GetMapping("/getAnunciosPorNombre")
	public String getAnunciosPorNombre(Model model, @RequestParam String juego) {
		model.addAttribute("anuncios", saAnuncio.getAnunciosByNombreJuego(juego));
		return "index";
	}

	@GetMapping("/lobbyAnuncio")
	public String irALobby(Model model) {
		
		return "lobbyAnuncio";
	}
}
