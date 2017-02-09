package cu.uci.abcd.acquisition.ui.updateArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.acquisition.ui.ConsultPurchaseOrder;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class EditPurchaseOrderArea extends BaseEditableArea {
	
	private ViewController controller;

	private PurchaseOrderFragment purchaseOrderFragment;
	private ConsultPurchaseOrder consultar;
	private CRUDTreeTable tabla;
	private PurchaseOrder purchaseOrder;
	private Map<String, Control> controlsMaps;
	private int page = 0;
	private int size = 10;
	private Button updateBtn;
	private Button rdb;
	
	public EditPurchaseOrderArea(ViewController controller,CRUDTreeTable tabla,ConsultPurchaseOrder c){
		this.controller=controller;
		this.tabla=tabla;
		consultar=c;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {
	
	}


	@Override
	public void setViewController(ViewController arg0) {

	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public Composite createButtons(final Composite parent, final IGridViewEntity entity,
			final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		updateBtn = new Button(parent, SWT.PUSH);
		updateBtn.setText(AbosMessages.get().BUTTON_ACCEPT);
		
		updateBtn.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			
				PurchaseOrder purchaseToRegister= (PurchaseOrder)entity.getRow();
				List<PurchaseRequest> listPurchaseRequest = new ArrayList<>();
				
				List<Desiderata> listDesideratasAux = new ArrayList<>();
			
				double aux = 0;
					
				if (purchaseOrderFragment.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}
				else if(purchaseOrderFragment.getAssociatedDesiderataTable().getRows().isEmpty() && !rdb.getSelection())
					showErrorMessage(AbosMessages.get().MESSAGE_ERROR_MUST_BE_A_DESIDERATA_ASSOCIATED);
				else if (purchaseOrderFragment.getValidator().decorationFactory.AllControlDecorationsHide()) {	
					PurchaseOrder purchaseOrderNumber = ((AllManagementController)controller).getPurchaseOrder().findPurchaseOrderByNumber(((Text) controlsMaps.get("txtOrderNumber")).getText());
					
					if (purchaseOrderNumber == null || purchaseOrderNumber.getPurchaseOrderID().equals(purchaseToRegister.getPurchaseOrderID())) {
										
						//purchaseToRegister.setCreationDate(purchaseToRegister.getCreationDate());
						purchaseToRegister.setOrderNumber(((Text) controlsMaps.get("txtOrderNumber")).getText());
						purchaseToRegister.setQuoteNumber(Integer.parseInt(((Text) controlsMaps.get("txtQuoteNumber")).getText()));
						purchaseToRegister.setProvider( (Provider) UiUtils.getSelected((Combo) controlsMaps.get("cbProvider")));
						purchaseToRegister.setState(((AllManagementController)controller).getSuggestion().getNomenclator((AdquisitionNomenclator.PURCHASEORDER_STATE_PENDING)));
						//purchaseToRegister.setRequestIdentifier(purchaseToRegister.getRequestIdentifier());
				
				User user = (User)SecurityUtils.getService().getPrincipal().getByKey("user");
                long  idPerson = user.getPerson().getPersonID();
                Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(idPerson);
 
                purchaseToRegister.setCreator(workerLoggin);
                
				if (rdb.getSelection())
				{					
					purchaseToRegister.setQuantity(Integer.parseInt(((Text) controlsMaps.get("txtQuantity")).getText()));
					purchaseToRegister.setPrice(Double.valueOf(((Text) controlsMaps.get("txtPrice")).getText()));
					purchaseToRegister.setTotalAmount(Integer.parseInt(((Text) controlsMaps.get("txtQuantity")).getText())* Double.valueOf(((Text) controlsMaps.get("txtPrice")).getText()));
					purchaseToRegister.setObjecttitle(((Text) controlsMaps.get("txtObjectTitle")).getText());
					purchaseToRegister.setCointype((Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("cbCoinType")));
					
					if (((Combo) controlsMaps.get("cbObjectType")).getSelectionIndex()== 1) {
						purchaseToRegister.setObjectType("Nuevo objeto");
					}else
						purchaseToRegister.setObjectType("Nueva copia");		
					
					((AllManagementController) controller).getPurchaseOrder().setPurchaseOrder(purchaseToRegister);
										
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
					
										
					purchaseToRegister.setTotalAmount(aux);
				
					((AllManagementController) controller).getPurchaseOrder().setPurchaseOrder(purchaseToRegister);
					
					for(int i=0;i<purchaseOrderFragment.getPurchaseRequestList().size();i++){	
						purchaseOrderFragment.getPurchaseRequestList().get(i).setState(((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.PURCHASEREQUEST_STATE_APROVED));
							
						try {									
							purchaseOrderFragment.getPurchaseRequestList().get(i).setPurchaseorder(null);														
							} catch (Exception e) {
								
							}
						((AllManagementController) controller).getPurchaseRequest().setPurchaseRequest(purchaseOrderFragment.getPurchaseRequestList().get(i));
						
					}
					
					for (int i = 0; i < purchaseOrderFragment.getListPurchaseRequestSelect().size(); i++) {
						purchaseOrderFragment.getListPurchaseRequestSelect().get(i).setState(((AllManagementController) controller).getSuggestion().getNomenclator(AdquisitionNomenclator.PURCHASEREQUEST_STATE_EXECUTED));
						purchaseOrderFragment.getListPurchaseRequestSelect().get(i).setPurchaseorder(purchaseToRegister);
						
						((AllManagementController) controller).getPurchaseRequest().setPurchaseRequest(purchaseOrderFragment.getListPurchaseRequestSelect().get(i));
					}
										
					
					purchaseOrderFragment.getListPurchaseRequestSelect().clear();
					purchaseOrderFragment.searchPurchaseRequestApprovedTable(page, size);
					purchaseOrderFragment.getAssociatedDesiderataTable().refresh();
					purchaseOrderFragment.getPurchaseRequestApprovedTable().refresh();
				}
											
				showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);
				
				manager.save( new BaseGridViewEntity<PurchaseOrder>(purchaseToRegister));
				manager.refresh();
				
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
		return parent;
	}

	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity,
			IVisualEntityManager arg2) {

		setDimension(parent.getParent().getParent().getBounds().width);
		
		PurchaseOrder purchaseOrderToView=(PurchaseOrder)entity.getRow();
		
		Composite registerPenalty = new Composite(parent, SWT.NORMAL);
		addComposite(registerPenalty);
		registerPenalty.setData(RWT.CUSTOM_VARIANT, "gray_background");
		String aux = MessageUtil.unescape(AbosMessages.get().EDIT_PURCHASE_ORDER);
		purchaseOrderFragment = new PurchaseOrderFragment(controller,purchaseOrderToView,parent.getParent().getParent().getBounds().width, aux);
		Composite a = (Composite)purchaseOrderFragment.createUIControl(registerPenalty);
		
		controlsMaps = purchaseOrderFragment.getControls();
		
		rdb = new Button(parent, SWT.RADIO);
		rdb = ((Button) controlsMaps.get("rd_sPurchaseRequest"));	

		l10n();
	
		return parent;
	}

	


}
