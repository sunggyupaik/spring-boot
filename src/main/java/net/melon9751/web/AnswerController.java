package net.melon9751.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.melon9751.domain.Answer;
import net.melon9751.domain.AnswerRepository;
import net.melon9751.domain.Question;
import net.melon9751.domain.QuestionRepository;
import net.melon9751.domain.User;

@Controller

//종속 관계이기 때문에 questions의 경로를 따라준다.
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;

	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session))
			return "redirect:/users/loginForm";

		User loginUser = HttpSessionUtils.getUserfromSession(session);
		Question question = questionRepository.findById(questionId).get();
		Answer answer = new Answer(loginUser, question, contents);
		answerRepository.save(answer);
		return String.format("redirect:/questions/%d", questionId);
	}

}
