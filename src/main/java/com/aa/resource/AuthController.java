package com.aa.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aa.domain.AuthRequest;
import com.aa.domain.JWT;
import com.aa.domain.User;
import com.aa.domain.UserPrincipal;
import com.aa.exception.DuplicateEmailException;
import com.aa.exception.UserNotFoundException;
import com.aa.service.UserService;
import com.aa.utility.TokenProvider;

import jakarta.validation.Valid;
import static com.aa.constant.SecurityConstant.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private TokenProvider tokenProvider;
	@Autowired private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JWT> login(@RequestBody @Valid AuthRequest request) throws UserNotFoundException {
		authenticateUser(request.getEmail(), request.getPassword());
		
		String jwt = tokenProvider.generateJWT(new UserPrincipal(userService.findByEmail(request.getEmail())));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(JWT_HEADER, jwt);
		
		return new ResponseEntity<JWT>(new JWT(jwt), headers, HttpStatus.OK);
	}

	private void authenticateUser(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid User user) throws DuplicateEmailException {
		userService.register(user);
		
		return new ResponseEntity<>(REGISTATION_SUCCESS, HttpStatus.OK);
	}
	
}
