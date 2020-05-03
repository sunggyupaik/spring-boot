package net.melon9751.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends AbstractEntity {
//	@Id 
//	@GeneratedValue(strategy = GenerationType.IDENTITY) 
//	private Long Id;
	
	//primarykey를 지정한다. = Id
	//데이터베이스에서 id값을 자동으로 +1씩 시켜 준다. = GeneratedValue
	
	//null값이 들어갈 수 없음을 의미한다. default는 true이다. = Column
	@Column(nullable=false, length=20)
	
	@JsonProperty
	private String userId;
	
	@JsonIgnore
	private String password;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String email;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean matchPassword(String newPassword) {
		if(newPassword == null)
			return false;
		return newPassword.equals(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean matchId(Long newId) {
		if(newId == null)
			return false;
		
		return newId.equals(getId());
	}

	@Override
	public String toString() {
		return "User [" + super.toString() + ", userId=" + userId + ", password=" + password + ", name=" + name + ", email="
				+ email + "]";
	}

	public void update(User newUser) {
		this.userId = newUser.userId;
		this.password = newUser.password;
		this.name = newUser.name;
		this.email = newUser.email;
		// TODO Auto-generated method stub
	}
	
	
}
