package com.app.francafebackend.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.Factura;

public interface FacturaRepository extends CrudRepository<Factura, Integer> {

	public Factura findFirstByCodigo(String codigo);

	public List<Factura> findByFechaFactura(Date fechaFactura);

	@Query(value = "SELECT SUM(f.valorTotal), SUM(f.valorIva), SUM(f.valorNeto) FROM Factura f where f.fechaFactura=:fechaFactura")
	public List<Double[]> getValorTotalPorFecha(Date fechaFactura);

}
