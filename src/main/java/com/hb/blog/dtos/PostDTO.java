package com.hb.blog.dtos;

import jakarta.validation.constraints.NotBlank;

public record PostDTO(
		@NotBlank(message = "title is mandatory") String title, 
		@NotBlank(message = "content is mandatory") String content) {}
