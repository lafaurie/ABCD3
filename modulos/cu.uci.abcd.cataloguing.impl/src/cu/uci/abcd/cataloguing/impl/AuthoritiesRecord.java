package cu.uci.abcd.cataloguing.impl;

import java.util.ArrayList;
import java.util.List;

import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.IAuthoritiesRecord;
import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.cataloguing.util.JisisRegistration;
import cu.uci.abcd.cataloguing.util.QuickSort;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;

public class AuthoritiesRecord implements IAuthoritiesRecord {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	@Override
	public Boolean registerAuthoritiesRecord(ArrayList<FieldStructure> children, IDataBaseManager dataBaseManager,
			String dataBaseName) {

		IRecord record = Record.createRecord();
		Boolean successful = false;

		QuickSort sort = new QuickSort();
		sort.quickSort(0, children.size()-1, children);

		JisisRegistration jisis = new JisisRegistration(dataBaseName);
		boolean save = jisis.save(children, record);

		try {

			if(save){
				dataBaseManager.saveRecord((Record)record,dataBaseName);
				successful = true;
				RetroalimentationUtils.showInformationMessage(
						AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT);
			}
			else{
				successful = false;
				RetroalimentationUtils.showErrorShellMessage(
						"Se debe guardar al menos un campo");
			}

		} catch (JisisDatabaseException e) {
			RetroalimentationUtils.showErrorShellMessage(
					"Problema de conección con JISIS");
		}
		return successful;
	}

	@Override
	public void displayAuthoritiesRecord() {
		//display

	}

	@Override
	public Boolean editAuthoritiesRecord(ArrayList<FieldStructure> children, IDataBaseManager dataBaseManager,
			String dataBaseName, Record record) {

		Boolean successfull = false;
		QuickSort sort = new QuickSort();
		sort.quickSort(0, children.size()-1, children);

		record.removeEmptyFields();
		record.clear();

		JisisRegistration jisis = new JisisRegistration(dataBaseName);
		boolean save = jisis.save(children, record);

		if(save){
			try {
				dataBaseManager.updateRecord(record, dataBaseName);
				successfull = true;
				RetroalimentationUtils.showInformationMessage(
						AbosMessages.get().MSG_INF_UPDATE_DATA);

			} catch (JisisDatabaseException e) {
				RetroalimentationUtils.showErrorShellMessage(
						"Problema de conección con JISIS");
			}
		}
		else{
			successfull = false;
			RetroalimentationUtils.showErrorShellMessage(
					"Se debe guardar al menos un campo");
		}
		return successfull;
	}

	@Override
	public void deleteAuthoritiesRecord(IDataBaseManager dataBaseManager, IRecord record, String databaseName) {
		//borrar el record
		try {
			dataBaseManager.deleteRecord(record, databaseName);

			RetroalimentationUtils.showInformationMessage(
					AbosMessages.get().MSG_INF_DELETE_ONE_ITEM);

		} catch (JisisDatabaseException e) {
			RetroalimentationUtils.showErrorShellMessage(
					"Problema de conección con JISIS");
		}
	}

	@Override
	public List<Record> findAuthoritiesRecords(String term, String databaseName)
			throws JisisDatabaseException {

		return null;
	}

	@Override
	public List<Record> findByOptionsAuthoritiesRecords(List<Option> options,
			String databaseName) throws JisisDatabaseException {

		return null;
	}
}
