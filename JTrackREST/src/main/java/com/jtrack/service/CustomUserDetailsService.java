package com.jtrack.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        
    	/*
    	 * Note:
		 * userId will not be null (validated by the login)
		 * password must be same as userId (case sensitive)
		 */
    	
    	boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
                
        com.jtrack.model.User user = userService.getUser(userId.toUpperCase());
        
        String pword = "";
        if(user != null) {
        	pword = user.getPword();
        }
        
        return new org.springframework.security.core.userdetails.User(
        				userId, 
        				passwordEncoder.encode(pword), 
                        enabled, 
                        accountNonExpired, 
                        credentialsNonExpired, 
                        accountNonLocked,
                        getAuthorities(userId));
    }
    
    public Collection<? extends GrantedAuthority> getAuthorities(String userId) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(userId));
        return authList;
    }

    public List<String> getRoles(String userId) {

        List<String> roles = new ArrayList<String>();

        if(userId != null && !userId.equals("")) {
        	if(userId.equalsIgnoreCase("ADMIN")) {
        		roles.add("USER");
                roles.add("ADMIN");
        	}else {
        		roles.add("USER");
        	}
        }     
        
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }
}
