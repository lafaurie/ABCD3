package cu.uci.abcd.circulation.ui.auxiliary;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.RegisterLoanUser;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.model.ViewAreaPenaltyConsult;
import cu.uci.abcd.circulation.ui.model.ViewAreaReservations;
import cu.uci.abcd.circulation.ui.model.ViewAreaTransactions;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ViewLoanUserFragment extends FragmentPage {

	private ViewController controller;
	private PagePainter painter;
	private LoanUser loanUser;

	private Composite registerLoanUser;
	private Composite fragmentView;
	private Composite compoLoanUser;

	private List<String> searchC = new ArrayList<>();

	private TabItem tabItemLoanUSer;

	private Button btnClose;
	private Button btnNew;

	private CRUDTreeTable tableActualPenalty;
	private CRUDTreeTable tableActualReservations;
	private CRUDTreeTable tableActualTransactions;
	private CRUDTreeTable tableHitorialPenalty;
	private CRUDTreeTable tableHistorialReservaciones;
	private CRUDTreeTable tableHistorialTransaciones;

	private String lastString;
	private String lastStringLoanUserCurrent;
	private String lastStringLoanUser;
	private String lastStringLoanUserRecord;

	private Label lbUserCurrentTransaction;
	private Label lbUserCurrentReservations;
	private Label lbUserCurrentSanctions;
	private Label lbRecordTransaction;
	private Label lbUserRecordSanctions;
	private Label lbUserRecordReservations;

	private TabFolder tabFolder;
	private TabItem tabItemCurrentState;
	private TabItem tabItemR;
	private TabItem tabItemT;
	private TabItem tabItemS;
	private TabItem tabItemRecord;
	private TabItem tabItemTRecord;
	private TabItem tabItemRRecord;
	private TabItem tabItemSRecord;

	private Label lbViewLoanUser;
	private Composite compoView;
	// FIXME FALTA VISIBILIDAD DE ATRIBUTOS Y METODOS
	int direction = 1024;	

	private String orderByStringReservations = "reservationID";
	private String orderByStringTransactions = "transactionID";
	private String orderByStringPenalty = "penaltyID";
	private int dimension;
	private RegisterLoanUser registerLoanUserClass;
	private ContributorService contributorService;
	private List<String> leftList = new ArrayList<>();
	private List<String> leftListLoanUser = new ArrayList<>();
	private List<String> leftListLoanUserCurrent = new ArrayList<>();
	private List<String> leftListLoanUserRecord = new ArrayList<>();
   
	private List<Control> grupControlsPerson = new ArrayList<>();
	private List<Control> grupControlsLoanUser = new ArrayList<>();
	private List<Control> grupControlsCurrent = new ArrayList<>();
	private List<Control> grupControlsRecord = new ArrayList<>();

	private Group personData;
	private Group loanUserGroup;
	private Group loanUserGroupCurrent;
	private Group loanUserGroupRecord;	

	public ViewLoanUserFragment(ViewController controller) {
		this.setController(controller);
	}

	public ViewLoanUserFragment(ViewController controller, LoanUser loanUser, int dimension) {
		this.setController(controller);
		this.setLoanUser(loanUser);
		this.dimension = dimension;
	}

	public ViewLoanUserFragment(ViewController controller, LoanUser loanUser, Composite registerLoanUser, int dimension, RegisterLoanUser registerLoanUserClass, ContributorService contributorService) {
		this.setController(controller);
		this.setLoanUser(loanUser);
		this.setRegisterLoanUser(registerLoanUser);
		this.dimension = dimension;
		this.registerLoanUserClass = registerLoanUserClass;
		this.contributorService = contributorService;
	}

	// FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(final Composite parent) {

		painter = new FormPagePainter();
		painter.setDimension(dimension);
		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		compoView = new Composite(parent, SWT.NORMAL);
		painter.addComposite(compoView);
		compoView.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbViewLoanUser = new Label(compoView, SWT.NONE);
		painter.addHeader(lbViewLoanUser);

		Label separator = new Label(compoView, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
      
		tabFolder = new TabFolder(compoView, SWT.NONE);		
		painter.add(tabFolder);

		compoLoanUser = new Composite(tabFolder, SWT.None);
		compoLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");

		tabItemLoanUSer = new TabItem(tabFolder, SWT.None);
		tabItemLoanUSer.setControl(compoLoanUser);		
		painter.addComposite(compoLoanUser);		

		final Composite grupo = new Composite(compoLoanUser, SWT.None);
		grupo.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(grupo);

		personData = new Group(grupo, SWT.NORMAL);
		painter.add(personData);
	
		leftList = new LinkedList<>();
		leftList.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftList.add(AbosMessages.get().TABLE_AGE);
		leftList.add(AbosMessages.get().TABLE_SEX);

		loanUserGroup = new Group(grupo, SWT.NORMAL);
		painter.add(loanUserGroup);
		
		leftListLoanUser = new LinkedList<>();
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(AbosMessages.get().LABEL_REGISTER_DATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		leftListLoanUser.add(AbosMessages.get().LABEL_REGISTER_BY);

		painter.reset();

		painter.add(new Label(grupo, SWT.NORMAL));
		painter.reset();
		painter.add(new Label(grupo, SWT.NORMAL));
		painter.reset();	


		btnClose = new Button(grupo, SWT.PUSH);
		painter.add(btnClose);

		btnNew = new Button(grupo, SWT.PUSH);
		painter.add(btnNew);

		painter.reset();
		painter.add(new Label(grupo, 0),Percent.W100);
	
		if (registerLoanUser != null) {
			btnNew.setVisible(true);
			btnClose.setVisible(true);
		} else {
			btnNew.setVisible(false);
			btnClose.setVisible(false);
		}
		// ----------------------Current State

		tabItemCurrentState = new TabItem(tabFolder, SWT.None);

		Composite compoCurrentState = new Composite(tabFolder, SWT.NONE);
		compoCurrentState.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(compoCurrentState);
		tabItemCurrentState.setControl(compoCurrentState);		

		loanUserGroupCurrent = new Group(compoCurrentState, SWT.NORMAL);
		painter.add(loanUserGroupCurrent);

		leftListLoanUserCurrent = new LinkedList<>();
		leftListLoanUserCurrent.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUserCurrent.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUserCurrent.add(AbosMessages.get().LABEL_USER);
		leftListLoanUserCurrent.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUserCurrent.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUserCurrent.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUserCurrent.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		
		painter.reset();
		// ------------------TabFolder de adentro

		TabFolder tabFolderCurrentState = new TabFolder(compoCurrentState, SWT.NONE);
		painter.add(tabFolderCurrentState);

		// ------------------tabItems Transacciones
		tabItemT = new TabItem(tabFolderCurrentState, SWT.None);

		Composite grupo2 = new Composite(tabFolderCurrentState, SWT.None);
		grupo2.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(grupo2);

		tabItemT.setControl(grupo2);

		lbUserCurrentTransaction = new Label(grupo2, SWT.NONE);
		painter.addHeader(lbUserCurrentTransaction);

		tableActualTransactions = new CRUDTreeTable(grupo2, SWT.NONE);
		painter.add(tableActualTransactions);
		tableActualTransactions.setEntityClass(Transaction.class);
		tableActualTransactions.setWatch(true, new ViewAreaTransactions(controller));
		//FormDatas.attach(tableActualTransactions).atTopTo(lbUserCurrentTransaction, 3).atLeft(15).atRight(15);
		
		tableActualTransactions.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});
		
		
		CRUDTreeTableUtils.configReports(tableActualTransactions, MessageUtil.unescape(AbosMessages.get().LABEL_USER_CURRENT_LOAN_TRANSACTIONS), searchC,AbosMessages.get().LABEL_LIST);

		TreeTableColumn columnsTransaccion[] = {
				new TreeTableColumn(16, 0, "getLoanObject.getTitle"),
				new TreeTableColumn(16, 1, "getLoanObject.getAuthor"),
				new TreeTableColumn(16, 2,
						"getLoanObject.getRecordType.getNomenclatorName"),
				new TreeTableColumn(15, 3, "getState.getNomenclatorName"),
				new TreeTableColumn(19, 4, "getLoanUser.fullName"),
				new TreeTableColumn(14, 5,
						"getLoanUser.getLoanUserType.getNomenclatorName") };

		tableActualTransactions.createTable(columnsTransaccion);

		tableActualTransactions.setPageSize(10);
		
		painter.add(new Label(grupo2, 0),Percent.W100);
		// ------------------tabItems Reservaciones
		tabItemR = new TabItem(tabFolderCurrentState, SWT.None);

		Composite compoR = new Composite(tabFolderCurrentState, SWT.None);
		compoR.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(compoR);
		tabItemR.setControl(compoR);

		lbUserCurrentReservations = new Label(compoR, SWT.NONE);
		painter.addHeader(lbUserCurrentReservations);

		//final String userCurrentReservations = lbUserCurrentReservations.getText().toString();

		tableActualReservations = new CRUDTreeTable(compoR, SWT.NONE);
		painter.add(tableActualReservations);
		tableActualReservations.setEntityClass(Reservation.class);
		tableActualReservations.setWatch(true, new ViewAreaReservations(controller));

		tableActualReservations.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});
		
		CRUDTreeTableUtils.configReports(tableActualReservations, MessageUtil.unescape(AbosMessages.get().LABEL_USER_CURRENT_LOAN_RESERVATIONS), searchC,AbosMessages.get().LABEL_LIST);

		TreeTableColumn columnsReservation[] = { new TreeTableColumn(20, 0, "getReservationDate"), new TreeTableColumn(20, 1, "getState.getNomenclatorName"), new TreeTableColumn(20, 2, "getObjecttype.getNomenclatorName"), new TreeTableColumn(20, 3, "getTitle"),
				new TreeTableColumn(20, 4, "getAuthorLoanObject") };

		tableActualReservations.createTable(columnsReservation);
		tableActualReservations.setPageSize(10);

		painter.add(new Label(compoR, 0),Percent.W100);
		// ------------------tabItems Sanciones
		tabItemS = new TabItem(tabFolderCurrentState, SWT.None);

		Composite compoS = new Composite(tabFolderCurrentState, SWT.None);
		compoS.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(compoS);
		tabItemS.setControl(compoS);

		lbUserCurrentSanctions = new Label(compoS, SWT.NONE);
		painter.addHeader(lbUserCurrentSanctions);

		tableActualPenalty = new CRUDTreeTable(compoS, SWT.NONE);
		painter.add(tableActualPenalty);
		tableActualPenalty.setEntityClass(Penalty.class);
		tableActualPenalty.setWatch(true, new ViewAreaPenaltyConsult(controller, tableActualPenalty,this));

		tableActualPenalty.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});
				
		painter.add(new Label(compoS, 0),Percent.W100);
		
		CRUDTreeTableUtils.configReports(tableActualPenalty, MessageUtil.unescape(AbosMessages.get().LABEL_USER_CURRENT_LOAN_SANCTIONS), searchC,AbosMessages.get().LABEL_LIST);

		TreeTableColumn columnsPenalty[] = { new TreeTableColumn(18, 0, "getLoanUser.fullName"), new TreeTableColumn(17, 1, "getPenaltyType.getNomenclatorName"), new TreeTableColumn(17, 2, "getPenaltyState.getNomenclatorName"), new TreeTableColumn(17, 3, "getEffectiveDate"),
				new TreeTableColumn(17, 4, "getExpirationDate"), new TreeTableColumn(10, 5, "getDay") };

		tableActualPenalty.createTable(columnsPenalty);

		tableActualPenalty.setPageSize(10);
		
		tableActualPenalty.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				
				initializeGridPenalty(loanUser, event.currentPage - 1, event.pageSize);
			}
		});
		
		tableActualTransactions.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				
				initializeGridTransaction(loanUser, event.currentPage - 1, event.pageSize);
			}
		});
		   
		tableActualReservations.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				
				initializeGridReservation(loanUser, event.currentPage - 1, event.pageSize);
			}
		});
		

		initializeGridPenalty(loanUser, 0, 10);
		initializeGridReservation(loanUser, 0, 10);
		initializeGridTransaction(loanUser, 0, 10);

		// --------------------Historial

		tabItemRecord = new TabItem(tabFolder, SWT.None);

		Composite compoRecord = new Composite(tabFolder, SWT.NONE);
		compoRecord.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(compoRecord);
		tabItemRecord.setControl(compoRecord);

		loanUserGroupRecord = new Group(compoRecord, SWT.NORMAL);
		painter.add(loanUserGroupRecord);

		leftListLoanUserRecord = new LinkedList<>();
		leftListLoanUserRecord.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUserRecord.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUserRecord.add(AbosMessages.get().LABEL_USER);
		leftListLoanUserRecord.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUserRecord.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUserRecord.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUserRecord.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));

		painter.reset();
		// ------------------TabFolder de adentro
		TabFolder tabFolderRecord = new TabFolder(compoRecord, SWT.NONE);
		painter.add(tabFolderRecord);

		// ------------------tabItems Transacciones
		tabItemTRecord = new TabItem(tabFolderRecord, SWT.None);

		Composite compoTR = new Composite(tabFolderRecord, SWT.None);
		compoTR.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(compoTR);

		tabItemTRecord.setControl(compoTR);

		lbRecordTransaction = new Label(compoTR, SWT.NONE);
		painter.addHeader(lbRecordTransaction);

		//final String recordTransaction = lbRecordTransaction.getText().toString();

		tableHistorialTransaciones = new CRUDTreeTable(compoTR, SWT.NONE);
		painter.add(tableHistorialTransaciones);
		tableHistorialTransaciones.setEntityClass(Transaction.class);
		tableHistorialTransaciones.setWatch(true, new ViewAreaTransactions(controller));
		
		tableHistorialTransaciones.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		CRUDTreeTableUtils.configReports(tableHistorialTransaciones, MessageUtil.unescape(AbosMessages.get().LABEL_USER_HISTORICAL_LOAN_TRANSACTIONS), searchC,AbosMessages.get().LABEL_LIST);

		TreeTableColumn columnsTransaccionHistory[] = {
						new TreeTableColumn(16, 0, "getLoanObject.getTitle"),
						new TreeTableColumn(16, 1, "getLoanObject.getAuthor"),
						new TreeTableColumn(16, 2,
								"getLoanObject.getRecordType.getNomenclatorName"),
						new TreeTableColumn(15, 3, "getState.getNomenclatorName"),
						new TreeTableColumn(19, 4, "getLoanUser.fullName"),
						new TreeTableColumn(14, 5,
								"getLoanUser.getLoanUserType.getNomenclatorName") };

		tableHistorialTransaciones.createTable(columnsTransaccionHistory);
		tableHistorialTransaciones.setPageSize(10);

		painter.add(new Label(compoTR, 0),Percent.W100);
		
		// ------------------tabItems Reservaciones
		tabItemRRecord = new TabItem(tabFolderRecord, SWT.None);

		Composite compoRRecord = new Composite(tabFolderRecord, SWT.None);
		compoRRecord.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(compoRRecord);
		tabItemRRecord.setControl(compoRRecord);

		lbUserRecordReservations = new Label(compoRRecord, SWT.NONE);
		painter.addHeader(lbUserRecordReservations);

		//final String userRecordReservations = lbUserRecordReservations.getText().toString();

		tableHistorialReservaciones = new CRUDTreeTable(compoRRecord, SWT.NONE);
		painter.add(tableHistorialReservaciones);
		tableHistorialReservaciones.setEntityClass(Reservation.class);
		tableHistorialReservaciones.setWatch(true, new ViewAreaReservations(controller));
		
		tableHistorialReservaciones.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		CRUDTreeTableUtils.configReports(tableHistorialReservaciones, MessageUtil.unescape(AbosMessages.get().LABEL_USER_HISTORICAL_LOAN_RESERVATIONS), searchC,AbosMessages.get().LABEL_LIST);

		TreeTableColumn columnsReservationHistory[] = { new TreeTableColumn(20, 0, "getReservationDate"), new TreeTableColumn(20, 1, "getState.getNomenclatorName"), new TreeTableColumn(20, 2, "getObjecttype.getNomenclatorName"), new TreeTableColumn(20, 3, "getTitle"),
				new TreeTableColumn(20, 4, "getAuthorLoanObject") };
		tableHistorialReservaciones.createTable(columnsReservationHistory);
		tableHistorialReservaciones.setPageSize(10);

		painter.add(new Label(compoRRecord, 0),Percent.W100);
		// ------------------tabItems Sanciones
		tabItemSRecord = new TabItem(tabFolderRecord, SWT.None);

		Composite compoSRecord = new Composite(tabFolderRecord, SWT.None);
		compoSRecord.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(compoSRecord);
		tabItemSRecord.setControl(compoSRecord);

		lbUserRecordSanctions = new Label(compoSRecord, SWT.NONE);
		painter.addHeader(lbUserRecordSanctions);
   
		//final String userRecordSanctions = lbUserRecordSanctions.getText().toString();
    
		tableHitorialPenalty = new CRUDTreeTable(compoSRecord, SWT.NONE);
		painter.add(tableHitorialPenalty);
		tableHitorialPenalty.setEntityClass(Penalty.class);
		tableHitorialPenalty.setWatch(true, new ViewAreaPenaltyConsult(controller));
		
		tableHitorialPenalty.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		CRUDTreeTableUtils.configReports(tableHitorialPenalty, MessageUtil.unescape(AbosMessages.get().LABEL_USER_HISTORICAL_LOAN_SANCTIONS), searchC,AbosMessages.get().LABEL_LIST);

		TreeTableColumn columnsPenaltyHistory[] = { new TreeTableColumn(18, 0, "getLoanUser.fullName"), new TreeTableColumn(17, 1, "getPenaltyType.getNomenclatorName"), new TreeTableColumn(17, 2, "getPenaltyState.getNomenclatorName"), new TreeTableColumn(17, 3, "getEffectiveDate"),
				new TreeTableColumn(17, 4, "getExpirationDate"), new TreeTableColumn(10, 5, "getDay") };

		tableHitorialPenalty.createTable(columnsPenaltyHistory);

		tableHitorialPenalty.setPageSize(10);

		painter.add(new Label(compoSRecord, 0),Percent.W100);
		
		LoanUser loanUserData = LoadLoanUserData(loanUser);
		lastString = AbosMessages.get().LABEL_DETAILS_OF_THE_PERSON;
		int edad = Auxiliary.getAge(loanUserData.getBirthDate());
		String aux = Integer.toString(edad);

		User user = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findUserByPersonID(loanUser.getPersonID());

		List<String> rigthList = new LinkedList<>();
		rigthList.add(loanUserData.fullName());
		rigthList.add(loanUserData.getDNI());
		rigthList.add(aux);
		rigthList.add(loanUserData.getSex().getNomenclatorName());

		grupControlsPerson = CompoundGroup.printGroup(loanUserData.getPerson().getPhoto().getImage(), personData, lastString, leftList, rigthList);

		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		List<String> rigthListLoanUser = new LinkedList<>();

		if (user != null) {
			rigthListLoanUser.add(user.getUsername());
		} else
			rigthListLoanUser.add(" - ");

		rigthListLoanUser.add(loanUser.getLoanUserCode());
		rigthListLoanUser.add(loanUser.getLoanUserType().getNomenclatorName());
		rigthListLoanUser.add(loanUser.getLoanUserState().getNomenclatorName());
		rigthListLoanUser.add(Auxiliary.FormatDate(loanUser.getRegistrationDate()));
		rigthListLoanUser.add(Auxiliary.FormatDate(loanUser.getExpirationDate()));
		rigthListLoanUser.add(loanUser.getRegisterby().getPerson().getFullName());

		grupControlsLoanUser = CompoundGroup.printGroup(loanUserGroup, lastStringLoanUser, leftListLoanUser, rigthListLoanUser);

		lastStringLoanUserCurrent = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		List<String> rigthListLoanUserCurrent = new LinkedList<>();
		rigthListLoanUserCurrent.add(loanUser.fullName());
		rigthListLoanUserCurrent.add(loanUser.getDNI());
		if (user != null) {
			rigthListLoanUserCurrent.add(user.getUsername());
		} else
			rigthListLoanUserCurrent.add(" - ");

		rigthListLoanUserCurrent.add(loanUser.getLoanUserCode());
		rigthListLoanUserCurrent.add(loanUser.getLoanUserType().getNomenclatorName());
		rigthListLoanUserCurrent.add(loanUser.getLoanUserState().getNomenclatorName());
		rigthListLoanUserCurrent.add(Auxiliary.FormatDate(loanUser.getExpirationDate()));

		grupControlsCurrent = CompoundGroup.printGroup(loanUser.getPhoto().getImage(), loanUserGroupCurrent, lastStringLoanUserCurrent, leftListLoanUserCurrent, rigthListLoanUserCurrent);

		lastStringLoanUserRecord = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		List<String> rigthListLoanUserRecord = new LinkedList<>();
		rigthListLoanUserRecord.add(loanUser.fullName());
		rigthListLoanUserRecord.add(loanUser.getDNI());
		if (user != null) {
			rigthListLoanUserRecord.add(user.getUsername());
		} else
			rigthListLoanUserRecord.add(" - ");

		rigthListLoanUserRecord.add(loanUser.getLoanUserCode());
		rigthListLoanUserRecord.add(loanUser.getLoanUserType().getNomenclatorName());
		rigthListLoanUserRecord.add(loanUser.getLoanUserState().getNomenclatorName());
		rigthListLoanUserRecord.add(Auxiliary.FormatDate(loanUser.getExpirationDate()));

		grupControlsRecord = CompoundGroup.printGroup(loanUser.getPhoto().getImage(), loanUserGroupRecord, lastStringLoanUserRecord, leftListLoanUserRecord, rigthListLoanUserRecord);

		btnClose.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2615553092700551346L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				registerLoanUserClass.notifyListeners(SWT.Dispose, new Event());
			}
		});

		btnNew.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				registerLoanUserClass.notifyListeners(SWT.Dispose, new Event());
				contributorService.selectContributor("addLoanUserID");
			}
		});		
		
		tableHistorialReservaciones.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {				
				initializeGridReservationHistorico(loanUser, event.currentPage - 1, event.pageSize);
			}
		});   
		
		tableHitorialPenalty.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {				
				initializeGridPenaltyHistorico(loanUser, event.currentPage - 1, event.pageSize);
			}
		});    
		
		tableHistorialTransaciones.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {				
				initializeGridTransactionHistoricos(loanUser, event.currentPage - 1, event.pageSize);
			}
		});
   
		// *************************
		initializeGridPenaltyHistorico(loanUser, 0, 10);
		initializeGridReservationHistorico(loanUser, 0, 10);
		initializeGridTransactionHistoricos(loanUser, 0, 10);
		
		
	
		try {
			
			tableActualPenalty.getPaginator().goToFirstPage();
			tableActualReservations.getPaginator().goToFirstPage();
			tableActualTransactions.getPaginator().goToFirstPage();
			tableHitorialPenalty.getPaginator().goToFirstPage();
			tableHistorialReservaciones.getPaginator().goToFirstPage();
			tableHistorialTransaciones.getPaginator().goToFirstPage();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		l10n();
		return parent;
	}

	public CRUDTreeTable getTableHitorialPenalty() {
		return tableHitorialPenalty;
	}

	public void setTableHitorialPenalty(CRUDTreeTable tableHitorialPenalty) {
		this.tableHitorialPenalty = tableHitorialPenalty;
	}

	public CRUDTreeTable getTableActualPenalty() {
		return tableActualPenalty;
	}

	public void setTableActualPenalty(CRUDTreeTable tableActualPenalty) {
		this.tableActualPenalty = tableActualPenalty;
	}

	@SuppressWarnings("unused")
	public LoanUser LoadLoanUserData(LoanUser loanUserLoaded) {
		if (!(loanUserLoaded == null)) {
			LoanUser loanUser;
			if (!(loanUserLoaded.getId() == null)) {
				Long idLoanUser = loanUserLoaded.getId();
				loanUser = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findOneLoanUser(idLoanUser);
			} else {
				loanUser = loanUserLoaded;
			}

		}
		return loanUser;
	}

	@Override
	public void l10n() {
		tabItemLoanUSer.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA));
		tabItemR.setText(AbosMessages.get().LABEL_RESERVATIONS);
		tabItemS.setText(AbosMessages.get().LABEL_SANCTIONS);
		tabItemSRecord.setText(AbosMessages.get().LABEL_SANCTIONS);

		tableActualTransactions.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tableActualTransactions.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		tableActualReservations.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tableActualReservations.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		tableHistorialReservaciones.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tableHistorialReservaciones.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		tableHitorialPenalty.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tableHitorialPenalty.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		tableActualPenalty.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tableActualPenalty.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		tableHistorialTransaciones.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tableHistorialTransaciones.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		tableActualTransactions.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), AbosMessages.get().LABEL_AUTHOR, AbosMessages.get().LABEL_OBJECT_TYPE, AbosMessages.get().LABEL_STATE, AbosMessages.get().TABLE_NAME_AND_LAST_NAME,
				AbosMessages.get().LABEL_TYPE_OF_USER));

		tableHistorialTransaciones.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), AbosMessages.get().LABEL_AUTHOR, AbosMessages.get().LABEL_OBJECT_TYPE, AbosMessages.get().LABEL_STATE, AbosMessages.get().TABLE_NAME_AND_LAST_NAME,
				AbosMessages.get().LABEL_TYPE_OF_USER));

		tableActualPenalty.setColumnHeaders(Arrays.asList(AbosMessages.get().TABLE_NAME_AND_LAST_NAME, MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_SANCTION), AbosMessages.get().LABEL_STATE, AbosMessages.get().LABEL_FROM, AbosMessages.get().LABEL_UP,
				MessageUtil.unescape(AbosMessages.get().LABEL_DAYS_LATE)));

		tableHitorialPenalty.setColumnHeaders(Arrays.asList(AbosMessages.get().TABLE_NAME_AND_LAST_NAME, MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_SANCTION), AbosMessages.get().LABEL_STATE, AbosMessages.get().LABEL_FROM, AbosMessages.get().LABEL_UP,
				MessageUtil.unescape(AbosMessages.get().LABEL_DAYS_LATE)));

		tableActualReservations.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RESERVATION), MessageUtil.unescape(AbosMessages.get().LABEL_RESERVATION_STATUS), AbosMessages.get().LABEL_OBJECT_TYPE, MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				AbosMessages.get().LABEL_AUTHOR));

		tableHistorialReservaciones.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RESERVATION), MessageUtil.unescape(AbosMessages.get().LABEL_RESERVATION_STATUS), AbosMessages.get().LABEL_OBJECT_TYPE, MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				AbosMessages.get().LABEL_AUTHOR));

		tableActualTransactions.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		tableActualReservations.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		tableActualPenalty.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		tableHistorialTransaciones.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		tableHistorialReservaciones.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		tableHitorialPenalty.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);

		lbUserCurrentTransaction.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CURRENT_LOAN_TRANSACTIONS));
		lbUserCurrentSanctions.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CURRENT_LOAN_SANCTIONS));
		lbRecordTransaction.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_HISTORICAL_LOAN_TRANSACTIONS));
		lbUserRecordSanctions.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_HISTORICAL_LOAN_SANCTIONS));
		lbUserRecordReservations.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_HISTORICAL_LOAN_RESERVATIONS));

		tabItemCurrentState.setText(AbosMessages.get().LABEL_CURRENT_STATUS);
		lbUserCurrentReservations.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CURRENT_LOAN_RESERVATIONS));

		btnNew.setText(AbosMessages.get().BUTTON_NEW);
		btnClose.setText(AbosMessages.get().BUTTON_CLOSE);

		tabItemT.setText(AbosMessages.get().LABEL_TRANSACTIONS);
		tabItemRecord.setText(AbosMessages.get().LABEL_RECORD);
		tabItemTRecord.setText(AbosMessages.get().LABEL_TRANSACTIONS);
		tabItemRRecord.setText(AbosMessages.get().LABEL_RESERVATIONS);

		lbViewLoanUser.setText(MessageUtil.unescape(AbosMessages.get().VIEW_LOAN_USER));

		lastString = AbosMessages.get().LABEL_DETAILS_OF_THE_PERSON;
		leftList.clear();
		leftList.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftList.add(AbosMessages.get().TABLE_AGE);
		leftList.add(AbosMessages.get().TABLE_SEX);
		CompoundGroup.l10n(grupControlsPerson, leftList);
		personData.setText(lastString);

		leftListLoanUser.clear();
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(AbosMessages.get().LABEL_REGISTER_DATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		leftListLoanUser.add(AbosMessages.get().LABEL_REGISTER_BY);
		CompoundGroup.l10n(grupControlsLoanUser, leftListLoanUser);

		leftListLoanUserCurrent.clear();
		leftListLoanUserCurrent.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUserCurrent.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUserCurrent.add(AbosMessages.get().LABEL_USER);
		leftListLoanUserCurrent.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUserCurrent.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUserCurrent.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUserCurrent.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		CompoundGroup.l10n(grupControlsCurrent, leftListLoanUserCurrent);

		leftListLoanUserRecord.clear();
		leftListLoanUserRecord.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUserRecord.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUserRecord.add(AbosMessages.get().LABEL_USER);
		leftListLoanUserRecord.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUserRecord.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUserRecord.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUserRecord.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		CompoundGroup.l10n(grupControlsRecord, leftListLoanUserRecord);

		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		loanUserGroup.setText(lastStringLoanUser);
		lastStringLoanUserCurrent = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		loanUserGroupCurrent.setText(lastStringLoanUserCurrent);
		lastStringLoanUserRecord = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		loanUserGroupRecord.setText(lastStringLoanUserRecord);
		
		refresh();
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public LoanUser getLoanUser() {
		return loanUser;
	}

	public void setLoanUser(LoanUser loanUser) {
		this.loanUser = loanUser;
	}

	public Composite getRegisterLoanUser() {
		return registerLoanUser;
	}
   
	public void setRegisterLoanUser(Composite registerLoanUser) {
		this.registerLoanUser = registerLoanUser;
	}

	Date date = new Timestamp(new Date().getTime());

	public void initializeGridPenalty(LoanUser loanUserLoaded, int page, int size) {

		Long idLoanUser = loanUserLoaded.getId();
		tableActualPenalty.clearRows();

		Page<Penalty> list = ((AllManagementLoanUserViewController) controller).getManagePenalty().findAllPenaltyByLoanUserCurrent(idLoanUser, page, size, direction, orderByStringPenalty);
		tableActualPenalty.setTotalElements((int) list.getTotalElements());

		tableActualPenalty.setRows(list.getContent());
		tableActualPenalty.refresh();

	}

	public void initializeGridPenaltyHistorico(LoanUser loanUserLoaded, int page, int size) {
		Long idLoanUser = loanUserLoaded.getId();
		tableHitorialPenalty.clearRows();
		Page<Penalty> list = ((AllManagementLoanUserViewController) controller).getManagePenalty().findAllPenaltyByLoanUserHistory(idLoanUser, page, size, direction, orderByStringPenalty);
		tableHitorialPenalty.setTotalElements((int) list.getTotalElements());
		tableHitorialPenalty.setRows(list.getContent());
		tableHitorialPenalty.refresh();

	}

	private void initializeGridReservation(LoanUser loanUserLoaded, int page, int size) {
		Long idLoanUser = loanUserLoaded.getId();
		tableActualReservations.clearRows();

		Page<Reservation> list = ((AllManagementLoanUserViewController) controller).getManageReservation().findAllReservationByLoanUserCurrent(idLoanUser, page, size, direction, orderByStringReservations);
		tableActualReservations.setTotalElements((int) list.getTotalElements());
		tableActualReservations.setRows(list.getContent());
		tableActualReservations.refresh();

	}

	private void initializeGridReservationHistorico(LoanUser loanUserLoaded, int page, int size) {
		Long idLoanUser = loanUserLoaded.getId();
		tableHistorialReservaciones.clearRows();

		Page<Reservation> list = ((AllManagementLoanUserViewController) controller).getManageReservation().findAllReservationByLoanUserHistory(idLoanUser, page, size, direction, orderByStringReservations);
		tableHistorialReservaciones.setTotalElements((int) list.getTotalElements());
		tableHistorialReservaciones.setRows(list.getContent());
		tableHistorialReservaciones.refresh();

	}

	private void initializeGridTransaction(LoanUser loanUserLoaded, int page, int size) {
		Long idLoanUser = loanUserLoaded.getId();
		tableActualTransactions.clearRows();

		Page<Transaction> list = ((AllManagementLoanUserViewController) controller).getManageTransaction().findAllTransactionByLoanUserCurrent(idLoanUser, page, size, direction, orderByStringTransactions);
		tableActualTransactions.setTotalElements((int) list.getTotalElements());
		tableActualTransactions.setRows(list.getContent());
		tableActualTransactions.refresh();
	}

	private void initializeGridTransactionHistoricos(LoanUser loanUserLoaded, int page, int size) {
		Long idLoanUser = loanUserLoaded.getId();
		tableHistorialTransaciones.clearRows();

		Page<Transaction> list = ((AllManagementLoanUserViewController) controller).getManageTransaction().findAllTransactionByLoanUserHistory(idLoanUser, page, size, direction, orderByStringTransactions);
		tableHistorialTransaciones.setTotalElements((int) list.getTotalElements());
		tableHistorialTransaciones.setRows(list.getContent());
		tableHistorialTransaciones.refresh();
	}

	@Override
	public String getID() {
		return null;
	}

	public Composite getFragmentView() {
		return fragmentView;
	}

	public void setFragmentView(Composite fragmentView) {
		this.fragmentView = fragmentView;
	}

	public void cleanComponentParent() {
		try {
			Control[] temp = compoView.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		}
	}
    
	public void refresh() {
		Composite parentRezize = compoView.getParent();
		parentRezize.layout(true, true);
		parentRezize.redraw();
		parentRezize.update();
	}

}