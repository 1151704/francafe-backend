package com.app.francafebackend.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.francafebackend.api.model.Categoria;
import com.app.francafebackend.api.model.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer> {

	public Producto findFirstByCodigo(String codigo);

	public List<Producto> findFirst10ByCodigoContainingIgnoreCaseOrNombreContainingIgnoreCase(String codigo,
			String nombre);

	public List<Producto> findByCodigoContainingIgnoreCaseOrNombreContainingIgnoreCase(String codigo, String nombre);

	public List<Producto> findFirst10ByCategoriaAndCodigoContainingIgnoreCaseOrNombreContainingIgnoreCase(
			Categoria categoria, String codigo, String nombre);

	public List<Producto> findByCategoriaAndCodigoContainingIgnoreCaseOrNombreContainingIgnoreCase(Categoria categoria,
			String codigo, String nombre);

	public List<Producto> findByCategoria(Categoria categoria);
}
