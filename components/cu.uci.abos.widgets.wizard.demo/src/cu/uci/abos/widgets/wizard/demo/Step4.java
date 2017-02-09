package cu.uci.abos.widgets.wizard.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.wizard.BaseStep;
import cu.uci.abos.widgets.wizard.IStep;
import cu.uci.abos.widgets.wizard.Wizard;

public class Step4 extends BaseStep implements IStep {
	
	public Step4(Wizard wizard) {
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
		
		Label lblOtherData = new Label(parent, SWT.NONE);
		FormDatas.attach(lblOtherData).atTop(5).atLeft(5);
		lblOtherData.setText("Otros datos:");
		this.addOutputControl(lblOtherData);
		
		String label1 = "Soltero";
		Button singleChk = new Button(parent, SWT.CHECK);
		FormDatas.attach(singleChk).atTopTo(lblOtherData, 5).atLeft(5);
		singleChk.setSelection(true);
		singleChk.setText(label1);
		this.addInputControl(label1, singleChk);
		
		String label2 = "Mayor de 60";
		Button olderChk = new Button(parent, SWT.CHECK);
		FormDatas.attach(olderChk).atTopTo(lblOtherData, 5).atLeftTo(singleChk, 5);
		olderChk.setSelection(false);
		olderChk.setText(label2);
		this.addInputControl(label2, olderChk);
		
		return parent;
	}
}
