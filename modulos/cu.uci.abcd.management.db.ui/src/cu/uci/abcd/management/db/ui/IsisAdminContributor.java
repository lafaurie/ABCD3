package cu.uci.abcd.management.db.ui;


import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.management.db.l10n.AbosMessages;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.widget.wizard.IStep;
import cu.uci.abos.core.widget.wizard.Wizard;
import cu.uci.abos.core.widget.wizard.listener.ICancelListener;
import cu.uci.abos.core.widget.wizard.listener.IStepChangeListener;

public class IsisAdminContributor extends ContributorPage implements Contributor {


	private Wizard wizard;

	@Override
	public String contributorName() {
		return (MessageUtil.unescape(AbosMessages.get().ISIS_MANAGEMENT_HEADER));
	}

	@Override
	public String getID() {
		return "adminIsisID";
	}

	public Control createUIControl(final Composite parent) {
    	

			parent.setLayout(new GridLayout(1, true));
		
			 FillLayout topFillLayout = new FillLayout();
			 GridData topGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
			 Composite topComposite = new Composite(parent, SWT.NONE);
			 topComposite.setLayout(new FormLayout());
			 topComposite.setLayout(topFillLayout);
			 topComposite.setLayoutData(topGridData);
		
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(parent);
		
		wizard = new Wizard(parent, SWT.NONE);
		add(wizard);
		wizard.addStep(new FDTStep(wizard));
		//wizard.addStep(new DEWStep(wizard));
		wizard.createUI();
		
		FormDatas.attach(wizard).atTop(10).atLeft(20).atRight(20);

			wizard.addStepChangeListener(new IStepChangeListener() {
			  
			  public void handleEvent(IStep currentStep, IStep oldStep,  boolean isLast) { //do something.
				  } });
 

		wizard.addCancelListener(new ICancelListener() {
			  
			  public void handleEvent() {  //do something. 
				  } });
			 return parent;
	}
		
		//l10n();
		
	
 

	public void l10n() {
		wizard.setCancelBtnText(AbosMessages.get().WIZARD_BUTTON_CANCEL);
		wizard.setBeforeBtnText(AbosMessages.get().WIZARD_BUTTON_BEFORE);
		wizard.setNextBtnText(AbosMessages.get().WIZARD_BUTTON_NEXT);
		wizard.setFinishBtnText(AbosMessages.get().WIZARD_BUTTON_FINISH);


		wizard.l10n();

	}

	@Override
	public boolean canClose() {
		return true;
	}

	@Override
	public void setViewController(ViewController controller) {
		this.controller = controller;

	}
}
