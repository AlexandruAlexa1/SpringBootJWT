package com.aa.constant;

public class SecurityConstant {

	public static final String AUTHORITIES = "authorities";
	public static final long EXPIRATION_TIME = 604_800_000; // 7 Days
	public static final String JWT_SECRET = "AA10^Ey$y7T^tYGqpe!U@8G*V*S5SmXyYC4zWSRx+9*HyR#)tvS#Xj#D#uzF)10G";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
	public static final String TOKEN_REQUIRED = "Token is required";
	public static final String SUBJECT_REQUIRED = "Subject is required";
	public static final String OPTIONS_METHOD = "OPTIONS";
	public static final String[] PUBLIC_URLS = { "/auth/login", "/auth/register" };
	public static final String JWT_PREFIX = "Bearer ";
	public static final String JWT_HEADER = "JWT";
	public static final String REGISTATION_SUCCESS = "Registration completed successfully.";
}
 