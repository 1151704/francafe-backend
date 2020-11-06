package com.app.francafebackend.api.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class ImagenAbsolute implements PdfPCellEvent {

	protected Image img;

	public ImagenAbsolute(Image img) {
		this.img = img;
	}

	@Override
	public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
		img.scaleAbsolute(position.getWidth(), position.getHeight());
		img.setAbsolutePosition(position.getLeft(), position.getBottom());
		PdfContentByte canvas = canvases[PdfPTable.BACKGROUNDCANVAS];
		try {
			canvas.addImage(img);
		} catch (DocumentException ex) {
			ex.printStackTrace();
		}
	}

}
