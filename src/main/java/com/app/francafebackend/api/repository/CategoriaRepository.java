package com.app.francafebackend.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {

	public Categoria findFirstByNombre(String nombre);

}
