package com.packtpub.jwt.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtResponse {
	private String token;

	public JwtResponse() { }

	public JwtResponse(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@JsonIgnore
	@Override
	public int hashCode() {
		return Objects.hash(token);
	}

	@JsonIgnore
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtResponse other = (JwtResponse) obj;
		return Objects.equals(token, other.token);
	}
	
	

}
