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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
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

public class PurchaseRequestFragment extends FragmentPage{

	private ViewController controller;
	private Map<String, Control> controls = new HashMap<String, Control>();
	private ValidatorUtils validator;
	
	private Label lbRegister;
	private CRUDTreeTable pendingDesiderataTable;
	private CRUDTreeTable associatedDesiderataTable;

	private ViewPurchaseRequestFragment viewPurchaseRequestFragment;
	private List<Desiderata> desiderataAssociatedList=new ArrayList<>();
	private int dimension;
	private int page = 0;
	private int size = 10;
	private  String orderByStringDesiderata = "desiderataID";
	private  int direction = 1024;
	private PurchaseRequest purchaseRequestNumber;
	private Label lblApplicationNumber;
	private Text txtApplicationNumber;
	private Text txtSearch;
		
	private Button consult;
	private Label lblSearch;
	private Label separator;
	private Label list;
	private Label list2;

	private List<String> aaa = new Vector<>();
	private Library library;
	private Composite compoSearch;

	private String title = null;
	private String author = null;
	private Combo cbObjectType;
	private Label lblObjectType;
	private Composite viewPurchaseRequestSave;
	private Composite group;
	private PurchaseRequest purchaseRequest;
	private String aux;
	private Composite compoButtons;
	private Label separator1;
	private String params;
	private List<Desiderata> temp;
	private Page<Desiderata> listDB;
	private List<Desiderata> desiderataList;
	
	public CRUDTreeTable getPendingDesiderataTable() {
		return pendingDesiderataTable;
	}

	public void setPendingDesiderataTable(CRUDTreeTable pendingDesiderataTable) {
		this.pendingDesiderataTable = pendingDesiderataTable;
	}

	public PurchaseRequestFragment(ViewController controller,PurchaseRequest purchaseRequest, int dimension,String aux) {
		this.controller = controller;
		this.purchaseRequest = purchaseRequest;
		this.dimension = dimension;
		this.aux = aux;
	}	
	
	public PurchaseRequestFragment(ViewController controller,PurchaseRequest purchaseRequest, int dimension,String aux, Composite compoButtons) {
		this.controller = controller;
		this.purchaseRequest = purchaseRequest;
		this.dimension = dimension;
		this.aux = aux;
		this.compoButtons = compoButtons;
	}	
	
