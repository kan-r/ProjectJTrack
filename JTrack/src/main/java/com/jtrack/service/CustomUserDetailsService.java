/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtrack.service;

import com.jtrack.model.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kan
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    @Resource
    private UsersService usersService;
    
//    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        int role;
                
        Users user = usersService.get(userId.toUpperCase());
        usersService.user = user;
        
        if(user.getUserId().equalsIgnoreCase("admin")){
            role = 1;
        }else{
            role = 2;
        }
        
        return new User(
                        user.getUserId(), 
                        user.getUserId().toLowerCase(), 
                        enabled, 
                        accountNonExpired, 
                        credentialsNonExpired, 
                        accountNonLocked,
                        getAuthorities(role));
    }
    
    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List<String> getRoles(Integer role) {

        List<String> roles = new ArrayList<String>();

        if (role.intValue() == 1) {
                roles.add("ROLE_USER");
                roles.add("ROLE_ADMIN");
        } else if (role.intValue() == 2) {
                roles.add("ROLE_USER");
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
