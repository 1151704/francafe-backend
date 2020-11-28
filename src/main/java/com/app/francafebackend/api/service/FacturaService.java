package com.app.francafebackend.api.service;

import java.util.Date;
import java.util.List;

import com.app.francafebackend.api.model.Factura;

public interface FacturaService {

	public Factura guardar(Factura entity);

	public Factura buscarPorCodigo(String codigo);

	public Factura buscarPorIdentificador(Integer id);

	public List<Factura> listarFacturasPorFecha(Date fechaFactura);

	public Double[] getValorTotalPorFecha(Date fechaFactura);

}
