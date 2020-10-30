package com.app.francafebackend.api.data;

import java.io.Serializable;

public class UsuarioApi implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombres;
	private String apellidos;
	private String email;
	private String identificacion;
	private String password;
	private Boolean enable;
	private String tipoId;
	private String nit;
	private String rol;
	private Boolean auditor;

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Boolean getAuditor() {
		return auditor;
	}

	public void setAuditor(Boolean auditor) {
		this.auditor = auditor;
	}

}
