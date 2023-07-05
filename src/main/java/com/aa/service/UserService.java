package com.aa.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aa.domain.User;
import com.aa.exception.DuplicateEmailException;
import com.aa.exception.UserNotFoundException;
import com.aa.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public List<User> listAll() {
		return repo.findAll();
	}
	
	public User save(User userInForm) throws DuplicateEmailException {
		boolean isEditMode = userInForm.getId() != null;
		
		if (isEditMode) {
			User userInDB = repo.findById(userInForm.getId()).get();
			
			boolean isTheSameEmail = userInForm.getEmail().equals(userInDB.getEmail());
			
			if (isTheSameEmail) {
				userInForm.setEmail(userInDB.getEmail());
			} else {
				checkDuplicateEmail(userInForm.getEmail());
			}
		} else {
			checkDuplicateEmail(userInForm.getEmail());
		}
		
		return repo.save(userInForm);
	}

	private void checkDuplicateEmail(String email) throws DuplicateEmailException {
		User user = repo.findByEmail(email);
		
		if (user != null) {
			throw new DuplicateEmailException("This E-mail: " + email + " already exist. Please choose another E-mail!");
		}
	}
	
	public User get(Integer id) throws UserNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("Could not find any User with id: " + id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Optional<User> user = repo.findById(id);
		
		if (user.isEmpty()) {
			throw new UserNotFoundException("Could not find any User with id: " + id);
		}
		
		repo.deleteById(id);
	}
	
	public User findByEmail(String email) throws UserNotFoundException {
		try {
			return repo.findByEmail(email);
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("Could not find any User with email: " + email);
		}
	}
	
}
