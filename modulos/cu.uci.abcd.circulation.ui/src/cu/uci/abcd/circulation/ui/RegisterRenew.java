package cu.uci.abcd.circulation.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.auxiliary.Auxiliary;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.model.ViewAreaTransactions;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class RegisterRenew extends ContributorPage implements Contributor {

	private Date salida = null;
	int tempo = 0;
	String orderByStringTransactions = "transactionID";
	int direction = 1024;
	private Label msjeListRenew;
	private CRUDTreeTable tableLoanObjectCRUD;
	private List<Transaction> listEntityTransactions = new ArrayList<>();
	private List<Transaction> listTransactions = new ArrayList<>();
	private List<LoanObject> lista_de_objetos = new ArrayList<LoanObject>();
	private List<Transaction> lista_de_objetos_BD = new ArrayList<Transaction>();
	private List<LoanObject> lista_de_objetos_Visual = new ArrayList<LoanObject>();
	private List<LoanObject> lista_de_objetos_Completos = new ArrayList<LoanObject>();
	private int cantLoanLista = 0;
	private Library library;
	private User user;
	private Button btnCancel;
	private Composite registerRenew;
	private List<Room> listWorkerRooms;
	private List<Integer> listIDRoomWorker;
	private int dimension;

	private Label lbRegisterRenew;
	private Label lbsearchTransaction;
	private Text txtSearchTransaction;
	private Button searchButton;

	private String params = null;
	private List<cu.uci.abcd.domain.management.library.Calendar> listCalendar;
	private List<Schedule> listHorary;
	private List<Reservation> listReservations = new ArrayList<>();
	private Composite msg;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().NAME_UI_RENEWAL);
	}

	@Override
	public String getID() {
		return "addRenewID";
	}

	// FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(final Composite parent) {

		dimension = parent.getParent().getParent().getParent().getBounds().width;
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");	     
		      
		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 130);
		
		msg = new Composite(parent, SWT.NORMAL);
		msg.setLayout(new FormLayout());
		msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(msg).atTopTo(parent).withWidth(340).withHeight(50).atRight(0);


		user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");

		if (user.getPerson() == null) {			
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);			
		}
		else{
		
		long idPerson = user.getPerson().getPersonID();

		Worker workerLoggin = ((AllManagementLoanUserViewController) controller).getManagePerson().findWorkerbyPersonID(idPerson);

		listCalendar = (List<cu.uci.abcd.domain.management.library.Calendar>) ((AllManagementLoanUserViewController) controller).getManageTransaction().findCalendar(library.getLibraryID());
		listHorary = ((AllManagementLoanUserViewController) controller).getManageTransaction().findHorarybyLibrary(library.getLibraryID());

		if (workerLoggin == null) {
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);
		}
		else if (listHorary.isEmpty()) {
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_LIBRARY_NOT_HORARY);
		} else {

			listWorkerRooms = workerLoggin.getRooms();
			

			registerRenew = new Composite(parent, SWT.NONE);
			registerRenew.setData(RWT.CUSTOM_VARIANT, "gray_background");
			addComposite(registerRenew);			

			lbRegisterRenew = new Label(registerRenew, 0);
			addHeader(lbRegisterRenew);

			Label separatorHeader = new Label(registerRenew,  SWT.SEPARATOR | SWT.HORIZONTAL);
			addSeparator(separatorHeader);
		
			lbsearchTransaction = new Label(registerRenew, 0);
			add(lbsearchTransaction);
			FormDatas.attach(lbsearchTransaction).atTopTo(separatorHeader, 19).atLeft(15);

			txtSearchTransaction = new Text(registerRenew, 0);
			add(txtSearchTransaction, Percent.W40);
			FormDatas.attach(txtSearchTransaction).atTopTo(separatorHeader, 15).atLeftTo(lbsearchTransaction, 10).withHeight(12).withWidth(410);

			searchButton = new Button(registerRenew, SWT.PUSH);
			FormDatas.attach(searchButton).atLeftTo(txtSearchTransaction, 10).atTopTo(separatorHeader, 15).withHeight(22);

			br();
			
			txtSearchTransaction.addFocusListener(new FocusListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void focusLost(FocusEvent arg0) {
					searchButton.getShell().setDefaultButton(null);
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
					searchButton.getShell().setDefaultButton(searchButton);
				}
			});
			
			msjeListRenew = new Label(registerRenew, SWT.None);
			addHeader(msjeListRenew);
			msjeListRenew.setVisible(false);

			tableLoanObjectCRUD = new CRUDTreeTable(registerRenew, SWT.NONE);
			add(tableLoanObjectCRUD);
			tableLoanObjectCRUD.setEntityClass(Transaction.class);
			tableLoanObjectCRUD.setPageSize(10);
			tableLoanObjectCRUD.setWatch(true, new ViewAreaTransactions(controller, this,tableLoanObjectCRUD));
			tableLoanObjectCRUD.setVisible(false);
    
			tableLoanObjectCRUD.addListener(SWT.Resize, new Listener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void handleEvent(Event arg0) {
					refresh();
				}
			});

			Column columnRenew = new Column("renew", registerRenew.getDisplay(), new TreeColumnListener() {
				
				public void handleEvent(final TreeColumnEvent event) {
					
					Transaction entity = (Transaction) event.entity.getRow();
					
					Nomenclator penaltyTypeSuspension = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_TYPE_SUSPENCION);
					Nomenclator penaltyStateActive = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_STATE_ACTIVE);
					List<Penalty> listPenaltyByLoanUser = ((AllManagementLoanUserViewController) controller).getManagePenalty().findPenaltyByLoanUserIdAndPenaltyTypeAndPenaltyState(entity.getLoanUser().getPersonID(), penaltyTypeSuspension, penaltyStateActive);

					Nomenclator penaltyTypeFine = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_TYPE_FINE);
					Nomenclator penaltyStatePengingPaid = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_STATE_PENDING_PAYMENT);
					List<Penalty> listPenaltyByLoanUserFine = ((AllManagementLoanUserViewController) controller).getManagePenalty().findPenaltyByLoanUserIdAndPenaltyTypeAndPenaltyState(entity.getLoanUser().getPersonID(), penaltyTypeFine, penaltyStatePengingPaid);

					if (listPenaltyByLoanUser.size() == 0 && listPenaltyByLoanUserFine.size() == 0) {
					
					MessageDialogUtil.openConfirm(
							Display.getCurrent().getActiveShell(),
							MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MESSAGE_QUESTION),
							MessageUtil
									.unescape(AbosMessages.get().MSG_USER_SUCURE_RENEW) + " " +entity.getLoanObject().getTitle() + " " +AbosMessages.get().MSG_USER_SUCURE_ASSOCIATE + " " + entity.getLoanUser().fullName()+ "?",
							new DialogCallback() {
										
			private static final long serialVersionUID = 1L;
		
				@Override
				public void dialogClosed(int returnCode) {
					if (returnCode == 0) {					
							
					listTransactions.clear();

					Transaction entity = (Transaction) event.entity.getRow();

					Nomenclator loanUserType = entity.getLoanUser().getLoanUserType();
					Nomenclator recordType = entity.getLoanObject().getRecordType();
					Nomenclator loanState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOAN_STATE_RENEW);
					Nomenclator circulationRuleState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.CIRCULATION_RULE_STATE_ACTIVE);

					List<Transaction> transactionsDB = ((AllManagementLoanUserViewController) controller).getManageTransaction().findTransactionByStateAndLoanUserAndLoanObject_RecordType(loanState, entity.getLoanUser(), recordType);

					Long idLibrary = library.getLibraryID();

					CirculationRule circulationRule = ((AllManagementLoanUserViewController) controller).getManageTransaction().findCirculationRule(circulationRuleState, loanUserType, recordType, idLibrary);

					if (circulationRule == null) {
						RetroalimentationUtils.showInformationMessage(msg, MessageUtil.unescape(AbosMessages.get().MSJE_LOAN_RULE_NOT_EXIST));

					} else {
						int cantRenew = circulationRule.getQuantityOfRenewedAllowed();
						int cantDays = circulationRule.getQuantityOfRenewedDayAllowed();

						listTransactions.add(entity);
						lista_de_objetos_BD = listTransactions;
						List<LoanObject> abel = Auxiliary.TransactionBD(lista_de_objetos_BD);
						lista_de_objetos_Visual = lista_de_objetos;
						lista_de_objetos_Completos = Auxiliary.concatenar(abel, lista_de_objetos_Visual);

						cantLoanLista = Auxiliary.cantidad_de_elementos(lista_de_objetos_Completos, recordType.getNomenclatorID());

						if (cantRenew <= transactionsDB.size() || cantRenew < cantLoanLista) {

							RetroalimentationUtils.showInformationMessage(msg, MessageUtil.unescape(AbosMessages.get().MSJE_NOT_MORE_RENEW));

						} else {

							Nomenclator reservationState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.RESERVATION_STATE_PENDING);
							List<Reservation> listR = ((AllManagementLoanUserViewController) controller).getManageReservation().findReservationByState(reservationState);
							listReservations = Auxiliary.listReservationLoanObject(listR, entity.getLoanObject().getLoanObjectID());

							java.util.Date fecha = new java.util.Date();
							Date startDate = new Date(fecha.getTime());

							java.util.Date ultimo = Auxiliary.addDays(listCalendar, listHorary, entity.getEndTransactionDate(), cantDays+1, library.getLibraryID(), listReservations);
							salida = new Date(ultimo.getTime());

							Transaction transactionParent = entity;
							transactionParent.setIsparent(true);
							((AllManagementLoanUserViewController) controller).getManageTransaction().addTransaction(transactionParent);

							Transaction transaction = new Transaction();
							transaction.setLoanUser(entity.getLoanUser());
							transaction.setLoanObject(entity.getLoanObject());
							transaction.setLoanType(entity.getLoanType());
							transaction.setState(loanState);
							transaction.setTransactionDateTime(startDate);
							transaction.setParenttransaction(entity);
							transaction.setEndTransactionDate(salida);
							transaction.setIsparent(false);
							transaction.setLibrarian(user);
							if (entity.getReservation() != null) {
								transaction.setReservation(entity.getReservation());
							}
							((AllManagementLoanUserViewController) controller).getManageTransaction().addTransaction(transaction);

							searchTransaction(0, tableLoanObjectCRUD.getPageSize());
							tableLoanObjectCRUD.refresh();

							RetroalimentationUtils.showInformationMessage(msg, MessageUtil.unescape(AbosMessages.get().MSJE_RENEW_SUCCESS));

						}
					}
					}
					
				}
				});
			
			}
				 else				
					RetroalimentationUtils.showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_SANCTIONED_USER);

			}});
		

			columnRenew.setToolTipText(AbosMessages.get().BUTTON_RENEW);
			columnRenew.setAlignment(SWT.CENTER);
		
			tableLoanObjectCRUD.addActionColumn(columnRenew);

			TreeTableColumn columns[] = { new TreeTableColumn(16, 0, "getLoanObject.getTitle"), new TreeTableColumn(16, 1, "getLoanObject.getInventorynumber"), new TreeTableColumn(16, 2, "getLoanObject.getRecordType.getNomenclatorName"), new TreeTableColumn(16, 3, "getState.getNomenclatorName"),
					new TreeTableColumn(16, 4, "getLoanUser.fullName"), new TreeTableColumn(16, 5, "getLoanUser.getLoanUserCode"), };

			tableLoanObjectCRUD.createTable(columns);

			/*tableLoanObjectCRUD.setActionDenied(new IActionDenied() {
				@Override
				public boolean isDenied(Column column, Row row) {
					Transaction entity = (Transaction) row;
					if (column.getIndex() == 6) {
						if (!listIDRoomWorker.contains((int) (long) entity.getLoanObject().getRoom().getRowID())) {
							return true;
						}
					}
					return false;
				}
			});*/

			tableLoanObjectCRUD.addPageChangeListener(new PageChangeListener() {
				@Override
				public void pageChanged(final PageChangedEvent event) {
		
					searchTransaction(event.currentPage - 1, event.pageSize);
				}
			});

			searchButton.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = -8115718469128343452L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					msjeListRenew.setVisible(true);
					tableLoanObjectCRUD.setVisible(true);
					btnCancel.setVisible(true);
					tableLoanObjectCRUD.clearRows();

					params = (txtSearchTransaction.getText().length() > 0 ? txtSearchTransaction.getText().replaceAll(" +", " ").trim() : null);

					orderByStringTransactions = "transactionID";
					direction = 1024;
					searchTransaction(0, tableLoanObjectCRUD.getPageSize());
					if (tableLoanObjectCRUD.getRows().isEmpty()) {
						tableLoanObjectCRUD.setVisible(false);
						msjeListRenew.setVisible(false);
						btnCancel.setVisible(false);
						RetroalimentationUtils.showInformationMessage(msg, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					} else
						tableLoanObjectCRUD.getPaginator().goToFirstPage();

					refresh();

				}
			});

			br();
			btnCancel = new Button(registerRenew, SWT.PUSH);
			add(btnCancel);
			btnCancel.setVisible(false);

			l10n();

			btnCancel.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {

						private static final long serialVersionUID = 1L;

						@Override
						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
							
								RegisterRenew.this.notifyListeners(SWT.Dispose, new Event());
							
							}
						}
					});
				}
			});

		
		l10n();
		}}
		return parent;
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {

		lbRegisterRenew.setText(MessageUtil.unescape(AbosMessages.get().NAME_UI_REGISTER_RENEWAL));
		lbsearchTransaction.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_TRANSACTION));
		txtSearchTransaction.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));

		searchButton.setText(AbosMessages.get().BUTTON_SEARCH);
		msjeListRenew.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_OBJECTS_TO_RENEW));

		btnCancel.setText(AbosMessages.get().BUTTON_CANCEL);
		tableLoanObjectCRUD.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		tableLoanObjectCRUD.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER), AbosMessages.get().LABEL_OBJECT_TYPE, AbosMessages.get().LABEL_STATE, AbosMessages.get().TABLE_NAME_AND_LAST_NAME,
				MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE)));

		refresh();
	}

	@Override
	public void setViewController(ViewController controller) {
		super.setViewController(controller);
	}

	public void searchTransaction(int page, int size) {
		tableLoanObjectCRUD.clearRows();

		Page<Transaction> list = ((AllManagementLoanUserViewController) controller)
				.getManageTransaction().findAllTransactionByRegisterReturn(
						params,library,listWorkerRooms, page, size, size, orderByStringTransactions);

		tableLoanObjectCRUD.setRows(list.getContent());
		tableLoanObjectCRUD.setTotalElements((int) list.getTotalElements());
		tableLoanObjectCRUD.refresh();
	}

	public List<Transaction> getListEntityTransactions() {
		return listEntityTransactions;
	}

	public void setListEntityTransactions(List<Transaction> listEntityTransactions) {
		this.listEntityTransactions = listEntityTransactions;
	}

	public CRUDTreeTable getTableLoanObjectCRUD() {
		return tableLoanObjectCRUD;
	}

}