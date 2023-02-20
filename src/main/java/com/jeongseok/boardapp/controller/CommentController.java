package com.jeongseok.boardapp.controller;

import com.jeongseok.boardapp.dto.CommentInfo;
import com.jeongseok.boardapp.service.CommentService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/post/{postId}/comment")
	public String getComments(@PathVariable Long postId, Model model) {

//		List<CommentInfo> comments = commentService.getCommentsByCommentType(postId).stream()
//			.map(commentDto -> CommentInfo.builder()
//				.comment(commentDto.getComment())
//				.updatedAt(commentDto.getUpdatedAt())
//				.build())
//			.collect(Collectors.toList());
//
//		model.addAttribute("comments", comments);

		return "post/{postId}";
	}

	@PostMapping("/post/{postId}/comment")
	public String writeComment(@PathVariable Long postId, @RequestParam String comment, Principal principal) {

		commentService.writeComment(postId, comment, principal.getName());

		return "post/{postId}";
	}

	@PatchMapping("/post/{postId}/comment/{commentId}")
	public String updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestParam String comment, Principal principal) {

		commentService.updateComment(postId, commentId, comment, principal.getName());

		return "post/{postId}";
	}

	@DeleteMapping("/post/{postId}/comment/{commentId}")
	public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId, Principal principal) {

		commentService.deleteComment(postId, commentId, principal.getName());

		return "post/{postId}";
	}
}
