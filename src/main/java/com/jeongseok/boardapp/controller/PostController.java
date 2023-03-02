package com.jeongseok.boardapp.controller;

import static com.jeongseok.boardapp.util.ValidationUtil.validationRequestValue;

import com.jeongseok.boardapp.dto.comment.CommentInfo;
import com.jeongseok.boardapp.dto.post.CreatePost;
import com.jeongseok.boardapp.dto.post.PostDto;
import com.jeongseok.boardapp.dto.post.PostInfo;
import com.jeongseok.boardapp.dto.post.UpdatePost;
import com.jeongseok.boardapp.dto.user.CreateUser;
import com.jeongseok.boardapp.dto.user.LoginUserDto;
import com.jeongseok.boardapp.dto.user.UserDto;
import com.jeongseok.boardapp.service.PostService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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

		List<PostInfo> postInfos = postService.getPostsByPostType().stream()
			.map(postDto -> PostInfo.builder()
				.id(postDto.getId())
				.title(postDto.getTitle())
				.user(postDto.getUser())
				.createdAt(postDto.getCreatedAt())
				.build())
			.collect(Collectors.toList());

		model.addAttribute("list", postInfos);

		return "post/list";
	}

	@GetMapping("/post")
	public String writePostsForm() {
		return "post/write";
	}

	@PostMapping("/post")
	public String writePosts(@Valid CreatePost.Request request, Errors errors, HttpSession httpSession, Model model) {
		if (errors.hasErrors()) {

			model.addAttribute(validationRequestValue(errors));

			return "post/write";
		}

		CreateUser.Response user = (CreateUser.Response) httpSession.getAttribute("user");

		postService.writePost(request, user.getUsername());

		return "redirect:/";
	}

	@GetMapping("/post/{id}")
	public String detailPostsForm(@PathVariable Long id, Model model) {

		PostDto postDto = postService.detailPost(id);

		PostInfo postInfo = new PostInfo(postDto);

		List<CommentInfo> comments = postInfo.getComments();

		// 여기서 댓글 리스트들을 쭈루룩 뽑아와야함
		model.addAttribute("post", postInfo);
		model.addAttribute("comments", comments);

		return "post/detail";
	}

	@PatchMapping("/post/{id}")
	public String updatePosts(@PathVariable Long id, @Valid UpdatePost.Request request, Errors errors, Model model, HttpSession httpSession) {
		if (errors.hasErrors()) {

			model.addAttribute(validationRequestValue(errors));

			return "post/detail";
		}

		CreateUser.Response user = (CreateUser.Response) httpSession.getAttribute("user");

		postService.updatePost(id, user.getUsername(), request);

		return "redirect:/";
	}

	@DeleteMapping("/post/{id}")
	public String deletePosts(@PathVariable Long id, HttpSession httpSession) {

		CreateUser.Response user = (CreateUser.Response) httpSession.getAttribute("user");

		postService.deletePost(id, user.getUsername());

		return "redirect:/";
	}
}
