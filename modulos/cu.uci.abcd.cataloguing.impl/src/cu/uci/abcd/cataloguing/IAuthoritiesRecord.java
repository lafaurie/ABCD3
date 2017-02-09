package cu.uci.abcd.cataloguing;

import java.util.ArrayList;
import java.util.List;

import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;

public interface IAuthoritiesRecord {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public Boolean registerAuthoritiesRecord(ArrayList<FieldStructure> children, IDataBaseManager dataBaseManager,
			String dataBaseName);

	public void displayAuthoritiesRecord();

	public Boolean editAuthoritiesRecord(ArrayList<FieldStructure> children, IDataBaseManager dataBaseManager,
			String dataBaseName, Record record);

	public void deleteAuthoritiesRecord(IDataBaseManager dataBaseManager, IRecord record, String databaseName);

	public List<Record> findAuthoritiesRecords(String term, String databaseName) throws JisisDatabaseException;

	public List<Record> findByOptionsAuthoritiesRecords(List<Option> options, String databaseName) throws JisisDatabaseException;
}