	@Override
	public Control createUIControl(Composite parent) {		
		setDimension(dimension);
		validator = new ValidatorUtils(new CustomControlDecoration());
		library = (Library)SecurityUtils.getService().getPrincipal().getByKey("library");
		
		temp = new ArrayList<>();
					
		desiderataAssociatedList.clear();
		if (purchaseRequest != null) {
			
			desiderataList	= ((AllManagementController) controller).getDesiderata().findDesideratasByPurchaseRequestId(purchaseRequest.getPurchaseRequestID());
				
		desiderataAssociatedList.addAll(desiderataList);
		}
		
		group = new Composite(parent, SWT.NONE);
		addComposite(group);

		group.setData(RWT.CUSTOM_VARIANT,"gray_background");
		parent.setData(RWT.CUSTOM_VARIANT,"gray_background");

		lbRegister = new Label(group, SWT.NONE);
		addHeader(lbRegister);
		
		separator1 = new Label(group,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator1);
	
		lblApplicationNumber = new Label(group, SWT.NONE);
		add(lblApplicationNumber);

		txtApplicationNumber = new Text(group, SWT.NONE);
		controls.put("txtApplicationNumber", txtApplicationNumber);
		add(txtApplicationNumber);
		validator.applyValidator(txtApplicationNumber, "txtApplicationNumber", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtApplicationNumber, "txtApplicationNumber1", DecoratorType.ALPHA_NUMERIC, true,20);
		
	
		lblObjectType = new Label(group, 0);
		add(lblObjectType);
		
		cbObjectType = new Combo(group, SWT.READ_ONLY);
		add(cbObjectType);
		controls.put("cbObjectType", cbObjectType);
		aaa.add(MessageUtil.unescape(AbosMessages.get().LABEL_NEW_OBJECT));
		aaa.add(MessageUtil.unescape(AbosMessages.get().LABEL_NEW_COPY));
		UiUtils.initialize(cbObjectType, aaa);
			validator.applyValidator(cbObjectType, "cbObjectType", DecoratorType.REQUIRED_FIELD, true);
		
		Label a = new Label(group, 0);
		add(a, Percent.W100);
				
		separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		//-------CONSULTAR----------------
		compoSearch = new Composite(parent, SWT.NORMAL);
		addComposite(compoSearch);
		compoSearch.setData(RWT.CUSTOM_VARIANT, "gray_background");
	
		lblSearch = new Label(compoSearch, 0);
		FormDatas.attach(lblSearch).atTopTo(compoSearch, 19).atLeft(15);
		
		
		txtSearch = new Text(compoSearch, SWT.NORMAL);
		FormDatas.attach(txtSearch).atTopTo(compoSearch, 15).atLeftTo(lblSearch, 10).withHeight(12).withWidth(260);
		
		consult = new Button(compoSearch, SWT.PUSH);
		FormDatas.attach(consult).atLeftTo(txtSearch, 10).atTopTo(compoSearch, 15).withHeight(22);
		
		
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
		
		reset();
		add(new Label(compoSearch, 0),Percent.W100);
		
		list = new Label(compoSearch, SWT.NORMAL);
		addHeader(list);
		list.setVisible(false);

		consult.addSelectionListener(new SelectionAdapter() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
			
				params = (txtSearch.getText().length() > 0 ? txtSearch.getText() : null);
	
				pendingDesiderataTable.clearRows();
				pendingDesiderataTable.destroyEditableArea();
				list.setVisible(true);
				pendingDesiderataTable.setVisible(true);
				
				list2.setVisible(true);
				associatedDesiderataTable.setVisible(true);
				
				if (compoButtons!=null) {
					compoButtons.setVisible(true);					
				}
				
				refresh();
				
				searchCurrentPendingDesiderataTable(0, pendingDesiderataTable.getPageSize());
				if (pendingDesiderataTable.getRows().isEmpty()) 					
				{
					if (associatedDesiderataTable.getRows().isEmpty()) {						
					
					list2.setVisible(false);
					associatedDesiderataTable.setVisible(false);	
					if (compoButtons!=null) {
							compoButtons.setVisible(false);
					}
				
					}
					RetroalimentationUtils.showInformationMessage(compoSearch, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				}
				else					
					pendingDesiderataTable.getPaginator().goToFirstPage();
			}				
		});

		//--------------------PENDING ORDERS TABLE--------------------------------------
		pendingDesiderataTable = new CRUDTreeTable(compoSearch,SWT.NONE);
		add(pendingDesiderataTable);
		pendingDesiderataTable.setVisible(false);
		pendingDesiderataTable.setEntityClass(Desiderata.class);
				

		TreeTableColumn columns[] = {
				new TreeTableColumn(20, 0, "getTitle"),
				new TreeTableColumn(20, 1, "getAuthor"),
				new TreeTableColumn(20, 2, "getQuantity"),
				new TreeTableColumn(20, 3, "getPrice"),
				new TreeTableColumn(20, 4, "getCreationDate")};
		 
