package cu.uci.abos.widget.repeatable.field.util;

import org.eclipse.swt.widgets.Control;

public class SimpleFieldStructure {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

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
