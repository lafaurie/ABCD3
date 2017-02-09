package cu.uci.abos.core.widget.repeatable.field.util;

import org.eclipse.swt.widgets.Control;

public class SubFieldStructure {

	private int tag;
	private Control control;
	private String labelText;
	private String subFieldCode;

	public SubFieldStructure(int tag, Control control, String labelText, String subFieldCode) {

		this.setTag(tag);
		this.setControl(control);
		this.setLabelText(labelText);
		this.subFieldCode = subFieldCode;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public String getLabelText() {
		return labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	public String getSubFieldCode() {
		return subFieldCode;
	}

	public void setSubFieldCode(String subFieldCode) {
		this.subFieldCode = subFieldCode;
	}

}
