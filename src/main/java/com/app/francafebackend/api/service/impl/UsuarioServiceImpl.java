package com.app.francafebackend.api.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.Empresa;
import com.app.francafebackend.api.model.Usuario;
import com.app.francafebackend.api.repository.UsuarioRepository;
import com.app.francafebackend.api.service.UsuarioService;


@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public List<Usuario> findAll() {
		try {
			ArrayList<Usuario> usuarios = (ArrayList<Usuario>) repository.findAll();
			Comparator<Usuario> compareById = (Usuario o1, Usuario o2) -> o1.getIdentificacion().compareTo( o2.getIdentificacion() );
			Collections.sort(usuarios, compareById);
			return usuarios;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Usuario> findByEmpresa(Empresa empresa) {
		try {
			List<Usuario> usuarios = repository.findByEmpresa(empresa);
			Comparator<Usuario> compareById = (Usuario o1, Usuario o2) -> o1.getIdentificacion().compareTo( o2.getIdentificacion() );
			Collections.sort(usuarios, compareById);
			return usuarios;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Usuario save(Usuario entity) {
		try {

			entity.setApellidos(entity.getApellidos().toUpperCase());
			entity.setNombres(entity.getNombres().toUpperCase());
			entity.setUsername(entity.getUsername().toUpperCase());

			return repository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Usuario findByUsernameAndIdentificacion(String username, String identificacion) {
		try {
			return repository.findByUsernameIgnoreCaseAndIdentificacion(username, identificacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Usuario findByUsername(String username) {
		try {
			return repository.findByUsernameIgnoreCase(username);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public Usuario findByIdentificacion(String identificacion) {
		try {
			return repository.findByIdentificacion(identificacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean existByUsernameAndIdentificacion(String username, String identificacion) {
		try {
			return repository.existsByUsernameIgnoreCaseAndIdentificacion(username, identificacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Usuario findByIdentificacionAndEmpresa(String identificacion, Empresa empresa) {
		try {
			return repository.findByIdentificacionAndEmpresa(identificacion, empresa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
