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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The persistent class for the insumo_entrada database table.
 * 
 */
@Entity
@Table(name = "insumo_entrada")
public class InsumoEntrada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, name = "valor_unit")
	private Double valorUnt;

	@Column(nullable = false, name = "valor_total")
	private Double valorTotal;

	@Column(name = "unidades")
	private Double unidades;

	@Column(name = "es_nuevo")
	private Boolean esNuevo = false;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_ingreso", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Bogota")
	private Date fechaIngreso;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Bogota")
	private Date fechaRegistro;

	@ManyToOne(optional = false)
	@JoinColumn(name = "insumo", referencedColumnName = "id")
	private Insumo insumo;

	@PrePersist
	protected void prePersist() {
		this.valorTotal = this.valorUnt * this.unidades;
		this.fechaRegistro = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValorUnt() {
		return valorUnt;
	}

	public void setValorUnt(Double valorUnt) {
		this.valorUnt = valorUnt;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getUnidades() {
		return unidades;
	}

	public void setUnidades(Double unidades) {
		this.unidades = unidades;
	}

	public Boolean getEsNuevo() {
		return esNuevo;
	}

	public void setEsNuevo(Boolean esNuevo) {
		this.esNuevo = esNuevo;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}

}
