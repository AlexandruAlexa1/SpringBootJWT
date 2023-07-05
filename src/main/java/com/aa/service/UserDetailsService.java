package com.aa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aa.domain.User;
import com.aa.domain.UserPrincipal;
import com.aa.repository.UserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException("Authentication error: User with E-mail: " + email + "could not be found.");
		}
		
		return new UserPrincipal(user);
	}

}
