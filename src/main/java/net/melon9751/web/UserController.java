package net.melon9751.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
//	@PostMapping("/create")
//	public String create(String id, String pasword, String name, String email) {
//		System.out.println("UserId" + id);
//		System.out.println("name" + name);
//		return "bye";
//	}
	private List<User> Users = new ArrayList<User>();
	
	@PostMapping("/create")
	public String create(User user) {
		System.out.println("User" + user);
		Users.add(user);
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("Users", Users);
		return "list";
	}
}
