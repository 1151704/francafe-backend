package com.app.francafebackend.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the detalle_factura database table.
 * 
 */
@Entity
@Table(name = "detalle_factura")
public class DetalleFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = true)
	private String observacion;

	@Column(nullable = false)
	private Integer cantidad = 0;

	@Column(nullable = false, name = "valor_total")
	private Double valorTotal = 0d;

	@Column(nullable = false, name = "valor_unidad")
	private Double valorUnidad = 0d;

	@Column(nullable = false, name = "valor_iva")
	private Double valorIva = 0d;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Bogota")
	private Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_actualizacion")
	@JsonIgnore
	private Date fechaActualizacion;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_factura", referencedColumnName = "id")
	@JsonBackReference
	private Factura factura;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_producto", referencedColumnName = "id")
	private Producto producto;

	@PrePersist
	protected void prePersist() {
		this.fechaRegistro = new Date();
	}

	@PreUpdate
	protected void preUpdate() {
		this.fechaActualizacion = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Double getValorUnidad() {
		return valorUnidad;
	}

	public void setValorUnidad(Double valorUnidad) {
		this.valorUnidad = valorUnidad;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Double getValorIva() {
		return valorIva;
	}

	public void setValorIva(Double valorIva) {
		this.valorIva = valorIva;
	}

}
