package cu.uci.abcd.cataloguing.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;
import org.unesco.jisis.corelib.record.StringOccurrence;
import org.unesco.jisis.corelib.record.Subfield;

import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.cataloguing.IExemplaryRecord;
import cu.uci.abcd.cataloguing.ILoanObjectCreation;
import cu.uci.abcd.cataloguing.util.Constant;
import cu.uci.abcd.cataloguing.util.JisisRegistration;
import cu.uci.abcd.cataloguing.util.QuickSort;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionAND;
import cu.uci.abcd.domain.cataloguing.CataloguingNomenclator;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;

public class ExemplaryRecord implements IExemplaryRecord{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	@Override
	public Boolean registerExemplaryRecord(ArrayList<FieldStructure> children, IDataBaseManager dataBaseManager,
			String dataBaseName, ILoanObjectCreation loanObjectCreation){

		Boolean successfull = false;
		IRecord record = Record.createRecord();

		QuickSort sort = new QuickSort();
		sort.quickSort(0, children.size()-1, children);

		JisisRegistration jisis = new JisisRegistration(dataBaseName);
		boolean save = jisis.save(children, record);

		try {
			if(save){
				dataBaseManager.saveRecord((Record)record,dataBaseName);
				successfull = true;
				RetroalimentationUtils.showInformationMessage(
						AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT);
			}
			else{
				successfull = false;
			}

		} catch (JisisDatabaseException e) {
			RetroalimentationUtils.showErrorShellMessage(
					"Problema de conección con JISIS");
		}
		return successfull;
	}

	@Override
	public void displayExemplaryRecord() {
		//for display

	}

