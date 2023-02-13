package com.hb.blog.repositories;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.blog.models.LocalUser;

@Repository
public class UserRepository {
	
	private final static Logger logger = LoggerFactory.getLogger(UserRepository.class);

	public List<LocalUser> getUsers() {
		List<LocalUser> users = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		try {
			File resourceJson = new File("src/main/resources/users.json");
			users = mapper.readValue(resourceJson, new TypeReference<List<LocalUser>>() {});
			//logger.info("getUsers called - nb users fetched = " + users.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}

	public LocalUser getUserByUsername(String username) {

		List<LocalUser> users = getUsers();

		for (LocalUser user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public void save(LocalUser user) {
		List<LocalUser> existingsUsers = getUsers();
		int newId = 0;
		for (LocalUser existingUser : existingsUsers) {
			if (existingUser.getId() >= newId) {
				newId = existingUser.getId() + 1;
			}
		}
		user.setId(newId);
		existingsUsers.add(user);

		ObjectMapper mapper = new ObjectMapper();
		try {
			File resourceJson = new File("src/main/resources/users.json");
			String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(existingsUsers);
			
			logger.info(jsonString);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(resourceJson));
			writer.write(jsonString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}