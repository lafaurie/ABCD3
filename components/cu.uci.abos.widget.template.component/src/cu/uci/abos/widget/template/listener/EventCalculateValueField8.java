package cu.uci.abos.widget.template.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.common.WorksheetDef.WorksheetField;
import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.template.util.BibliographicConstant;
import cu.uci.abos.widget.template.util.Util;

public class EventCalculateValueField8 implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Text text;
	private IJisisDataProvider service;
	private String dataBaseName;
	private WorksheetDef workSheet;
	private Record lastRecord;

	public EventCalculateValueField8(Text text, IJisisDataProvider service, String dataBaseName){
		this.text = text;
		this.service = service;
		this.dataBaseName = dataBaseName;
	}

	@Override
	public void handleEvent(Event arg0) {
		String value = text.getText();

		if(value.equals("") || value == null){

			try {
				lastRecord = service.getLastRecord(dataBaseName, Util.getDefHome());
			} catch (JisisDatabaseException e) {
				RetroalimentationUtils.showErrorShellMessage(
						"Problema de conección con JISIS");
			}			
			if(lastRecord != null){
				text.setText(getValue());
				text.setEnabled(false);
				text.setEnabled(true);
			}
			else{
				text.setEnabled(false);
				text.setEnabled(true);

				RetroalimentationUtils.showErrorShellMessage(
						"Debe configurar la base de datos");
			}
		}
		else{
			text.setText("");	
		}

	}

	private String getValue(){
		String response = "";

		try {
			workSheet = service.getWorksheet(BibliographicConstant.DEFAULT_WORKSHEET, dataBaseName, Util.getDefHome());

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
