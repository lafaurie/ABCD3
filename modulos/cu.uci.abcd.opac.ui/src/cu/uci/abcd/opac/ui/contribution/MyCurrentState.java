package cu.uci.abcd.opac.ui.contribution;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abcd.opac.ui.model.ReservationUpdateArea;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class MyCurrentState extends ContributorPage {

	private User user;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<String> values;

	private List<Control> grupControls;

	private LoanUser loanUser;

	private Image image;

	TabItem tabItemTransactions;
	TabItem tabItemPenaltys;
	TabItem tabItemReservations;

	Composite transactionsCompo;
	Composite penaltysCompo;
	Composite reservationCompo;
	Link countTransactions;
	Link countPenaltys;
	Link countReservations;
	CRUDTreeTable currentLoansTable;
	CRUDTreeTable existingPenaltyTable;
	CRUDTreeTable currentReservationTable;

	static String orderByStringLoans = "transactionID";
	static String orderByStringPenalty = "penaltyID";
	static String orderByStringReservation = "reservationID";

	static int direction = 1024;

	@Override
	public Control createUIControl(Composite parent) {

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
			loanUser = ((AllManagementOpacViewController) controller).findLoanUserByPersonIdAndIdLibrary(user.getPerson().getPersonID(), user.getLibrary().getLibraryID());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);

		dataGroup = new Group(parent, SWT.NORMAL);
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_PROFILE_DATA);
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

		final TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		FormDatas.attach(tabFolder).atTopTo(dataGroup).atLeft().atRight().atBottom();

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

		tabItemPenaltys = new TabItem(tabFolder, SWT.None);
		tabItemPenaltys.setControl(penaltysCompo);

		tabItemReservations = new TabItem(tabFolder, SWT.None);
		tabItemReservations.setControl(reservationCompo);

		// //// My current loans Table////////

		currentLoansTable = new CRUDTreeTable(transactionsCompo, SWT.NONE);
		currentLoansTable.setEntityClass(Transaction.class);

		TreeTableColumn transactionColumns[] = { new TreeTableColumn(15, 0, "getLoanType.getNomenclatorName"), new TreeTableColumn(30, 1, "getLoanObject.getTitle"), new TreeTableColumn(15, 2, "getLoanObject.getRecordType.getNomenclatorName"),
				new TreeTableColumn(20, 3, "getTransactionDateTime.toString") };

		currentLoansTable.createTable(transactionColumns);
		currentLoansTable.setPageSize(10);
		FormDatas.attach(currentLoansTable).atTopTo(countTransactions).atRight(5).atLeft(5);

		currentLoansTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				searchCurrentLoansTable(event.currentPage - 1, event.pageSize);
				refresh();

			}
		});

		currentLoansTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();

			}
		});  

		// //// My existing Penalty Table////////

		existingPenaltyTable = new CRUDTreeTable(penaltysCompo, SWT.NONE);
		existingPenaltyTable.setEntityClass(Penalty.class);

		TreeTableColumn penaltyColumns[] = { new TreeTableColumn(25, 0, "getEffectiveDate.toString"), new TreeTableColumn(25, 1, "getExpirationDate.toString"), new TreeTableColumn(25, 2, "getPenaltyType.getNomenclatorName"), new TreeTableColumn(25, 3, "getAmount") };

		existingPenaltyTable.createTable(penaltyColumns);
		existingPenaltyTable.setPageSize(10);
		FormDatas.attach(existingPenaltyTable).atTopTo(countPenaltys).atRight(5).atLeft(5);

		existingPenaltyTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				searchExistingPenaltyTable(event.currentPage - 1, event.pageSize);
				refresh();

			}
		});

		existingPenaltyTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();

			}
		});

		// //// My current Reservation Table////////

		countReservations = new Link(reservationCompo, SWT.NORMAL);
		countReservations.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(countReservations).atTop(10).atLeft(30);

		currentReservationTable = new CRUDTreeTable(reservationCompo, SWT.NONE);
		currentReservationTable.setEntityClass(Reservation.class);
		currentReservationTable.setDelete(true);
		currentReservationTable.setUpdate(true, new ReservationUpdateArea(controller, currentReservationTable));
		currentReservationTable.setCancelButtonText(MessageUtil.unescape((AbosMessages.get().CANCEL)));

		TreeTableColumn reservationColumns[] = { new TreeTableColumn(30, 0, "getTitle"), new TreeTableColumn(20, 1, "getAuthorLoanObject"), new TreeTableColumn(20, 2, "getReservationDate.toString"), new TreeTableColumn(20, 3, "getReservationEndDate.toString"),
				new TreeTableColumn(10, 5, "getState.getNomenclatorName") };

		currentReservationTable.createTable(reservationColumns);
		currentReservationTable.setPageSize(10);
		FormDatas.attach(currentReservationTable).atTopTo(countReservations).atRight(5).atLeft(5);

		currentReservationTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				searchCurrentReservationTable(event.currentPage - 1, event.pageSize);
				reservationCompo.notifyListeners(SWT.Resize, new Event());
				currentReservationTable.notifyListeners(SWT.Resize, new Event());
				refresh();

			}
		});

		currentReservationTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
				tabFolder.notifyListeners(SWT.Resize, new Event());

			}
		});

		currentReservationTable.addDeleteListener(new TreeColumnListener() {

			@Override
			public void handleEvent(TreeColumnEvent event) {

				try {
					Reservation reservation = (Reservation) event.entity.getRow();
					final Long idReservation = reservation.getReservationID();

					DialogCallback callback = new DialogCallback() {
						private static final long serialVersionUID = 1L;

						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
								((AllManagementOpacViewController) controller).deleteReservation(idReservation);
								searchCurrentReservationTable(0, currentReservationTable.getPaginator().getPageSize());
								showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM));
								currentReservationTable.getPaginator().goToFirstPage();
								refresh();
								tabFolder.notifyListeners(SWT.Resize, new Event());
								l10n();
							}
						}
					};

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_WARNING), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), callback);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		try {
			searchCurrentLoansTable(0, currentLoansTable.getPaginator().getPageSize());
			currentLoansTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			searchExistingPenaltyTable(0, existingPenaltyTable.getPaginator().getPageSize());
			existingPenaltyTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			searchCurrentReservationTable(0, currentReservationTable.getPaginator().getPageSize());
			currentReservationTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		l10n();
		return parent;
	}

	private void searchCurrentLoansTable(int page, int size) {

		currentLoansTable.clearRows();
		Page<Transaction> list = ((AllManagementOpacViewController) controller).findAllTransactionByUser(loanUser.getId(), page, size, direction, orderByStringLoans);
		currentLoansTable.setTotalElements((int) list.getTotalElements());
		currentLoansTable.setRows(list.getContent());
		currentLoansTable.refresh();

	}

	private void searchExistingPenaltyTable(int page, int size) {

		existingPenaltyTable.clearRows();
		Page<Penalty> list = ((AllManagementOpacViewController) controller).findAllPenaltyByUser(loanUser.getId(), page, size, direction, orderByStringPenalty);
		existingPenaltyTable.setTotalElements((int) list.getTotalElements());
		existingPenaltyTable.setRows(list.getContent());
		existingPenaltyTable.refresh();

	}

	private void searchCurrentReservationTable(int page, int size) {

		currentReservationTable.clearRows();
		Page<Reservation> list = ((AllManagementOpacViewController) controller).findAllCurrentReservationByUser(loanUser.getId(), page, size, direction, orderByStringReservation);
		currentReservationTable.setTotalElements((int) list.getTotalElements());
		currentReservationTable.setRows(list.getContent());
		currentReservationTable.refresh();

	}

	@Override
	public String getID() {
		return "MyCurrentStateID";
	}

	@Override
	public void l10n() {

		tabItemTransactions.setText(MessageUtil.unescape(AbosMessages.get().TAB_LOANS));
		tabItemPenaltys.setText(MessageUtil.unescape(AbosMessages.get().TAB_PENALTIES));
		tabItemReservations.setText(MessageUtil.unescape(AbosMessages.get().TAB_RESERVATIONS));

		currentLoansTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TABLE_LOANS_LOAN_TYPE), MessageUtil.unescape(AbosMessages.get().TABLE_LOANS_TITLE), MessageUtil.unescape(AbosMessages.get().TABLE_LOANS_OBJECT_TYPE),
				MessageUtil.unescape(AbosMessages.get().TABLE_LOANS_LOAN_DATE), MessageUtil.unescape(AbosMessages.get().TABLE_LOANS_DELIVERY_DATE)));
      
		existingPenaltyTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TABLE_PENALTY_START_DATE), MessageUtil.unescape(AbosMessages.get().TABLE_PENALTY_END_DATE), MessageUtil.unescape(AbosMessages.get().TABLE_PENALTY_TYPE),
				MessageUtil.unescape(AbosMessages.get().TABLE_PENALTY_AMOUNT_TO_PAY)));

		currentReservationTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_TITLE), MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_AUTHOR), MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_DATE),
				MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_EXPIRATION_DATE), MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_STATE)));

		try {
			countReservations.setText((MessageUtil.unescape(AbosMessages.get().TABLE_RESERVATION_COUNT) + "  (" + " " + currentReservationTable.getPaginator().getTotalElements() + " )"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		grupControls = CompoundGroup.printGroup(image, dataGroup, titleGroup, leftList, values);
		CompoundGroup.l10n(grupControls, leftList);

		refresh();

	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_MY_CURRENT_STATE);
	}

}
