package cu.uci.abcd.opac.ui.contribution;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class MyHistoryState extends ContributorPage {

	private User user;
	private LoanUser loanUser;

	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<String> values;

	private List<Control> grupControls;

	private Image image;

	private Group filters;
	private String title;
	private Long state;
	private Combo bookStateCombo;

	private Button cancelSearchBtn;

	private Label bookTitleLabel;
	private Label stateLabel;

	private TabFolder tabFolder;
	private TabItem tabItemTransactions;
	private TabItem tabItemPenaltys;
	private TabItem tabItemReservations;

	private Composite result;
	private Composite transactionsCompo;
	private Composite penaltysCompo;
	private Composite reservationCompo;

	private CRUDTreeTable historicalLoansTable;
	private CRUDTreeTable historicalSanctionsTable;
	private CRUDTreeTable historicalReservationTable;

	static String orderByStringLoans = "transactionID";
	static String orderByStringPenalty = "penaltyID";
	static String orderByStringReservation = "reservationID";

	static int direction = 1024;

	@Override
	public Control createUIControl(Composite parent) {

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
			loanUser = ((AllManagementOpacViewController) controller).findLoanUserByPersonIdAndIdLibrary(user.getPerson().getPersonID(), user.getLibrary().getLibraryID());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		result = parent;

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);

		dataGroup = new Group(parent, SWT.NORMAL);
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_HISTORICAL_PROFILE_DATA);
		dataGroup.setBackground(parent.getBackground());
		FormDatas.attach(dataGroup).atTop().atLeft(10);

		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_BIRTHDAY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SEX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));

		values = new LinkedList<>();
		values.add(user.getPerson().getFullName());
		values.add((user.getPerson().getUser() != null) ? user.getPerson().getUser().getUsername() : "-");
		values.add(new SimpleDateFormat("dd-MM-yyyy").format(user.getPerson().getBirthDate()));
		values.add(user.getPerson().getSex().getNomenclatorName());
		values.add(user.getPerson().getDNI());
		image = user.getPerson().getPhoto().getImage();

		grupControls = CompoundGroup.printGroup(image, dataGroup, titleGroup, leftList, values);
		CompoundGroup.l10n(grupControls, leftList);

		tabFolder = new TabFolder(result, SWT.NONE);
		FormDatas.attach(tabFolder).atTopTo(dataGroup).atLeft().atRight().atBottom();

		createFilter();

		transactionsCompo = new Composite(tabFolder, SWT.V_SCROLL);
		transactionsCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		transactionsCompo.setLayout(new FormLayout());

		penaltysCompo = new Composite(tabFolder, SWT.V_SCROLL);
		penaltysCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		penaltysCompo.setLayout(new FormLayout());

		reservationCompo = new Composite(tabFolder, SWT.V_SCROLL);
		reservationCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		reservationCompo.setLayout(new FormLayout());

		tabItemTransactions = new TabItem(tabFolder, SWT.None);
		tabItemTransactions.setControl(transactionsCompo);
		tabItemTransactions.setText("Prestamos históricos");

		tabItemPenaltys = new TabItem(tabFolder, SWT.None);
		tabItemPenaltys.setControl(penaltysCompo);
		tabItemPenaltys.setText("Sanciones históricas");

		tabItemReservations = new TabItem(tabFolder, SWT.None);
		tabItemReservations.setControl(reservationCompo);
		tabItemReservations.setText("Reservaciones históricas");

		// //// My historical loans Table////////

		historicalLoansTable = new CRUDTreeTable(transactionsCompo, SWT.NONE);
		historicalLoansTable.setEntityClass(Transaction.class);
		historicalLoansTable.setCancelButtonText("Cancelar");

		TreeTableColumn transactionColumns[] = { new TreeTableColumn(15, 0, "getLoanType.getNomenclatorName"), new TreeTableColumn(30, 1, "getLoanObject.getTitle"), new TreeTableColumn(15, 2, "getLoanObject.getRecordType.getNomenclatorName"),
				new TreeTableColumn(20, 3, "getTransactionDateTime.toString") };

		historicalLoansTable.createTable(transactionColumns);
		historicalLoansTable.setPageSize(10);
		FormDatas.attach(historicalLoansTable).atTop(10).atRight(5).atLeft(5);

		historicalLoansTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				searchHistoricalLoansTable(event.currentPage - 1, event.pageSize);
				refresh();

			}
		});

		historicalLoansTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();

			}
		});

		// //// My historical Sanctions Table////////

		historicalSanctionsTable = new CRUDTreeTable(penaltysCompo, SWT.NONE);
		historicalSanctionsTable.setEntityClass(Penalty.class);

		TreeTableColumn penaltyColumns[] = { new TreeTableColumn(25, 0, "getEffectiveDate.toString"), new TreeTableColumn(25, 1, "getExpirationDate.toString"), new TreeTableColumn(25, 2, "getPenaltyType.getNomenclatorName"), new TreeTableColumn(25, 3, "getAmount") };

		historicalSanctionsTable.createTable(penaltyColumns);
		historicalSanctionsTable.setPageSize(10);
		FormDatas.attach(historicalSanctionsTable).atTop(10).atRight(5).atLeft(5);

		historicalSanctionsTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				searchExistingPenaltyTable(event.currentPage - 1, event.pageSize);
				refresh();

			}
		});

		historicalSanctionsTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();

			}
		});

		// //// My historical Reservation Table////////

		historicalReservationTable = new CRUDTreeTable(reservationCompo, SWT.NONE);
		historicalReservationTable.setEntityClass(Reservation.class);

		TreeTableColumn reservationColumns[] = { new TreeTableColumn(30, 0, "getTitle"), new TreeTableColumn(20, 1, "getAuthorLoanObject"), new TreeTableColumn(20, 2, "getCreationDate.toString"), new TreeTableColumn(20, 3, "getReservationDate.toString"),
				new TreeTableColumn(10, 5, "getState.getNomenclatorName") };

		historicalReservationTable.createTable(reservationColumns);
		historicalReservationTable.setPageSize(10);
		FormDatas.attach(historicalReservationTable).atTop(10).atRight(5).atLeft(5);

		historicalReservationTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				searchHistoricalReservationTableByFilter(event.currentPage - 1, event.pageSize, title, state);
				refresh();

			}
		});

		historicalReservationTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();

			}
		});

		tabFolder.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (reservationCompo.getVisible() == true) {

					if (result.getShell().getDisplay().getBounds().width < 1200) {
						FormDatas.attach(filters).atTopTo(dataGroup, 10).atLeft(10).withHeight(142).withWidth(300);
						FormDatas.attach(tabFolder).atTopTo(filters).atLeft().atRight().atBottom();

					} else {
						createFilter();
						FormDatas.attach(filters).atTop().atLeftTo(dataGroup, 10).withHeight(142).withWidth(300);
						FormDatas.attach(tabFolder).atTopTo(dataGroup).atLeft().atRight().atBottom();
					}

					filters.setVisible(true);
				} else if (filters != null) {
					FormDatas.attach(tabFolder).atTopTo(dataGroup).atLeft().atRight().atBottom();
					filters.setVisible(false);
				}

				l10n();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		try {
			searchHistoricalLoansTable(0, historicalLoansTable.getPageSize());
			historicalLoansTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			searchExistingPenaltyTable(0, historicalSanctionsTable.getPageSize());
			historicalSanctionsTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			searchHistoricalReservationTableByFilter(0, historicalReservationTable.getPageSize(), null, null);
			historicalReservationTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		l10n();
		return result;

	}

	private void searchHistoricalLoansTable(int page, int size) {

		historicalLoansTable.clearRows();
		Page<Transaction> list = ((AllManagementOpacViewController) controller).findAllTransactionByUser(loanUser.getId(), page, size, direction, orderByStringLoans);
		historicalLoansTable.setTotalElements((int) list.getTotalElements());
		historicalLoansTable.setRows(list.getContent());
		historicalLoansTable.refresh();

	}

	private void searchExistingPenaltyTable(int page, int size) {

		historicalSanctionsTable.clearRows();
		Page<Penalty> list = ((AllManagementOpacViewController) controller).findHistoricalPenaltyByUser(loanUser.getId(), page, size, direction, orderByStringPenalty);
		historicalSanctionsTable.setTotalElements((int) list.getTotalElements());
		historicalSanctionsTable.setRows(list.getContent());
		historicalSanctionsTable.refresh();

	}

	private void searchHistoricalReservationTableByFilter(int page, int size, String titleBook, Long state) {

		historicalReservationTable.clearRows();
		Page<Reservation> list;

		if (titleBook != "" || state != null)
			list = ((AllManagementOpacViewController) controller).findHistoricalReservationByUser(loanUser.getId(), page, size, direction, orderByStringReservation, titleBook, state);
		else
			list = ((AllManagementOpacViewController) controller).findHistoricalReservationByUser(loanUser.getId(), page, size, direction, orderByStringReservation);

		historicalReservationTable.setTotalElements((int) list.getTotalElements());
		historicalReservationTable.setRows(list.getContent());
		historicalReservationTable.refresh();

	}

	@Override
	public String getID() {
		return "MyHistoryStateID";
	}

	@Override
	public void l10n() {

		tabItemTransactions.setText(MessageUtil.unescape(AbosMessages.get().TAB_LOANS));
		tabItemPenaltys.setText(MessageUtil.unescape(AbosMessages.get().TAB_PENALTIES));
		tabItemReservations.setText(MessageUtil.unescape(AbosMessages.get().TAB_RESERVATIONS));

		if (filters != null) {
			filters.setText(MessageUtil.unescape(AbosMessages.get().LABEL_HISTORICAL_FILTER));
			bookTitleLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_HISTORICAL_TITLE_FILTER));
			stateLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_HISTORICAL_STATE_FILTER));
			cancelSearchBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_FILTER_CLEAN));

			initialize(bookStateCombo, ((AllManagementOpacViewController) controller).findAllNomencaltors(Nomenclator.RESERVATION_STATE));
		}

		historicalLoansTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TABLE_LOANS_LOAN_TYPE), MessageUtil.unescape(AbosMessages.get().TABLE_LOANS_TITLE), MessageUtil.unescape(AbosMessages.get().TABLE_LOANS_OBJECT_TYPE),
				MessageUtil.unescape(AbosMessages.get().TABLE_LOANS_LOAN_DATE), MessageUtil.unescape(AbosMessages.get().TABLE_LOANS_DELIVERY_DATE)));

		historicalSanctionsTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TABLE_PENALTY_START_DATE), MessageUtil.unescape(AbosMessages.get().TABLE_PENALTY_END_DATE), MessageUtil.unescape(AbosMessages.get().TABLE_PENALTY_TYPE),
				MessageUtil.unescape(AbosMessages.get().TABLE_PENALTY_AMOUNT_TO_PAY)));

		historicalReservationTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_TITLE), MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_AUTHOR), MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_DATE),
				MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_EXPIRATION_DATE), MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_STATE)));

		CompoundGroup.l10n(grupControls, leftList);

		refresh();

	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_MY_HISTORICAL_STATE);
	}

	private void createFilter() {

		filters = new Group(result, SWT.NONE);
		filters.setBackground(result.getBackground());
		filters.setLayout(new FormLayout());

		filters.setVisible(false);

		Label bound = new Label(filters, 0);
		FormDatas.attach(bound).atTop().atLeft(50);

		bookTitleLabel = new Label(filters, SWT.NORMAL);
		FormDatas.attach(bookTitleLabel).atTop(25).atRightTo(bound);

		final Text bookTitleText = new Text(filters, SWT.NORMAL);
		FormDatas.attach(bookTitleText).atTop(20).atLeftTo(bound, 5).withWidth(200).withHeight(10);

		stateLabel = new Label(filters, SWT.NORMAL);
		FormDatas.attach(stateLabel).atTopTo(bookTitleText, 20).atRightTo(bound);

		bookStateCombo = new Combo(filters, SWT.NORMAL);
		FormDatas.attach(bookStateCombo).atTopTo(bookTitleText, 15).atLeftTo(bound, 5).withWidth(220).withHeight(20);

		cancelSearchBtn = new Button(filters, SWT.PUSH);
		FormDatas.attach(cancelSearchBtn).atTopTo(bookStateCombo, 20).atLeftTo(bound, 5);

		bookTitleText.addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {

				if (UiUtils.getSelected(bookStateCombo) != null)
					state = ((Nomenclator) UiUtils.getSelected(bookStateCombo)).getNomenclatorID();

				title = bookTitleText.getText();

				try {
					searchHistoricalReservationTableByFilter(0, historicalReservationTable.getPaginator().getPageSize(), title, state);
					historicalReservationTable.getPaginator().goToFirstPage();
				} catch (Exception e) {
					e.printStackTrace();
				}
				refresh();

			}
		});

		cancelSearchBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 4402295950606054133L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {
					bookTitleText.setText("");
					bookStateCombo.select(0);
					state = null;
					searchHistoricalReservationTableByFilter(0, historicalReservationTable.getPageSize(), null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		bookStateCombo.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (bookTitleText.getText() != null) {
					if (bookStateCombo.getSelectionIndex() != 0) {
						title = bookTitleText.getText();

						state = ((Nomenclator) UiUtils.getSelected(bookStateCombo)).getNomenclatorID();
						searchHistoricalReservationTableByFilter(0, historicalReservationTable.getPaginator().getPageSize(), title, state);

					} else {
						state = null;
						searchHistoricalReservationTableByFilter(0, historicalReservationTable.getPaginator().getPageSize(), null, null);
					}
				} else
					searchHistoricalReservationTableByFilter(0, historicalReservationTable.getPaginator().getPageSize(), null, state);

				historicalReservationTable.getPaginator().goToFirstPage();
				refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

	}
}
