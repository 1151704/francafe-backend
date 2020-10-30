package com.app.francafebackend.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, Integer> {

	public Empresa findFirstByNit(String nit);

}
