package cu.uci.abos.widgets.wizard.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.widgets.wizard.BaseStep;
import cu.uci.abos.widgets.wizard.ControlData;
import cu.uci.abos.widgets.wizard.IStep;
import cu.uci.abos.widgets.wizard.Wizard;

public class Step5 extends BaseStep implements IStep {
	
	public Step5(Wizard wizard) {
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
	
		parent.setLayout(new GridLayout(2, true));
		
		Label lblOutline = new Label(parent, SWT.NONE);
		lblOutline.setText("Resumen:");
		Label lbl = new Label(parent, SWT.NONE);
		lbl.setText("");
		this.addOutputControl(lblOutline);
		this.addOutputControl(lbl);
		
		for(ControlData controlData : wizard.getAllControlsData()) {
			Label lblX = new Label(parent, SWT.NONE);
			lblX.setText(controlData.getLabel());
			Text txt = new Text(parent, SWT.NONE);
			GridData txtGridData = new GridData();
			txtGridData.horizontalAlignment = SWT.FILL;
			txtGridData.grabExcessHorizontalSpace = true;
			txt.setLayoutData(txtGridData);
			txt.setText(String.valueOf(controlData.getValue()));
			this.addOutputControl(lblX);
			this.addOutputControl(txt);
		}
		
		return parent;
	}
}
