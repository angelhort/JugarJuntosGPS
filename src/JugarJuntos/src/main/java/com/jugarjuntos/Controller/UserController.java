package com.jugarjuntos.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jugarjuntos.ServiciosAplicacion.SAUsuario;
import com.jugarjuntos.Transfers.TUsuario;

@Controller
public class UserController {
	
	@Autowired 
	SAUsuario saUsuario;	

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("usuario", new TUsuario());

		return "login";
	}
	
	@GetMapping("/registro")
	public String crearFormRegistro(Model model) {
		model.addAttribute("usuario", new TUsuario());
		
		return "registro";
	}
	
	@PostMapping("/registro")
	public String crearUsuario(TUsuario usuario, HttpServletRequest request) {
		long res = saUsuario.altaUsuario(usuario);
		if (res != -1) {
			request.getSession().setAttribute("COOKIE_SESION_ID", res);
		}
		return "redirect:/";
	}
}
