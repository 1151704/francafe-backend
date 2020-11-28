package com.app.francafebackend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfigurationSource;

import com.app.francafebackend.api.auth.FcAuthenticationManager;
import com.app.francafebackend.api.auth.filter.JWTAuthenticationFilter;
import com.app.francafebackend.api.auth.filter.JWTAuthorizationFilter;
import com.app.francafebackend.api.auth.service.JWTService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;	

	@Autowired
	private CorsConfigurationSource corsConfigurationSource;

	@Autowired
	private FcAuthenticationManager epsAuthenticationManager;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.cors().configurationSource(corsConfigurationSource).and()
				.csrf().disable().authorizeRequests().and()
				.authorizeRequests().antMatchers("/","/validate/", "/images/**", 
						"/files/**", "/favicon.ico", "/api/file/**", "/api/reportes/contabilidad/**/**").permitAll()
				.anyRequest()
				.authenticated().and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
				.addFilter(new JWTAuthenticationFilter(epsAuthenticationManager, jwtService))
				.addFilter(new JWTAuthorizationFilter(epsAuthenticationManager, jwtService));

		http.headers().frameOptions().disable();
	}
	
}
