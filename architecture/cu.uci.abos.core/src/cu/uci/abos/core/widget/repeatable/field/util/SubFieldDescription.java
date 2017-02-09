package cu.uci.abos.core.widget.repeatable.field.util;

import java.util.ArrayList;

import cu.uci.abos.core.widget.repeatable.field.ControlType;

public class SubFieldDescription {

	private int type;
	private ControlType control;
	private String[] picklist;
	private String[] texts;
	private int[] types;
	private ControlType[] controls;
	private String[] subFieldCode;
	private ArrayList<String[]> comboList;
	private boolean haveSubfields;
	private int componentCount;
	private String moreBig;

	// not have subFields
	public SubFieldDescription(int type, ControlType control) {
		this.type = type;
		this.control = control;
		this.haveSubfields = false;
		this.componentCount = 1;
	}

	// not have subFields
	public SubFieldDescription(int type, ControlType control, String[] pickList) {

		this.type = type;
		this.control = control;
		this.picklist = pickList;
		this.haveSubfields = false;
		this.componentCount = 1;
	}

	// have subFields
	public SubFieldDescription(String[] texts, int[] types, ControlType[] controls, String[] subFieldCode, ArrayList<String[]> comboList, String moreBig) {
		this.texts = texts;
		this.types = types;
		this.controls = controls;
		this.subFieldCode = subFieldCode;
		this.comboList = comboList;
		this.haveSubfields = true;
		this.componentCount = types.length;
		this.moreBig = moreBig;
	}

	public String[] getTexts() {
		return texts;
	}

	public void setTexts(String[] texts) {
		this.texts = texts;
	}

	public int[] getTypes() {
		return types;
	}

	public void setTypes(int[] types) {
		this.types = types;
	}

	public ControlType[] getControls() {
		return controls;
	}

	public void setControls(ControlType[] controls) {
		this.controls = controls;
	}

	public String[] getSubFieldCode() {
		return subFieldCode;
	}

	public void setSubFieldCode(String[] subFieldCode) {
		this.subFieldCode = subFieldCode;
	}

	public void setDescriptions(String[] texts, int[] types, ControlType[] controls) {
		this.texts = texts;
		this.types = types;
		this.controls = controls;
	}

	public void setDescriptions(String[] texts, int[] types, ControlType[] controls, String[] subFieldCode) {
		this.texts = texts;
		this.types = types;
		this.controls = controls;
		this.subFieldCode = subFieldCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ControlType getControl() {
		return control;
	}

	public void setControl(ControlType control) {
		this.control = control;
	}

	public String[] getPicklist() {
		return picklist;
	}

	public void setPicklist(String[] picklist) {
		this.picklist = picklist;
	}

	public ArrayList<String[]> getComboList() {
		return comboList;
	}

	public void setComboList(ArrayList<String[]> comboList) {
		this.comboList = comboList;
	}

	public boolean itHasSubfields() {
		return haveSubfields;
	}

	public int getComponentCount() {
		return componentCount;
	}

	public String getMoreBig() {
		return moreBig;
	}

}
