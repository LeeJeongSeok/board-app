package com.jeongseok.boardapp.controller;

import com.jeongseok.boardapp.dto.comment.CreateComment;
import com.jeongseok.boardapp.dto.comment.UpdateComment;
import com.jeongseok.boardapp.service.CommentService;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	/**
	 *  2023/2/23
	 *	Validation 실패 시 프론트 단 전송 실패 - 수정해야함
	 */
	@PostMapping("/post/{postId}/comment")
	public String writeComment(@PathVariable Long postId, @Valid CreateComment.Request request, Errors errors, Principal principal, Model model) {

		if (errors.hasErrors()) {
			Map<String, String> validateResult = validationRequestValue(errors);

			for (String key : validateResult.keySet()) {
				model.addAttribute(key, validateResult.get(key));
			}

			return "redirect:/post/" + postId;
		}
		commentService.writeComment(postId, request, principal.getName());

		return "redirect:/post/" + postId;
	}

	@PatchMapping("/post/{postId}/comment/{commentId}")
	public String updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestParam String comment, Principal principal) {

		System.out.println(comment);
		commentService.updateComment(postId, commentId, comment, principal.getName());

		return "redirect:/post/" + postId;
	}

	@DeleteMapping("/post/{postId}/comment/{commentId}")
	public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId, Principal principal) {

		commentService.deleteComment(postId, commentId, principal.getName());

		return "redirect:/post/" + postId;
	}

	private Map<String, String> validationRequestValue(Errors errors) {
		Map<String, String> validateResult = new HashMap<>();

		for (FieldError fieldError : errors.getFieldErrors()) {
			validateResult.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return validateResult;
	}
}
