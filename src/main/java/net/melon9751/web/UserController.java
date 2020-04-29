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

		// userId를 primary key로 user 정보를 조회
		User user = userRepository.findByUserId(userId);

		// user의 정보가 없으면 다시 로그인창
		if (user == null) {
			System.out.println("Login Failed");
			return "rediret:/users/loginForm";
		}

		//값을 꺼낼려고 하지 말고, 메시지를 보내 확인하게 한다.(객체지향적설계)
//		if (!password.equals(user.getPassword())) {
//			System.out.println("Login Failed");
//			return "rediret:/users/loginForm";
//		}
		
		// 비밀번호가 다르면 다시 로그인창
		if (!user.matchPassword(password)) {
			System.out.println("Login Failed");
			return "rediret:/users/loginForm";
		}

		// 로그인이 정상적으로 되면 session에 user 정보 저장
		System.out.println("Login Success");
		Session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		return "redirect:/";
	}

	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}

	@GetMapping("/form")
	public String form() {
		return "/users/form";
	}

	@PostMapping("")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("")
	public String list(Model model) {
		System.out.println("list에 들어가기 전");
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	// "개인정보 수정"을 위한 버튼 클릭 시,
	@GetMapping("/{Id}/form")
	public String updateForm(@PathVariable Long Id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserfromSession(session);
		if(!sessionedUser.matchId(Id)) {
			throw new IllegalStateException("자신의 정보만 수정 할 수 있습니다.");
		}
		User user = userRepository.findById(Id).get();
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	// "개인정보 수정"을 완료하기 확인 시,
	@PostMapping("/{Id}")
	public String update(@PathVariable Long Id, User updatedUser, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserfromSession(session);
		if(!sessionedUser.matchId(Id)) {
			throw new IllegalStateException("자신의 정보만 수정 할 수 있습니다.");
		}
		
		System.out.println("UpdatedUser : " +updatedUser);
		User user = userRepository.findById(Id).get();
		System.out.println("beforeUser : " + user);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
