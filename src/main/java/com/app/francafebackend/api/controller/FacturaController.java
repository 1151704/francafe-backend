package com.app.francafebackend.api.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.francafebackend.api.data.DetallePedidoApi;
import com.app.francafebackend.api.data.PedidoApi;
import com.app.francafebackend.api.model.Cliente;
import com.app.francafebackend.api.model.DetalleFactura;
import com.app.francafebackend.api.model.Empresa;
import com.app.francafebackend.api.model.Factura;
import com.app.francafebackend.api.model.FormaPago;
import com.app.francafebackend.api.model.Producto;
import com.app.francafebackend.api.model.Sexo;
import com.app.francafebackend.api.model.TipoIdentificacion;
import com.app.francafebackend.api.model.Usuario;
import com.app.francafebackend.api.service.ClienteService;
import com.app.francafebackend.api.service.EmpresaService;
import com.app.francafebackend.api.service.FacturaService;
import com.app.francafebackend.api.service.ProductoService;
import com.app.francafebackend.api.service.UsuarioService;
import com.app.francafebackend.api.utils.ValidationException;

@RestController
@RequestMapping("api/factura/")
public class FacturaController {

	@Autowired
	private FacturaService service;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ProductoService productoService;

	@Value("${francafe.empresa}")
	private String nitEmpresa;

	@PostMapping("")
	public ResponseEntity<Factura> save(@RequestBody(required = false) PedidoApi entrada) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Factura factura = new Factura();

			Empresa empresa = empresaService.buscarPorNit(nitEmpresa);
			Usuario empleado = usuarioService.findByUsername(authentication.getName());
			Cliente cliente = clienteService.buscarPorIdentificacion(entrada.getCliente().getIdentificacion());

			if (cliente == null) {
				cliente = new Cliente();

				cliente.setIdentificacion(entrada.getCliente().getIdentificacion());
			}
			cliente.setTipoId(new TipoIdentificacion(entrada.getCliente().getIdTipo()));
			cliente.setSexo(new Sexo(entrada.getCliente().getIdSexo()));
			cliente.setNombres(entrada.getCliente().getNombres().toUpperCase());
			cliente.setApellidos(entrada.getCliente().getApellidos().toUpperCase());
			cliente.setTelefono(entrada.getCliente().getTelefono());

			cliente = clienteService.guardar(cliente);

			DetalleFactura detalle;
			Set<DetalleFactura> detalles = new HashSet<>();

			Double valorTotal = 0d;
			Double valorIva = 0d;
			Double valorNeto = 0d;

			Producto producto;

			for (DetallePedidoApi item : entrada.getDetalles()) {
				detalle = new DetalleFactura();
				producto = productoService.buscarPorIdentificador(item.getIdProducto());

				detalle.setCantidad(item.getCantidad());
				detalle.setFactura(factura);
				detalle.setProducto(producto);
				detalle.setValorIva(producto.getValorIva() * item.getCantidad());
				detalle.setValorUnidad(producto.getPrecio());
				detalle.setValorTotal(producto.getPrecio() * item.getCantidad());

				valorNeto += detalle.getValorTotal();
				valorIva += detalle.getValorIva();

				detalles.add(detalle);
			}
			valorTotal = valorNeto + valorIva;

			factura.setDetalles(detalles);
			factura.setValorIva(valorIva);
			factura.setValorNeto(valorNeto);
			factura.setValorTotal(valorTotal);
			factura.setFormaPago(new FormaPago(entrada.getIdFormaPago()));
			factura.setEmpleado(empleado);
			factura.setCliente(cliente);
			factura.setCodigo(empresa.getPrefijoFactura() + (empresa.getNumFacturaActual() + 1));

			factura = service.guardar(factura);

			if (factura != null) {
				empresa.setNumFacturaActual(empresa.getNumFacturaActual() + 1);
				empresaService.guardar(empresa);
			}

			return new ResponseEntity<>(factura, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ValidationException("Error de formulario", HttpStatus.BAD_REQUEST);
		}
	}

}
