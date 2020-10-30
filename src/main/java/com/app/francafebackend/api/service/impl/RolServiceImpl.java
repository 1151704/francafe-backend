package com.app.francafebackend.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.Rol;
import com.app.francafebackend.api.repository.RolRepository;
import com.app.francafebackend.api.service.RolService;

@Service
public class RolServiceImpl implements RolService {
	Logger logger = LoggerFactory.getLogger(RolServiceImpl.class);

	@Autowired
	private RolRepository repository;

	@Override
	public Rol buscarPorRol(String rol) {
		try {
			return repository.findFirstByRol(rol);
		} catch (Exception e) {
			logger.error("Busqueda rol por nombre", e);
		}
		return null;
	}

	@Override
	public Rol buscarPorIdentificador(Integer id) {
		try {
			return repository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.error("Busqueda rol por id", e);
		}
		return null;
	}

	@Override
	public List<Rol> listarRoles() {
		try {
			return (List<Rol>) repository.findAll();
		} catch (Exception e) {
			logger.error("Listar roles", e);
		}
		return null;
	}

}
