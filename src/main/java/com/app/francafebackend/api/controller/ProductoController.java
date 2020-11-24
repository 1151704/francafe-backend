package com.app.francafebackend.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.francafebackend.api.model.Categoria;
import com.app.francafebackend.api.model.Producto;
import com.app.francafebackend.api.data.ProductoApi;
import com.app.francafebackend.api.service.CategoriaService;
import com.app.francafebackend.api.service.ProductoService;

import com.app.francafebackend.api.utils.ValidationException;

@RestController
@RequestMapping("api/producto")
public class ProductoController {

	@Autowired
	private ProductoService service;

	@Autowired
	private CategoriaService categoriaService;

	@Secured({ "ROLE_ADMIN", "ROLE_PROV" })
	@PostMapping("")
	public ResponseEntity<Producto> save(@RequestBody(required = false) ProductoApi entrada) {

		Producto producto = service.buscarPorCodigo(entrada.getCodigo());

		if (producto == null && entrada.getId() == null) {
			try {

				Categoria categoria = categoriaService.buscarPorIdentificador(entrada.getIdCategoria());
				
				if (entrada.getId() != null) {
					producto = service.buscarPorIdentificador(entrada.getId());
				} else {
					producto = new Producto();
				}
				
				producto.setCodigo(entrada.getCodigo());
				producto.setNombre(entrada.getNombre());
				producto.setDescripcion(entrada.getDescripcion());

				producto.setPrecio(entrada.getPrecio());
				producto.setEnable(entrada.getEnable());
				producto.setValorIva(entrada.getValorIva());

				producto.setCategoria(categoria);

				producto = service.guardar(producto);
				return new ResponseEntity<>(producto, HttpStatus.OK);
			} catch (Exception e) {
				throw new ValidationException("Error de formulario", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new ValidationException("El producto con codigo '" + entrada.getCodigo() + "' ya existe",
					HttpStatus.BAD_REQUEST);
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_EPS" })
	@GetMapping("byProducto/{codigo}")
	public Producto getProducto(@PathVariable String codigo) {
		return service.buscarPorCodigo(codigo);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_EPS" })
	@PostMapping("enableEdit/{codigo}/{enableNew}")
	public ResponseEntity<Boolean> editEstado(@PathVariable String codigo, @PathVariable Boolean enableNew) {
		Boolean hecho = false;

		Producto producto = service.buscarPorCodigo(codigo);

		if (producto != null) {

			if (enableNew != null && enableNew != producto.getEnable()) {

				producto.setEnable(enableNew);
				try {
					service.guardar(producto);
					hecho = true;
				} catch (Exception e) {
					throw new ValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);
				}

			} else {
				throw new ValidationException("El estado no ha cambiado", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new ValidationException("Producto inexistente", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(hecho, HttpStatus.OK);
	}

}
