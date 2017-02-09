package cu.uci.abcd.acquisition.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.acquisition.ui.updateArea.DesiderataFragment;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;

public class RegisterDesiderata extends ContributorPage {

	private Composite compoParent;
	private DesiderataFragment desiderataFragment;
	
		
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().CONTRIBUTOR_REGISTER_DESIDERATA);
	}

	public int order() {
		return 0;
	}

	@Override
	public String getID() {
		return "addDesiderataID";
	}

	public String containerMenu() {
		return "Adquisición";
	}

	@Override
	public void l10n() {
		desiderataFragment.l10n();
		
		desiderataFragment.setOkButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT));
	
		refresh();
	}

	@Override
	public Control createUIControl(final Composite parent) {
		
		addComposite(parent);
		
		compoParent= new Composite(parent, SWT.NORMAL);
		addComposite(compoParent);
		compoParent.setData(RWT.CUSTOM_VARIANT, "gray_background");
	
		Composite resize = new Composite(compoParent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 370);
		
		User user = (User)SecurityUtils.getService().getPrincipal().getByKey("user");
      
		if (user.getPerson() == null) {			
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);			
		}
		else{		
		
		long  idPerson = user.getPerson().getPersonID();
        Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(idPerson);
     
		if (workerLoggin == null) {
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);
		}
		else{			
		
		desiderataFragment = new DesiderataFragment(this);
		refresh();
		desiderataFragment.setAux(MessageUtil.unescape(AbosMessages.get().REGISTER_DESIDERATA));
		desiderataFragment.createUIControl(compoParent);
		desiderataFragment.buttonVisible(true);
		
		// RF_AQ3_Registrar Pedido con Sugerencias asociadas
		desiderataFragment.addOkListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if (desiderataFragment.validate()) {
					Desiderata desiderata =desiderataFragment.llenarDesiderata();
					showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
					RegisterDesiderata.this.notifyListeners(SWT.Dispose, new Event());
					navigate("viewDesiderataContributor",desiderata);				
				} 				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		// Cancelar registro de desiderata con sugerencias asociadas
		desiderataFragment.addCancelListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {				
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION),
						new DialogCallback() {
				private static final long serialVersionUID = 1L;
					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {
							desiderataFragment.limpiarCampos();
							desiderataFragment.dispose();
							RegisterDesiderata.this.notifyListeners(SWT.Dispose, new Event()); 														
						}						
					}					
				} );	
			}
		});
		
		l10n();
		}}
		return parent;
	}

}