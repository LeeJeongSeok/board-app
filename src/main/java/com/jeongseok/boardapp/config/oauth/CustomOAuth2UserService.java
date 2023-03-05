package com.jeongseok.boardapp.config.oauth;

import com.jeongseok.boardapp.config.OAuthAttributes;
import com.jeongseok.boardapp.dto.user.CreateUser;
import com.jeongseok.boardapp.dto.user.UserDto;
import com.jeongseok.boardapp.entity.User;
import com.jeongseok.boardapp.repository.UserRepository;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;
	private final HttpSession httpSession;

	// 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 메소드
	// 구글로그인 버튼 -> 구글 로그인 창 -> 로그인 완료 -> code (OAuth-Client라이브러리) -> AccessToken ->
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

		/* OAuth2UserService */
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

		User user = saveOrUpdate(attributes);

		httpSession.setAttribute("user", CreateUser.Response.from(UserDto.fromEntity(user)));

		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(user.getRoleValue())),
			attributes.getAttributes(),
			attributes.getNameAttributeKey());
	}

	private User saveOrUpdate(OAuthAttributes oAuthAttributes) {


		User user = userRepository.findByEmail((String) oAuthAttributes.getAttributes().get("email"))
			.map(User::updateDate)
			.orElse(oAuthAttributes.toEntity());

		return userRepository.save(user);
	}
}
