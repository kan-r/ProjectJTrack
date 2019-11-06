package com.jtrack.config;

import javax.annotation.Resource;

import com.jtrack.service.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	 
	@Resource
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	 public BCryptPasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure")
//                .failureUrl("/login?error=true")
                .and()
            .logout()
                .permitAll();
    }
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//            	.antMatchers("/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .defaultSuccessUrl("/hello")
//                .failureUrl("/login?error=true")
//                .and()
//            .logout()
//                .permitAll();
//    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(customUserDetailsService);
        
//    	auth
//          .inMemoryAuthentication()
//          .withUser("kan")
//            .password("{noop}kan")
//            .roles("USER")
//            .and()
//          .withUser("admin")
//            .password("{noop}admin")
//            .roles("USER", "ADMIN");
    }
}