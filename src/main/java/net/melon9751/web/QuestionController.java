package net.melon9751.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.melon9751.domain.Question;
import net.melon9751.domain.QuestionRepository;
import net.melon9751.domain.Result;
import net.melon9751.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session))
			return "redirect:/users/loginForm";
		return "/qna/form";
	}

	// POST는 "없던 새로운 정보를 추가"
	// 내용을 다 입력하고 질문 올리기 버튼 클릭 시
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session))
			return "redirect:/users/loginForm";

		User sessionUser = HttpSessionUtils.getUserfromSession(session);
		System.out.println(sessionUser);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		return "redirect:/";
	}

	// 질문에서 제목을 클릭 할 때
	@GetMapping("/{Id}")
	public String show(@PathVariable Long Id, Model model) {
		Question question = questionRepository.findById(Id).get();
		// 여기에서 answer의 repository들을 가지고 와도 되지만 Question.class에서 할 수도 있다.
		model.addAttribute("question", question);
		return "/qna/show";
	}

	// 게시글을 수정하고 싶어서 수정을 누른 경우
	@GetMapping("{Id}/form")
	public String updateForm(@PathVariable Long Id, Model model, HttpSession session) {
		Question question = questionRepository.findById(Id).get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "redirect:/user/login";
		}
		model.addAttribute("question", question);
		return "/qna/show";
	}


	// PUT은 "기존에 있던 데이터를 변경"
	// 게시글을 다 수정하고 완료를 눌렀을 때
	@PostMapping("/{Id}/update")
	public String update(@PathVariable Long Id, String title, String contents, Model model, HttpSession session) {
		Question question = questionRepository.findById(Id).get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "redirect:/user/login";
		}

		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", Id);

	}

	private Result valid(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}

		User loginUser = HttpSessionUtils.getUserfromSession(session);
		if (!question.isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}

		return Result.ok();
	}

	// DELETE는 "해당 내용을 삭제"
	// 게시글 삭제를 눌렀을 때
	@PostMapping("/{Id}/delete")
	public String delete(@PathVariable Long Id, Model model, HttpSession session) {
		Question question = questionRepository.findById(Id).get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "redirect:/user/login";
		}
		questionRepository.deleteById(Id);
		return "redirect:/";
	}
}
