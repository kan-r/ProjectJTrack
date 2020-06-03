package com.jtrack.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import com.jtrack.service.CustomUserDetailsService;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends WebSecurityConfigurerAdapter implements AuthorizationServerConfigurer {
	
	@Resource
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	 /**
     * See: https://github.com/spring-projects/spring-boot/issues/11136
     *
     * @return
     * @throws Exception
     */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}    
	
	private int expirationInSecs = 3600;
	
	/**
     * Setting up the endpointsconfigurer authentication manager.
     * The AuthorizationServerEndpointsConfigurer defines the authorization and token endpoints and the token services.
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(customUserDetailsService);
    }

    /**
     * Setting up the clients with a clientId, a clientSecret, a scope, the grant types and the authorities.
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
        	.inMemory()
        	.withClient("jtrackAdmin")
	        	.secret(passwordEncoder.encode("admin"))
	        	.authorizedGrantTypes("password", "refresh_toekn")
	        	.accessTokenValiditySeconds(expirationInSecs)
	        	.resourceIds("oauth2-resource")
	        	.scopes("read","write")
        	.and()
        	.withClient("jTrackUser")
	        	.secret(passwordEncoder.encode("user"))
	        	.authorizedGrantTypes("password", "refresh_toekn")
	        	.accessTokenValiditySeconds(expirationInSecs)
	        	.resourceIds("oauth2-resource")
	        	.scopes("read");
    }

    /**
     * We here defines the security constraints on the token endpoint.
     * We set it up to isAuthenticated, which returns true if the user is not anonymous
     * @param security the AuthorizationServerSecurityConfigurer.
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");
    }


    /**
     * No security check for /loginHist
     */
    @Override
    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/loginHist");
		
		web.ignoring().antMatchers(
        		"/loginHist", 
        		"/pageVisitHist", 
        		"/pageVisitHist/**", 
        		"/pageVisitCount",
        		"/pageVisitCount/**");
       
//        web.ignoring().anyRequest();
    }
}
