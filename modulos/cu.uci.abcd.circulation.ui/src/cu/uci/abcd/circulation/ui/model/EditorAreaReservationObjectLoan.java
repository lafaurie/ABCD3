package cu.uci.abcd.circulation.ui.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.eclipse.swt.widgets.Spinner;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.ConsultReservation;
import cu.uci.abcd.circulation.ui.auxiliary.Auxiliary;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class EditorAreaReservationObjectLoan extends BaseEditableArea {

	private Date salida = null;
	private Button btnLoan;
	private Reservation fdtEntity;
	private CRUDTreeTable tabla;
	private ViewController controller;
	private Library library;
	private List<cu.uci.abcd.domain.management.library.Calendar> listCalendar;
	private List<Schedule> listHorary;
	private List<Reservation> listReservations = null;
	private List<Transaction> listTransaction = new ArrayList<>();
	private CRUDTreeTable tableLoanObject;
	private int direction = 1024;
	private String orderByString = "title";
	private int page = 0;
	private int size = 10;
	private Spinner spinner;
	private Label lbLoanReservation;
	private ConsultReservation consultReservation;
	private Label lbcantLoan;
	
	private String lastStringLoanUser;
	private Group loanUserGroupLoan;
	private List<String> leftListLoanUser = new LinkedList<>();
	
	private String lastStringReservation;
	private Group reservationGroup;
	private List<String> leftListReservation = new LinkedList<>();
	
	private Group loanObjectGroup;
	private String lastStringLoanObject;
	private List<String> leftListLoanObject = new LinkedList<>();
	
	private List<Control> grupControlsLoanUser = new ArrayList<>();
	private List<Control> grupControlsLoanObject = new ArrayList<>();
	private List<Control> grupControlsReservation = new ArrayList<>();

	private Label listObjectLoan;
	
	public EditorAreaReservationObjectLoan(ViewController controller, CRUDTreeTable tabla,ConsultReservation consultReservation) {
		super();
		this.controller = controller;
		this.tabla = tabla;
		this.consultReservation = consultReservation;
	}

	@Override
	//FIXME METODO COMPLEJO
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		
		listCalendar = (List<cu.uci.abcd.domain.management.library.Calendar>) ((AllManagementLoanUserViewController) controller).getManageTransaction().findCalendar(library.getLibraryID());
		listHorary = ((AllManagementLoanUserViewController) controller).getManageTransaction().findHorarybyLibrary(library.getLibraryID());

		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		buildMessage(parent);
		
		lbLoanReservation = new Label(parent, SWT.NONE);
		addHeader(lbLoanReservation);
	
		Label separator = new Label(parent,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
	
		setDimension(parent.getParent().getParent().getBounds().width);
		fdtEntity = (Reservation) entity.getRow();

		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		loanUserGroupLoan = new Group(parent, SWT.NORMAL);
		add(loanUserGroupLoan);

		leftListLoanUser = new LinkedList<>();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));

		User user = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findUserByPersonID(fdtEntity.getLoanUser().getId());

		List<String> rigthListLoanUser = new LinkedList<>();
		rigthListLoanUser.add(fdtEntity.getLoanUser().fullName());
		rigthListLoanUser.add(fdtEntity.getLoanUser().getDNI());
		if (user != null) {
			rigthListLoanUser.add(user.getUsername());
		} else
			rigthListLoanUser.add(" - ");

		rigthListLoanUser.add(fdtEntity.getLoanUser().getLoanUserCode());
		rigthListLoanUser.add(fdtEntity.getLoanUser().getLoanUserType().getNomenclatorName());
		rigthListLoanUser.add(fdtEntity.getLoanUser().getLoanUserState().getNomenclatorName());
		rigthListLoanUser.add(Auxiliary.FormatDate(fdtEntity.getLoanUser().getExpirationDate()));

		grupControlsLoanUser=CompoundGroup.printGroup(fdtEntity.getLoanUser().getPhoto().getImage(), loanUserGroupLoan, lastStringLoanUser, leftListLoanUser, rigthListLoanUser);

		//******************Data Reservation
		lastStringReservation = MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_RESERVATION);
		reservationGroup = new Group(parent, SWT.NORMAL);
		add(reservationGroup);

		leftListReservation = new LinkedList<>();
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_UP));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_LOAN));

		List<String> rigthListReservation = new LinkedList<>();
		rigthListReservation.add(fdtEntity.getState().getNomenclatorName());
		rigthListReservation.add(new SimpleDateFormat("dd-MM-yyyy").format(fdtEntity.getCreationDate()));
		rigthListReservation.add(new SimpleDateFormat("dd-MM-yyyy").format(fdtEntity.getReservationDate()));
		rigthListReservation.add(fdtEntity.getReservationType().getNomenclatorName());

		grupControlsReservation= CompoundGroup.printGroup(reservationGroup, lastStringReservation, leftListReservation, rigthListReservation);

		//*********************Data LoanObject
		
		if (fdtEntity.getReservationList().size() > 1) {
			
			listObjectLoan = new Label(parent, 0);
			addHeader(listObjectLoan);
				
			br();
			
			tableLoanObject = new CRUDTreeTable(parent, SWT.NONE);
			add(tableLoanObject);
			tableLoanObject.setEntityClass(LoanObject.class);
			tableLoanObject.setWatch(true, new ViewAreaLoanObject(controller));
		
			TreeTableColumn columns[] = {
					new TreeTableColumn(20, 0, "getTitle"),
					new TreeTableColumn(20, 1, "getAuthor"),
					new TreeTableColumn(20, 2, "getInventorynumber"),
					new TreeTableColumn(20, 3, "getRecordType.getNomenclatorName"),
					new TreeTableColumn(20, 4,
							"getLoanObjectState.getNomenclatorName") };

			tableLoanObject.createTable(columns);
			tableLoanObject.setPageSize(10);
			tableLoanObject.addPageChangeListener(new PageChangeListener() {
				@Override
				public void pageChanged(final PageChangedEvent event) {
					
					searchLoanObject(event.currentPage - 1, event.pageSize);
				}
			});
				
			searchLoanObject(page, size);
			
			br();
			
			lbcantLoan = new Label(parent, 0);
			add(lbcantLoan);
			
			spinner = new Spinner(parent, SWT.BORDER);
			add(spinner);
			spinner.setMaximum(fdtEntity.getReservationList().size());
			spinner.setMinimum(1);			
			}			
			else
			{
			lastStringLoanObject = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN);
		
			loanObjectGroup = new Group(parent, SWT.NORMAL);
			add(loanObjectGroup);
		
			leftListLoanObject = new LinkedList<>();
			leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
			leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);
			leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
			leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
			leftListLoanObject.add(AbosMessages.get().LABEL_STATE);

			List<String> rigthListLoanObject = new LinkedList<>();
			rigthListLoanObject.add(fdtEntity.getReservationList().get(0).getTitle());
			rigthListLoanObject.add(fdtEntity.getReservationList().get(0).getAuthor());
			rigthListLoanObject.add(fdtEntity.getReservationList().get(0).getInventorynumber());
			rigthListLoanObject.add(fdtEntity.getReservationList().get(0).getRecordType().getNomenclatorName());
			rigthListLoanObject.add(fdtEntity.getReservationList().get(0).getLoanObjectState().getNomenclatorName());

		grupControlsLoanObject=	CompoundGroup.printGroup(loanObjectGroup, lastStringLoanObject, leftListLoanObject, rigthListLoanObject);

			}	
		l10n();
		return parent;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	public Control getControl(String key) {
		return null;
	}

	@Override
	//FIXME METODO COMPLEJO
	public Composite createButtons(Composite parent, final IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		btnLoan = new Button(parent, SWT.PUSH);
		btnLoan.setText(AbosMessages.get().BUTTON_LOAN);

		// CI8.1_Registrar Préstamo de Objeto a partir de Reservación
		btnLoan.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
				User user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");

				Nomenclator loanUserType = fdtEntity.getLoanUser().getLoanUserType();
				Nomenclator recordType = fdtEntity.getReservationList().get(0).getRecordType();
				Nomenclator circulationRuleState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.CIRCULATION_RULE_STATE_ACTIVE);

				java.util.Date fecha = new java.util.Date();
				Date startDate = new Date(fecha.getTime());

				CirculationRule circulationRule = ((AllManagementLoanUserViewController) controller).getManageTransaction().findCirculationRule(circulationRuleState,loanUserType, recordType, library.getLibraryID());

				if (circulationRule == null) {
					showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSJE_LOAN_RULE_NOT_EXIST));
					
				} else {
					int cantDays = circulationRule.getQuantityDayOnLoan();

					Nomenclator loanObjectState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOANOBJECT_STATE_BORROWED);
					Nomenclator reservationState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.RESERVATION_STATE_EXECUTED);
					Nomenclator loanState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOAN_STATE_BORROWED);

					java.util.Date ultimo = Auxiliary.addDays(listCalendar, listHorary, startDate, cantDays, library.getLibraryID(), listReservations);
					salida = new Date(ultimo.getTime());
					int cant= 0;
					
					if (fdtEntity.getReservationList().size() > 1) {
						cant = Integer.parseInt(spinner.getText());
					}
					else
						cant = 1;
					
					for (int i = 0; i < cant; i++) {
								
						Transaction transaction = new Transaction();
						transaction.setLoanUser(fdtEntity.getLoanUser());
						transaction.setLoanObject(fdtEntity.getReservationList().get(i));
						transaction.setState(loanState);
						transaction.setTransactionDateTime(startDate);
						transaction.setEndTransactionDate(salida);
						transaction.setReservation(fdtEntity);
						transaction.setLibrarian(user);
						transaction.setIsparent(false);

						if (fdtEntity.getReservationType().getNomenclatorID().equals(Nomenclator.RESERVATION_TYPE_INTERN)) {
							Nomenclator loanTypeIntern = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.RESERVATION_TYPE_INTERN);
							transaction.setLoanType(loanTypeIntern);
						} else if (fdtEntity.getReservationType().getNomenclatorID().equals(Nomenclator.RESERVATION_TYPE_EXTERN)) {
							Nomenclator loanTypeExtern = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.RESERVATION_TYPE_EXTERN);
							transaction.setLoanType(loanTypeExtern);
						}

						((AllManagementLoanUserViewController) controller).getManageTransaction().addTransaction(transaction);
						
						
						LoanObject loanObject = fdtEntity.getReservationList().get(i);
						loanObject.setLoanObjectState(loanObjectState);
						((AllManagementLoanUserViewController) controller).getManageObject().addLoanObject(loanObject);
						
						listTransaction.add(transaction);
							
					}
					
					Reservation reservation = fdtEntity;
					reservation.setState(reservationState);
					((AllManagementLoanUserViewController) controller).getManageReservation().addReservation(reservation);

					// ---------------Mensaje

					showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_REGISTER + " " + cant + " " + MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_REGISTER_LOAN));
						
					manager.save(new BaseGridViewEntity<Reservation>(reservation));
					manager.refresh();
				
					tabla.destroyEditableArea();
					tabla.createEditableArea(new ViewAreaTransactionsToReservation(controller, listTransaction), entity, tabla.getVisualEntityManager());
					
					consultReservation.searchReservations(0, tabla.getPageSize());
				}
			}
		});

		return parent;
	}
	
	public void searchLoanObject(int page, int size) {
		tableLoanObject.clearRows();
		tableLoanObject.setTotalElements(fdtEntity.getReservationList().size());
		tableLoanObject.setRows(fdtEntity.getReservationList());
		tableLoanObject.refresh();
	}

	@Override
	public boolean closable() {
		return true;
	}


	@Override
	public void l10n() {		
		lbLoanReservation.setText(MessageUtil.unescape(AbosMessages.get().NAME_UI_REGISTER_LOAN_RESERVATION));
		
		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		leftListLoanUser.clear();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		CompoundGroup.l10n(grupControlsLoanUser, leftListLoanUser);
		loanUserGroupLoan.setText(lastStringLoanUser);
	
		lastStringReservation = MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_RESERVATION);
		leftListReservation.clear();
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_UP));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_LOAN));
		CompoundGroup.l10n(grupControlsReservation, leftListReservation);
		reservationGroup.setText(lastStringReservation);
	
			
		if (fdtEntity.getReservationList().size() > 1) {
			
		listObjectLoan.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_OBJECT_LOAN));
		lbcantLoan.setText(AbosMessages.get().LABEL_CANT_REAL_LOANOBJECT_TO_LOAN);
		
		tableLoanObject.setColumnHeaders(Arrays.asList(MessageUtil
				.unescape(AbosMessages.get().LABEL_TITLE), AbosMessages
				.get().LABEL_AUTHOR, MessageUtil.unescape(AbosMessages
				.get().LABEL_INVENTORY_NUMBER),
				AbosMessages.get().LABEL_OBJECT_TYPE,
				AbosMessages.get().LABEL_STATE));

		
		}
		else{
			lastStringLoanObject = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN);
			leftListLoanObject.clear();
			leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
			leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);
			leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
			leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
			leftListLoanObject.add(AbosMessages.get().LABEL_STATE);
			CompoundGroup.l10n(grupControlsLoanObject, leftListLoanObject);
			loanObjectGroup.setText(lastStringLoanObject);
	
		}
	}
	
	@Override
	public String getID() {
		return "addLoanReservationID";
	}
}
