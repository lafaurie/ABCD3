package cu.uci.abcd.acquisition;

import java.util.List;

import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;

// 3.10. Manage Registry Acquisition
public interface IRegistrationManageAcquisitionService {
	// RF_AQ15_Registry Acquisition
	public void registerAcquisition(IRecord record, String dataBaseName, String defHome);

	// RF_AQ17_Edit Record Acquisition
	public void editRecordAcquisition(Record record, String dataBaseName, String viaAdquisicion);

	// RF_AQ18_ Delete Record Acquisition
	public void deleteRecordAcquisition(Record record, String dataBaseName,String defHome);

	// Find Record
	public List<Record> findRecord(String parametros1,String defHome);

	//Get service
	public IJisisDataProvider getService();

	//Search record by mfn
	public List<Record> findByMfns(long[] mfn, String databaseName, String defHome);

	//Search record by mfn
	public Record getRecordByMfn(long mfn, String defHome );
	
	
	//Get Data base formats
	public List<String> getDatabaseFormats(String databaseName,String defHome) throws JisisDatabaseException;
	    
	// Get formatted record
	public FormattedRecord getFormattedRecord(String databaseName, Record record, String formatName,String defHome) throws JisisDatabaseException;
	
	// Get last record
	public Record getLastRecord(String databaseName,String defHome) throws JisisDatabaseException;
	   
	// //Search record by criteria
	 public List<Record> findByOptions(List<Option> options, String databaseName,String defHome) throws JisisDatabaseException;
	
}
