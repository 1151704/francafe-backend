package com.app.francafebackend.api.service;

import java.util.List;

import com.app.francafebackend.api.model.Empresa;
import com.app.francafebackend.api.model.Usuario;

public interface UsuarioService {

	public List<Usuario> findAll();

	public List<Usuario> findByEmpresa(Empresa empresa);

	public Usuario save(Usuario entity);

	public Usuario findByUsernameAndIdentificacion(String username, String identificacion);

	public Usuario findByUsername(String username);

	public Usuario findByIdentificacion(String identificacion);

	public Usuario findByIdentificacionAndEmpresa(String identificacion, Empresa empresa);

	public boolean existByUsernameAndIdentificacion(String username, String identificacion);


}
