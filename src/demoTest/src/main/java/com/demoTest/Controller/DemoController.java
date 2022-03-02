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
	
	@PostMapping("/add") // Map ONLY POST Requests
    public String addAnuncio (@RequestParam String demoName , @RequestParam int demoNumber, @RequestParam String demoLinkedValue) {
      // @ResponseBody means the returned String is the response, not a view name
      // @RequestParam means it is a parameter from the GET or POST request
       demoService.addDemos(demoName, demoNumber, demoLinkedValue);

       return "index";
    }
}