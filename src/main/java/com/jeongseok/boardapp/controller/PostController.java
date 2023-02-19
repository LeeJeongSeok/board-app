package com.jeongseok.boardapp.controller;

import com.jeongseok.boardapp.dto.CreatePost;
import com.jeongseok.boardapp.dto.PostDto;
import com.jeongseok.boardapp.dto.PostInfo;
import com.jeongseok.boardapp.dto.UpdatePost;
import com.jeongseok.boardapp.service.PostService;
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
public class PostController {

	private final PostService postService;

	@GetMapping("/")
	public String list(Model model) {

		List<PostInfo> postInfo = postService.getPostsByUseFlag().stream()
			.map(postDto -> PostInfo.builder()
				.id(postDto.getId())
				.title(postDto.getTitle())
				.user(postDto.getUser())
				.createdAt(postDto.getCreatedAt())
				.build())
			.collect(Collectors.toList());

		model.addAttribute("list", postInfo);

		return "post/list";
	}

	@GetMapping("/post")
	public String writePostsForm() {
		return "post/write";
	}

	@PostMapping("/post")
	public String writePosts(@Valid CreatePost.Request request, Errors errors, Principal principal, Model model) {
		if (errors.hasErrors()) {
			Map<String, String> validateResult = validationRequestValue(errors);

			for (String key : validateResult.keySet()) {
				model.addAttribute(key, validateResult.get(key));
			}

			return "post/write";
		}

		postService.writePosts(request, principal.getName());

		return "redirect:/";
	}

	@GetMapping("/post/{id}")
	public String detailPostsForm(@PathVariable Long id, Model model) {

		PostDto postDto = postService.detailPosts(id);

		PostInfo postInfo = PostInfo.builder()
			.id(postDto.getId())
			.title(postDto.getTitle())
			.content(postDto.getContent())
			.build();

		model.addAttribute("post", postInfo);

		return "post/detail";
	}

	@PatchMapping("/post/{id}")
	public String updatePosts(@PathVariable Long id, @Valid UpdatePost.Request request, Errors errors, Model model, Principal principal) {
		if (errors.hasErrors()) {
			Map<String, String> validateResult = validationRequestValue(errors);

			for (String key : validateResult.keySet()) {
				model.addAttribute(key, validateResult.get(key));
			}

			return "post/detail";
		}

		postService.updatePosts(id, principal.getName(), request);

		return "redirect:/";
	}

	@DeleteMapping("/post/{id}")
	public String deletePosts(@PathVariable Long id, Principal principal) {
		postService.deletePosts(id, principal.getName());

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
