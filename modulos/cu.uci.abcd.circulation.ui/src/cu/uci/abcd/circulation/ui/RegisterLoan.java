package cu.uci.abcd.circulation.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.auxiliary.AssociateLoanUserFragment;
import cu.uci.abcd.circulation.ui.auxiliary.Auxiliary;
import cu.uci.abcd.circulation.ui.auxiliary.ViewTransactionsFragment;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.domain.circulation.LoanUser;
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
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class RegisterLoan extends ContributorPage implements Contributor {

	private List<LoanObject> temListObjects;
	private List<String> controlNumbers = new ArrayList<String>();

	private List<Transaction> listEntityTransactionsBd;

	private Composite compoRegister;
	private Label lbLoanType;
	public Button rdbExternal;
	public Button rdbInternal;
	public Button rdbInterB;

	public Button getRdbInterB() {
		return rdbInterB;
	}

	private int loan = 0;
	private List<LoanObject> listDB;
	private List<LoanObject> listDBok;
	private CRUDTreeTable tableSearchLoanObject;
	private CRUDTreeTable tableListObjectToLoan;

	private List<IGridViewEntity> list1 = new ArrayList<IGridViewEntity>();

	private Composite compoGroupLoanObject;

	private Label lbMsjSearchLoanObject;
	private Text txtSearchLoanObject;
	private Button searchButtonLoanObject;

	private Label lbCoincidenceListLO;
	private Button unAssociateButtonLO;
	private Composite compoLoanObject;
	private Button btnAdd;
	private Button btnLoan;
	private Button btnCancel;
	private Label lbListObjectLoan;
	private String orderByString = "loanUserCode";
	private int direction = 1024;
	private Library library;
	private Transaction transaction;
	private Nomenclator loanUserType;
	private Nomenclator recordType;
	private Long idLibrary;
	private CirculationRule circulationRule;
	private int page = 0;
	private int size = 10;

	private int sizeLoanObject = 50;
	
	private String orderByStringTransactions = "transactionID";
	private String lastStringLoanObject = "";
	private Group loanObjectGroup;
	private List<LoanObject> entityLoanObjects;

	private List<Transaction> listEntityTransactions = new ArrayList<Transaction>();

	private Composite viewTransactionSave;
	private ViewTransactionsFragment viewTransactionsFragment;

	private Label separador;
	private int cantDays = 0;
	private int cantLoan = 0;
	private int cantLoanLista = 0;

	private String inventory_number = null;
	private String orderByStringLoanObject = "title";
	private Date salida = null;
	private LoanUser loanUser;
	private LoanObject loanObject;
	private LoanUser representant = null;
	private AssociateLoanUserFragment associateLoanUserFragment;
	private Composite associatePersonComposite;
	private Transaction transactionSaved;
	private int temp;
	private List<Room> listWorkerRooms;
	private List<String> leftListLoanObject = new ArrayList<String>();
	private List<Transaction> listLoanObject = null;
	private Composite compoParent;
	private Composite compoFather;
	private Label lbRegisterLoan;
	private List<Reservation> listDateReservations;
	private List<LoanObject> lista_de_objetos = new ArrayList<>();
	private List<LoanObject> lista_de_objetos_Visual;
	private List<LoanObject> listObjectOrdenados;
	private List<Transaction> lista_de_objetos_BD;
	private List<LoanObject> lista_de_objetos_Completos;
	private int dimension;
	private User user = null;
	
	private Map<String, Control> controls = new HashMap<String, Control>();
	private List<cu.uci.abcd.domain.management.library.Calendar> listCalendar;
	private List<Schedule> listHorary;

	private List<Control> grupControlsLoanObject = new ArrayList<>();

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().NAME_UI_LOAN);
	}

	@Override
	public String getID() {
		return "addLoanID";
	}

	@SuppressWarnings("serial")
	@Override
	// FIXME METODO COMPLEJO
	public Control createUIControl(final Composite parent) {

		dimension = parent.getParent().getParent().getParent().getBounds().width;

		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 60);

		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");

		if (user.getPerson() == null) {
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);
			// MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(),
			// MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION),
			// MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT),
			// null);
			// RegisterLoan.this.notifyListeners(SWT.Dispose, new Event());
		} else {
			long idPerson = user.getPerson().getPersonID();
			idLibrary = library.getLibraryID();

			Worker workerLoggin = ((AllManagementLoanUserViewController) controller).getManagePerson().findWorkerbyPersonID(idPerson);

			listCalendar = (List<cu.uci.abcd.domain.management.library.Calendar>) ((AllManagementLoanUserViewController) controller).getManageTransaction().findCalendar(idLibrary);
			listHorary = ((AllManagementLoanUserViewController) controller).getManageTransaction().findHorarybyLibrary(idLibrary);

			if (workerLoggin == null) {
				RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);
			} else if (listHorary.isEmpty()) {
				RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_LIBRARY_NOT_HORARY);
			} else {

				listWorkerRooms = workerLoggin.getRooms();
				
				compoFather = new Composite(parent, SWT.NORMAL);
				addComposite(compoFather);
				compoFather.setData(RWT.CUSTOM_VARIANT, "gray_background");

				compoRegister = new Composite(compoFather, SWT.NORMAL);
				addComposite(compoRegister);
				compoRegister.setData(RWT.CUSTOM_VARIANT, "gray_background");

				lbRegisterLoan = new Label(compoRegister, SWT.None);
				addHeader(lbRegisterLoan);

				Label separatorHeader = new Label(compoRegister, SWT.SEPARATOR | SWT.HORIZONTAL);
				addSeparator(separatorHeader);

				lbLoanType = new Label(compoRegister, SWT.None);
				add(lbLoanType);
				FormDatas.attach(lbLoanType).atLeft(20).atTopTo(separatorHeader, 15);
				br();

				if (dimension < 840) {
					rdbInternal = new Button(compoRegister, SWT.RADIO);
					// add(rdbInternal);
					FormDatas.attach(rdbInternal).atLeft(20).atTopTo(lbLoanType, 15);
					rdbInternal.setSelection(true);

					rdbExternal = new Button(compoRegister, SWT.RADIO);
					// add(rdbExternal);
					FormDatas.attach(rdbExternal).atLeftTo(rdbInternal, 150).atTopTo(lbLoanType, 15);

					rdbInterB = new Button(compoRegister, SWT.RADIO);
					// add(rdbInterB);
					FormDatas.attach(rdbInterB).atLeftTo(rdbExternal, 150).atTopTo(lbLoanType, 15);

				} else {
					rdbInternal = new Button(compoRegister, SWT.RADIO);
					add(rdbInternal);
					// FormDatas.attach(rdbInternal).atLeft(20).atTopTo(lbLoanType,
					// 15);
					rdbInternal.setSelection(true);

					rdbExternal = new Button(compoRegister, SWT.RADIO);
					add(rdbExternal);
					// FormDatas.attach(rdbExternal).atLeftTo(rdbInternal,
					// 150).atTopTo(lbLoanType, 15);

					rdbInterB = new Button(compoRegister, SWT.RADIO);
					add(rdbInterB);
					// FormDatas.attach(rdbInterB).atLeftTo(rdbExternal,
					// 150).atTopTo(lbLoanType, 15);
				}
				add(new Label(compoRegister, 0), Percent.W100);

				separador = new Label(compoRegister, SWT.SEPARATOR | SWT.HORIZONTAL);
				addSeparator(separador);

				// ***************Loan
				// User****************************************

				compoParent = new Composite(compoFather, SWT.NORMAL);
				addComposite(compoParent);
				compoParent.setData(RWT.CUSTOM_VARIANT, "gray_background");

				compoLoanObject = new Composite(compoParent, SWT.NORMAL);

				createComponentLoanUser(compoFather);

				// **************************Loan
				// Object****************************

				compoLoanObject.setData(RWT.CUSTOM_VARIANT, "gray_background");
				addComposite(compoLoanObject);
				compoLoanObject.setVisible(false);

				Label separador1 = new Label(compoLoanObject, SWT.SEPARATOR | SWT.HORIZONTAL);
				addSeparator(separador1);

				lbMsjSearchLoanObject = new Label(compoLoanObject, SWT.NONE);
				add(lbMsjSearchLoanObject);
				FormDatas.attach(lbMsjSearchLoanObject).atLeft(10).atTopTo(separador1, 19);

				txtSearchLoanObject = new Text(compoLoanObject, 0);
				add(txtSearchLoanObject);
				FormDatas.attach(txtSearchLoanObject).atLeftTo(lbMsjSearchLoanObject, 10).atTopTo(separador1, 17).withWidth(250).withHeight(10);

				txtSearchLoanObject.addFocusListener(new FocusListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void focusLost(FocusEvent arg0) {
						searchButtonLoanObject.getShell().setDefaultButton(null);
					}

					@Override
					public void focusGained(FocusEvent arg0) {
						searchButtonLoanObject.getShell().setDefaultButton(searchButtonLoanObject);
					}
				});

				searchButtonLoanObject = new Button(compoLoanObject, SWT.PUSH);
				FormDatas.attach(searchButtonLoanObject).atLeftTo(txtSearchLoanObject, 10).atTopTo(separador1, 17).withHeight(22);

				searchButtonLoanObject.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						lbCoincidenceListLO.setVisible(true);
						tableSearchLoanObject.setVisible(true);
						tableSearchLoanObject.clearRows();

						inventory_number = txtSearchLoanObject.getText().replaceAll(" +", " ").trim();

						direction = 1024;
						loanObjectOrderBycontrolNumber(0, tableSearchLoanObject.getPageSize());
						if (tableSearchLoanObject.getRows().isEmpty()) {
							tableSearchLoanObject.setVisible(false);
							lbCoincidenceListLO.setVisible(false);
							RetroalimentationUtils.showInformationShellMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
						} else
							tableSearchLoanObject.getPaginator().goToFirstPage();

						txtSearchLoanObject.setText("");
						tableSearchLoanObject.refresh();

						refresh();
					};
				});

				lbCoincidenceListLO = new Label(compoLoanObject, SWT.NONE);
				addHeader(lbCoincidenceListLO);

				lbCoincidenceListLO.setVisible(false);

				// -------------Tabla de buscar los Objetos de Prestamo
				tableSearchLoanObject = new CRUDTreeTable(compoLoanObject, SWT.NONE);
				add(tableSearchLoanObject);
				tableSearchLoanObject.setVisible(false);
				tableSearchLoanObject.setEntityClass(LoanObject.class);

				tableSearchLoanObject.addListener(SWT.Resize, new Listener() {

					@Override
					public void handleEvent(Event arg0) {
						refresh();
					}
				});

				Column columnAssociate = new Column("associate", compoLoanObject.getDisplay(), new TreeColumnListener() {

					public void handleEvent(TreeColumnEvent event) {
						Nomenclator circulationRuleState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.CIRCULATION_RULE_STATE_ACTIVE);

						LoanObject entity = (LoanObject) event.entity.getRow();
						loanObject = entity;

						loanUser = associateLoanUserFragment.getLoanUser();

						loanUserType = loanUser.getLoanUserType();
						recordType = entity.getRecordType();
						idLibrary = library.getLibraryID();
						int cont = 0;
						circulationRule = ((AllManagementLoanUserViewController) controller).getManageTransaction().findCirculationRule(circulationRuleState, loanUserType, recordType, idLibrary);

						if (circulationRule == null) {
							RetroalimentationUtils.showInformationShellMessage(AbosMessages.get().MSJE_LOAN_RULE_NOT_EXIST);
						} else {
							cantDays = circulationRule.getQuantityDayOnLoan();
							cantLoan = circulationRule.getQuantityOfLoanAllowed();

							lista_de_objetos_BD = initializeGridTransaction(loanUser);
							List<LoanObject> abel = Auxiliary.TransactionBD(lista_de_objetos_BD);
							lista_de_objetos_Visual = lista_de_objetos;
							lista_de_objetos_Completos = Auxiliary.concatenar(abel, lista_de_objetos_Visual);
							cantLoanLista = Auxiliary.cantidad_de_elementos(lista_de_objetos_Completos, recordType.getNomenclatorID());

							if (cantLoan <= cantLoanLista) {
								RetroalimentationUtils.showInformationShellMessage(AbosMessages.get().MSJE_NOT_MORE_LOANS);
							} else {
								if (cantLoanLista == 0 || circulationRule.isSeveralMaterialsAllowed() == true) {
									showDataLoanObject();
									refresh();
									compoGroupLoanObject.setVisible(true);

									tableSearchLoanObject.setVisible(false);
									txtSearchLoanObject.setVisible(false);
									searchButtonLoanObject.setVisible(false);
									lbCoincidenceListLO.setVisible(false);
									lbMsjSearchLoanObject.setVisible(false);
								} else {
									for (int i = 0; i < lista_de_objetos_Completos.size(); i++) {
										if (lista_de_objetos_Completos.get(i).getControlNumber().equals(loanObject.getControlNumber())) {
											cont++;
										}
									}
									if (cont != 0) {
										RetroalimentationUtils.showInformationShellMessage(AbosMessages.get().MSJE_NOT_LOAN_COPIES);

									} else {
										showDataLoanObject();
										refresh();
										compoGroupLoanObject.setVisible(true);

										tableSearchLoanObject.setVisible(false);
										txtSearchLoanObject.setVisible(false);
										searchButtonLoanObject.setVisible(false);
										lbCoincidenceListLO.setVisible(false);
										lbMsjSearchLoanObject.setVisible(false);
									}
								}
							}
						}
					}
				});

				columnAssociate.setToolTipText(AbosMessages.get().BUTTON_ASSOCIATE);
				columnAssociate.setAlignment(SWT.CENTER);

				tableSearchLoanObject.addActionColumn(columnAssociate);

				TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getTitle"), new TreeTableColumn(20, 1, "getAuthor"), new TreeTableColumn(20, 2, "getRecordType.getNomenclatorName"), new TreeTableColumn(20, 3, "getControlNumber"), new TreeTableColumn(20, 4, "getBookAvailable") };

				tableSearchLoanObject.createTable(columns);
				tableSearchLoanObject.setPageSize(10);

				tableSearchLoanObject.addPageChangeListener(new PageChangeListener() {
					@Override
					public void pageChanged(final PageChangedEvent event) {

						loanObjectOrderBycontrolNumber(event.currentPage - 1, event.pageSize);

					}
				});

			/*	tableSearchLoanObject.setActionDenied(new IActionDenied() {
					@Override
					public boolean isDenied(Column column, Row row) {
						LoanObject entity = (LoanObject) row;
						if (column.getIndex() == 5) {
							if (!listIDRoomWorker.contains((int) (long) entity.getRoom().getRowID())) {
								return true;
							}
						}
						return false;
					}
				});*/
				// -------------------------------End Table

				// -----------------Table
				// ListObjectToLoan--------------------------------------

				lbListObjectLoan = new Label(compoLoanObject, SWT.None);
				addHeader(lbListObjectLoan);
				lbListObjectLoan.setVisible(false);

				tableListObjectToLoan = new CRUDTreeTable(compoLoanObject, SWT.NONE);
				add(tableListObjectToLoan);
				tableListObjectToLoan.setEntityClass(Transaction.class);
				tableListObjectToLoan.setVisible(false);

				tableListObjectToLoan.addListener(SWT.Resize, new Listener() {

					@Override
					public void handleEvent(Event arg0) {
						refresh();
					}
				});

				tableListObjectToLoan.addPageChangeListener(new PageChangeListener() {
					@Override
					public void pageChanged(final PageChangedEvent event) {
						pageTableListObjectToLoan(event.currentPage - 1, event.pageSize);
					}
				});

				Column columnDissociate = new Column("dissociate", compoLoanObject.getDisplay(), new TreeColumnListener() {

					public void handleEvent(TreeColumnEvent event) {

						Transaction entity = (Transaction) event.entity.getRow();

						LoanObject loanObjectD = ((AllManagementLoanUserViewController) controller).getManageObject().findOneLoanObject(entity.getLoanObject().getLoanObjectID());
						loanObjectD.setChecked(false);

						((AllManagementLoanUserViewController) controller).getManageObject().addLoanObject(loanObjectD);

						listEntityTransactions.remove(entity);
						lista_de_objetos_Visual.remove(entity.getLoanObject());
						event.performDelete = true;
						loanObjectOrderBycontrolNumber(0, tableSearchLoanObject.getPageSize());
						tableListObjectToLoan.clearRows();
						tableSearchLoanObject.refresh();

						pageTableListObjectToLoan(0, tableListObjectToLoan.getPageSize());
						tableListObjectToLoan.getPaginator().goToFirstPage();

						tableSearchLoanObject.setTotalElements((int) temListObjects.size());

						if (tableListObjectToLoan.getEntities().isEmpty())
							btnLoan.setEnabled(false);

					}
				});

				columnDissociate.setToolTipText(AbosMessages.get().BUTTON_DISSOCIATE);
				columnDissociate.setAlignment(SWT.CENTER);

				tableListObjectToLoan.addActionColumn(columnDissociate);

				TreeTableColumn columns1[] = { new TreeTableColumn(20, 0, "getLoanObject.getTitle"), new TreeTableColumn(20, 1, "getLoanObject.getAuthor"), new TreeTableColumn(20, 2, "getLoanObject.getInventorynumber"), new TreeTableColumn(20, 3, "getLoanUser.fullName"),
						new TreeTableColumn(20, 4, "getLoanUser.getLoanUserCode") };

				tableListObjectToLoan.createTable(columns1);

				br();

				btnCancel = new Button(compoLoanObject, SWT.PUSH);
				add(btnCancel);
				btnLoan = new Button(compoLoanObject, SWT.PUSH);
				add(btnLoan);
				br();
				add(new Label(compoLoanObject, 0), Percent.W100);

				btnLoan.setVisible(false);
				btnCancel.setVisible(false);

				// RF_CI8.2_Registrar Prestamo de Objeto sin Reservacion
				btnLoan.addSelectionListener(new SelectionAdapter() {
					private static final long serialVersionUID = 1L;

					@Override
					public void widgetSelected(SelectionEvent e) {

						listLoanObject = new ArrayList<>();

						java.util.Date fecha = new java.util.Date();
						Date dateSystem = new Date(fecha.getTime());

						list1 = tableListObjectToLoan.getRows();

						for (IGridViewEntity iGridViewEntity : list1) {

							Transaction a = (Transaction) iGridViewEntity.getRow();

							Nomenclator loanObjectState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOANOBJECT_STATE_BORROWED);
							Nomenclator reservationState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.RESERVATION_STATE_PENDING);
							Nomenclator loanState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOAN_STATE_BORROWED);

							List<Reservation> listR = ((AllManagementLoanUserViewController) controller).getManageReservation().findReservationByState(reservationState);
							listDateReservations = Auxiliary.listReservationLoanObject(listR, a.getLoanObject().getLoanObjectID());

							java.util.Date ultimo = Auxiliary.addDays(listCalendar, listHorary, dateSystem, cantDays, idLibrary, listDateReservations);
							salida = new Date(ultimo.getTime());

							Transaction transaction = new Transaction();
							transaction.setLoanUser(a.getLoanUser());
							transaction.setLoanObject(a.getLoanObject());
							transaction.setState(loanState);
							transaction.setTransactionDateTime(dateSystem);
							transaction.setEndTransactionDate(salida);
							transaction.setIsparent(false);
							transaction.setLibrarian(user);

							if (rdbInternal.getSelection() == true) {
								Nomenclator loanTypeIntern = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOAN_TYPE_INTERN);
								transaction.setLoanType(loanTypeIntern);
							} else if (rdbExternal.getSelection() == true) {
								Nomenclator loanTypeExtern = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOAN_TYPE_EXTERN);
								transaction.setLoanType(loanTypeExtern);
							} else if (rdbInterB.getSelection() == true) {
								Nomenclator loanTypeInterB = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOAN_TYPE_INTER_LIBRARY);
								transaction.setLoanType(loanTypeInterB);
							}

							transactionSaved = ((AllManagementLoanUserViewController) controller).getManageTransaction().addTransaction(transaction);

							LoanObject loanObjectLoan = ((AllManagementLoanUserViewController) controller).getManageObject().findOneLoanObject(transactionSaved.getLoanObject().getLoanObjectID());
							loanObjectLoan.setChecked(false);
							loanObjectLoan.setLoanObjectState(loanObjectState);
							((AllManagementLoanUserViewController) controller).getManageObject().addLoanObject(loanObjectLoan);

							listLoanObject.add(transactionSaved);
							loan = list1.size();
						}

						ContributorService contributorService = getContributorService();
						viewTransactionsFragment = new ViewTransactionsFragment(controller, listLoanObject, compoFather, dimension, RegisterLoan.this, contributorService);
						viewTransactionSave = (Composite) viewTransactionsFragment.createUIControl(parent);
						viewTransactionSave.setData(RWT.CUSTOM_VARIANT, "gray_background");

						compoFather.setVisible(false);
						viewTransactionSave.setVisible(true);

						tableListObjectToLoan.clearRows();
						listEntityTransactions.clear();
						lista_de_objetos_Visual.clear();

						RetroalimentationUtils.showInformationShellMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_REGISTER + " " + loan + " " + MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_REGISTER_LOAN));

						cleanComponentLoanUser();
						cleanComponent();
						createComponentLoanUser(compoRegister);
						createComponent();

						compoLoanObject.setVisible(false);
						compoFather.setVisible(false);
						compoGroupLoanObject.setVisible(false);

						lbListObjectLoan.setVisible(false);
						btnLoan.setVisible(false);
						btnCancel.setVisible(false);
						tableListObjectToLoan.setVisible(false);
					}
				});

				createComponent();
				compoGroupLoanObject.setVisible(false);

				btnCancel.addSelectionListener(new SelectionAdapter() {
					private static final long serialVersionUID = 1L;

					@Override
					public void widgetSelected(SelectionEvent e) {

						MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
							private static final long serialVersionUID = 1L;

							@Override
							public void dialogClosed(int returnCode) {
								if (returnCode == 0) {
									if (!listEntityTransactions.isEmpty()) {
										for (int i = 0; i < listEntityTransactions.size(); i++) {

											LoanObject loanObjectD = ((AllManagementLoanUserViewController) controller).getManageObject().findOneLoanObject(listEntityTransactions.get(i).getLoanObject().getLoanObjectID());
											loanObjectD.setChecked(false);

											((AllManagementLoanUserViewController) controller).getManageObject().addLoanObject(loanObjectD);

										}
										tableListObjectToLoan.clearRows();
										listEntityTransactions.clear();
										lista_de_objetos_Visual.clear();

									}
									RegisterLoan.this.notifyListeners(SWT.Dispose, new Event());
								}
							}
						});
					}
				});

				// Display.getCurrent().getActiveShell().setDefaultButton(searchButtonLoanObject);

				l10n();
			}
		}
		return parent;
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {

		rdbExternal.setText(AbosMessages.get().LABEL_EXTERNAL);
		rdbInternal.setText(AbosMessages.get().LABEL_INTERNAL);
		rdbInterB.setText(AbosMessages.get().LABEL_INTER_LIBRARY);
		lbLoanType.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_LOAN));

		lbMsjSearchLoanObject.setText(MessageUtil.unescape(AbosMessages.get().LABEL_MESSAGE_OBJECT_LOAN));
		searchButtonLoanObject.setText(AbosMessages.get().BUTTON_SEARCH);
		lbCoincidenceListLO.setText(AbosMessages.get().LABEL_COINCIDENCE_LIST);
		unAssociateButtonLO.setText(AbosMessages.get().BUTTON_DISSOCIATE);
		lbListObjectLoan.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_OBJECTS_TO_LOAN));
		txtSearchLoanObject.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		tableSearchLoanObject.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), AbosMessages.get().LABEL_AUTHOR, AbosMessages.get().LABEL_OBJECT_TYPE, MessageUtil.unescape(AbosMessages.get().LABEL_CONTROL_NUMBER), AbosMessages.get().TABLE_QUANTITY_AVAILABLE));

		btnAdd.setText(AbosMessages.get().BUTTON_ADD);
		btnLoan.setText(AbosMessages.get().BUTTON_LOAN);
		btnCancel.setText(AbosMessages.get().BUTTON_CANCEL);

		leftListLoanObject.clear();
		;
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
		leftListLoanObject.add(AbosMessages.get().LABEL_STATE_OF_OBJECT);

		lastStringLoanObject = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_RELATED_LOAN);
		tableListObjectToLoan.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), AbosMessages.get().LABEL_AUTHOR, MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER), AbosMessages.get().TABLE_NAME_AND_LAST_NAME,
				MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE)));

		lbRegisterLoan.setText(MessageUtil.unescape(AbosMessages.get().NAME_UI_REGISTER_LOAN));
		associateLoanUserFragment.l10n();

		if (!grupControlsLoanObject.isEmpty() && !grupControlsLoanObject.get(0).isDisposed())
			CompoundGroup.l10n(grupControlsLoanObject, leftListLoanObject);
		loanObjectGroup.setText(lastStringLoanObject);

		tableSearchLoanObject.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);

		refresh();

	}

	public void showDataLoanObject() {
		if (loanObject != null) {
			cleanComponent();
			createComponent();

			leftListLoanObject = new ArrayList<String>();
			leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
			leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);
			leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
			leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
			leftListLoanObject.add(AbosMessages.get().LABEL_STATE_OF_OBJECT);

			List<String> rigthListLoanObject = new ArrayList<String>();
			rigthListLoanObject.add(loanObject.getTitle());

			if (loanObject.getAuthor() == null) {
				rigthListLoanObject.add("-");
			} else
				rigthListLoanObject.add(loanObject.getAuthor());

			rigthListLoanObject.add(loanObject.getInventorynumber());
			rigthListLoanObject.add(loanObject.getRecordType().getNomenclatorName());
			rigthListLoanObject.add(loanObject.getLoanObjectState().getNomenclatorName());

			grupControlsLoanObject = CompoundGroup.printGroup(loanObjectGroup, lastStringLoanObject, leftListLoanObject, rigthListLoanObject);
			l10n();
		}
	}

	@Override
	public void setViewController(ViewController controller) {
		super.setViewController(controller);
	}

	public LoanObject getLoanObject() {
		return loanObject;
	}

	public void setLoanObject(LoanObject loanObject) {
		this.loanObject = loanObject;
	}

	@SuppressWarnings("serial")
	public void createComponent() {

		compoGroupLoanObject = new Composite(compoLoanObject, SWT.NONE);
		addComposite(compoGroupLoanObject);
		compoGroupLoanObject.setData(RWT.CUSTOM_VARIANT, "gray_background");
		compoGroupLoanObject.setVisible(true);

		// Gruop de Objeto de Prestamo
		loanObjectGroup = new Group(compoGroupLoanObject, SWT.NORMAL);
		// add(loanObjectGroup);
		if (dimension < 840) {
			FormDatas.attach(loanObjectGroup).withWidth(450).atLeft(15);
		} else
			FormDatas.attach(loanObjectGroup).withWidth(500).atLeft(15);
		loanObjectGroup.setVisible(true);

		br();

		btnAdd = new Button(compoGroupLoanObject, SWT.PUSH);
		FormDatas.attach(btnAdd).atLeftTo(loanObjectGroup, 10).atTopTo(compoGroupLoanObject, 160).withHeight(22);
		// add(btnAdd);
		btnAdd.setVisible(true);

		unAssociateButtonLO = new Button(compoGroupLoanObject, SWT.PUSH);
		// add(unAssociateButtonLO);
		FormDatas.attach(unAssociateButtonLO).atLeftTo(btnAdd, 10).atTopTo(compoGroupLoanObject, 160).withHeight(22);
		unAssociateButtonLO.setVisible(true);

		unAssociateButtonLO.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				loanObjectGroup.setVisible(false);
				unAssociateButtonLO.setVisible(false);
				btnAdd.setVisible(false);

				lbCoincidenceListLO.setVisible(true);
				tableSearchLoanObject.setVisible(true);
				txtSearchLoanObject.setVisible(true);
				searchButtonLoanObject.setVisible(true);
				lbMsjSearchLoanObject.setVisible(true);

				cleanComponent();
				refresh();

			}
		});

		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loanUser = associateLoanUserFragment.getLoanUser();

				LoanObject loanObjectS = loanObject;
				loanObjectS.setChecked(true);

				((AllManagementLoanUserViewController) controller).getManageObject().addLoanObject(loanObjectS);

				transaction = new Transaction();
				transaction.setLoanUser(loanUser);
				transaction.setLoanObject(loanObjectS);

				listEntityTransactions.add(transaction);
				lista_de_objetos.add(loanObjectS);

				btnLoan.setEnabled(true);
				ShowData();

				pageTableListObjectToLoan(0, tableListObjectToLoan.getPageSize());
				tableListObjectToLoan.getPaginator().goToFirstPage();

				loanObjectOrderBycontrolNumber(0, tableSearchLoanObject.getPageSize());
				tableSearchLoanObject.getPaginator().goToFirstPage();
				refresh();
			}
		});

		refresh();
	}

	public void cleanComponent() {
		try {
			Control[] temp = compoGroupLoanObject.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		}
	}

	private List<Transaction> initializeGridTransaction(LoanUser loanUserLoaded) {
		listEntityTransactionsBd = new ArrayList<Transaction>();
		Nomenclator stateLoan = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOAN_STATE_BORROWED);
		List<Transaction> list = ((AllManagementLoanUserViewController) controller).getManageTransaction().searchTransactionsLoanByLoanUser(loanUserLoaded, stateLoan);
		for (Transaction transaction : list) {
			listEntityTransactionsBd.add(transaction);
		}
		return listEntityTransactionsBd;
	}

	public void cleanComponentParent() {
		try {
			Control[] temp = compoFather.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		}
	}

	public void createComponentParent() {
		createUIControl(compoFather);
	}

	int tempo = 1;

	public void createComponentLoanUser(Composite compoSMS) {
		associatePersonComposite = new Composite(compoParent, SWT.NORMAL);
		addComposite(associatePersonComposite);
		associatePersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");

		associateLoanUserFragment = new AssociateLoanUserFragment(controller, representant, compoLoanObject, tempo, dimension, rdbInterB);

		TreeColumnListener treeColumnListener = new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				LoanUser loanUser = (LoanUser) event.entity.getRow();
				associateLoanUserFragment.setLoanUser(loanUser);

				Nomenclator penaltyTypeSuspension = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_TYPE_SUSPENCION);
				Nomenclator penaltyStateActive = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_STATE_ACTIVE);
				List<Penalty> listPenaltyByLoanUser = ((AllManagementLoanUserViewController) controller).getManagePenalty().findPenaltyByLoanUserIdAndPenaltyTypeAndPenaltyState(loanUser.getPersonID(), penaltyTypeSuspension, penaltyStateActive);

				Nomenclator penaltyTypeFine = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_TYPE_FINE);
				Nomenclator penaltyStatePengingPaid = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_STATE_PENDING_PAYMENT);
				List<Penalty> listPenaltyByLoanUserFine = ((AllManagementLoanUserViewController) controller).getManagePenalty().findPenaltyByLoanUserIdAndPenaltyTypeAndPenaltyState(loanUser.getPersonID(), penaltyTypeFine, penaltyStatePengingPaid);

				if (listPenaltyByLoanUser.size() == 0 && listPenaltyByLoanUserFine.size() == 0) {
					associateLoanUserFragment.showDataLoanUser(loanUser);
					compoLoanObject.setVisible(true);
				} else
					RetroalimentationUtils.showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_SANCTIONED_USER);
			}
		};
		associateLoanUserFragment.setTreeColumnListener(treeColumnListener);
		Composite a = (Composite) associateLoanUserFragment.createUIControl(associatePersonComposite);

	}

	public void cleanComponentLoanUser() {
		try {
			Control[] temp = associatePersonComposite.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		}
	}

	public void loanObjectOrderBycontrolNumber(int page, int size) {

		createLoanObjectsList();

		tableSearchLoanObject.setTotalElements((int) temListObjects.size());

		if (temListObjects.size() <= page * size + size) {
			tableSearchLoanObject.setRows(temListObjects.subList(page * size, temListObjects.size()));
		} else {
			tableSearchLoanObject.setRows(temListObjects.subList(page * size, page * size + size));
		}

		tableSearchLoanObject.refresh();

		/*
		 * 
		 * List<Long> listLoanObjectIDEnd = new ArrayList<>(); listDBok = new
		 * ArrayList<LoanObject>(); listObjectOrdenados = new
		 * ArrayList<LoanObject>();
		 * 
		 * Page<LoanObject> listDB1 = ((AllManagementLoanUserViewController)
		 * controller
		 * ).getManageObject().findAllLoanObjectByInventoryNumber(inventory_number
		 * , page, size, direction, orderByStringLoanObject);
		 * 
		 * listDB = listDB1.getContent();
		 * 
		 * for (int j = 0; j < listEntityTransactions.size(); j++) {
		 * listLoanObjectIDEnd
		 * .add(listEntityTransactions.get(j).getLoanObject().
		 * getLoanObjectID()); }
		 * 
		 * for (int i = 0; i < listDB.size(); i++) { if
		 * (listDB.get(i).isChecked() && listEntityTransactions.isEmpty()) {
		 * listDBok.add(listDB.get(i)); } else if (!listDB.get(i).isChecked()) {
		 * listDBok.add(listDB.get(i)); } else if (listDB.get(i).isChecked() &&
		 * !listLoanObjectIDEnd.contains(listDB.get(i).getLoanObjectID())) {
		 * listDBok.add(listDB.get(i)); } }
		 * 
		 * int cantCopias[] = new int[listDBok.size()]; int cont = 1; try {
		 * listObjectOrdenados.add(listDBok.get(0)); cantCopias[0] = cont; for
		 * (int i = 1; i < listDBok.size(); i++) {
		 * 
		 * if
		 * (listDBok.get(i).getControlNumber().equals(listObjectOrdenados.get(
		 * listObjectOrdenados.size() - 1).getControlNumber())) { cont++;
		 * cantCopias[listObjectOrdenados.size() - 1] = cont; } else {
		 * listObjectOrdenados.add(listDBok.get(i)); cont = 1;
		 * cantCopias[listObjectOrdenados.size() - 1] = cont; } } } catch
		 * (Exception e) { }
		 * 
		 * tableSearchLoanObject.clearRows(); entityLoanObjects = new
		 * ArrayList<LoanObject>(); tableSearchLoanObject.setTotalElements((int)
		 * listObjectOrdenados.size());
		 * 
		 * for (int i = 0; i < listObjectOrdenados.size(); i++) { LoanObject aux
		 * = new LoanObject(); aux = listObjectOrdenados.get(i);
		 * aux.setQuantity(cantCopias[i]); entityLoanObjects.add(aux); }
		 * 
		 * tableSearchLoanObject.setRows(entityLoanObjects);
		 * tableSearchLoanObject.refresh();
		 */

	}

	public void ShowData() {
		tableListObjectToLoan.setVisible(true);
		lbListObjectLoan.setVisible(true);
		btnLoan.setVisible(true);
		btnCancel.setVisible(true);
		txtSearchLoanObject.setVisible(true);
		searchButtonLoanObject.setVisible(true);
		lbMsjSearchLoanObject.setVisible(true);

		lbCoincidenceListLO.setVisible(true);
		tableSearchLoanObject.setVisible(true);

		loanObjectGroup.setVisible(false);
		unAssociateButtonLO.setVisible(false);
		btnAdd.setVisible(false);
	}

	public Map<String, Control> getControls() {
		return controls;
	}

	public void setControls(Map<String, Control> controls) {
		this.controls = controls;
	}

	private void pageTableListObjectToLoan(int page, int size) {

		tableListObjectToLoan.clearRows();

		tableListObjectToLoan.setTotalElements((int) listEntityTransactions.size());
		if (listEntityTransactions.size() <= page * size + size) {
			tableListObjectToLoan.setRows(listEntityTransactions.subList(page * size, listEntityTransactions.size()));
		} else {
			tableListObjectToLoan.setRows(listEntityTransactions.subList(page * size, page * size + size));
		}
		tableListObjectToLoan.refresh();
		tableSearchLoanObject.refresh();

	}

	private void createLoanObjectsList() {

		Page<LoanObject> listDB1 = ((AllManagementLoanUserViewController) controller)
				.getManageObject().findAllLoanObjectByInventoryNumber(
						inventory_number,library,listWorkerRooms, page, sizeLoanObject, direction,
						orderByStringLoanObject);

		List<LoanObject> listInventoryNumber = new ArrayList<LoanObject>();
		
		listInventoryNumber = listDB1.getContent();
		
		controlNumbers = ((AllManagementLoanUserViewController) controller).getManageObject().findAllControlNumbersFromAvailableLoanObjects(listInventoryNumber);

		List<LoanObject> temp = new ArrayList<LoanObject>();
		temListObjects = new ArrayList<LoanObject>();

		for (int i = 0; i < controlNumbers.size(); i++) {

			temp = ((AllManagementLoanUserViewController) controller).getManageObject().findAvailableControlNumberLoanObject(controlNumbers.get(i));

			if (!temp.isEmpty()) {
				try {
					temp = findInListToLoan(temp);
				} catch (Exception e) {

				}  

				if (!temp.isEmpty()) {
					temp.get(0).setQuantity(temp.size());
					temListObjects.add(temp.get(0));
				}
			}
		}
	}

	private List<LoanObject> findInListToLoan(List<LoanObject> temp) {

		for (int j = 0; j < temp.size(); j++) {
			
			for (int i = 0; i < listEntityTransactions.size(); i++)
				if (temp.get(j).getLoanObjectID().equals(listEntityTransactions.get(i).getLoanObject().getLoanObjectID()))
					temp.remove(j--);
					
		}
		return temp;
	}
}