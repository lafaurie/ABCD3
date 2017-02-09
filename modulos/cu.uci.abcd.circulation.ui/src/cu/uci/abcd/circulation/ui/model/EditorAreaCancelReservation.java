package cu.uci.abcd.circulation.ui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.ConsultReservation;
import cu.uci.abcd.circulation.ui.auxiliary.Auxiliary;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class EditorAreaCancelReservation extends BaseEditableArea{

	private Label reasonCancel;
	private Button btnRenew;
	private ViewController controller;
	private CRUDTreeTable tabla;
	private Label lbCancelReservation;
	private Reservation fdtEntity;
	private Combo cbReason;
	private Library library;
	private ValidatorUtils validator;
	private String lastStringLoanObject;
	private CRUDTreeTable tableLoanObject;
	private int direction = 1024;
	private String orderByString = "title";
	private int page = 0;
	private int size = 10;
	private ConsultReservation consultReservation;
	
	private List<Control> grupControlsLoanUser = new ArrayList<>();
	private List<Control> grupControlsLoanObject = new ArrayList<>();
	private List<Control> grupControlsReservation = new ArrayList<>();
	
	private Group loanUserGroup;
	private Group reservationGroup;
	private Group loanObjectGroup;
	
	private List<String> leftListLoanUser = new LinkedList<>();
	private List<String> leftListReservation = new LinkedList<>();
	private List<String> leftListLoanObject = new LinkedList<>();
	
	private String lastStringLoanUser;
	private String lastStringReservation;
	private Label listObjectLoan;
	
	public EditorAreaCancelReservation(ViewController controller, CRUDTreeTable tabla,ConsultReservation consultReservation) {
		this.controller = controller;
		this.tabla = tabla;
		this.consultReservation = consultReservation;
	}
//FIXME METODO COMPLEJO
	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		setDimension(parent.getParent().getParent().getBounds().width);
		
		validator = new ValidatorUtils(new CustomControlDecoration());
		
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
	
		buildMessage(parent);
		
		lbCancelReservation = new Label(parent, SWT.NONE);
		addHeader(lbCancelReservation);
		
		Label separator = new Label(parent,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
				
		fdtEntity = (Reservation) entity.getRow();
		
		reasonCancel = new Label(parent, SWT.None);
		add(reasonCancel);
				
		cbReason = new Combo(parent, SWT.READ_ONLY);
		add(cbReason);
		initialize(cbReason, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(),Nomenclator.RESERVATION_REASON_CANCELLED));
		validator.applyValidator(cbReason, "cbReason", DecoratorType.REQUIRED_FIELD, true);
		
		add(new Label(parent, 0), Percent.W100);
		
		Label separador = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separador);
		
		loanUserGroup = new Group(parent, SWT.NORMAL);
		add(loanUserGroup);
	
		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
	
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
	
		grupControlsLoanUser=CompoundGroup.printGroup(fdtEntity.getLoanUser().getPhoto().getImage(), loanUserGroup, lastStringLoanUser, leftListLoanUser, rigthListLoanUser);

		//***********************
		
		lastStringReservation = MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_RESERVATION);
		reservationGroup = new Group(parent, SWT.NORMAL);
		add(reservationGroup);
	
		leftListReservation = new LinkedList<>();
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RESERVATION));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_RESERVATION_TYPE));

		List<String> rigthListReservation = new LinkedList<>();
		rigthListReservation.add(fdtEntity.getState().getNomenclatorName());
		rigthListReservation.add(Auxiliary.FormatDate(fdtEntity.getReservationDate()));
		rigthListReservation.add(fdtEntity.getReservationType().getNomenclatorName());

		grupControlsReservation=CompoundGroup.printGroup(reservationGroup, lastStringReservation, leftListReservation, rigthListReservation);

		br();
		
		//*******************Data LoanObject
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

		grupControlsLoanObject=CompoundGroup.printGroup(loanObjectGroup, lastStringLoanObject, leftListLoanObject, rigthListLoanObject);

		}
		l10n();
		return parent;
	}

	@Override
	public boolean isValid() {
		return false;
	}
	
	public void searchLoanObject(int page, int size) {
		tableLoanObject.clearRows();
		tableLoanObject.setTotalElements(fdtEntity.getReservationList().size());
		tableLoanObject.setRows(fdtEntity.getReservationList());
		tableLoanObject.refresh();
	}
	
	@Override
	public Composite createButtons(Composite parent,final IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		btnRenew = new Button(parent, SWT.PUSH);	
		btnRenew.setText(AbosMessages.get().BUTTON_ACEPT);
		btnRenew.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			//FIXME METODO COMPLEJO
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}
				else if (validator.decorationFactory.AllControlDecorationsHide()) {
					Reservation reservation = fdtEntity;
					Nomenclator reason = (Nomenclator) UiUtils.getSelected(cbReason);
					Nomenclator state = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.RESERVATION_STATE_CANCELLED);
				
					reservation.setState(state);
					reservation.setReasoncancel(reason);
					
					((AllManagementLoanUserViewController) controller).getManageReservation().addReservation(reservation);
	
					// ---------------Mensaje
					
					showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);
				
					manager.save(new BaseGridViewEntity<Reservation>(reservation));
					manager.refresh();
					consultReservation.searchReservations(0, tabla.getPageSize());
				}
					
				}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
						
			}
		}

		);		
		return parent;
	}

	public void l10n() {
		lbCancelReservation.setText(MessageUtil.unescape(AbosMessages.get().CANCEL_RESERVATION));
		reasonCancel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REASON_CANCEL));
		
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
		
		lastStringLoanObject = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN);
		leftListLoanObject.clear();
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
		leftListLoanObject.add(AbosMessages.get().LABEL_STATE);
		CompoundGroup.l10n(grupControlsLoanObject, leftListLoanObject);
		loanUserGroup.setText(lastStringLoanUser);
		loanObjectGroup.setText(lastStringLoanObject);	
	
		if (fdtEntity.getReservationList().size() > 1) {
			listObjectLoan.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_OBJECT_LOAN));
			
			tableLoanObject.setColumnHeaders(Arrays.asList(MessageUtil
					.unescape(AbosMessages.get().LABEL_TITLE), AbosMessages
					.get().LABEL_AUTHOR, MessageUtil.unescape(AbosMessages
					.get().LABEL_INVENTORY_NUMBER),
					AbosMessages.get().LABEL_OBJECT_TYPE,
					AbosMessages.get().LABEL_STATE));
		}
		else{					
		lastStringReservation = MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_RESERVATION);
		leftListReservation.clear();
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RESERVATION));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_RESERVATION_TYPE));
		CompoundGroup.l10n(grupControlsReservation, leftListReservation);
		
		reservationGroup.setText(lastStringReservation);
		}
			
	}

	@Override
	public boolean closable() {
		return true;
	}
	
	@Override
	public String getID() {
		return "addRenewReservationID";
	}
}
