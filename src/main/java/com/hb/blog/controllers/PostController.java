package com.hb.blog.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hb.blog.dtos.PostDTO;
import com.hb.blog.services.PostService;

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
	
}