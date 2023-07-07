package com.aa.domain;

import java.time.Instant;

import com.aa.constant.SecurityConstant;

public class JWT {

	private String jwt;
	
	private Instant expirationDate;
	
	public JWT() {}

	public JWT(String jwt) {
		this.jwt = jwt;
		this.expirationDate = Instant.now().plusMillis(SecurityConstant.EXPIRATION_TIME);
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public Instant getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Instant expirationDate) {
		this.expirationDate = expirationDate;
	}
}
