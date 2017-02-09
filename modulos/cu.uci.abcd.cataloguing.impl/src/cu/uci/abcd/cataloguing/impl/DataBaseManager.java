package cu.uci.abcd.cataloguing.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.unesco.jisis.corelib.common.FieldSelectionTable;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abos.widget.template.util.Util;

public class DataBaseManager implements IDataBaseManager {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private IJisisDataProvider service;

	//Implement RF_CA1_Register datas of the Exemplary Record
	//Implement RF_CA9_Register datas of the Authorities Record
	@Override
	public void saveRecord(IRecord record, String databaseName)
			throws JisisDatabaseException {
		service.saveRecord(record, databaseName, Util.getDefHome());
	}

	//Implement RF_CA4_Delete Exemplary Record
	//Implement RF_CA12_Delete Authorities Record
	@Override
	public void deleteRecord(IRecord record, String databaseName)
			throws JisisDatabaseException {
		service.deleteRecord(record, databaseName, Util.getDefHome());
	}

	//Implement RF_CA3_Edit Exemplary Record
	//Implement RF_CA11_Edit Authorities Record
	@Override
	public void updateRecord(IRecord record, String databaseName)
			throws JisisDatabaseException {
		service.updateRecord(record, databaseName, Util.getDefHome());
	}

	//Implement RF_CA9.2_Get all databases
	@Override
	public List<String> getDatabaseNames() throws JisisDatabaseException{
		return service.getDatabaseNames(Util.getDefHome());
	}

	//Implement RF_CA6_Catalog Query
	//Implement RF_CA13_AuthoritiesQuery
	@Override
	public FieldSelectionTable getFieldSelectionTable(String databaseName)
			throws JisisDatabaseException {
		return service.getFieldSelectionTable(databaseName, Util.getDefHome());
	}

	//Implement RF_CA13_Advcanced Query
	@Override
	public List<Record> findByOptions(List<Option> options, String databaseName)
			throws JisisDatabaseException {
		return service.findByOptions(options, databaseName, Util.getDefHome());
	}

	//Implement RF_CA6_Catalog Query
	//Implement RF_CA13_AuthoritiesQuery
	@Override
	public List<Record> find(String term, String databaseName)
			throws JisisDatabaseException {

		List<Record> records = null;

		try {
			records = service.find(term, databaseName, Util.getDefHome(), new ArrayList<Option>());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return records;
	}

	//CUS_CA_Manage Catalog Record
	@Override
	public List<String> getWorksheetNames(String databaseName)
			throws JisisDatabaseException {
		return service.getWorksheetNames(databaseName, Util.getDefHome());
	}

	//CUS_CA_Manage Catalog Record 
	@Override
	public WorksheetDef getWorksheet(String worksheetName, String databaseName)
			throws JisisDatabaseException {
		return service.getWorksheet(worksheetName, databaseName, Util.getDefHome());
	}

	//service
	public void bind(IJisisDataProvider provider, Map<?, ?> properties) {
		this.service = provider;
	}

	//CUS_CA_Manage Catalog Record
	public IJisisDataProvider getService() {
		return service;
	}

	//Implement RF_CA2_Display data of the Exemplary Record
	//Implement RF_CA10_Display data of the Authorities Record
	@Override
	public List<String> getDatabaseFormats(String databaseName)
			throws JisisDatabaseException {
		return service.getDatabaseFormats(databaseName, Util.getDefHome());
	}

	//Implement RF_CA2_Display data of the Exemplary Record
	//Implement RF_CA10_Display data of the Authorities Record
	@Override
	public FormattedRecord getFormattedRecord(String databaseName,
			Record record, String formatName) throws JisisDatabaseException {
		return service.getFormattedRecord(databaseName, record, formatName, Util.getDefHome());
	}

	//Implement RF_CA2_Display data of the Exemplary Record
	//Implement RF_CA10_Display data of the Authorities Record
	@Override
	public Record getLastRecord(String databaseName)
			throws JisisDatabaseException {
		return service.getLastRecord(databaseName, Util.getDefHome());
	}

}
