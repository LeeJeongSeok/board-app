package com.jeongseok.boardapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User extends BaseEntity{

	@Id
	@Column(name = "user_id")
	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;

	public void update(String password, String name, String email, String phone) {
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

}
