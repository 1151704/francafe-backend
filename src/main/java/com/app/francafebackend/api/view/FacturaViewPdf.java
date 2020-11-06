package com.app.francafebackend.api.view;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.app.francafebackend.api.model.Cliente;
import com.app.francafebackend.api.model.DetalleFactura;
import com.app.francafebackend.api.model.Empresa;
import com.app.francafebackend.api.model.Factura;
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

@Component("factura.pdf")
public class FacturaViewPdf extends AbstractPdfView {

	@Autowired
	private EmpresaService empresaService;

	@Value("${francafe.empresa}")
	private String nitEmpresa;

	private final static Font FONT_CELL_TITLE = new Font(Font.HELVETICA, 6, Font.NORMAL);
	private final static Font FONT_CELL = new Font(Font.HELVETICA, 4, Font.NORMAL);

	private final static Color COLOR_CELL_TITLE = new Color(182, 212, 246);
	private final static Color COLOR_CELL_TITLE_2 = new Color(219, 234, 250);

	@Override
	protected Document newDocument() {
		return new Document(PageSize.LETTER);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		final String CONTEXTO = request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");

		Factura factura = (Factura) model.get("factura");

		if (factura != null) {

			Cliente cliente = factura.getCliente();

			Empresa empresa = empresaService.buscarPorNit(nitEmpresa);
			document.addTitle("Factura " + factura.getCodigo());

			PdfPTable tablaXn;
			PdfPTable tabla = new PdfPTable(10);
			tabla.setWidthPercentage(45);
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

			celda = new PdfPCell(new Phrase("FACTURA DE VENTA No. " + factura.getCodigo(), FONT_CELL_TITLE));
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

			tablaXn = new PdfPTable(2);

			celda = new PdfPCell(new Phrase(
					"Nombre: " + cliente.getNombres().toUpperCase() + " " + cliente.getApellidos().toUpperCase(),
					FONT_CELL));
			celda.setColspan(2);
			celda.setBorder(Rectangle.NO_BORDER);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("GENERO: " + cliente.getSexo().getNombre().toUpperCase(), FONT_CELL));
			celda.setColspan(2);
			celda.setBorder(Rectangle.NO_BORDER);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("TELEFONO: " + cliente.getTelefono().toUpperCase(), FONT_CELL));
			celda.setBorder(Rectangle.NO_BORDER);
			tablaXn.addCell(celda);

			celda.setBorder(Rectangle.NO_BORDER);
			celda = new PdfPCell(new Phrase("", FONT_CELL));
			celda.setBorder(Rectangle.NO_BORDER);
			tablaXn.addCell(celda);

			celda = new PdfPCell(tablaXn);
			celda.setColspan(5);

			tabla.addCell(celda);

			tablaXn = new PdfPTable(5);

			celda = new PdfPCell(new Phrase("Identificación", FONT_CELL));
			celda.setBackgroundColor(COLOR_CELL_TITLE);
			celda.setColspan(2);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase(cliente.getTipoId().getTipo() + " " + cliente.getIdentificacion(), FONT_CELL));
			celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			celda.setColspan(3);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("Fecha factura", FONT_CELL));
			celda.setBackgroundColor(COLOR_CELL_TITLE);
			celda.setColspan(2);
			tablaXn.addCell(celda);

			SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");

			celda = new PdfPCell(new Phrase(formatFecha.format(factura.getFechaRegistro()), FONT_CELL));
			celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			celda.setColspan(3);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("Forma de pago", FONT_CELL));
			celda.setBackgroundColor(COLOR_CELL_TITLE);
			celda.setColspan(2);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase(factura.getFormaPago().getNombre(), FONT_CELL));
			celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			celda.setColspan(3);
			tablaXn.addCell(celda);

			celda = new PdfPCell(tablaXn);
			celda.setColspan(5);

			tabla.addCell(celda);

			tablaXn = new PdfPTable(5);
			tablaXn.setWidths(new int[] { 40, 15, 15, 15, 15 });

			celda = new PdfPCell(new Phrase("ARTICULO", FONT_CELL_TITLE));
			celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			celda.setBackgroundColor(COLOR_CELL_TITLE_2);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("IVA", FONT_CELL_TITLE));
			celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			celda.setBackgroundColor(COLOR_CELL_TITLE_2);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("CANTIDAD", FONT_CELL_TITLE));
			celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			celda.setBackgroundColor(COLOR_CELL_TITLE_2);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("PRECIO NETO", FONT_CELL_TITLE));
			celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			celda.setBackgroundColor(COLOR_CELL_TITLE_2);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("VALOR TOTAL", FONT_CELL_TITLE));
			celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			celda.setBackgroundColor(COLOR_CELL_TITLE_2);
			tablaXn.addCell(celda);

			for (DetalleFactura detalle : factura.getDetalles()) {

				celda = new PdfPCell(new Phrase(
						detalle.getProducto().getCodigo() + " " + detalle.getProducto().getNombre(), FONT_CELL));
				tablaXn.addCell(celda);

				celda = new PdfPCell(new Phrase(String.valueOf(detalle.getValorIva()), FONT_CELL));
				celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				tablaXn.addCell(celda);

				celda = new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), FONT_CELL));
				celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				tablaXn.addCell(celda);

				celda = new PdfPCell(
						new Phrase(formatDecimal(detalle.getValorUnidad() * detalle.getCantidad()), FONT_CELL));
				celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				tablaXn.addCell(celda);

				celda = new PdfPCell(new Phrase(formatDecimal(detalle.getValorTotal()), FONT_CELL));
				celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				tablaXn.addCell(celda);

			}

			celda = new PdfPCell(tablaXn);
			celda.setColspan(10);
			tabla.addCell(celda);

			celda = new PdfPCell(new Phrase(" "));
			celda.setBorder(Rectangle.NO_BORDER);
			celda.setColspan(10);
			tabla.addCell(celda);

			tablaXn = new PdfPTable(4);
			tablaXn.setWidths(new int[] { 50, 20, 15, 15 });

			celda = new PdfPCell(
					new Phrase("* La presente FACTURA DE VENTA se asimila en todos sus efectos a la LETRA DE CAMBIO"
							+ "(Art. 774 Numeral 6 del código de comercio)", FONT_CELL));
			celda.setRowspan(5);
			celda.setColspan(2);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("Valor Neto", FONT_CELL));
			celda.setBorder(Rectangle.NO_BORDER);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase(formatDecimal(factura.getValorNeto()), FONT_CELL));
			celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			celda.setBorder(Rectangle.NO_BORDER);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("IVA", FONT_CELL));
			celda.setBorder(Rectangle.NO_BORDER);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase(formatDecimal(factura.getValorIva()), FONT_CELL));
			celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			celda.setBorder(Rectangle.NO_BORDER);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase("TOTAL", FONT_CELL));
			celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tablaXn.addCell(celda);

			celda = new PdfPCell(new Phrase(formatDecimal(factura.getValorTotal()), FONT_CELL));
			celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			celda.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tablaXn.addCell(celda);

			celda = new PdfPCell(tablaXn);
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

		} else {
			document.add(new Phrase("Número de factura invalida"));
		}

	}

	private String formatDecimal(Double valor) {

		if (valor == null) {
			valor = 0d;
		}
		return String.format(Locale.ENGLISH, "%,.2f", valor);
	}

}
