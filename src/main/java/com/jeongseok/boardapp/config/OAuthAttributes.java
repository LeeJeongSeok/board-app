package com.jeongseok.boardapp.config;

import com.jeongseok.boardapp.entity.User;
import java.util.Map;
import javax.management.relation.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthAttributes {

	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String username;
	private String password;
	private String name;
	private String email;
	private Role role;

	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.username((String) attributes.get("email"))
			.email((String) attributes.get("email"))
			.name((String) attributes.get("name"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	public User toEntity() {
		return User.builder()
			.username(email)
			.email(email)
			.name(name)
			.build();
	}

}
