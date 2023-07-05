package com.aa.domain;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class HttpResponse {

	private Date timeStamp;
	
	private HttpStatus status;
	
	private int statusCode;
	
	private String message;
	
	public HttpResponse() {}

	public HttpResponse(HttpStatus status, int statusCode, String message) {
		this.timeStamp = new Date();
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	}
