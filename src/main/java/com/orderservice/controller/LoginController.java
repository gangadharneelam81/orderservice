package com.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.model.JwtTokenResponse;
import com.orderservice.model.UserData;
import com.orderservice.service.jwt.JwtUserDetailsService;
import com.orderservice.util.JwtTokenUtil;

@RestController
public class LoginController {
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public JwtTokenResponse login(@RequestBody UserData userData) {
		//assumimg the authentication is success
		String jwtToken = jwtTokenUtil.generateToken(userData.getUsername());
		JwtTokenResponse response = new JwtTokenResponse();
		response.setJwttoken(jwtToken);
		System.out.println(jwtToken);
		return response;
	}

}
