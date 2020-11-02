package com.app.francafebackend.api.service;

import java.util.List;

import com.app.francafebackend.api.model.Empresa;

public interface EmpresaService {
	
	public Empresa guardar(Empresa entity);

	public Empresa buscarPorNit(String nit);

	public Empresa buscarPorIdentificador(Integer id);
	
	public List<Empresa> listarEmpresas();

}