	@Override
	public Boolean editExemplaryRecord(ArrayList<FieldStructure> children, IDataBaseManager dataBaseManager,
			String dataBaseName, ILoanObjectCreation loanObjectCreation, Record record) {
		
		Boolean successfull = false;
		String controlNumber = null;
		
		try {
			controlNumber = record.getField(1).getStringFieldValue();
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<LoanObject> loanObjects = loanObjectCreation.findAllByControlNumber(controlNumber);
		
		QuickSort sort = new QuickSort();
		sort.quickSort(0, children.size()-1, children);

		JisisRegistration jisis = new JisisRegistration(dataBaseName);

		record.removeEmptyFields();
		record.clear();

		boolean save = jisis.save(children, record);

		try {
			if(save){

			   dataBaseManager.updateRecord(record, dataBaseName);

				//For loan object
			    //update all loan objects that have the same control number that the record

				int loanObjectCount = loanObjects.size();
					
				if(loanObjectCount > 0){
						
				    String title = null;
				    String author = null;
					String editionNumber = null;

					//title
					Field field = (Field) record.getField(Constant.TITLE);
					StringOccurrence occurrence = (StringOccurrence) field.getOccurrence(0);
					Subfield[] subFieldsRecord = occurrence.getSubfields();
					int count1 = subFieldsRecord.length;
					for (int i = 0; i < count1; i++) {
						if(Constant.TITLE_SUB_FIELD_CODE == subFieldsRecord[i].getSubfieldCode()){
							title = subFieldsRecord[i].getData();
							break;
						}
					}

					//author
					Field field2 = (Field) record.getField(Constant.AUTHOR);
					StringOccurrence occurrence2 = (StringOccurrence) field2.getOccurrence(0);
					if(occurrence2 != null){
						Subfield[] subFieldsRecord2 = occurrence2.getSubfields();
						int count2 = subFieldsRecord2.length;
						for (int i = 0; i < count2; i++) {
							if(Constant.AUTHOR_SUB_FIELD_CODE == subFieldsRecord[i].getSubfieldCode()){
								author = subFieldsRecord2[i].getData();
								break;
							}
						}
					}

					//editionNumber
					Field field3 = (Field) record.getField(Constant.EDITION_NUMBER);
					StringOccurrence occurrence3 = (StringOccurrence) field3.getOccurrence(0);
					if(occurrence3 != null){
						Subfield[] subFieldsRecord3 = occurrence3.getSubfields();
						int count3 = subFieldsRecord3.length;
						for (int i = 0; i < count3; i++) {
							if(Constant.EDITION_SUB_FIELD_CODE == subFieldsRecord[i].getSubfieldCode()){
								editionNumber = subFieldsRecord3[i].getData();
								break;
							}
						}
					}	

					for (int i = 0; i < loanObjectCount; i++) {
						LoanObject currentLoanObject = loanObjects.get(i);

						if(title != null)
							currentLoanObject.setTitle(title);

						if(author != null)
							currentLoanObject.setAuthor(author);

						if(editionNumber != null)
							currentLoanObject.setEditionNumber(editionNumber);
							
						currentLoanObject.setCatalogued(true);

						loanObjectCreation.addLoanObject(currentLoanObject);
					}
				}

				successfull = true;
				RetroalimentationUtils.showInformationMessage(
						AbosMessages.get().MSG_INF_UPDATE_DATA);
				}
				else{
					successfull = false;
					RetroalimentationUtils.showErrorShellMessage(
							"Se debe guardar al menos un campo");
				}

			} catch (JisisDatabaseException e) {
				e.printStackTrace();
			} catch (DbException e) {
				e.printStackTrace();
			}
			
		return successfull;
	}

	@Override
	public Boolean deleteExemplaryRecord(IDataBaseManager dataBaseManager, IRecord record, String databaseName, 
			ILoanObjectCreation loanObjectCreation) {
		
		boolean response = false;
		
		try {
			//borrar todos los objetos de prestamo asociados al record
			String controlNumber = record.getField(1).getStringFieldValue();

			List<LoanObject> loanObjects = loanObjectCreation.findAllByControlNumber(controlNumber);
			int loanObjectCount = loanObjects.size();
			
			boolean check = this.check(loanObjects, loanObjectCreation);
			
			if(check){
				for (int i = 0; i < loanObjectCount; i++) {
					loanObjectCreation.removedLoanObject(loanObjects.get(i).getLoanObjectID());
				}

				//borrar el record
				dataBaseManager.deleteRecord(record, databaseName);
				
				response = true;

				RetroalimentationUtils.showInformationMessage(
						AbosMessages.get().MSG_INF_DELETE_ONE_ITEM);
			}
			else{
				RetroalimentationUtils.showErrorMessage(
						"El elemento que desea eliminar se está usando.");
			}

		} catch (JisisDatabaseException e) {
			RetroalimentationUtils.showErrorShellMessage(
					"Problema de conección con JISIS");
		} catch (DbException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<Record> findExemplaryRecords(String term, String databaseName, IDataBaseManager dataBaseManager)
			throws JisisDatabaseException {

		List<Record> records = dataBaseManager.find(term, databaseName);

		return records;
	}

	@Override
	public List<Record> findByOptionsExemplaryRecords(Map<String, String> fieldsWithValues, String databaseName,
			IDataBaseManager dataBaseManager, String isisDefHome) throws JisisDatabaseException {
		
		boolean first = true;

		List<Option> options = new LinkedList<Option>();

		for(Map.Entry<String, String> entry : fieldsWithValues.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			
			if(first){
				Option option = new Option(key, value);
				options.add(option);
				first = false;
			}
			else{
				Option option = new OptionAND(key, value);
				options.add(option);
			}
		}

		return dataBaseManager.getService().findByOptions(options, databaseName, isisDefHome);
	}
	
	private boolean check(List<LoanObject> loanObjects, ILoanObjectCreation loanObjectCreation){
		
		boolean response = true;
		
		int count = loanObjects.size();
		
		for (int i = 0; i < count; i++) {
			LoanObject current = loanObjects.get(i);
			
			Nomenclator state = current.getLoanObjectState();
			
			if(state.getNomenclatorID() == CataloguingNomenclator.LOANOBJECT_STATE_BORROWED){
				response = false;
				break;
			}
			
			long id = current.getLoanObjectID();
			
			List<Transaction> transactions = loanObjectCreation.findTranssactionByLoanObject(id);
			List<Penalty> penalties = loanObjectCreation.findPenaltyByLoanObject(id);
			
			List<Reservation> reservations = loanObjectCreation.findAllReservations();
			boolean canErase = true;
			
			int reservationsSize = reservations.size();
			for (int j = 0; j < reservationsSize; j++) {
				Reservation currentReservation = reservations.get(j);
				
				List<LoanObject> loanOjts = currentReservation.getReservationList();
				int loanObjectsSize = loanOjts.size();
				for (int k = 0; k < loanObjectsSize; k++) {
					LoanObject currentLoanObject = loanOjts.get(k);
					if(currentLoanObject.getLoanObjectID() == id){
						canErase = false;
						break;
					}
				}
				if(canErase == false)
					break;
			}
			
			if(transactions.size() > 0 || penalties.size() > 0 && canErase){
				response = false;
				break;
			}
		}
		return response;
	}
	
	@Override
	public Boolean canEditAndRemove(List<LoanObject> loanObjects) {
	    boolean response = true;
		
		int count = loanObjects.size();
		
		for (int i = 0; i < count; i++) {
			LoanObject current = loanObjects.get(i);
			Nomenclator state = current.getLoanObjectState();
			
			if(state.getNomenclatorID() == CataloguingNomenclator.LOANOBJECT_STATE_BORROWED){
				response = false;
				break;
			}
		}
		return response;
	}
}
