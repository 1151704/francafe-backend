package com.app.francafebackend.api.service;

import java.util.List;

import com.app.francafebackend.api.model.FormaPago;

public interface FormaPagoService {

	public FormaPago buscarPorNombre(String nombre);

	public FormaPago buscarPorIdentificador(Integer id);

	public List<FormaPago> listarFormaPagos();

}
