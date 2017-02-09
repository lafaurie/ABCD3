package cu.uci.abcd.opac.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.unesco.jisis.corelib.common.FieldSelectionTable;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.opac.IOpacDataBaseManager;
import cu.uci.abcd.opac.RecordIsis;
      
public class OpacDataBaseManager implements IOpacDataBaseManager {
    
	private IJisisDataProvider service;
    
	@Override
	public List<RecordIsis> findByOptions(List<Option> options, String databaseName, String libraryIsisDatabasesHomeFolder, Library library) throws JisisDatabaseException {
             
		List<Record> temp = service.findByOptions(options, databaseName, libraryIsisDatabasesHomeFolder, true);
	    
		List<RecordIsis> records = new ArrayList<RecordIsis>();

		for (Record record : temp)
			records.add(new RecordIsis(record, databaseName, library));
     
		return records;
         
	}            
     
	@Override
	public List<RecordIsis> find(String term, String databaseName, String libraryIsisDatabasesHomeFolder, Library library, List<Option> options) throws JisisDatabaseException {

		List<Record> temp = service.find(term, databaseName, libraryIsisDatabasesHomeFolder, options);

		List<RecordIsis> records = new ArrayList<RecordIsis>();

		for (Record record : temp)
			records.add(new RecordIsis(record, databaseName, library));

		return records;

	}

	@Override
	public FieldSelectionTable getFieldSelectionTable(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return this.service.getFieldSelectionTable(databaseName, libraryIsisDatabasesHomeFolder);
	}

	@Override
	public WorksheetDef getWorksheet(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return this.service.getWorksheet("Default worksheet", databaseName, libraryIsisDatabasesHomeFolder);
	}

	@Override
	public List<String> getWorksheetNames(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return this.service.getWorksheetNames(databaseName, libraryIsisDatabasesHomeFolder);
	}

	public void bind(IJisisDataProvider provider, Map<?, ?> properties) {
		this.service = provider;
	}
      
	@Override
	public List<String> getDatabaseNames(String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return this.service.getDatabaseNames(libraryIsisDatabasesHomeFolder);
	}
/*
	@Override
	public List<RecordIsis> findByMfns(long[] mfns, String databaseName, String libraryIsisDatabasesHomeFolder, Library library) throws JisisDatabaseException {
        		
		List<Record> temp = service.findByMfns(mfns, databaseName, libraryIsisDatabasesHomeFolder);
		
		List<RecordIsis> records = new ArrayList<RecordIsis>();

		for (Record record : temp)
			records.add(new RecordIsis(record, databaseName, library));

		return records;
	}*/
       
	@Override
	public List<RecordIsis> findRecordByControlNumber(List<String> controlNumber, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {

		List<Record> temp = service.findByRecordNumber(controlNumber, databaseName, libraryIsisDatabasesHomeFolder);
		
		List<RecordIsis> records = new ArrayList<RecordIsis>();

		for (Record record : temp)
			records.add(new RecordIsis(record, databaseName));

		return records;
	}

	@Override
	public List<String> getDatabaseFormats(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return this.service.getDatabaseFormats(databaseName, libraryIsisDatabasesHomeFolder);
	}
   
	@Override
	public FormattedRecord getFormattedRecord(String databaseName, Record record, String formatName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return this.service.getFormattedRecord(databaseName, record, formatName, libraryIsisDatabasesHomeFolder);
	}

}
