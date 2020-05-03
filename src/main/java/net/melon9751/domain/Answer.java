package net.melon9751.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Answer extends AbstractEntity {

//	@Id
//	@GeneratedValue
//	@JsonProperty
//	private Long Id;

	// 여러개의 Answer가 User에 의해 만들어 진다.
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	@JsonProperty
	private User writer;

	// 여러개의 Answer이 Question에 달릴 수 있다.
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
	@JsonProperty
	private Question question;

	// JPA를 사용하면 한군데 mapping 되어 있는것을 다른 곳에서도 사용할 수 있다.
	@Lob
	@JsonProperty
	private String contents;

//	private LocalDateTime createDate;

	public Answer() {
	}

	public Answer(User writer, Question question, String contents) {
		this.writer = writer;
		this.question = question;
		this.contents = contents;
	}

	public boolean isSameWriter(User loginUser) {
		// TODO Auto-generated method stub
		return loginUser.equals(this.writer);
	}

	@Override
	public String toString() {
		return "Answer [ " + super.toString() + ", writer=" + writer + ", contents=" + contents + "]";
	}

}
