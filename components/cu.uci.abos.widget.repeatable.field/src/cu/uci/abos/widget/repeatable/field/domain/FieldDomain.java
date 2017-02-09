package cu.uci.abos.widget.repeatable.field.domain;

import java.util.ArrayList;

import cu.uci.abos.widget.repeatable.field.util.Constants;
import cu.uci.abos.widget.repeatable.field.util.SubFieldDescription;

public class FieldDomain {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private boolean repeatableField;
	private int tag;
	private int subtag;
	private String defaultValue;
	private String description;
	private String displayControl;
	private boolean descriptors;
	private String helpMessage;
	private String pickList;
	private int subFieldCount;
	private int type;
	private String validationFormat;
	private String expandItemText;
	private int tabItemName;
	private ArrayList<SubFieldDomain> subFields;
	private SubFieldDescription subFieldDescription;
	private boolean referenceDataBase = false;
	private String referencedDataBase = null;

	public boolean isObligatory(){
		boolean response = false;
		if(validationFormat.equals(Constants.MANDATORY))
			response = true;
		return response;
	}

	public boolean isRepeatableField() {
		return repeatableField;
	}

	public void setRepeatableField(boolean repeatableField) {
		this.repeatableField = repeatableField;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getSubtag() {
		return subtag;
	}

	public void setSubtag(int subtag) {
		this.subtag = subtag;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayControl() {
		return displayControl;
	}

	public void setDisplayControl(String displayControl) {
		this.displayControl = displayControl;
	}

	public boolean isDescriptors() {
		return descriptors;
	}

	public void setDescriptors(boolean descriptors) {
		this.descriptors = descriptors;
	}

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}

	public String getPickList() {
		return pickList;
	}

	public void setPickList(String pickList) {
		this.pickList = pickList;
	}

	public int getSubFieldCount() {
		return subFieldCount;
	}

	public void setSubFieldCount(int subFieldCount) {
		this.subFieldCount = subFieldCount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValidationFormat() {
		return validationFormat;
	}

	public void setValidationFormat(String validationFormat) {
		this.validationFormat = validationFormat;
	}

	public String getExpandItemText() {
		return expandItemText;
	}

	public void setExpandItemText(String expandItemText) {
		this.expandItemText = expandItemText;
	}

	public int getTabItemName() {
		return tabItemName;
	}

	public void setTabItemName(int tabItemName) {
		this.tabItemName = tabItemName;
	}

	public ArrayList<SubFieldDomain> getSubFields() {
		return subFields;
	}

	public void setSubFieldDomain(ArrayList<SubFieldDomain> subFields) {
		this.subFields = subFields;
	}

	public SubFieldDescription getSubfieldDescription() {
		return subFieldDescription;
	}

	public void setSubfieldDescription(SubFieldDescription subFieldDescription) {
		this.subFieldDescription = subFieldDescription;
	}

	public FieldDomain(boolean repeatableField, int tag, String defaultValue, String description, String displayControl,
			boolean descriptors, String helpMessage, String pickList, int subFieldCount, int type, String validationFormat,
			int tabItemName, ArrayList<SubFieldDomain> subFields, SubFieldDescription subFieldDescription) {

		this.repeatableField = repeatableField;
		this.tag = tag;
		this.defaultValue = defaultValue;
		this.description = description;
		this.displayControl = displayControl;
		this.descriptors = descriptors;
		this.helpMessage = helpMessage;
		this.pickList = pickList;
		this.subFieldCount = subFieldCount;
		this.type = type;
		this.validationFormat = validationFormat;
		this.tabItemName = tabItemName;
		this.subFields = subFields;
		this.subFieldDescription = subFieldDescription;
		this.subtag = 0;
		if (helpMessage.equals(""))
			this.expandItemText = String.valueOf(tag) + "- " + description;
		else
			this.expandItemText = String.valueOf(tag) + "- " + description + "  (" + helpMessage + ")";

		if(!this.pickList.equals("") && this.pickList != null){
			String character = this.pickList.substring(0, 1);

			if(character.equals("@")){
				this.setReferenceDataBase(true);
				this.setReferencedDataBase(this.pickList.substring(1, this.pickList.length()));
			}
		}
	}

	public FieldDomain(boolean repeatableField, int tag, String defaultValue, String description, String displayControl,
			boolean descriptors, String helpMessage, String pickList, int subFieldCount, int type, String validationFormat,
			ArrayList<SubFieldDomain> subFields, SubFieldDescription subFieldDescription) {

		this.repeatableField = repeatableField;
		this.tag = tag;
		this.defaultValue = defaultValue;
		this.description = description;
		this.displayControl = displayControl;
		this.descriptors = descriptors;
		this.helpMessage = helpMessage;
		this.pickList = pickList;
		this.subFieldCount = subFieldCount;
		this.type = type;
		this.validationFormat = validationFormat;
		this.subFields = subFields;
		this.subFieldDescription = subFieldDescription;
		this.subtag = 0;
	}

	public boolean isReferenceDataBase() {
		return referenceDataBase;
	}

	public void setReferenceDataBase(boolean referenceDataBase) {
		this.referenceDataBase = referenceDataBase;
	}

	public String getReferencedDataBase() {
		return referencedDataBase;
	}

	public void setReferencedDataBase(String referencedDataBase) {
		this.referencedDataBase = referencedDataBase;
	}
}
