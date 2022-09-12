package com.tatasky.mcr.constants;

/**
 * 
 * @author vikaschoudhary
 *
 */
public class SecurityConstants {

	public final static String AUTH_URL = "/auth";

	public static final String[] AUTH_WHITELIST = { "/v2/api-docs", "/v2/api-docs/**", "/configuration/**", "/swagger*/**",
			"/webjars/**" };
}
