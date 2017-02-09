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

public class Step3 extends BaseStep implements IStep {
	
	public Step3(Wizard wizard) {
		super(wizard);
	}
	
	@Override
	public boolean isValid() {
		//for(Control control : this.controls.values()) {
			//TODO: validation logic here.
		//}
		return true;
	}

	@Override
	public Control createUI(Composite parent) {
		
		String label = "Segundo apellido:";
		Label lblLastName2 = new Label(parent, SWT.NONE);
		FormDatas.attach(lblLastName2).atTop(5).atLeft(5);
		lblLastName2.setText(label);
		this.addOutputControl(lblLastName2);
		
		Text txtLastName2 = new Text(parent, SWT.BORDER);
		FormDatas.attach(txtLastName2).atTop(5).atLeftTo(lblLastName2, 5);
		this.addInputControl(label, txtLastName2);
		
		return parent;
	}
}
