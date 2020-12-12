package com.app.francafebackend.api.service;

import java.util.Date;
import java.util.List;

import com.app.francafebackend.api.model.InsumoEntrada;

public interface InsumoEntradaService {

	public InsumoEntrada guardar(InsumoEntrada entity);
	
	public List<InsumoEntrada> listarPorInsumo(Integer idInsumo);
	
	public Double getValorTotalPorFecha(Date fechaIngreso);

}
