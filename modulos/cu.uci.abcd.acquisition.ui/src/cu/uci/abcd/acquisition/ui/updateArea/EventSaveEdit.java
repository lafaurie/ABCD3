package cu.uci.abcd.acquisition.ui.updateArea;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.Record;
import org.unesco.jisis.corelib.record.StringOccurrence;
import org.unesco.jisis.corelib.record.Subfield;

import cu.uci.abcd.acquisition.IRegistrationManageAcquisitionService;
import cu.uci.abcd.cataloguing.util.Constant;
import cu.uci.abcd.cataloguing.util.JisisRegistration;
import cu.uci.abcd.cataloguing.util.QuickSort;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;

public class EventSaveEdit implements TreeColumnListener, Listener{
	private ArrayList<FieldStructure> children;
	private IRegistrationManageAcquisitionService dataBaseManager;
	private Composite parent;
	private Record record;
	private ViewController controller;
	private String dataBaseName;
	public static final String DataBaseName = "Registro_De_Adquisicion";

	public EventSaveEdit(ViewController controller,ArrayList<FieldStructure> children, 
			String dataBaseName, Record record){
		this.children = children;
		this.record = record;
		this.dataBaseName = DataBaseName;
		this.controller = controller;
	}

	@Override
	public void handleEvent(TreeColumnEvent arg0) {
		Boolean successfull = false;
		QuickSort sort = new QuickSort();
		sort.quickSort(0, children.size()-1, children);

		JisisRegistration jisis = new JisisRegistration();

		record.removeEmptyFields();
		record.clear();

		boolean save = jisis.save(children, record);

		try {
			if(save){

				dataBaseManager.editRecordAcquisition(record, dataBaseName, "DEF_HOME");
				
				String controlNumber = record.getField(1).getStringFieldValue();

				String title = "";
				String author = "";
				String editionNumber = "";

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
					int count2 = subFieldsRecord.length;
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
					int count3 = subFieldsRecord.length;
					for (int i = 0; i < count3; i++) {
						if(Constant.EDITION_SUB_FIELD_CODE == subFieldsRecord[i].getSubfieldCode()){
							editionNumber = subFieldsRecord3[i].getData();
							break;
						}
					}
				}	


				successfull = true;
				RetroalimentationUtils.showInformationMessage(
						"Se ha modificado un elemento");
			}
			else{
				successfull = false;
				RetroalimentationUtils.showErrorShellMessage(
						"Se debe guardar al menos un campo");
			}

		} catch (DbException e) {
			e.printStackTrace();
		}

	}




	@Override
	public void handleEvent(Event arg0) {
		Boolean successfull = false;
		QuickSort sort = new QuickSort();
		sort.quickSort(0, children.size()-1, children);

		JisisRegistration jisis = new JisisRegistration();

		record.removeEmptyFields();
		record.clear();

		boolean save = jisis.save(children, record);

		try {
			if(save){

				dataBaseManager.editRecordAcquisition(record, dataBaseName, "DEF_HOME");
				
				String controlNumber = record.getField(1).getStringFieldValue();

				String title = "";
				String author = "";
				String editionNumber = "";

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
					int count2 = subFieldsRecord.length;
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
					int count3 = subFieldsRecord.length;
					for (int i = 0; i < count3; i++) {
						if(Constant.EDITION_SUB_FIELD_CODE == subFieldsRecord[i].getSubfieldCode()){
							editionNumber = subFieldsRecord3[i].getData();
							break;
						}
					}
				}	


				successfull = true;
				RetroalimentationUtils.showInformationMessage(
						"Se ha modificado un elemento");
				
			}
			else{
				successfull = false;
				RetroalimentationUtils.showErrorShellMessage(
						"Se debe guardar al menos un campo");
			}

		} catch (DbException e) {
			e.printStackTrace();
		}

		
	}}


