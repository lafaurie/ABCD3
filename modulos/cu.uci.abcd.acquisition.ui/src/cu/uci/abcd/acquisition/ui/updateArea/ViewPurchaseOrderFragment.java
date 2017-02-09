package cu.uci.abcd.acquisition.ui.updateArea;

import java.text.SimpleDateFormat;
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

import cu.uci.abcd.acquisition.ui.RegisterPurchaseOrder;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ViewPurchaseOrderFragment extends FragmentPage{

	private PurchaseOrder purchaseOrder;

	private int dimension;
	private static int direction = 1024;
	private int page = 0;
	private int size = 10;
	private ContributorService contributorService;
	private Button btnClose;
	private Button btnNew;
	private Composite registerPenalty;
	private RegisterPurchaseOrder registerPurchaseOrder;
	private CRUDTreeTable purchaseRequestTable;
	private Label associatedPurchaseRequest;
	private List<PurchaseRequest> suggestionList = new ArrayList<PurchaseRequest>();
	private Label lblViewPurchaseOrder;
	private ViewController controller;
	private String lastString;
	private Group personData;
	private List<Control> grupControlsPurchaseOrder = new ArrayList<>();
	private List<String> leftList = new LinkedList<>();

	public ViewPurchaseOrderFragment(ViewController controller) {
		this.setController(controller);
	}

	public ViewPurchaseOrderFragment(ViewController controller, PurchaseOrder purchaseOrder, int dimension) {
		this.setController(controller);
		this.setPurchaseOrder(purchaseOrder);
		this.dimension = dimension;
	}

	public ViewPurchaseOrderFragment(ViewController controller,PurchaseOrder purchaseOrder, Composite registerPenalty,int dimension, RegisterPurchaseOrder registerPurchaseOrder,ContributorService contributorService) {
		this.setController(controller);
		this.setPurchaseOrder(purchaseOrder);
		this.registerPenalty = registerPenalty;
		this.dimension = dimension;
		this.registerPurchaseOrder = registerPurchaseOrder;
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

		lblViewPurchaseOrder = new Label(group, 0);
		addHeader(lblViewPurchaseOrder);
		
		Label separator = new Label(group,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
	
		lastString = MessageUtil.unescape(AbosMessages.get().LABEL_DETAILS_OF_PURCHASE_ORDER);

		personData = new Group(group, SWT.NORMAL);
		add(personData);

		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ORDER_NUMBER));
		if (purchaseOrder.getQuoteNumber()!= 0) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_QUOTE_NUMBER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PROVIDER));
		if (purchaseOrder.getObjecttitle()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		if (purchaseOrder.getQuantity()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY));
		if (purchaseOrder.getPrice()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PRICE_OF_A_COPY));
		if (purchaseOrder.getObjectType()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_TYPE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_AMOUNT));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY));
		
		List<String> rightList = new LinkedList<>();
		rightList.add(String.valueOf(purchaseOrder.getOrderNumber()));
		if (purchaseOrder.getQuoteNumber()!= 0) 
			rightList.add(String.valueOf(purchaseOrder.getQuoteNumber()));
		rightList.add(purchaseOrder.getProvider().getName());
		if (purchaseOrder.getObjecttitle()!= null) 
			rightList.add(purchaseOrder.getObjecttitle());
		if (purchaseOrder.getQuantity()!= null) 
			rightList.add(String.valueOf(purchaseOrder.getQuantity()));
		if (purchaseOrder.getPrice()!= null) 
			rightList.add(String.valueOf(purchaseOrder.getPrice()) + " " +purchaseOrder.getCointype().getNomenclatorName());
		if (purchaseOrder.getObjectType()!= null) 
			rightList.add(purchaseOrder.getObjectType());
		rightList.add(new SimpleDateFormat("dd-MM-yyyy").format(purchaseOrder.getCreationDate()));
		rightList.add(String.valueOf(purchaseOrder.getTotalAmount()));
		rightList.add(purchaseOrder.getState().getNomenclatorName());
		rightList.add(purchaseOrder.getCreator().getPerson().getFullName());
		
		grupControlsPurchaseOrder=CompoundGroup.printGroup(personData, lastString, leftList, rightList);
	
		// --------------------TABLA DE Solicitudes de compra asociadas----------------------
		associatedPurchaseRequest = new Label(group, SWT.NONE);
		addHeader(associatedPurchaseRequest);
		associatedPurchaseRequest.setVisible(true);
		
		purchaseRequestTable = new CRUDTreeTable(group, SWT.NONE);
		purchaseRequestTable.setEntityClass(Suggestion.class);
		add(purchaseRequestTable);
		purchaseRequestTable.setVisible(true);
		
		TreeTableColumn column[] = {
				new TreeTableColumn(25, 0, "getCreationDate"),
				new TreeTableColumn(25, 1, "getCreator.getPerson.getFullName"),
				new TreeTableColumn(25, 2, "getRequestNumber"),
				new TreeTableColumn(25, 3, "getState")};
		
		purchaseRequestTable.createTable(column);
		
		purchaseRequestTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
				}
				searchCurrentReservationTable(event.currentPage - 1, event.pageSize);
			}
		});

		purchaseRequestTable.setPageSize(10);

		searchCurrentReservationTable(0, purchaseRequestTable.getPageSize());
		
		purchaseRequestTable.getPaginator().goToFirstPage();
	
		
		if (purchaseRequestTable.getRows().isEmpty()) {
			associatedPurchaseRequest.setVisible(false);
			purchaseRequestTable.setVisible(false);
		}
	
		reset();
		
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
				
				registerPurchaseOrder.notifyListeners(SWT.Dispose, new Event()); 
				contributorService.selectContributor("addPurchaseOrderID");
			}
		});

		btnClose.addSelectionListener(new SelectionAdapter() {
		    private static final long serialVersionUID = 2615553092700551346L;

		    @Override
		    public void widgetSelected(SelectionEvent e) {
		    	registerPurchaseOrder.notifyListeners(SWT.Dispose, new Event()); 
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

		associatedPurchaseRequest.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_PURCHASE_REQUEST_ASSOCIATED));
		purchaseRequestTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_REGISTRATION), MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY), MessageUtil.unescape(AbosMessages.get().LABEL_APPLICATION_NUMBER), MessageUtil.unescape(AbosMessages.get().LABEL_STATE)));
		lblViewPurchaseOrder.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PURCHASE_ORDER));
		btnClose.setText(AbosMessages.get().BUTTON_CLOSE);
		btnNew.setText(AbosMessages.get().BUTTON_NEW);
		
		lastString = MessageUtil.unescape(AbosMessages.get().LABEL_DETAILS_OF_PURCHASE_ORDER);

		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ORDER_NUMBER));
		if (purchaseOrder.getQuoteNumber()!= 0) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_QUOTE_NUMBER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PROVIDER));
		if (purchaseOrder.getObjecttitle()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		if (purchaseOrder.getQuantity()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY));
		if (purchaseOrder.getPrice()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PRICE_OF_A_COPY));
		if (purchaseOrder.getObjectType()!= null) 
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_TYPE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_AMOUNT));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY));
		
		CompoundGroup.l10n(grupControlsPurchaseOrder, leftList);
		personData.setText(lastString);
		
	}
	
	public void searchCurrentReservationTable(int page, int size) {
		suggestionList.clear();
		
		suggestionList = ((AllManagementController) controller).getPurchaseRequest().findPurchaseRequestByPurchaseOrderID(purchaseOrder.getPurchaseOrderID());
			
		purchaseRequestTable.setTotalElements(suggestionList.size());
		if (suggestionList.size() <= page * size + size) {
			purchaseRequestTable.setRows(suggestionList.subList(page * size, suggestionList.size()));
		} else {
			purchaseRequestTable.setRows(suggestionList.subList(page * size, page * size + size));
		}
	
		purchaseRequestTable.refresh();
	}
	
	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}	

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}
}
