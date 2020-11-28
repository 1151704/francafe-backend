package com.app.francafebackend.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.francafebackend.api.data.ValorDiarioData;
import com.app.francafebackend.api.service.FacturaService;

@Controller
@RequestMapping("api/reportes/")
public class ReportesController {

	@Autowired
	private FacturaService service;

	@GetMapping("contabilidad/{fechaInicio}/{fechaFinal}")
	public String reporteContabilidad(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFinal, Model model) {

		Calendar start = Calendar.getInstance();
		start.setTime(fechaInicio);

		Calendar end = Calendar.getInstance();
		end.setTime(fechaFinal);

		List<ValorDiarioData> valores = new ArrayList<>();

		Double valorTotal, valorTotalIva, valorTotalNeto;
		Double[] data;
		while (!start.after(end)) {

			data = service.getValorTotalPorFecha(start.getTime());

			valorTotalNeto = 0d;
			valorTotalIva = 0d;
			valorTotal = 0d;

			if (data != null && data.length > 0) {
				valorTotal = data[0] != null ? data[0] : 0d;
				valorTotalIva = data[1] != null ? data[1] : 0d;
				valorTotalNeto = data[2] != null ? data[2] : 0d;
			}

			valores.add(new ValorDiarioData(start.getTime(), valorTotal, valorTotalIva, valorTotalNeto));

			start.add(Calendar.DATE, 1);
		}

		model.addAttribute("valores", valores);
		model.addAttribute("fechaInicio", fechaInicio);
		model.addAttribute("fechaFinal", fechaFinal);

		return "contabilidad";
	}

}
