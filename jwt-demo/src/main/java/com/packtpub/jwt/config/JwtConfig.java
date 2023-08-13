package com.packtpub.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.packtpub.jwt.service.CustomUserDetailsService;
import com.packtpub.jwt.web.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class JwtConfig extends WebSecurityConfigurerAdapter {	
	
	@Autowired
	private CustomUserDetailsService customUserService;
	
	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	
	/**
	 * How we want to manage authentication 
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService);
	}
	
	
	/**
	 * This method will control which endpoints are permitted and which ones
	 * are not permitted
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http			
			.csrf()
				.disable()
			.cors()
				.disable()
			.authorizeRequests()
				.antMatchers("/api/generateToken").permitAll()
			.anyRequest()
				.authenticated()
				.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//		super.configure(http);
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
//		return super.authenticationManagerBean();
		return super.authenticationManager();
	}

}
