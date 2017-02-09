package cu.uci.abcd.acquisition.ui.updateArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.acquisition.ui.RegisterPurchaseOrder;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class PurchaseOrderFragment extends FragmentPage {

	private ViewController controller;
	private Map<String, Control> controls = new HashMap<String, Control>();
	private ValidatorUtils validator;
	
	private Composite group;
	
	private Label lbRegister;
	private CRUDTreeTable purchaseRequestApprovedTable;
	private CRUDTreeTable associatedDesiderataTable;

	private List<PurchaseRequest> listPurchaseRequestSelect= new ArrayList<>();
	//private List<PurchaseRequest> listPurchaseRequestSave = new ArrayList<PurchaseRequest>();
	private List<String> aaa = new Vector<>();
	private int page = 0;
	private int size = 10;
	private  String orderByStringDesiderata = "purchaseRequestID";
	private  int direction = 1024;

	private Button rd_sPurchaseRequest;
	private Button rd_cPurchaseRequest;
	
	private Label separator;
	private Label list;
	private Label list2;
	
	private Composite compoPurchaseRequestAssociate;
	private Composite compoAcquiringObject;

	private Text txtSearch;	
	private Button consult;
	private Label lblSearch;

	private String titulo="";
	private String requestNumber=null;
	private Label lblOrderNumber;
	private Label lblProvider;
	private Label lblAcquiringObjects;
	private Label lblObjectTitle;
	private Label lblPrice;
	private Label lblQuoteNumber;
	private Label lblObjecType;
	private Label lblQuantity;
	
	private Text txtOrderNumber;
	private Combo cbProvider;
	private Text txtObjectTitle;
	private Text txtPrice;
	private Text txtQuoteNumber;
	private Combo cbObjectType;
	private Text txtQuantity;
	private Label lblCoinType;
	private Combo cbCoinType;
	private Label separator1;
	private PurchaseOrder purchaseOrder;
	int dimension;
	private String aux;
	private Library library;
	private RegisterPurchaseOrder registerPurchaseOrder;
	private List<PurchaseRequest> purchaseRequestList; 
	private List<PurchaseRequest> temp;
	private Page<PurchaseRequest> listDB;
	
	public PurchaseOrderFragment(ViewController controller,PurchaseOrder purchaseOrder, int dimension, String aux) {
		this.controller = controller;
		this.purchaseOrder = purchaseOrder;
		this.dimension = dimension;
		this.aux = aux;
	}	
	
	
	@Override
	public Control createUIControl(Composite parent) {		
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		
		addComposite(parent);
		setDimension(dimension);
		
		validator = new ValidatorUtils(new CustomControlDecoration());
		temp = new ArrayList<>();	
		listPurchaseRequestSelect.clear();
		if (purchaseOrder != null) {
			
			purchaseRequestList =	((AllManagementController) controller).getPurchaseRequest().findPurchaseRequestByPurchaseOrderID(purchaseOrder.getPurchaseOrderID());
					
			listPurchaseRequestSelect.addAll(purchaseRequestList);
		}
		
		group = new Composite(parent, SWT.NONE);
		addComposite(group);

		group.setData(RWT.CUSTOM_VARIANT,"gray_background");
		parent.setData(RWT.CUSTOM_VARIANT,"gray_background");

		lbRegister = new Label(group, SWT.NONE);
		addHeader(lbRegister);

		separator1 = new Label(group,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator1);
		
		rd_sPurchaseRequest = new Button(group, SWT.RADIO);
		controls.put("rd_sPurchaseRequest", rd_sPurchaseRequest);
		rd_sPurchaseRequest.setSelection(true);
		add(rd_sPurchaseRequest);

		rd_cPurchaseRequest = new Button(group, SWT.RADIO);
		controls.put("rd_cPurchaseRequest", rd_cPurchaseRequest);
		add(rd_cPurchaseRequest);
		
		reset();
		
		lblOrderNumber = new Label(group, SWT.NONE);
		add(lblOrderNumber);		
		txtOrderNumber = new Text(group, SWT.NONE);
		controls.put("txtOrderNumber", txtOrderNumber);
		add(txtOrderNumber);
		validator.applyValidator(txtOrderNumber, "txtOrderNumber", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtOrderNumber, "txtOrderNumber1", DecoratorType.ALPHA_NUMERIC, true,20);
		
		lblQuoteNumber = new Label(group, SWT.NONE);
		add(lblQuoteNumber);		
		txtQuoteNumber = new Text(group, SWT.NONE);
		controls.put("txtQuoteNumber", txtQuoteNumber);
		add(txtQuoteNumber);
		validator.applyValidator(txtQuoteNumber, "txtQuoteNumber", DecoratorType.NUMBER_ONLY, true,6);
		
		reset();
		
		lblProvider = new Label(group, SWT.NONE);
		add(lblProvider);		
		cbProvider = new Combo(group, SWT.READ_ONLY);
		controls.put("cbProvider", cbProvider);
		add(cbProvider);
		initialize(cbProvider,((AllManagementController)controller).getPurchaseOrder().findAllProviders());
		validator.applyValidator(cbProvider, "cbProvider", DecoratorType.REQUIRED_FIELD, true);
		
	//	add(new Label(group, 0),Percent.W100);
				
		reset();
		
		compoAcquiringObject = new Composite(parent, SWT.NORMAL);
		compoAcquiringObject.setVisible(true);
		addComposite(compoAcquiringObject);
		compoAcquiringObject.setData(RWT.CUSTOM_VARIANT, "gray_background");
	
		lblAcquiringObjects = new Label(compoAcquiringObject, SWT.NONE);
		addHeader(lblAcquiringObjects);
		reset();
		
		lblObjectTitle = new Label(compoAcquiringObject,SWT.NONE);
		add(lblObjectTitle);
		txtObjectTitle = new Text(compoAcquiringObject, SWT.NONE);
		controls.put("txtObjectTitle", txtObjectTitle);
		add(txtObjectTitle);
		validator.applyValidator(txtObjectTitle, "txtObjectTitle", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtObjectTitle, 500);
		
		
		lblQuantity = new Label(compoAcquiringObject, SWT.NONE);
		add(lblQuantity);
		txtQuantity = new Text(compoAcquiringObject, SWT.NONE);
		controls.put("txtQuantity", txtQuantity);
		add(txtQuantity);
		validator.applyValidator(txtQuantity, "txtQuantity", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtQuantity, "txtQuantity1", DecoratorType.NUMBER_ONLY, true,4);
		
		reset();
		
		lblPrice = new Label(compoAcquiringObject, SWT.NONE);
		add(lblPrice);
		txtPrice = new Text(compoAcquiringObject, SWT.NONE);
		controls.put("txtPrice", txtPrice);
		add(txtPrice);
		validator.applyValidator(txtPrice, "txtPrice", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtPrice, "txtPrice1", DecoratorType.DOUBLE, true);
		
		lblCoinType=new Label(compoAcquiringObject,SWT.NONE);
		add(lblCoinType);

		cbCoinType = new Combo(compoAcquiringObject, SWT.READ_ONLY);
		controls.put("cbCoinType", cbCoinType);
		add(cbCoinType);
		initialize(cbCoinType, ((AllManagementController) controller).getSuggestion().findAllNomenclators(library.getLibraryID(),Nomenclator.COIN_TYPE));
		validator.applyValidator(cbCoinType, "cbCoinType2", DecoratorType.REQUIRED_FIELD, true);

		lblObjecType = new Label(compoAcquiringObject, SWT.NONE);
		add(lblObjecType);
	    cbObjectType = new Combo(compoAcquiringObject, SWT.READ_ONLY);
	    controls.put("cbObjectType", cbObjectType);
	    add(cbObjectType);

		aaa.add(MessageUtil.unescape(AbosMessages.get().LABEL_NEW_OBJECT));
		aaa.add(MessageUtil.unescape(AbosMessages.get().LABEL_NEW_COPY));
		UiUtils.initialize(cbObjectType, aaa);
		
	    validator.applyValidator(cbObjectType, "cbObjectType", DecoratorType.REQUIRED_FIELD, true);
	
				
		//-------------Composite PurchaseRequest Associated----------------
		compoPurchaseRequestAssociate = new Composite(parent, SWT.NORMAL);
		compoPurchaseRequestAssociate.setVisible(false);
		addComposite(compoPurchaseRequestAssociate);
		compoPurchaseRequestAssociate.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		//add(new Label(compoPurchaseRequestAssociate, 0),Percent.W100);
		
		separator = new Label(compoPurchaseRequestAssociate, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
	
		lblSearch = new Label(compoPurchaseRequestAssociate, 0);
		FormDatas.attach(lblSearch).atTopTo(separator, 19).atLeft(52);
				
		txtSearch = new Text(compoPurchaseRequestAssociate, SWT.NORMAL);
		FormDatas.attach(txtSearch).atTopTo(separator, 15).atLeftTo(lblSearch, 10).withHeight(12).withWidth(260);
				
		consult = new Button(compoPurchaseRequestAssociate, SWT.NONE);
		FormDatas.attach(consult).atLeftTo(txtSearch, 10).atTopTo(separator, 15).withHeight(22);
		
		txtSearch.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void focusLost(FocusEvent arg0) {
				consult.getShell().setDefaultButton(null);
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				consult.getShell().setDefaultButton(consult);
			}
		});
		
		
		add(new Label(compoPurchaseRequestAssociate, 0),Percent.W100);
		
		list = new Label(compoPurchaseRequestAssociate, SWT.NORMAL);
		addHeader(list);
		list.setVisible(false);
		//--------------------PurchaseRequest Aproved TABLE--------------------------------------
		purchaseRequestApprovedTable = new CRUDTreeTable(compoPurchaseRequestAssociate,SWT.NONE);
		add(purchaseRequestApprovedTable);
		purchaseRequestApprovedTable.setEntityClass(PurchaseRequest.class);
		//pendingDesiderataTable.setWatch(true,new ViewDesiderataArea(pendingDesiderataTable,controller,desiderataAssociatedList));
		purchaseRequestApprovedTable.setVisible(false);
		
		TreeTableColumn columns[] = {
				new TreeTableColumn(25, 0, "getCreationDate"),
				new TreeTableColumn(25, 1, "getCreator.getPerson.getFullName"),
				new TreeTableColumn(25, 2, "getRequestNumber"),
				new TreeTableColumn(25, 3, "getState")};
		//purchaseRequestApprovedTable.createTable(columns);  

		purchaseRequestApprovedTable.addListener(SWT.Resize, new Listener() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();				
			}
		});
			//----------------------------------FIN TABLA PEDIDOS PENDIENTES-------------------------

		list2 = new Label(compoPurchaseRequestAssociate, SWT.NORMAL);
		list2.setVisible(false);
		addHeader(list2);

		//------------TABLA DE PEDIDOS ASOCIADOS A LA ORDEN DE COMPRA-----------------------------------
		associatedDesiderataTable = new CRUDTreeTable(compoPurchaseRequestAssociate,SWT.NONE);
		add(associatedDesiderataTable);
		associatedDesiderataTable.setVisible(false);
		associatedDesiderataTable.setEntityClass(PurchaseRequest.class);
		associatedDesiderataTable.setDelete(true);
	
		Column checked = new Column("right-arrow", compoPurchaseRequestAssociate.getDisplay(), new TreeColumnListener() {
			
			@Override
			public void handleEvent(TreeColumnEvent event) {
				
				PurchaseRequest purchaseRequestAssociate = (PurchaseRequest) event.entity.getRow();
					
				searchPurchaseRequestApprovedTable(0, purchaseRequestApprovedTable.getPageSize());
				
				temp.remove(purchaseRequestAssociate);
				listPurchaseRequestSelect.add(purchaseRequestAssociate);
				
				
				searchPurchaseRequestApprovedTable(0, purchaseRequestApprovedTable.getPageSize());
				purchaseRequestApprovedTable.getPaginator().goToFirstPage();
				purchaseRequestApprovedTable.refresh();

				searchAssociatedPurchaseRequestTable(0, associatedDesiderataTable.getPageSize());
				associatedDesiderataTable.getPaginator().goToFirstPage();
				refresh();
				
			}
		});
			

		checked.setToolTipText(AbosMessages.get().BUTTON_ASSOCIATE_SELECTION);
		checked.setAlignment(SWT.CENTER);
		purchaseRequestApprovedTable.addActionColumn(checked);
		purchaseRequestApprovedTable.createTable(columns); 
		purchaseRequestApprovedTable.setPageSize(10);	
		
		TreeTableColumn columns1[] = {
				new TreeTableColumn(25, 0, "getCreationDate"),
				new TreeTableColumn(25, 1, "getCreator.getPerson.getFullName"),
				new TreeTableColumn(25, 2, "getRequestNumber"),
				new TreeTableColumn(25, 3, "getState")};
		
		associatedDesiderataTable.createTable(columns1);  	
		associatedDesiderataTable.setPageSize(10);
		
		associatedDesiderataTable.addListener(SWT.Resize, new Listener() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();				
			}
		});
		
		purchaseRequestApprovedTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
				}   
				searchPurchaseRequestApprovedTable(event.currentPage - 1, event.pageSize);
			}
		}); 
		
		associatedDesiderataTable.addDeleteListener(new TreeColumnListener() {
			
			@Override
			public void handleEvent(TreeColumnEvent event) {
				PurchaseRequest purchaseRequestEliminate = (PurchaseRequest) event.entity.getRow();
				
				temp.add(purchaseRequestEliminate);
				listPurchaseRequestSelect.remove(purchaseRequestEliminate);
			
				searchPurchaseRequestApprovedTable(0, purchaseRequestApprovedTable.getPageSize());
				purchaseRequestApprovedTable.getPaginator().goToFirstPage();
				purchaseRequestApprovedTable.refresh();

				searchAssociatedPurchaseRequestTable(0, associatedDesiderataTable.getPageSize());
				associatedDesiderataTable.getPaginator().goToFirstPage();
				refresh();
				
			}
		});
			
	
		rd_sPurchaseRequest.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				compoPurchaseRequestAssociate.setVisible(false);
				compoAcquiringObject.setVisible(true);
				
				insertComposite(compoAcquiringObject, group);
				compoAcquiringObject.getShell().layout(true, true);
				compoAcquiringObject.getShell().redraw();
				compoAcquiringObject.getShell().update();
				
				insertComposite(compoPurchaseRequestAssociate, group);

				compoPurchaseRequestAssociate.getShell().layout(true, true);
				compoPurchaseRequestAssociate.getShell().redraw();
				compoPurchaseRequestAssociate.getShell().update();
				
				if (registerPurchaseOrder != null) {					
					registerPurchaseOrder.refresh();
				}
				refresh();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// Para cuando seleccione registrar con sugerencias
		rd_cPurchaseRequest.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				compoPurchaseRequestAssociate.setVisible(true);				
				compoAcquiringObject.setVisible(false);				
				
				insertComposite(compoAcquiringObject, group);
				compoAcquiringObject.getShell().layout(true, true);
				compoAcquiringObject.getShell().redraw();
				compoAcquiringObject.getShell().update();
				
				insertComposite(compoPurchaseRequestAssociate, group);
				compoPurchaseRequestAssociate.getShell().layout(true, true);
				compoPurchaseRequestAssociate.getShell().redraw();
				compoPurchaseRequestAssociate.getShell().update();
				
				if (registerPurchaseOrder != null) {					
					registerPurchaseOrder.refresh();
				}
				refresh();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

	consult.addSelectionListener(new SelectionAdapter() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
			
				if (txtSearch.getText().length() == 0) {
					requestNumber= null;
				}else
				requestNumber = txtSearch.getText();	
				
				purchaseRequestApprovedTable.clearRows();
				purchaseRequestApprovedTable.destroyEditableArea();
				list.setVisible(true);
				list2.setVisible(true);
				purchaseRequestApprovedTable.setVisible(true);
				associatedDesiderataTable.setVisible(true);
				
				searchPurchaseRequestApprovedTable(0, purchaseRequestApprovedTable.getPageSize());
				if (purchaseRequestApprovedTable.getRows().isEmpty()) 
				{
					if (associatedDesiderataTable.getRows().isEmpty()) {	
						
					list.setVisible(false);
					list2.setVisible(false);
					purchaseRequestApprovedTable.setVisible(false);
					associatedDesiderataTable.setVisible(false);
					}
					
					RetroalimentationUtils.showInformationMessage(compoPurchaseRequestAssociate, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				}
				else
					purchaseRequestApprovedTable.getPaginator().goToFirstPage();
				
				if (registerPurchaseOrder != null) {					
					registerPurchaseOrder.refresh();
				}
				refresh();
			}				
		});
	
		l10n();
	loadPurchaseOrderData();
		
		return parent;
	}

	public List<PurchaseRequest> getPurchaseRequestList() {
		return purchaseRequestList;
	}


	public void setPurchaseRequestList(List<PurchaseRequest> purchaseRequestList) {
		this.purchaseRequestList = purchaseRequestList;
	}


	public void l10n() {
		lbRegister.setText(aux);
		
		rd_sPurchaseRequest.setText(MessageUtil.unescape(AbosMessages.get().LABEL_WITHOUT_PURCHASE_REQUEST_ASSOCIATED));
		rd_cPurchaseRequest.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PURCHASE_REQUEST__ASSOCIATED));
		
		associatedDesiderataTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_REGISTRATION), MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY), MessageUtil.unescape(AbosMessages.get().LABEL_APPLICATION_NUMBER), MessageUtil.unescape(AbosMessages.get().LABEL_STATE)));

		purchaseRequestApprovedTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_REGISTRATION), MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY), MessageUtil.unescape(AbosMessages.get().LABEL_APPLICATION_NUMBER), MessageUtil.unescape(AbosMessages.get().LABEL_STATE)));
	
		list.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_PURCHASE_REQUEST_APROVED));
		list2.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_PURCHASE_REQUEST_ASSOCIATED));
		lblOrderNumber.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ORDER_NUMBER));
		lblProvider.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PROVIDER));
		lblAcquiringObjects.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUIRING_OBJECT));
		lblObjectTitle.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_TITLE));
		lblPrice.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COST));
		lblQuoteNumber.setText(MessageUtil.unescape(AbosMessages.get().LABEL_QUOTE_NUMBER));
		lblObjecType.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_TYPE));
		lblQuantity.setText(MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY));
			
		lblCoinType.setText((MessageUtil.unescape(AbosMessages.get().LABEL_COIN)));
		
		lblSearch.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_PURCHASE_REQUEST_BY));
		txtSearch.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_APPLICATION_NUMBER));
		consult.setText(AbosMessages.get().BUTTON_SEARCH);
	
	}
	

	//Buscar las solicitudes de compra aprobadas
	public void searchPurchaseRequestApprovedTable(int page, int size) {
		
		listDB = ((AllManagementController) controller).findAllPendingPurchaseRequest(((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.PURCHASEREQUEST_STATE_APROVED),requestNumber, page, 20,direction, orderByStringDesiderata);
		purchaseRequestApprovedTable.clearRows();
		
		if (temp.isEmpty()) {
			temp.addAll(listDB.getContent());
		}
	
			
		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0; j < listPurchaseRequestSelect.size(); j++) {
				try {
					if (temp.get(i).getPurchaseRequestID().equals(listPurchaseRequestSelect.get(j).getPurchaseRequestID())) {
						
						temp.remove(i--);
					}		
				} catch (Exception e) {
					// TODO: handle exception
				}			
			}			
		}
		//temp.removeAll(desiderataAssociatedList);       	
	
		purchaseRequestApprovedTable.clearRows();				
		purchaseRequestApprovedTable.setTotalElements((int) temp.size());
		
		if (temp.size() <= page * size + size) {
			purchaseRequestApprovedTable.setRows(temp.subList(page * size, temp.size()));
		} else {
			purchaseRequestApprovedTable.setRows(temp.subList(page * size, page * size + size));
		}	
	
		purchaseRequestApprovedTable.refresh();
		
	}
	
	public void searchAssociatedPurchaseRequestTable(int page, int size) {
		
		associatedDesiderataTable.clearRows();
		associatedDesiderataTable.setTotalElements(listPurchaseRequestSelect.size());
		if (listPurchaseRequestSelect.size() <= page * size + size) {
			associatedDesiderataTable.setRows(listPurchaseRequestSelect.subList(page * size, listPurchaseRequestSelect.size()));
		} else {
			associatedDesiderataTable.setRows(listPurchaseRequestSelect.subList(page * size, page * size + size));
		}
	
		associatedDesiderataTable.refresh();
		purchaseRequestApprovedTable.refresh();
	}
	
		
	public void loadPurchaseOrderData() {
		if (purchaseOrder != null) {
						
			if (purchaseOrder.getQuoteNumber() == null)
				txtQuoteNumber.setText("");
			else
				txtQuoteNumber.setText(String.valueOf(purchaseOrder.getQuoteNumber()));
			
			txtOrderNumber.setText(String.valueOf(purchaseOrder.getOrderNumber()));
			
			UiUtils.initialize(cbProvider, ((AllManagementController)controller).getPurchaseOrder().findAllProviders());
			UiUtils.selectValue(cbProvider, purchaseOrder.getProvider());
		
											
			if (!listPurchaseRequestSelect.isEmpty()) {
				rd_cPurchaseRequest.setSelection(true);
				rd_sPurchaseRequest.setSelection(false);
				
				compoPurchaseRequestAssociate.setVisible(true);				
				compoAcquiringObject.setVisible(false);				
				
				list.setVisible(true);
				list2.setVisible(true);
				
				purchaseRequestApprovedTable.setVisible(true);
				associatedDesiderataTable.setVisible(true);
				
				insertComposite(compoAcquiringObject, group);
				compoAcquiringObject.getShell().layout(true, true);
				compoAcquiringObject.getShell().redraw();
				compoAcquiringObject.getShell().update();
				
				insertComposite(compoPurchaseRequestAssociate, group);
				compoPurchaseRequestAssociate.getShell().layout(true, true);
				compoPurchaseRequestAssociate.getShell().redraw();
				compoPurchaseRequestAssociate.getShell().update();
				
				 searchPurchaseRequestApprovedTable(page, size);
				 searchAssociatedPurchaseRequestTable(page, size);
				 
				 purchaseRequestApprovedTable.getPaginator().goToFirstPage();
				  associatedDesiderataTable.getPaginator().goToFirstPage();
			}
			else{
				rd_sPurchaseRequest.setSelection(true);
				rd_cPurchaseRequest.setSelection(false);
				
				txtObjectTitle.setText(purchaseOrder.getObjecttitle());	
				txtPrice.setText(purchaseOrder.getPrice().toString());
				txtQuantity.setText(String.valueOf(purchaseOrder.getQuantity()));
		
				if (purchaseOrder.getObjectType().equals("Nuevo objeto")) {
					cbObjectType.select(1);
				}
				else
					cbObjectType.select(2);
				
				UiUtils.initialize(cbCoinType,  ((AllManagementController) controller).getSuggestion().findAllNomenclators(library.getLibraryID(),Nomenclator.COIN_TYPE));
				UiUtils.selectValue(cbCoinType, purchaseOrder.getCointype());
			
				compoPurchaseRequestAssociate.setVisible(false);
				compoAcquiringObject.setVisible(true);
				
				insertComposite(compoAcquiringObject, group);
				compoAcquiringObject.getShell().layout(true, true);
				compoAcquiringObject.getShell().redraw();
				compoAcquiringObject.getShell().update();
				
				insertComposite(compoPurchaseRequestAssociate, group);

				compoPurchaseRequestAssociate.getShell().layout(true, true);
				compoPurchaseRequestAssociate.getShell().redraw();
				compoPurchaseRequestAssociate.getShell().update();
				}
		}
	}
	
	public void cleanFields(){
		txtOrderNumber.setText("");
		txtObjectTitle.setText("");
		txtPrice.setText("");
		txtQuantity.setText("");
		txtQuoteNumber.setText("");
		cbObjectType.select(0);
		cbProvider.select(0);
		cbCoinType.select(0);
	}


	public CRUDTreeTable getPurchaseRequestApprovedTable() {
		return purchaseRequestApprovedTable;
	}


	public void setPurchaseRequestApprovedTable(
			CRUDTreeTable purchaseRequestApprovedTable) {
		this.purchaseRequestApprovedTable = purchaseRequestApprovedTable;
	}


	public CRUDTreeTable getAssociatedDesiderataTable() {
		return associatedDesiderataTable;
	}


	public void setAssociatedDesiderataTable(CRUDTreeTable associatedDesiderataTable) {
		this.associatedDesiderataTable = associatedDesiderataTable;
	}


	public ValidatorUtils getValidator() {
		return validator;
	}


	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public List<PurchaseRequest> getListPurchaseRequestSelect() {
		return listPurchaseRequestSelect;
	}


	public void setListPurchaseRequestSelect(
			List<PurchaseRequest> listPurchaseRequestSelect) {
		this.listPurchaseRequestSelect = listPurchaseRequestSelect;
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public Map<String, Control> getControls() {
		return controls;
	}

	public void setControls(Map<String, Control> controls) {
		this.controls = controls;
	}
	

}
