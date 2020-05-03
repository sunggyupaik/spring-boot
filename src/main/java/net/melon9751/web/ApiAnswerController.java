package net.melon9751.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.melon9751.domain.Answer;
import net.melon9751.domain.AnswerRepository;
import net.melon9751.domain.Question;
import net.melon9751.domain.QuestionRepository;
import net.melon9751.domain.Result;
import net.melon9751.domain.User;

//Json을 인식해 주기 위해서 ResController를 사용한다.
@RestController
//종속 관계이기 때문에 questions의 경로를 따라준다.
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;

	//답글을 쓰고 답글을 누를 때
	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session))
			return null;
 
		User loginUser = HttpSessionUtils.getUserfromSession(session);
		Question question = questionRepository.findById(questionId).get();
		Answer answer = new Answer(loginUser, question, contents);
		question.addAnswer();
		//return은 Answer형의 데이터베이스가 리턴된다.
		return answerRepository.save(answer);
	}
	
	//댓글 삭제 시
	@DeleteMapping("/{Id}")
	public Result delete(@PathVariable Long questionId, @PathVariable Long Id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session))
			return Result.fail("로그인해야 합니다.");
	
		Answer answer = answerRepository.findById(Id).get();
		User loginUser = HttpSessionUtils.getUserfromSession(session);
		if (!answer.isSameWriter(loginUser)) {
			return Result.fail("자신의 글만 삭제할 수 있습니다.");
		}
		
		answerRepository.deleteById(Id);
		Question question = questionRepository.findById(questionId).get();
		question.deleteAnswer();
		questionRepository.save(question);
		return Result.ok();
	}
}

//@Controller
////종속 관계이기 때문에 questions의 경로를 따라준다.
//@RequestMapping("/questions/{questionId}/answers")
//public class ApiAnswerController {
//
//@Autowired
//private AnswerRepository answerRepository;
//
//@Autowired
//private QuestionRepository questionRepository;
//
//@PostMapping("")
//public String create(@PathVariable Long questionId, String contents, HttpSession session) {
//	if (!HttpSessionUtils.isLoginUser(session))
//		return "redirect:/users/loginForm";
//
//	User loginUser = HttpSessionUtils.getUserfromSession(session);
//	Question question = questionRepository.findById(questionId).get();
//	Answer answer = new Answer(loginUser, question, contents);
//	answerRepository.save(answer);
//	return String.format("redirect:/questions/%d", questionId);
//}
//
//}
