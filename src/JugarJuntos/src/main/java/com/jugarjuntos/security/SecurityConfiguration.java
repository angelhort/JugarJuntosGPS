package com.jugarjuntos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	 http.csrf() 
				.disable()
				.authorizeRequests()
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated();
//	         .authorizeRequests()
//	         .antMatchers("/**").permitAll()
//	         .anyRequest().permitAll().and().formLogin().loginPage("/login").permitAll();                                          
	    }
}
