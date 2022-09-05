package com.orderservice.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//utility class to generate and read the JWT token
@Component
public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//this method generates JWT token
	public String generateToken(String user) {
		Map<String, Object> claims = new HashMap<String,Object>();
		claims.put("role", "admin");
		return Jwts.builder().setClaims(claims)
				.setSubject(user)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+(3600*1000)))
				.signWith(SignatureAlgorithm.HS512, "secret").compact();
	}
	
	//this method reads username from the JWT token
	public String getUserNameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey("secret")
				.parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	//this method reads expiration date from the JWT token
	public Date getExpirationDateFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey("secret")
				.parseClaimsJws(token).getBody();
		return claims.getExpiration();
	}
	
	//this method reads issue date from the JWT token
	public Date getIssueDateFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey("secret")
				.parseClaimsJws(token).getBody();
		return claims.getIssuedAt();
	}
	

}
