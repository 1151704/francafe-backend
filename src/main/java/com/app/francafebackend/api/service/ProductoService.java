package com.app.francafebackend.api.service;

import java.util.List;

import com.app.francafebackend.api.model.Categoria;
import com.app.francafebackend.api.model.Producto;

public interface ProductoService {

	public Producto guardar(Producto entity);

	public Producto buscarPorCodigo(String codigo);

	public Producto buscarPorIdentificador(Integer id);

	public List<Producto> listarProductosPorNombreOrCodigo(String busqueda);

	public List<Producto> listarProductosPorNombreOrCodigoAndCategoria(String busqueda, Categoria categoria);

	public List<Producto> listar10ProductosPorNombreOrCodigo(String busqueda);

	public List<Producto> listar10ProductosPorNombreOrCodigoAndCategoria(String busqueda, Categoria categoria);

}
