package com.app.francafebackend.api.data;

import java.io.Serializable;
import java.util.Date;

public class ValorDiarioData implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date fechaFactura;
	private Double valorTotal;
	private Double valorTotalIva;
	private Double valorTotalNeto;

	public ValorDiarioData(Date fechaFactura, Double valorTotal, Double valorTotalIva, Double valorTotalNeto) {
		super();
		this.fechaFactura = fechaFactura;
		this.valorTotal = valorTotal;
		this.valorTotalIva = valorTotalIva;
		this.valorTotalNeto = valorTotalNeto;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getValorTotalIva() {
		return valorTotalIva;
	}

	public void setValorTotalIva(Double valorTotalIva) {
		this.valorTotalIva = valorTotalIva;
	}

	public Double getValorTotalNeto() {
		return valorTotalNeto;
	}

	public void setValorTotalNeto(Double valorTotalNeto) {
		this.valorTotalNeto = valorTotalNeto;
	}

}
