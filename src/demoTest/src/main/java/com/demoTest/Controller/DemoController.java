package com.demoTest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demoTest.AplicationServices.Anuncios.SADemo;

@Controller
@RequestMapping(path = "/jj")
public class DemoController {
	
	@Autowired
	SADemo demoService;
	
	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
	
	@GetMapping("/")
	public String getAnuncios(Model model) {
		model.addAttribute("data", demoService.getDemos());
		return "index";
	}
}