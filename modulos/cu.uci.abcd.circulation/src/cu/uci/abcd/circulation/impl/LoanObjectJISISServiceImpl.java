package cu.uci.abcd.circulation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.circulation.ILoanObjectJISISService;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;


public class LoanObjectJISISServiceImpl implements ILoanObjectJISISService {

	private IJisisDataProvider registro;
	private Option option1;
	private String dataBaseName= "marc21";
	
	public void editWhenNotExistingCopies(IRecord newRecord) {
		try {
			registro.saveRecord(newRecord, dataBaseName,"DEF_HOME");
		
		} catch (JisisDatabaseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
		
	public void bind(IJisisDataProvider registro, Map<?, ?> properties) {
		this.registro = registro;
	}

	public List<Record> findRecord(String controlNumber) {
		List<Record> auxList = new ArrayList<>();
	
		option1 = new Option("1", controlNumber);
		
		List<Option> aux = new ArrayList<Option>();
		aux.add(option1);
		
		try {
			auxList= registro.findByOptions(aux, dataBaseName,"DEF_HOME");
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
	public List<Record> findByMfns(long[] mfn) {
		try {
			return registro.findByMfns(mfn, dataBaseName,"DEF_HOME");
		} catch (JisisDatabaseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Record getRecordByMfn(long mfn) {
		try {
			return ((ILoanObjectJISISService) registro).getRecordByMfn(mfn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String> getDatabaseFormats(String databaseName)
			throws JisisDatabaseException {
		return registro.getDatabaseFormats(databaseName, "DEF_HOME");
	}

	@Override
	public FormattedRecord getFormattedRecord(String databaseName,
			Record record, String formatName) throws JisisDatabaseException {
		return registro.getFormattedRecord(databaseName, record, formatName, "DEF_HOME");
	}

	@Override
	public Record getLastRecord(String databaseName)
			throws JisisDatabaseException {
		return registro.getLastRecord(databaseName, "DEF_HOME");
	}
}