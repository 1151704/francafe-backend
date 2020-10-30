package com.app.francafebackend.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.TipoIdentificacion;

public interface TipoIdentificacionRepository extends CrudRepository<TipoIdentificacion, Integer> {

	public TipoIdentificacion findFirstByTipo(String tipo);

}
