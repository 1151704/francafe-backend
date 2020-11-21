package com.app.francafebackend.api.controller;

import java.util.Date;

import org.aspectj.weaver.patterns.TypeCategoryTypePattern;
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
import com.app.francafebackend.api.model.Rol;
import com.app.francafebackend.api.model.Usuario;
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
		

		if (producto == null) {
			try {
				Producto productoSave;
				
				Categoria categoria = categoriaService.buscarPorIdentificador(entrada.getId_categoria());


				producto = new Producto();
				producto.setCodigo(entrada.getCodigo());
				producto.setNombre(entrada.getNombre());
				producto.setDescripcion(entrada.getDescripcion());

				producto.setPrecio(entrada.getPrecio());
				producto.setEnable(entrada.getEnable());
				producto.setFechaActualizacion(new Date());
				producto.setValorIva(0.0);

				producto.setCategoria(categoria);
				

			

				productoSave = service.guardar(producto);
				return new ResponseEntity<>(productoSave, HttpStatus.OK);
			} catch (Exception e) {
				throw new ValidationException("Error de formulario", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new ValidationException(
					"El producto con codigo '" + entrada.getCodigo()+ "' ya existe",
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@Secured({ "ROLE_ADMIN" })
	@PostMapping("update")
	public ResponseEntity<Producto> update(@RequestBody(required = false) ProductoApi entrada) {

		if (entrada != null && entrada.getCodigo() != null && !entrada.getCodigo().isEmpty()) {

			Producto producto = service.buscarPorCodigo(entrada.getCodigo());

			if (producto != null) {

				Categoria categoria = producto.getCategoria();
				

				Producto productoEdit = new Producto();

				// VALORES NO CAMBIANTES
				productoEdit.setId(producto.getId());
				productoEdit.setFechaRegistro(producto.getFechaRegistro());

				// VALORES CAMBIANTES
				productoEdit.setCodigo(entrada.getCodigo());
				productoEdit.setEnable(entrada.getEnable());
				productoEdit.setCategoria(categoria);
				productoEdit.setDescripcion(entrada.getDescripcion());
				productoEdit.setNombre(entrada.getNombre());
				productoEdit.setPrecio(entrada.getPrecio());
				productoEdit.setValorIva(entrada.getValor_iva());
				productoEdit.setFechaActualizacion(new Date());
				

				try {
					productoEdit = service.guardar(productoEdit);
				} catch (Exception e) {
					throw new ValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>(productoEdit, HttpStatus.OK);
			} else {
				throw new ValidationException(
						"El usuario con identificación '" + entrada.getCodigo() + "' no existe",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new ValidationException("La identificación '" + entrada.getCodigo() + "' es invalida",
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
