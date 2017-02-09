package cu.uci.abcd.opac;

import java.util.List;
   
import org.unesco.jisis.corelib.common.FieldSelectionTable;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.domain.management.library.Library;
    
public interface IOpacDataBaseManager {
	//FIXME FALTAN COMENTARIOS DE INTERFACE
      
	public List<RecordIsis> findByOptions(List<Option> options, String databaseName, String libraryIsisDatabasesHomeFolder, Library library) throws JisisDatabaseException;

	public List<RecordIsis> find(String term, String databaseName, String libraryIsisDatabasesHomeFolder, Library library, List<Option> options) throws JisisDatabaseException;

	public FieldSelectionTable getFieldSelectionTable(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

	public List<String> getWorksheetNames(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

	public WorksheetDef getWorksheet(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

	public List<String> getDatabaseNames(String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;
   
    public List<RecordIsis> findRecordByControlNumber(List<String> controlNumber, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    public List<String> getDatabaseFormats(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    public FormattedRecord getFormattedRecord(String databaseName, Record record, String formatName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

}
