package com.app.francafebackend.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.Empresa;
import com.app.francafebackend.api.model.Usuario;


public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	public Usuario findByUsernameIgnoreCaseAndIdentificacion(String username, String identificacion);

	public Usuario findByUsernameIgnoreCase(String username);

	public Usuario findByIdentificacion(String identificacion);

	public Usuario findByIdentificacionAndEmpresa(String identificacion, Empresa empresa);

	public List<Usuario> findByEmpresa(Empresa empresa);

	public boolean existsByUsernameIgnoreCaseAndIdentificacion(String username, String identificacion);
	
}
