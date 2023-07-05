package com.aa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.aa.domain.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository repo;
	
	@Test
	void saveAll() {
		// Arrange
		Role admin = new Role("ADMIN");
		Role editor = new Role("EDITOR");
		Role user = new Role("USER");
		
		// Act
		List<Role> savedRoles = repo.saveAll(List.of(admin, editor, user));
		
		// Assert
		assertNotNull(savedRoles);
		assertEquals(3, savedRoles.size());
	}
}
