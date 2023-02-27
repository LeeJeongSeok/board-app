package com.jeongseok.boardapp.controller;

import static com.jeongseok.boardapp.util.ValidationUtil.validationRequestValue;

import com.jeongseok.boardapp.dto.comment.CreateComment;
import com.jeongseok.boardapp.dto.comment.UpdateComment;
import com.jeongseok.boardapp.service.CommentService;
import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/post/{postId}/comment")
	public String writeComment(@PathVariable long postId, @Valid CreateComment.Request request, Errors errors, Principal principal, Model model) {

		if (errors.hasErrors()) {
			model.addAttribute(validationRequestValue(errors));

			return "redirect:/post/" + postId;
		}
		commentService.writeComment(postId, request, principal.getName());

		return "redirect:/post/" + postId;
	}

	@PatchMapping("/post/{postId}/comment/{commentId}")
	public String updateComment(@PathVariable long postId, @PathVariable long commentId, @Valid UpdateComment.Request request, Errors errors, Principal principal, Model model) {

		if (errors.hasErrors()) {
			model.addAttribute(validationRequestValue(errors));

			return "redirect:/post/" + postId;
		}
		commentService.updateComment(commentId, request, principal.getName());

		return "redirect:/post/" + postId;
	}

	@DeleteMapping("/post/{postId}/comment/{commentId}")
	public String deleteComment(@PathVariable long postId, @PathVariable long commentId, Principal principal) {

		commentService.deleteComment(commentId, principal.getName());

		return "redirect:/post/" + postId;
	}
}
