package cu.uci.abcd.management.report.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import cu.uci.abcd.management.report.IReportingObject;
import cu.uci.abcd.management.report.PDFGenerator;

public class PDFGeneratorImpl implements PDFGenerator {
	public static final String TABLE = "Listado de Coincidencias: ";
	public static final String criterios = "Criterios de Búsqueda: ";
	public static final String TITLE = "REPORTE ESTADÍSTICO";

	@Override
	public ByteArrayOutputStream generatePDFAsTabularReport(String title, List<String> searchCriteria, List<String> columnHeaders, String[][] data) {
		ByteArrayOutputStream out = null;
		try {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			document.addAuthor("ABCD");
			document.addTitle("ABCD REPORT");

			out = new ByteArrayOutputStream();

			PdfWriter writer = PdfWriter.getInstance(document, out);
			writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);

			writer.setInitialLeading(16);
			Rectangle rct = new Rectangle(36, 54, 559, 788);
			// Definimos un nombre y un tamaño para el PageBox los nombres
			// posibles son: “crop”, “trim”, “art” and “bleed”.
			writer.setBoxSize("art", rct);
			PDFUtil event = new PDFUtil();
			writer.setPageEvent(event);
			document.open();

			document.add(Chunk.NEWLINE);
			Chunk titulo1 = new Chunk(title, FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLACK));
			document.add(titulo1);
			document.add(Chunk.NEWLINE);

