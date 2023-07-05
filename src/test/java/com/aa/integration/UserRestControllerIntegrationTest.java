package com.aa.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.aa.domain.Role;
import com.aa.domain.User;
import com.aa.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerIntegrationTest {

	private static String URI;
	
	@LocalServerPort
	private Integer port;
	
	private RestTemplate restTemplate;
	
	@Autowired private UserRepository repo;

	private User admin;
	private User editor;
	private User user;
	
	@BeforeEach
	void init() {
		URI = "http://localhost:" + port + "/api/v1/users";
		
		restTemplate = new RestTemplate();
		
		String password = "password";
		
		admin = new User("Admin", "Admin", "admin@yahoo.com", password, true, true, new Role(1));
		editor = new User("Editor", "Editor", "editor@yahoo.com", password, true, true, new Role(2));
		user = new User("User", "User", "user@yahoo.com", password, true, true, new Role(3));
	}
	
	@AfterEach
	void after() {
		repo.deleteAll();
	}
		
	@Test
	void listAll() {
		repo.saveAll(List.of(admin, editor, user));
		
		ResponseEntity<List<User>> response = restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
		List<User> listUsers = response.getBody();
		
		assertNotNull(listUsers);
		assertEquals(3, listUsers.size());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void get() {
		User savedUser = repo.save(admin);
		
		ResponseEntity<User> response = restTemplate.getForEntity(URI + "/" + savedUser.getId(), User.class);
		User existingUser = response.getBody();
		
		assertNotNull(existingUser);
		assertEquals(savedUser.getFirstName(), existingUser.getFirstName());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void save() {
		ResponseEntity<User> response = restTemplate.postForEntity(URI, admin, User.class);
		
		User savedUser = response.getBody();
		
		assertNotNull(savedUser);
		assertEquals(admin.getEmail(), savedUser.getEmail());
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	void update() {
		User savedUser = repo.save(admin);
		savedUser.setFirstName("Administrator");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<User> requestEntity = new HttpEntity<>(savedUser, headers);
		
		ResponseEntity<User> response = restTemplate.exchange(URI, HttpMethod.PUT, requestEntity, User.class);
		User updatedUser = response.getBody();
		
		assertNotNull(updatedUser);
		assertEquals(updatedUser.getFirstName(), savedUser.getFirstName());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void delete() {
		User savedUser = repo.save(admin);
		
		restTemplate.delete(URI + "/" + savedUser.getId());
		
		int size = repo.findAll().size();
		
		assertEquals(0, size);
	}
}

