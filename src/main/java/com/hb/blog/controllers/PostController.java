package com.hb.blog.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hb.blog.dtos.PostDTO;
import com.hb.blog.services.PostService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/private/post")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("")
	public ModelAndView getPosts() {
		List<PostDTO> posts = postService.getPosts();
		ModelAndView mav = new ModelAndView("posts");
		mav.addObject("posts", posts);
		return mav;
	}

	@GetMapping("/add")
	public ModelAndView addPost() {
		ModelAndView mav = new ModelAndView("addpost");
		mav.addObject("post", new PostDTO("", ""));
		return mav;
	}

	@PostMapping("/add")
	public String addPost(@Valid @ModelAttribute("post") PostDTO post, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "addpost";
		}
		postService.addPost(post);
		return "redirect:/private/post";
	}

}