package com.app.francafebackend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.francafebackend.api.model.Categoria;
import com.app.francafebackend.api.model.Cliente;
import com.app.francafebackend.api.model.FormaPago;
import com.app.francafebackend.api.model.Insumo;
import com.app.francafebackend.api.model.InsumoEntrada;
import com.app.francafebackend.api.model.Producto;
import com.app.francafebackend.api.model.Rol;
import com.app.francafebackend.api.model.Sexo;
import com.app.francafebackend.api.model.TipoIdentificacion;
import com.app.francafebackend.api.service.CategoriaService;
import com.app.francafebackend.api.service.ClienteService;
import com.app.francafebackend.api.service.FormaPagoService;
import com.app.francafebackend.api.service.InsumoEntradaService;
import com.app.francafebackend.api.service.InsumoService;
import com.app.francafebackend.api.service.ProductoService;
import com.app.francafebackend.api.service.RolService;
import com.app.francafebackend.api.service.SexoService;
import com.app.francafebackend.api.service.TipoIdentificacionService;

@RestController
@RequestMapping("api/datos/")
public class DataController {

	@Autowired
	private RolService rolService;

	@Autowired
	private FormaPagoService formaPagoService;

	@Autowired
	private TipoIdentificacionService tipoIdService;

	@Autowired
	private SexoService sexoService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ProductoService productoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private InsumoService insumosService;

	@Autowired
	private InsumoEntradaService insumosEntradaService;

	@GetMapping("cliente/{identificacion}")
	public ResponseEntity<Cliente> getCliente(@PathVariable String identificacion) {

		Cliente cliente = clienteService.buscarPorIdentificacion(identificacion);

		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@GetMapping("roles")
	public ResponseEntity<List<Rol>> getCliente() {

		List<Rol> roles = rolService.listarRoles();

		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	@GetMapping("tiposId")
	public ResponseEntity<List<TipoIdentificacion>> getTiposIdentificacion() {

		List<TipoIdentificacion> tiposId = tipoIdService.listarTiposIdentificacion();

		return new ResponseEntity<>(tiposId, HttpStatus.OK);
	}

	@GetMapping("sexos")
	public ResponseEntity<List<Sexo>> getSexo() {

		List<Sexo> sexos = sexoService.listarSexos();

		return new ResponseEntity<>(sexos, HttpStatus.OK);
	}

	@GetMapping("insumos")
	public ResponseEntity<List<Insumo>> getInsumos() {

		List<Insumo> sexos = insumosService.listarInsumos();

		return new ResponseEntity<>(sexos, HttpStatus.OK);
	}

	@GetMapping("insumos/{idInsumo}")
	public ResponseEntity<List<InsumoEntrada>> getInsumosEntrada(@PathVariable Integer idInsumo) {

		List<InsumoEntrada> sexos = insumosEntradaService.listarPorInsumo(idInsumo);

		return new ResponseEntity<>(sexos, HttpStatus.OK);
	}

	@GetMapping("categorias")
	public ResponseEntity<List<Categoria>> getCategorias() {

		List<Categoria> categorias = categoriaService.listarCategorias();

		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}

	@GetMapping("formasPago")
	public ResponseEntity<List<FormaPago>> getFormasPago() {

		List<FormaPago> formasPago = formaPagoService.listarFormaPagos();

		return new ResponseEntity<>(formasPago, HttpStatus.OK);
	}

	@GetMapping("productos/{busqueda}/{idCategoria}")
	public ResponseEntity<List<Producto>> getProductos(@PathVariable String busqueda,
			@PathVariable Integer idCategoria) {

		Categoria categoria = null;

		if (!idCategoria.equals(0)) {
			categoria = categoriaService.buscarPorIdentificador(idCategoria);
		}

		List<Producto> productos;

		if (categoria != null) {
			productos = productoService.listar10CategoriaAndProductosPorNombreOrCodigo(categoria, busqueda);
		} else {
			productos = productoService.listar10ProductosPorNombreOrCodigo(busqueda);
		}

		return new ResponseEntity<>(productos, HttpStatus.OK);
	}

	@GetMapping("productos/{idCategoria}")
	public ResponseEntity<List<Producto>> getProductosByCategoria(@PathVariable Integer idCategoria) {

		Categoria categoria = null;

		if (!idCategoria.equals(0)) {
			categoria = categoriaService.buscarPorIdentificador(idCategoria);
		}

		List<Producto> productos;

		if (categoria != null) {
			productos = productoService.listarProductosPorCategoria(categoria);
		} else {
			productos = productoService.listarProductos();
		}

		return new ResponseEntity<>(productos, HttpStatus.OK);
	}

}
