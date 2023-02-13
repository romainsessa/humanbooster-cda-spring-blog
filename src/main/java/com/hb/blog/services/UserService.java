package com.hb.blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hb.blog.dtos.UserDTO;
import com.hb.blog.dtos.UserFormDTO;
import com.hb.blog.models.LocalUser;
import com.hb.blog.repositories.UserRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;

@Service
public class UserService {

	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;
	
	private ValidatorFactory validatorFactory;
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ValidatorFactory validatorFactory) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.validatorFactory = validatorFactory;
	}
	
	public List<UserDTO> getUsers() {
		
		List<LocalUser> users = userRepository.getUsers();
		List<UserDTO> userDtos = new ArrayList<>();
		
		users.forEach((user) -> {
			userDtos.add(new UserDTO(user.getId(), user.getUsername(), user.getRole()));
		});

		return userDtos;
	}
	
	public void saveUser(UserFormDTO inputUser) {
		LocalUser user = new LocalUser();
		user.setUsername(inputUser.username());
		user.setPassword(inputUser.password());
		user.setRole("USER");
		
		Set<ConstraintViolation<LocalUser>> violations = validatorFactory.getValidator().validate(user);
		if(violations.isEmpty()) {
			user.setPassword(passwordEncoder.encode(inputUser.password()));
			userRepository.save(user);
		} else {
			// ....
			logger.error("Validation failed !");
			violations.forEach((violation) -> { logger.error(violation.getMessage()); });
		}
	}	
}
