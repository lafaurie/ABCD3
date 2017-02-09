package cu.uci.abos.widget.compoundLabel;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.core.util.FormDatas;

public class CompoundLabel extends Composite {

	Label label;
	Control control;
	Integer orientation;
	Integer margin;

	public CompoundLabel(Composite parent, int style) {
		super(parent, style);
		label = new Label(this, 0);
		control = new Text(this, 0);
		orientation = 0;
		margin = 0;
		setLayout(new FormLayout());
		setData(RWT.CUSTOM_VARIANT, "gray_background");

	}

	public CompoundLabel(Composite parent, int style, CompoundLabelType controlType) {

		super(parent, style);

		label = new Label(this, 0);
		switch (controlType) {
		case TEXT:
			control = new Text(this, SWT.NONE);
			break;
		case COMBO:
			control = new Combo(this, SWT.NORMAL);
			break;
		case DATETIME:
			control = new DateTime(this, SWT.DROP_DOWN | SWT.BORDER);
			break;

		default:
			break;
		}

		setLayout(new FormLayout());
		setData(RWT.CUSTOM_VARIANT, "gray_background");

	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
		layout(true);
	}

	public void setText(String text) {
		this.label.setText(text);

		layout(true);
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
		layout(true);
	}

	public void setPosition(int style, int margin, int controlWidth) {
		if (controlWidth == -1 || controlWidth == 0) {
			controlWidth = 170;
		}
		switch (style) {
		case SWT.TOP:

			FormDatas.attach(control).atTopTo(label, margin).withWidth(controlWidth).atLeft(5);
			FormDatas.attach(label).atTop(5).atLeft(5);
			layout(true);

			break;

		case SWT.LEFT:

			FormDatas.attach(control).atLeftTo(label, margin).withWidth(controlWidth);
			FormDatas.attach(label).atTop(14).atLeft(5);
			layout(true);
			break;

		default:
			break;
		}
	}

	private static final long serialVersionUID = -144798876113110208L;

}
