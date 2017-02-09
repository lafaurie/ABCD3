package cu.uci.abos.core.widget.repeatable.field.util;

import org.eclipse.swt.widgets.Control;

public class SimpleFieldStructure {

	private String labelText;
	private Control control;

	public String getLabelText() {
		return labelText;
	}

	public Control getControl() {
		return control;
	}

	public SimpleFieldStructure(String labelText, Control control) {
		this.labelText = labelText;
		this.control = control;
	}

}
