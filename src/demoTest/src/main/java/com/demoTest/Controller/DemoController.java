package com.demoTest.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demoTest.AplicationServices.Anuncios.SADemo;

@Controller
public class DemoController {
	
	@Autowired
	SADemo demoService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("data", demoService.getDemos());
		return "index";
	}
	
	@GetMapping("/form")
	public String getForm(Model model) {
		return "form";
	}
	
	@GetMapping("/deleteForm")
	public String getdeleteForm(Model model) {
		return "deleteForm";
	}
	
	
	@PostMapping("/add") // Map ONLY POST Requests
    public String addAnuncio (@RequestParam String demoName, 
							  @RequestParam int demoNumber,
							  @RequestParam String demoLinkedValue,
							  Model m) {
       demoService.addDemos(demoName, demoNumber, demoLinkedValue);

       return "redirect:/";
    }
	
}