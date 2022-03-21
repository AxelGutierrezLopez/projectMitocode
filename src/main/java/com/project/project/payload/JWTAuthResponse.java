package com.project.project.payload;

import lombok.Data;

@Data
public class JWTAuthResponse {
	
	private String accessToken;
	private String tokenType="Bearer";
	
	
	public JWTAuthResponse(String accessToken) {
		this.accessToken=accessToken;
	}
}
