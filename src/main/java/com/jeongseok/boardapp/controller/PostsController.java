package com.jeongseok.boardapp.controller;

import com.jeongseok.boardapp.dto.CreatePosts;
import com.jeongseok.boardapp.dto.PostsDto;
import com.jeongseok.boardapp.dto.PostsInfo;
import com.jeongseok.boardapp.dto.UpdatePosts;
import com.jeongseok.boardapp.service.PostsService;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostsController {

	private final PostsService postsService;

	@GetMapping("/")
	public String list(Model model) {

		List<PostsInfo> postsInfo = postsService.getPostsByUseFlag().stream()
			.map(postsDto -> PostsInfo.builder()
				.id(postsDto.getId())
				.title(postsDto.getTitle())
				.user(postsDto.getUser())
				.createdAt(postsDto.getCreatedAt())
				.build())
			.collect(Collectors.toList());

		model.addAttribute("list", postsInfo);

		return "posts/list";
	}

	@GetMapping("/posts")
	public String writePostsForm() {
		return "posts/write";
	}

	@PostMapping("/posts")
	public String writePosts(@Valid CreatePosts.Request request, Errors errors, Principal principal, Model model) {
		if (errors.hasErrors()) {
			Map<String, String> validateResult = validationRequestValue(errors);

			for (String key : validateResult.keySet()) {
				model.addAttribute(key, validateResult.get(key));
			}

			return "posts/write";
		}

		postsService.writePosts(request, principal.getName());

		return "redirect:/";
	}

	@GetMapping("/posts/{id}")
	public String detailPostsForm(@PathVariable Long id, Model model) {

		PostsDto postsDto = postsService.detailPosts(id);

		PostsInfo postsInfo = PostsInfo.builder()
			.id(postsDto.getId())
			.title(postsDto.getTitle())
			.content(postsDto.getContent())
			.build();

		model.addAttribute("posts", postsInfo);

		return "posts/detail";
	}

	@PatchMapping("/posts/{id}")
	public String updatePosts(@PathVariable Long id, @Valid UpdatePosts.Request request, Errors errors, Model model, Principal principal) {
		if (errors.hasErrors()) {
			Map<String, String> validateResult = validationRequestValue(errors);

			for (String key : validateResult.keySet()) {
				model.addAttribute(key, validateResult.get(key));
			}

			return "posts/detail";
		}

		postsService.updatePosts(id, principal.getName(), request);

		return "redirect:/";
	}

	@DeleteMapping("/posts/{id}")
	public String deletePosts(@PathVariable Long id, Principal principal) {
		postsService.deletePosts(id, principal.getName());

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
