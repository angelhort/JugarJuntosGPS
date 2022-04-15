package com.jugarjuntos.Controller;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jugarjuntos.Entities.Anuncio;
import com.jugarjuntos.Entities.Participacion;
import com.jugarjuntos.Entities.Usuario;
import com.jugarjuntos.Entities.UsuarioDetalles.CustomUserDetails;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAParticipacion;
import com.jugarjuntos.Transfers.TAnuncio;

@Controller
public class AdController {

	@Autowired
	SAAnuncio saAnuncio;
	
	@Autowired
	SAParticipacion saParticipacion;

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
		tAnuncio.setPersonas_actuales(1);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			tAnuncio.setId_Usuario(((CustomUserDetails) principal).getId());
		}catch(Exception e) {
			
		}
		
		System.out.println(tAnuncio.getId());
		long res = saAnuncio.altaAnuncio(tAnuncio);
		
		if (res > 0)
			redirAttrs.addFlashAttribute("success", "Anuncio dado de alta correctamente.");
		else if(res == -1) {
			redirAttrs.addFlashAttribute("error", "Error a la hora de crear el anuncio (asegúrese introducir un número valido de jugadores [2-226])");
			return "redirect:/formAnuncio";
		}
		else {
			redirAttrs.addFlashAttribute("error", "Error a la hora de crear el anuncio (asegúrese de no tener un anuncio en curso y vuelva a intentarlo)");
			return "redirect:/formAnuncio";
		}
			
		return "redirect:/";
	}
	
	@GetMapping("/getAnuncios")
	public String getAnuncios(Model model) {
		model.addAttribute("anuncios", saAnuncio.getAllAnuncios());
		return "index";
	}
	
	@GetMapping("/getAnunciosOrderByTime")
	public String getAnunciossOrderByTime(Model model) {
		model.addAttribute("anuncios", saAnuncio.getAllAnunciosOrderByTime());
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
	
	@GetMapping("/valorarJugadores")
	public String valorarJugadores(Model model, @RequestParam int id) {// a esto tiene que llamar el web socket
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long idUsuario = -1L;
		try {
			idUsuario = ((CustomUserDetails) principal).getId();
		}catch(Exception e) {
			
		}
		Anuncio anuncio = saAnuncio.getAnuncioByID(id);
		model.addAttribute("anuncio", anuncio);
		List<Usuario> jugadoresAValorar = new ArrayList<>();
		List<Long> listaParticipantesId = new ArrayList<>();
		if(anuncio.getAnunciante().getId() != idUsuario) {
			jugadoresAValorar.add(anuncio.getAnunciante());
			listaParticipantesId.add(anuncio.getAnunciante().getId());
		}
		for(Participacion a : anuncio.getParticipacion()) {
			if(a.getUsuario().getId() != idUsuario) {
				jugadoresAValorar.add(a.getUsuario());
				listaParticipantesId.add(a.getUsuario().getId());
			}
		}
		model.addAttribute("listaParticipantes", jugadoresAValorar);
		model.addAttribute("listaParticipantesId", listaParticipantesId);
		return "valorarJugadores";
	}
	

	@PostMapping("/valoracionJugadores")//esto es lo que devuelve la vista de valorar jugadores
	public String valoracionJugadores(Model model, RedirectAttributes redirAttrs, @RequestParam List<Integer> listaNumEstrellas, @RequestParam List<Long> listaNumEstrellasId) {
		
		String aux = "";
		for(Integer i :listaNumEstrellas) {
			aux += i + " ";//esto se puede borrar, era para ver si funcionaba bien
		}
		for(Long i : listaNumEstrellasId) {
			aux += i + " ";
		}
		//listaNumEstrellas es el numero de estrellas en el que a valorado el jugador
		//listaNumEstrellasId es el id de cada jugador en el mismo orden que la del anterior
		//ejempo: listaNumEstrellas.get(0) es la valoracion para el jug listaNumEstrellasId.get(0)
		
		redirAttrs.addFlashAttribute("success", aux);
		
		return "redirect:/";
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
	
	@PostMapping("/borrarAnuncio")
	public String borrarAnuncio(Model model, RedirectAttributes redirAttrs, @RequestParam int idanuncio) {
		if (saAnuncio.borrarAnuncio(idanuncio))
			redirAttrs.addFlashAttribute("success", "El anuncio se borró correctamente");
		else {
			redirAttrs.addFlashAttribute("error", "Ocurrió un error borrando el anuncio");
			redirAttrs.addAttribute("id", idanuncio);
			return "redirect:/detalles"; //Preguntar a PO
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/terminarAnuncio")
	public String terminarAnuncio(Model model, RedirectAttributes redirAttrs, @RequestParam int id) {
		if (saAnuncio.terminarAnuncio(id))
			redirAttrs.addFlashAttribute("success", "El anuncio terminó correctamente");
		else {
			redirAttrs.addFlashAttribute("error", "Ocurrió un error terminando el anuncio");
			redirAttrs.addAttribute("id", id);
			return "redirect:/detalles";
		}
		
		return "redirect:/";
	}
	
	@MessageMapping("/terminarAnuncio")
	@SendTo("/detalles") //TODO PONER LA URL DE LOS QUE ESTAN DENTRO DEL ANUNCIO
	public String redireccionValoracion(Model model, @RequestParam long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long idUsuario = -1L;
		try {
			idUsuario = ((CustomUserDetails) principal).getId();
		}catch(Exception e) {
			
		}
		if(saParticipacion.isUserInPartida(id, idUsuario)) {
			model.addAttribute("id", id);
			return "valorarJugadores";			
		}
		else return "";
	}
	
	@MessageMapping("/empezarPartida")
	@SendTo("/detalles")
	public String redireccionPartida(@RequestParam long id) {
		return "Hola";
	}
	
	@GetMapping("/pruebaSocket")
	public String prueba() {
		return "pruebaSocket";
	}
	
}
