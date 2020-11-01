package com.app.francafebackend.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.Cliente;
import com.app.francafebackend.api.repository.ClienteRepository;
import com.app.francafebackend.api.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
	Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteRepository repository;

	@Override
	public Cliente guardar(Cliente entity) {
		try {
			return repository.save(entity);
		} catch (Exception e) {
			logger.error("Error al registrar el cliente", e);
		}
		return null;
	}

	@Override
	public Cliente buscarPorIdentificacion(String identificacion) {
		try {
			return repository.findFirstByIdentificacion(identificacion);
		} catch (Exception e) {
			logger.error("Busqueda cliente por identificacion", e);
		}
		return null;
	}

	@Override
	public Cliente buscarPorIdentificador(Integer id) {
		try {
			return repository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.error("Busqueda cliente por id", e);
		}
		return null;
	}

}
