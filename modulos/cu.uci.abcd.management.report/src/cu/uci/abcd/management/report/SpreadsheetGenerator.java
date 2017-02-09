package cu.uci.abcd.management.report;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 
 * @author
 * @version
 */
public interface SpreadsheetGenerator {
	/**
	 * 
	 * @param title
	 * @param searchCriteria
	 * @param columnHeaders
	 * @param data
	 * @return
	 */
	public ByteArrayOutputStream generateSpreadsheetAsTabularReport(
			String title, List<String> searchCriteria,
			List<String> columnHeaders, String[][] data);

	/**
	 * 
	 * @param title
	 * @param reportingObject
	 * @return
	 */
	public ByteArrayOutputStream generateSpreadsheetAsDataSheet(String title,
			List<IReportingObject> reportingObject);
	
	/**
	 * 
	 * @param columnHead
	 * @param columnHeaders
	 * @param rowHead
	 * @param rowHeaders
	 * @param data
	 * @return
	 */
	public ByteArrayOutputStream generateSpreadsheetStatistic(String columnHead, List<String> columnHeaders,String rowHead, List<String> rowHeaders, String[][] data);

	public ByteArrayOutputStream generateSpreadsheetMESReport(String title,String title2,String title3,String title4,
			List<String> searchCriteria, List<String> columnHeaders,
			String[][] data);
}
