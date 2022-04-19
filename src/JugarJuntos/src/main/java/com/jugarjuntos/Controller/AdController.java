package com.jugarjuntos.Controller;

import java.util.ArrayList;
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
import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAParticipacion;
import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TAnuncio;

@Controller
public class AdController {

	@Autowired
	SAAnuncio saAnuncio;

	@Autowired
	SAUsuario saUsuario;

	@Autowired
	SAParticipacion saParticipacion;

	@GetMapping("/formAnuncio")
	public String crearForm(Model model) {
		return "crearAnuncio.html";
	}

	@PostMapping("/formAnuncio")
	public String crearAnuncio(Model model, RedirectAttributes redirAttrs, @RequestParam String juego,
			@RequestParam String max_personas) {
		TAnuncio tAnuncio = new TAnuncio();
		tAnuncio.setJuego(juego);
		tAnuncio.setMax_personas(Integer.parseInt(max_personas));
		tAnuncio.setEstado("pendiente");
		tAnuncio.setPersonas_actuales(1);

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		tAnuncio.setId_Usuario(((CustomUserDetails) principal).getId());

		long res = saAnuncio.altaAnuncio(tAnuncio);

		if (res > 0)
			redirAttrs.addFlashAttribute("success", "Anuncio dado de alta correctamente.");
		else if (res == -1) {
			redirAttrs.addFlashAttribute("error",
					"Error a la hora de crear el anuncio (asegúrese introducir un número valido de jugadores [2-226])");
			return "redirect:/formAnuncio";
		} else if (res == -3) {
			redirAttrs.addFlashAttribute("error",
					"El nombre del juego no es válido. Introduzca un número entre 1-150 caracteres");
			return "redirect:/formAnuncio";
		} else {
			redirAttrs.addFlashAttribute("error",
					"Error a la hora de crear el anuncio (asegúrese de no tener un anuncio en curso y vuelva a intentarlo)");
			return "redirect:/formAnuncio";
		}

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
		String media = "0";
		Integer cont = 0;

		try {
			idUsuario = ((CustomUserDetails) principal).getId();
			model.addAttribute("idUsuario", idUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Anuncio anuncio = saAnuncio.getAnuncioByID(id);
		model.addAttribute("anuncio", anuncio);

		List<Object> mediaYCont;
		try {
			mediaYCont = saUsuario.calcularMedia(anuncio.getAnunciante().getId());
			media = (String) mediaYCont.get(0);
			cont = (Integer) mediaYCont.get(1);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		model.addAttribute("media", media);
		model.addAttribute("contValor", cont);
		model.addAttribute("idUsuario", idUsuario);

		String estadoUsuario = null;
		for (Participacion p : anuncio.getParticipacion())
			if (p.getUsuario().getId() == idUsuario)
				estadoUsuario = p.getEstado_solicitud();

		model.addAttribute("estadoUsuario", estadoUsuario);

		if (saAnuncio.UsuarioEnAnuncio(anuncio.getId(), idUsuario) && saAnuncio.checkEmpezado(anuncio.getId())) {
			// redirAttrs.addFlashAttribute("success", "La partida ha dado comienzo.
			// Asegúrate de ponerte en contacto con el resto de jugadores por Discord para
			// obtener una mejor experiencia.");
			return "detallesEnPartida.html";
		}

		// Incluir IF para redireccionar a valorar jugadores en caso de haber acabado la
		// partida, no haber valorado a los jugadores previamente y haber participado en
		// esa partida.

		return "detallesAnuncio.html";
	}

	@GetMapping("/verUsuario")
	public String verUsuario(Model model, @RequestParam int id) {
		Usuario usuario = saUsuario.getUsuarioByID(id);
		model.addAttribute("usuario", usuario);
		model.addAttribute("anuncios", saAnuncio.findAnunciosByAnunciante(id));
		try {
			model.addAttribute("media", saUsuario.calcularMedia(id).get(0));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return "detallesUsuario.html";
	}

	@GetMapping("/valorarJugadores")
	public String valorarJugadores(Model model, @RequestParam int id) {// a esto tiene que llamar el web socket
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long idUsuario = -1L;
		try {
			idUsuario = ((CustomUserDetails) principal).getId();
		} catch (Exception e) {

		}
		Anuncio anuncio = saAnuncio.getAnuncioByID(id);
		model.addAttribute("anuncio", anuncio);
		List<Usuario> jugadoresAValorar = new ArrayList<>();
		List<Long> listaParticipantesId = new ArrayList<>();
		if (anuncio.getAnunciante().getId() != idUsuario) {
			jugadoresAValorar.add(anuncio.getAnunciante());
			listaParticipantesId.add(anuncio.getAnunciante().getId());
		}
		for (Participacion a : anuncio.getParticipacion()) {
			if (a.getUsuario().getId() != idUsuario) {
				jugadoresAValorar.add(a.getUsuario());
				listaParticipantesId.add(a.getUsuario().getId());
			}
		}
		model.addAttribute("listaParticipantes", jugadoresAValorar);
		model.addAttribute("listaParticipantesId", listaParticipantesId);
		return "valorarJugadores";
	}

	@PostMapping("/valoracionJugadores") // esto es lo que devuelve la vista de valorar jugadores
	public String valoracionJugadores(Model model, RedirectAttributes redirAttrs,
			@RequestParam List<Integer> listaNumEstrellas, @RequestParam List<Long> listaNumEstrellasId) {

		// listaNumEstrellas es el numero de estrellas en el que a valorado el jugador
		// listaNumEstrellasId es el id de cada jugador en el mismo orden que la del
		// anterior
		// ejempo: listaNumEstrellas.get(0) es la valoracion para el jug
		// listaNumEstrellasId.get(0)

		if (saAnuncio.valorarJugadores(listaNumEstrellas, listaNumEstrellasId))
			redirAttrs.addFlashAttribute("success", "Gracias por valorar a los jugadores, nos vemos en la próxima");
		else
			redirAttrs.addFlashAttribute("success", "Error en la valoración");

		return "redirect:/";
	}

	@GetMapping("/getAnunciosPorNombre")
	public String getAnunciosPorNombre(Model model, @RequestParam String juego, @RequestParam String orden) {
		List<Anuncio> lista = null;

		switch (orden) {
			case "recientes":
				lista = saAnuncio.getAllAnunciosOrderByTime(juego);
				break;
			case "antiguos":
				lista = saAnuncio.getAnunciosByNombreJuego(juego);
				break;
			case "valorados":
				lista = saAnuncio.getAllAnunciosOrderByValoracion(juego);
				break;
			default:
				lista = saAnuncio.getAnunciosByNombreJuego(juego);
				break;
		}
		
		model.addAttribute("anuncios", lista);
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
			return "redirect:/detalles"; // Preguntar a PO
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
	  model.addAttribute("id", id); return "valorarJugadores";
	  } 
	  else return ""; 
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

	@MessageMapping("/empezarPartida")
	@SendTo("/detalles")
	public String redireccionPartida(@RequestParam long id) {
		return "Hola";
	}

	@GetMapping("/pruebaSocket")
	public String prueba() {
		return "pruebaSocket";
	}

	@PostMapping("/empezarPartida")
	public String empezarPartida(Model model, RedirectAttributes redirAttrs, @RequestParam long idAnuncio,
			@RequestParam long idUsuario) {
		if (saAnuncio.empezarAnuncio(idAnuncio, idUsuario)) {
			redirAttrs.addAttribute("idAnuncio", idAnuncio);
			redirAttrs.addAttribute("idUsuario", idUsuario);
			return "redirect:/enPartida";
		}

		redirAttrs.addAttribute("id", idAnuncio);
		return "redirect:/detalles";
	}

	@GetMapping("/enPartida")
	public String entrarEnPartida(Model model, RedirectAttributes redirAttrs, @RequestParam long idAnuncio,
			@RequestParam long idUsuario) {

		if (saAnuncio.UsuarioEnAnuncio(idAnuncio, idUsuario) && saAnuncio.checkEmpezado(idAnuncio)) {

			model.addAttribute("idUsuario", idUsuario);
			Anuncio anuncio = saAnuncio.getAnuncioByID(idAnuncio);
			model.addAttribute("anuncio", anuncio);
			List<Long> participantes = new ArrayList<>();
			for (Participacion a : anuncio.getParticipacion()) {
				participantes.add(a.getUsuario().getId());
			}

			model.addAttribute("listaParticipantes", participantes);
			return "detallesEnPartida.html";
		}

		model.addAttribute("id", idAnuncio);
		return "detalles";
	}

}
