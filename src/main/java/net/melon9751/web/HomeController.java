package net.melon9751.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.melon9751.domain.QuestionRepository;

@Controller
public class HomeController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
		System.out.println("초기화면");
		return "index";
	}
}
