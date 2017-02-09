package cu.uci.abos.widget.repeatable.field.domain;

import cu.uci.abos.widget.repeatable.field.ControlType;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;


public class SimpleFieldDomain {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private int type;
	private String labelText;
	private ControlType controlType;
	private boolean plus;
	private boolean repeatedField;
	private String description;
	private int tag;
	private boolean haveSubfields;
	private String subFieldCode;
	private String[] comboList;
	private FieldStructure topComponent;
	private String expandItemText;
	private String defaultValue;
	private String displayControl;
	private int subFieldCount;
	private boolean register;
	private boolean notPlusItem;
	private boolean mandatory;

	public boolean isMandatory(){
		return this.mandatory;
	}

	public void setMandatoryField(boolean mandatory){
		this.mandatory = mandatory;
	}

	public void setNotPlusItem(boolean value){
		this.notPlusItem = value;
	}

	public boolean getNotPlusItem(){
		return this.notPlusItem;
	}

	public void setRegister(boolean value){
		this.register = value;
	}

	public boolean getRegister(){
		return this.register;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLabelText() {
		return labelText;
	}
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}
	public ControlType getControlType() {
		return controlType;
	}
	public void setControlType(ControlType controlType) {
		this.controlType = controlType;
	}
	public boolean isPlus() {
		return plus;
	}
	public void setPlus(boolean plus) {
		this.plus = plus;
	}
	public boolean isRepeatedField() {
		return repeatedField;
	}
	public void setRepeatedField(boolean repeatedField) {
		this.repeatedField = repeatedField;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public boolean isHaveSubfields() {
		return haveSubfields;
	}
	public void setHaveSubfields(boolean haveSubfields) {
		this.haveSubfields = haveSubfields;
	}
	public String getSubFieldCode() {
		return subFieldCode;
	}
	public void setSubFieldCode(String subFieldCode) {
		this.subFieldCode = subFieldCode;
	}
	public String[] getComboList() {
		return comboList;
	}
	public void setComboList(String[] comboList) {
		this.comboList = comboList;
	}
	public FieldStructure getTopComponent() {
		return topComponent;
	}
	public void setTopComponent(FieldStructure topComponent) {
		this.topComponent = topComponent;
	}
	public String getExpandItemText() {
		return expandItemText;
	}
	public void setExpandItemText(String expandItemText) {
		this.expandItemText = expandItemText;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDisplayControl() {
		return displayControl;
	}
	public void setDisplayControl(String displayControl) {
		this.displayControl = displayControl;
	}

	public SimpleFieldDomain(boolean repeatedField, String description, int tag, boolean haveSubFields,
			FieldStructure topComponent, String expandItemText, int subFieldCount, boolean plus){

		this.repeatedField = repeatedField;
		this.description = description;
		this.tag = tag;
		this.haveSubfields = haveSubFields;
		this.topComponent = topComponent;
		this.expandItemText = expandItemText;
		this.plus = plus;
		this.setSubFieldCount(subFieldCount);
	}
	public int getSubFieldCount() {
		return subFieldCount;
	}
	public void setSubFieldCount(int subFieldCount) {
		this.subFieldCount = subFieldCount;
	}


}
