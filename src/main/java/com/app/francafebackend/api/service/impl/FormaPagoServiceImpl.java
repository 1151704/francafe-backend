package com.app.francafebackend.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.FormaPago;
import com.app.francafebackend.api.repository.FormaPagoRepository;
import com.app.francafebackend.api.service.FormaPagoService;

@Service
public class FormaPagoServiceImpl implements FormaPagoService {
	Logger logger = LoggerFactory.getLogger(FormaPagoServiceImpl.class);

	@Autowired
	private FormaPagoRepository repository;

	@Override
	public FormaPago buscarPorNombre(String nombre) {
		try {
			return repository.findFirstByNombre(nombre);
		} catch (Exception e) {
			logger.error("Busqueda formas pago por nombre", e);
		}
		return null;
	}

	@Override
	public FormaPago buscarPorIdentificador(Integer id) {
		try {
			return repository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.error("Busqueda formas pago por nit", e);
		}
		return null;
	}

	@Override
	public List<FormaPago> listarFormaPagos() {
		try {
			return (List<FormaPago>) repository.findAll();
		} catch (Exception e) {
			logger.error("Listar formas pago", e);
		}
		return new ArrayList<>();
	}

}
