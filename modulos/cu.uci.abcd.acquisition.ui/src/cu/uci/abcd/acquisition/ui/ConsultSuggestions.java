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
import cu.uci.abcd.acquisition.ui.updateArea.EditSuggestionArea;
import cu.uci.abcd.acquisition.ui.updateArea.SetSuggestionArea;
import cu.uci.abcd.acquisition.ui.updateArea.ViewSuggestionArea;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.Suggestion;
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

public class ConsultSuggestions extends ContributorPage implements Contributor {

	private CRUDTreeTable acceptedSuggestionsTable;
	private List<String> searchC = new ArrayList<>();
	private int page = 0;
	private int size = 10;
	private static String orderByStringSuggestion = "suggestionID";
	private static int direction = 1024;
	private Composite compoButton;
	private Label lblTitle;
	private Label criteria;
	private Label lblAuthor;
	private Label lblSugestionBy;
	private Label lblSince;
	private Label lblUntil;
	private Label list;
	private Label separator;
	private Label rangeDateRegister;
	private Label lblState;

	private Text txtTitle;
	private Text txtAuthor;

	private Combo cbSugestionBy;
	private Combo cbState;

	private DateTime dt_since;
	private DateTime dt_until;

	private Button newSearch;
	private Button consult;

	private String title = null;
	private String author = null;
	private Date dateRegister = null;
	private Date endDateRegister = null;
	private User sugestionBy = null;
	private Nomenclator state = null;
	private Label lbConsult;
	private Library library;

	@Override
	public String contributorName() {

		return MessageUtil.unescape(AbosMessages.get().CONTRIBUTOR_CONSULT_SUGGESTIONS);
	}

	public int order() {
		return 0;
	}

	@Override
	public String getID() {
		return "consultSuggestionID";
	}

	public String containerMenu() {
		return "Adquisición";
	}

