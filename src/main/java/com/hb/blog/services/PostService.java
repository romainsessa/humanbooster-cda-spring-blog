package com.hb.blog.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hb.blog.dtos.PostDTO;
import com.hb.blog.models.Post;
import com.hb.blog.repositories.PostRepository;

@Service
public class PostService {
	
	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public List<PostDTO> getPosts() {
		List<Post> posts = postRepository.getPosts();
		List<PostDTO> postsDtos = new ArrayList<>();
		
		posts.forEach((post) -> { 
					postsDtos.add(new PostDTO(post.getTitle(), post.getContent())); 
				});
		
		return postsDtos;
	}
	
	public void addPost(PostDTO postDTO) {
		Post post = new Post();
		post.setTitle(postDTO.title());
		post.setContent(postDTO.content());
		postRepository.save(post);
	}
	
	
}
