package cu.uci.abcd.acquisition.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.acquisition.IRegistrationManageAcquisitionService;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;

public class RegistrationManageAcquisition implements
		IRegistrationManageAcquisitionService {

	private IJisisDataProvider registro;
	private String controlNumber;
	private String defhome;
	private String fechadquisicion;
	

	private Option option1;
	

	public static final String DataBaseName = "Registro_De_Adquisicion";
	

	// RF_AQ15_Registry Acquisition
	public void registerAcquisition(IRecord newRecord, String dataBaseName, String defHome) {
		try {
			// if
			// (registro.findByOptions(listOption(newRecord.getField(2).getStringFieldValue(),
			// newRecord.getField(3).getStringFieldValue()), null,
			// DataBaseName).isEmpty())
			registro.saveRecord(newRecord, dataBaseName, defHome);
			// } catch (DbException e) {
			// e.printStackTrace();
			// throw new RuntimeException(e);
		} catch (JisisDatabaseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void editRecordAcquisition(Record record, String dataBaseName, String defHome) {

		try {
			registro.updateRecord(record, DataBaseName, defHome);
		} catch (JisisDatabaseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void bind(IJisisDataProvider registro, Map<?, ?> properties) {
		this.registro = registro;
	}

	public List<Record> findRecord(String title,String defHome) {
		List<Record> auxList = new ArrayList<>();

		option1 = new Option("2", title);

		// option2 = new Option("1", controlNumber);

		List<Option> aux = new ArrayList<>();
		aux.add(0, option1);
		// aux.add(1, option2);

		try {
			auxList = registro.findByOptions(aux, DataBaseName, defHome);
			return auxList;

		} catch (JisisDatabaseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public IJisisDataProvider getService() {
		return registro;
	}

	@Override
	public List<Record> findByMfns(long[] mfn, String databaseName, String defHome) {
		try {
			return registro.findByMfns(mfn, databaseName, defHome);
		} catch (JisisDatabaseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Record getRecordByMfn(long mfn, String defhome) {
		
		long[] mfns = new long[1];
		mfns[0] = mfn;
		
		try {			
			return registro.findByMfns(mfns, "Registro_De_Adquisicion", defhome).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String> getDatabaseFormats(String databaseName,String defHome)
			throws JisisDatabaseException {
		return registro.getDatabaseFormats(databaseName, defHome);
	}

	@Override
	public FormattedRecord getFormattedRecord(String databaseName,
			Record record, String formatName,String defHome) throws JisisDatabaseException {
		
		return registro.getFormattedRecord(databaseName, record, formatName,
				defHome);
	}

	@Override
	public Record getLastRecord(String databaseName,String defHome)
			throws JisisDatabaseException {
		return registro.getLastRecord(databaseName, defHome);
	}

	@Override
	public void deleteRecordAcquisition(Record record, String dataBaseName,String defHome) {
		getService().deleteRecord(record, dataBaseName, defHome);
	}

	@Override
	public List<Record> findByOptions(List<Option> options,
			String databaseName,String defHome)
			throws JisisDatabaseException {
		List<Record> list = new ArrayList<Record>();
		
		try {
			list = registro.findByOptions(options, databaseName,defHome);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}