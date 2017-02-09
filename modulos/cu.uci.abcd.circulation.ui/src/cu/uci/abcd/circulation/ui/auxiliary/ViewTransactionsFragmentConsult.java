package cu.uci.abcd.circulation.ui.auxiliary;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.ConsultTransaction;
import cu.uci.abcd.circulation.ui.RegisterRenew;
import cu.uci.abcd.circulation.ui.RegisterReturn;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.domain.cataloguing.CataloguingNomenclator;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.FineEquation;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewTransactionsFragmentConsult extends FragmentPage {
	private ViewController controller;
	private Transaction transaction;
	private PagePainter painter;
	private Date salida = null;

	private Button btnRegisterLost;

	private Group loanObjectGroup;
	private Group loanUserGroup;
	private Group transactionGroup;
	private Group reservationGroup;

	private Composite registerLoan;
	private Composite compoParent;
	private RegisterRenew registerRenew;
	private Label lbViewTransaction;
	private int dimension;
	private Library library;
	private IVisualEntityManager manager;
	private Transaction transactionData;
	private User userLoggin;
	private List<cu.uci.abcd.domain.management.library.Calendar> listCalendar;
	private List<Schedule> listHorary;
	private List<Reservation> listDateReservations = null;
	private ConsultTransaction consultTransaction;
	private CRUDTreeTable tabla;
	private CRUDTreeTable tablaRenew;
	private List<Control> grupControlsLoanUser = new ArrayList<>();
	private List<Control> grupControlsLoanObject = new ArrayList<>();
	private List<Control> grupControlsTransaction = new ArrayList<>();
	private List<Control> grupControlsReservation = new ArrayList<>();

	private List<String> leftListLoanUser = new LinkedList<>();
	private List<String> leftListTransaction = new LinkedList<>();
	private List<String> rigthListLoanUser = new LinkedList<>();
	private List<String> leftListLoanObject = new LinkedList<>();
	private List<String> leftListReservation = new LinkedList<>();

	private String lastStringLoanUser;
	private String lastStringTransaction;
	private String lastStringLoanObject;
	private String lastStringReservation;
	private User user;
	/**
	 * prueba
	 */
	
	RegisterReturn registerReturn;

	public ViewTransactionsFragmentConsult(ViewController controller) {
		this.setController(controller);
	}

	public ViewTransactionsFragmentConsult(ViewController controller, Transaction transaction) {
		this.setController(controller);
		this.setTransaction(transaction);
	}
    
	public ViewTransactionsFragmentConsult(ViewController controller, Transaction transaction, RegisterRenew registerRenew, int dimension, IVisualEntityManager manager, ConsultTransaction consultTransaction, CRUDTreeTable tabla, RegisterReturn registerReturn,CRUDTreeTable tablaRenew) {
		this.setController(controller);
		this.setTransaction(transaction);
		this.registerRenew = registerRenew;
		this.dimension = dimension;
		this.manager = manager;
		this.consultTransaction = consultTransaction;
		this.tabla = tabla;
		this.registerReturn = registerReturn;
		this.tablaRenew = tablaRenew;
	}

	// FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(final Composite parent) {

		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		
		listCalendar = (List<cu.uci.abcd.domain.management.library.Calendar>) ((AllManagementLoanUserViewController) controller).getManageTransaction().findCalendar(library.getLibraryID());
		listHorary = ((AllManagementLoanUserViewController) controller).getManageTransaction().findHorarybyLibrary(library.getLibraryID());

		painter = new FormPagePainter();
		painter.setDimension(dimension);
		painter.addComposite(parent);

		compoParent = new Composite(parent, SWT.NORMAL);
		painter.addComposite(compoParent);
		compoParent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbViewTransaction = new Label(compoParent, SWT.NONE);
		painter.addHeader(lbViewTransaction);

		Label separator = new Label(compoParent, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);

		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		loanUserGroup = new Group(compoParent, SWT.NORMAL);
		painter.add(loanUserGroup);

		leftListLoanUser = new LinkedList<>();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));

		lastStringTransaction = MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_TRANSACTION);
		transactionGroup = new Group(compoParent, SWT.NORMAL);
		painter.add(transactionGroup);

		leftListTransaction = new LinkedList<>();
		leftListTransaction.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_LOAN));
		leftListTransaction.add(AbosMessages.get().LABEL_STATE);
		leftListTransaction.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_LOAN));
		leftListTransaction.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RETURN));
		leftListTransaction.add(MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_RETURN));
		leftListTransaction.add(AbosMessages.get().LABEL_OPERATOR);

		painter.reset();
		loanObjectGroup = new Group(compoParent, SWT.NORMAL);
		painter.add(loanObjectGroup);

		transactionData = LoadTransactionData(transaction);
		User userT = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findUserByPersonID(transactionData.getLoanUser().getPersonID());

		List<String> rigthListLoanUser = new LinkedList<>();
		rigthListLoanUser.add(transactionData.getLoanUser().fullName());
		rigthListLoanUser.add(transactionData.getLoanUser().getDNI());
		if (userT != null) {
			rigthListLoanUser.add(userT.getUsername());
		} else
			rigthListLoanUser.add(" - ");
		rigthListLoanUser.add(transactionData.getLoanUser().getLoanUserCode());
		rigthListLoanUser.add(transactionData.getLoanUser().getLoanUserType().getNomenclatorName());
		rigthListLoanUser.add(transactionData.getLoanUser().getLoanUserState().getNomenclatorName());
		rigthListLoanUser.add(cu.uci.abcd.domain.util.Auxiliary.FormatDate(transactionData.getLoanUser().getExpirationDate()));

		grupControlsLoanUser = CompoundGroup.printGroup(transactionData.getLoanUser().getPhoto().getImage(), loanUserGroup, lastStringLoanUser, leftListLoanUser, rigthListLoanUser);

		lastStringLoanObject = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN);

		leftListLoanObject = new LinkedList<>();
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
		leftListLoanObject.add(AbosMessages.get().LABEL_STATE);

		List<String> rigthListLoanObject = new LinkedList<>();
		rigthListLoanObject.add(transactionData.getLoanObject().getTitle());
		if (transactionData.getLoanObject().getAuthor() == null) {
			rigthListLoanObject.add("-");
		} else
			rigthListLoanObject.add(transactionData.getLoanObject().getAuthor());
		rigthListLoanObject.add(transactionData.getLoanObject().getInventorynumber());
		rigthListLoanObject.add(transactionData.getLoanObject().getRecordType().getNomenclatorName());
		rigthListLoanObject.add(transactionData.getLoanObject().getLoanObjectState().getNomenclatorName());

		grupControlsLoanObject = CompoundGroup.printGroup(loanObjectGroup, lastStringLoanObject, leftListLoanObject, rigthListLoanObject);

		Date date = transactionData.getTransactionDateTime();
		Date endDate = transactionData.getEndTransactionDate();

		List<String> rigthListTransaction = new LinkedList<>();
		rigthListTransaction.add(transactionData.getLoanType().getNomenclatorName());
		rigthListTransaction.add(transactionData.getState().getNomenclatorName());
		rigthListTransaction.add(Auxiliary.FormatDate(date));
		rigthListTransaction.add(Auxiliary.FormatDate(endDate));
		rigthListTransaction.add(new SimpleDateFormat("hh:mma").format(transactionData.getLoanObject().getRoom().getHour()));
		rigthListTransaction.add(transactionData.getLibrarian().getPerson().getFullName());

		grupControlsTransaction = CompoundGroup.printGroup(transactionGroup, lastStringTransaction, leftListTransaction, rigthListTransaction);

		if (transactionData.getReservation() != null) {

			lastStringReservation = MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_RESERVATION);

			reservationGroup = new Group(compoParent, SWT.NORMAL);
			painter.add(reservationGroup);

			leftListReservation = new LinkedList<>();
			leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
			leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
			leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_UP));
			leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_LOAN));

			List<String> rigthListReservation = new LinkedList<>();
			rigthListReservation.add(transactionData.getReservation().getState().getNomenclatorName());
			rigthListReservation.add(Auxiliary.FormatDate(transactionData.getReservation().getCreationDate()));
			rigthListReservation.add(Auxiliary.FormatDate(transactionData.getReservation().getReservationDate()));
			rigthListReservation.add(transactionData.getReservation().getReservationType().getNomenclatorName());

			grupControlsReservation = CompoundGroup.printGroup(reservationGroup, lastStringReservation, leftListReservation, rigthListReservation);
		}

		painter.reset();
		btnRegisterLost = new Button(compoParent, SWT.PUSH);
		painter.add(btnRegisterLost);

		try {

			btnRegisterLost.setVisible(true);
			if ((transactionData.getState().getNomenclatorID().equals(Nomenclator.LOAN_STATE_BORROWED) || transactionData.getState().getNomenclatorID().equals(Nomenclator.LOAN_STATE_RENEW)) && consultTransaction != null) {
				btnRegisterLost.setVisible(true);
			} else if (registerRenew != null) {
				btnRegisterLost.setVisible(true);
			} else
				btnRegisterLost.setVisible(false);

			if (transactionData.getState().getNomenclatorID().equals(Nomenclator.LOAN_STATE_NOT_DELIVERED))
				btnRegisterLost.setVisible(false);

		
			if (user.getPerson() == null ) {
				btnRegisterLost.setVisible(false);
			}
			else {
				Worker workerLoggin = ((AllManagementLoanUserViewController) controller).getManagePerson().findWorkerbyPersonID(user.getPerson().getPersonID());
				if ( workerLoggin==null) {
					btnRegisterLost.setVisible(false);
				}
			}
			
			btnRegisterLost.addSelectionListener(new SelectionAdapter() {

				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {

					library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
					userLoggin = (User) SecurityUtils.getService().getPrincipal().getByKey("user");

					Nomenclator circulationRuleState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.CIRCULATION_RULE_STATE_ACTIVE);

					CirculationRule circulationRule = ((AllManagementLoanUserViewController) controller).getManageTransaction().findCirculationRule(circulationRuleState, transactionData.getLoanUser().getLoanUserType(), transactionData.getLoanObject().getRecordType(), library.getLibraryID());
					FineEquation fineEquation = ((AllManagementLoanUserViewController) controller).getManagePenalty().findFineEquationByLibrary(library.getLibraryID());

					Double amountFineEquation = fineEquation.getLostAmount();
					Double amountCirculationRule = circulationRule.getAmountPenaltyByLost();
					double price = transactionData.getLoanObject().getPrice();
					Double valueMaterial = price;
					Double amountTotal = amountFineEquation + (amountCirculationRule * valueMaterial);

					java.util.Date fecha = new java.util.Date();
					Date dateSystem = new Date(fecha.getTime());

					Nomenclator loanObjectState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOANOBJECT_STATE_LOST);
					Nomenclator loanObjectSituationLost = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(CataloguingNomenclator.SITUATION_LOST);

					Nomenclator loanState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOAN_STATE_NOT_DELIVERED);

					Date dateTransaction = transactionData.getTransactionDateTime();

					LoanObject loanObject = transactionData.getLoanObject();
					loanObject.setLoanObjectState(loanObjectState);

					((AllManagementLoanUserViewController) controller).getManageObject().addLoanObject(loanObject);

					Transaction transaction = transactionData;
					transaction.setState(loanState);
					transaction.setTransactionDateTime(dateTransaction);
					transaction.setEndTransactionDate(dateSystem);

					((AllManagementLoanUserViewController) controller).getManageTransaction().addTransaction(transaction);

					Penalty penalty = new Penalty();

					Nomenclator typeFine = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_TYPE_FINE);
					Nomenclator statePending = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_STATE_PENDING_PAYMENT);

					java.util.Date fecha1 = new java.util.Date();
					Date fechaSQL = new Date(fecha1.getTime());

					java.util.Date ultimo = Auxiliary.addDays(listCalendar, listHorary, fechaSQL, circulationRule.getLostSuspensionDay(), library.getLibraryID(), listDateReservations);
					salida = new Date(ultimo.getTime());

					penalty.setLoanUser(transactionData.getLoanUser());
					penalty.setLoanObject(transactionData.getLoanObject());
					penalty.setPenaltyState(statePending);
					penalty.setPenaltyType(typeFine);
					penalty.setLibrary(library);
					penalty.setEffectiveDate(fechaSQL);
					penalty.setExpirationDate(salida);
					penalty.setAmount(amountTotal);
					penalty.setCoinType(transactionData.getLoanObject().getAcquisitionCoinType());
					penalty.setLibrarian(userLoggin);
					penalty.setMotivation(MessageUtil.unescape(AbosMessages.get().MSG_USER_SANCIONED_LOST));

					((AllManagementLoanUserViewController) controller).getManagePenalty().addPenalty(penalty);

					// ---------------Mensaje
				
					manager.save(new BaseGridViewEntity<Transaction>(transaction));
					manager.refresh();

					try {
						RetroalimentationUtils.showInformationMessage(compoParent,AbosMessages.get().MSJE_USER_PENALTY_LOST_BOOK);

						if (consultTransaction != null) {
							consultTransaction.searchTransactions(0, tabla.getPageSize());

						} else if (registerRenew != null) {
						
							registerRenew.searchTransaction(0, registerRenew.getTableLoanObjectCRUD().getPageSize());
							tablaRenew.getPaginator().goToFirstPage();
						}

					} catch (Exception e2) {
						e2.printStackTrace();
					}

					//List<LoanObject> loanObjectIqualControlNumber = ((AllManagementLoanUserViewController) controller).getManageObject().findControlNumberLoanObject(transactionData.getLoanObject().getControlNumber());
		
					LoanObject loanObjectEditSituation = transactionData.getLoanObject();
					loanObjectEditSituation.setSituation(loanObjectSituationLost);

					((AllManagementLoanUserViewController) controller).getManageObject().addLoanObject(loanObject);
    
				/*	loanObjectIqualControlNumber = ((AllManagementLoanUserViewController) controller).getManageObject().findControlNumberLoanObject(transactionData.getLoanObject().getControlNumber());

						       
					for (int i = 0; i < loanObjectIqualControlNumber.size(); i++) 
						if ((loanObjectIqualControlNumber.get(i)
								.getLoanObjectState().getNomenclatorID()
								.equals(Nomenclator.LOANOBJECT_STATE_LOST) || (loanObjectIqualControlNumber
								.get(i).getLoanObjectState().getNomenclatorID()
								.equals(Nomenclator.LOANOBJECT_STATE_EXPURGO))))
							loanObjectIqualControlNumber.remove(i--);						
					
					
					if (loanObjectIqualControlNumber.isEmpty()) {
						try {
							
					

						if (((AllManagementLoanUserViewController) controller).getManageLoanObjectJISIS().getService() == null) {
							RetroalimentationUtils.showInformationMessage(compoParent, "No hay conexión con el servidor JISIS");
						} else {
							if (((AllManagementLoanUserViewController) controller).getManageLoanObjectJISIS().findRecord(transactionData.getLoanObject().getControlNumber()) == null) {
								RetroalimentationUtils.showInformationMessage(compoParent, "No hay libro con ese Numero de Control en el servidor JISIS");
							} else {
							
								List<Record> listRecord = ((AllManagementLoanUserViewController) controller).getManageLoanObjectJISIS().findRecord(transactionData.getLoanObject().getControlNumber());
								Field fieldTemp = null;
								try {
									fieldTemp = (Field) listRecord.get(0).getField(971);
									fieldTemp.setFieldValue("0");

								} catch (Exception e1) {

									e1.printStackTrace();
								}

								listRecord.get(0).setField(fieldTemp);

								((AllManagementLoanUserViewController) controller).getManageLoanObjectJISIS().editWhenNotExistingCopies(listRecord.get(0));
							}
						}
						} catch (Exception e2) {
						//	RetroalimentationUtils.showInformationMessage(compoParent, "No hay conexión con el servidor JISIS");
						}
					}*/
				}
			});

		} catch (Exception exception) {
			Shell shell = parent.getShell();

			MessageDialogUtil.openError(shell, "Error", "Problema de Conección con JISIS \n \n Detalles: \n" + exception.toString(), null);

		}

		l10n();
		return parent;
	}

	@SuppressWarnings("unused")
	public Transaction LoadTransactionData(Transaction transactionLoaded) {
		if (!(transactionLoaded == null)) {
			Transaction transaction;
			if (!(transactionLoaded.getTransactionID() == null)) {
				Long idTransaction = transactionLoaded.getTransactionID();
				transaction = ((AllManagementLoanUserViewController) controller).getManageTransaction().findOneTransaction(idTransaction);
			} else {
				transaction = transactionLoaded;
			}

		}
		return transaction;
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		lbViewTransaction.setText(MessageUtil.unescape(AbosMessages.get().VIEW_TRANSACTION));
		if (!btnRegisterLost.isDisposed())
			btnRegisterLost.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LOST_REGISTER));

		leftListLoanUser.clear();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		CompoundGroup.l10n(grupControlsLoanUser, leftListLoanUser);

		leftListTransaction.clear();
		leftListTransaction.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_LOAN));
		leftListTransaction.add(AbosMessages.get().LABEL_STATE);
		leftListTransaction.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_LOAN));
		leftListTransaction.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RETURN));
		leftListTransaction.add(MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_RETURN));
		leftListTransaction.add(AbosMessages.get().LABEL_OPERATOR);
		CompoundGroup.l10n(grupControlsTransaction, leftListTransaction);

		leftListLoanObject.clear();
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
		leftListLoanObject.add(AbosMessages.get().LABEL_STATE);
		CompoundGroup.l10n(grupControlsLoanObject, leftListLoanObject);

		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		lastStringTransaction = MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_TRANSACTION);
		lastStringLoanObject = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN);

		loanUserGroup.setText(lastStringLoanUser);
		loanObjectGroup.setText(lastStringLoanObject);
		transactionGroup.setText(lastStringTransaction);

		if (transactionData.getReservation() != null) {
			leftListReservation.clear();
			leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
			leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
			leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_UP));
			leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_LOAN));
			CompoundGroup.l10n(grupControlsReservation, leftListReservation);
			lastStringReservation = MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_RESERVATION);
			reservationGroup.setText(lastStringReservation);
		}	
		
		refresh();
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public Composite getRegisterLoan() {
		return registerLoan;
	}

	public void setRegisterLoan(Composite registerLoan) {
		this.registerLoan = registerLoan;
	}

	public void cleanComponentParent() {
		try {
			Control[] temp = compoParent.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		}
	}

	public void createComponentParent() {
		createUIControl(compoParent);
	}

	public void refresh() {
		Composite parentRezize = compoParent.getParent();
		parentRezize.layout(true, true);
		parentRezize.redraw();
		parentRezize.update();
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}