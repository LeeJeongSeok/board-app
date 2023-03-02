package com.jeongseok.boardapp.dto.user;

import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDto implements UserDetails, OAuth2User {

	private String username;
	private String password;
	private Map<String, Object> attributes;


	// form 로그인
	public LoginUserDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// OAuth 로그인
	public LoginUserDto(String username, Map<String, Object> attributes) {
		this.username = username;
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}
}
