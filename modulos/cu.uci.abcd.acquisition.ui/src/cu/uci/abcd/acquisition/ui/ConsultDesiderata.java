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
import cu.uci.abcd.acquisition.ui.updateArea.EditDesiderataArea;
import cu.uci.abcd.acquisition.ui.updateArea.ViewDesiderataArea;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abcd.acquisition.ui.Auxiliary;
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
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConsultDesiderata extends ContributorPage {

	private CRUDTreeTable pendingOrderTable;
	private List<String> searchC = new ArrayList<>();
	private static String orderByStringOrder = "desiderataID";
	private static int direction = 1024;

	private Label criteria;
	private Label lblTitle;
	private Label lblAuthor;
	private Label lblEditor;
	private Label lblSince;
	private Label lblUntil;
	private Label separator;
	private Label lblList;
	private Label lbState;
	private Label rangeDateRegister;

	private DateTime dt_since;
	private DateTime dt_until;

	private Text txtTitle;
	private Text txtAuthor;
	private Text txtEditor;

	private Combo comboState;

	private Button newSearch;
	private Button consult;

	private Label lbConsult;
	private Composite compoButton;

	private String title = null;
	private String author = null;
	private String editor = null;
	private Date dateRegister = null;
	private Date endDateRegister = null;
	private Nomenclator stateDesiderata = null;
	private Library library;

	@Override
	public String contributorName() {

		return MessageUtil.unescape(AbosMessages.get().CONTRIBUTOR_CONSULT_DESIDERATA);
	}

	public int order() {

		return 0;
	}

	@Override
	public String getID() {

		return "consultDesiderataID";
	}

	public String containerMenu() {

		return "Adquisición";
	}

	@Override
	public void l10n() {
		lbConsult.setText(MessageUtil.unescape(AbosMessages.get().CONSULT_DESIDERATA));
		criteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		lblTitle.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		lblAuthor.setText(MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR));
		lblEditor.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
		lblSince.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SINCE));
		lblUntil.setText(MessageUtil.unescape(AbosMessages.get().LABEL_UNTIL));
		lbState.setText(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		newSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		rangeDateRegister.setText(AbosMessages.get().LABEL_CREATION_DATE_RANGE);
		consult.setText(AbosMessages.get().BUTTON_CONSULT);
		lblList.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COINCIDENCE_LIST));

		pendingOrderTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR), MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL), MessageUtil.unescape(AbosMessages.get().LABEL_DATE),
				MessageUtil.unescape(AbosMessages.get().LABEL_STATE)));

		initialize(comboState, ((AllManagementController) controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.DESIDERATA_STATE));

		pendingOrderTable.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		pendingOrderTable.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		pendingOrderTable.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);
		pendingOrderTable.l10n();
	}

	@Override
	public Control createUIControl(final Composite parent) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

		final Composite group = new Composite(parent, SWT.NONE);
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

		lblTitle = new Label(group, SWT.NONE);
		add(lblTitle);

		txtTitle = new Text(group, SWT.NONE);
		add(txtTitle);

		lblAuthor = new Label(group, SWT.NONE);
		add(lblAuthor);

		txtAuthor = new Text(group, SWT.NONE);
		add(txtAuthor);

		br();

		lblEditor = new Label(group, SWT.NONE);
		add(lblEditor);

		txtEditor = new Text(group, SWT.NONE);
		add(txtEditor);

		lbState = new Label(group, SWT.NONE);
		add(lbState);

		comboState = new Combo(group, SWT.READ_ONLY);
		add(comboState);

		br();

		rangeDateRegister = new Label(group, SWT.NONE);
		addHeader(rangeDateRegister);

		lblSince = new Label(group, SWT.NONE);
		add(lblSince);

		dt_since = new DateTime(group, SWT.BORDER | SWT.DROP_DOWN);
		add(dt_since);

		Auxiliary.goDateTimeToBeforeOneMonth(dt_since);

		lblUntil = new Label(group, SWT.NONE);
		add(lblUntil);

		dt_until = new DateTime(group, SWT.BORDER | SWT.DROP_DOWN);
		add(dt_until);

		newSearch = new Button(group, SWT.PUSH);
		add(newSearch);

		consult = new Button(group, SWT.PUSH);
		add(consult);

		br();

		separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		br();

		compoButton = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton);
		compoButton.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lblList = new Label(compoButton, SWT.NONE);
		addHeader(lblList);
		lblList.setVisible(false);

		// ---------TABLA DE PEDIDOS PENDIENTES O EJECUTADOS--------------------

		pendingOrderTable = new CRUDTreeTable(compoButton, SWT.NONE);
		add(pendingOrderTable);
		pendingOrderTable.setEntityClass(Desiderata.class);
		pendingOrderTable.setVisible(false);

		ViewDesiderataArea size = new ViewDesiderataArea(controller);
		size.setDimension(getDimension());

		EditDesiderataArea size1 = new EditDesiderataArea(controller, pendingOrderTable);
		size1.setDimension(getDimension());
		// RF_AQ5_Visualizar datos del Pedido
		pendingOrderTable.setWatch(true, size);
		pendingOrderTable.setUpdate(true, size1);
		pendingOrderTable.setDelete(true);
		pendingOrderTable.setVisualEntityManager(new MessageVisualEntityManager(pendingOrderTable));

		CRUDTreeTableUtils.configRemove(pendingOrderTable, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				Desiderata d = (Desiderata) event.entity.getRow();
				((AllManagementController) controller).getDesiderata().deleteDesiderata(d.getDesidertaID());
			}
		});

		pendingOrderTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
				}
				searchCurrentDesiderataTable(event.currentPage - 1, event.pageSize);
			}
		});

		pendingOrderTable.addListener(SWT.Resize, new Listener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});
		// ----------FIN TABLA PEDIDOS PENDIENTES----------

		// export pdf and excel
		CRUDTreeTableUtils.configReports(pendingOrderTable, MessageUtil.unescape(AbosMessages.get().CONSULT_DESIDERATA), searchC);

		TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getTitle"), new TreeTableColumn(20, 1, "getAuthor"), new TreeTableColumn(20, 2, "getEditorial"), new TreeTableColumn(20, 3, "getCreationDate"), new TreeTableColumn(20, 4, "getState.getNomenclatorName") };

		pendingOrderTable.createTable(columns);

		pendingOrderTable.setPageSize(10);

		// Realizar la consulta de las sugerencias por titulo, autor y tipo User
		consult.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				dateRegister = new java.sql.Date(dt_since.getYear() - 1900, dt_since.getMonth(), dt_since.getDay());
				endDateRegister = new java.sql.Date(dt_until.getYear() - 1900, dt_until.getMonth(), dt_until.getDay());

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String sinceString = sdf.format(dateRegister);
				String untilString = sdf.format(endDateRegister);

				searchC.clear();

				lblList.setVisible(true);
				pendingOrderTable.setVisible(true);

				if (txtTitle.getText().length() == 0) {
					title = null;
				} else {
					title = txtTitle.getText();
					searchC.add(lblTitle.getText());
					searchC.add(txtTitle.getText());
				}
				if (txtAuthor.getText().length() == 0) {
					author = null;
				} else {
					author = txtAuthor.getText();
					searchC.add(lblAuthor.getText());
					searchC.add(txtAuthor.getText());
				}

				if (txtEditor.getText().length() == 0) {
					editor = null;
				} else {
					editor = txtEditor.getText();
					searchC.add(lblEditor.getText());
					searchC.add(txtEditor.getText());
				}
				if (UiUtils.getSelected(comboState) == null) {
					stateDesiderata = null;
				} else {
					stateDesiderata = (Nomenclator) UiUtils.getSelected(comboState);
					searchC.add(lbState.getText());
					searchC.add(comboState.getText());
				}

				searchC.add(lblSince.getText());
				searchC.add(sinceString);
				searchC.add(lblUntil.getText());
				searchC.add(untilString);

				searchCurrentDesiderataTable(0, pendingOrderTable.getPaginator().getPageSize());
				pendingOrderTable.getPaginator().goToFirstPage();

				if (pendingOrderTable.getRows().isEmpty()) {
					lblList.setVisible(false);
					pendingOrderTable.setVisible(false);
					RetroalimentationUtils.showInformationMessage(compoButton, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				}
				refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		pendingOrderTable.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {

				User user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
				Desiderata desiderata = (Desiderata) row;

				if (column.getIndex() == 6) {
					if (user.getPerson() == null) {
						return true;
					} else if (!desiderata.getState().getNomenclatorID().equals(Nomenclator.DESIDERATA_STATE_PENDING)) {
						return true;
					} else {
						Worker workerLoggin = ((AllManagementController) controller).getPurchaseRequest().getWorkerByPerson(user.getPerson().getPersonID());
						if (workerLoggin == null) {
							return true;
						}
					}
				}

				return false;
			}
		});

		// Realizar una nueva busqueda
		newSearch.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				txtAuthor.setText("");
				txtEditor.setText("");
				txtTitle.setText("");
				comboState.select(0);

				pendingOrderTable.clearRows();
				lblList.setVisible(false);
				pendingOrderTable.setVisible(false);
				pendingOrderTable.destroyEditableArea();

				Auxiliary.goDateTimeToBeforeOneMonth(dt_since);
				Auxiliary.goDateTimeToToday(dt_until);
				  
				    
				

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		l10n();
		return parent;
	}

	// Buscar desideratas pendientes
	public void searchCurrentDesiderataTable(int page, int size) {
		pendingOrderTable.clearRows();
		Page<Desiderata> list = ((AllManagementController) controller).findAllDesideratasByParameters(title, author, editor, dateRegister, endDateRegister, stateDesiderata, library, page, size, direction, orderByStringOrder);
		pendingOrderTable.setTotalElements((int) list.getTotalElements());

		pendingOrderTable.setRows(list.getContent());
		pendingOrderTable.refresh();

		refresh();
	}
}
