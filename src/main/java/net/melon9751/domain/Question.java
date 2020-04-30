package net.melon9751.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Question {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long Id;
	
	//JPA를 사용하지 않으면 Join을 사용하여서 테이블끼리 연결해야하지만
	//JPA를 사용하면 Annotation만으로 쉽게 객체간의 관계를 맺을 수 있다.
	
	//객체지향프로그래밍을 하기 위해서는 이렇게 쓰지 않는다.
	//private Long writerId;
	
	//많은 질문이 하나의 유저에게 나온다.
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name ="fk_question_writer"))
	private User writer;
	
	//하나의 질문은 많은 답변을 가진다.
	@OneToMany(mappedBy="question") //question은 ManyToOne에 정의된 "필드명"이다.
	@OrderBy("Id ASC")
	private List<Answer> answers;
	
	private String title;
	
	//Lob는 엄청나게 많은 글자를 추가할 수 있다. 기본 String은 글자의 제한이 있다.
	@Lob
	private String contents;
	
	private LocalDateTime createDate;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Question() {}
	
	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}
	
	public String getFormattedCreateDate() {
		if(createDate == null)
			return "";
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}

	public void update(String title, String contents) {
		// TODO Auto-generated method stub
		this.title=title;
		this.contents=contents;
	}
	
	public boolean isSameWriter(User loginUser) {
		// TODO Auto-generated method stub
		return this.writer.equals(loginUser);
	}
	
	

}
