package cu.uci.abos.core.widget.wizard;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.core.widget.wizard.listener.IStepChangeListener;

public abstract class BaseStep {

	private List<Control> inputControls;
	private List<Control> outputControls;
	private List<ControlData> controlsData;
	protected Wizard wizard;

	public BaseStep(Wizard wizard) {
		this.controlsData = new LinkedList<ControlData>();
		this.inputControls = new LinkedList<Control>();
		this.outputControls = new LinkedList<Control>();
		this.wizard = wizard;
		this.wizard.addStepChangeListener(new IStepChangeListener() {
			@Override
			public void handleEvent(IStep currentStep, IStep oldStep, boolean isLast) {
				saveData();
			}
		});
	}

	public void destroyUI() {
		for (Control control : this.inputControls) {
			control.dispose();
		}
		for (Control control : this.outputControls) {
			control.dispose();
		}
		this.inputControls.clear();
		this.outputControls.clear();
	}

	private void saveData() {
		if (this.inputControls.size() > 0) {
			for (int i = 0; i < this.controlsData.size(); i++) {
				ControlData controlData = this.controlsData.get(i);
				Control control = this.inputControls.get(i);

				if (control.getClass().equals(Text.class)) {
					controlData.setValue(((Text) control).getText());
				} else if (control.getClass().equals( Button.class)) {
					controlData.setValue(((Button) control).getSelection());
				}
				// TODO: agregar los otros tipos de controles.
			}
		}
	}

	public void loadData() {
		if (this.inputControls.size() > 0) {
			for (int i = 0; i < this.controlsData.size(); i++) {
				ControlData controlData = this.controlsData.get(i);
				Control control = this.inputControls.get(i);
				Object value = controlData.getValue();

				if (value != null) {
					if (control instanceof Text) {
						((Text) control).setText((String) (value));
					} else if (control instanceof Button) {
						((Button) control).setSelection((Boolean) value);
					}
				}
				// TODO: agregar los otros tipos de controles.
			}
		}
	}

	protected void addInputControl(String label, Control control) {
		this.inputControls.add(control);
		if (this.controlsData.isEmpty() || this.controlsData.get(0).getValue() == null) {
			this.controlsData.add(new ControlData(label));
		}
	}

	protected void addOutputControl(Control control) {
		this.outputControls.add(control);
	}

	public List<ControlData> getControlsData() {
		return this.controlsData;
	}
}
