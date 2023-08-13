package com.packtpub.jwt.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.packtpub.jwt.service.CustomUserDetailsService;
import com.packtpub.jwt.util.JwtUtil;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	/**
	 * Does the following jobs related to JWT Token:
	 * 1. get JWT Token from request heade
	 * 2. validate it
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain) 
							throws ServletException, IOException {
		String username = null;
		String token = null;
		
		// get JWT Token from request header
		String bearerToken = request.getHeader("Authorization");
		// validate that JWT token
		if ( bearerToken != null && bearerToken.startsWith("Bearer ") ) {
			// extract jwt token from bearerToken
			token = bearerToken.replace("Bearer ", "");
			try {
				username = jwtUtil.extractUsername(token);
				UserDetails userDetails = userDetailsService
						.loadUserByUsername(username);
				//security checks
				if ( username != null && 
					SecurityContextHolder.getContext().getAuthentication() == null) {
					UsernamePasswordAuthenticationToken authToken = new
							UsernamePasswordAuthenticationToken(userDetails, null,
									userDetails.getAuthorities());
					authToken.setDetails( new WebAuthenticationDetailsSource()
												.buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
					
				} else {
					logger.error("Invalid Bearer Token Format error!");
//					throw new ServletException("JWTDemo: Invalid Bearer Token");
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else {
			logger.error("Invalid Bearer Token Format error!");
//			throw new ServletException("JWTDemo: Invalid Bearer Token");
		}
		
		filterChain.doFilter(request, response);
	}

	
	
}
