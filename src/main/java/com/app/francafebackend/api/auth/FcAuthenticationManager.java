package com.app.francafebackend.api.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.Usuario;
import com.app.francafebackend.api.service.UsuarioService;

@Service
public class FcAuthenticationManager implements AuthenticationManager {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();

		@SuppressWarnings("unchecked")
		HashMap<String, Object> details = (HashMap<String, Object>) authentication.getDetails();

		Usuario respuesta = usuarioService.findByUsername(username);

		if (respuesta != null) {
			if (respuesta.getRol() == null) {
				throw new BadCredentialsException("No cuenta con ningún rol");
			}

			if (!respuesta.getEnable()) {
				throw new BadCredentialsException("Usuario inhabilitado");
			}
			if (respuesta.getEmpresa() != null && !respuesta.getEmpresa().getActivo()) {
				throw new BadCredentialsException("Empresa inhabilitada");
			}
		}

		if (respuesta == null || !passwordEncoder.matches(password, respuesta.getPassword())) {
			throw new BadCredentialsException("Contraseña o nombre de usuario invalidos");
		}

		details = new HashMap<>();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		details.put("token", respuesta);

		authorities.add(new SimpleGrantedAuthority(respuesta.getRol().getRol()));
		details.put("role", respuesta.getRol().getRol());

		return new FcAuthenticationToken(username, password, details, authorities);
	
	}

}
