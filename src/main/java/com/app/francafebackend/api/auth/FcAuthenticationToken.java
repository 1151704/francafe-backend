package com.app.francafebackend.api.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class FcAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;
	private Object credentials;
	private Object details;

	public FcAuthenticationToken(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	public FcAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	public FcAuthenticationToken(Object principal, Object credentials, Object details,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.details = details;
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	public Object getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return this.principal;
	}

	public Object getDetails() {
		return this.details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	private static final long serialVersionUID = 1L;
}
