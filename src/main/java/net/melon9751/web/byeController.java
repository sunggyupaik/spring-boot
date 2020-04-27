package net.melon9751.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class byeController {
	@GetMapping("/hello")
	public String hello(String name, Model model) {
		model.addAttribute("name", name);
//		model.addAttribute("age", age);
		return "hello";
	}
}
