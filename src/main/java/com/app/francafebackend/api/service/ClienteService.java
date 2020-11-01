package com.app.francafebackend.api.service;

import com.app.francafebackend.api.model.Cliente;

public interface ClienteService {

	public Cliente guardar(Cliente entity);

	public Cliente buscarPorIdentificacion(String identificacion);

	public Cliente buscarPorIdentificador(Integer id);

}
