package com.app.francafebackend.api.auth.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.francafebackend.api.auth.ConstantsAuth;
import com.app.francafebackend.api.auth.FcAuthenticationToken;
import com.app.francafebackend.api.auth.UserLogin;
import com.app.francafebackend.api.auth.service.JWTService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private JWTService jwtService;

	private UserLogin credenciales = null;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);

			credenciales = mapper.readValue(request.getInputStream(), UserLogin.class);

			return authenticationManager.authenticate(new FcAuthenticationToken(credenciales.getUsername(),
					credenciales.getPassword(), new HashMap<>(), new ArrayList<>()));

			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = jwtService.create(authResult);

		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) authResult.getDetails();

		response.addHeader(ConstantsAuth.HEADER_AUTHORIZACION_KEY, ConstantsAuth.TOKEN_BEARER_PREFIX + token);

		Map<String, Object> body = new HashMap<>();

		body.put("usuario", data.get("token"));
		body.put("type", ConstantsAuth.TOKEN_BEARER_PREFIX.replace(" ", ""));
		body.put("accessToken", token);

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		Map<String, Object> body = new HashMap<>();

		body.put("message", "Acceso denegado");
		body.put("error", failed.getMessage());

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");

	}

}