			if (searchCriteria.size() > 0) {

				document.add(Chunk.NEWLINE);
				Chunk cirterios = new Chunk(criterios.toUpperCase(), FontFactory.getFont("arial", 11, Font.BOLD, BaseColor.BLACK));
				document.add(cirterios);
				document.add(Chunk.NEWLINE);

				for (int i = 0; i < searchCriteria.size(); i++) {
					Chunk search = new Chunk(searchCriteria.get(i), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK));
					document.add(search);
					document.add(Chunk.NEWLINE);
				}
			}

			document.add(Chunk.NEWLINE);
			Chunk listadoC = new Chunk(TABLE.toUpperCase(), FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLACK));
			document.add(listadoC);

			List<String> cabeceras = columnHeaders;
			int columnas = cabeceras.size();
			PdfPTable table = new PdfPTable(columnas);

			// Añadir CABECERA
			PdfPCell cell = null;

			for (int i = 0; i < cabeceras.size(); i++) {
				cell = new PdfPCell(new Phrase(cabeceras.get(i)));
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell);
			}

			String[][] datosTabla = data;
			table.setWidthPercentage(100);
			int temp = datosTabla.length;

			// Añadir filas a la tabla

			if (temp > 0) {
				for (int k = 0; k < temp; k++) {
					for (int l = 0; l < columnas; l++) {
						PdfPCell value = new PdfPCell(table);
						value.setPadding(3);
						value.setPaddingTop(1);
						value.setPaddingBottom(5);
						value.setLeading(1, 1);
						value.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
						value.setPhrase(new Phrase(datosTabla[k][l]));
						value.setUseVariableBorders(true);
						value.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
						if (l == 0) {
							value.setBorderColorLeft(BaseColor.WHITE);
						}
						if (l == (columnas - 1)) {
							value.setBorderColorRight(BaseColor.WHITE);
						}
						table.addCell(value);
					}
				}
			} else {
				PdfPCell cell1 = new PdfPCell(new Phrase("No se encontraron Coincidencias"));
				cell1.setColspan(columnas);
				table.addCell(cell1);
			}

			document.add(table);
			// FIN Ejemplos de TABLE

			document.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return out;
	}

	@Override
	public ByteArrayOutputStream generatePDFAsDataSheet(String title, List<IReportingObject> reportingObjectContainer) {
		ByteArrayOutputStream out = null;
		try {
			// Creacion del documento con un tamaño y unos margenes
			// predeterminados
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			// Al documento se le puede añadir cierta metaInformacion
			document.addAuthor("ABCD");
			document.addTitle("ABCD REPORT");

			out = new ByteArrayOutputStream();

			PdfWriter writer = PdfWriter.getInstance(document, out);
			// LEADING = separacion entre lineas del documento
			writer.setInitialLeading(10);
			Rectangle rct = new Rectangle(36, 54, 559, 788);
			// Definimos un nombre y un tamaño para el PageBox los nombres
			// posibles son: “crop”, “trim”, “art” and “bleed”.
			writer.setBoxSize("art", rct);

			PDFUtil event = new PDFUtil();
			writer.setPageEvent(event);

			// Opens the document.
			// You have to open the document before you can begin to add content
			// to the body of the document.
			document.open();

			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			Chunk encabezado = new Chunk(title, FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLACK));
			document.add(encabezado);
			document.add(Chunk.NEWLINE);

			for (int i = 0; i < reportingObjectContainer.size(); i++) {

				PdfPTable table = new PdfPTable(2);
				table.setWidthPercentage(45);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell cell = new PdfPCell(new Phrase(reportingObjectContainer.get(i).getHeaders(), FontFactory.getFont("arial", 10, Font.ITALIC, BaseColor.BLACK)));
				cell.setColspan(2);
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);

				for (int k = 0; k < reportingObjectContainer.get(i).getLeftValue().size(); k++) {

					table.addCell(new Phrase(reportingObjectContainer.get(i).getLeftValue().get(k), FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLACK)));
					table.addCell(reportingObjectContainer.get(i).getRigthValue().get(k));

				}
				document.add(table);
				document.add(Chunk.NEWLINE);
			}

			document.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return out;
	}

	/*
	 * private void fillRows(List<IReportingObject> reportingObjectContainer,
	 * int i, PdfPTable table) { for (int k = 0; k <
	 * reportingObjectContainer.get(i).getContainedData().size(); k++) {
	 * PdfPCell name = new PdfPCell(table); name.setPadding(3);
	 * name.setSpaceCharRatio(2); name.setLeading(1, 1);
	 * name.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
	 * name.setBorderColorLeft(BaseColor.WHITE); name.setPhrase(new
	 * Phrase(reportingObjectContainer
	 * .get(i).getContainedData().get(k).getName())); name.setPaddingTop(1);
	 * name.setPaddingBottom(5);
	 * name.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	 * name.setUseVariableBorders(true);
	 * 
	 * PdfPCell value = new PdfPCell(table); value.setPadding(3);
	 * value.setPaddingTop(1); value.setPaddingBottom(5); value.setLeading(1,
	 * 1); value.setBorderColorRight(BaseColor.WHITE);
	 * value.setVerticalAlignment(PdfPCell.ALIGN_CENTER); value.setPhrase(new
	 * Phrase
	 * (reportingObjectContainer.get(i).getContainedData().get(k).getValue(
	 * ).toString())); value.setUseVariableBorders(true);
	 * value.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
	 * table.addCell(name); table.addCell(value);
	 * 
	 * } }
	 * 
	 * private void buidTableHeader(List<IReportingObject>
	 * reportingObjectContainer, int i, PdfPTable table) {
	 * table.setWidthPercentage(100);
	 * table.setHorizontalAlignment(Element.ALIGN_LEFT); Phrase phrase = new
	 * Phrase
	 * (reportingObjectContainer.get(i).getContainerHeader().toUpperCase(),
	 * FontFactory.getFont("arial", 11, Font.BOLD, BaseColor.BLACK)); PdfPCell
	 * cell = new PdfPCell(phrase);
	 * cell.setVerticalAlignment(PdfPCell.ALIGN_TOP); cell.setPaddingBottom(4);
	 * cell.setColspan(2); cell.setBorder(Rectangle.NO_BORDER);
	 * cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	 * cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	 * cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER); table.addCell(cell); }
	 */

	@Override
	public ByteArrayOutputStream generatePDFListSelection(String title, List<String> data) {
		ByteArrayOutputStream out = null;
		try {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			document.addAuthor("ABCD");
			document.addTitle("ABCD REPORT");

			out = new ByteArrayOutputStream();

			PdfWriter writer = PdfWriter.getInstance(document, out);
			writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);

			writer.setInitialLeading(16);
			Rectangle rct = new Rectangle(36, 54, 559, 788);
			// Definimos un nombre y un tamaño para el PageBox los nombres
			// posibles son: “crop”, “trim”, “art” and “bleed”.
			writer.setBoxSize("art", rct);

			// sPDFUtil event = new PDFUtil();
			// writer.setPageEvent(event);

			document.open();

			document.add(Chunk.NEWLINE);
			Chunk titulo1 = new Chunk("Lista de selección", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLACK));
			document.add(titulo1);
			document.add(Chunk.NEWLINE);

			for (int i = 0; i < data.size(); i++) {
				try {

					String bibliography = data.get(i);
					Paragraph biblio = new Paragraph(bibliography, FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLACK));
					document.add(biblio);
					document.add(Chunk.NEWLINE);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			document.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return out;
	}

	@Override
	public ByteArrayOutputStream generatePDFStatistic(String columnHead, List<String> columnHeaders, String rowHead, List<String> rowHeaders, String[][] data) {

		ByteArrayOutputStream out = null;
		try {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			document.addAuthor("ABCD");
			document.addTitle("ABCD REPORT");

			out = new ByteArrayOutputStream();

			PdfWriter writer = PdfWriter.getInstance(document, out);
			writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);

			writer.setInitialLeading(16);
			Rectangle rct = new Rectangle(36, 54, 559, 788);
			// Definimos un nombre y un tamaño para el PageBox los nombres
			// posibles son: “crop”, “trim”, “art” and “bleed”.
			writer.setBoxSize("art", rct);
			PDFUtil event = new PDFUtil();
			writer.setPageEvent(event);
			document.open();

			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			Chunk titulo1 = new Chunk(TITLE, FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLACK));
			document.add(titulo1);
			document.add(Chunk.NEWLINE);

			document.add(Chunk.NEWLINE);

			List<String> cabeceras = columnHeaders;
			int columnas = cabeceras.size();

			// ------------tabla

			PdfPTable table = new PdfPTable(columnas + 1);

			table.addCell(" ");// columna en blanco

			PdfPCell cell = new PdfPCell(new Phrase(columnHead, FontFactory.getFont("arial", 11, Font.BOLD, BaseColor.BLACK)));
			cell.setColspan(columnas);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell); // head de las columnas

			PdfPCell cellRow = new PdfPCell(new Phrase(rowHead, FontFactory.getFont("arial", 11, Font.BOLD, BaseColor.BLACK)));
			cellRow.setBackgroundColor(BaseColor.GRAY);
			table.addCell(cellRow); // head de las filas

			// Añadir CABECERA de las columnas
			PdfPCell cellColumnHeaders = null;

			for (int i = 0; i < cabeceras.size(); i++) {
				cellColumnHeaders = new PdfPCell(new Phrase(cabeceras.get(i)));
				cellColumnHeaders.setBackgroundColor(BaseColor.LIGHT_GRAY);
				cellColumnHeaders.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cellColumnHeaders);
			}

			// Añadir CABECERA de las filas
			PdfPCell cellRowHeaders = null;

			String[][] datosTabla = data;
			table.setWidthPercentage(100);
			int temp = datosTabla.length;

			for (int k = 0; k < temp; k++) {

				cellRowHeaders = new PdfPCell(new Phrase(rowHeaders.get(k)));
				cellRowHeaders.setBackgroundColor(BaseColor.GRAY);
				cellRowHeaders.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cellRowHeaders);

				for (int l = 0; l < columnas; l++) {

					PdfPCell value = new PdfPCell(table);
					value.setVerticalAlignment(Element.ALIGN_CENTER);
					// value.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					value.setPhrase(new Phrase(datosTabla[k][l]));
					value.setUseVariableBorders(true);
					table.addCell(value);
				}
			}

			document.add(table);

			document.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return out;
	}

	@Override
	public ByteArrayOutputStream generatePDFMESReport(String title,String title2,String title3,String title4,
			List<String> searchCriteria, List<String> columnHeaders,
			String[][] data) {
		ByteArrayOutputStream out = null;
		try {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			document.addAuthor("ABCD");
			document.addTitle("ABCD REPORT");

			out = new ByteArrayOutputStream();

			PdfWriter writer = PdfWriter.getInstance(document, out);
			writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);

			writer.setInitialLeading(16);
			Rectangle rct = new Rectangle(36, 54, 559, 788);
			// Definimos un nombre y un tamaño para el PageBox los nombres
			// posibles son: “crop”, “trim”, “art” and “bleed”.
			writer.setBoxSize("art", rct);
			PDFUtil event = new PDFUtil();
			writer.setPageEvent(event);
			document.open();

			Paragraph paragraph = new Paragraph();
			Paragraph paragraph2 = new Paragraph();
			Paragraph paragraph3 = new Paragraph();
			Paragraph paragraph4 = new Paragraph();
			
			document.add(Chunk.NEWLINE);
			Chunk titulo1 = new Chunk(title, FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLACK));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.add(titulo1);
			document.add(paragraph);
			document.add(Chunk.NEWLINE);
			
			document.add(Chunk.NEWLINE);
			Chunk titulo2 = new Chunk(title2, FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLACK));
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph2.add(titulo2);
			document.add(paragraph2);
			document.add(Chunk.NEWLINE);

			document.add(Chunk.NEWLINE);
			Chunk titulo3 = new Chunk(title3, FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLACK));
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph3.add(titulo3);
			document.add(paragraph3);
			document.add(Chunk.NEWLINE);

			document.add(Chunk.NEWLINE);
			Chunk titulo4 = new Chunk(title4, FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.BLACK));
			paragraph4.setAlignment(Element.ALIGN_CENTER);
			paragraph4.add(titulo4);
			document.add(paragraph4);
			document.add(Chunk.NEWLINE);


			if (searchCriteria.size() > 0) {

				document.add(Chunk.NEWLINE);
				Chunk cirterios = new Chunk(criterios.toUpperCase(), FontFactory.getFont("arial", 11, Font.BOLD, BaseColor.BLACK));
				document.add(cirterios);
				document.add(Chunk.NEWLINE);

				for (int i = 0; i < searchCriteria.size(); i++) {
					Chunk search = new Chunk(searchCriteria.get(i), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK));
					document.add(search);
					document.add(Chunk.NEWLINE);
				}
			}

			document.add(Chunk.NEWLINE);
			Chunk listadoC = new Chunk(TABLE.toUpperCase(), FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLACK));
			document.add(listadoC);

			List<String> cabeceras = columnHeaders;
			int columnas = cabeceras.size();
			PdfPTable table = new PdfPTable(columnas);

			// Añadir CABECERA
			PdfPCell cell = null;

			for (int i = 0; i < cabeceras.size(); i++) {
				cell = new PdfPCell(new Phrase(cabeceras.get(i)));
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell);
			}

			String[][] datosTabla = data;
			table.setWidthPercentage(100);
			int temp = datosTabla.length;

			// Añadir filas a la tabla

			if (temp > 0) {
				for (int k = 0; k < temp; k++) {
					for (int l = 0; l < columnas; l++) {
						PdfPCell value = new PdfPCell(table);
						value.setPadding(3);
						value.setPaddingTop(1);
						value.setPaddingBottom(5);
						value.setLeading(1, 1);
						value.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
						value.setPhrase(new Phrase(datosTabla[k][l]));
						value.setUseVariableBorders(true);
						value.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
						if (l == 0) {
							value.setBorderColorLeft(BaseColor.WHITE);
						}
						if (l == (columnas - 1)) {
							value.setBorderColorRight(BaseColor.WHITE);
						}
						table.addCell(value);
					}
				}
			} else {
				PdfPCell cell1 = new PdfPCell(new Phrase("No se encontraron Coincidencias"));
				cell1.setColspan(columnas);
				table.addCell(cell1);
			}

			document.add(table);
			// FIN Ejemplos de TABLE

			document.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return out;
	}
}
