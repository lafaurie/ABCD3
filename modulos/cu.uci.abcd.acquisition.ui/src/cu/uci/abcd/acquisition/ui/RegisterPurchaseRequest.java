package cu.uci.abcd.acquisition.ui;

import java.sql.Date;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.acquisition.ui.updateArea.PurchaseRequestFragment;
import cu.uci.abcd.acquisition.ui.updateArea.ViewPurchaseRequestFragment;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;

public class RegisterPurchaseRequest extends ContributorPage implements Contributor {

	private int dimension;
	private Composite compoParent;
	private PurchaseRequestFragment purchaseRequestFragment;
	private PurchaseRequest purchaseRequest = null;
	private Map<String, Control> controlsMaps;
	private Composite compoButton;
	private Button rdb;
	private Button registerButton;
	private Button cancelButton;
	private int page = 0;
	private int size = 10;
	private PurchaseRequest purchaseRequestNumber = null;
	private ViewPurchaseRequestFragment viewPurchaseRequestFragment;
	private Composite viewPurchaseRequestSave;
	private String aux;
	private Composite compoRegister;
	private Composite associatePersonComposite;
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().CONTRIBUTOR_PURCHASE_REQUEST_REGISTER);
	}

	public int order() {
		return 0;
	}

	@Override
	public String getID() {
		return "addPurchaseRequestID";
	}

	public String containerMenu() {
		return "Adquisición";
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}


	@Override
	public Control createUIControl(final Composite parent) {
	
		addComposite(parent);
		dimension = parent.getParent().getParent().getParent().getBounds().width;
		
		compoParent= new Composite(parent, SWT.NORMAL);
		addComposite(compoParent);
		compoParent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		aux = MessageUtil.unescape(AbosMessages.get().REGISTER_PURCHASE_REQUEST);
		
		compoRegister = new Composite(compoParent, SWT.NORMAL);
		addComposite(compoRegister);
		compoRegister.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Composite resize = new Composite(compoRegister, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 370);
	
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
		associatePersonComposite = new Composite(compoRegister, SWT.NORMAL);
		addComposite(associatePersonComposite);
		associatePersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
	
		compoButton = new Composite(compoRegister, SWT.NORMAL);
		
		purchaseRequestFragment = new PurchaseRequestFragment(controller,purchaseRequest,dimension,aux,compoButton);
		Composite a = (Composite)purchaseRequestFragment.createUIControl(associatePersonComposite);
	
		controlsMaps = purchaseRequestFragment.getControls();
		
		
		compoButton.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		addComposite(compoButton);
		compoButton.setVisible(false);
		
		cancelButton = new Button(compoButton, SWT.NONE);
		add(cancelButton);
	
		registerButton = new Button(compoButton, SWT.NONE);
		add(registerButton);
	
		//RF_AQ9_Registrar Solicitud de Compra
		registerButton.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
						
				Nomenclator statePending = ((AllManagementController)controller).getSuggestion().getNomenclator((AdquisitionNomenclator.PURCHASEREQUEST_STATE_PENDING));
				
				if (purchaseRequestFragment.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}
				else if(purchaseRequestFragment.getAssociatedDesiderataTable().getRows().isEmpty())
					showErrorMessage(MessageUtil.unescape(AbosMessages.get().MESSAGES_ERROR_NO_DESIDERATA_ASSOCIATED));
				
				else
					if(purchaseRequestFragment.getValidator().decorationFactory.AllControlDecorationsHide()){
						
						purchaseRequestNumber = ((AllManagementController)controller).getPurchaseRequest().findPurchaseRequestByNumber(((Text) controlsMaps.get("txtApplicationNumber")).getText().replaceAll(" +", " ").trim());
						
						if (purchaseRequestNumber == null) {
						
						Library	library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
						User user = (User)SecurityUtils.getService().getPrincipal().getByKey("user");
		                long  idPerson = user.getPerson().getPersonID();
		                Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(idPerson);
		 
						java.util.Date fecha=new java.util.Date();
						
						PurchaseRequest purchaseToRegister=new PurchaseRequest();
						
						if (((Combo) controlsMaps.get("cbObjectType")).getSelectionIndex()== 1) {
								purchaseToRegister.setObjectType("Nuevo objeto");
						}else
							purchaseToRegister.setObjectType("Nueva copia");
						
						purchaseToRegister.setArea(library);
						purchaseToRegister.setCreator(workerLoggin);
					
						String identifier="";
						identifier+=fecha.getYear()+""+fecha.getMonth()+""+fecha.getDate();

						purchaseToRegister.setRequestIdentifier(identifier);
						purchaseToRegister.setCreationDate(new Date((fecha.getYear()), fecha.getMonth(), fecha.getDate()));
						purchaseToRegister.setState(statePending);
						purchaseToRegister.setRequestNumber(((Text) controlsMaps.get("txtApplicationNumber")).getText().replaceAll(" +", " ").trim());
					
						//revisar porke no esta cogiendo el estado 
						((AllManagementController)controller).getPurchaseRequest().setPurchaseRequest(purchaseToRegister);
						
						for(int i=0;i<purchaseRequestFragment.getDesiderataAssociatedList().size();i++){	
							purchaseRequestFragment.getDesiderataAssociatedList().get(i).setPurchaseRequest(purchaseToRegister);
							purchaseRequestFragment.getDesiderataAssociatedList().get(i).setState(((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.DESIDERATA_STATE_EXECUTED));
							((AllManagementController)controller).getDesiderata().setDesiderata(purchaseRequestFragment.getDesiderataAssociatedList().get(i));
						}
						
						RetroalimentationUtils.showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT);	
						
						//purchaseRequestFragment.searchCurrentProccesedSuggestionTable(page, size);
						purchaseRequestFragment.getDesiderataAssociatedList().clear();
						
						purchaseRequestFragment.limpiarCampos();
						
						ContributorService contributorService = getContributorService();
						
						viewPurchaseRequestFragment = new ViewPurchaseRequestFragment(controller, purchaseToRegister, compoParent,dimension, RegisterPurchaseRequest.this, contributorService);
						viewPurchaseRequestSave = (Composite) viewPurchaseRequestFragment.createUIControl(parent);
						viewPurchaseRequestSave.setData(RWT.CUSTOM_VARIANT, "gray_background");
					
						compoParent.setVisible(false);
						viewPurchaseRequestSave.setVisible(true);
						
						insertComposite(viewPurchaseRequestSave, parent);
						viewPurchaseRequestSave.getParent().layout(true, true);
						viewPurchaseRequestSave.getParent().redraw();
						viewPurchaseRequestSave.getParent().update();

					refresh();
					}
					else{
						((Text) controlsMaps.get("txtApplicationNumber")).setFocus();
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MESSAGES_PURCHASE_REQUEST_UNIQUE));}
					}else
						showErrorMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA);
		
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

		});

		//Cancelar los desideratas
		cancelButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {				
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION),
						new DialogCallback() {
				private static final long serialVersionUID = 1L;
					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {
							RegisterPurchaseRequest.this.notifyListeners(SWT.Dispose, new Event()); 
														
						}						
					}					
				} );	
			}
		});

		l10n();
		}}
		return parent;
	}

	@Override
	public void l10n() {
		cancelButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		registerButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT));	
		
		purchaseRequestFragment.l10n();
		refresh();
	}

}
