package cu.uci.abos.widget.template.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.common.WorksheetDef.WorksheetField;
import org.unesco.jisis.corelib.common.WorksheetDef.WorksheetSubField;
import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.Record;
import org.unesco.jisis.corelib.record.StringOccurrence;
import org.unesco.jisis.corelib.record.Subfield;

import cu.uci.abos.widget.repeatable.field.ControlType;
import cu.uci.abos.widget.repeatable.field.util.Constants;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.SubFieldDescription;
import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;
import cu.uci.abos.widget.repeatable.field.domain.SubFieldDomain;
import cu.uci.abos.widget.template.listener.EventBuildDynamicView2;
import cu.uci.abos.widget.template.listener.EventCalculateValueField8;
import cu.uci.abos.widget.template.listener.EventChangeWorkSheet;
import cu.uci.abos.widget.template.util.BibliographicConstant;
import cu.uci.abos.widget.template.util.FieldView;
import cu.uci.abos.widget.template.util.QuickSort;
import cu.uci.abos.widget.template.util.RecordValidator;
import cu.uci.abos.widget.template.util.TabItemRange;
import cu.uci.abos.widget.template.util.Util;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.SecurityUtils;

public class TemplateCompound extends Composite {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private ArrayList<FieldStructure> children;
	private Button save;
	private Label dataBaseRecord;
	private CTabFolder tabFolder;
	private Composite father;
	private Composite header;
	private Composite boddy;
	private Composite view;
	private Label changeWorksSheet;
	private Combo changeCombo;
	private ArrayList<String> texts;
	private ArrayList<Integer> types;
	private ArrayList<ControlType> controls;
	private ArrayList<String> subFieldCode;
	private ArrayList<String[]> comboList;
	private String dataBaseName;
	private String workSheetName;
	private Record record;
	private int height;
	private int width;
	private IJisisDataProvider service;
	private ArrayList<FieldStructure> notNull; 
	private ArrayList<Integer> mandatoryTabItems;
	private ArrayList<Integer> mandatoryExpandItems;
	private boolean register;
	private ToolBar back;
	private ToolItem backItem;

	//builder component dataBase
	public TemplateCompound(Composite parent, int style, String dataBaseName, IJisisDataProvider service) throws JisisDatabaseException {
		super(parent, style);
		this.children = new ArrayList<FieldStructure>();
		this.notNull = new ArrayList<FieldStructure>();
		this.mandatoryTabItems = new ArrayList<Integer>();
		this.mandatoryExpandItems = new ArrayList<Integer>();
		this.width = parent.getShell().getBounds().width - 300;
		this.height = parent.getShell().getBounds().height - 160;
		this.view = parent;
		this.service = service;
		this.dataBaseName = dataBaseName;
		this.register = true;
	}

	//builder component dataBase and record(used for editing)
	public TemplateCompound(Composite parent, int style, Record record, String dataBaseName,
			IJisisDataProvider service)throws JisisDatabaseException{		 
		super(parent, style);
		this.children = new ArrayList<FieldStructure>();
		this.notNull = new ArrayList<FieldStructure>();
		this.mandatoryTabItems = new ArrayList<Integer>();
		this.mandatoryExpandItems = new ArrayList<Integer>();
		this.view = parent;
		this.width = parent.getShell().getBounds().width - 300;
		this.height = parent.getShell().getBounds().height - 160;
		this.dataBaseName = dataBaseName;
		this.record = record;
		this.service = service;
		this.register = false;
	}

	//createComponent
	public void createComponent() throws JisisDatabaseException{
		String workSheet;
		if(workSheetName == null)
			workSheet = BibliographicConstant.DEFAULT_WORKSHEET;
		else
			workSheet = workSheetName;
		createRecord(dataBaseName, workSheet);
	}

