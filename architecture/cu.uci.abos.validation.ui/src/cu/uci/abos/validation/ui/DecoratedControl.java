package cu.uci.abos.validation.ui;

import org.eclipse.swt.widgets.Control;

public class DecoratedControl {
	private Control decorateControl;
	private String decorateControlKey;
	private String text;

	public DecoratedControl(Control decorateControl, String decorateControlKey, String text) {
		super();
		this.decorateControl = decorateControl;
		this.decorateControlKey = decorateControlKey;
		this.text = text;
	}

	public Control getDecorateControl() {
		return decorateControl;
	}

	public void setDecorateControl(Control decorateControl) {
		this.decorateControl = decorateControl;
	}

	public String getDecorateControlKey() {
		return decorateControlKey;
	}

	public void setDecorateControlKey(String decorateControlKey) {
		this.decorateControlKey = decorateControlKey;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
