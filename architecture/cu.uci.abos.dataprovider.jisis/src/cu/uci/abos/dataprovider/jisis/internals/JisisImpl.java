package cu.uci.abos.dataprovider.jisis.internals;

import java.util.ArrayList;
import java.util.List;

import org.unesco.jisis.corelib.client.ClientDbProxy;
import org.unesco.jisis.corelib.common.CreateDbParams;
import org.unesco.jisis.corelib.common.FieldDefinitionTable;
import org.unesco.jisis.corelib.common.FieldSelectionTable;
import org.unesco.jisis.corelib.common.Global;
import org.unesco.jisis.corelib.common.PrintFormat;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abos.dataprovider.jisis.internals.utils.ConnectionManager;
import cu.uci.abos.dataprovider.jisis.publics.IJisis;
import cu.uci.abos.dataprovider.jisis.publics.exceptions.DatabaseException;
import cu.uci.abos.dataprovider.jisis.publics.options.Options;
import cu.uci.abos.dataprovider.jisis.publics.options.OptionsAnd;
import cu.uci.abos.dataprovider.jisis.publics.options.OptionsOr;

public class JisisImpl implements IJisis {

    private String dbHome;

    private ClientDbProxy proxy;
    private ConnectionManager connectionManager;

    /**
     * Used to initialize bean configuration from spring context
     * */
    @SuppressWarnings("unused")
    private void initialize() {
	proxy = connectionManager.getProxy();
    }

    @Override
    public void createDatabase(String name, FieldDefinitionTable fdt, FieldSelectionTable fst, WorksheetDef defaultWorksheet, PrintFormat defaultPft) throws DatabaseException {
	try {
	    CreateDbParams params = new CreateDbParams(dbHome, name);
	    params.setFieldDefinitionTable(fdt);
	    params.setFieldSelectionTable(fst);
	    params.setDefaultWorkSheet(defaultWorksheet);
	    params.setDefaultPft(defaultPft.getName(), defaultPft.getFormat());

	    proxy.createDatabase(params, Global.DATABASE_BULK_WRITE);
	} catch (DbException e) {
	    System.out.println("========================================ERROR=====================================");
	    e.printStackTrace();
	    System.out.println("Database connection failed");
	    System.out.println("==================================================================================");
	    throw new DatabaseException("Error creating database");
	}
    }

    public void saveFieldSelectionTable(FieldSelectionTable fst) throws DatabaseException {
	try {
	    proxy.saveFieldSelectionTable(fst);
	} catch (DbException e) {
	    System.out.println("========================================ERROR=====================================");
	    e.printStackTrace();
	    System.out.println("Database connection failed");
	    System.out.println("==================================================================================");
	    throw new DatabaseException("Error saving fst");
	}
    }

    public void removeDatabase(String databaseName) throws DatabaseException {
	throw new DatabaseException("Exception removing database. Not implemented yet");
    }

    public List<String> getDatabaseNames() throws DatabaseException {
	List<String> databaseNames = new ArrayList<>();
	try {
	    databaseNames = connectionManager.getConnection().getDbNames(getDbHome());
	} catch (DbException e) {
	    System.out.println("========================================ERROR=====================================");
	    e.printStackTrace();
	    System.out.println("Database connection failed");
	    System.out.println("==================================================================================");
	    throw new DatabaseException("Error getting database names");
	}

	return databaseNames;
    }

    public void saveRecord(IRecord record, String databaseName) throws DatabaseException {
	try {
	    proxy.getDatabase(getDbHome(), databaseName, Global.DATABASE_BULK_WRITE);
	    proxy.addRecord((Record) record);
	} catch (Exception e) {
	    System.out.println("========================================ERROR=====================================");
	    e.printStackTrace();
	    System.out.println("Error creating record");
	    System.out.println("==================================================================================");
	    throw new DatabaseException("Error saving record");
	}
    }

