package com.jeongseok.boardapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

	@GetMapping("/post/{postId}/comment")
	public String getComments(@PathVariable Long postId) {

		return "";
	}

	@PostMapping("/post/{postId}/comment")
	public String writeComment(@PathVariable Long postId) {

		return "";
	}

	@PatchMapping("/post/{postId}/comment/{commentId}")
	public String updateComment(@PathVariable Long postId, @PathVariable Long commentId) {

		return "";
	}

	@DeleteMapping("/post/{postId}/comment/{commentId}")
	public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {

		return "";
	}
}
