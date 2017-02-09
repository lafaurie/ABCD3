package cu.uci.abcd.circulation;

import java.util.List;

import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;

public interface ILoanObjectJISISService {
	
	public void editWhenNotExistingCopies(IRecord record);

	// Find Record
	public List<Record> findRecord(String parametros1);

	//Get service
	public IJisisDataProvider getService();

	//Search record by mfn
	public List<Record> findByMfns(long[] mfn);

	//Search record by mfn
	public Record getRecordByMfn(long mfn);
		
	//Get Data base formats
	public List<String> getDatabaseFormats(String databaseName) throws JisisDatabaseException;
	    
	// Get formatted record
	public FormattedRecord getFormattedRecord(String databaseName, Record record, String formatName) throws JisisDatabaseException;
	
	// Get last record
	public Record getLastRecord(String databaseName) throws JisisDatabaseException;
		
}
