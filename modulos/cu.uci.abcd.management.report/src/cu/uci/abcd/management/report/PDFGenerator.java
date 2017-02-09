package cu.uci.abcd.management.report;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 
 * @author
 * @version
 */
public interface PDFGenerator {
	/**
	 * 
	 * @param title
	 * @param searchCriteria
	 * @param columnHeaders
	 * @param data
	 * @return
	 */
	public ByteArrayOutputStream generatePDFAsTabularReport(String title,
			List<String> searchCriteria, List<String> columnHeaders,
			String[][] data);

	/**
	 * 
	 * @param title
	 * @param reportingObject
	 * @return
	 */
	public ByteArrayOutputStream generatePDFAsDataSheet(String title,
			List<IReportingObject> reportingObject);
	
	/**
	 * 
	 * @param title
	 * @param data
	 * @return
	 */
	public ByteArrayOutputStream generatePDFListSelection(String title, List<String> data);
	
	/**
	 * 
	 * @param columnHead
	 * @param columnHeaders
	 * @param rowHead
	 * @param rowHeaders
	 * @param data
	 * @return
	 */
	public ByteArrayOutputStream generatePDFStatistic(String columnHead, List<String> columnHeaders,String rowHead, List<String> rowHeaders, String[][] data);

	
	/**
	 * 
	 * @param title
	 * @param title2
	 * @param title3
	 * @param title4
	 * @param searchCriteria
	 * @param columnHeaders
	 * @param data
	 * @return
	 */
	public ByteArrayOutputStream generatePDFMESReport(String title,String title2,String title3,String title4,
			List<String> searchCriteria, List<String> columnHeaders,
			String[][] data);
	
}
