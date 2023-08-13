package com.packtpub.jwt.model;

import java.util.Objects;

public class JwtRequest {
	
	private String username;
	private String password;
	
	/**
	 * Default constructor
	 */
	public JwtRequest() { }
	
	/**
	 * Parameterized constructor
	 * @param username
	 * @param password
	 */
	public JwtRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtRequest other = (JwtRequest) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}
	
}
