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

public class Step1 extends BaseStep implements IStep {
	
	public Step1(Wizard wizard) {
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
		
		String label = "Nombre:";
		Label lblName = new Label(parent, SWT.NONE);
		FormDatas.attach(lblName).atTop(5).atLeft(5);
		lblName.setText(label);	
		this.addOutputControl(lblName);
		
		Text txtName = new Text(parent, SWT.BORDER);
		FormDatas.attach(txtName).atTop(5).atLeftTo(lblName, 5);
		this.addInputControl(label, txtName);
		
		return parent;
	}
}
