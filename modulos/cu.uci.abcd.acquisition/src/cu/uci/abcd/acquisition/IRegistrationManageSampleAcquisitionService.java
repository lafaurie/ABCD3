package cu.uci.abcd.acquisition;

import java.util.List;

import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;

public interface IRegistrationManageSampleAcquisitionService {

	//Register Sample Acquisition
	public void registerSampleAcquisition(IRecord newRecord);
	

	// Edit Record Sample Acquisition
	public void editRecordSampleAcquisition(Record record, Record nuevo);

	//Delete Record Sample Acquisition
	public void deleteRecordSampleAcquisition(Record record);

	// Find Records
	public List<Record> findRecords(String parametro1,String parametro2);

	//Get Service
	public IJisisDataProvider getService();

	//Check Record Sample Acquisition
	public IRecord readRecordSampleAcquisition(IRecord r);
	


}
