package cu.uci.abcd.acquisition.ui.updateArea;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.acquisition.ui.ConsultPurchaseOrder;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class SetPurchaseOrderArea extends BaseEditableArea{
	
	private ValidatorUtils validator;
	private String valor;
	private CRUDTreeTable tabla;
	private ViewController controller;
	private ConsultPurchaseOrder a;
	private PurchaseOrder auxPurchaseOrder;
	private int page = 0;
	private int size = 10;
	private Nomenclator state;
	private String lastString;
	private Label lblReason;
	private Combo cbReason;
	private Button setPurchaseOrder;
	private PurchaseOrder purchaseOrderToView;
	private Library library;
	
	private  Group personData;
	private List<Control> grupControlsPurchaseOrder = new ArrayList<>();
	private List<String> leftList = new LinkedList<>();

	
	public SetPurchaseOrderArea(String valor,ViewController controller, CRUDTreeTable tabla,ConsultPurchaseOrder a){
		this.valor=valor;
		this.tabla=tabla;
		this.controller=controller;
		this.a=a;
	}
	
	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1,
			IVisualEntityManager arg2) {
		
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		setPurchaseOrder = new Button(parent, SWT.PUSH);
			
		//Aceptar la orden de compra
		setPurchaseOrder.addSelectionListener(new SelectionListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if(validator.decorationFactory.AllControlDecorationsHide()){
					auxPurchaseOrder=new PurchaseOrder();
					auxPurchaseOrder = purchaseOrderToView;
				
					User user = (User)SecurityUtils.getService().getPrincipal().getByKey("user");
	                long  idPerson = user.getPerson().getPersonID();
	                Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(idPerson);
	 
					if(setPurchaseOrder.getText().equals(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT)))
					{						
						state=((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.PURCHASEORDER_STATE_APROVED);
						auxPurchaseOrder.setState(state);
						auxPurchaseOrder.setApprovedBy(workerLoggin);
						auxPurchaseOrder.setAcceptanceMotive((Nomenclator) UiUtils.getSelected((Combo) cbReason));
						((AllManagementController) controller).getPurchaseOrder().setPurchaseOrder(auxPurchaseOrder);
					
						RetroalimentationUtils.showInformationMessage(AbosMessages.get().MESSAGES_PURCHASE_ORDER_ACCEPTED);
					}
					else if(setPurchaseOrder.getText().equals(MessageUtil.unescape(AbosMessages.get().BUTTON_REJECT)))
					{
						
						state=((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.PURCHASEORDER_STATE_REJECTED);
						auxPurchaseOrder.setState(state);
						auxPurchaseOrder.setRejectionMotive((Nomenclator) UiUtils.getSelected((Combo) cbReason));
						((AllManagementController) controller).getPurchaseOrder().setPurchaseOrder(auxPurchaseOrder);
						
						RetroalimentationUtils.showInformationMessage(AbosMessages.get().MESSAGES_PURCHASE_ORDER_REJECTED);
						
					}
									
					a.searchCurrentpurchaseOrderTable(page, size);
					tabla.destroyEditableArea();
				}else{
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			
			}
		});
		
		if(!valor.equals("ver")){
			if(valor.equals(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT))){
				lblReason.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REASON_FOR_ACCEPTANCE));
				setPurchaseOrder.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT));
			}
			if(valor.equals(MessageUtil.unescape(AbosMessages.get().BUTTON_REJECT))){
				lblReason.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REASON_FOR_REJECTION));
				setPurchaseOrder.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_REJECT));
			}
		}
		
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager arg2) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		
		validator = new ValidatorUtils(new CustomControlDecoration());
		
		Composite group = new Composite(parent, SWT.NONE);
		addComposite(group);
		
		purchaseOrderToView=(PurchaseOrder)entity.getRow();		
		
	
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		group.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
        personData = new Group(parent, SWT.NORMAL);
        add(personData);
        
       	lastString=MessageUtil.unescape(AbosMessages.get().LABEL_DATA_PURCHASE_ORDER);
     
    	leftList = new LinkedList<>();
    	leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ORDER_NUMBER));
		if (purchaseOrderToView.getQuoteNumber()!= 0) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_QUOTE_NUMBER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PROVIDER));
		if (purchaseOrderToView.getObjecttitle()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		if (purchaseOrderToView.getQuantity()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY));
		if (purchaseOrderToView.getPrice()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PRICE_OF_A_COPY));
		if (purchaseOrderToView.getObjectType()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_TYPE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_AMOUNT));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY));
		
		List<String> rightList = new LinkedList<>();
		rightList.add(String.valueOf(purchaseOrderToView.getOrderNumber()));
		if (purchaseOrderToView.getQuoteNumber()!= 0) 
			rightList.add(String.valueOf(purchaseOrderToView.getQuoteNumber()));
		rightList.add(purchaseOrderToView.getProvider().getName());
		if (purchaseOrderToView.getObjecttitle()!= null) 
			rightList.add(purchaseOrderToView.getObjecttitle());
		if (purchaseOrderToView.getQuantity()!= null) 
			rightList.add(String.valueOf(purchaseOrderToView.getQuantity()));
		if (purchaseOrderToView.getPrice()!= null) 
			rightList.add(String.valueOf(purchaseOrderToView.getPrice()) + " " +purchaseOrderToView.getCointype().getNomenclatorName());
		if (purchaseOrderToView.getObjectType()!= null) 
			rightList.add(purchaseOrderToView.getObjectType());
		rightList.add(new SimpleDateFormat("dd-MM-yyyy").format(purchaseOrderToView.getCreationDate()));
		rightList.add(String.valueOf(purchaseOrderToView.getTotalAmount()));
		rightList.add(purchaseOrderToView.getState().getNomenclatorName());
		rightList.add(purchaseOrderToView.getCreator().getPerson().getFullName());

       grupControlsPurchaseOrder= CompoundGroup.printGroup(personData, lastString, leftList, rightList);
        
        br();
        
    	lblReason = new Label(parent, SWT.NONE);
		lblReason.setVisible(false);
		add(lblReason);
		
		cbReason = new Combo(parent, SWT.READ_ONLY);
		cbReason.setVisible(false);
		add(cbReason);
		
        if(!valor.equals(MessageUtil.unescape(AbosMessages.get().BUTTON_VIEW))){
			
			lblReason.setVisible(true);
			cbReason.setVisible(true);
			
			if(valor.equals(MessageUtil.unescape(AbosMessages.get().BUTTON_REJECT)))
				listOfRejectReasons(cbReason);

			if(valor.equals(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT)))
				listOfAcceptanceReasons(cbReason);	
			
			validator.applyValidator(cbReason, "cbReason", DecoratorType.REQUIRED_FIELD, true);	
			
		}
		
        l10n();
     
		return parent;
	}

	
	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {		
	lastString=MessageUtil.unescape(AbosMessages.get().LABEL_DATA_PURCHASE_ORDER);	
	
	leftList.clear();
	leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ORDER_NUMBER));
	if (purchaseOrderToView.getQuoteNumber()!= 0) 
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_QUOTE_NUMBER));
	leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PROVIDER));
	if (purchaseOrderToView.getObjecttitle()!= null) 
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
	if (purchaseOrderToView.getQuantity()!= null) 
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY));
	if (purchaseOrderToView.getPrice()!= null) 
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PRICE_OF_A_COPY));
	if (purchaseOrderToView.getObjectType()!= null) 
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_TYPE));
	leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE));
	leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_AMOUNT));
	leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
	leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY));
	
    CompoundGroup.l10n(grupControlsPurchaseOrder, leftList);
	personData.setText(lastString);

	
	}
	
	void listOfRejectReasons(Combo c){
		initialize(cbReason, ((AllManagementController)controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.REASON_FOR_REJECTION));
	}
	
	void listOfAcceptanceReasons(Combo c){
		initialize(cbReason, ((AllManagementController)controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.APPROVAL_REASON));
	}
	
}
