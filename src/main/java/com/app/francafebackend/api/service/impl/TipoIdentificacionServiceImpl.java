package com.app.francafebackend.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.TipoIdentificacion;
import com.app.francafebackend.api.repository.TipoIdentificacionRepository;
import com.app.francafebackend.api.service.TipoIdentificacionService;

@Service
public class TipoIdentificacionServiceImpl implements TipoIdentificacionService {
	Logger logger = LoggerFactory.getLogger(TipoIdentificacionServiceImpl.class);

	@Autowired
	private TipoIdentificacionRepository repository;

	@Override
	public TipoIdentificacion buscarPorTipo(String tipoIdentificacion) {
		try {
			return repository.findFirstByTipo(tipoIdentificacion);
		} catch (Exception e) {
			logger.error("Busqueda tipo identificacion por tipo", e);
		}
		return null;
	}

	@Override
	public TipoIdentificacion buscarPorIdentificador(Integer id) {
		try {
			return repository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.error("Busqueda tipo identificacion por nombre", e);
		}
		return null;
	}

	@Override
	public List<TipoIdentificacion> listarTiposIdentificacion() {
		try {
			return (List<TipoIdentificacion>) repository.findAll();
		} catch (Exception e) {
			logger.error("Listar tipos identificacion", e);
		}
		return null;
	}

}