	// Internacionalizacion
	@Override
	public void l10n() {
		lbConsult.setText(MessageUtil.unescape(AbosMessages.get().CONSULT_SUGGESTIONS));
		criteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		rangeDateRegister.setText(AbosMessages.get().LABEL_CREATION_DATE_RANGE);
		lblTitle.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		lblSugestionBy.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SUGGESTED_BY));
		lblAuthor.setText(MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR));
		lblSince.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SINCE));
		lblUntil.setText(MessageUtil.unescape(AbosMessages.get().LABEL_UNTIL));
		lblState.setText(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		consult.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		acceptedSuggestionsTable.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR),
				MessageUtil.unescape(AbosMessages.get().LABEL_SUGGESTED_BY),
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE),
				MessageUtil.unescape(AbosMessages.get().LABEL_STATE)));

		list.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COINCIDENCE_LIST));
		newSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));

		acceptedSuggestionsTable.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);
		acceptedSuggestionsTable.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		acceptedSuggestionsTable.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		acceptedSuggestionsTable.l10n();
		
		initialize(cbSugestionBy, ((AllManagementController) controller).getSuggestion().findAllUsers());
		initialize(cbState, ((AllManagementController) controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.SUGGESTION_STATE));
	
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
	public Control createUIControl(Composite parent) {
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

		lblTitle = new Label(group, SWT.NONE);
		add(lblTitle);

		txtTitle = new Text(group, SWT.NONE);
		add(txtTitle);

		lblAuthor = new Label(group, SWT.NONE);
		add(lblAuthor);

		txtAuthor = new Text(group, SWT.NONE);
		add(txtAuthor);

		br();

		lblSugestionBy = new Label(group, SWT.NONE);
		add(lblSugestionBy);

		cbSugestionBy = new Combo(group, SWT.READ_ONLY);
		add(cbSugestionBy);

		lblState = new Label(group, SWT.NONE);
		add(lblState);

		cbState = new Combo(group, SWT.READ_ONLY);
		add(cbState);

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

		consult = new Button(group, SWT.NONE);
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

		acceptedSuggestionsTable = new CRUDTreeTable(compoButton, SWT.NONE);
		add(acceptedSuggestionsTable);
		acceptedSuggestionsTable.setVisible(false);
		acceptedSuggestionsTable.setEntityClass(Suggestion.class);

		Column columnAccept = new Column("accept", parent.getDisplay(),
				new SetSuggestionArea(MessageUtil.unescape(AbosMessages
						.get().BUTTON_ACCEPT), controller, acceptedSuggestionsTable,
						this), new TreeColumnListener(){
			public void handleEvent(TreeColumnEvent event) {
			}
		});
		columnAccept.setToolTipText(AbosMessages.get().BUTTON_APPROVE);
		columnAccept.setAlignment(SWT.CENTER);			
		acceptedSuggestionsTable.addActionColumn(columnAccept);
		
		
		// REJECT SUGGESTION BUTTON
		Column columnReject = new Column("reject", parent.getDisplay(),
				new SetSuggestionArea(MessageUtil.unescape(AbosMessages
						.get().BUTTON_REJECT), controller, acceptedSuggestionsTable,
						this), new TreeColumnListener(){
			public void handleEvent(TreeColumnEvent event) {
			}
		});
		columnReject.setToolTipText(AbosMessages.get().BUTTON_REJECT);
		columnReject.setAlignment(SWT.CENTER);			
		acceptedSuggestionsTable.addActionColumn(columnReject);
		
		
		acceptedSuggestionsTable.setWatch(true, new ViewSuggestionArea(controller, this, acceptedSuggestionsTable));
		acceptedSuggestionsTable.setUpdate(true, new EditSuggestionArea(controller, acceptedSuggestionsTable, this, page, size));
		acceptedSuggestionsTable.setDelete(true);
		acceptedSuggestionsTable.setVisualEntityManager(new MessageVisualEntityManager(acceptedSuggestionsTable));

		// exportar a pdf
		CRUDTreeTableUtils.configReports(acceptedSuggestionsTable, MessageUtil.unescape(AbosMessages.get().CONSULT_SUGGESTIONS), searchC);
			
		TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getTitle"), new TreeTableColumn(20, 1, "getAuthor"), new TreeTableColumn(20, 2, "getUser.getPerson.getFullName"),
				new TreeTableColumn(20, 3, "getRegisterDate"), new TreeTableColumn(20, 4, "getState.getNomenclatorName") };
		acceptedSuggestionsTable.createTable(columns);

		acceptedSuggestionsTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
				}
				searchCurrentSuggestionTableByParameters(event.currentPage - 1, event.pageSize);
			}
		});

		
		CRUDTreeTableUtils.configRemove(acceptedSuggestionsTable, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				Suggestion s = (Suggestion) event.entity.getRow();
				((AllManagementController) controller).getSuggestion().deleteSuggestion(s.getSuggestionID());

			}
		});

		acceptedSuggestionsTable.getPaginator().setPageSize(10);
		
		acceptedSuggestionsTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});	
		
		consult.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			public void widgetSelected(SelectionEvent arg0) {

				dateRegister = new java.sql.Date(dt_since.getYear() - 1900, dt_since.getMonth(), dt_since.getDay());
				endDateRegister = new java.sql.Date(dt_until.getYear() - 1900, dt_until.getMonth(), dt_until.getDay());

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String sinceString = sdf.format(dateRegister);
				String untilString = sdf.format(endDateRegister);

				title = (txtTitle.getText().length() > 0 ? txtTitle.getText() : null);
				author = (txtAuthor.getText().length() > 0 ? txtAuthor.getText() : null);

				
					searchC.clear();
					acceptedSuggestionsTable.destroyEditableArea();

					list.setVisible(true);
					acceptedSuggestionsTable.setVisible(true);

					if (!txtTitle.getText().equals("")) {
						searchC.add(lblTitle.getText());
						searchC.add(txtTitle.getText());
					}
					if (!txtAuthor.getText().equals("")) {
						searchC.add(lblAuthor.getText());
						searchC.add(txtAuthor.getText());
					}

					if (UiUtils.getSelected(cbSugestionBy) == null) {
						sugestionBy = null;
					} else {
						sugestionBy = (User) UiUtils.getSelected(cbSugestionBy);
						searchC.add(lblSugestionBy.getText());
						searchC.add(cbSugestionBy.getText());
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
		
				searchCurrentSuggestionTableByParameters(0, acceptedSuggestionsTable.getPageSize());
				acceptedSuggestionsTable.getPaginator().goToFirstPage();
				
				if (acceptedSuggestionsTable.getRows().isEmpty()) {
					list.setVisible(false);
					acceptedSuggestionsTable.setVisible(false);
					RetroalimentationUtils.showInformationMessage(compoButton, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				}
				refresh();
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		// Realizar nueva busqueda
		newSearch.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				txtAuthor.setText("");
				txtTitle.setText("");
				cbSugestionBy.select(0);
				cbState.select(0);
				cbSugestionBy.select(0);
				acceptedSuggestionsTable.clearRows();
				list.setVisible(false);
				acceptedSuggestionsTable.setVisible(false);
				acceptedSuggestionsTable.destroyEditableArea();
				 
				Auxiliary.goDateTimeToBeforeOneMonth(dt_since);
				Auxiliary.goDateTimeToToday(dt_until);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
			
		acceptedSuggestionsTable.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {
			
				User user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
				Suggestion s = (Suggestion) row;
				
				if (column.getIndex() ==5) {
					 if (user.getPerson() == null ) {			
						return true;
					}
					 else if (!s.getState().getNomenclatorID().equals(Nomenclator.SUGGESTION_STATE_PENDING)) {
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
					else if (!s.getState().getNomenclatorID().equals(Nomenclator.SUGGESTION_STATE_PENDING)) {
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
					else if (!s.getState().getNomenclatorID().equals(Nomenclator.SUGGESTION_STATE_PENDING)) {
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

	// Buscar sugerencias por parametros
	public void searchCurrentSuggestionTableByParameters(int page, int size) {
		acceptedSuggestionsTable.clearRows();
		Page<Suggestion> list = ((AllManagementController) controller)
				.findAllApprovedSuggestionsByParameters(title, author,
						dateRegister, endDateRegister, sugestionBy, state,library,
						page, size, direction, orderByStringSuggestion);
	acceptedSuggestionsTable.setTotalElements((int) list.getTotalElements());
		acceptedSuggestionsTable.setRows(list.getContent());
		acceptedSuggestionsTable.refresh();
	}
}