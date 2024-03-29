package com.jeongseok.boardapp.controller;

import static com.jeongseok.boardapp.util.ValidationUtil.validationRequestValue;

import com.jeongseok.boardapp.dto.comment.CreateComment;
import com.jeongseok.boardapp.dto.comment.UpdateComment;
import com.jeongseok.boardapp.dto.user.SessionUser;
import com.jeongseok.boardapp.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
	public String writeComment(@PathVariable long postId, @Valid CreateComment.Request request, Errors errors, HttpSession httpSession, Model model) {

		if (errors.hasErrors()) {
			model.addAttribute(validationRequestValue(errors));

			return "redirect:/post/" + postId;
		}

		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

		commentService.writeComment(postId, request, sessionUser.getUsername());

		return "redirect:/post/" + postId;
	}

	@PatchMapping("/post/{postId}/comment/{commentId}")
	public String updateComment(@PathVariable long postId, @PathVariable long commentId, @Valid UpdateComment.Request request, Errors errors, HttpSession httpSession, Model model) {

		if (errors.hasErrors()) {
			model.addAttribute(validationRequestValue(errors));

			return "redirect:/post/" + postId;
		}

		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

		commentService.updateComment(commentId, request, sessionUser.getUsername());

		return "redirect:/post/" + postId;
	}

	@DeleteMapping("/post/{postId}/comment/{commentId}")
	public String deleteComment(@PathVariable long postId, @PathVariable long commentId, HttpSession httpSession) {

		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

		commentService.deleteComment(commentId, sessionUser.getUsername());

		return "redirect:/post/" + postId;
	}
}
