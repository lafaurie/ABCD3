package cu.uci.abcd.cataloguing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;

public interface IExemplaryRecord{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public Boolean registerExemplaryRecord(ArrayList<FieldStructure> children, IDataBaseManager dataBaseManager,
			String dataBaseName, ILoanObjectCreation loanObjectCreation);

	public void displayExemplaryRecord();

	public Boolean editExemplaryRecord(ArrayList<FieldStructure> children, IDataBaseManager dataBaseManager,
			String dataBaseName, ILoanObjectCreation loanObjectCreation, Record record);

	public Boolean deleteExemplaryRecord(IDataBaseManager dataBaseManager, IRecord record, String databaseName, ILoanObjectCreation loanObjectCreation);

	public List<Record> findExemplaryRecords(String term, String databaseName, IDataBaseManager dataBaseManager) throws JisisDatabaseException;

	public List<Record> findByOptionsExemplaryRecords(Map<String, String> fieldsWithValue, String databaseName, IDataBaseManager dataBaseManager, String isisDefHome) throws JisisDatabaseException;
	
	public Boolean canEditAndRemove(List<LoanObject> loanObjects);

}
