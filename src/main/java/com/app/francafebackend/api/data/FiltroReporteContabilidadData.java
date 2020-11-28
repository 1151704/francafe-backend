package com.app.francafebackend.api.data;

import java.io.Serializable;
import java.util.Date;

public class FiltroReporteContabilidadData implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date fechaInicio;
	private Date fechaFinal;

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

}
