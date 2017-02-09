package cu.uci.abcd.acquisition.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.acquisition.ui.updateArea.EditPurchaseOrderArea;
import cu.uci.abcd.acquisition.ui.updateArea.SetPurchaseOrderArea;
import cu.uci.abcd.acquisition.ui.updateArea.ViewPurchaseOrderArea;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.domain.Row;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.IActionDenied;
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConsultPurchaseOrder extends ContributorPage implements Contributor {

	private CRUDTreeTable purchaseOrderTable;
	private List<String> searchC = new ArrayList<>();
	private Label lbConsult;
	private Label criteria;
	private Label lblIdentifier;
	private Label lblProvider;
	private Label lblSince;
	private Label lblUntil;
	private Label lblCriteria;
	private Label lblTotalAmount;
	private Label list;
	private Label separator;
	private Label rangeDateRegister;
	private DateTime dtSince;
	private DateTime dtUntil;

	private Combo cbCriteria;
	private Combo cbProvider;

	private Text txtIdentifier;
	private Text quantity;

	private Button newSearch;
	private Button consult;
	private Composite compoButton;

	private String identifier = null;
	private Double totalMount = null;
	private Integer index = null;
	private Provider provedor = null;
	private Worker creator = null;
	private Date since = null;
	private Date until = null;
	private Nomenclator state = null;

	private Label lblCreator;
	private Label lblState;
	private Combo cbCreator;
	private Combo cbState;

	private int page = 0;
	private int size = 10;
	private static String purchaseOrderByString = "purchaseOrderID";
	private static int direction = 1024;
	private Library library;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().CONTRIBUTOR_CONSULT_PURCHASE_ORDERS);
	}

	public int order() {
		return 0;
	}

	@Override
	public String getID() {
		return "consultPurchaseOrderID";
	}

	public String containerMenu() {
		return "Adquisición";
	}

	@Override
	public void l10n() {
		lbConsult.setText(MessageUtil.unescape(AbosMessages.get().CONSULT_PURCHASE_ORDERS));
		criteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		lblIdentifier.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ORDER_NUMBER));
		lblProvider.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PROVIDER));
		lblSince.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SINCE));
		lblUntil.setText(MessageUtil.unescape(AbosMessages.get().LABEL_UNTIL));
		lblCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_AMOUNT_RANGE));
		lblTotalAmount.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_AMOUNT));
		rangeDateRegister.setText(AbosMessages.get().LABEL_CREATION_DATE_RANGE);
		lblCreator.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY));
		lblState.setText(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));

		initialize(cbCreator, ((AllManagementController) controller).getPurchaseRequest().getWorkers());
		initialize(cbState, ((AllManagementController) controller).getSuggestion().findAllNomenclators(library.getLibraryID(), AdquisitionNomenclator.PURCHASEORDER_STATE));
		initialize(cbProvider, ((AllManagementController) controller).getPurchaseOrder().findAllProviders());

		consult.setText(AbosMessages.get().BUTTON_CONSULT);
		newSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		list.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COINCIDENCE_LIST));
		purchaseOrderTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_ORDER_NUMBER), MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY),
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE), MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_AMOUNT), MessageUtil.unescape(AbosMessages.get().LABEL_STATE)));

		purchaseOrderTable.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		purchaseOrderTable.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		purchaseOrderTable.l10n();
		purchaseOrderTable.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);
		
		refresh();
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
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

		Composite group = new Composite(parent, SWT.NONE);
		addComposite(group);

		group.setData(RWT.CUSTOM_VARIANT, "gray_background");
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite resize = new Composite(parent, 0);
		resize.setVisible(true);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 160);
	
		lbConsult = new Label(group, SWT.NONE);
		addHeader(lbConsult);

		criteria = new Label(group, SWT.NONE);
		addHeader(criteria);

		lblIdentifier = new Label(group, SWT.NONE);
		add(lblIdentifier);

		txtIdentifier = new Text(group, SWT.NONE);
		add(txtIdentifier);
		//validator.applyValidator(txtIdentifier, "txtIdentifier", DecoratorType.NUMBER_ONLY, true);

		lblProvider = new Label(group, SWT.NONE);
		add(lblProvider);

		cbProvider = new Combo(group, SWT.READ_ONLY);
		add(cbProvider);

		br();

		lblCreator = new Label(group, SWT.NONE);
		add(lblCreator);

		cbCreator = new Combo(group, SWT.READ_ONLY);
		add(cbCreator);

		lblState = new Label(group, SWT.NONE);
		add(lblState);

		cbState = new Combo(group, SWT.READ_ONLY);
		add(cbState);

		br();
		rangeDateRegister = new Label(group, SWT.NONE);
		addHeader(rangeDateRegister);
		br();

		lblSince = new Label(group, SWT.NONE);
		add(lblSince);

		dtSince = new DateTime(group, SWT.BORDER | SWT.DROP_DOWN);
		add(dtSince);

		Auxiliary.goDateTimeToBeforeOneMonth(dtSince);

		lblUntil = new Label(group, SWT.NONE);
		add(lblUntil);

		dtUntil = new DateTime(group, SWT.BORDER | SWT.DROP_DOWN);
		add(dtUntil);

		br();

		lblCriteria = new Label(group, SWT.NONE);
		add(lblCriteria);

		cbCriteria = new Combo(group, SWT.READ_ONLY);
		add(cbCriteria);
		cbCriteria.add("-seleccione-");
		cbCriteria.add(MessageUtil.unescape(AbosMessages.get().LABEL_GREAT_THAN));
		cbCriteria.add(MessageUtil.unescape(AbosMessages.get().LABEL_LESS_THAN));
		cbCriteria.add(MessageUtil.unescape(AbosMessages.get().LABEL_EQUALS_TO));
		cbCriteria.select(0);

		lblTotalAmount = new Label(group, SWT.NONE);
		add(lblTotalAmount);
		lblTotalAmount.setVisible(false);

		quantity = new Text(group, SWT.NONE);
		add(quantity);
		quantity.setVisible(false);
	
		br();

		newSearch = new Button(group, SWT.PUSH);
		add(newSearch);
		consult = new Button(group, SWT.PUSH);
		add(consult);

		br();
		separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		compoButton = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton);
		compoButton.setData(RWT.CUSTOM_VARIANT, "gray_background");

		list = new Label(compoButton, SWT.NONE);
		addHeader(list);
		list.setVisible(false);

		// ----------------TABLA DE ORDEN DE COMPRA-------------------

		purchaseOrderTable = new CRUDTreeTable(compoButton, SWT.NONE);
		add(purchaseOrderTable);
		purchaseOrderTable.setEntityClass(PurchaseOrder.class);
		purchaseOrderTable.setVisible(false);

		Column columnAccept = new Column("accept", parent.getDisplay(),
				new SetPurchaseOrderArea(MessageUtil.unescape(AbosMessages
						.get().BUTTON_ACCEPT), controller, purchaseOrderTable,
						this), new TreeColumnListener(){
			public void handleEvent(TreeColumnEvent event) {
			}
		});
		columnAccept.setToolTipText(AbosMessages.get().BUTTON_APPROVE);
		columnAccept.setAlignment(SWT.CENTER);			
		purchaseOrderTable.addActionColumn(columnAccept);
		
		
		// REJECT SUGGESTION BUTTON
		Column columnReject = new Column("reject", parent.getDisplay(),
				new SetPurchaseOrderArea(MessageUtil.unescape(AbosMessages
						.get().BUTTON_REJECT), controller, purchaseOrderTable,
						this), new TreeColumnListener(){
			public void handleEvent(TreeColumnEvent event) {
			}
		});
		columnReject.setToolTipText(AbosMessages.get().BUTTON_REJECT);
		columnReject.setAlignment(SWT.CENTER);			
		purchaseOrderTable.addActionColumn(columnReject);
		
		purchaseOrderTable.setWatch(true, new ViewPurchaseOrderArea(controller));
		purchaseOrderTable.setUpdate(true, new EditPurchaseOrderArea(controller, purchaseOrderTable, this));
		purchaseOrderTable.setDelete(true);
		purchaseOrderTable.setVisualEntityManager(new MessageVisualEntityManager(purchaseOrderTable));

		// export pdf and excel 
		CRUDTreeTableUtils.configReports(purchaseOrderTable, MessageUtil.unescape(AbosMessages.get().CONSULT_PURCHASE_ORDERS), searchC);

		TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getOrderNumber"), new TreeTableColumn(20, 1, "getCreator.getPerson.getFullName"), new TreeTableColumn(20, 2, "getCreationDate"),
				new TreeTableColumn(20, 3, "getTotalAmount"), new TreeTableColumn(20, 4, "getState") };
		purchaseOrderTable.createTable(columns);

		CRUDTreeTableUtils.configRemove(purchaseOrderTable, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				PurchaseOrder p = (PurchaseOrder) event.entity.getRow();
				((AllManagementController) controller).getPurchaseOrder().deletePurchaseOrder(p.getPurchaseOrderID());
				searchCurrentpurchaseOrderTable(page, size);
			}
		});

		purchaseOrderTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				
				searchCurrentpurchaseOrderTable(event.currentPage - 1, event.pageSize);
			}
		});
		
		purchaseOrderTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});		
		
		purchaseOrderTable.getPaginator().setPageSize(10);

		// ----------------END PURCHASE ORDER TABLE-----------------

		
		consult.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (quantity.getText().length() != 0 && !isNumeric(quantity.getText())) {
						list.setVisible(false);
						purchaseOrderTable.setVisible(false);
						purchaseOrderTable.clearRows();
						RetroalimentationUtils.showInformationMessage(compoButton, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					
						
				}else{
					if (cbCriteria.getSelectionIndex() != 0 && quantity.getText().length() == 0) {
						list.setVisible(false);
						purchaseOrderTable.setVisible(false);
						quantity.setFocus();
						RetroalimentationUtils.showInformationMessage(AbosMessages.get().MESSAGES_SPECIFY_TOTAL_AMOUNT);
					}
					else{
					since = new java.sql.Date(dtSince.getYear() - 1900, dtSince.getMonth(), dtSince.getDay());
					until = new java.sql.Date(dtUntil.getYear() - 1900, dtUntil.getMonth(), dtUntil.getDay());

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String sinceString = sdf.format(since);
					String untilString = sdf.format(until);
					
						list.setVisible(true);
						purchaseOrderTable.setVisible(true);
						searchC.clear();

						if (txtIdentifier.getText().length() == 0) {
							identifier = null;
						} else {
							identifier =txtIdentifier.getText();
							searchC.add(lblIdentifier.getText());
							searchC.add(txtIdentifier.getText());
						}

						if (quantity.getText().length() == 0) {
							totalMount = null;
						} else {   
							totalMount = Double.parseDouble(quantity.getText());
							searchC.add(lblTotalAmount.getText());
							searchC.add(quantity.getText());
						}

						if (UiUtils.getSelected(cbProvider) == null) {
							provedor = null;
						} else {
							provedor = (Provider) UiUtils.getSelected(cbProvider);
							searchC.add(lblProvider.getText());
							searchC.add(cbProvider.getText());
						}

						if (cbCriteria.getSelectionIndex() != 0 && quantity.getText().length() == 0) {
							list.setVisible(false);
							purchaseOrderTable.setVisible(false);
							quantity.setFocus();
							RetroalimentationUtils.showInformationMessage(AbosMessages.get().MESSAGES_SPECIFY_TOTAL_AMOUNT);
						} else if (cbCriteria.getSelectionIndex() == 1) {
							index = 1;
						} else if (cbCriteria.getSelectionIndex() == 2) {
							index = 2;
						} else if (cbCriteria.getSelectionIndex() == 3)
							index = 3;
						else
							index = null;

						if (UiUtils.getSelected(cbCreator) == null) {
							creator = null;
						} else {
							creator = (Worker) UiUtils.getSelected(cbCreator);
							searchC.add(lblCreator.getText());
							searchC.add(cbCreator.getText());
						}

						if (UiUtils.getSelected(cbState) == null) {
							state = null;
						} else {
							state = (Nomenclator) UiUtils.getSelected(cbState);
							searchC.add(lblState.getText());
							searchC.add(cbState.getText());
						}

						searchC.add(lblSince.getText());
						searchC.add(sinceString);
						searchC.add(lblUntil.getText());
						searchC.add(untilString);

						searchCurrentpurchaseOrderTable(0, purchaseOrderTable.getPageSize());
						purchaseOrderTable.getPaginator().goToFirstPage();
						if (purchaseOrderTable.getRows().isEmpty()) {
							list.setVisible(false);
							purchaseOrderTable.setVisible(false);
							RetroalimentationUtils.showInformationMessage(compoButton, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
						}
						
						refresh();
				}
			}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		// Realizar una nueva busqueda
		newSearch.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				cbProvider.select(0);
				cbCreator.select(0);
				cbState.select(0);
				txtIdentifier.setText("");
				quantity.setText("");
				cbCriteria.select(0);

				purchaseOrderTable.clearRows();
				list.setVisible(false);
				purchaseOrderTable.setVisible(false);
				purchaseOrderTable.destroyEditableArea();
				
				Auxiliary.goDateTimeToBeforeOneMonth(dtSince);
				Auxiliary.goDateTimeToToday(dtUntil); 
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		cbCriteria.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (cbCriteria.getSelectionIndex() == 0) {
					lblTotalAmount.setVisible(false);
					quantity.setText("");
					quantity.setVisible(false);
				} else {
					lblTotalAmount.setVisible(true);
					quantity.setVisible(true);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		purchaseOrderTable.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {
			
				User user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
				PurchaseOrder purchaseOrder = (PurchaseOrder) row;
				
				if (column.getIndex() ==5) {
					if (user.getPerson() == null ) {			
						return true;
					}
					else if (!purchaseOrder.getState().getNomenclatorID().equals(Nomenclator.PURCHASEORDER_STATE_PENDING)) {
						return true;
					}
					else{
						  Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(user.getPerson().getPersonID());
						     if ( workerLoggin==null) {
							return true;
						}
					}
				}
				if (column.getIndex() == 6) {
					if (user.getPerson() == null ) {			
						return true;
					}
					else if (!purchaseOrder.getState().getNomenclatorID().equals(Nomenclator.PURCHASEORDER_STATE_PENDING)) {
						return true;
					}
					else{
						  Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(user.getPerson().getPersonID());
						     if ( workerLoggin==null) {
							return true;
						}
					}
				}
				if (column.getIndex() == 8) {
					if (user.getPerson() == null ) {			
						return true;
					}					
					else{
						  Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(user.getPerson().getPersonID());
						     if ( workerLoggin==null) {
							return true;
						}
					}
				}
				if (column.getIndex() == 9) {
					if (user.getPerson() == null ) {			
						return true;
					}
					else{
						  Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(user.getPerson().getPersonID());
						     if ( workerLoggin==null) {
							return true;
						}
					}
				}
				return false;
			}
		});
		l10n();

		return parent;
	}    

	public void searchCurrentpurchaseOrderTable(int page, int size) {

		purchaseOrderTable.clearRows();
		Page<PurchaseOrder> list = ((AllManagementController) controller).searchConsultPurchaseOrderByParameters(since, until, provedor, index, totalMount, creator, state, identifier,library, page, size,
				direction, purchaseOrderByString);
		purchaseOrderTable.setTotalElements((int) list.getTotalElements());
		purchaseOrderTable.setRows(list.getContent());
		purchaseOrderTable.refresh();

	}
	
    private static boolean isNumeric(String cadena){
    	try {
    		Double.parseDouble(cadena);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }

}
