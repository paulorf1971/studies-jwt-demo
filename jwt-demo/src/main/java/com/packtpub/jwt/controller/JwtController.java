package com.packtpub.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packtpub.jwt.model.JwtRequest;
import com.packtpub.jwt.model.JwtResponse;
import com.packtpub.jwt.service.CustomUserDetailsService;
import com.packtpub.jwt.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class JwtController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping("/generateToken")
	public ResponseEntity<JwtResponse> generateToken(
					@RequestBody JwtRequest jwtRequest){
		// authenticate user
		UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(
									jwtRequest.getUsername(),
									jwtRequest.getPassword());
		Authentication auth = authenticationManager
				.authenticate(authToken);
		UserDetails userDetails = customUserDetailsService
				.loadUserByUsername(jwtRequest.getUsername());		
		String jwtToken = jwtUtil.generateToken(userDetails);
		JwtResponse jwtResponse = new JwtResponse(jwtToken); 
		
		return ResponseEntity.ok(jwtResponse);
	}
	
}
