package com.app.francafebackend.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.Rol;

public interface RolRepository extends CrudRepository<Rol, Integer> {

	public Rol findFirstByRol(String rol);

}
