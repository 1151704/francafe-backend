package com.app.francafebackend.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.FormaPago;

public interface FormaPagoRepository extends CrudRepository<FormaPago, Integer> {

	public FormaPago findFirstByNombre(String nombre);

}
