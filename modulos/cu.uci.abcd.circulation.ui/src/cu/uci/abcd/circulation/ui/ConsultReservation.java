package cu.uci.abcd.circulation.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import org.eclipse.swt.widgets.TreeItem;
import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.listener.EventConsultReservation;
import cu.uci.abcd.circulation.ui.model.EditorAreaCancelReservation;
import cu.uci.abcd.circulation.ui.model.EditorAreaRenewReservation;
import cu.uci.abcd.circulation.ui.model.EditorAreaReservationObjectLoan;
import cu.uci.abcd.circulation.ui.model.ViewAreaReservations;
import cu.uci.abcd.domain.circulation.Reservation;
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
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IActionDenied;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConsultReservation extends ContributorPage implements Contributor {

	private ConsultReservation consultReservation = this;

	private List<String> searchC = new ArrayList<>();

	private Label lbConsultReservation;
	private Label lbSearchCriteria;
	private Label lbObjectLoanData;
	private Label lbTitle;
	private Label lbObjectType;
	private Label lbUserLoanData;
	private Label lbUserCode;
	private Label lbUserType;
	private Label lbDateReservation;
	private Label lbStateReservation;
	private Label lbLoanType;

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
	private Text txtTitle;
	private Text txtUserCode;
	private Text txtFirstName;
	private Text txtSecondName;
	private Text txtFirstLast;
	private Text txtSecondLast;

	private Combo comboObjectType;
	private Combo comboState;
	private Combo comboUserType;
	private Combo comboLoanType;
	private CRUDTreeTable tabla;

	private Date dateRegister = null;
	private Date endDateRegister = null;

	private DateTime dateTime;
	private DateTime dateTime1;

	private Composite compoLoanObject;

	// search//
	private Nomenclator record_type_id = null;
	private String loan_user_code = null;
	private Nomenclator loan_user_type_id = null;
	private String first_Name = null;
	private String second_Name = null;
	private String first_Surname = null;
	private String second_Surname = null;

	private Nomenclator reservation_state = null;
	private Nomenclator reservation_type = null;
	private String title = null;

	private String orderByString = "loanUser.person.firstName";
	private int direction = 1024;

	private Composite compoReservationAdvanced;
	private Composite compoLoanUserAdvanced;
	private Composite compoButton;
	private List<Room> listWorkerRooms;
	private List<Integer> listIDRoomWorker;
	private Library library;
	private User user;
	private Composite compoButton1;
	private Composite compoLoanUser;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().NAME_UI_CONSULT_RESERVATION);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	// FIXME METODO COMPLEJO
	public Control createUIControl(Composite parent) {
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		
		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);
	
		// ----------------Compo
		// LoanUser-------------------------------------------

		compoLoanUser = new Composite(parent, SWT.NORMAL);
		compoLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoLoanUser);

		lbConsultReservation = new Label(compoLoanUser, SWT.NONE);
		addHeader(lbConsultReservation);

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

		lbTitle = new Label(compoLoanObject, SWT.NONE);
		add(lbTitle);
		txtTitle = new Text(compoLoanObject, SWT.NORMAL);
		add(txtTitle);

		lbObjectType = new Label(compoLoanObject, SWT.NONE);
		add(lbObjectType);
		comboObjectType = new Combo(compoLoanObject, SWT.READ_ONLY);
		add(comboObjectType);

		// ******************Compo Reservation***********************

		Composite compoReservations = new Composite(parent, SWT.NORMAL);
		compoReservations.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoReservations);

		lbDateReservation = new Label(compoReservations, SWT.NONE);
		addHeader(lbDateReservation);

		lbStateReservation = new Label(compoReservations, SWT.NONE);
		add(lbStateReservation);
		comboState = new Combo(compoReservations, SWT.READ_ONLY);
		add(comboState);

		lbLoanType = new Label(compoReservations, SWT.NONE);
		add(lbLoanType);
		comboLoanType = new Combo(compoReservations, SWT.READ_ONLY);
		add(comboLoanType);

		compoReservationAdvanced = new Composite(parent, SWT.NORMAL);
		compoReservationAdvanced.setVisible(false);
		compoReservationAdvanced.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoReservationAdvanced);

		lbRangeDate = new Label(compoReservationAdvanced, SWT.RIGHT);
		addHeader(lbRangeDate);

		lbFrom = new Label(compoReservationAdvanced, SWT.NONE);
		add(lbFrom);
		dateTime = new DateTime(compoReservationAdvanced, SWT.BORDER | SWT.DROP_DOWN);
		add(dateTime);

		cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToBeforeOneMonth(dateTime);
		

		lbUp = new Label(compoReservationAdvanced, SWT.NONE);
		add(lbUp);
		dateTime1 = new DateTime(compoReservationAdvanced, SWT.BORDER | SWT.DROP_DOWN);
		add(dateTime1);

		// --------------------------------------------------------------
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

		link.addListener(SWT.Selection, new EventConsultReservation(compoLoanUser, compoLoanUserAdvanced, compoReservations, compoReservationAdvanced, link, this));

		btnNewSearch.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8525541221879944571L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				txtTitle.setText("");
				txtUserCode.setText("");
				txtFirstLast.setText("");
				txtFirstName.setText("");
				txtSecondLast.setText("");
				txtSecondName.setText("");

				comboLoanType.select(0);
				comboObjectType.select(0);
				comboState.select(0);
				comboUserType.select(0);

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
			private static final long serialVersionUID = 1L;

			// FIXME METODO COMPLEJO
			@SuppressWarnings({ "deprecation" })
			@Override
			public void widgetSelected(SelectionEvent e) {

				searchC.clear();
				tabla.clearRows();
				lbCoincidenceList.setVisible(true);
				tabla.setVisible(true);
				tabla.destroyEditableArea();

				if (!compoLoanUserAdvanced.getVisible()) {
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

				title = (txtTitle.getText().length() > 0 ? txtTitle.getText().replaceAll(" +", " ").trim() : null);
				loan_user_code = (txtUserCode.getText().length() > 0 ? txtUserCode.getText().replaceAll(" +", " ").trim() : null);
				first_Name = (txtFirstName.getText().length() > 0 ? txtFirstName.getText().replaceAll(" +", " ").trim() : null);
				second_Name = (txtSecondName.getText().length() > 0 ? txtSecondName.getText().replaceAll(" +", " ").trim() : null);
				first_Surname = (txtFirstLast.getText().length() > 0 ? txtFirstLast.getText().replaceAll(" +", " ").trim() : null);
				second_Surname = (txtSecondLast.getText().length() > 0 ? txtSecondLast.getText().replaceAll(" +", " ").trim() : null);

				UiUtils.get().addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE), txtUserCode.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), txtTitle.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_NAME), txtFirstName.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_NAME), txtSecondName.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_LAST), txtFirstLast.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_LAST), txtSecondLast.getText());

				if (UiUtils.getSelected(comboUserType) == null) {
					loan_user_type_id = null;
				} else {
					loan_user_type_id = (Nomenclator) UiUtils.getSelected(comboUserType);
					searchC.add(lbUserType.getText());
					searchC.add(comboUserType.getText());
				}

				if (UiUtils.getSelected(comboObjectType) == null) {
					record_type_id = null;
				} else {
					record_type_id = (Nomenclator) UiUtils.getSelected(comboObjectType);
					searchC.add(lbObjectType.getText());
					searchC.add(comboObjectType.getText());
				}

				if (UiUtils.getSelected(comboState) == null) {
					reservation_state = null;
				} else {
					reservation_state = (Nomenclator) UiUtils.getSelected(comboState);
					searchC.add(lbStateReservation.getText());
					searchC.add(comboState.getText());
				}

				if (UiUtils.getSelected(comboLoanType) == null) {
					reservation_type = null;
				} else {
					reservation_type = (Nomenclator) UiUtils.getSelected(comboLoanType);
					searchC.add(lbLoanType.getText());
					searchC.add(comboLoanType.getText());
				}
     
				orderByString = "loanUser.person.firstName";
				direction = 1024;
				searchReservations(0, tabla.getPageSize());
				if (tabla.getRows().isEmpty()) {
					lbCoincidenceList.setVisible(false);
					tabla.setVisible(false);
					RetroalimentationUtils.showInformationMessage(compoButton1, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				} else
					tabla.getPaginator().goToFirstPage();

				refresh();
			}
		});

		Label separador = new Label(compoButton, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separador);

		compoButton1 = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton1);
		compoButton1.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbCoincidenceList = new Label(compoButton1, SWT.NONE);
		addHeader(lbCoincidenceList);
		lbCoincidenceList.setVisible(false);

		// -----------------Tabla--------------------------------------
		tabla = new CRUDTreeTable(compoButton1, SWT.NONE);
		add(tabla);
		tabla.setVisible(false);
		tabla.setEntityClass(Reservation.class);
		tabla.setVisualEntityManager(new MessageVisualEntityManager(tabla));

		tabla.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		Column columnRenew = new Column("renew", parent.getDisplay(), new TreeColumnListener() {
		@Override
			public void handleEvent(TreeColumnEvent event) {

				if (event.showEditableArea) {
					TreeItem selectedTreeItem = event.item;
					IGridViewEntity selectedEntity = (IGridViewEntity) selectedTreeItem.getData("entity");
    
					try {
						tabla.createEditableArea(new EditorAreaRenewReservation(controller), selectedEntity, tabla.getVisualEntityManager());
					} catch (Exception e) {
						e.printStackTrace();
					}					
					refresh();
				}
			}
		});
	
		columnRenew.setToolTipText(AbosMessages.get().BUTTON_RENEW);
		columnRenew.setAlignment(SWT.CENTER);		
		tabla.addActionColumn(columnRenew);
		
		tabla.setWatch(true, new ViewAreaReservations(controller));

		Column columnLoan= new Column("loan", parent.getDisplay(), new TreeColumnListener() {
			@Override
			public void handleEvent(TreeColumnEvent event) {
				if (event.showEditableArea) {
					TreeItem selectedTreeItem = event.item;
					IGridViewEntity selectedEntity = (IGridViewEntity) selectedTreeItem.getData("entity");
					tabla.createEditableArea(new EditorAreaReservationObjectLoan(controller, tabla, consultReservation), selectedEntity, tabla.getVisualEntityManager());
				}
			}
		});
		columnLoan.setToolTipText(AbosMessages.get().BUTTON_LOAN);
		columnLoan.setAlignment(SWT.CENTER);		
		tabla.addActionColumn(columnLoan);
		
		Column columnReject= new Column("reject", parent.getDisplay(), new TreeColumnListener() {	
		@Override
			public void handleEvent(TreeColumnEvent event) {

				if (event.showEditableArea) {
					TreeItem selectedTreeItem = event.item;
					IGridViewEntity selectedEntity = (IGridViewEntity) selectedTreeItem.getData("entity");

					tabla.createEditableArea(new EditorAreaCancelReservation(controller, tabla, consultReservation), selectedEntity, tabla.getVisualEntityManager());
				}
			}
		});
		columnReject.setToolTipText(AbosMessages.get().BUTTON_CANCEL);
		columnReject.setAlignment(SWT.CENTER);		
		
		tabla.addActionColumn(columnReject);
		
		CRUDTreeTableUtils.configReports(tabla, MessageUtil.unescape(AbosMessages.get().NAME_CONSULT_RESERVATION), searchC);

		TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getReservationDate"), new TreeTableColumn(20, 1, "getState.getNomenclatorName"), new TreeTableColumn(20, 2, "getObjecttype.getNomenclatorName"), new TreeTableColumn(20, 3, "getTitle"),
				new TreeTableColumn(20, 4, "getAuthorLoanObject") };

		tabla.createTable(columns);

		tabla.setPageSize(10);
		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
			
				searchReservations(event.currentPage - 1, event.pageSize);
			}
		});

		tabla.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {

				user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
							
				Reservation entity = (Reservation) row;

				final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; // Milisegundos
																	// al día
				java.util.Date hoy = new java.util.Date(); // Fecha de hoy

				int año = entity.getReservationDate().getYear();
				int mes = entity.getReservationDate().getMonth();
				int dia = entity.getReservationDate().getDay(); // Fecha
																// anterior

				Calendar calendar = new GregorianCalendar(año, mes - 1, dia);
				java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());

				long diferencia = (hoy.getTime() - fecha.getTime()) / MILLSECS_PER_DAY;

				if (column.getIndex() == 5) {
					if (!entity.getState().getNomenclatorID().equals(Nomenclator.RESERVATION_STATE_PENDING)) {
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
				if (column.getIndex() == 6) {
					if (!entity.getState().getNomenclatorID().equals(Nomenclator.RESERVATION_STATE_PENDING)) {
						return true;
					} else if ((int) diferencia < 0) {
						System.out.println("la diferencia de fechas es menor de 0 ");
						return true;
					} else if ((int) diferencia == 0) {
						System.out.println("la diferencia de fechas es de 0 ");
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
				if (column.getIndex() == 7) {
					if (!entity.getState().getNomenclatorID().equals(Nomenclator.RESERVATION_STATE_PENDING)) {
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

	@Override
	public String getID() {
		return "consultReservationID";
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		lbConsultReservation.setText(MessageUtil.unescape(AbosMessages.get().NAME_CONSULT_RESERVATION));
		lbUserLoanData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA));
		lbSearchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		lbUserCode.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		lbUserType.setText(AbosMessages.get().LABEL_TYPE_OF_USER);
		lbTitle.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		lbObjectType.setText(AbosMessages.get().LABEL_OBJECT_TYPE);
		lbFirstName.setText(AbosMessages.get().LABEL_FIRST_NAME);
		lbSecondName.setText(AbosMessages.get().LABEL_SECOND_NAME);
		lbFirstLast.setText(AbosMessages.get().LABEL_FIRST_LAST);
		lbSecondLast.setText(AbosMessages.get().LABEL_SECOND_LAST);
		lbObjectLoanData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_LOAN_DATA));
		lbLoanType.setText(MessageUtil.unescape(AbosMessages.get().LABEL_RESERVATION_TYPE));
		lbRangeDate.setText(AbosMessages.get().LABEL_DATE_OF_REGISTRATION_RANGE);
		lbFrom.setText(AbosMessages.get().LABEL_FROM);
		lbUp.setText(AbosMessages.get().LABEL_UP);
		link.setText("<a>" + MessageUtil.unescape(AbosMessages.get().LABEL_ADVANCED_SEARCH) + "</a>");
		btnConsult.setText(AbosMessages.get().BUTTON_CONSULT);
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		lbCoincidenceList.setText(AbosMessages.get().LABEL_COINCIDENCE_LIST);
		lbDateReservation.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_RESERVATION));
		lbStateReservation.setText(MessageUtil.unescape(AbosMessages.get().LABEL_RESERVATION_STATUS));
		tabla.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CLOSE);
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_RESERVATION), MessageUtil.unescape(AbosMessages.get().LABEL_RESERVATION_STATUS), AbosMessages.get().LABEL_OBJECT_TYPE, MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				AbosMessages.get().LABEL_AUTHOR));
		tabla.l10n();
		initialize(comboUserType, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOANUSER_TYPE));
		initialize(comboState, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.RESERVATION_STATE));
		initialize(comboObjectType, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOANOBJECT_TYPE));
		initialize(comboLoanType, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.RESERVATION_TYPE));

		refresh();
	}

	public void searchReservations(int page, int size) {
		tabla.clearRows();
		Page<Reservation> listReservation = ((AllManagementLoanUserViewController) controller)
				.getManageReservation().findAllReservationConsult(
						loan_user_type_id, loan_user_code, first_Name,
						second_Name, first_Surname, second_Surname, title,
						record_type_id, reservation_state, reservation_type,
						dateRegister, endDateRegister,library, page, size, direction,
						orderByString);

		tabla.setTotalElements((int) listReservation.getTotalElements());

		if ((int) listReservation.getTotalElements() > 0) {
			tabla.setRows(listReservation.getContent());
			tabla.refresh();
		}

	}

	@Override
	public void setViewController(ViewController controller) {
		super.setViewController(controller);
	}
}