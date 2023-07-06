package com.aa.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.aa.domain.HttpResponse;
import com.aa.utility.JsonResponseHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTForbiddenEntryPoint extends Http403ForbiddenEntryPoint {

	private static final String MESSAGE = "Access denied. You need to login to access this resource";

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException {
		HttpResponse httpResponse = new HttpResponse(HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value(), MESSAGE);
		JsonResponseHelper.writeJsonResponse(response, httpResponse, HttpStatus.FORBIDDEN);
	}
	
}
