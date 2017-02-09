package cu.uci.abcd.circulation.ui.auxiliary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
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
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.model.ViewAreaPenaltyConsult;
import cu.uci.abcd.circulation.ui.model.ViewAreaReservations;
import cu.uci.abcd.circulation.ui.model.ViewAreaTransactions;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ViewLoanObjectFragment extends FragmentPage {

	private ViewController controller;
	private LoanObject loanObject;
	private TabItem tabItemLoanObject;	
	private CRUDTreeTable tableHitorialPenalty;
	private CRUDTreeTable tableHistorialReservaciones;
	private CRUDTreeTable tableHistorialTransaciones;
	private int direction = 1024;	
	private String orderByStringTransactions = "transactionID";
	private String orderByStringPenalty = "penaltyID";
	private Label lbViewLoanObject;
	private List<String> searchC = new ArrayList<>();
	private List<Control> grupControls = new ArrayList<>();
	private LoanObject loanObjectData;
	private List<String> leftListLoanObject;
	private String lastStringLoanObject;
	private int dimension;
	private List<Control> grupControlsCurrent = new ArrayList<>();
	private LoanObject loanObjectDataCurrent;
	private String lastStringLoanObjectCurrent;
	private List<String> leftListLoanObjectCurrent;
	private Group loanObjectGroup;
	Group loanObjectGroupCurrent;
	TabItem tabItemRecord;
	TabItem tabItemSRecord;
	TabItem tabItemTRecord;
	TabItem tabItemRRecord;
	Label lbRecordTransaction;
	Label lbUserRecordReservations;
	Label lbUserRecordSanctions;
	
	public ViewLoanObjectFragment(ViewController controller, LoanObject loanObject, int dimension) {
		this.setController(controller);
		this.setLoanObject(loanObject);
		this.dimension= dimension;
	}

	public ViewLoanObjectFragment(ViewController controller) {
		this.setController(controller);
	}
//FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(Composite parent) {
	
		addComposite(parent);
		setDimension(dimension);	
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbViewLoanObject = new Label(parent, SWT.NONE);
		addHeader(lbViewLoanObject);
		
		Label separator = new Label(parent,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		
		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		add(tabFolder);

		Composite compoLoanObject = new Composite(tabFolder, SWT.None);
		compoLoanObject.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoLoanObject);
		tabItemLoanObject = new TabItem(tabFolder, SWT.None);
		tabItemLoanObject.setControl(compoLoanObject);
			
		final Composite grupo = new Composite(compoLoanObject, SWT.None);
		addComposite(grupo);
		grupo.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		loanObjectGroup = new Group(grupo, SWT.NORMAL);
		add(loanObjectGroup);

		loanObjectData = LoadObjectData(loanObject);
		lastStringLoanObject = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN);
		leftListLoanObject = new LinkedList<>();
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		
		if (loanObjectData.getAuthor()!= null) {
		leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);}
		
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		if (loanObjectData.getVolume() != null) {
			leftListLoanObject.add(AbosMessages.get().LABEL_VOLUMEN);
		}
		if (loanObjectData.getTome() != null) {
			leftListLoanObject.add(AbosMessages.get().LABEL_TOME);
		}
		leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
		leftListLoanObject.add(AbosMessages.get().LABEL_STATE);

		List<String> rigthListLoanObject = new LinkedList<>();
		rigthListLoanObject.add(loanObjectData.getTitle());
		if (loanObjectData.getAuthor()!= null) {
			rigthListLoanObject.add(loanObjectData.getAuthor());
		}
		rigthListLoanObject.add(loanObjectData.getInventorynumber());
		if (loanObjectData.getVolume() != null) {
			rigthListLoanObject.add(loanObjectData.getVolume());
		}
		if (loanObjectData.getTome() != null) {
			rigthListLoanObject.add(loanObjectData.getTome());
		}
		rigthListLoanObject.add(loanObjectData.getRecordType().getNomenclatorName());
		rigthListLoanObject.add(loanObjectData.getLoanObjectState().getNomenclatorName());

		grupControls=CompoundGroup.printGroup(loanObjectGroup, lastStringLoanObject, leftListLoanObject, rigthListLoanObject);

		// /--------------------Historial

		tabItemRecord = new TabItem(tabFolder, SWT.None);
		
		Composite compoRecord = new Composite(tabFolder, SWT.NONE);
		addComposite(compoRecord);
		compoRecord.setData(RWT.CUSTOM_VARIANT, "gray_background");
		tabItemRecord.setControl(compoRecord);
		
		loanObjectGroupCurrent = new Group(compoRecord, SWT.NORMAL);
		add(loanObjectGroupCurrent);
		
		loanObjectDataCurrent = LoadObjectData(loanObject);
		lastStringLoanObjectCurrent = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN);
		leftListLoanObjectCurrent = new LinkedList<>();				
		leftListLoanObjectCurrent.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		leftListLoanObjectCurrent.add(AbosMessages.get().LABEL_AUTHOR);
		leftListLoanObjectCurrent.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		if (loanObjectDataCurrent.getVolume() != null) {
			leftListLoanObjectCurrent.add(AbosMessages.get().LABEL_VOLUMEN);
		}
		if (loanObjectDataCurrent.getTome() != null) {
			leftListLoanObjectCurrent.add(AbosMessages.get().LABEL_TOME);
		}
		leftListLoanObjectCurrent.add(AbosMessages.get().LABEL_OBJECT_TYPE);
		leftListLoanObjectCurrent.add(AbosMessages.get().LABEL_STATE);

		List<String> rigthListLoanObjectCurrent = new LinkedList<>();
		rigthListLoanObjectCurrent.add(loanObjectDataCurrent.getTitle());
		rigthListLoanObjectCurrent.add(loanObjectDataCurrent.getAuthor());
		rigthListLoanObjectCurrent.add(loanObjectDataCurrent.getInventorynumber());
		if (loanObjectDataCurrent.getVolume() != null) {
			rigthListLoanObjectCurrent.add(loanObjectDataCurrent.getVolume());
		}
		if (loanObjectDataCurrent.getTome() != null) {
			rigthListLoanObjectCurrent.add(loanObjectDataCurrent.getTome());
		}
		rigthListLoanObjectCurrent.add(loanObjectDataCurrent.getRecordType().getNomenclatorName());
		rigthListLoanObjectCurrent.add(loanObjectDataCurrent.getLoanObjectState().getNomenclatorName());

		grupControlsCurrent=CompoundGroup.printGroup(loanObjectGroupCurrent, lastStringLoanObjectCurrent, leftListLoanObject, rigthListLoanObject);

		reset();
		// ------------------TabFolder de adentro
		TabFolder tabFolderRecord = new TabFolder(compoRecord, SWT.NONE);
		add(tabFolderRecord);

		// ------------------tabItems Transacciones
		tabItemTRecord = new TabItem(tabFolderRecord, SWT.None);
		
		Composite compoTR = new Composite(tabFolderRecord, SWT.None);
		compoTR.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoTR);

		tabItemTRecord.setControl(compoTR);

		lbRecordTransaction = new Label(compoTR, SWT.NONE);
		addHeader(lbRecordTransaction);

	//	final String recordTransaction = lbRecordTransaction.getText().toString();

		tableHistorialTransaciones = new CRUDTreeTable(compoTR, SWT.NONE);
		add(tableHistorialTransaciones);
		tableHistorialTransaciones.setEntityClass(Transaction.class);
		tableHistorialTransaciones.setWatch(true, new ViewAreaTransactions(controller));
		//FormDatas.attach(tableHistorialTransaciones).atTopTo(lbRecordTransaction, 3).atLeft(15).atRight(15);
		
		tableHistorialTransaciones.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});
		
		CRUDTreeTableUtils.configReports(tableHistorialTransaciones, MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_HISTORICAL_LOAN_TRANSACTIONS), searchC,AbosMessages.get().LABEL_LIST);
	
		TreeTableColumn columnsTransaccion[] = {
				new TreeTableColumn(16, 0, "getLoanObject.getTitle"),
				new TreeTableColumn(16, 1, "getLoanObject.getAuthor"),
				new TreeTableColumn(16, 2,
						"getLoanObject.getRecordType.getNomenclatorName"),
				new TreeTableColumn(15, 3, "getState.getNomenclatorName"),
				new TreeTableColumn(19, 4, "getLoanUser.fullName"),
				new TreeTableColumn(14, 5,
						"getLoanUser.getLoanUserType.getNomenclatorName") };

		tableHistorialTransaciones.createTable(columnsTransaccion);

		tableHistorialTransaciones.setPageSize(10);

		add(new Label(compoTR, 0),Percent.W100);
		
		// ------------------tabItems Reservaciones
		tabItemRRecord = new TabItem(tabFolderRecord, SWT.None);
		
		Composite compoRRecord = new Composite(tabFolderRecord, SWT.None);
		compoRRecord.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoRRecord);
		tabItemRRecord.setControl(compoRRecord);

		lbUserRecordReservations = new Label(compoRRecord, SWT.NONE);
		addHeader(lbUserRecordReservations);

	//	final String userRecordReservations = lbUserRecordReservations.getText().toString();

		tableHistorialReservaciones = new CRUDTreeTable(compoRRecord, SWT.NONE);
		add(tableHistorialReservaciones);
		tableHistorialReservaciones.setEntityClass(Reservation.class);
		tableHistorialReservaciones.setWatch(true, new ViewAreaReservations(controller));
		
		tableHistorialReservaciones.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});
		
		CRUDTreeTableUtils.configReports(tableHistorialReservaciones, MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_HISTORICAL_LOAN_RESERVATIONS), searchC,AbosMessages.get().LABEL_LIST);
	
		TreeTableColumn columnsReservation[] = {
				new TreeTableColumn(20, 0, "getReservationDate"),
				new TreeTableColumn(20, 1,
						"getState.getNomenclatorName"),
				new TreeTableColumn(20, 2, "getObjecttype.getNomenclatorName"),
				new TreeTableColumn(20, 3, "getTitle"),
				new TreeTableColumn(20, 4, "getAuthorLoanObject")
				};

		tableHistorialReservaciones.createTable(columnsReservation);

		tableHistorialReservaciones.setPageSize(10);

		add(new Label(compoRRecord, 0),Percent.W100);
		
		// ------------------tabItems Sanciones
		tabItemSRecord = new TabItem(tabFolderRecord, SWT.None);
		
		Composite compoSRecord = new Composite(tabFolderRecord, SWT.None);
		compoSRecord.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoSRecord);
		tabItemSRecord.setControl(compoSRecord);

		lbUserRecordSanctions = new Label(compoSRecord, SWT.NONE);
		addHeader(lbUserRecordSanctions);
		
		//final String recordSanctions = lbUserRecordSanctions.getText().toString();

		tableHitorialPenalty = new CRUDTreeTable(compoSRecord, SWT.NONE);
		add(tableHitorialPenalty);
		tableHitorialPenalty.setEntityClass(Penalty.class);
		tableHitorialPenalty.setWatch(true, new ViewAreaPenaltyConsult(controller));
		
		tableHitorialPenalty.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		CRUDTreeTableUtils.configReports(tableHitorialPenalty, MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_HISTORICAL_LOAN_SANCTIONS), searchC,AbosMessages.get().LABEL_LIST);

		TreeTableColumn columnsPenalty[] = {				
				new TreeTableColumn(18, 0, "getLoanUser.fullName"),
				new TreeTableColumn(17, 1, "getPenaltyType.getNomenclatorName"),
				new TreeTableColumn(17, 2, "getPenaltyState.getNomenclatorName"),
				new TreeTableColumn(17, 3, "getEffectiveDate"),
				new TreeTableColumn(17, 4, "getExpirationDate"),
				new TreeTableColumn(10, 5, "getDay")};		

		tableHitorialPenalty.createTable(columnsPenalty);

		tableHitorialPenalty.setPageSize(10);	
		
		add(new Label(compoSRecord, 0),Percent.W100);
		
		initializeGridPenaltyHistorico(loanObject, 0, 10);
		initializeGridReservationHistorico(loanObject, 0, 10);
		initializeGridTransactionHistoricos(loanObject, 0, 10);
		
		tableHistorialReservaciones.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {				
				initializeGridReservationHistorico(loanObject, event.currentPage - 1, event.pageSize);
			}
		});
		
		tableHistorialTransaciones.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {				
				initializeGridTransactionHistoricos(loanObject, event.currentPage - 1, event.pageSize);
			}
		});
		
		tableHitorialPenalty.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {				
				initializeGridPenaltyHistorico(loanObject, event.currentPage - 1, event.pageSize);
			}
		});
		
		
		
		try {			
			tableHistorialReservaciones.getPaginator().goToFirstPage();
			tableHistorialTransaciones.getPaginator().goToFirstPage();
			tableHitorialPenalty.getPaginator().goToFirstPage();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		l10n();
		return parent;
	}

	public LoanObject LoadObjectData(LoanObject LoanObjectLoaded) {
		if (!(LoanObjectLoaded == null)) {

			if (!(LoanObjectLoaded.getLoanObjectID() == null)) {
				Long idLoanObject = LoanObjectLoaded.getLoanObjectID();
				loanObject = ((AllManagementLoanUserViewController) controller).getManageObject().findOneLoanObject(idLoanObject);
			} else {
				loanObject = LoanObjectLoaded;
			}

		}
		return loanObject;
	}



	private void initializeGridPenaltyHistorico(LoanObject LoanObjectLoaded, int page, int size) {
		Long idLoanObject = LoanObjectLoaded.getLoanObjectID();
		tableHitorialPenalty.clearRows();

		Page<Penalty> listPenaltyHistory = ((AllManagementLoanUserViewController) controller).getManagePenalty().findAllPenaltyByLoanObject(idLoanObject, page, size, direction, orderByStringPenalty);
		tableHitorialPenalty.setTotalElements((int) listPenaltyHistory.getTotalElements());
		tableHitorialPenalty.setRows(listPenaltyHistory.getContent());
		tableHitorialPenalty.refresh();

	}
	
	private void initializeGridReservationHistorico(LoanObject LoanObjectLoaded, int page, int size) {
		Long idLoanObject = LoanObjectLoaded.getLoanObjectID();
		tableHistorialReservaciones.clearRows();
		Nomenclator reservationState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.RESERVATION_STATE_EXECUTED);
		Nomenclator reservationStateCancel = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.RESERVATION_STATE_CANCELLED);

		List<Reservation> listR = ((AllManagementLoanUserViewController) controller).getManageReservation().findReservationByState(reservationState);
		List<Reservation> listReservationHistory = Auxiliary.listReservationLoanObject(listR, idLoanObject);
			
		List<Reservation> listRCancel = ((AllManagementLoanUserViewController) controller).getManageReservation().findReservationByState(reservationStateCancel);
		List<Reservation> listReservationHistoryCancel = Auxiliary.listReservationLoanObject(listRCancel, idLoanObject);
	
		listReservationHistory.addAll(listReservationHistoryCancel);
		
	//	Page<Reservation> listReservationHistory = ((AllManagementLoanUserViewController) controller).findAllReservationSearchLoanObject(idLoanObject, page, size, direction, orderByStringReservations);
		tableHistorialReservaciones.setTotalElements((int) listReservationHistory.size());
		tableHistorialReservaciones.setRows(listReservationHistory);
		tableHistorialReservaciones.refresh();

	}

	private void initializeGridTransactionHistoricos(LoanObject LoanObjectLoaded, int page, int size) {
		Long idLoanObject = LoanObjectLoaded.getLoanObjectID();
		tableHistorialTransaciones.clearRows();

		Page<Transaction> list = ((AllManagementLoanUserViewController) controller).getManageTransaction().findAllTransactionByLoanObject(idLoanObject, page, size, direction, orderByStringTransactions);
		tableHistorialTransaciones.setTotalElements((int) list.getTotalElements());
		tableHistorialTransaciones.setRows(list.getContent());
		tableHistorialTransaciones.refresh();
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		lastStringLoanObject = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN);
		leftListLoanObject.clear();
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		if (loanObjectData.getVolume() != null) {
			leftListLoanObject.add(AbosMessages.get().LABEL_VOLUMEN);
		}
		if (loanObjectData.getTome() != null) {
			leftListLoanObject.add(AbosMessages.get().LABEL_TOME);
		}
		leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
		leftListLoanObject.add(AbosMessages.get().LABEL_STATE);
		loanObjectGroup.setText(lastStringLoanObject);
		CompoundGroup.l10n(grupControls, leftListLoanObject);
		
		
		lastStringLoanObjectCurrent = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN);
		leftListLoanObjectCurrent.clear();
		leftListLoanObjectCurrent.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		leftListLoanObjectCurrent.add(AbosMessages.get().LABEL_AUTHOR);
		leftListLoanObjectCurrent.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		if (loanObjectDataCurrent.getVolume() != null) {
			leftListLoanObjectCurrent.add(AbosMessages.get().LABEL_VOLUMEN);
		}
		if (loanObjectDataCurrent.getTome() != null) {
			leftListLoanObjectCurrent.add(AbosMessages.get().LABEL_TOME);
		}
		leftListLoanObjectCurrent.add(AbosMessages.get().LABEL_OBJECT_TYPE);
		leftListLoanObjectCurrent.add(AbosMessages.get().LABEL_STATE);
		
		CompoundGroup.l10n(grupControlsCurrent, leftListLoanObjectCurrent);
		loanObjectGroupCurrent.setText(lastStringLoanObjectCurrent);
		
		tableHistorialTransaciones.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				AbosMessages.get().LABEL_AUTHOR,
				AbosMessages.get().LABEL_OBJECT_TYPE,
				AbosMessages.get().LABEL_STATE, AbosMessages.get().TABLE_NAME_AND_LAST_NAME,
				AbosMessages.get().LABEL_TYPE_OF_USER));
		
		tableHistorialReservaciones.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RESERVATION),
				MessageUtil.unescape(AbosMessages.get().LABEL_RESERVATION_STATUS),
				AbosMessages.get().LABEL_OBJECT_TYPE,
				MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), AbosMessages
						.get().LABEL_AUTHOR));
		
		tableHitorialPenalty
				.setColumnHeaders(Arrays.asList(
						AbosMessages.get().TABLE_NAME_AND_LAST_NAME,
						MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_SANCTION),
						AbosMessages.get().LABEL_STATE, AbosMessages.get().LABEL_FROM,
						AbosMessages.get().LABEL_UP,
						MessageUtil.unescape(AbosMessages.get().LABEL_DAYS_LATE)));

		tabItemLoanObject.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_LOAN_DATA));
		tabItemRecord.setText(AbosMessages.get().LABEL_RECORD);
		tabItemSRecord.setText(AbosMessages.get().LABEL_SANCTIONS);
		tabItemRRecord.setText(AbosMessages.get().LABEL_RESERVATIONS);
		tabItemTRecord.setText(AbosMessages.get().LABEL_TRANSACTIONS);

		tableHistorialReservaciones.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		tableHistorialTransaciones.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		tableHitorialPenalty.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		
		lbViewLoanObject.setText(MessageUtil.unescape(AbosMessages.get().VIEW_OBJECT_LOAN));
		tableHitorialPenalty.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tableHitorialPenalty.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		tableHistorialReservaciones.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tableHistorialReservaciones.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		tableHistorialTransaciones.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tableHistorialTransaciones.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		lbRecordTransaction.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_HISTORICAL_LOAN_TRANSACTIONS));
		lbUserRecordReservations.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_HISTORICAL_LOAN_RESERVATIONS));
		lbUserRecordSanctions.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_HISTORICAL_LOAN_SANCTIONS));
		
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
	public LoanObject getLoanObject() {
		return loanObject;
	}
	public void setLoanObject(LoanObject loanObject) {
		this.loanObject = loanObject;
	}

}
