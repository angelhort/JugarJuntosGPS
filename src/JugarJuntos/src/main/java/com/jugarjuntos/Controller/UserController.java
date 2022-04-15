package com.jugarjuntos.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jugarjuntos.Exceptions.BusinessException;
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
	public String crearUsuario(TUsuario usuario, RedirectAttributes redirAttrs) {
		long res = saUsuario.altaUsuario(usuario);
		if (res > 0) {
			redirAttrs.addFlashAttribute("success", "Te registraste correctamente. BIENVENIDO!");
				
		} else if (res == -1) {
			redirAttrs.addFlashAttribute("error", "Error en la creación del usuario. El formato del discord es incorrecto");
			return "redirect:/registro";
		}else if(res == -2) {
			redirAttrs.addFlashAttribute("error", "Error en la creación del usuario. El correo introducido tiene una cuenta vinculada");
			return "redirect:/registro";
		}

		return "redirect:/";
	}


}
