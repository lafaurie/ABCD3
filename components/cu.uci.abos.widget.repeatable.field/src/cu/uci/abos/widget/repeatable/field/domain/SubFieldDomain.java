package cu.uci.abos.widget.repeatable.field.domain;

import cu.uci.abos.widget.repeatable.field.util.Constants;

public class SubFieldDomain {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private String defaultValue;
	private String description;
	private String displayControl;
	private String helpMessage;
	private String pickList;
	private boolean repeatableSubField;
	private String subFieldCode;
	private int tag;
	private int type;
	private String validationFormat;

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

	public boolean isRepeatableSubField() {
		return repeatableSubField;
	}

	public void setRepeatableSubField(boolean repeatableSubField) {
		this.repeatableSubField = repeatableSubField;
	}

	public String getSubFieldCode() {
		return subFieldCode;
	}

	public void setSubFieldCode(String subFieldCode) {
		this.subFieldCode = subFieldCode;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
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

	public SubFieldDomain(String defaultValue, String description, String displayControl, String helpMessage, String pickList,
			boolean repeatableSubField, String subFieldCode, int tag, int type,
			String validationFormat) {

		this.defaultValue = defaultValue;
		this.description = description;
		this.displayControl = displayControl;
		this.helpMessage = helpMessage;
		this.pickList = pickList;
		this.repeatableSubField = repeatableSubField;
		this.subFieldCode = subFieldCode;
		this.tag = tag;
		this.type = type;
		this.validationFormat = validationFormat;
	}

	public boolean isObligatory(){
		boolean response = false;
		if(validationFormat.equals(Constants.MANDATORY))
			response = true;
		return response;
	}
}
