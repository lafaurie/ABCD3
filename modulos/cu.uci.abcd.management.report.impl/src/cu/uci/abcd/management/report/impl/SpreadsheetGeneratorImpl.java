package cu.uci.abcd.management.report.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;

import cu.uci.abcd.management.report.IReportingObject;
import cu.uci.abcd.management.report.SpreadsheetGenerator;

public class SpreadsheetGeneratorImpl implements SpreadsheetGenerator {
	// Textos
	public static final String SEPARADOR = "_______________________________________________";

	public static final String TABLE = "Listado de Coincidencias: ";
	public static final String SEARCH_CRITERIA = "Criterios de Búsqueda: ";
	public static final String TITLE = "REPORTE ESTADÍSTICO";

	@Override
	public ByteArrayOutputStream generateSpreadsheetAsTabularReport(
			String title, List<String> searchCriteria,
			List<String> columnHeaders, String[][] data) {
		ByteArrayOutputStream out = null;
		try {
			Workbook wb = new HSSFWorkbook();

			Sheet sheet = wb.createSheet(title);

			Row row = sheet.createRow((short) 4);

			Row rowTitle = sheet.createRow((short) 5);

			Row rowSearch = sheet.createRow((short) 6);

			Row rowResultSearch = sheet.createRow((short) 7);

			Row rowList = sheet.createRow((short) 9);

			// add picture data to this workbook.
			InputStream is = new FileInputStream("abcdconfig/abcd_report_logo_excel.jpg");
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();

			CreationHelper helper = wb.getCreationHelper();

			// Create the drawing patriarch. This is the top level container for
			// all shapes.
			Drawing drawing = sheet.createDrawingPatriarch();

			// add a picture shape
			ClientAnchor anchor = helper.createClientAnchor();
			// set top-left corner of the picture,
			// subsequent call of Picture#resize() will operate relative to it
			anchor.setCol1(3);
			anchor.setRow1(3);

			drawing.createPicture(anchor, pictureIdx);
			// Picture pict = drawing.createPicture(anchor, pictureIdx);

			// auto-size picture relative to its top-left corner
			// pict.resize();

			// Para el formato del texto
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("arial");
			// font.setColor((short) 10);
			// pone el texto en negrita
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// Fonts are set into a style so create a new one to use.
			CellStyle style = wb.createCellStyle();
			style.setFont(font);

			// Para el formato del texto
			Font fontHearders = wb.createFont();
			fontHearders.setFontHeightInPoints((short) 10);
			fontHearders.setFontName("arial");
			// font.setColor((short) 10);
			// pone el texto en negrita
			fontHearders.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// Fonts are set into a style so create a new one to use.
			CellStyle styleHeaders = wb.createCellStyle();
			styleHeaders.setFont(fontHearders);
			styleHeaders.setBorderBottom(CellStyle.BORDER_MEDIUM);
			styleHeaders.setBorderLeft(CellStyle.BORDER_MEDIUM);
			styleHeaders.setBorderRight(CellStyle.BORDER_MEDIUM);
			styleHeaders.setBorderTop(CellStyle.BORDER_MEDIUM);

			Cell cell1 = row.createCell((short) 1);
			cell1.setCellValue(SEPARADOR);
			cell1.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(2, // first row (0-based)
					2, // last row (0-based)
					1, // first column (0-based)
					6 // last column (0-based)
			));

			// Title of Consult
			Cell cell = rowTitle.createCell((short) 1);
			cell.setCellValue(title);
			cell.setCellStyle(style);
			rowTitle.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));

			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(4, // first row (0-based)
					4, // last row (0-based)
					1, // first column (0-based)
					4 // last column (0-based)
			));

		
         if (searchCriteria.size()>0) {
				
			
			// Title of Search Criteria
			Cell cellSearch = rowSearch.createCell((short) 1);
			cellSearch.setCellValue(SEARCH_CRITERIA);
			cellSearch.setCellStyle(style);
			rowSearch.setHeightInPoints((2 * sheet
					.getDefaultRowHeightInPoints()));

			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(5, // first row (0-based)
					5, // last row (0-based)
					1, // first column (0-based)
					4 // last column (0-based)
			));

			short auxCell = 1;
			
			
			for (int i = 0; i < searchCriteria.size(); i++) {
				Cell cells = rowResultSearch.createCell(auxCell);
				cells.setCellValue(searchCriteria.get(i));
				auxCell = (short) (auxCell + 1);
			}
			
         }

			// Title of List
			Cell cellList = rowList.createCell((short) 1);
			cellList.setCellValue(TABLE);
			cellList.setCellStyle(style);
			rowList.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));

			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(9, // first row (0-based)
					9, // last row (0-based)
					1, // first column (0-based)
					2 // last column (0-based)
			));

			short aux = 11;

			// Style the cell with borders all around.
			CellStyle styleBorder = wb.createCellStyle();
			styleBorder.setBorderBottom(CellStyle.BORDER_THIN);
			styleBorder.setBorderLeft(CellStyle.BORDER_THIN);
			styleBorder.setBorderRight(CellStyle.BORDER_THIN);
			styleBorder.setBorderTop(CellStyle.BORDER_THIN);

			Row rows = sheet.createRow(aux);
			for (int i = 0; i < columnHeaders.size(); i++) {
				Cell cells = rows.createCell((short) i + 1);
				cells.setCellValue(columnHeaders.get(i));
				cells.setCellStyle(styleHeaders);
				sheet.autoSizeColumn((short) i + 1);
			}
			aux = (short) (aux + 1);
			
			int column =columnHeaders.size();
			int temp = data.length;
			
			if (temp>0) {
			for (int k = 0; k < temp; k++) {
				Row rowsData = sheet.createRow(aux);
				for (int l = 0; l < columnHeaders.size(); l++) {
					Cell cells = rowsData.createCell((short) l + 1);
					cells.setCellValue(data[k][l]);
					cells.setCellStyle(styleBorder);

				}
				aux = (short) (aux + 1);
			}
			}
			else{
				Row rowsData = sheet.createRow(aux);
				Cell cells = rowsData.createCell(1);
				cells.setCellValue("No se encontraron Coincidencias");
				cells.setCellStyle(styleBorder);
				sheet.addMergedRegion(new CellRangeAddress(9, // first row (0-based)
						9, // last row (0-based)
						1, // first column (0-based)
						column-2 // last column (0-based)
				));
			}

			out = new ByteArrayOutputStream();
			wb.write(out);
			out.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return out;
	}


	@Override
	public ByteArrayOutputStream generateSpreadsheetAsDataSheet(String title,
			List<IReportingObject> reportingObjectContainer) {
		ByteArrayOutputStream out = null;
		try {
			// Se crea el libro
			Workbook wb = new HSSFWorkbook();

			// Se crea una hoja dentro del libro
			Sheet sheet = wb.createSheet("Reporte");

			// add picture data to this workbook.
			InputStream is = new FileInputStream("abcdconfig/abcd_report_logo_excel.jpg");
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();

			CreationHelper helper = wb.getCreationHelper();

			// create sheet

			// Create the drawing patriarch. This is the top level container for
			// all shapes.
			Drawing drawing = sheet.createDrawingPatriarch();

			// add a picture shape
			ClientAnchor anchor = helper.createClientAnchor();
			// set top-left corner of the picture,
			// subsequent call of Picture#resize() will operate relative to it
			anchor.setCol1(3);
			anchor.setRow1(3);
			drawing.createPicture(anchor, pictureIdx);
			// Picture pict = drawing.createPicture(anchor, pictureIdx);

			// auto-size picture relative to its top-left corner
			// pict.resize();

			// Se crea una fila dentro de la hoja
			Row row = sheet.createRow((short) 4);

			Row row1 = sheet.createRow((short) 5);

			// Para el formato del texto
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("arial");
			// font.setColor((short) 10);
			// pone el texto en negrita
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// Fonts are set into a style so create a new one to use.
			CellStyle style = wb.createCellStyle();
			style.setFont(font);

			Cell cell1 = row.createCell((short) 1);
			cell1.setCellValue(SEPARADOR);
			cell1.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(2, // first row (0-based)
					2, // last row (0-based)
					1, // first column (0-based)
					10 // last column (0-based)
			));

			// Se crea una celda dentro de la fila
			Cell cell = row1.createCell((short) 1);
			cell.setCellValue(title);
			cell.setCellStyle(style);
			row1.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));

			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(4, // first row (0-based)
					4, // last row (0-based)
					1, // first column (0-based)
					4 // last column (0-based)
			));

			short aux = 7;

			// Style the cell with borders all around.
			CellStyle styleBorder = wb.createCellStyle();
			styleBorder.setBorderBottom(CellStyle.BORDER_THIN);
			styleBorder.setBorderLeft(CellStyle.BORDER_THIN);
			styleBorder.setBorderRight(CellStyle.BORDER_THIN);
			styleBorder.setBorderTop(CellStyle.BORDER_THIN);
			
			
			for (int i = 0; i < reportingObjectContainer.size(); i++) {

				Row rows = sheet.createRow(aux);
				Cell cells = rows.createCell((short) 1);
				cells.setCellValue(reportingObjectContainer.get(i)
						.getHeaders());
				cells.setCellStyle(style);
				// cells.setCellStyle(styleBorder);

				sheet.addMergedRegion(new CellRangeAddress(aux, // first row
																// (0-based)
						aux, // last row (0-based)
						1, // first column (0-based)
						2 // last column (0-based)
				));

				aux = (short) (aux + 1);

				for (int k = 0; k < reportingObjectContainer.get(i)
						.getLeftValue().size(); k++) {

					Row rowsFor = sheet.createRow(aux);
					Cell cellsName = rowsFor.createCell((short) 1);
					cellsName.setCellValue(reportingObjectContainer.get(i).getLeftValue().get(k));
					Cell cellsValue = rowsFor.createCell((short) 2);
					cellsValue.setCellValue(reportingObjectContainer.get(i).getRigthValue().get(k));
					cellsName.setCellStyle(styleBorder);
					cellsValue.setCellStyle(styleBorder);

					aux = (short) (aux + 1);
				}
				aux = (short) (aux + 1);
			}

			sheet.autoSizeColumn((short) 1);
			sheet.autoSizeColumn((short) 2);

			// Se salva el libro.
			out = new ByteArrayOutputStream();
			// FileOutputStream elFichero = new FileOutputStream(RESULT);
			wb.write(out);
			out.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return out;
	}


	@Override
	public ByteArrayOutputStream generateSpreadsheetStatistic(String columnHead, List<String> columnHeaders, String rowHead, List<String> rowHeaders, String[][] data) {
		ByteArrayOutputStream out = null;
		try {
			Workbook wb = new HSSFWorkbook();

			Sheet sheet = wb.createSheet(TITLE);

			Row row = sheet.createRow((short) 4);

			Row rowTitle = sheet.createRow((short) 5);

			// add picture data to this workbook.
			InputStream is = new FileInputStream("abcdconfig/abcd_report_logo_excel.jpg");
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();

			CreationHelper helper = wb.getCreationHelper();

			// Create the drawing patriarch. This is the top level container for
			// all shapes.
			Drawing drawing = sheet.createDrawingPatriarch();

			// add a picture shape
			ClientAnchor anchor = helper.createClientAnchor();
			// set top-left corner of the picture,
			// subsequent call of Picture#resize() will operate relative to it
			anchor.setCol1(4);
			anchor.setRow1(3);

			drawing.createPicture(anchor, pictureIdx);
			// Picture pict = drawing.createPicture(anchor, pictureIdx);

			// auto-size picture relative to its top-left corner
			// pict.resize();

			// Para el formato del texto
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("arial");
			// font.setColor((short) 10);
			// pone el texto en negrita
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// Fonts are set into a style so create a new one to use.
			CellStyle style = wb.createCellStyle();
			style.setFont(font);

			// Para el formato del texto
			Font fontHearders = wb.createFont();
			fontHearders.setFontHeightInPoints((short) 10);
			fontHearders.setFontName("arial");
			// font.setColor((short) 10);
			// pone el texto en negrita
			fontHearders.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// Fonts are set into a style so create a new one to use.
			CellStyle styleHeaders = wb.createCellStyle();
			styleHeaders.setFont(fontHearders);
			styleHeaders.setBorderBottom(CellStyle.BORDER_MEDIUM);
			styleHeaders.setBorderLeft(CellStyle.BORDER_MEDIUM);
			styleHeaders.setBorderRight(CellStyle.BORDER_MEDIUM);
			styleHeaders.setBorderTop(CellStyle.BORDER_MEDIUM);

			Cell cell1 = row.createCell((short) 0);
			cell1.setCellValue(SEPARADOR);
			cell1.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(2, // first row (0-based)
					2, // last row (0-based)
					0, // first column (0-based)
					6 // last column (0-based)
			));

			// Title of Consult
			Cell cell = rowTitle.createCell((short) 0);
			cell.setCellValue(TITLE);
			cell.setCellStyle(style);
			rowTitle.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(5, // first row (0-based)
					5, // last row (0-based)
					0, // first column (0-based)
					4 // last column (0-based)
			));

			// Style the cell with borders all around.
			CellStyle styleBorder = wb.createCellStyle();
			styleBorder.setBorderBottom(CellStyle.BORDER_THIN);
			styleBorder.setBorderLeft(CellStyle.BORDER_THIN);
			styleBorder.setBorderRight(CellStyle.BORDER_THIN);
			styleBorder.setBorderTop(CellStyle.BORDER_THIN);

			
			
			Row rows = sheet.createRow(7);
					
			//Columna en blanco
			Cell cellWhite = rows.createCell((short) 1);
			cellWhite.setCellValue(" ");
			cellWhite.setCellStyle(styleBorder);
			
			// Encabezado de las columnas
			Cell cellColumnHead = rows.createCell((short) 2);
			cellColumnHead.setCellValue(columnHead);
			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(7, // first row (0-based)
					7, // last row (0-based)
					2, // first column (0-based)
					columnHeaders.size() +1// last column (0-based)
			));
			cellColumnHead.setCellStyle(styleHeaders);
			
			short aux = 8;
			//new fila
			Row rowsHeaders = sheet.createRow(aux);
			
			//Encabezado de las filas
			Cell cellRowHead = rowsHeaders.createCell((short) 1);
			cellRowHead.setCellValue(rowHead);
			cellRowHead.setCellStyle(styleHeaders);
			
			//Lista de los encabezados de las columnas
			for (int i = 0; i < columnHeaders.size(); i++) {
				Cell cells = rowsHeaders.createCell((short) i + 2);
				cells.setCellValue(columnHeaders.get(i));
				cells.setCellStyle(styleHeaders);
				//sheet.autoSizeColumn((short) i + 1);
			}
			aux = (short) (aux + 1);
			
			//int column =columnHeaders.size();
			int temp = data.length;
			
			
			for (int k = 0; k < temp; k++) {
				
				Row rowsData = sheet.createRow(aux);
				Cell cellsRow = rowsData.createCell((short)1);
				cellsRow.setCellValue(rowHeaders.get(k));
				cellsRow.setCellStyle(styleHeaders);
				sheet.autoSizeColumn((short) k + 1);
				
				for (int l = 0; l < columnHeaders.size(); l++) {
					Cell cells = rowsData.createCell((short) l +2);
					cells.setCellValue(data[k][l]);
					cells.setCellStyle(styleBorder);

				}
				aux = (short) (aux + 1);
			}
			
			
			
			// Se salva el libro.
			out = new ByteArrayOutputStream();
			// FileOutputStream elFichero = new FileOutputStream(RESULT);
			wb.write(out);
			out.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return out;
	}


	@Override
	public ByteArrayOutputStream generateSpreadsheetMESReport(String title,String title2,String title3,String title4,
			List<String> searchCriteria, List<String> columnHeaders,
			String[][] data) {
		ByteArrayOutputStream out = null;
		try {
			Workbook wb = new HSSFWorkbook();

			Sheet sheet = wb.createSheet(title);

			Row row = sheet.createRow((short) 4);

			Row rowTitle = sheet.createRow((short) 5);
			Row rowTitle2 = sheet.createRow((short) 6);
			Row rowTitle3 = sheet.createRow((short) 7);
			Row rowTitle4 = sheet.createRow((short) 8);
			
			Row rowSearch = sheet.createRow((short) 9);

			Row rowResultSearch = sheet.createRow((short) 10);

			Row rowList = sheet.createRow((short) 12);

			// add picture data to this workbook.
			InputStream is = new FileInputStream("abcdconfig/abcd_report_logo_excel.jpg");
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();

			CreationHelper helper = wb.getCreationHelper();

			// Create the drawing patriarch. This is the top level container for
			// all shapes.
			Drawing drawing = sheet.createDrawingPatriarch();

			// add a picture shape
			ClientAnchor anchor = helper.createClientAnchor();
			// set top-left corner of the picture,
			// subsequent call of Picture#resize() will operate relative to it
			anchor.setCol1(3);
			anchor.setRow1(3);

			drawing.createPicture(anchor, pictureIdx);
			// Picture pict = drawing.createPicture(anchor, pictureIdx);

			// auto-size picture relative to its top-left corner
			// pict.resize();

			// Para el formato del texto
			Font font = wb.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("arial");
			// font.setColor((short) 10);
			// pone el texto en negrita
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// Fonts are set into a style so create a new one to use.
			CellStyle style = wb.createCellStyle();
			style.setFont(font);

			// Para el formato del texto
			Font fontHearders = wb.createFont();
			fontHearders.setFontHeightInPoints((short) 10);
			fontHearders.setFontName("arial");
			// font.setColor((short) 10);
			// pone el texto en negrita
			fontHearders.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// Fonts are set into a style so create a new one to use.
			CellStyle styleHeaders = wb.createCellStyle();
			styleHeaders.setFont(fontHearders);
			styleHeaders.setBorderBottom(CellStyle.BORDER_MEDIUM);
			styleHeaders.setBorderLeft(CellStyle.BORDER_MEDIUM);
			styleHeaders.setBorderRight(CellStyle.BORDER_MEDIUM);
			styleHeaders.setBorderTop(CellStyle.BORDER_MEDIUM);

			Cell cell1 = row.createCell((short) 1);
			cell1.setCellValue(SEPARADOR);
			cell1.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(2, // first row (0-based)
					2, // last row (0-based)
					1, // first column (0-based)
					6 // last column (0-based)
			));

			// Title of 1 String
			Cell cell = rowTitle.createCell((short) 1);
			cell.setCellValue(title);
			cell.setCellStyle(style);
			rowTitle.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));

			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(5, // first row (0-based)
					5, // last row (0-based)
					1, // first column (0-based)
					4 // last column (0-based)
			));
			
			//******************************************************

			// Title of 2 String
			Cell cel2 = rowTitle2.createCell((short) 1);
			cel2.setCellValue(title2);
			cel2.setCellStyle(style);
			rowTitle2.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));

			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(6, // first row (0-based)
					6, // last row (0-based)
					1, // first column (0-based)
					4 // last column (0-based)
			));
			
			//******************************************************
			
			// Title of 3 String
			Cell cel3 = rowTitle3.createCell((short) 1);
			cel3.setCellValue(title3);
			cel3.setCellStyle(style);
			rowTitle3.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));

			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(7, // first row (0-based)
					7, // last row (0-based)
					1, // first column (0-based)
					4 // last column (0-based)
			));
			
			//******************************************************
			// Title of 4 String
			Cell cel4 = rowTitle4.createCell((short) 1);
			cel4.setCellValue(title4);
			cel4.setCellStyle(style);
			rowTitle4.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));

			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(8, // first row (0-based)
					8, // last row (0-based)
					1, // first column (0-based)
					4 // last column (0-based)
			));
		
         if (searchCriteria.size()>0) {
				
			
			// Title of Search Criteria
			Cell cellSearch = rowSearch.createCell((short) 1);
			cellSearch.setCellValue(SEARCH_CRITERIA);
			cellSearch.setCellStyle(style);
			rowSearch.setHeightInPoints((2 * sheet
					.getDefaultRowHeightInPoints()));

			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(9, // first row (0-based)
					9, // last row (0-based)
					1, // first column (0-based)
					4 // last column (0-based)
			));

			short auxCell = 1;
			
			
			for (int i = 0; i < searchCriteria.size(); i++) {
				Cell cells = rowResultSearch.createCell(auxCell);
				cells.setCellValue(searchCriteria.get(i));
				auxCell = (short) (auxCell + 1);
			}
			
         }

			// Title of List
			Cell cellList = rowList.createCell((short) 1);
			cellList.setCellValue(TABLE);
			cellList.setCellStyle(style);
			rowList.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));

			// Para unir filas y columnas
			sheet.addMergedRegion(new CellRangeAddress(12, // first row (0-based)
					12, // last row (0-based)
					1, // first column (0-based)
					2 // last column (0-based)
			));

			short aux = 14;

			// Style the cell with borders all around.
			CellStyle styleBorder = wb.createCellStyle();
			styleBorder.setBorderBottom(CellStyle.BORDER_THIN);
			styleBorder.setBorderLeft(CellStyle.BORDER_THIN);
			styleBorder.setBorderRight(CellStyle.BORDER_THIN);
			styleBorder.setBorderTop(CellStyle.BORDER_THIN);

			Row rows = sheet.createRow(aux);
			for (int i = 0; i < columnHeaders.size(); i++) {
				Cell cells = rows.createCell((short) i + 1);
				cells.setCellValue(columnHeaders.get(i));
				cells.setCellStyle(styleHeaders);
				sheet.autoSizeColumn((short) i + 1);
			}
			aux = (short) (aux + 1);
			
			int column =columnHeaders.size();
			int temp = data.length;
			
			if (temp>0) {
			for (int k = 0; k < temp; k++) {
				Row rowsData = sheet.createRow(aux);
				for (int l = 0; l < columnHeaders.size(); l++) {
					Cell cells = rowsData.createCell((short) l + 1);
					cells.setCellValue(data[k][l]);
					cells.setCellStyle(styleBorder);

				}
				aux = (short) (aux + 1);
			}
			}
			else{
				Row rowsData = sheet.createRow(aux);
				Cell cells = rowsData.createCell(1);
				cells.setCellValue("No se encontraron Coincidencias");
				cells.setCellStyle(styleBorder);
				sheet.addMergedRegion(new CellRangeAddress(12, // first row (0-based)
						12, // last row (0-based)
						1, // first column (0-based)
						column-2 // last column (0-based)
				));
			}

			out = new ByteArrayOutputStream();
			wb.write(out);
			out.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return out;
	}

	

}
