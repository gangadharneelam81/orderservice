package com.orderservice.service.jwt;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userName) 
			throws UsernameNotFoundException {
		//there should be some database logic
		if ("jwtuser".equals(userName)) {
			String password = userName;
			return new User(userName, password, new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("user not found with " + userName);
		}
	}

}

