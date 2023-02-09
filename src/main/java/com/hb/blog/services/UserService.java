package com.hb.blog.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hb.blog.dtos.UserDTO;
import com.hb.blog.models.LocalUser;
import com.hb.blog.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<UserDTO> getUsers() {
		
		List<LocalUser> users = userRepository.getUsers();
		List<UserDTO> userDtos = new ArrayList<>();
		
		users.forEach((user) -> {
			userDtos.add(new UserDTO(user.getId(), user.getUsername(), user.getRole()));
		});

		return userDtos;
	}
	
	
}
