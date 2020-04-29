package net.melon9751.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.melon9751.domain.User;
import net.melon9751.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession Session) {
		
		//userId를 primary key로 user 정보를 조회
		User user = userRepository.findByUserId(userId);
		
		//user의 정보가 없으면 다시 로그인창
		if(user == null) {
			System.out.println("Login Failed");
			return "rediret:/users/loginForm";
		}
		
		//비밀번호가 다르면 다시 로그인창
		if(!password.equals(user.getPassword())) {
			System.out.println("Login Failed");
			return "rediret:/users/loginForm";
		}
		
		//로그인이 정상적으로 되면 session에 user 정보 저장
		System.out.println("Login Success");
		Session.setAttribute("user", user);
		return"redirect:/";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@PostMapping("")
	public String create(User user) {
		System.out.println("user" + user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/{Id}/form")
	public String updateForm(@PathVariable Long Id, Model model) {
		System.out.println("updateForm Controller");
		User user = userRepository.findById(Id).get();
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PostMapping("/{Id}")
	public String update(@PathVariable Long Id, User newUser) {
		User user = userRepository.findById(Id).get();
		user.update(newUser);
		userRepository.save(user);
		return "/redirect/users";
	}
}
