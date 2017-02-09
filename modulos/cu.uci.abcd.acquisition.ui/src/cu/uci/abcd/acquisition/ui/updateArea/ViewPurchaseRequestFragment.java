package cu.uci.abcd.acquisition.ui.updateArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.acquisition.ui.RegisterPurchaseRequest;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.acquisition.ui.controller.Auxiliary;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ViewPurchaseRequestFragment extends FragmentPage {

	private PurchaseRequest purchaseRequest;
	private CRUDTreeTable tablaDesideratasAssociated;
	private Label associatedDesideratas;
	private int dimension;
	private int page = 0;
	private int size = 10;
	private int direction = 1024;
	private List<Desiderata> desiderataList = new ArrayList<>();
	private ContributorService contributorService;
	private Button btnClose;
	private Button btnNew;
	private Composite registerPenalty;
	private RegisterPurchaseRequest registerPurchaseRequestClass;
	private Label lblViewPurchaseRequest;
	private ViewController controller;
	
	private List<Control> grupControlsPurchaseRequest = new ArrayList<>();
	private List<String> leftList = new LinkedList<>();
	private String lastString;
	private Group purchaseResquestGroup;

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public ViewPurchaseRequestFragment(ViewController controller) {
		this.setController(controller);
	}

	public ViewPurchaseRequestFragment(ViewController controller, PurchaseRequest purchaseRequest, int dimension) {
		this.setController(controller);
		this.setPurchaseRequest(purchaseRequest);
		this.dimension = dimension;
	}

	public ViewPurchaseRequestFragment(ViewController controller,PurchaseRequest purchaseRequest, Composite registerPenalty,int dimension, RegisterPurchaseRequest registerPurchaseRequestClass,ContributorService contributorService) {
		this.setController(controller);
		this.setPurchaseRequest(purchaseRequest);
		this.registerPenalty = registerPenalty;
		this.dimension = dimension;
		this.registerPurchaseRequestClass = registerPurchaseRequestClass;
		this.contributorService = contributorService;
	}
	
	
	@Override
	public Control createUIControl(Composite parent) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
	

		dimension = parent.getParent().getParent().getBounds().width;
		setDimension(dimension);
		
		Composite group = new Composite(parent, SWT.NONE);
		addComposite(group);
	
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		group.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		lblViewPurchaseRequest = new Label(group, 0);
		addHeader(lblViewPurchaseRequest);
		
		Label separator = new Label(group,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
	
		lastString = MessageUtil.unescape(AbosMessages.get().LABEL_DATA_PURCHASE_REQUEST);
		purchaseResquestGroup = new Group(group, SWT.NORMAL);
	    add(purchaseResquestGroup);
	    
	    leftList = new LinkedList<>();
	    leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_APPLICATION_NUMBER));
	    leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_TYPE));
	    leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE));
        leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY));
        leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
        
        List<String> rightList = new LinkedList<>();
        rightList.add(String.valueOf(purchaseRequest.getRequestNumber()));
        rightList.add(String.valueOf(purchaseRequest.getObjectType()));
        rightList.add(Auxiliary.FormatDate(purchaseRequest.getCreationDate()));
        rightList.add(purchaseRequest.getCreator().getPerson().getFullName());
        rightList.add(purchaseRequest.getState().getNomenclatorName());
      	                
        grupControlsPurchaseRequest=CompoundGroup.printGroup( purchaseResquestGroup, lastString, leftList, rightList);
        
     // --------------------TABLA DE Desidratas Asociadas----------------------
        associatedDesideratas = new Label(group, SWT.NONE);
		addHeader(associatedDesideratas);
		associatedDesideratas.setVisible(true);
		
		tablaDesideratasAssociated = new CRUDTreeTable(group, SWT.NONE);
		tablaDesideratasAssociated.setEntityClass(Desiderata.class);
		add(tablaDesideratasAssociated);
		tablaDesideratasAssociated.setVisible(true);
		
		TreeTableColumn column[] = {
				new TreeTableColumn(20, 0, "getTitle"),
				new TreeTableColumn(20, 1, "getAuthor"),
				new TreeTableColumn(20, 2, "getEditorial"),
				new TreeTableColumn(20, 3, "getCreationDate"),
				new TreeTableColumn(20, 4, "getState.getNomenclatorName") };
		
		tablaDesideratasAssociated.createTable(column);
		
		tablaDesideratasAssociated.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
				}
				searchCurrentReservationTable(event.currentPage - 1, event.pageSize);
			}
		});

		tablaDesideratasAssociated.setPageSize(10);

		searchCurrentReservationTable(0, tablaDesideratasAssociated.getPageSize());
		
		tablaDesideratasAssociated.getPaginator().goToFirstPage();
	   
				
		btnClose = new Button(group, SWT.PUSH);
		add(btnClose);
		
		btnNew = new Button(group, SWT.PUSH);
		add(btnNew);
		
		if (registerPenalty != null) {
			btnNew.setVisible(true);
			btnClose.setVisible(true);
		} else
		{
			btnNew.setVisible(false);
			btnClose.setVisible(false);
		}
		
		
		btnNew.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				registerPurchaseRequestClass.notifyListeners(SWT.Dispose, new Event()); 
			
				contributorService.selectContributor("addPurchaseRequestID");
			}
		});

		btnClose.addSelectionListener(new SelectionAdapter() {
		    private static final long serialVersionUID = 2615553092700551346L;

		    @Override
		    public void widgetSelected(SelectionEvent e) {
		    	registerPurchaseRequestClass.notifyListeners(SWT.Dispose, new Event()); 
		     }
		    });
		
		
		l10n();
		return parent;
	}
	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		lblViewPurchaseRequest.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PURCHASE_REQUEST));
		
		associatedDesideratas.setText(AbosMessages.get().LABEL_LIST_OF_ASSOCIATED_DESIDERATAS);
		tablaDesideratasAssociated.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR),
				MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL),
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE),
				MessageUtil.unescape(AbosMessages.get().LABEL_STATE)));	
		btnClose.setText(AbosMessages.get().BUTTON_CLOSE);
		btnNew.setText(AbosMessages.get().BUTTON_NEW);
		
		 lastString=MessageUtil.unescape(AbosMessages.get().LABEL_DATA_PURCHASE_REQUEST);
		  leftList.clear();
		  leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_APPLICATION_NUMBER));
		  leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_TYPE));
		    leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE));
	        leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY));
	        leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
	        CompoundGroup.l10n(grupControlsPurchaseRequest, leftList);
		  purchaseResquestGroup.setText(lastString);
	}
	
	public void searchCurrentReservationTable(int page, int size) {	
		desiderataList.clear();		
		desiderataList = ((AllManagementController) controller).getDesiderata().findDesideratasByPurchaseRequestId(purchaseRequest.getPurchaseRequestID());
		
		tablaDesideratasAssociated.setTotalElements(desiderataList.size());
		if (desiderataList.size() <= page * size + size) {
			tablaDesideratasAssociated.setRows(desiderataList.subList(page * size, desiderataList.size()));
		} else {
			tablaDesideratasAssociated.setRows(desiderataList.subList(page * size, page * size + size));
		}
	
		tablaDesideratasAssociated.refresh();
	}
	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}
	
	
}
