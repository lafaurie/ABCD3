package cu.uci.abos.widget.compoundLabel;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Compound extends Composite {

	private Label label;
	private Control control;

	public Compound(Composite parent, int style) {
		super(parent, style);
		initializeComponent(CompoundLabelType.TEXT);
	}

	public Compound(Composite parent, int style, CompoundLabelType type) {
		super(parent, style);
		initializeComponent(type);
	}

	public void initializeComponent(CompoundLabelType type) {
		// initialize content
		label = new Label(this, 0);
		switch (type) {
		case TEXT:
			control = new Text(this, SWT.NONE);
			break;
		case COMBO:
			control = new Combo(this, SWT.NORMAL);
			break;
		case DATETIME:
			control = new DateTime(this, SWT.DROP_DOWN | SWT.BORDER);
			break;
		}
		
		setLayout(new CompoundLabelLayout());
		setData(RWT.CUSTOM_VARIANT, "gray_background");
		if (!type.equals(CompoundLabelType.TEXT))
			setComponentWidth(375);
	}

	public void setLabelText(String text) {
		this.label.setText(text);
		layout(true);
	}

	public Label getLabel() {
		return label;
	}

	public Control getControl() {
		return control;
	}

	public void setComponentWidth(int newWidth) {
		((CompoundLabelLayout) getLayout()).width = newWidth;
		layout(true);
	}

	public void setComponentSpacing(int spacing) {
		int nSpacing = (spacing - label.getSize().x) + 10;
		((CompoundLabelLayout) getLayout()).difference = nSpacing;
		layout(true);
	}
	
	private static final long serialVersionUID = 1L;
}
