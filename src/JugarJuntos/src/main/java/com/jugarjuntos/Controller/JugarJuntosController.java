package com.jugarjuntos.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jugarjuntos.Entities.UsuarioDetalles.CustomUserDetails;
import com.jugarjuntos.Exceptions.BusinessException;
import com.jugarjuntos.ServiciosAplicacion.SAAnuncio;
import com.jugarjuntos.ServiciosAplicacion.SAParticipacion;
import com.jugarjuntos.Transfers.TParticipacion;

@Controller
public class JugarJuntosController {

	@Autowired
	SAAnuncio saAnuncio;
	
	@Autowired 
	SAParticipacion saParticipacion;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("anuncios", saAnuncio.getAllAnuncios());
		
		return "index";
	}
	
	@GetMapping("/verSolicitudesDeAcceso")
	public String verSolicitudes(Model model, @RequestParam long id){
		model.addAttribute("solicitudes", saParticipacion.solicitudesPendientes(id));
		return "solicitudes";
	}
	
	@PostMapping("/enviarSolicitud")
	public String enviarSolicitud(Model model, RedirectAttributes redirAttrs, @RequestParam long id_anuncio) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long idUsuario = -1L;
		try {
			idUsuario = ((CustomUserDetails) principal).getId();
			TParticipacion participacion = new TParticipacion(idUsuario, id_anuncio, null);
			
			try {
				saParticipacion.enviarSolicitud(participacion);
			} catch (BusinessException e) {
				redirAttrs.addAttribute("juego", id_anuncio);
				return "redirect:/detalles";
			}
			
			
		}catch(Exception e) {
			
		}
		redirAttrs.addAttribute("id", id_anuncio);
		if (idUsuario != -1L)
			redirAttrs.addFlashAttribute("success", "Se ha enviado la solicitud correctamente.");
		else
			redirAttrs.addFlashAttribute("error", "Error al unirte al anuncio.");
		
		return "redirect:/detalles";
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
}
