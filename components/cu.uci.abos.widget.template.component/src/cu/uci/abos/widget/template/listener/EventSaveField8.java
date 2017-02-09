package cu.uci.abos.widget.template.listener;

import java.util.ArrayList;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.common.Global;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.common.WorksheetDef.WorksheetField;
import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.IField;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.MyDate;
import cu.uci.abos.widget.repeatable.field.util.MyDateTime;
import cu.uci.abos.widget.repeatable.field.util.MyTime;
import cu.uci.abos.widget.template.util.BibliographicConstant;
import cu.uci.abos.widget.template.util.Util;

public class EventSaveField8 implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private ArrayList<FieldStructure> children;
	private IJisisDataProvider service;
	private String dataBaseName;
	private CTabFolder tabFolder;
	private CTabItem tabItem;
	private CTabItem tabItemSelection;
	private Text field8Text;
	private Record lastRecord;

	public EventSaveField8(ArrayList<FieldStructure> children, IJisisDataProvider service,
			String dataBaseName, CTabFolder tabFolder, CTabItem tabItem, CTabItem tabItemSelection,
			Text field8Text){
		this.children = children;
		this.service = service;
		this.dataBaseName = dataBaseName;
		this.tabFolder = tabFolder;
		this.tabItem = tabItem;
		this.tabItemSelection = tabItemSelection;
		this.field8Text = field8Text;
	}

	@Override
	public void handleEvent(Event arg0) {
		IRecord record = null;

		try {
			lastRecord = service.getLastRecord(dataBaseName, Util.getDefHome());

		} catch (JisisDatabaseException e1) {
			RetroalimentationUtils.showErrorShellMessage(
					"Problema de conección con JISIS");
		}

		if(lastRecord != null)
			record = lastRecord;
		else
			record = Record.createRecord();

		int fieldCount = children.size();

		try {
			int fieldNotEmpty = 0;

			for (int i = 0; i < fieldCount; i++) {
				FieldStructure fieldStructure = children.get(i);
				int tag = fieldStructure.getTag();

				IField field;
				String value = "";

				if(fieldStructure.getControl() instanceof Text)
					value = ((Text)fieldStructure.getControl()).getText();
				else if(fieldStructure.getControl() instanceof Combo){
					value = ((Combo)fieldStructure.getControl()).getText();
					String[] data = value.split("-");
					value = data[0];
				}
				else if(fieldStructure.getControl() instanceof MyDateTime)
					value = ((MyDateTime)fieldStructure.getControl()).getText();
				else if(fieldStructure.getControl() instanceof MyDate)
					value = ((MyDate)fieldStructure.getControl()).getDate();
				else if(fieldStructure.getControl() instanceof MyTime)
					value = ((MyTime)fieldStructure.getControl()).getText();

				if(!fieldStructure.getControl().isDisposed() && !value.equals("") && !value.equals(null) 
						&& (lastRecord == null && !value.equals("Seleccione") || lastRecord != null)){				    		
					field = new Field(tag, Global.FIELD_TYPE_ALPHANUMERIC);

					field.setFieldValue(value);

					if(lastRecord == null)
						record.addField(field);
					else{

						if(field.getStringFieldValue().equals("Seleccione"))
							record.removeField(field.getTag());
						else{
							Field fieldValidator = (Field) record.getField(field.getTag());

							if(fieldValidator != null)
								record.setField(field);
							else
								record.addField(field);
						}
					}
					fieldNotEmpty++;
				}			    						    	
			}

			if(fieldNotEmpty > 0){
				if(lastRecord == null)
					service.saveRecord(record, dataBaseName, Util.getDefHome());
				else
					service.updateRecord(record, dataBaseName, Util.getDefHome());

				this.field8Text.setText(getValue());

				tabFolder.setSelection(tabItemSelection);
				tabItem.dispose();
				RetroalimentationUtils.showInformationMessage(
						"Se ha guardado la configuración del campo");
			}
			else{
				RetroalimentationUtils.showErrorShellMessage(
						"Debe guardar al menos un campo");
			}

		}catch (Exception e){
			RetroalimentationUtils.showErrorShellMessage(
					"Problema de conección con JISIS");
			e.printStackTrace();
		}
	}

	private String getValue(){
		String response = "";

		try {
			WorksheetDef workSheet = service.getWorksheet(BibliographicConstant.DEFAULT_WORKSHEET, dataBaseName, Util.getDefHome());
			lastRecord = service.getLastRecord(dataBaseName, Util.getDefHome());

			int fieldCount = workSheet.getFieldsCount();

			for (int i = 0; i < fieldCount; i++) {
				WorksheetField field = workSheet.getFieldByIndex(i);
				Field recordField = (Field) lastRecord.getField(field.getTag());

				if(!recordField.isEmpty()){
					response += recordField.getStringFieldValue(); 
				}
				else
					response+=" ";
			}

		} catch (JisisDatabaseException e) {
			RetroalimentationUtils.showErrorShellMessage(
					"Problema de conección con JISIS");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}
