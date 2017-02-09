package cu.uci.abcd.acquisition.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
import cu.uci.abcd.acquisition.ui.updateArea.PurchaseOrderFragment;
import cu.uci.abcd.acquisition.ui.updateArea.ViewPurchaseOrderFragment;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
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
import cu.uci.abos.core.util.UiUtils;

public class RegisterPurchaseOrder extends ContributorPage implements Contributor {

	private int dimension;
	private Composite compoParent;
	private PurchaseOrderFragment purchaseOrderFragment;
	private PurchaseOrder purchaseOrder = null;
	private Map<String, Control> controlsMaps;
	private Composite compoButton;
	private Button rdb;
	private Button registerButton;
	private Button cancelButton;
	private int page = 0;
	private int size = 10;
	private PurchaseOrder purchaseOrderNumber;
	private ViewPurchaseOrderFragment viewPurchaseOrderFragment;
	private Composite viewPurchaseOrderSave;
	private String aux;
	
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().CONTRIBUTOR_PURCHASE_ORDER_REGISTER);
	}

	public int order() {
		return 0;
	}

	@Override
	public String getID() {
		return "addPurchaseOrderID";
	}

	public String containerMenu() {
		return "Adquisición";
	}

	@Override
	public void l10n() {
		
		cancelButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		registerButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT));		
	
		purchaseOrderFragment.l10n();
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
		aux = MessageUtil.unescape(AbosMessages.get().REGISTER_PURCHASE_ORDER);
		
		Composite resize = new Composite(compoParent, 0);
		resize.setVisible(true);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height-150);

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
			
		
		purchaseOrderFragment = new PurchaseOrderFragment(controller,purchaseOrder,dimension,aux);
		Composite a = (Composite)purchaseOrderFragment.createUIControl(compoParent);
	
		controlsMaps = purchaseOrderFragment.getControls();
		
		rdb = ((Button) controlsMaps.get("rd_sPurchaseRequest"));
		
		compoButton = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton);
		compoButton.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		
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
				Library	library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
						
				double aux = 0;
				List<PurchaseRequest> listPurchaseRequest = new ArrayList<>();
				
				List<Desiderata> listDesideratasAux = new ArrayList<>();
				
				
				PurchaseOrder purchaseOrderSave = new PurchaseOrder();
				java.util.Date fecha=new java.util.Date();
						
				if (purchaseOrderFragment.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}
				else if(purchaseOrderFragment.getAssociatedDesiderataTable().getRows().isEmpty() && !rdb.getSelection())
				
					showErrorMessage(AbosMessages.get().MESSAGE_ERROR_MUST_BE_A_DESIDERATA_ASSOCIATED);
				
				else if (purchaseOrderFragment.getValidator().decorationFactory.AllControlDecorationsHide()) {	
					
					purchaseOrderNumber = ((AllManagementController)controller).getPurchaseOrder().findPurchaseOrderByNumber(((Text) controlsMaps.get("txtOrderNumber")).getText().replaceAll(" +", " ").trim());
										
					if (purchaseOrderNumber == null) {
						
				String identifier="";
				identifier+=fecha.getYear()+""+fecha.getMonth()+""+fecha.getDate();
				purchaseOrderSave.setLibrary(library);
				purchaseOrderSave.setCreationDate(new Date((fecha.getYear()), fecha.getMonth(), fecha.getDate()));
				purchaseOrderSave.setOrderNumber(((Text) controlsMaps.get("txtOrderNumber")).getText().replaceAll(" +", " ").trim());
				
				if (((Text) controlsMaps.get("txtQuoteNumber")).getText().isEmpty()) {
					purchaseOrderSave.setQuoteNumber(0);
				}else
				purchaseOrderSave.setQuoteNumber(Integer.parseInt(((Text) controlsMaps.get("txtQuoteNumber")).getText().replaceAll(" +", " ").trim()));
				
				purchaseOrderSave.setProvider( (Provider) UiUtils.getSelected((Combo) controlsMaps.get("cbProvider")));
				purchaseOrderSave.setState(((AllManagementController)controller).getSuggestion().getNomenclator((AdquisitionNomenclator.PURCHASEORDER_STATE_PENDING)));
				purchaseOrderSave.setRequestIdentifier(identifier);
				
				User user = (User)SecurityUtils.getService().getPrincipal().getByKey("user");
                long  idPerson = user.getPerson().getPersonID();
                Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(idPerson);
 
                purchaseOrderSave.setCreator(workerLoggin);
                
				if (rdb.getSelection())
				{					
					purchaseOrderSave.setQuantity(Integer.parseInt(((Text) controlsMaps.get("txtQuantity")).getText().replaceAll(" +", " ").trim()));
					purchaseOrderSave.setPrice(Double.valueOf(((Text) controlsMaps.get("txtPrice")).getText().replaceAll(" +", " ").trim()));
					purchaseOrderSave.setTotalAmount(Integer.parseInt(((Text) controlsMaps.get("txtQuantity")).getText()) * Double.valueOf(((Text) controlsMaps.get("txtPrice")).getText().replaceAll(" +", " ").trim()));
					purchaseOrderSave.setObjecttitle(((Text) controlsMaps.get("txtObjectTitle")).getText().replaceAll(" +", " ").trim());
					if (((Combo) controlsMaps.get("cbObjectType")).getSelectionIndex()== 1) {
						purchaseOrderSave.setObjectType("Nuevo objeto");
					}else
						purchaseOrderSave.setObjectType("Nueva copia");		
					
					purchaseOrderSave.setCointype((Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("cbCoinType")));
					
					((AllManagementController) controller).getPurchaseOrder().setPurchaseOrder(purchaseOrderSave);
					
					purchaseOrderFragment.cleanFields();
				}
				else if(!rdb.getSelection())
				{
					
					for (int i = 0; i < purchaseOrderFragment.getListPurchaseRequestSelect().size(); i++) 
					{
						 listPurchaseRequest.add(purchaseOrderFragment.getListPurchaseRequestSelect().get(i));
					}
					
					for (int k = 0; k < listPurchaseRequest.size(); k++) 
					{
						List<Desiderata> listDesideratas = new ArrayList<>();
						
						listDesideratas = ((AllManagementController) controller).getDesiderata().findDesideratasByPurchaseRequestId(listPurchaseRequest.get(k).getPurchaseRequestID());
						
						listDesideratasAux.addAll(listDesideratas);
					}
					
					for (int j = 0; j < listDesideratasAux.size(); j++) {
						double price = 0;
						price = listDesideratasAux.get(j).getPrice() * listDesideratasAux.get(j).getQuantity();
						aux = price + aux; 
					}
										
					purchaseOrderSave.setTotalAmount(aux);
				
					((AllManagementController) controller).getPurchaseOrder().setPurchaseOrder(purchaseOrderSave);
					
					for (int i = 0; i < purchaseOrderFragment.getListPurchaseRequestSelect().size(); i++) {
						purchaseOrderFragment.getListPurchaseRequestSelect().get(i).setState(((AllManagementController) controller).getSuggestion().getNomenclator(AdquisitionNomenclator.PURCHASEREQUEST_STATE_EXECUTED));
						purchaseOrderFragment.getListPurchaseRequestSelect().get(i).setPurchaseorder(purchaseOrderSave);
						
						((AllManagementController) controller).getPurchaseRequest().setPurchaseRequest(purchaseOrderFragment.getListPurchaseRequestSelect().get(i));
					}
										
					purchaseOrderFragment.cleanFields();
					purchaseOrderFragment.getListPurchaseRequestSelect().clear();
					purchaseOrderFragment.searchPurchaseRequestApprovedTable(page, size);
					purchaseOrderFragment.getAssociatedDesiderataTable().refresh();
					purchaseOrderFragment.getPurchaseRequestApprovedTable().refresh();
					
				}
											
				showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
				
				ContributorService contributorService = getContributorService();
				
				viewPurchaseOrderFragment = new ViewPurchaseOrderFragment(controller, purchaseOrderSave, compoParent,dimension, RegisterPurchaseOrder.this, contributorService);
				viewPurchaseOrderSave = (Composite) viewPurchaseOrderFragment.createUIControl(parent);
				viewPurchaseOrderSave.setData(RWT.CUSTOM_VARIANT, "gray_background");
			
				compoParent.setVisible(false);
				compoButton.setVisible(false);
				viewPurchaseOrderSave.setVisible(true);
				
				insertComposite(viewPurchaseOrderSave, parent);
				viewPurchaseOrderSave.getParent().layout(true, true);
				viewPurchaseOrderSave.getParent().redraw();
				viewPurchaseOrderSave.getParent().update();

					}
					else{
						((Text) controlsMaps.get("txtOrderNumber")).setFocus();
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MESSAGES_PURCHASE_ORDER_UNIQUE));}
				}
				else
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
			
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}

		});

		//Cancelar los pedidos
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
							RegisterPurchaseOrder.this.notifyListeners(SWT.Dispose, new Event()); 							
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
