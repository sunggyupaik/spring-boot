package net.melon9751.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.melon9751.domain.User;
import net.melon9751.domain.UserRepository;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/{Id}")
	public User show(@PathVariable Long Id) {
		return userRepository.findById(Id).get();
	}
}
