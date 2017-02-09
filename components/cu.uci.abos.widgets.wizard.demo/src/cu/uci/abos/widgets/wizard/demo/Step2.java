package cu.uci.abos.widgets.wizard.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.wizard.BaseStep;
import cu.uci.abos.widgets.wizard.IStep;
import cu.uci.abos.widgets.wizard.Wizard;

public class Step2 extends BaseStep implements IStep {
	
	public Step2(Wizard wizard) {
		super(wizard);
	}
	
	@Override
	public boolean isValid() {
		//for(Control control : this.controls.values()) {
		//}		
		return true;
	}

	@Override
	public Control createUI(Composite parent) {
		
		String label = "Primer apellido:";
		Label lblLastName = new Label(parent, SWT.NONE);
		FormDatas.attach(lblLastName).atTop(5).atLeft(5);
		lblLastName.setText(label);
		this.addOutputControl(lblLastName);
		
		Text txtLastName = new Text(parent, SWT.BORDER);
		FormDatas.attach(txtLastName).atTop(5).atLeftTo(lblLastName, 5);
		this.addInputControl(label, txtLastName);
		
		return parent;
	}
}
