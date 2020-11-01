package com.app.francafebackend.api.service;

import java.util.List;

import com.app.francafebackend.api.model.Categoria;

public interface CategoriaService {

	public Categoria guardar(Categoria entity);

	public Categoria buscarPorNombre(String nombre);

	public Categoria buscarPorIdentificador(Integer id);

	public List<Categoria> listarCategorias();

}
