package cu.uci.abos.widgets.wizard.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.widgets.wizard.IStep;
import cu.uci.abos.widgets.wizard.Wizard;
import cu.uci.abos.widgets.wizard.listeners.ICancelListener;
import cu.uci.abos.widgets.wizard.listeners.IStepChangeListener;

public class WizardDemoContributor implements IContributor {

	@Override
	public String contributorName() {
		return "Wizard demo";
	}

	@Override
	public String getID() {
		return "wizardDemo";
	}

	@Override
	public Control createUIControl(Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		
		FillLayout topFillLayout = new FillLayout();
		GridData topGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		Composite topComposite = new Composite(parent, SWT.NONE);
		topComposite.setLayout(topFillLayout);
		topComposite.setLayoutData(topGridData);
		
		Wizard wizard = new Wizard(topComposite, SWT.NONE);
		
		wizard.addStep(new Step1(wizard));
		wizard.addStep(new Step2(wizard));
		wizard.addStep(new Step3(wizard));
		wizard.addStep(new Step4(wizard));
		wizard.addStep(new Step5(wizard));
		
		wizard.createUI();
		wizard.addStepChangeListener(new IStepChangeListener() {			
			@Override
			public void handleEvent(IStep currentStep, IStep oldStep, boolean isLast) {
				// do something.
				
			}
		});
		
		wizard.addCancelListener(new ICancelListener() {			
			@Override
			public void handleEvent() {
				// do something.				
			}
		});
		
		return parent;
	}

	@Override
	public boolean canClose() {
		return true;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setViewController(IViewController arg0) {
		// TODO Auto-generated method stub		
	}
}
