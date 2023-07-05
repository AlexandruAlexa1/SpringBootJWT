package com.aa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.aa.domain.Role;
import com.aa.domain.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;
	
	@Test
	void saveAll() {
		String password = "password";
		
		User admin = new User("Admin", "Admin", "admin@yahoo.com", password, true, true, new Role(1));
		User editor = new User("Editor", "Editor", "editor@yahoo.com", password, true, true, new Role(2));
		User user = new User("User", "User", "user@yahoo.com", password, true, true, new Role(3));
		
		List<User> savedUsers = repo.saveAll(List.of(admin, editor, user));
		
		assertNotNull(savedUsers);
		assertEquals(3, savedUsers.size());
	}
	
	@Test
	void listAll() {
		List<User> listUsers = repo.findAll();
		
		assertNotNull(listUsers);
		
		listUsers.forEach(user -> System.err.println(user));
	}
	
	@Test
	void get() {
		User user = repo.findById(1).get();
		
		assertNotNull(user);
		
		System.err.println(user);
	}
	
	@Test
	void update() {
		String firstName = "Administrator";
		
		User user = repo.findById(1).get();
		user.setFirstName(firstName);
		
		User updatedUser = repo.save(user);
		
		assertNotNull(updatedUser);
		assertEquals(firstName, updatedUser.getFirstName());
	}
	
	@Test
	void delete() {
		Integer id = 3;
		
		repo.deleteById(id);
		
		Optional<User> user = repo.findById(id);
		
		assertThat(!user.isPresent());
	}
	
	@Test
	void findByEmail() {
		String email = "admin@yahoo.com";
		
		User user = repo.findByEmail(email);
		
		assertNotNull(user);
		
		System.err.println(user);
	}
	
}
