package com.app.francafebackend.api.service;

import java.util.List;

import com.app.francafebackend.api.model.Rol;

public interface RolService {

	public Rol buscarPorRol(String rol);

	public Rol buscarPorIdentificador(Integer id);
	
	public List<Rol> listarRoles();
	
}