	//createEditComponent
	public void createEditComponent() throws JisisDatabaseException{
		String registerType = null;
		String workSheetName = null;

		try {
			if(dataBaseName.equals(BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE))
				registerType = record.getField(BibliographicConstant.RECORD_TYPE).getStringFieldValue();

			initialize(dataBaseName, "Editar registro en ");

			if(this.workSheetName == null){
				if(registerType != null && !registerType.equals(""))
					workSheetName = registerType;
				else
					workSheetName = BibliographicConstant.DEFAULT_WORKSHEET;
			}
			else
				workSheetName = this.workSheetName;

			changeCombo.add(workSheetName);
			changeCombo.select(0);

			WorksheetDef workSheet = service.getWorksheet(workSheetName, this.dataBaseName, Util.getDefHome());

			ArrayList<Integer> tabItemsName = new ArrayList<Integer>();
			ArrayList<ArrayList<FieldDomain>> components = new ArrayList<ArrayList<FieldDomain>>();

			getInformationFromJisisAndRecord(tabItemsName, components, workSheet);

			commonSteaps(tabItemsName, components);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//for builders to create record
	private void createRecord(String dataBaseName, String workSheetName)throws JisisDatabaseException{
		this.dataBaseName = dataBaseName;
		initialize(dataBaseName, "Agregar registro en ");
		List<String> list = getWorkSheetList(dataBaseName);
		workSheetChange(list, workSheetName, changeCombo);
		WorksheetDef workSheet = service.getWorksheet(workSheetName, dataBaseName, Util.getDefHome());

		ArrayList<Integer> tabItemsName = new ArrayList<Integer>();
		ArrayList<ArrayList<FieldDomain>> components = new ArrayList<ArrayList<FieldDomain>>();

		getInformationFromJisis(tabItemsName, components, workSheet);
		commonSteaps(tabItemsName, components);

		this.pack();
		this.redraw();
		this.update();
		this.layout(true, true);

		//injection event
		changeCombo.addListener(SWT.Selection, new EventChangeWorkSheet(changeCombo, this, view, father,
				boddy, header, dataBaseName, service));			
	}

	//common steaps
	private void commonSteaps(ArrayList<Integer> tabItemsName, ArrayList<ArrayList<FieldDomain>> components)
			throws JisisDatabaseException{

		Integer[] arrayTabItems = tabItemsName.toArray(new Integer[tabItemsName.size()]);
		QuickSort.sort(arrayTabItems, 0, arrayTabItems.length - 1, components);

		createView(arrayTabItems, components, tabFolder);
		tabFolder.setSelection(0);

		//updates
		father.layout();
		father.pack();

		this.layout();
		this.pack();

		view.layout();
		view.pack();

		view.getShell().layout(true);

		if(dataBaseName.equals(BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE)){
			FieldStructure field8 = null;
			int count = notNull.size();
			for (int i = 0; i < count; i++) {
				FieldStructure fieldI = notNull.get(i);
				if(fieldI.getTag() == BibliographicConstant.FIELD_8){
					field8 = fieldI;
					break;
				}
			}
			field8.getControl().addListener(SWT.FocusIn, new EventCalculateValueField8((Text)field8.getControl(),service,
					dataReferencedName));
		}
	}		

	//used to change the workSheet
	public void createBoddy(Composite boddy, Composite parent, String dataBaseName, String workSheetName,
			IJisisDataProvider service, Combo combo) throws JisisDatabaseException {

		//reset children and notNUll(for validateRecord)
		resetStructures();

		this.mandatoryTabItems = new ArrayList<Integer>();
		this.mandatoryExpandItems = new ArrayList<Integer>();

		this.service = service;

		CTabFolder newTabFolder = new CTabFolder(boddy, 0);
		newTabFolder.setLayout(new FormLayout());
		FormDatas.attach(newTabFolder).atTop(0).atLeft(0).atRight(0).atBottom(0);
		this.setTabFolder(newTabFolder);

		List<String> list = getWorkSheetList(dataBaseName);

		Combo newChangeCombo = new Combo(header, SWT.READ_ONLY);
		FormDatas.attach(newChangeCombo).atTop(0).withWidth(250).atRight(0);
		combo.dispose();
		this.setChangeCombo(newChangeCombo);

		FormDatas.attach(changeWorksSheet).atTop(2).atRightTo(newChangeCombo, 5).withWidth(200);
		FormDatas.attach(changeWorksSheet).atTop(2).atRightTo(changeCombo, 5).withWidth(120);

		workSheetChange(list, workSheetName, newChangeCombo);
		WorksheetDef workSheet = service.getWorksheet(workSheetName, dataBaseName, Util.getDefHome());

		ArrayList<Integer> tabItemsName = new ArrayList<Integer>();
		ArrayList<ArrayList<FieldDomain>> components = new ArrayList<ArrayList<FieldDomain>>();

		getInformationFromJisis(tabItemsName, components, workSheet);

		commonSteaps(tabItemsName, components);

		//injection event
		newChangeCombo.addListener(SWT.Selection, new EventChangeWorkSheet(newChangeCombo, this, parent, father, boddy, header,
				dataBaseName, service));
	}

	private void initialize(String dataBaseName, String label) {

		this.setData(RWT.CUSTOM_VARIANT, "gray_background");
		this.setLayout(new FormLayout());

		father = new Composite(this, 0);
		father.setData(RWT.CUSTOM_VARIANT, "gray_background");
		father.setLayout(new FormLayout());
		FormDatas.attach(father).atLeft(5).atRight(5).atTop(5).atBottom(5).withHeight(height).withWidth(width);

		header = new Composite(father, 0);
		header.setData(RWT.CUSTOM_VARIANT, "gray_background");
		header.setLayout(new FormLayout());
		FormDatas.attach(header).atRight(0).atLeft(0).atTop(0);

		boddy = new Composite(father, 0);
		boddy.setData(RWT.CUSTOM_VARIANT, "gray_background");
		boddy.setLayout(new FormLayout());
		FormDatas.attach(boddy).atTopTo(header, 5).atBottom(0).atRight(0).atLeft(0);

		back = new ToolBar(header, SWT.CENTER|SWT.WRAP|SWT.FLAT);
		backItem = new ToolItem(back, 0);
		Image image = new Image(father.getDisplay(), RWT.getResourceManager().getRegisteredContent("left-arrow"));
		backItem.setImage(image);
		backItem.setToolTipText("Vista anterior");

		save = new Button(header, SWT.PUSH);
		save.setText("Guardar");

		dataBaseRecord = new Label(header, SWT.WRAP|SWT.LEFT);
		dataBaseRecord.setText(label + dataBaseName);

		changeWorksSheet = new Label(header, SWT.WRAP|SWT.RIGHT);
		changeWorksSheet.setText("Cambiar hoja de trabajo:");

		changeCombo = new Combo(header, SWT.READ_ONLY);

		tabFolder = new CTabFolder(boddy, 0);

		FormDatas.attach(back).atTop(0).atLeft(0);
		FormDatas.attach(save).atTop(0).atLeftTo(back, 5);
		FormDatas.attach(dataBaseRecord).atLeftTo(save, 10).atTop(2).withWidth(120);
		FormDatas.attach(changeCombo).atTop(0).withWidth(250).atRight(0);
		FormDatas.attach(changeWorksSheet).atTop(2).atRightTo(changeCombo, 5).withWidth(120);
		FormDatas.attach(tabFolder).atTop(0).atRight(0).atBottom(0).atLeft(0);
	}

	private List<String> getWorkSheetList(String dataBaseName) {
		List<String> list = null;
		// Read of JIsis
		if (service != null) {
			try {
				list = service.getWorksheetNames(dataBaseName, Util.getDefHome());
			} catch (JisisDatabaseException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private void workSheetChange(List<String> pickList, String selected, Combo combo) {
		int count = pickList.size();
		int position = -1;
		int defaultPosition = -1;

		for (int i = 0; i < count; i++) {
			combo.add(pickList.get(i), i);
			if (pickList.get(i).equals(selected))
				position = i;

			if(pickList.get(i).equals(BibliographicConstant.DEFAULT_WORKSHEET))
				defaultPosition = i;
		}
		if(position > -1)
			combo.select(position);
		else if(defaultPosition > -1)
			combo.select(defaultPosition);
		else
			combo.select(0);
	}

	//covers all fields of the workSheet
	private void getInformationFromJisis(ArrayList<Integer> tabItemsName, ArrayList<ArrayList<FieldDomain>> components, WorksheetDef workSheet) throws JisisDatabaseException{
		int fieldCount = workSheet.getFieldsCount();

		for (int i = 0; i < fieldCount; i++) {
			WorksheetField field = workSheet.getFieldByIndex(i);
			buildingStructure(field, tabItemsName, components);									
		}
	}

	//builds a structure from field data
	private void buildingStructure(WorksheetField field, ArrayList<Integer> tabItemsName, ArrayList<ArrayList<FieldDomain>> components) throws JisisDatabaseException{

		FieldDomain fieldDomain = createFieldDomain(field);		

		if (fieldDomain.getSubFieldCount() > 0)
			fieldHasSubFields(field, fieldDomain);
		else
			fieldDomain.setSubfieldDescription(generateSubFieldDescription(fieldDomain.getPickList(), fieldDomain
					.getDisplayControl()));

		completeStructures(tabItemsName, components, fieldDomain);			
	}

	//it applies only to the fields with subfields
	private void fieldHasSubFields(WorksheetField field, FieldDomain fieldDomain){

		int subFieldsCount = field.getSubFieldsCount();
		texts = new ArrayList<String>();
		types = new ArrayList<Integer>();
		controls = new ArrayList<ControlType>();
		subFieldCode = new ArrayList<String>();
		comboList = new ArrayList<String[]>();
		ArrayList<SubFieldDomain> subfields = fieldDomain.getSubFields();

		for (int i = 0; i < subFieldsCount; i++) {
			WorksheetSubField subField = field.getSubFieldByIndex(i);
			// getInformationSubField
			createSubFieldDomain(subField, subfields, fieldDomain);

			controlProperties(subField.getDescription(), subField.getRepeatable(), subField.getSubfieldCode(),
					subField.getPickList(), subField.getDisplayControl());
		}			
		SubFieldDescription subFieldDescription = new SubFieldDescription(texts, types, controls, subFieldCode, comboList);
		fieldDomain.setSubfieldDescription(subFieldDescription);
	}

	//it applies only to the fields with subfields and a record
	private void fieldHasSubfieldsWithRecord(WorksheetField field, Field recordField,
			FieldDomain fielDomain, int occurrenceIndex){

		ArrayList<SubFieldDomain> subfields = fielDomain.getSubFields();
		int subFieldCount = field.getSubFieldsCount();
		StringOccurrence occurrence = (StringOccurrence) recordField.getOccurrence(occurrenceIndex);
		Subfield[] subFieldsRecord = occurrence.getSubfields();
		int subFieldRecordCount = subFieldsRecord.length;
		texts = new ArrayList<String>();
		types = new ArrayList<Integer>();
		controls = new ArrayList<ControlType>();
		subFieldCode = new ArrayList<String>();
		comboList = new ArrayList<String[]>();

		for (int i = 0; i < subFieldCount; i++){
			WorksheetSubField subfield = field.getSubFieldByIndex(i);

			// getInformationSubField
			String subFieldDefaultValue = subfield.getDefaultValue();
			String subFieldDescriptions = subfield.getDescription();
			String subFieldDisplayControl = subfield.getDisplayControl();
			String subFieldHelpMessage = subfield.getHelpMessage();
			String subFieldPickList = subfield.getPickList();
			boolean repeatableSubField = subfield.getRepeatable();
			String subFieldCode = subfield.getSubfieldCode();
			int subFieldTag = subfield.getTag();
			int subFieldType = subfield.getType();
			String subFieldValidationFormat = subfield.getValidationFormat();

			ArrayList<Integer> subfieldOccurrence = new ArrayList<Integer>();
			char realCode = subFieldCode.charAt(subFieldCode.length()-1);

			for (int j = 0; j < subFieldRecordCount; j++) {
				if(realCode == subFieldsRecord[j].getSubfieldCode())
					subfieldOccurrence.add(j);
			}

			int subfieldOccurrenceCount = subfieldOccurrence.size();
			if(subfieldOccurrenceCount == 0){
				subfields.add(new SubFieldDomain(subFieldDefaultValue, subFieldDescriptions, subFieldDisplayControl,
						subFieldHelpMessage, subFieldPickList, repeatableSubField, subFieldCode,
						subFieldTag, subFieldType, subFieldValidationFormat));

				controlProperties(subFieldDescriptions, repeatableSubField, subFieldCode, subFieldPickList, subFieldDisplayControl);
			}
			else{
				for (int j = 0; j < subfieldOccurrenceCount; j++) {
					int position = subfieldOccurrence.get(j);
					subFieldDefaultValue = subFieldsRecord[position].getData();

					subfields.add(new SubFieldDomain(subFieldDefaultValue, subFieldDescriptions, subFieldDisplayControl,
							subFieldHelpMessage, subFieldPickList, repeatableSubField, subFieldCode,
							subFieldTag, subFieldType, subFieldValidationFormat));

					controlProperties(subFieldDescriptions, repeatableSubField, subFieldCode, subFieldPickList, subFieldDisplayControl);
				}
			}
		}
		SubFieldDescription subFieldDescription = new SubFieldDescription(texts, types, controls, subFieldCode, comboList);
		fielDomain.setSubfieldDescription(subFieldDescription);
	}

	//tabItemsName and components are filled
	private void completeStructures(ArrayList<Integer> tabItemsName, ArrayList<ArrayList<FieldDomain>> components,
			FieldDomain fieldDomain){

		int tabNumber = fieldDomain.getTabItemName();

		if (!tabItemsName.contains(tabNumber)){
			tabItemsName.add(tabNumber);
			ArrayList<FieldDomain> array = new ArrayList<FieldDomain>();
			array.add(fieldDomain);			
			components.add(array);
		} else {
			int positionArray = -1;
			boolean found = false;
			int componentCount = components.size();

			for (int i = 0; i < componentCount && found == false; i++) {
				int tabItemName = 0;
				ArrayList<FieldDomain> ar = components.get(i);
				FieldDomain fd = ar.get(0);
				tabItemName = fd.getTabItemName();

				if (tabItemName == tabNumber) {
					found = true;
					positionArray = i;
				}
			}
			components.get(positionArray).add(fieldDomain);
		}
	}		

	private void getInformationFromJisisAndRecord(ArrayList<Integer> tabItemsName, ArrayList<ArrayList<FieldDomain>> components,
			WorksheetDef workSheet){

		int fieldCount = workSheet.getFieldsCount();
		for (int i = 0; i < fieldCount; i++) {
			WorksheetField field = workSheet.getFieldByIndex(i);

			try {
				Field recordField = (Field) record.getField(field.getTag());
				
				FieldDomain fieldDomain = createFieldDomain(field);

				//if the field is on the record
				if(!recordField.isEmpty()){
					int occurrenceCount = recordField.getOccurrenceCount();

					if(!mandatoryTabItems.contains(recordField.getTag()))
						mandatoryExpandItems.add(recordField.getTag());

					TabItemRange range = new TabItemRange(recordField.getTag());

					int tabItemNumber = range.calculateTabItem();

					if(!mandatoryTabItems.contains(tabItemNumber));
					mandatoryTabItems.add(tabItemNumber);

					//if the field has occurrence
					if(occurrenceCount > 1){

						//if the field has subfields
						if(fieldDomain.getSubFieldCount() > 0){
							for (int j = 0; j < occurrenceCount; j++){

								fieldDomain = createFieldDomain(field);
								fieldDomain.setSubtag(j);	

								fieldHasSubfieldsWithRecord(field, recordField, fieldDomain, j);
								completeStructures(tabItemsName, components, fieldDomain);
							}   
						}						  
						//if the field has no subfields
						else{														
							for (int j = 0; j < occurrenceCount; j++){
								StringOccurrence occurrence = (StringOccurrence) recordField.getOccurrence(j);
								fieldDomain = createFieldDomain(field);
								fieldDomain.setDefaultValue(occurrence.getValue());
								fieldDomain.setSubtag(j);
								fieldDomain.setSubfieldDescription(generateSubFieldDescription(fieldDomain.getPickList(),
										fieldDomain.getDisplayControl()));
								completeStructures(tabItemsName, components, fieldDomain);
							}
						}
					}
					//if the field has no occurrence
					else{
						int subFieldCount = field.getSubFieldsCount();

						//if the field has subfields
						if(subFieldCount > 0){	
							fieldHasSubfieldsWithRecord(field, recordField, fieldDomain, 0);					
							completeStructures(tabItemsName, components, fieldDomain);
						}						
						//if the field has no subfields
						else{
							fieldDomain.setSubfieldDescription(generateSubFieldDescription(fieldDomain.getPickList(),
									fieldDomain.getDisplayControl()));
							fieldDomain.setDefaultValue(recordField.getStringFieldValue());
							completeStructures(tabItemsName, components, fieldDomain);
						}
					}		
				}
				//if the field is not on the record
				else
					buildingStructure(field, tabItemsName, components);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void createView(Integer[] arrayTabItems, ArrayList<ArrayList<FieldDomain>> components,
			CTabFolder tabFolder) throws JisisDatabaseException
			{
		FieldView fieldView = new FieldView(tabFolder, children, notNull, mandatoryExpandItems,
				father, register);

		for (int i = 0; i < arrayTabItems.length; i++) {

			if(i==0)
				this.mandatoryTabItems.add(0, arrayTabItems[i]);

			CTabItem tabItem = new CTabItem(tabFolder, 0);
			tabItem.setText(String.valueOf(arrayTabItems[i]));

			ArrayList<FieldDomain> fieldsList = components.get(i);
			String tabItemText = tabItem.getText();

			//the default tags
			if (mandatoryTabItems.contains(Integer.valueOf(tabItemText))) {

				Composite page = new Composite(tabFolder, SWT.BORDER);
				page.setLayout(new FormLayout());
				FormDatas.attach(page).atTop(0).atLeft(0);

				ExpandBar expandBar = new ExpandBar(page, SWT.V_SCROLL|SWT.H_SCROLL|SWT.BORDER);
				FormDatas.attach(expandBar).atLeft(0).atRight(0).atTop(0).atBottom(0);

				fieldView.setTabItem(tabItem);
				fieldView.setExpandBar(expandBar);
				fieldView.setWidth(width);
				fieldView.setHeight(height);

				int fieldsListSize = fieldsList.size();
				for (int j = 0; j < fieldsListSize; j++) {

					if(!register)
						fieldView.setNoPlusItem(false);

					String expandItemText = fieldsList.get(j).getExpandItemText();
					ExpandItem expandItem = new ExpandItem(expandBar, SWT.PUSH);
					expandItem.setText(expandItemText);

					if(!register){
						if(fieldsList.get(j).isRepeatableField()){
							if(j+1 < fieldsListSize){
								if(fieldsList.get(j+1).getTag() == fieldsList.get(j).getTag())
									fieldView.setNoPlusItem(true);
							}
						}							
					}

					fieldView.setFieldDomain(fieldsList.get(j));
					fieldView.setExpandItem(expandItem);

					fieldView.buildingView(fieldsList.get(j).getSubfieldDescription(), expandItemText, this, view,
							service);
				}

				page.pack();
				page.redraw();
				page.update();
				page.layout(true, true);

				tabItem.setControl(page);

				tabFolder.layout();
				tabFolder.pack();

			} else {
				tabItem.setData("fieldsList", fieldsList);

				tabFolder.addSelectionListener(new EventBuildDynamicView2(tabFolder,
						this, view, father, children, notNull, mandatoryExpandItems, service, register));
			}
		}
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
			else if(displayControl.equals("FileUpLoad")){
				control = ControlType.FileUpLoad;
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

	private String getControlNumber() throws JisisDatabaseException{
		String controlNumber = "";
		String exactTime;

		Calendar calendar = Calendar.getInstance();
		String time = String.valueOf(calendar.getTimeInMillis());

		exactTime = time.substring(time.length()-5, time.length());

		Record lastRecord = service.getLastRecord(dataBaseName, Util.getDefHome());

		if (lastRecord != null){
			if(dataBaseName.equals(BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE))
				controlNumber = "C"+String.valueOf(lastRecord.getMfn() + 1);
			else
				controlNumber = "A"+ String.valueOf(lastRecord.getMfn() + 1);
		}		
		else{
			if(dataBaseName.equals(BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE))
				controlNumber = "C"+String.valueOf(1);
			else
				controlNumber = "A"+ String.valueOf(1);
		}
		return controlNumber+exactTime;	 
	}
	
	private String getLastTranssaction(){
		String lastTranssaction = "005 ";
		
		Date currentDate = new Date();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat hourFormat = new SimpleDateFormat("HHmmss");
		
		lastTranssaction = lastTranssaction + dateFormat.format(currentDate) + hourFormat.format(currentDate);
		
		return lastTranssaction;
	}

	private void controlProperties(String subFieldDescriptions, boolean repeatableSubField, String subFieldCode,
			String subFieldPickList, String displayControl){
		
		subFieldPickList.replaceAll("/n","").trim();
		texts.add(subFieldDescriptions);

		if (repeatableSubField)
			types.add(3);
		else
			types.add(1);

		this.subFieldCode.add(subFieldCode);

		if(!displayControl.equals("")){
			if(displayControl.equals("Text")){
				controls.add(ControlType.Text);
				comboList.add(null);
			}
			else if(displayControl.equals("Combo")){
				if (subFieldPickList.equals("")) {
					controls.add(ControlType.Text);
					comboList.add(null);
				} else {
					controls.add(ControlType.Combo);
					String[] valueList = subFieldPickList.split(";");
					comboList.add(valueList);
				}
			}
			else if(displayControl.equals("DateTime")){
				controls.add(ControlType.DateTime);
				comboList.add(null);
			}
			else if(displayControl.equals("Date")){
				controls.add(ControlType.Date);
				comboList.add(null);
			}
			else if(displayControl.equals("Time")){
				controls.add(ControlType.Time);
				comboList.add(null);
			}
			else if(displayControl.equals("TextArea")){
				controls.add(ControlType.TextArea);
				comboList.add(null);
			}
			else if(displayControl.equals("NW")){
				controls.add(ControlType.NW);
				comboList.add(null);
			}
			else if(displayControl.equals("FileUpLoad")){
				controls.add(ControlType.FileUpLoad);
				comboList.add(null);
			}
			else{
				controls.add(ControlType.Text);
				comboList.add(null);
			}
		}
		else{
			if (subFieldPickList.equals("")) {
				controls.add(ControlType.Text);
				comboList.add(null);
			} else {
				String character = subFieldPickList.substring(0, 1);
				if(character.equals("@")){
					controls.add(ControlType.Text);
					comboList.add(null);
				}
				else{
					controls.add(ControlType.Combo);
					String[] valueList = subFieldPickList.split(";");
					comboList.add(valueList);
				}
			}
		}
	}

	private FieldDomain createFieldDomain(WorksheetField field) throws JisisDatabaseException{
		FieldDomain response = null;

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
		TabItemRange tabItemRange = new TabItemRange(tag);
		int tabNumber = tabItemRange.calculateTabItem();
		SubFieldDescription subFieldDescription = null;
		ArrayList<SubFieldDomain> subfields = new ArrayList<SubFieldDomain>();

		if(defaultValue.equals(BibliographicConstant.GENERATE_CONTROL_NUMBER))
			defaultValue =  this.getControlNumber();
		else if(defaultValue.equals(BibliographicConstant.GENERATE_LAST_TRANSSACTION))
			defaultValue =  this.getLastTranssaction();

		response = new FieldDomain(repeatableField, tag, defaultValue, description, displayControl, descriptors,
				helpMessage, pickList, subFieldsCount, type, validationFormat, tabNumber, subfields, subFieldDescription);

		if(validationFormat.equals(Constants.MANDATORY)){
			if(!mandatoryTabItems.contains(tabNumber))
				mandatoryTabItems.add(tabNumber);
			if(!mandatoryExpandItems.contains(tag))
				mandatoryExpandItems.add(tag);
		}

		return response;
	}

	private void createSubFieldDomain(WorksheetSubField subField, ArrayList<SubFieldDomain> subfields, FieldDomain fieldDomain){
		String subFieldDefaultValue = subField.getDefaultValue();
		String subFieldDescriptions = subField.getDescription();
		String subFieldDisplayControl = subField.getDisplayControl();
		String subFieldHelpMessage = subField.getHelpMessage();
		String subFieldPickList = subField.getPickList();
		boolean repeatableSubField = subField.getRepeatable();
		String subFieldCode = subField.getSubfieldCode();
		int subFieldTag = subField.getTag();
		int subFieldType = subField.getType();
		String subFieldValidationFormat = subField.getValidationFormat();

		subfields.add(new SubFieldDomain(subFieldDefaultValue, subFieldDescriptions, subFieldDisplayControl, subFieldHelpMessage,
				subFieldPickList, repeatableSubField, subFieldCode, subFieldTag, subFieldType, subFieldValidationFormat));

		if(subFieldValidationFormat.equals(Constants.MANDATORY)){
			if(!mandatoryTabItems.contains(fieldDomain.getTabItemName()))
				mandatoryTabItems.add(fieldDomain.getTabItemName());
			if(!mandatoryExpandItems.contains(fieldDomain.getTag()))
				mandatoryExpandItems.add(fieldDomain.getTag());
		}
	}

	private void resetStructures(){
		this.children.removeAll(children);
		this.children = new ArrayList<FieldStructure>();
		this.notNull.removeAll(notNull);
		this.notNull = new ArrayList<FieldStructure>();
	}

	public Button getButtonSave() {
		return save;
	}

	public Library getUserLogLibrary(){
		return (Library) SecurityUtils.getService()
				.getPrincipal().getByKey("library");
	}

	public Label getLabel() {
		return dataBaseRecord;
	}

	public ArrayList<FieldStructure> getChildrens() {
		return children;
	}

	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	public Composite getFather() {
		return father;
	}

	public void setChangeCombo(Combo changeCombo) {
		this.changeCombo = changeCombo;
	}

	public void setComboLabel(String label){
		this.changeWorksSheet.setText(label);
	}

	public void setRecordLabel(String text){
		this.dataBaseRecord.setText(text);
	}

	public Combo getWorkSheetSelected(){
		return changeCombo;
	}

	public int getHeight(){
		return this.height;
	}

	public int getWidth(){
		return this.width;
	}

	public boolean validateRecord(){
		RecordValidator recordValidator = new RecordValidator(notNull, children);
		return recordValidator.validate();
	}

	public void setWorkSheetName(String workSheetName){
		this.workSheetName = workSheetName;
	}
	
	public ToolItem getBackItem(){
		return this.backItem;
	}

	public static String dataReferencedName = "";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
