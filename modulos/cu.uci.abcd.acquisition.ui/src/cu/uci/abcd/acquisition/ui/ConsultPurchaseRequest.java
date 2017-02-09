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
import cu.uci.abcd.acquisition.ui.updateArea.EditPurchaseRequestArea;
import cu.uci.abcd.acquisition.ui.updateArea.SetPurchaseRequestArea;
import cu.uci.abcd.acquisition.ui.updateArea.ViewPurchaseRequestArea;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
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

public class ConsultPurchaseRequest extends ContributorPage implements Contributor {

	private CRUDTreeTable purchaseRequestTable;
	private List<String> searchC = new ArrayList<>();

	private int page = 0;
	private int size = 10;
	private static String purchaseRequestByString = "purchaseRequestID";
	private static int direction = 1024;

	private Label criteria;
	private Label lblCreator;
	private Label lblRequestNumber;
	private Label listAssociatedSuggestion;
	private Label lblSince;
	private Label lblUntil;
	private Label separator;
	private Label rangeDateRegister;
	private Text txtRequestNumber;
	private Combo cbCreator;
	private Label lblState;
	private Combo cbState;
	private Button newSearch;
	private Button consult;

	private DateTime dt_since;
	private DateTime dt_until;
	private Label lbConsult;
	private Composite compoButton;
	private java.sql.Date since = null;
	private java.sql.Date until = null;
	private String requestNumber = null;
	private Worker creator = null;
	private Nomenclator state;
	private Library library;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().CONTRIBUTOR_CONSULT_PURCHASE_REQUEST);
	}

	public int order() {
		return 0;
	}

	@Override
	public String getID() {
		return "consultPurchaseRequestID";
	}

	public String containerMenu() {
		return "Adquisición";
	}

	@Override
	public void l10n() { 
		lblState.setText(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		lbConsult.setText(MessageUtil.unescape(AbosMessages.get().CONSULT_PURCHASE_REQUEST));
		criteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		lblCreator.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY));
		lblRequestNumber.setText(MessageUtil.unescape(AbosMessages.get().LABEL_APPLICATION_NUMBER));
		rangeDateRegister.setText(AbosMessages.get().LABEL_CREATION_DATE_RANGE);
		lblSince.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SINCE));
		lblUntil.setText(MessageUtil.unescape(AbosMessages.get().LABEL_UNTIL));
		newSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		consult.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		listAssociatedSuggestion.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COINCIDENCE_LIST));

		purchaseRequestTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_APPLICATION_NUMBER), MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY),
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_REGISTRATION), MessageUtil.unescape(AbosMessages.get().LABEL_STATE)));

		initialize(cbCreator, ((AllManagementController) controller).getPurchaseRequest().getWorkers());
		initialize(cbState, ((AllManagementController) controller).getSuggestion().findAllNomenclators(library.getLibraryID(), AdquisitionNomenclator.PURCHASEREQUEST_STATE));

		purchaseRequestTable.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		purchaseRequestTable.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		purchaseRequestTable.l10n();
		purchaseRequestTable.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);
		
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
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height-100);
	
		lbConsult = new Label(group, SWT.NONE);
		addHeader(lbConsult);

		criteria = new Label(group, SWT.NONE);
		addHeader(criteria);

		lblRequestNumber = new Label(group, SWT.NONE);
		add(lblRequestNumber);

		txtRequestNumber = new Text(group, SWT.NONE);
		add(txtRequestNumber);
		
		lblCreator = new Label(group, SWT.NONE);
		add(lblCreator);

		cbCreator = new Combo(group, SWT.READ_ONLY);
		add(cbCreator);

		br();
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

		dt_since = new DateTime(group, SWT.BORDER | SWT.DROP_DOWN);
		add(dt_since);

		Auxiliary.goDateTimeToBeforeOneMonth(dt_since);

		lblUntil = new Label(group, SWT.NONE);
		add(lblUntil);

		dt_until = new DateTime(group, SWT.BORDER | SWT.DROP_DOWN);
		add(dt_until);
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

		listAssociatedSuggestion = new Label(compoButton, SWT.NONE);
		addHeader(listAssociatedSuggestion);
		listAssociatedSuggestion.setVisible(false);
		// ----------------TABLA DE ORDEN DE COMPRA-------------------

		purchaseRequestTable = new CRUDTreeTable(compoButton, SWT.NONE);
		add(purchaseRequestTable);
		purchaseRequestTable.setEntityClass(PurchaseRequest.class);
		
		Column columnAccept = new Column("accept", parent.getDisplay(),
				new SetPurchaseRequestArea(MessageUtil.unescape(AbosMessages
						.get().BUTTON_ACCEPT), controller, purchaseRequestTable,
						this), new TreeColumnListener(){
			public void handleEvent(TreeColumnEvent event) {
			}
		});
		columnAccept.setToolTipText(AbosMessages.get().BUTTON_APPROVE);
		columnAccept.setAlignment(SWT.CENTER);			
		purchaseRequestTable.addActionColumn(columnAccept);
		
		
		// REJECT SUGGESTION BUTTON
		Column columnReject = new Column("reject", parent.getDisplay(),
				new SetPurchaseRequestArea(MessageUtil.unescape(AbosMessages
						.get().BUTTON_REJECT), controller, purchaseRequestTable,
						this), new TreeColumnListener(){
			public void handleEvent(TreeColumnEvent event) {
			}
		});
		columnReject.setToolTipText(AbosMessages.get().BUTTON_REJECT);
		columnReject.setAlignment(SWT.CENTER);			
		purchaseRequestTable.addActionColumn(columnReject);
		
		
		purchaseRequestTable.setVisible(false);

		purchaseRequestTable.setWatch(true, new ViewPurchaseRequestArea(controller));
		purchaseRequestTable.setUpdate(true, new EditPurchaseRequestArea(controller, purchaseRequestTable, this));
		purchaseRequestTable.setDelete(true);
		purchaseRequestTable.setVisualEntityManager(new MessageVisualEntityManager(purchaseRequestTable));
		// export pdf and excel
		CRUDTreeTableUtils.configReports(purchaseRequestTable, MessageUtil.unescape(AbosMessages.get().CONSULT_PURCHASE_REQUEST), searchC);

		TreeTableColumn columns[] = { new TreeTableColumn(25, 0, "getRequestNumber"), new TreeTableColumn(25, 1, "getCreator.getPerson.getFullName"), new TreeTableColumn(25, 2, "getCreationDate"),
				new TreeTableColumn(25, 3, "getState") };
		purchaseRequestTable.createTable(columns);

		CRUDTreeTableUtils.configRemove(purchaseRequestTable, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				PurchaseRequest purchaseRequest = (PurchaseRequest) event.entity.getRow();
			
				((AllManagementController) controller).getPurchaseRequest().deletePurchaseRequest(purchaseRequest.getPurchaseRequestID());

			}
		});

		purchaseRequestTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
				}
				searchCurrentPurchaseDesideratasTable(event.currentPage - 1, event.pageSize);
			}
		});

		purchaseRequestTable.setPageSize(10);

	
		purchaseRequestTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});	
		
		// ----------------END PURCHASE ORDER TABLE-----------------

		
		// Realizar la consulta de las sugerencias por titulo, autor y tipo User
		consult.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent arg0) {

					since = new java.sql.Date(dt_since.getYear() - 1900, dt_since.getMonth(), dt_since.getDay());
					until = new java.sql.Date(dt_until.getYear() - 1900, dt_until.getMonth(), dt_until.getDay());

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

					String sinceString = sdf.format(since);
					String untilString = sdf.format(until);

					
						searchC.clear();
						listAssociatedSuggestion.setVisible(true);
						purchaseRequestTable.setVisible(true);

						if (txtRequestNumber.getText().length() == 0) {
							requestNumber = null;
						} else {
							requestNumber = txtRequestNumber.getText();
							searchC.add(lblRequestNumber.getText());
							searchC.add(txtRequestNumber.getText());
						}

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

						searchCurrentPurchaseDesideratasTable(0, purchaseRequestTable.getPageSize());
						purchaseRequestTable.getPaginator().goToFirstPage();
						
						if (purchaseRequestTable.getRows().isEmpty()) {
							listAssociatedSuggestion.setVisible(false);
							purchaseRequestTable.setVisible(false);
							RetroalimentationUtils.showInformationMessage(compoButton, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
						}
						refresh();
					
				
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
				purchaseRequestTable.destroyEditableArea();
				searchCurrentPurchaseDesideratasTable(page, size);
				txtRequestNumber.setText("");
				listAssociatedSuggestion.setVisible(false);
				purchaseRequestTable.setVisible(false);
				cbCreator.select(0);
				cbState.select(0);
				
				Auxiliary.goDateTimeToBeforeOneMonth(dt_since);
				Auxiliary.goDateTimeToToday(dt_until);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		
		purchaseRequestTable.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {
			
				User user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
				PurchaseRequest purchaseRequest = (PurchaseRequest) row;
				
				if (column.getIndex() ==4) {
					if (user.getPerson() == null ) {			
						return true;
					}
					else if (!purchaseRequest.getState().getNomenclatorID().equals(Nomenclator.PURCHASEREQUEST_STATE_PENDING)) {
						return true;
					}
					else{
						  Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(user.getPerson().getPersonID());
						     if ( workerLoggin==null) {
							return true;
						}
					}
				}
				if (column.getIndex() == 5) {
					if (user.getPerson() == null ) {			
						return true;
					}
					else if (!purchaseRequest.getState().getNomenclatorID().equals(Nomenclator.PURCHASEREQUEST_STATE_PENDING)) {
						return true;
					}
					else{
						  Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(user.getPerson().getPersonID());
						     if ( workerLoggin==null) {
							return true;
						}
					}
				}
				if (column.getIndex() == 7) {
					if (user.getPerson() == null ) {			
						return true;
					}
					else if (!purchaseRequest.getState().getNomenclatorID().equals(Nomenclator.PURCHASEREQUEST_STATE_PENDING)) {
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

	// Dibujar tabla
	public void searchCurrentPurchaseDesideratasTable(int page, int size) {

		purchaseRequestTable.clearRows();
		Page<PurchaseRequest> list = ((AllManagementController) controller).findAllPurchaseRequestByParameters(since, until, creator, requestNumber, state,library, page, size, direction,
				purchaseRequestByString);
		purchaseRequestTable.setTotalElements((int) list.getTotalElements());

		purchaseRequestTable.setRows(list.getContent());
		purchaseRequestTable.refresh();
		
		refresh();
	}
}
