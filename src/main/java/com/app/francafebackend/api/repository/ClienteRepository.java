package com.app.francafebackend.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

	Cliente findFirstByIdentificacion(String identificacion);

}
