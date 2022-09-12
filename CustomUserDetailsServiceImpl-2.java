package com.tatasky.mcr.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tatasky.mcr.entity.User;
import com.tatasky.mcr.model.response.CustomUserDetails;
import com.tatasky.mcr.repository.UserRepository;
import com.tatasky.mcr.service.CustomUserDetailsService;

/**
 * 
 * @author vikaschoudhary
 *
 */
@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// admin = $2a$10$YHL5qkm7vKQ8oHwVR/ah9eqvwYklF7YrW9SW8LZWWIiZTJwfFZw8W

		Optional<User> user = userRepository.findByUserName(username);

		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

		return new CustomUserDetails(username, user.get().getPassword(), Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
				user.get().isEnabled(), new ArrayList<SimpleGrantedAuthority>());
	}

}
