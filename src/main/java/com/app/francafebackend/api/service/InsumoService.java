package com.app.francafebackend.api.service;

import java.util.List;

import com.app.francafebackend.api.model.Insumo;

public interface InsumoService {

	public Insumo guardar(Insumo entity);

	public Insumo buscarPorIdentificador(Integer id);

	public List<Insumo> listarInsumos();

}
