package com.hb.blog.repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.blog.models.Post;

@Repository
public class PostRepository {

	public List<Post> getPosts() {
		List<Post> posts = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();		
		try {
			File resourceJson = new ClassPathResource("posts.json").getFile();
			posts = mapper.readValue(
					resourceJson, 
					new TypeReference<List<Post>>() {} );			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return posts;
	}
	
}