package com.app.francafebackend.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.InsumoEntrada;

public interface InsumoEntradaRepository extends CrudRepository<InsumoEntrada, Integer> {

	@Query(value = "SELECT SUM(f.valorTotal) FROM InsumoEntrada f where f.fechaIngreso=:fechaIngreso")
	public List<Double> getValorTotalPorFecha(Date fechaIngreso);
	
	@Query(value = "select * from insumo_entrada where insumo = :insumo", nativeQuery = true)
	public List<InsumoEntrada> findByInsumo(Integer insumo);

}
