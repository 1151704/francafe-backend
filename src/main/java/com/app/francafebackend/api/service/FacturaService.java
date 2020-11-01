package com.app.francafebackend.api.service;

import java.util.Date;
import java.util.List;

import com.app.francafebackend.api.model.Factura;
import com.app.francafebackend.api.model.Usuario;

public interface FacturaService {

	public Factura guardar(Factura entity);

	public Factura buscarPorCodigo(String codigo);

	public Factura buscarPorIdentificador(Integer id);

	public List<Factura> listarFacturasPorRangoFechas(Date fechaInicio, Date fechaFinal);

	public List<Factura> listarFacturasPorEmpleadoAndRangoFechas(Usuario empleado, Date fechaInicio, Date fechaFinal);

	public List<Factura> listarFacturasPorEmpleado(Usuario empleado);

}
