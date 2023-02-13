package com.hb.blog;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser("user")
	public void testGetPosts() throws Exception {		
		mockMvc.perform(MockMvcRequestBuilders.get("/private/post"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());		
	}
	
	@Test
	@WithMockUser("user")
	public void testAddPost() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/private/post/add")
					.param("title", "tester")
					.param("content", "tester")
					.with(csrf()))
				.andExpect(MockMvcResultMatchers.redirectedUrl("/private/post"))
				.andExpect(status().isFound());
	}
	
}