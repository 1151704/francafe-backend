package com.app.francafebackend.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.InsumoEntrada;
import com.app.francafebackend.api.repository.InsumoEntradaRepository;
import com.app.francafebackend.api.service.InsumoEntradaService;

@Service
public class InsumoEntradaServiceImpl implements InsumoEntradaService {
	Logger logger = LoggerFactory.getLogger(InsumoEntradaServiceImpl.class);

	@Autowired
	private InsumoEntradaRepository repository;

	@Override
	public InsumoEntrada guardar(InsumoEntrada entity) {
		try {
			return repository.save(entity);
		} catch (Exception e) {
			logger.error("Registrar insumo de entrada", e);
		}
		return null;
	}
	
	@Override
	public Double getValorTotalPorFecha(Date fechaIngreso) {
		try {
			return repository.getValorTotalPorFecha(fechaIngreso).get(0);
		} catch (Exception e) {
			logger.error("Busqueda insumo por fecha", e);
		}
		return null;
	}
	
	@Override
	public List<InsumoEntrada> listarPorInsumo(Integer idInsumo) {
		try {
			return repository.findByInsumo(idInsumo);
		} catch (Exception e) {
			logger.error("Busqueda insumo por insumo", e);
		}				
		return new ArrayList<>();
	}

}
