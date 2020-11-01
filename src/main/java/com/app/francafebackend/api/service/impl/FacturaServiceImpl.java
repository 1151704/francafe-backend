package com.app.francafebackend.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.Factura;
import com.app.francafebackend.api.model.Usuario;
import com.app.francafebackend.api.repository.FacturaRepository;
import com.app.francafebackend.api.service.FacturaService;

@Service
public class FacturaServiceImpl implements FacturaService {
	Logger logger = LoggerFactory.getLogger(FacturaServiceImpl.class);

	@Autowired
	private FacturaRepository repository;

	@Override
	public Factura guardar(Factura entity) {
		try {
			return repository.save(entity);
		} catch (Exception e) {
			logger.error("Error al registrar la factura", e);
		}
		return null;
	}

	@Override
	public Factura buscarPorCodigo(String codigo) {
		try {
			return repository.findFirstByCodigo(codigo);
		} catch (Exception e) {
			logger.error("Busqueda factura por codigo", e);
		}
		return null;
	}

	@Override
	public Factura buscarPorIdentificador(Integer id) {
		try {
			return repository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.error("Busqueda factura por id", e);
		}
		return null;
	}

	@Override
	public List<Factura> listarFacturasPorRangoFechas(Date fechaInicio, Date fechaFinal) {
		try {
			return repository.findByFechaRegistroBetweenOrderByFechaRegistroAsc(fechaInicio, fechaFinal);
		} catch (Exception e) {
			logger.error("Listar facturas por rango fechas", e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Factura> listarFacturasPorEmpleadoAndRangoFechas(Usuario empleado, Date fechaInicio, Date fechaFinal) {
		try {
			return repository.findByEmpleadoAndFechaRegistroBetweenOrderByFechaRegistroAsc(empleado, fechaInicio,
					fechaFinal);
		} catch (Exception e) {
			logger.error("Listar facturas por rango de fechas y empleado", e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Factura> listarFacturasPorEmpleado(Usuario empleado) {
		try {
			return repository.findByEmpleadoOrderByFechaRegistroAsc(empleado);
		} catch (Exception e) {
			logger.error("Listar facturas por empleado", e);
		}
		return new ArrayList<>();
	}

}
