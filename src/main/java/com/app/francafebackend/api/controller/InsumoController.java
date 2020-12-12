package com.app.francafebackend.api.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.francafebackend.api.model.Insumo;
import com.app.francafebackend.api.model.InsumoEntrada;
import com.app.francafebackend.api.service.InsumoEntradaService;
import com.app.francafebackend.api.service.InsumoService;
import com.app.francafebackend.api.utils.ValidationException;

@RestController
@RequestMapping("api/insumo/")
public class InsumoController {

	@Autowired
	private InsumoEntradaService service;

	@Autowired
	private InsumoService insumoService;

	@PostMapping(value = "cargaMasivaInsumos", consumes = { "multipart/form-data" })
	public void cargaMasivaTipo(@RequestParam("file") MultipartFile file,
			@RequestParam("fechaIngreso") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIngreso, HttpServletResponse response) {
		XSSFWorkbook workbook = null;
		try {

			workbook = new XSSFWorkbook(file.getInputStream());

			XSSFSheet worksheet = workbook.getSheetAt(0);
			CellStyle wrapText = workbook.createCellStyle();
			wrapText.setWrapText(true);

			XSSFRow row;
			XSSFCell cell;

			Insumo insumo;
			InsumoEntrada entrada;
			String id;

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				row = worksheet.getRow(i);
				
				if (row.getCell(1) == null) {
					break;
				}
				id = getCellValue(row.getCell(0));

				entrada = new InsumoEntrada();
				insumo = null;

				if (id != null && !id.isEmpty()) {
					insumo = insumoService.buscarPorIdentificador(Integer.parseInt(id));
				}
				cell = row.createCell(5);

				if (insumo == null) {

					insumo = new Insumo();
					insumo.setNombre(getCellValue(row.getCell(1)));
					insumo.setDescripcion(getCellValue(row.getCell(2)));
					insumo = insumoService.guardar(insumo);
					entrada.setEsNuevo(true);
					cell.setCellValue("Insumo Nuevo");
				} else {
					cell.setCellValue("Insumo Existente");
				}

				cell = row.createCell(6);
				entrada.setFechaIngreso(fechaIngreso);
				entrada.setInsumo(insumo);
				entrada.setValorUnt(Double.parseDouble(getCellValue(row.getCell(3))));
				entrada.setUnidades(Double.parseDouble(getCellValue(row.getCell(4))));

				try {
					entrada = service.guardar(entrada);
					cell.setCellValue("Entrada registrada: " + entrada.getValorTotal());
				} catch (Exception e) {
					cell.setCellValue("Entrada no registrada.");
				}

			}
			try (OutputStream outputStream = response.getOutputStream()) {
				workbook.write(outputStream);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new ValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}
	

	@SuppressWarnings("resource")
	@GetMapping(value = "generarInsumos")
	public void generarReporte(HttpServletResponse response) {
		XSSFWorkbook workbook = null;
		try {

			workbook = new XSSFWorkbook();

			XSSFSheet worksheet = workbook.createSheet();

			XSSFRow row;

			List<Insumo> insumos = insumoService.listarInsumos();
			
			int i = 0;

			row = worksheet.createRow(i++);			
			row.createCell(0).setCellValue("Código");
			row.createCell(1).setCellValue("Nombre");
			row.createCell(2).setCellValue("Descripción");
			
			for (Insumo insumo : insumos) {
				row = worksheet.createRow(i++);				

				row.createCell(0).setCellValue(insumo.getId());
				row.createCell(1).setCellValue(insumo.getNombre());
				row.createCell(2).setCellValue(insumo.getDescripcion() != null ? insumo.getDescripcion():"");

			}
			try (OutputStream outputStream = response.getOutputStream()) {
				workbook.write(outputStream);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new ValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	private String getCellValue(XSSFCell cell) {
		if (cell != null) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			return ((float) cell.getNumericCellValue()) + "";
		default:
			break;
		}

		return "";
		} 
		return null;
	}

}
