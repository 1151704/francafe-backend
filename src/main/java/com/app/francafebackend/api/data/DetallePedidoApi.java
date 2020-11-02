package com.app.francafebackend.api.data;

import java.io.Serializable;

public class DetallePedidoApi implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idProducto;

	private Integer cantidad;

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "DetallePedidoApi [idProducto=" + idProducto + ", cantidad=" + cantidad + "]";
	}

}
