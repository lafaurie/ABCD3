package cu.uci.abcd.dataprovider.jisis;

import java.util.List;

import org.unesco.jisis.corelib.common.FieldDefinitionTable;
import org.unesco.jisis.corelib.common.FieldSelectionTable;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.common.PrintFormat;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;

/**
 * 
 * @author Leandro Tabares Martín
 * @version 1.0
 * 
 */
public interface IJisisDataProvider {

    /**
     * 
     * @param name
     * @param fdt
     * @param fst
     * @param defaultWorksheet
     * @param defaultPft
     * @throws JisisDatabaseException
     */
    public void createDatabase(String name, FieldDefinitionTable fdt, FieldSelectionTable fst, WorksheetDef defaultWorksheet, PrintFormat defaultPft, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    /**
     * 
     * @param record
     * @param databaseName
     * @throws JisisDatabaseException
     */
    public void deleteRecord(IRecord record, String databaseName, String libraryIsisDatabasesHomeFolder);

    /**
     * 
     * @param record
     * @param databaseName
     * @throws JisisDatabaseException
     */
    public void updateRecord(IRecord record, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    /**
     * 
     * @return
     * @throws JisisDatabaseException
     */
    public List<String> getDatabaseNames(String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    /**
     * 
     * @param record
     * @param databaseName
     * @throws JisisDatabaseException
     */
    public void saveRecord(IRecord record, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    /**
     * 
     * @param fst
     * @throws JisisDatabaseException
     */
    public void saveFieldSelectionTable(FieldSelectionTable fst, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    /**
     * 
     * @param databaseName
     * @return
     * @throws JisisDatabaseException
     */
    public FieldSelectionTable getFieldSelectionTable(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    /**
     * 
     * @param options
     * @return
     * @throws JisisDatabaseException
     */
    public List<Record> findByOptions(List<Option> options, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    
    /**
     * 
     * @param options
     * @param databaseName
     * @param libraryIsisDatabasesHomeFolder
     * @param opac
     * @return
     * @throws JisisDatabaseException
     */
     
    public List<Record> findByOptions(List<Option> options, String databaseName, String libraryIsisDatabasesHomeFolder, boolean opac) throws JisisDatabaseException;

       
    
    /**
     * 
     * @param term
     * @return
     * @throws JisisDatabaseException
     */
    
    public List<Record> find(String term, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;
    
    public List<Record> find(String term, String databaseName, String libraryIsisDatabasesHomeFolder, List<Option> options) throws JisisDatabaseException;

    public List<String> getWorksheetNames(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    public WorksheetDef getWorksheet(String worksheetName, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    public List<String> getDatabaseFormats(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    public FormattedRecord getFormattedRecord(String databaseName, Record record, String formatName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    public Record getLastRecord(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;
    
    public Record getFirstRecord(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    public FieldDefinitionTable getFieldDefinitionTable(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    /**
     * 
     * @param mfns
     * @return
     * @throws JisisDatabaseException
     */
    public List<Record> findByMfns(long[] mfn, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;
    
    public Record findByMfn(long mfn, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException;

    /**
     *    
     * @param controlNumber
     * @return
     * @throws JisisDatabaseException
     */    
    public List<Record> findByRecordNumber(List<String> recordNumber, String databaseName, String libraryIsisDatabasesHomeFolder);
    

    public Long totalRecords(String databaseName, String libraryIsisDatabasesHomeFolder);


    /**
     * 
     * @param worksheet
     * @return 
     * @throws JisisDatabaseException
     */
    public boolean updateWorksheet(WorksheetDef worksheet)	throws JisisDatabaseException;

    

}
