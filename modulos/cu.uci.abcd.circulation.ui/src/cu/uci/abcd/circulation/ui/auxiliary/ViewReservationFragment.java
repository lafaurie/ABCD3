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

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.model.ViewAreaLoanObject;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.User;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ViewReservationFragment extends FragmentPage {

	private ViewController controller;
	private Reservation reservation;
	private PagePainter painter;
	private String lastStringReservation;
	private String lastStringLoanUser;
	private String lastStringLoanObject;
	private int dimension;
	private Label lbViewReservation;
	private CRUDTreeTable tableLoanObject;
	private int direction = 1024;
	private String orderByString = "title";
	private int page = 0;
	private int size = 10;
	private Reservation reservationData;
	
	private List<Control> grupControlsLoanUser = new ArrayList<>();
	private List<Control> grupControlsLoanObject = new ArrayList<>();
	private List<Control> grupControlsReservation = new ArrayList<>();

	private Group loanUserGroup;
	private Group loanObjectGroup;
	private Group reservationGroup;
	
	private List<String> leftListLoanUser = new LinkedList<>();
	private List<String> leftListReservation= new LinkedList<>();
	private List<String> leftListLoanObject = new LinkedList<>();
	private Label listObjectLoan;
	
	public ViewReservationFragment(ViewController controller) {
		this.setController(controller);
	}

	public ViewReservationFragment(ViewController controller, Reservation reservation, int dimension) {
		this.setController(controller);
		this.setReservation(reservation);
		this.dimension = dimension;
	}
    
		@Override
	//FIXME METODO COMPLEJO
	public Control createUIControl(final Composite parent) {
		painter = new FormPagePainter();
		painter.setDimension(dimension);
		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
	
		lbViewReservation = new Label(parent, SWT.NONE);
		painter.addHeader(lbViewReservation);
		
		Label separator = new Label(parent,  SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
	
		loanUserGroup = new Group(parent, SWT.NORMAL);
		painter.add(loanUserGroup);

		leftListLoanUser = new LinkedList<>();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		
		reservationData = LoadReservationData(reservation);
		User user = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findUserByPersonID(reservationData.getLoanUser().getPersonID());

		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		
		List<String> rigthListLoanUser = new LinkedList<>();
		rigthListLoanUser.add(reservationData.getLoanUser().fullName());
		rigthListLoanUser.add(reservationData.getLoanUser().getDNI());
		if (user != null) {
			rigthListLoanUser.add(user.getUsername());
		}
		else 
			rigthListLoanUser.add( " - ");
	
		rigthListLoanUser.add(reservationData.getLoanUser().getLoanUserCode());
		rigthListLoanUser.add(reservationData.getLoanUser().getLoanUserType().getNomenclatorName());
		rigthListLoanUser.add(reservationData.getLoanUser().getLoanUserState().getNomenclatorName());
		rigthListLoanUser.add(Auxiliary.FormatDate(reservationData.getLoanUser().getExpirationDate()));
	
		grupControlsLoanUser=CompoundGroup.printGroup(reservationData.getLoanUser().getPhoto().getImage(), loanUserGroup, lastStringLoanUser, leftListLoanUser, rigthListLoanUser);

		//********************Data Reservation
		reservationGroup = new Group(parent, SWT.NORMAL);
		painter.add(reservationGroup);
		lastStringReservation = MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_RESERVATION);

		leftListReservation= new LinkedList<>();
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RESERVATION));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_RESERVATION_TYPE));
		
		if (reservationData.getReasoncancel() != null) {
			leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_REASON_CANCEL));
		}	
		
		List<String> rigthListReservation= new LinkedList<>();
		rigthListReservation.add(reservationData.getState().getNomenclatorName());
		rigthListReservation.add(Auxiliary.FormatDate(reservationData.getReservationDate()));
		rigthListReservation.add(reservationData.getReservationType().getNomenclatorName());
		if (reservationData.getReasoncancel()!= null) {
			rigthListReservation.add(reservationData.getReasoncancel().getNomenclatorName());
		}
		
		grupControlsReservation=CompoundGroup.printGroup(reservationGroup, lastStringReservation, leftListReservation, rigthListReservation);
		
		painter.reset();
		
		//*******************Data LoanObject
		if (reservationData.getReservationList().size() > 1) {
		
		listObjectLoan = new Label(parent, 0);
		painter.addHeader(listObjectLoan);
			
		painter.reset();
		
		tableLoanObject = new CRUDTreeTable(parent, SWT.NONE);
		painter.add(tableLoanObject);
		tableLoanObject.setEntityClass(LoanObject.class);
		tableLoanObject.setWatch(true, new ViewAreaLoanObject(controller));
		
		tableLoanObject.addListener(SWT.Resize, new Listener() {			
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				//refresh();     
			}
		});
	
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
		painter.add(loanObjectGroup);
	
		leftListLoanObject = new LinkedList<>();
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);
		leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
		leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
		leftListLoanObject.add(AbosMessages.get().LABEL_STATE);

	
	List<String> rigthListLoanObject = new LinkedList<>();
	
	try {
		rigthListLoanObject.add(reservationData.getReservationList().get(0).getTitle());
		rigthListLoanObject.add(reservationData.getReservationList().get(0).getAuthor());
		rigthListLoanObject.add(reservationData.getReservationList().get(0).getInventorynumber());
		rigthListLoanObject.add(reservationData.getReservationList().get(0).getRecordType().getNomenclatorName());
		rigthListLoanObject.add(reservationData.getReservationList().get(0).getLoanObjectState().getNomenclatorName());
     
	
		grupControlsLoanObject=CompoundGroup.printGroup(loanObjectGroup, lastStringLoanObject, leftListLoanObject, rigthListLoanObject);
	}  catch (Exception e) {
		// TODO: handle exception
	}
		}
		painter.reset();
		
			
		// **********************************************
		
		l10n();		
		return parent;
	}

	public void searchLoanObject(int page, int size) {
		tableLoanObject.clearRows();
		tableLoanObject.setTotalElements(reservationData.getReservationList().size());
		tableLoanObject.setRows(reservationData.getReservationList());
		tableLoanObject.refresh();
	}
	
	@SuppressWarnings("unused")
	public Reservation LoadReservationData(Reservation reservationLoaded) {
		if (!(reservationLoaded == null)) {
			Reservation reservation;
			if (!(reservationLoaded.getReservationID() == null)) {
				Long idReservation = reservationLoaded.getReservationID();
				reservation = ((AllManagementLoanUserViewController) controller).getManageReservation().findOneReservation(idReservation);
			} else {
				reservation = reservationLoaded;
			}

		}
		return reservation;
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		lbViewReservation.setText(MessageUtil.unescape(AbosMessages.get().VIEW_RESERVATION));
		
		leftListLoanUser.clear();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		CompoundGroup.l10n(grupControlsLoanUser, leftListLoanUser);
		
		lastStringLoanUser = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		loanUserGroup.setText(lastStringLoanUser);
		
		leftListReservation.clear();
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RESERVATION));
		leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_RESERVATION_TYPE));
		if (reservationData.getReasoncancel() != null) {
			leftListReservation.add(MessageUtil.unescape(AbosMessages.get().LABEL_REASON_CANCEL));
		}
		CompoundGroup.l10n(grupControlsReservation, leftListReservation);
		
		lastStringReservation = MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_RESERVATION);
		reservationGroup.setText(lastStringReservation);
	
		if (reservationData.getReservationList().size() > 1) {
			listObjectLoan.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_OBJECT_LOAN));
			tableLoanObject.setColumnHeaders(Arrays.asList(MessageUtil
					.unescape(AbosMessages.get().LABEL_TITLE), AbosMessages
					.get().LABEL_AUTHOR, MessageUtil.unescape(AbosMessages
					.get().LABEL_INVENTORY_NUMBER),
					AbosMessages.get().LABEL_OBJECT_TYPE,
					AbosMessages.get().LABEL_STATE));

		}	
		else
		{
			
			leftListLoanObject.clear();
			leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
			leftListLoanObject.add(AbosMessages.get().LABEL_AUTHOR);
			leftListLoanObject.add(MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER));
			leftListLoanObject.add(AbosMessages.get().LABEL_OBJECT_TYPE);
			leftListLoanObject.add(AbosMessages.get().LABEL_STATE);
			CompoundGroup.l10n(grupControlsLoanObject, leftListLoanObject);
			
			lastStringLoanObject = MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN);
			loanObjectGroup.setText(lastStringLoanObject);
		}
		
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

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

}
