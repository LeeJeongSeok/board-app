package com.jeongseok.boardapp.entity;

import com.jeongseok.boardapp.type.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	public void update(String password, String name, String email, String phone) {
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	/**
	 * 소셜 로그인시 이미 등록된 회원이면 수정날짜만 update
	 */
	public User updateDate() {
		this.onPreUpdate();
		return this;
	}

	public String getRoleValue() {
		return this.role.getValue();
	}

}
