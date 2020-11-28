package com.app.francafebackend.api.view;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.app.francafebackend.api.data.ValorDiarioData;
import com.app.francafebackend.api.model.Empresa;
import com.app.francafebackend.api.service.EmpresaService;
import com.app.francafebackend.api.utils.ImagenAbsolute;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("contabilidad.pdf")
public class ContabilidadViewPdf extends AbstractPdfView {

	@Autowired
	private EmpresaService empresaService;

	@Value("${francafe.empresa}")
	private String nitEmpresa;

	private final static Font FONT_CELL_TITLE = new Font(Font.HELVETICA, 8, Font.NORMAL);
	private final static Font FONT_CELL = new Font(Font.HELVETICA, 6, Font.NORMAL);

	private final static Color COLOR_CELL_TITLE_2 = new Color(219, 234, 250);

	@Override
	protected Document newDocument() {
		return new Document(PageSize.LETTER);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		final String CONTEXTO = request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");

		Date fechaInicio = (Date) model.get("fechaInicio");
		Date fechaFinal = (Date) model.get("fechaFinal");
		List<ValorDiarioData> valores = (List<ValorDiarioData>) model.get("valores");

		Empresa empresa = empresaService.buscarPorNit(nitEmpresa);
		document.addTitle("Reporte contabilidad");

		PdfPTable tablaXn;
		PdfPTable tabla = new PdfPTable(10);
		tabla.setWidthPercentage(80);
		PdfPCell celda;

		tablaXn = new PdfPTable(7);
		Image img;

		/* INICIO ENCABEZADO */
		celda = new PdfPCell(new Phrase("Imagen"));
		String imagen = "/images/logo-pdf.jpg";
		String logo_titulo = CONTEXTO + imagen;

		try {

			celda = new PdfPCell(new Phrase("\u0020"));
			img = Image.getInstance(logo_titulo);
			celda.setCellEvent(new ImagenAbsolute(img));

		} catch (BadElementException | IOException e) {
		}

		celda.setFixedHeight(40);
		celda.setColspan(2);
		tablaXn.addCell(celda);

		String titulo = String.format("%s\nNit: %s\n%s\nEmail: %s", empresa.getNombre(), empresa.getNit(),
				empresa.getDireccion(), empresa.getEmail());

		celda = new PdfPCell(new Phrase(titulo, FONT_CELL_TITLE));
		celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setColspan(3);
		tablaXn.addCell(celda);

		SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");

		celda = new PdfPCell(new Phrase("Consolidado diario desde " + formatFecha.format(fechaInicio) + " hasta el "
				+ formatFecha.format(fechaFinal), FONT_CELL_TITLE));
		celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setColspan(2);
		tablaXn.addCell(celda);

		celda = new PdfPCell(tablaXn);
		celda.setColspan(10);

		tabla.addCell(celda);

		celda = new PdfPCell(new Phrase(" "));
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(10);
		tabla.addCell(celda);

		/* FIN ENCABEZADO */

		/* INICIO CUERPO */

		tablaXn = new PdfPTable(4);
		tablaXn.setWidths(new int[] { 40, 20, 20, 20 });

		celda = new PdfPCell(new Phrase("FECHA", FONT_CELL_TITLE));
		celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBackgroundColor(COLOR_CELL_TITLE_2);
		tablaXn.addCell(celda);

		celda = new PdfPCell(new Phrase("VALOR IVA", FONT_CELL_TITLE));
		celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBackgroundColor(COLOR_CELL_TITLE_2);
		tablaXn.addCell(celda);

		celda = new PdfPCell(new Phrase("VALOR NETO", FONT_CELL_TITLE));
		celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBackgroundColor(COLOR_CELL_TITLE_2);
		tablaXn.addCell(celda);

		celda = new PdfPCell(new Phrase("VALOR TOTAL", FONT_CELL_TITLE));
		celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBackgroundColor(COLOR_CELL_TITLE_2);
		tablaXn.addCell(celda);
		
		int totalDias = 0;
		Double valorTotal=0d, valorIva=0d, valorNeto = 0d;

		for (ValorDiarioData detalle : valores) {

			celda = new PdfPCell(
					new Phrase(formatFecha.format(detalle.getFechaFactura()), FONT_CELL));
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase(formatDecimal(detalle.getValorTotalIva()), FONT_CELL));
			celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase(formatDecimal(detalle.getValorTotalNeto()), FONT_CELL));
			celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase(formatDecimal(detalle.getValorTotal()), FONT_CELL));
			celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			tablaXn.addCell(celda);
			
			valorTotal += detalle.getValorTotal();
			valorIva += detalle.getValorTotalIva();
			valorNeto += detalle.getValorTotalNeto();
			totalDias += 1;

		}

		celda = new PdfPCell(new Phrase("Días: "+ totalDias, FONT_CELL_TITLE));
		celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBackgroundColor(COLOR_CELL_TITLE_2);
		tablaXn.addCell(celda);

		celda = new PdfPCell(new Phrase("TOTAL IVA: "+formatDecimal(valorIva), FONT_CELL_TITLE));
		celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBackgroundColor(COLOR_CELL_TITLE_2);
		tablaXn.addCell(celda);

		celda = new PdfPCell(new Phrase("TOTAL NETO: "+formatDecimal(valorNeto), FONT_CELL_TITLE));
		celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBackgroundColor(COLOR_CELL_TITLE_2);
		tablaXn.addCell(celda);

		celda = new PdfPCell(new Phrase("TOTAL: "+formatDecimal(valorTotal), FONT_CELL_TITLE));
		celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBackgroundColor(COLOR_CELL_TITLE_2);
		tablaXn.addCell(celda);

		celda = new PdfPCell(tablaXn);
		celda.setColspan(10);
		tabla.addCell(celda);

		celda = new PdfPCell(new Phrase(" "));
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(10);
		tabla.addCell(celda);

		tablaXn = new PdfPTable(3);
		SimpleDateFormat formatHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		celda = new PdfPCell(new Phrase(formatHora.format(new Date()), FONT_CELL));
		celda.setBorder(Rectangle.NO_BORDER);
		tablaXn.addCell(celda);

		celda = new PdfPCell(new Phrase("ORIGINAL", FONT_CELL));
		celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		celda.setBorder(Rectangle.NO_BORDER);
		tablaXn.addCell(celda);

		celda = new PdfPCell(new Phrase("Nexus Dev SAS", FONT_CELL));
		celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		celda.setBorder(Rectangle.NO_BORDER);
		tablaXn.addCell(celda);

		celda = new PdfPCell(tablaXn);
		celda.setColspan(10);
		celda.setBorder(Rectangle.NO_BORDER);
		tabla.addCell(celda);

		document.add(tabla);

	}

	private String formatDecimal(Double valor) {

		if (valor == null) {
			valor = 0d;
		}
		return String.format(Locale.ENGLISH, "%,.2f", valor);
	}

}
