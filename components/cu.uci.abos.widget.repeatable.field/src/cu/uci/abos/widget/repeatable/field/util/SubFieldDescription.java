package cu.uci.abos.widget.repeatable.field.util;

import java.util.ArrayList;

import cu.uci.abos.widget.repeatable.field.ControlType;

public class SubFieldDescription {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private int type;
	private ControlType control;
	private String[] picklist;
	private ArrayList<String> texts;
	private ArrayList<Integer> types;
	private ArrayList<ControlType> controls;
	private ArrayList<String> subFieldCode;
	private ArrayList<String[]> comboList;
	private boolean haveSubfields;
	private int componentCount;

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
	public SubFieldDescription(ArrayList<String> texts, ArrayList<Integer> types, ArrayList<ControlType> controls,
			ArrayList<String> subFieldCode, ArrayList<String[]> comboList) {
		this.texts = texts;
		this.types = types;
		this.controls = controls;
		this.subFieldCode = subFieldCode;
		this.comboList = comboList;
		this.haveSubfields = true;
		this.componentCount = types.size();
	}

	public ArrayList<String> getTexts() {
		return texts;
	}

	public void setTexts(ArrayList<String> texts) {
		this.texts = texts;
	}

	public ArrayList<Integer> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<Integer> types) {
		this.types = types;
	}

	public ArrayList<ControlType> getControls() {
		return controls;
	}

	public void setControls(ArrayList<ControlType> controls) {
		this.controls = controls;
	}

	public ArrayList<String> getSubFieldCode() {
		return subFieldCode;
	}

	public void setSubFieldCode(ArrayList<String> subFieldCode) {
		this.subFieldCode = subFieldCode;
	}

	public void setDescriptions(ArrayList<String> texts, ArrayList<Integer> types, ArrayList<ControlType> controls) {
		this.texts = texts;
		this.types = types;
		this.controls = controls;
	}

	public void setDescriptions(ArrayList<String> texts, ArrayList<Integer> types, ArrayList<ControlType> controls,
			ArrayList<String> subFieldCode) {
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
}
