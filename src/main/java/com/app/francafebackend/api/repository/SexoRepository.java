package com.app.francafebackend.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.Sexo;

public interface SexoRepository extends CrudRepository<Sexo, Integer> {

	public Sexo findFirstByNombre(String nombre);

}
