package cu.uci.abcd.cataloguing;

import java.util.List;

import org.unesco.jisis.corelib.common.FieldSelectionTable;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;


public interface IDataBaseManager {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	//Implement RF_CA1_Register datas of the Exemplary Record
	//Implement RF_CA9_Register datas of the Authorities Record 
	public void saveRecord(IRecord record, String databaseName) throws JisisDatabaseException;

	//Implement RF_CA4_Delete Exemplary Record
	//Implement RF_CA12_Delete Authorities Record
	public void deleteRecord(IRecord record, String databaseName) throws JisisDatabaseException;

	//Implement RF_CA3_Edit Exemplary Record
	//Implement RF_CA11_Edit Authorities Record
	public void updateRecord(IRecord record, String databaseName) throws JisisDatabaseException;

	//Implement RF_CA9.2_Get all databases
	public List<String> getDatabaseNames() throws JisisDatabaseException;

	//Implement RF_CA6_Catalog Query
	//Implement RF_CA13_AuthoritiesQuery
	public FieldSelectionTable getFieldSelectionTable(String databaseName) throws JisisDatabaseException;

	//Implement RF_CA13_Advcanced Query
	public List<Record> findByOptions(List<Option> options, String databaseName) throws JisisDatabaseException;

	//Implement RF_CA6_Catalog Query
	//Implement RF_CA13_AuthoritiesQuery
	public List<Record> find(String term, String databaseName) throws JisisDatabaseException;

	//CUS_CA_Manage Catalog Record 
	public List<String> getWorksheetNames(String databaseName) throws JisisDatabaseException;

	//CUS_CA_Manage Catalog Record 
	public WorksheetDef getWorksheet(String worksheetName, String databaseName) throws JisisDatabaseException;

	//CUS_CA_Manage Catalog Record 
	public IJisisDataProvider getService();

	//Implement RF_CA2_Display data of the Exemplary Record
	//Implement RF_CA10_Display data of the Authorities Record
	public List<String> getDatabaseFormats(String databaseName) throws JisisDatabaseException;

	//Implement RF_CA2_Display data of the Exemplary Record
	//Implement RF_CA10_Display data of the Authorities Record
	public FormattedRecord getFormattedRecord(String databaseName, Record record, String formatName) throws JisisDatabaseException;

	//Implement RF_CA2_Display data of the Exemplary Record
	//Implement RF_CA10_Display data of the Authorities Record
	public Record getLastRecord(String databaseName) throws JisisDatabaseException;

}