    public void deleteRecord(IRecord record, String databaseName) {
	try {
	    proxy.getDatabase(getDbHome(), databaseName, Global.DATABASE_BULK_WRITE);
	    proxy.deleteRecord(record.getMfn());
	} catch (Exception e) {
	    System.out.println("========================================ERROR=====================================");
	    e.printStackTrace();
	    System.out.println("Error removing record");
	    System.out.println("==================================================================================");
	}
    }

    public FieldSelectionTable getFieldSelectionTable(String databaseName) throws DatabaseException {
	try {
	    proxy.getDatabase(getDbHome(), databaseName, Global.DATABASE_BULK_WRITE);
	    return proxy.getFieldSelectionTable();
	} catch (DbException e) {
	    System.out.println("========================================ERROR=====================================");
	    e.printStackTrace();
	    System.out.println("Database connection failed");
	    System.out.println("==================================================================================");
	    throw new DatabaseException("Error obtaining FST");
	}
    }
    
    public List<Record> findByOptions(Options[] options) throws DatabaseException{
    	List<Record> records = new ArrayList<>();
    	long[] mfn = null;
    	String parte1 = "";
    	String parte2 = "";
    	String consulta = "";
		for(int i = 0; i < options.length ; i++){
			if(options[i] instanceof Options){			
				consulta = options[1].getCampo() + ":" + options[1].getTermino();				
			}
			else if(options[i] instanceof OptionsAnd){				
				parte1 += options[i].getCampo() + ":" + options[i].getTermino() + " AND ";
				parte2 = options[options.length].getCampo() + ":" + options[options.length].getTermino();
				consulta = parte1 + parte2;				
			}
			else if(options[i] instanceof OptionsOr){
				parte1 += options[i].getCampo() + ":" + options[i].getTermino() + " OR ";
				parte2 = options[options.length].getCampo() + ":" + options[options.length].getTermino();
				consulta = parte1 + parte2;				
			}
			else{
				parte1 += options[i].getCampo() + ":" + options[i].getTermino() + " AND NOT ";
				parte2 = options[options.length].getCampo() + ":" + options[options.length].getTermino();
				consulta = parte1 + parte2;			
			}			
		}	
		try {
			mfn = proxy.searchLucene(consulta);
			for(int j = 0; j < mfn.length; j++){
					records.add((Record) proxy.getRecord(mfn[j]));
			}
		} catch (DbException e) {					
			e.printStackTrace();
		}			
		return records;    	
    }   
    
    public List<Record> find(String termino){
    	FieldSelectionTable fst = new FieldSelectionTable(); 
    	List<Record> records = new ArrayList<>();
    	long[] mfn = null;
    	int[] tags = null;
    	String parte1 = "";
    	String parte2 = "";    	
    	String consulta = "";
    	try {
			fst = proxy.getFieldSelectionTable();
			tags = fst.getEntriesTag();
		} catch (DbException e) {
			e.printStackTrace();
		}
		String[] terminos = termino.split(" ");
		for(int i = 0; i < terminos.length; i++){
			if(terminos.length == 1){
				for (int j = 0; j < tags.length -1; j++) {
					parte1 += tags[j] + ":" + terminos[0] + " OR ";
					parte2 = tags[tags.length] + ":" + terminos[0];					
				}	
				consulta = parte1 + parte2;
			}
			else if(terminos.length > 1){
				for (int j = 0; j < tags.length; j++) {		
					parte1 = tags[j] + ":" + terminos[i] + " OR ";					
				}
				consulta = parte1.substring(0, parte1.length()-4);
			}			
		}
		try {
			mfn = proxy.searchLucene(consulta);
			for(int h = 0; h < mfn.length; h++){
					records.add((Record) proxy.getRecord(mfn[h]));
			}
		} catch (DbException e) {					
			e.printStackTrace();
		}			
		return records;    	    	
    }
    
    public String getDbHome() {
    	return dbHome;
    }

    public void setDbHome(String dbHome) {
    	this.dbHome = dbHome;
    }

    public ConnectionManager getConnectionManager() {
    	return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
    	this.connectionManager = connectionManager;
    }

}
