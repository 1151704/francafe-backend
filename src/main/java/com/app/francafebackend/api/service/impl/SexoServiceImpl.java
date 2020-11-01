package com.app.francafebackend.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.Sexo;
import com.app.francafebackend.api.repository.SexoRepository;
import com.app.francafebackend.api.service.SexoService;

@Service
public class SexoServiceImpl implements SexoService {
	Logger logger = LoggerFactory.getLogger(SexoServiceImpl.class);

	@Autowired
	private SexoRepository repository;

	@Override
	public Sexo buscarPorNombre(String nombre) {
		try {
			return repository.findFirstByNombre(nombre);
		} catch (Exception e) {
			logger.error("Busqueda sexo por nombre", e);
		}
		return null;
	}

	@Override
	public Sexo buscarPorIdentificador(Integer id) {
		try {
			return repository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.error("Busqueda sexo por id", e);
		}
		return null;
	}

	@Override
	public List<Sexo> listarSexos() {
		try {
			return (List<Sexo>) repository.findAll();
		} catch (Exception e) {
			logger.error("Listar sexos", e);
		}
		return new ArrayList<>();
	}

}
