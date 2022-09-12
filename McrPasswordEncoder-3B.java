package com.tatasky.mcr.util;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * Password hashing generator with salt base on BCrypt Algo
 * 
 * @author vikaschoudhary
 *
 */
public class McrPasswordEncoder extends BCryptPasswordEncoder {

	private final static int strength = 10;

	public McrPasswordEncoder() {
		this(strength, new SecureRandom());
	}

	public McrPasswordEncoder(int strength, SecureRandom random) {
		super(strength, random);
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return super.encode(rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return super.matches(rawPassword, encodedPassword);
	}

}
