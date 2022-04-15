package com.jugarjuntos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.jugarjuntos.Entities.UsuarioDetalles.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

//	    @Override
//	    protected void configure(HttpSecurity http) throws Exception {
//	    	 http.csrf() 
//				.disable()
//				.authorizeRequests()
//				.antMatchers("/**").permitAll()
//				.anyRequest().authenticated();
//	    }
	@Autowired
    private DataSource dataSource;
     
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
    
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/jugarjuntos.css", "/", "/fragments", 
                			 "/login", "/registro", "/detalles", 
                			 "/getAnunciosPorNombre", "/getAnunciosOrderByTime","/getAnunciosOrderByValoracion").permitAll()
                .antMatchers("/enviarSolicitud").permitAll()
                .antMatchers("/registro").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
	                .loginProcessingUrl("/signin")
	                .loginPage("/login").permitAll()
	                .defaultSuccessUrl("/")
	                .usernameParameter("correo")
	                .passwordParameter("password")
                .and()
 //               .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll()
                .deleteCookies("JSESSIONID");
//                .and()
//                .rememberMe().tokenValiditySeconds(3600000).key("secret").rememberMeParameter("checkRememberMe");
    }
}
