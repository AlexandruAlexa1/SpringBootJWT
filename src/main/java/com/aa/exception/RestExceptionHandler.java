package com.aa.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aa.domain.HttpResponse;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<HttpResponse> handleDuplicateEmailException(DuplicateEmailException ex) {
		HttpResponse response = new HttpResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		
		return new ResponseEntity<HttpResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<HttpResponse> handleUserNotFoundException(UserNotFoundException ex) {
		HttpResponse response = new HttpResponse(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), ex.getMessage());
		
		return new ResponseEntity<HttpResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<HttpResponse> handleBadCredentialsException(BadCredentialsException ex) {
		HttpResponse response = new HttpResponse(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
		
		return new ResponseEntity<HttpResponse>(response, HttpStatus.UNAUTHORIZED);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		
		List<String> listErrors = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
		
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("timeStamp", new Date());
		response.put("status", status);
		response.put("statusCode", status.value());
		response.put("message", listErrors);
		
		return ResponseEntity.status(status).headers(headers).body(response);
	}
}
