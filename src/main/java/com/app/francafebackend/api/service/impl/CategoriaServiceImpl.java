package com.app.francafebackend.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.Categoria;
import com.app.francafebackend.api.repository.CategoriaRepository;
import com.app.francafebackend.api.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	Logger logger = LoggerFactory.getLogger(CategoriaServiceImpl.class);

	@Autowired
	private CategoriaRepository repository;

	@Override
	public Categoria guardar(Categoria entity) {
		try {
			return repository.save(entity);
		} catch (Exception e) {
			logger.error("Error al registrar la categoria", e);
		}
		return null;
	}

	@Override
	public Categoria buscarPorNombre(String nombre) {
		try {
			return repository.findFirstByNombre(nombre);
		} catch (Exception e) {
			logger.error("Busqueda categoria por nombre", e);
		}
		return null;
	}

	@Override
	public Categoria buscarPorIdentificador(Integer id) {
		try {
			return repository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.error("Busqueda categoria por id", e);
		}
		return null;
	}

	@Override
	public List<Categoria> listarCategorias() {
		try {
			return (List<Categoria>) repository.findAll();
		} catch (Exception e) {
			logger.error("Listar categorias", e);
		}
		return new ArrayList<>();
	}

}
