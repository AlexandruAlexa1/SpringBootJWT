package com.aa.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.aa.domain.HttpResponse;
import com.aa.utility.JsonResponseHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

	private static final String MESSAGE = "Access denied. You do not have sufficient permissions to access this resource";

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		HttpResponse httpResponse = new HttpResponse(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), MESSAGE);
		JsonResponseHelper.writeJsonResponse(response, httpResponse, HttpStatus.UNAUTHORIZED);
	}

}
