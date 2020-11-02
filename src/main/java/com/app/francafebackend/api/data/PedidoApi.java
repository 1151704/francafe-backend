package com.app.francafebackend.api.data;

import java.io.Serializable;
import java.util.Arrays;

public class PedidoApi implements Serializable {
	private static final long serialVersionUID = 1L;

	private DetallePedidoApi[] detalles;

	private ClienteApi cliente;

	private Integer idFormaPago;

	public DetallePedidoApi[] getDetalles() {
		return detalles;
	}

	public void setDetalles(DetallePedidoApi[] detalles) {
		this.detalles = detalles;
	}

	public ClienteApi getCliente() {
		return cliente;
	}

	public void setCliente(ClienteApi cliente) {
		this.cliente = cliente;
	}

	public Integer getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(Integer idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	@Override
	public String toString() {
		return "PedidoApi [detalles=" + Arrays.toString(detalles) + ", cliente=" + cliente + ", idFormaPago="
				+ idFormaPago + "]";
	}

}
