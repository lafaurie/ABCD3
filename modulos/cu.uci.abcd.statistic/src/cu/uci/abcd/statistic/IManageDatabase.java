package cu.uci.abcd.statistic;

import java.util.Collection;
import java.util.List;

import org.unesco.jisis.corelib.common.FieldDefinitionTable;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Erdin Espinosa González
 * 
 */
public interface IManageDatabase {

	public List<String> getDataBaseNames(String library);

	public List<String> getWorksheetNames(String databaseName, String library);

	public WorksheetDef getWorksheet(String worksheetName, String databaseName, String library);

	public FieldDefinitionTable getFieldDefinitionTable(String databaseName, String library);

	public Record getLastRecord(String databaseName, String library);

	public List<Record> findByOptions(List<Option> options, String databaseName, String library);

	public List<Record> findByMfns(long[] mfn, String databaseName, String library);

	public Collection<Library> findAllLibrary();
	
	public boolean updateWorksheet(WorksheetDef worksheet);
	
	

}
