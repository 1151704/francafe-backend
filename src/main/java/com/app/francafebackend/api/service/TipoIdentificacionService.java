package com.app.francafebackend.api.service;

import java.util.List;

import com.app.francafebackend.api.model.TipoIdentificacion;

public interface TipoIdentificacionService {

	public TipoIdentificacion buscarPorTipo(String tipoIdentificacion);

	public TipoIdentificacion buscarPorIdentificador(Integer id);

	public List<TipoIdentificacion> listarTiposIdentificacion();

}
