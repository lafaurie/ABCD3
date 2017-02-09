package cu.uci.abos.widget.template.listener;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.common.WorksheetDef.WorksheetField;
import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.template.view.Field8View;
import cu.uci.abos.widget.repeatable.field.ControlType;
import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;
import cu.uci.abos.widget.repeatable.field.domain.SubFieldDomain;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.SubFieldDescription;
import cu.uci.abos.widget.template.util.BibliographicConstant;
import cu.uci.abos.widget.template.util.Util;

public class EventStructureField8 implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private String databaseName;
	private IJisisDataProvider service;
	private WorksheetDef workSheet;
	private FieldDomain fieldDomain;
	private ArrayList<FieldDomain> fieldsDomain;
	private CTabFolder tabFolder;
	private int width;
	private int height;
	private Composite father;
	private Record lastRecord;
	private ArrayList<FieldStructure> notNull;

	public EventStructureField8(String dataBaseName, IJisisDataProvider service, CTabFolder tabFolder,
			int width, int height, Composite father,
			ArrayList<FieldStructure> notNull) {
		this.service = service;
		this.databaseName = dataBaseName;
		this.tabFolder = tabFolder;
		this.width = width;
		this.height = height;
		this.father = father;
		this.notNull = notNull;
	}

	@Override
	public void handleEvent(Event arg0){

		if(!isOpen()){

			try {

				lastRecord = service.getLastRecord(databaseName, Util.getDefHome());

				this.fieldsDomain = new ArrayList<FieldDomain>();
				fieldsDomain.removeAll(fieldsDomain);

				workSheet = service.getWorksheet(BibliographicConstant.DEFAULT_WORKSHEET, this.databaseName, Util.getDefHome());

				int fieldCount = workSheet.getFieldsCount();

				if(lastRecord == null){
					for (int i = 0; i < fieldCount; i++) {
						WorksheetField field = workSheet.getFieldByIndex(i);
						createFieldDomain(field, null);
						this.fieldsDomain.add(fieldDomain);
					}
				}
				else{
					for (int i = 0; i < fieldCount; i++) {						
						WorksheetField field = workSheet.getFieldByIndex(i);

						Field recordField = (Field) lastRecord.getField(field.getTag());

						if(!recordField.isEmpty()){
							createFieldDomain(field, recordField.getStringFieldValue());
							this.fieldsDomain.add(fieldDomain);	
						}
						else{
							createFieldDomain(field, null);
							this.fieldsDomain.add(fieldDomain);	
						}
					}
				}

				CTabItem tabItem = new CTabItem(tabFolder, 0);
				tabItem.setText(databaseName);

				Composite page = new Composite(tabFolder, SWT.BORDER);
				page.setLayout(new FormLayout());
				FormDatas.attach(page).atTop(0).atLeft(0).withWidth(width).withHeight(height);

				//call the view using the component
				Field8View view = new Field8View();
				view.setFieldsDomain(fieldsDomain);
				view.setHeight(height);
				view.setWidth(width);
				view.setTabItem(tabItem);
				view.setTabItemSelection(tabFolder.getSelection());
				view.setTabFolder(tabFolder);
				view.setService(service);
				view.setDataBaseName(databaseName);

				FieldStructure field8 = null;
				int count = notNull.size();
				for (int i = 0; i < count; i++) {
					FieldStructure fieldI = notNull.get(i);
					if(fieldI.getTag() == BibliographicConstant.FIELD_8){
						field8 = fieldI;
						break;
					}
				}	  
				view.setField8Text((Text)field8.getControl());
				view.createUIControl(page);

				update(page, tabItem);

			} catch (JisisDatabaseException e1) {
				RetroalimentationUtils.showErrorShellMessage(
						"Problema de conección con JISIS");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			tabFolder.setSelection(tabFolder.getItemCount()-1);
			father.redraw();
			father.update();
			father.layout(true, true);
		}
	}

	public void createFieldDomain(WorksheetField field, String defValue)throws JisisDatabaseException{

		// getInformationField
		boolean repeatableField = field.getRepeatable();
		String defaultValue = field.getDefaultValue();
		String description = field.getDescription();
		String displayControl = field.getDisplayControl();
		boolean descriptors = field.getDescriptors();
		String helpMessage = field.getHelpMessage();
		String pickList = field.getPickList();
		int subFieldsCount = field.getSubFieldsCount();
		int type = field.getType();
		String validationFormat = field.getValidationFormat();
		int tag = field.getTag();
		SubFieldDescription subFieldDescription = null;
		ArrayList<SubFieldDomain> subfields = new ArrayList<SubFieldDomain>();

		if(defValue != null)
			defaultValue = defValue;

		this.fieldDomain = new FieldDomain(repeatableField, tag, defaultValue, description, displayControl, descriptors,
				helpMessage, pickList, subFieldsCount, type, validationFormat, subfields, subFieldDescription);

		this.fieldDomain.setSubfieldDescription(generateSubFieldDescription(fieldDomain.getPickList(), fieldDomain
				.getDisplayControl()));
	}

	private SubFieldDescription generateSubFieldDescription(String pickListField , String displayControl) {

		int type = 1;
		String[] picklist;
		ControlType control = null;
		SubFieldDescription subFieldDescription = null;
        
		if(!displayControl.equals("")){
			if(displayControl.equals("Text")){
				control = ControlType.Text;
				subFieldDescription = new SubFieldDescription(type, control);
			}
			else if(displayControl.equals("Combo")){
				if (!pickListField.equals("")) {
					picklist = pickListField.split(";");
					control = ControlType.Combo;
					subFieldDescription = new SubFieldDescription(type, control, picklist);
				}
				else{
					control = ControlType.Text;
					subFieldDescription = new SubFieldDescription(type, control);
				}
			}
			else if(displayControl.equals("DateTime")){
				control = ControlType.DateTime;
				subFieldDescription = new SubFieldDescription(type, control);
			}
			else if(displayControl.equals("Date")){
				control = ControlType.Date;
				subFieldDescription = new SubFieldDescription(type, control);
			}
			else if(displayControl.equals("Time")){
				control = ControlType.Time;
				subFieldDescription = new SubFieldDescription(type, control);
			}
			else if(displayControl.equals("TextArea")){
				control = ControlType.TextArea;
				subFieldDescription = new SubFieldDescription(type, control);
			}
			else if(displayControl.equals("NW")){
				control = ControlType.NW;
				subFieldDescription = new SubFieldDescription(type, control);
			}
			else{
				control = ControlType.Text;
				subFieldDescription = new SubFieldDescription(type, control);
			}
		}
		else{
			if (!pickListField.equals("")) {
				String character = pickListField.substring(0, 1);
				if(!character.equals("@")){
					picklist = pickListField.split(";");
					control = ControlType.Combo;
					subFieldDescription = new SubFieldDescription(type, control, picklist);
				}
				else{
					control = ControlType.Text;
					subFieldDescription = new SubFieldDescription(type, control);
				}
			} else {
				control = ControlType.Text;
				subFieldDescription = new SubFieldDescription(type, control);

			}
		}
		return subFieldDescription;
	}

	private boolean isOpen(){
		boolean response = false;
		CTabItem tabItem = tabFolder.getItem(tabFolder.getItemCount()-1);

		if(tabItem.getText().equals(databaseName))
			response = true;

		return response;
	}

	private void update(Composite page, CTabItem tabItem){
		page.pack();
		page.redraw();
		page.update();
		page.layout(true, true);

		tabItem.setControl(page);
		tabFolder.setSelection(tabItem);

		father.redraw();
		father.update();
		father.layout(true, true);
	}

}
