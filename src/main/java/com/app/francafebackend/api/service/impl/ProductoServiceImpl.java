package com.app.francafebackend.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.francafebackend.api.model.Categoria;
import com.app.francafebackend.api.model.Producto;
import com.app.francafebackend.api.repository.ProductoRepository;
import com.app.francafebackend.api.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {
	Logger logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

	@Autowired
	private ProductoRepository repository;

	@Override
	public Producto guardar(Producto entity) {
		try {
			return repository.save(entity);
		} catch (Exception e) {
			logger.error("Error al registrar la factura", e);
		}
		return null;
	}

	@Override
	public Producto buscarPorCodigo(String codigo) {
		try {
			return repository.findFirstByCodigo(codigo);
		} catch (Exception e) {
			logger.error("Busqueda factura por codigo", e);
		}
		return null;
	}

	@Override
	public Producto buscarPorIdentificador(Integer id) {
		try {
			return repository.findById(id).orElse(null);
		} catch (Exception e) {
			logger.error("Busqueda factura por codigo", e);
		}
		return null;
	}

	@Override
	public List<Producto> listarProductosPorNombreOrCodigo(String busqueda) {
		try {
			return repository.findByCodigoContainingIgnoreCaseOrNombreContainingIgnoreCase(busqueda, busqueda);
		} catch (Exception e) {
			logger.error("Listar facturas por rango fechas", e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Producto> listarProductosPorCategoriaAndNombreOrCodigo(Categoria categoria, String busqueda) {
		try {
			return repository.findByCategoriaAndCodigoContainingIgnoreCaseOrNombreContainingIgnoreCase(categoria,
					busqueda, busqueda);
		} catch (Exception e) {
			logger.error("Listar facturas por rango fechas", e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Producto> listar10ProductosPorNombreOrCodigo(String busqueda) {
		try {
			return repository.findFirst10ByCodigoContainingIgnoreCaseOrNombreContainingIgnoreCase(busqueda, busqueda);
		} catch (Exception e) {
			logger.error("Listar facturas por rango fechas", e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Producto> listar10CategoriaAndProductosPorNombreOrCodigo(Categoria categoria, String busqueda) {
		try {
			return repository.findFirst10ByCategoriaAndCodigoContainingIgnoreCaseOrNombreContainingIgnoreCase(categoria,
					busqueda, busqueda);
		} catch (Exception e) {
			logger.error("Listar facturas por rango fechas", e);
		}
		return new ArrayList<>();
	}

}
