package com.app.francafebackend.api.data;

import java.io.Serializable;

public class ClienteApi implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idTipo;

	private Integer idSexo;

	private String nombres;

	private String apellidos;

	private String identificacion;

	private String telefono;

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public Integer getIdSexo() {
		return idSexo;
	}

	public void setIdSexo(Integer idSexo) {
		this.idSexo = idSexo;
	}

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

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return "ClienteApi [idTipo=" + idTipo + ", idSexo=" + idSexo + ", nombres=" + nombres + ", apellidos="
				+ apellidos + ", identificacion=" + identificacion + ", telefono=" + telefono + "]";
	}

}
