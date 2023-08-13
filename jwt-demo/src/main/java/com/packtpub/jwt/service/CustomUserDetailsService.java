package com.packtpub.jwt.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class does the job of loading user data
 * 
 * @author paulorf
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws 
											UsernameNotFoundException {
		User user = null;	
		if (username.equals("paulorf")) {			
			// here one can make a database call through a repo to get credentials
			user = new User("paulorf", "secret", new ArrayList<>());
			
		} else {
			throw new UsernameNotFoundException("This user does not exist");
		}
		return user;
	}
	
}
