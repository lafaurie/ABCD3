package cu.uci.abcd.circulation.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.listener.EventConsultReservation;
import cu.uci.abcd.circulation.ui.model.ViewAreaTransactions;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.domain.Row;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.IActionDenied;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConsultTransaction extends ContributorPage implements Contributor {
	private List<String> searchC = new ArrayList<>();
	private Label lbConsultTransaction;
	private Label lbSearchCriteria;
	private Label lbObjectLoanData;
	private Label lbInventoryNumber;
	private Label lbTitle;
	private Label lbObjectType;
	private Label lbUserLoanData;
	private Label lbUserCode;
	private Label lbUserType;
	private Label lbDateTransactions;
	private Label lbLoanType;
	private Label lbState;
	private Label lbLocation;
	private Label lbFirstName;
	private Label lbSecondName;
	private Label lbFirstLast;
	private Label lbSecondLast;
	private Label lbFrom;
	private Label lbUp;
	private Label lbCoincidenceList;
	private Label lbRangeDate;
	private Link link;
	private Button btnConsult;
	private Button btnNewSearch;
	private Text txtInventoryNumber;
	private Text txtTitle;
	private Text txtUserCode;
	private Text txtFirstName;
	private Text txtSecondName;
	private Text txtFirstLast;
	private Text txtSecondLast;

	private Combo comboLocation;
	private Combo comboObjectType;
	private Combo comboState;
	private Combo comboUserType;
	private Combo comboLoanType;
	private SecurityCRUDTreeTable tabla;

	private Date dateRegister = null;
	private Date endDateRegister = null;

	private DateTime dateTime;
	private DateTime dateTime1;

	// search///

	private String inventory_number = null;
	private Nomenclator record_type_id = null;
	private String loan_user_code = null;
	private Nomenclator loan_user_type_id = null;
	private String first_Name = null;
	private String second_Name = null;
	private String first_Surname = null;
	private String second_Surname = null;

	private String title = null;
	private Room loan_object_rooms = null;
	private Nomenclator loan_type = null;
	private Nomenclator transaction_state = null;

	private String orderByString = "loanObject.title";
	private int direction = 1024;
	private Composite compoLoanObject;
	private Composite compoLoanUserAdvanced;
	private Composite compoButton;
	private Composite compoButton1;
	private Composite compoLoanUser;
	private Library library;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().NAME_UI_CONSULT_TRANSACTION);
	}

	// FIXME METODO COMPLEJO
	@SuppressWarnings("serial")
	@Override
	public Control createUIControl(Composite parent) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 160);
	
		// ----------------Compo
		// LoanUser----------------------------------------------

		compoLoanUser = new Composite(parent, SWT.NORMAL);
		compoLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoLoanUser);

		lbConsultTransaction = new Label(compoLoanUser, SWT.NONE);
		addHeader(lbConsultTransaction);

		Label separatorHeader = new Label(compoLoanUser,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separatorHeader);
		
		lbSearchCriteria = new Label(compoLoanUser, SWT.NONE);
		addHeader(lbSearchCriteria);

		lbUserLoanData = new Label(compoLoanUser, SWT.NONE);
		addHeader(lbUserLoanData);

		lbUserType = new Label(compoLoanUser, SWT.NONE);
		add(lbUserType);
		comboUserType = new Combo(compoLoanUser, SWT.READ_ONLY);
		add(comboUserType);

		lbUserCode = new Label(compoLoanUser, SWT.NONE);
		add(lbUserCode);
		txtUserCode = new Text(compoLoanUser, SWT.NORMAL);
		add(txtUserCode);

		compoLoanUserAdvanced = new Composite(parent, SWT.NORMAL);
		compoLoanUserAdvanced.setVisible(false);
		compoLoanUserAdvanced.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoLoanUserAdvanced);

		lbFirstName = new Label(compoLoanUserAdvanced, SWT.RIGHT);
		add(lbFirstName);
		txtFirstName = new Text(compoLoanUserAdvanced, SWT.NORMAL);
		add(txtFirstName);

		lbSecondName = new Label(compoLoanUserAdvanced, SWT.RIGHT);
		add(lbSecondName);
		txtSecondName = new Text(compoLoanUserAdvanced, SWT.NORMAL);
		add(txtSecondName);

		br();

		lbFirstLast = new Label(compoLoanUserAdvanced, SWT.RIGHT);
		add(lbFirstLast);
		txtFirstLast = new Text(compoLoanUserAdvanced, SWT.NORMAL);
		add(txtFirstLast);

		lbSecondLast = new Label(compoLoanUserAdvanced, SWT.RIGHT);
		add(lbSecondLast);
		txtSecondLast = new Text(compoLoanUserAdvanced, SWT.NORMAL);
		add(txtSecondLast);

		// *******************Compo LoanObject***********************
		compoLoanObject = new Composite(parent, SWT.NORMAL);
		compoLoanObject.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoLoanObject);

		lbObjectLoanData = new Label(compoLoanObject, SWT.NONE);
		addHeader(lbObjectLoanData);

		lbInventoryNumber = new Label(compoLoanObject, SWT.NONE);
		add(lbInventoryNumber);
		txtInventoryNumber = new Text(compoLoanObject, SWT.NORMAL);
		add(txtInventoryNumber);

		lbTitle = new Label(compoLoanObject, SWT.NONE);
		add(lbTitle);
		txtTitle = new Text(compoLoanObject, SWT.NORMAL);
		add(txtTitle);

		br();

		lbObjectType = new Label(compoLoanObject, SWT.NONE);
		add(lbObjectType);
		comboObjectType = new Combo(compoLoanObject, SWT.READ_ONLY);
		add(comboObjectType);

		lbLocation = new Label(compoLoanObject, SWT.RIGHT);
		add(lbLocation);
		comboLocation = new Combo(compoLoanObject, SWT.READ_ONLY);
		add(comboLocation);

		// ******************Compo Transaction***********************
		Composite compoTransaction = new Composite(parent, SWT.NORMAL);
		compoTransaction.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoTransaction);

		lbDateTransactions = new Label(compoTransaction, SWT.NONE);
		addHeader(lbDateTransactions);

		lbLoanType = new Label(compoTransaction, SWT.NONE);
		add(lbLoanType);
		comboLoanType = new Combo(compoTransaction, SWT.READ_ONLY);
		add(comboLoanType);

		lbState = new Label(compoTransaction, SWT.NONE);
		add(lbState);
		comboState = new Combo(compoTransaction, SWT.READ_ONLY);
		add(comboState);

		Composite compoTransactionAdvanced = new Composite(parent, SWT.NORMAL);
		compoTransactionAdvanced.setVisible(false);
		compoTransactionAdvanced.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoTransactionAdvanced);

		lbRangeDate = new Label(compoTransactionAdvanced, SWT.RIGHT);
		addHeader(lbRangeDate);

		lbFrom = new Label(compoTransactionAdvanced, SWT.NONE);
		add(lbFrom);
		dateTime = new DateTime(compoTransactionAdvanced, SWT.BORDER | SWT.DROP_DOWN);
		add(dateTime);

		cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToBeforeOneMonth(dateTime);
	
	
		lbUp = new Label(compoTransactionAdvanced, SWT.NONE);
		add(lbUp);
		dateTime1 = new DateTime(compoTransactionAdvanced, SWT.BORDER | SWT.DROP_DOWN);
		add(dateTime1);

		// -----------Button---------------------------------------------------
		compoButton = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton);
		compoButton.setData(RWT.CUSTOM_VARIANT, "gray_background");

		btnNewSearch = new Button(compoButton, SWT.PUSH);
		add(btnNewSearch);

		btnConsult = new Button(compoButton, SWT.PUSH);
		add(btnConsult);

		link = new Link(compoButton, SWT.NONE);
		add(link);

		br();

		link.addListener(SWT.Selection, new EventConsultReservation(compoLoanUser, compoLoanUserAdvanced, compoTransaction, compoTransactionAdvanced, link, this));
		Label separator = new Label(compoButton, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		compoButton1 = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton1);
		compoButton1.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbCoincidenceList = new Label(compoButton1, SWT.NONE);
		addHeader(lbCoincidenceList);
		lbCoincidenceList.setVisible(false);

		// -----------------Tabla--------------------------------------

		tabla = new SecurityCRUDTreeTable(compoButton1, SWT.NONE);
		add(tabla);
		tabla.setEntityClass(Transaction.class);
		// tabla.setVisualEntityManager(new MessageVisualEntityManager(tabla));
		tabla.setWatch(true, new ViewAreaTransactions(controller, tabla, this));
		tabla.setDelete(true);
		tabla.setVisible(false);
		tabla.setPageSize(10);

		tabla.addListener(SWT.Resize, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		CRUDTreeTableUtils.configReports(tabla, MessageUtil.unescape(AbosMessages.get().NAME_CONSULT_TRANSACTION), searchC);

		CRUDTreeTableUtils.configRemove(tabla, new IActionCommand() {
			@Override
			public void execute(TreeColumnEvent event) {
				Transaction transactions = (Transaction) event.entity.getRow();
				Nomenclator loanObjectState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOANOBJECT_STATE_AVAILABLE);
				if (transactions.getLoanObject().getLoanObjectState().getNomenclatorID().equals(Nomenclator.LOANOBJECT_STATE_BORROWED)) {

					LoanObject loanObject = transactions.getLoanObject();
					loanObject.setLoanObjectState(loanObjectState);
					((AllManagementLoanUserViewController) controller).getManageObject().addLoanObject(loanObject);
				}
				((AllManagementLoanUserViewController) controller).getManageTransaction().deleteTransaction(transactions.getTransactionID());
			}
		});

		btnNewSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtUserCode.setText("");
				txtInventoryNumber.setText("");
				txtTitle.setText("");
				txtFirstLast.setText("");
				txtFirstName.setText("");
				txtSecondLast.setText("");
				txtSecondName.setText("");

				comboUserType.select(0);
				comboLoanType.select(0);
				comboObjectType.select(0);
				comboState.select(0);
				comboLocation.select(0);

				tabla.clearRows();
				lbCoincidenceList.setVisible(false);
				tabla.setVisible(false);
				tabla.destroyEditableArea();

				cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToBeforeOneMonth(dateTime);
				cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToToday(dateTime1);
				refresh();

			}
		});

		btnConsult.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 7898030967335446631L;

			// FIXME METODO COMPLEJO
			@SuppressWarnings({ "deprecation" })
			@Override
			public void widgetSelected(SelectionEvent e) {

				searchC.clear();

				tabla.clearRows();
				lbCoincidenceList.setVisible(true);
				tabla.setVisible(true);
				tabla.destroyEditableArea();

				if (compoLoanUserAdvanced.getVisible() == false) {
					dateRegister = null;
					endDateRegister = null;
				} else {
					int fromYear = dateTime.getYear() - 1900;
					int fromMonth = dateTime.getMonth();
					int fromDay = dateTime.getDay();

					dateRegister = new Date(fromYear, fromMonth, fromDay);
					searchC.add(lbFrom.getText());
					searchC.add(Auxiliary.FormatDate(dateRegister));

					int fromYear1 = dateTime1.getYear() - 1900;
					int fromMonth1 = dateTime1.getMonth();
					int fromDay1 = dateTime1.getDay();

					endDateRegister = new Date(fromYear1, fromMonth1, fromDay1);
					searchC.add(lbUp.getText());
					searchC.add(Auxiliary.FormatDate(endDateRegister));

					if (!dateRegister.before(endDateRegister) && !(dateRegister.toString().equals(endDateRegister.toString()))) {
						lbCoincidenceList.setVisible(false);
						tabla.setVisible(false);
						RetroalimentationUtils.showErrorMessage(compoButton1, AbosMessages.get().MSG_ERROR_FINAL_DATE_LESS_THAN_INITIAL);

					}
				}

				inventory_number = (txtInventoryNumber.getText().length() > 0 ? txtInventoryNumber.getText().replaceAll(" +", " ").trim() : null);
				title = (txtTitle.getText().length() > 0 ? txtTitle.getText().replaceAll(" +", " ").trim() : null);
				loan_user_code = (txtUserCode.getText().length() > 0 ? txtUserCode.getText().replaceAll(" +", " ").trim() : null);
				first_Name = (txtFirstName.getText().length() > 0 ? txtFirstName.getText().replaceAll(" +", " ").trim() : null);
				second_Name = (txtSecondName.getText().length() > 0 ? txtSecondName.getText().replaceAll(" +", " ").trim() : null);
				first_Surname = (txtFirstLast.getText().length() > 0 ? txtFirstLast.getText().replaceAll(" +", " ").trim() : null);
				second_Surname = (txtSecondLast.getText().length() > 0 ? txtSecondLast.getText().replaceAll(" +", " ").trim() : null);

				UiUtils.get().addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE), txtUserCode.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), txtTitle.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER), txtInventoryNumber.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_NAME), txtFirstName.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_NAME), txtSecondName.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_LAST), txtFirstLast.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_LAST), txtSecondLast.getText());

				if (UiUtils.getSelected(comboObjectType) == null) {
					record_type_id = null;
				} else {
					record_type_id = (Nomenclator) UiUtils.getSelected(comboObjectType);
					searchC.add(lbObjectType.getText());
					searchC.add(comboObjectType.getText());
				}

				if (UiUtils.getSelected(comboUserType) == null) {
					loan_user_type_id = null;
				} else {
					loan_user_type_id = (Nomenclator) UiUtils.getSelected(comboUserType);
					searchC.add(lbUserType.getText());
					searchC.add(comboUserType.getText());
				}

				if (UiUtils.getSelected(comboState) == null) {
					transaction_state = null;
				} else {
					transaction_state = (Nomenclator) UiUtils.getSelected(comboState);
					searchC.add(lbState.getText());
					searchC.add(comboState.getText());
				}

				if (UiUtils.getSelected(comboLocation) == null) {
					loan_object_rooms = null;
				} else {
					loan_object_rooms = (Room) UiUtils.getSelected(comboLocation);
					searchC.add(lbLocation.getText());
					searchC.add(comboLocation.getText());
				}

				if (UiUtils.getSelected(comboLoanType) == null) {
					loan_type = null;
				} else {
					loan_type = (Nomenclator) UiUtils.getSelected(comboLoanType);
					searchC.add(lbLoanType.getText());
					searchC.add(comboLoanType.getText());
				}

				orderByString = "loanObject.title";

				direction = 1024;
				searchTransactions(0, tabla.getPageSize());
				if (tabla.getRows().isEmpty()) {
					lbCoincidenceList.setVisible(false);
					tabla.setVisible(false);
					RetroalimentationUtils.showInformationMessage(compoButton1, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				} else 
					tabla.getPaginator().goToFirstPage();

				refresh();
			}
		});

		TreeTableColumn columns[] = { new TreeTableColumn(16, 0, "getLoanObject.getTitle"), new TreeTableColumn(16, 1, "getLoanObject.getInventorynumber"), new TreeTableColumn(16, 2, "getLoanObject.getRecordType.getNomenclatorName"), new TreeTableColumn(16, 3, "getState.getNomenclatorName"),
				new TreeTableColumn(16, 4, "getLoanUser.fullName"), new TreeTableColumn(16, 5, "getLoanUser.getLoanUserCode"), };

		tabla.createTable(columns);

		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
			
				searchTransactions(event.currentPage - 1, event.pageSize);
			}
		});

		tabla.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {
				Transaction transaction = (Transaction) row;
				
				User user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
				
				if (column.getIndex() == 7) {
					if (transaction.getState().getNomenclatorID().equals(Nomenclator.LOAN_STATE_RETURN) || transaction.getState().getNomenclatorID().equals(Nomenclator.LOAN_STATE_NOT_DELIVERED)) {
						return true;
					}
					else if (user.getPerson() == null ) {			
						return true;
					}
					else{
						Worker workerLoggin = ((AllManagementLoanUserViewController) controller).getManagePerson().findWorkerbyPersonID(user.getPerson().getPersonID());
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

	public void searchTransactions(int page, int size) {
		tabla.clearRows();
		Page<Transaction> listTransaction = ((AllManagementLoanUserViewController) controller)
				.getManageTransaction().findAllTransactionConsult(
						inventory_number, title, record_type_id,
						loan_user_type_id, loan_user_code, first_Name,
						second_Name, first_Surname, second_Surname, loan_type,
						transaction_state, dateRegister, endDateRegister,
						loan_object_rooms,library, page, size, size, orderByString);
		tabla.setTotalElements((int) listTransaction.getTotalElements());
		if ((int) listTransaction.getTotalElements() > 0) {
			tabla.setRows(listTransaction.getContent());
			tabla.refresh();
		}

	}

	@Override
	public String getID() {
		return "consultTransactionID";
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		refresh();
		lbConsultTransaction.setText(MessageUtil.unescape(AbosMessages.get().NAME_CONSULT_TRANSACTION));
		lbSearchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		lbObjectLoanData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_LOAN_DATA));
		lbInventoryNumber.setText(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		lbTitle.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		lbObjectType.setText(AbosMessages.get().LABEL_OBJECT_TYPE);
		lbFirstName.setText(AbosMessages.get().LABEL_FIRST_NAME);
		lbSecondName.setText(AbosMessages.get().LABEL_SECOND_NAME);
		lbFirstLast.setText(AbosMessages.get().LABEL_FIRST_LAST);
		lbSecondLast.setText(AbosMessages.get().LABEL_SECOND_LAST);
		lbDateTransactions.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_TRANSACTION));
		lbLoanType.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_LOAN));
		lbState.setText(AbosMessages.get().LABEL_STATE);
		lbFrom.setText(AbosMessages.get().LABEL_FROM);
		lbUp.setText(AbosMessages.get().LABEL_UP);
		link.setText("<a>" + MessageUtil.unescape(AbosMessages.get().LABEL_ADVANCED_SEARCH) + "</a>");
		btnConsult.setText(AbosMessages.get().BUTTON_CONSULT);
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		lbCoincidenceList.setText(AbosMessages.get().LABEL_COINCIDENCE_LIST);
		lbUserLoanData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA));
		lbUserCode.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		lbUserType.setText(AbosMessages.get().LABEL_TYPE_OF_USER);
		lbRangeDate.setText(AbosMessages.get().LABEL_DATE_OF_REGISTRATION_RANGE);
		lbLocation.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LOCATION));
		initialize(comboLocation, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findRoomByLibrary(library.getLibraryID()));

		tabla.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER), AbosMessages.get().LABEL_OBJECT_TYPE, AbosMessages.get().LABEL_STATE, AbosMessages.get().TABLE_NAME_AND_LAST_NAME,
				MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE)));
		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);
		tabla.l10n();
		initialize(comboUserType, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOANUSER_TYPE));
		initialize(comboObjectType, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOANOBJECT_TYPE));
		initialize(comboState, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOAN_STATE));
		initialize(comboLoanType, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOAN_TYPE));

		refresh();

	}

	@Override
	public void setViewController(ViewController controller) {
		super.setViewController(controller);
	}
}