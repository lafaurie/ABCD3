package cu.uci.abos.core.validation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.core.l10n.AbosMessages;

public class LessOneRule extends ValidationRule {

	@Override
	public boolean validate(String group) {
		boolean result = false;
		boolean exist = false;
		for (Control control : controls) {
			if (control.getData(group) != null) {
				control.setBackground(control.getDisplay().getSystemColor(SWT.COLOR_WHITE));
				exist = true;
				if (control.getClass().equals( Text.class)) {
					if (!((Text) control).getText().isEmpty()) {
						result = true;
					}
				} else {
					if (control.getClass().equals( Combo.class)) {
						if ((((Combo) control).getSelectionIndex() > 0)) {
							result = true;
						}
					}
				}
			}
		}
		if (exist && result == false) {

			for (Control control : controls) {
				if (control.getData(group) != null) {
					control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
				}
			}
		}
		return result;
	}

	@Override
	public String getErrorMessage() {
		return AbosMessages.get().MSG_ERROR_FIELD_REQUIRED;
	}

}
