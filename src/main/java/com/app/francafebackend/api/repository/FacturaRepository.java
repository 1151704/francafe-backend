package com.app.francafebackend.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.Factura;
import com.app.francafebackend.api.model.Usuario;

public interface FacturaRepository extends CrudRepository<Factura, Integer> {

	public Factura findFirstByCodigo(String codigo);

	public List<Factura> findByFechaRegistroBetweenOrderByFechaRegistroAsc(Date start, Date end);

	public List<Factura> findByEmpleadoAndFechaRegistroBetweenOrderByFechaRegistroAsc(Usuario empleado, Date start,
			Date end);

	public List<Factura> findByEmpleadoOrderByFechaRegistroAsc(Usuario empleado);

}
