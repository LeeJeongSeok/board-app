package com.jeongseok.boardapp.controller;

import com.jeongseok.boardapp.service.CommentService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/post/{postId}/comment")
	public String writeComment(@PathVariable Long postId, @RequestParam String comment, Principal principal) {

		commentService.writeComment(postId, comment, principal.getName());

		return "redirect:/post/" + postId;
	}

	@PatchMapping("/post/{postId}/comment/{commentId}")
	public String updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestParam String comment, Principal principal) {

		commentService.updateComment(postId, commentId, comment, principal.getName());

		return "redirect:/post/" + postId;
	}

	@DeleteMapping("/post/{postId}/comment/{commentId}")
	public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId, Principal principal) {

		commentService.deleteComment(postId, commentId, principal.getName());

		return "redirect:/post/" + postId;
	}
}
