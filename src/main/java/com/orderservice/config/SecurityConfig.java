package com.orderservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.orderservice.filter.JwtRequestFilter;
import com.orderservice.service.jwt.JwtAuthenticationEntryPoint;
import com.orderservice.service.jwt.JwtUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
//WebSecurityConfigurerAdapter is a convenience class that 
//allows customization to both WebSecurity and HttpSecurity
//we will override default security features
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) 
			throws Exception {
		
		auth.userDetailsService(jwtUserDetailsService)
		.passwordEncoder(passwordEncoder());
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	/*
	 * @Override public void configure(WebSecurity web) throws Exception { web
	 * .ignoring() .antMatchers("/**.html"); }
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
        .ignoring()
        .antMatchers("/**");
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
		.antMatchers("/login").permitAll().anyRequest()
		.authenticated().and().exceptionHandling()
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//we will create one custom filter to validate JWT token
		
		httpSecurity.addFilterBefore(jwtRequestFilter, 
				UsernamePasswordAuthenticationFilter.class);
	}
	
	

}