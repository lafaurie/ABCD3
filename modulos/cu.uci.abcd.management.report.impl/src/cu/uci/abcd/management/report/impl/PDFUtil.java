package cu.uci.abcd.management.report.impl;

import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

class PDFUtil extends PdfPageEventHelper {

	public static final String SEPARADOR = "_________________________________________________________________________";
	Phrase txtCabecera;
	Phrase imgCabecera;
	Image imagen;
	PdfTemplate total;

	public void onOpenDocument(PdfWriter writer, Document document) {
		total = writer.getDirectContent().createTemplate(10, 16);
	}

	public void onEndPage(PdfWriter writer, Document document) {

		try {
			imagen = Image.getInstance("abcdconfig/abcd_report_logo.jpg");
			Chunk chunk = new Chunk(imagen, 0, -50);
			imgCabecera = new Phrase(chunk);
			txtCabecera = new Phrase(SEPARADOR);

		} catch (BadElementException | IOException e) {
			e.getMessage();
		}
		
		PdfContentByte cb = writer.getDirectContent();

		ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, imgCabecera, (document.left() - document.left()) / 2 + document.leftMargin(), document.top() + 35, 0);
		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, txtCabecera, (document.right() - document.left()) / 2 + document.leftMargin(), document.top() - 20, 0);

		PdfPTable table = new PdfPTable(2);
		try {
			// Establecemos las medidas de la tabla
			table.setWidths(new int[] { 10, 10 });
			table.setTotalWidth(100);
			// Establecemos la altura de la celda
			table.getDefaultCell().setFixedHeight(20);
			// Quitamos el borde
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			// Alineamos el contenido a la derecha
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			// Escribimos el primer texto
			// en la primera celda
			table.addCell(String.format("%d/", writer.getCurrentPageNumber()));
			// Obtenemos el template total
			// y lo agregamos a la celda
			PdfPCell cell = new PdfPCell(Image.getInstance(total));
			// quitamos el borde
			cell.setBorder(Rectangle.NO_BORDER);
			// lo agregamos a la tabla
			table.addCell(cell);
			table.writeSelectedRows(0, -1, 260, 40, writer.getDirectContent()); 
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	public void onCloseDocument(PdfWriter writer, Document document) {
		// Escribimos el contenido al cerrar el documento
		ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
	}

}
