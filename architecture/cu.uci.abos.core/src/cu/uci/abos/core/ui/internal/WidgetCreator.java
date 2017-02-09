package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class WidgetCreator {

	public static Text createText(Composite parent, String text) {
		Text input = new Text(parent, SWT.NORMAL);
		input.setText(text);
		return input;
	}

	public static Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE | SWT.RIGHT);
		label.setText(text);
		return label;
	}

	public static Label createHeader(Composite parent) {
		return new Label(parent, SWT.NONE | SWT.LEFT);
	}

	public static Button createButton(Composite parent) {
		return new Button(parent, SWT.PUSH | SWT.CENTER);
	}

	public static Combo createCombo(Composite parent) {
		return new Combo(parent, SWT.READ_ONLY);
	}
}
