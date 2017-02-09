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

import cu.uci.abcd.acquisition.ui.ConsultPurchaseRequest;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class EditPurchaseRequestArea extends BaseEditableArea {
	
	private ViewController controller;
	private PurchaseRequestFragment purchaseRequestFragment;
	private Map<String, Control> controlsMaps;
	private List<Desiderata> desiderataSaveList=new ArrayList<>();
	
	private Button acceptButton;

	private CRUDTreeTable tabla;
	private ConsultPurchaseRequest consultar;
	private PurchaseRequest purchaseRequestToView;
	private String aux;
	private int page = 0;
	private int size = 10;
	
	public EditPurchaseRequestArea(ViewController controller,CRUDTreeTable tabla,ConsultPurchaseRequest c){
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
		acceptButton = new Button(parent, SWT.PUSH);
		acceptButton.setText(AbosMessages.get().BUTTON_ACCEPT);
		
		acceptButton.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			
				PurchaseRequest purchaseToRegister= (PurchaseRequest)entity.getRow();
				
				if (purchaseRequestFragment.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}
				else if(purchaseRequestFragment.getAssociatedDesiderataTable().getRows().isEmpty())
					showErrorMessage(MessageUtil.unescape(AbosMessages.get().MESSAGES_ERROR_NO_DESIDERATA_ASSOCIATED));
								
				else
					if(purchaseRequestFragment.getValidator().decorationFactory.AllControlDecorationsHide()){
						
						PurchaseRequest	purchaseRequestNumber = ((AllManagementController)controller).getPurchaseRequest().findPurchaseRequestByNumber(((Text) controlsMaps.get("txtApplicationNumber")).getText());
						
						if (purchaseRequestNumber == null || purchaseRequestNumber.getPurchaseRequestID().equals(purchaseToRegister.getPurchaseRequestID())) {
						
						Library	library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
						User user = (User)SecurityUtils.getService().getPrincipal().getByKey("user");
		                long  idPerson = user.getPerson().getPersonID();
		                Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(idPerson);
		 
						if (((Combo) controlsMaps.get("cbObjectType")).getSelectionIndex()== 1) {
							purchaseToRegister.setObjectType("Nuevo objeto");
						}else
							purchaseToRegister.setObjectType("Nueva copia");
						
						purchaseToRegister.setArea(library);
						purchaseToRegister.setCreator(workerLoggin);
						purchaseToRegister.setState(((AllManagementController)controller).getSuggestion().getNomenclator((AdquisitionNomenclator.PURCHASEREQUEST_STATE_PENDING)));
						purchaseToRegister.setRequestNumber(((Text) controlsMaps.get("txtApplicationNumber")).getText());
						((AllManagementController)controller).getPurchaseRequest().setPurchaseRequest(purchaseToRegister);
						
						for(int i=0;i<purchaseRequestFragment.getDesiderataList().size();i++){	
							purchaseRequestFragment.getDesiderataList().get(i).setState(((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.DESIDERATA_STATE_PENDING));
								
							try {									
									purchaseRequestFragment.getDesiderataList().get(i).setPurchaseRequest(null);														
								} catch (Exception e) {
									
								}
								((AllManagementController)controller).getDesiderata().setDesiderata(purchaseRequestFragment.getDesiderataList().get(i));
								
						}
							
						for(int i=0;i<purchaseRequestFragment.getDesiderataAssociatedList().size();i++){	
							purchaseRequestFragment.getDesiderataAssociatedList().get(i).setPurchaseRequest(purchaseToRegister);
							purchaseRequestFragment.getDesiderataAssociatedList().get(i).setState(((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.DESIDERATA_STATE_EXECUTED));
							((AllManagementController)controller).getDesiderata().setDesiderata(purchaseRequestFragment.getDesiderataAssociatedList().get(i));
						}
											
						purchaseRequestFragment.getDesiderataAssociatedList().clear();
						purchaseRequestFragment.limpiarCampos();
												
						showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);
						
						manager.save(new BaseGridViewEntity<PurchaseRequest>(purchaseToRegister));
						manager.refresh();
					}
					else{
						((Text) controlsMaps.get("txtApplicationNumber")).setFocus();
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MESSAGES_PURCHASE_REQUEST_UNIQUE));}
					}else
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
		
		purchaseRequestToView= (PurchaseRequest) entity.getRow();
			
		setDimension(parent.getParent().getParent().getBounds().width);
		
		buildMessage(parent);
		
		Composite registerPenalty = new Composite(parent, SWT.NORMAL);
		addComposite(registerPenalty);
		registerPenalty.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		aux = MessageUtil.unescape(AbosMessages.get().EDIT_PURCHASE_REQUEST);
		
		purchaseRequestFragment = new PurchaseRequestFragment(controller,purchaseRequestToView,parent.getParent().getParent().getBounds().width, aux);
		Composite compoP = (Composite) purchaseRequestFragment.createUIControl(registerPenalty);
	
		controlsMaps = purchaseRequestFragment.getControls();
		
		return parent;
	}




}
