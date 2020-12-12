package com.app.francafebackend.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.Insumo;
import com.app.francafebackend.api.repository.InsumoRepository;
import com.app.francafebackend.api.service.InsumoService;

@Service
public class InsumoServiceImpl implements InsumoService {
	Logger logger = LoggerFactory.getLogger(InsumoServiceImpl.class);

	@Autowired
	private InsumoRepository repository;

	@Override
	public Insumo guardar(Insumo entity) {
		try {
			return repository.save(entity);
		} catch (Exception e) {
			logger.error("Error al registrar el insumo", e);
		}
		return null;
	}

	@Override
	public Insumo buscarPorIdentificador(Integer id) {
		try {
			return repository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.error("Busqueda insumo por id", e);
		}
		return null;
	}

	@Override
	public List<Insumo> listarInsumos() {
		try {
			return (List<Insumo>) repository.findAll();
		} catch (Exception e) {
			logger.error("Listar categorias", e);
		}
		return new ArrayList<>();
	}

}