		pendingDesiderataTable.addListener(SWT.Resize, new Listener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();				
			}
		});

		//----------------------------------FIN TABLA PEDIDOS PENDIENTES-------------------------

		list2 = new Label(compoSearch, SWT.NORMAL);
		list2.setVisible(false);
		addHeader(list2);

		//------------TABLA DE PEDIDOS ASOCIADOS A LA ORDEN DE COMPRA-----------------------------------
		associatedDesiderataTable = new CRUDTreeTable(compoSearch,SWT.NONE);
		add(associatedDesiderataTable);
		associatedDesiderataTable.setVisible(false);
		associatedDesiderataTable.setEntityClass(Desiderata.class);
		associatedDesiderataTable.setDelete(true);
		
		Column checked = new Column("right-arrow", compoSearch.getDisplay(), new TreeColumnListener() {
			
			@Override
			public void handleEvent(TreeColumnEvent event) {
				
				Desiderata desiderataAssociate = (Desiderata) event.entity.getRow();
					
				searchCurrentPendingDesiderataTable(0, pendingDesiderataTable.getPageSize());
				
				temp.remove(desiderataAssociate);
				desiderataAssociatedList.add(desiderataAssociate);
				
				
				searchCurrentPendingDesiderataTable(0, pendingDesiderataTable.getPageSize());
				pendingDesiderataTable.getPaginator().goToFirstPage();
				pendingDesiderataTable.refresh();

				searchAssociatedDesiderataTable(0, associatedDesiderataTable.getPageSize());
				associatedDesiderataTable.getPaginator().goToFirstPage();
				refresh();
				
			}
		});
		
		
		checked.setToolTipText(AbosMessages.get().BUTTON_ASSOCIATE_SELECTION);
		checked.setAlignment(SWT.CENTER);
		pendingDesiderataTable.addActionColumn(checked);
		pendingDesiderataTable.createTable(columns); 
		pendingDesiderataTable.getPaginator().setPageSize(10);
		
		TreeTableColumn columns1[] = {
				new TreeTableColumn(20, 0, "getTitle"),
				new TreeTableColumn(20, 1, "getAuthor"),
				new TreeTableColumn(20, 2, "getQuantity"),
				new TreeTableColumn(20, 3, "getPrice"),
				new TreeTableColumn(20, 4, "getCreationDate")};
		associatedDesiderataTable.createTable(columns1);  
		associatedDesiderataTable.setPageSize(10);
		
		associatedDesiderataTable.addListener(SWT.Resize, new Listener() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();				
			}
		});
	
		pendingDesiderataTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
				}   
				searchCurrentPendingDesiderataTable(event.currentPage - 1, event.pageSize);
			}
		}); 

		associatedDesiderataTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
				}   
				searchAssociatedDesiderataTable(event.currentPage - 1, event.pageSize);
			}
		}); 
		
		//----------------------------------FIN TABLA PEDIDOS ASOCIADOS-------------------------------------

		associatedDesiderataTable.addDeleteListener(new TreeColumnListener() {
			
			@Override
			public void handleEvent(TreeColumnEvent event) {
				Desiderata desiderataEliminate = (Desiderata) event.entity.getRow();
				
				//searchCurrentPendingDesiderataTable(0, pendingDesiderataTable.getPageSize());
				
				temp.add(desiderataEliminate);
				desiderataAssociatedList.remove(desiderataEliminate);
			
				searchCurrentPendingDesiderataTable(0, pendingDesiderataTable.getPageSize());
				pendingDesiderataTable.getPaginator().goToFirstPage();

				searchAssociatedDesiderataTable(0, associatedDesiderataTable.getPageSize());
				associatedDesiderataTable.getPaginator().goToFirstPage();
				refresh();
				
			}
		});
			
		
		associatedDesiderataTable.getPaginator().goToFirstPage();;

		l10n();
		loadPurchaseRequestData();
		
		return parent;
	}

	public List<Desiderata> getDesiderataAssociatedList() {
		return desiderataAssociatedList;
	}

	public void setDesiderataAssociatedList(
			List<Desiderata> desiderataAssociatedList) {
		this.desiderataAssociatedList = desiderataAssociatedList;
	}

	public void l10n() {
		lbRegister.setText(aux);
		lblApplicationNumber.setText(MessageUtil.unescape(AbosMessages.get().LABEL_APPLICATION_NUMBER));
		lblObjectType.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_TYPE));
		associatedDesiderataTable.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR),
				MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY),
				MessageUtil.unescape(AbosMessages.get().TABLE_PRICE),
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE)));

		pendingDesiderataTable.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR),
				MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY),
				MessageUtil.unescape(AbosMessages.get().TABLE_PRICE),
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE)));

		
		consult.setText(AbosMessages.get().BUTTON_SEARCH);
		list.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_PENDING_DESIDERATAS));
		list2.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_ASSOCIATED_DESIDERATAS));
		lblSearch.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_DESIDERATAS_BY));
		txtSearch.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR) + " | "
				+ MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
		refresh();
	}
	

	//Buscar los pedidos pendientes
	public void searchCurrentPendingDesiderataTable(int page, int size) {
		listDB = ((AllManagementController) controller).findDesideratasByPurchaseRequest(((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.DESIDERATA_STATE_PENDING),params,page, 20,direction, orderByStringDesiderata);
			
		if (temp.isEmpty()) {
			temp.addAll(listDB.getContent());
		}
			
		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0; j < desiderataAssociatedList.size(); j++) {
				try {
					if (temp.get(i).getDesidertaID().equals(desiderataAssociatedList.get(j).getDesidertaID())) {
						
						temp.remove(i--);
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}			
		}
		//temp.removeAll(desiderataAssociatedList);       	
	
		pendingDesiderataTable.clearRows();				
		pendingDesiderataTable.setTotalElements((int) temp.size());
		
		if (temp.size() <= page * size + size) {
			pendingDesiderataTable.setRows(temp.subList(page * size, temp.size()));
		} else {
			pendingDesiderataTable.setRows(temp.subList(page * size, page * size + size));
		}	
		
		pendingDesiderataTable.refresh();
		associatedDesiderataTable.refresh();
	}
	
	public void searchAssociatedDesiderataTable(int page, int size) {
			
		associatedDesiderataTable.clearRows();
		associatedDesiderataTable.setTotalElements(desiderataAssociatedList.size());
		if (desiderataAssociatedList.size() <= page * size + size) {
			associatedDesiderataTable.setRows(desiderataAssociatedList.subList(page * size, desiderataAssociatedList.size()));
		} else {
			associatedDesiderataTable.setRows(desiderataAssociatedList.subList(page * size, page * size + size));
		}	
		
		associatedDesiderataTable.refresh();
		pendingDesiderataTable.refresh();
	}
	
	public void loadPurchaseRequestData() {
		if (purchaseRequest != null) {
			txtApplicationNumber.setText(purchaseRequest.getRequestNumber());
			if (cbObjectType.getSelectionIndex()== 1) {
				cbObjectType.select(1);
			}else
				cbObjectType.select(2);
	
						
			if (desiderataAssociatedList.size()>0) {
					
				 list2.setVisible(true);
				 associatedDesiderataTable.setVisible(true);
				 
				 list.setVisible(true);
				 pendingDesiderataTable.setVisible(true);
				
								  
				  searchCurrentPendingDesiderataTable(page, size);
				  searchAssociatedDesiderataTable(page, size);
				  
				  pendingDesiderataTable.getPaginator().goToFirstPage();
				  associatedDesiderataTable.getPaginator().goToFirstPage();
			}
		}
	}
	

	
	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
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

	public CRUDTreeTable getAssociatedDesiderataTable() {
		return associatedDesiderataTable;
	}

	public void setAssociatedDesiderataTable(CRUDTreeTable associatedDesiderataTable) {
		this.associatedDesiderataTable = associatedDesiderataTable;
	}
	
	public void limpiarCampos() {
		if (validator.decorationFactory.AllControlDecorationsHide()) {
			cbObjectType.select(0);
			txtApplicationNumber.setText("");
		}
	}

	public List<Desiderata> getDesiderataList() {
		return desiderataList;
	}

	public void setDesiderataList(List<Desiderata> desiderataList) {
		this.desiderataList = desiderataList;
	}
	
	
}
