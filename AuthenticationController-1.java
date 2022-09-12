package com.tatasky.mcr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tatasky.mcr.constants.SecurityConstants;
import com.tatasky.mcr.model.request.AuthenticationRequest;
import com.tatasky.mcr.model.response.AuthenticationResponse;
import com.tatasky.mcr.service.CustomUserDetailsService;
import com.tatasky.mcr.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Validated
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(value = SecurityConstants.AUTH_URL, method = RequestMethod.POST)
	@ApiOperation(value = "Authentication API", notes = "API to autheticate User and will retrun user details & JWT")
	public ResponseEntity<?> authenticateAndGenerateToken(@RequestBody AuthenticationRequest authRequest) {
		log.debug("Inside authenticateAndGenerateToken method");

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		AuthenticationResponse authResponse = new AuthenticationResponse(jwt);
		log.debug("executed authenticateAndGenerateToken method");
		return ResponseEntity.ok(authResponse);
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "Vikas";
	}
}
