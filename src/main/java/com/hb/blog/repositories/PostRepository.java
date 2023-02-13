package com.hb.blog.repositories;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
			File resourceJson = new File("src/main/resources/posts.json");
			posts = mapper.readValue(
					resourceJson, 
					new TypeReference<List<Post>>() {} );			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return posts;
	}
	
	public void save(Post post) {
		List<Post> existingsPosts = getPosts();
		int newId = 0;
		for (Post existingPost : existingsPosts) {
			if (existingPost.getId() >= newId) {
				newId = existingPost.getId() + 1;
			}
		}
		post.setId(newId);
		existingsPosts.add(post);

		ObjectMapper mapper = new ObjectMapper();
		try {
			File resourceJson = new File("src/main/resources/posts.json");
			String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(existingsPosts);
			
			//logger.info(jsonString);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(resourceJson));
			writer.write(jsonString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}