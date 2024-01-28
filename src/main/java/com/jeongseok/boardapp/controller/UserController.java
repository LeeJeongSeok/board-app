package com.jeongseok.boardapp.controller;

import com.jeongseok.boardapp.dto.user.CreateUser;
import com.jeongseok.boardapp.dto.user.SessionUser;
import com.jeongseok.boardapp.dto.user.UpdateUser;
import com.jeongseok.boardapp.dto.user.UserDto;
import com.jeongseok.boardapp.service.UserService;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}

	@GetMapping("/join")
	public String joinForm() {
		return "user/join";
	}

	@PostMapping("/join")
	public String joinUser(@Valid CreateUser.Request request, Errors errors, Model model) {
		if (errors.hasErrors()) {
			/* 유효성 통과하지 못한 필드값 에러 핸들링*/
			Map<String, String> validateResult = validationRequestValue(errors);

			for (String key : validateResult.keySet()) {
				model.addAttribute(key, validateResult.get(key));
			}

			return "user/join";
		}
		userService.joinUser(request);
		return "redirect:/login";
	}

	@GetMapping("/update")
	public String updateUserForm(Model model, Principal principal) {
		// 업데이트 페이지 이동까지는 실질적으로 업데이트가 발생한 것이 아니기 때문에 CreateUser에서 Reponse가 나감
		UserDto userDto = userService.findUserByUsername(principal.getName());

		model.addAttribute("user", SessionUser.from(userDto));

		return "user/update";
	}


	@PostMapping("/update/user")
	public String updateUser(@Valid UpdateUser.Request updateUser, Errors errors, Model model, Principal principal) {
		if (errors.hasErrors()) {
			Map<String, String> validateResult = validationRequestValue(errors);

			for (String key : validateResult.keySet()) {
				model.addAttribute(key, validateResult.get(key));
			}

			return "user/update";
		}

		userService.updateUser(updateUser, principal.getName());

		return "redirect:/";
	}

	private Map<String, String> validationRequestValue(Errors errors) {
		Map<String, String> validateResult = new HashMap<>();

		for (FieldError fieldError : errors.getFieldErrors()) {
			validateResult.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return validateResult;
	}
}
