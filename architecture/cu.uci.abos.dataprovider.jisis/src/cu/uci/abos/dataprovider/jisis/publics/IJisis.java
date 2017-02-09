package cu.uci.abos.dataprovider.jisis.publics;

import java.util.List;

import org.unesco.jisis.corelib.common.FieldDefinitionTable;
import org.unesco.jisis.corelib.common.FieldSelectionTable;
import org.unesco.jisis.corelib.common.PrintFormat;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abos.dataprovider.jisis.publics.exceptions.DatabaseException;
import cu.uci.abos.dataprovider.jisis.publics.options.Options;

public interface IJisis {

    public void createDatabase(String name, FieldDefinitionTable fdt, FieldSelectionTable fst, WorksheetDef defaultWorksheet, PrintFormat defaultPft) throws DatabaseException;

    public void removeDatabase(String name) throws DatabaseException;

    public List<String> getDatabaseNames() throws DatabaseException;

    public void saveRecord(IRecord record, String databaseName) throws DatabaseException;

    public void saveFieldSelectionTable(FieldSelectionTable fst) throws DatabaseException;

    public FieldSelectionTable getFieldSelectionTable(String databaseName) throws DatabaseException;
    
    public List<Record> findByOptions(Options[] options) throws DatabaseException; 
    
    public List<Record> find(String termino) throws DatabaseException;

}
