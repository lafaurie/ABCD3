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
import cu.uci.abcd.circulation.ui.listener.EventConsultObjectLoan;
import cu.uci.abcd.circulation.ui.model.EditorAreaUserLoan;
import cu.uci.abcd.circulation.ui.model.ViewAreaUserLoan;
import cu.uci.abcd.domain.circulation.LoanUser;
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
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConsultLoanUser extends ContributorPage implements Contributor {

	private List<String> searchC = new ArrayList<>();

	private Label lbConsultLoanUser;
	private Label lbSearchCriteria;
	private Label lbUserLoanData;
	private Label lbUserCode;
	private Label lbRoom;
	private Label lbUserType;
	private Label lbState;
	private Label lbPersonData;
	private Label lbFirstName;
	private Label lbSecondName;
	private Label lbFirstLast;
	private Label lbSecondLast;
	private Label lbCoincidenceList;

	private Text txtFirstName;
	private Text txtSecondName;
	private Text txtFirstLast;
	private Text txtSecondLast;

	private Link link;
	private Button btnConsult;
	private Button btnNewSearch;
	private Label lbRangeDate;

	private Label lbFrom;
	private Label lbUp;
	private CRUDTreeTable tabla;
	private Combo comboUserType;
	private Combo comboState;
	private Combo comboRoom;
	private Label lbCI;
	private Text textCI;
	private Text txtUserCode;

	private DateTime dateTime;
	private DateTime dateTime1;

	private String loan_user_code = null;
	private String loan_fistname = null;
	private String loan_secondname = null;
	private String loan_fistLast = null;
	private String loan_secondLast = null;
	private String loan_Dni = null;
	private Nomenclator loan_user_state = null;
	private Nomenclator loan_user_type_id = null;
	private Room loan_user_Romm = null;
	private Nomenclator faculty = null;
	private Nomenclator career = null;

	private Date dateRegister = null;
	private Date endDateRegister = null;

	private String orderByString = "person.firstName";
	private int direction = 1024;

	private Composite busquedaB;
	private Composite compoSearchA;
	private Composite compoButton;
	private Composite compoButton1;
	private Library library;

	private Label lbFaculty;
	private Label lbCareer;
	private Combo comboFaculty;
	private Combo comboCareer;
	private User user;
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().NAME_UI_CONSULT_LOAN_USER);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("serial")
	@Override
	// FIXME METODO COMPLEJO
	public Control createUIControl(final Composite parent) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
   
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");         
		
		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 160);
	
		busquedaB = new Composite(parent, SWT.NORMAL);
		busquedaB.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(busquedaB);

		lbConsultLoanUser = new Label(busquedaB, SWT.NONE);
		addHeader(lbConsultLoanUser);

		Label separatorHeader = new Label(busquedaB,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separatorHeader);
	
		lbSearchCriteria = new Label(busquedaB, SWT.RIGHT);
		addHeader(lbSearchCriteria);

		lbUserLoanData = new Label(busquedaB, SWT.RIGHT);
		addHeader(lbUserLoanData);

		lbUserCode = new Label(busquedaB, SWT.RIGHT);
		add(lbUserCode);
		txtUserCode = new Text(busquedaB, SWT.NORMAL);
		add(txtUserCode);

		lbRoom = new Label(busquedaB, SWT.RIGHT);
		add(lbRoom);
		comboRoom = new Combo(busquedaB, SWT.READ_ONLY);
		add(comboRoom);

		lbUserType = new Label(busquedaB, SWT.RIGHT);
		add(lbUserType);
		comboUserType = new Combo(busquedaB, SWT.READ_ONLY);
		add(comboUserType);

		lbState = new Label(busquedaB, SWT.RIGHT);
		add(lbState);
		comboState = new Combo(busquedaB, SWT.READ_ONLY);
		add(comboState);

		// -----------------Search Advanced---------------------------
		compoSearchA = new Composite(parent, SWT.NORMAL);
		compoSearchA.setVisible(false);
		addComposite(compoSearchA);
		compoSearchA.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbFaculty = new Label(compoSearchA, SWT.RIGHT);
		add(lbFaculty);
		comboFaculty = new Combo(compoSearchA, SWT.READ_ONLY);
		add(comboFaculty);

		lbCareer = new Label(compoSearchA, SWT.RIGHT);
		add(lbCareer);
		comboCareer = new Combo(compoSearchA, SWT.READ_ONLY);
		add(comboCareer);

		lbPersonData = new Label(compoSearchA, SWT.RIGHT);
		addHeader(lbPersonData);

		lbFirstName = new Label(compoSearchA, SWT.RIGHT);
		add(lbFirstName);
		txtFirstName = new Text(compoSearchA, SWT.NORMAL);
		add(txtFirstName);

		lbSecondName = new Label(compoSearchA, SWT.RIGHT);
		add(lbSecondName);
		txtSecondName = new Text(compoSearchA, SWT.NORMAL);
		add(txtSecondName);

		br();

		lbFirstLast = new Label(compoSearchA, SWT.RIGHT);
		add(lbFirstLast);
		txtFirstLast = new Text(compoSearchA, SWT.NORMAL);
		add(txtFirstLast);

		lbSecondLast = new Label(compoSearchA, SWT.RIGHT);
		add(lbSecondLast);
		txtSecondLast = new Text(compoSearchA, SWT.NORMAL);
		add(txtSecondLast);

		lbRangeDate = new Label(compoSearchA, SWT.RIGHT);
		addHeader(lbRangeDate);

		lbFrom = new Label(compoSearchA, SWT.RIGHT);
		add(lbFrom);
		dateTime = new DateTime(compoSearchA, SWT.BORDER | SWT.DROP_DOWN);
		add(dateTime);

		cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToBeforeOneMonth(dateTime);
		
		lbUp = new Label(compoSearchA, SWT.RIGHT);
		add(lbUp);
		dateTime1 = new DateTime(compoSearchA, SWT.BORDER | SWT.DROP_DOWN);
		add(dateTime1);

		br();

		lbCI = new Label(compoSearchA, SWT.RIGHT);
		add(lbCI);
		textCI = new Text(compoSearchA, SWT.NORMAL);
		add(textCI);

		// ----------------Buttons---------------------------------------
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

		btnNewSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comboRoom.select(0);
				txtUserCode.setText("");
				comboUserType.select(0);
				comboFaculty.select(0);
				comboCareer.select(0);
				comboState.select(0);
				txtFirstName.setText("");
				txtSecondName.setText("");
				txtFirstLast.setText("");
				txtSecondLast.setText("");
				textCI.setText("");
				lbCoincidenceList.setVisible(false);

				tabla.destroyEditableArea();
				tabla.clearRows();
				tabla.setVisible(false);

				cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToBeforeOneMonth(dateTime);
				cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToToday(dateTime1);
			}
		});    

		link.addListener(SWT.Selection, new EventConsultObjectLoan(compoSearchA, link, busquedaB, this));

		btnConsult.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings({ "deprecation" })
			@Override
			public void widgetSelected(SelectionEvent e) {

				searchC.clear();
				tabla.clearRows();
				tabla.destroyEditableArea();
				lbCoincidenceList.setVisible(true);
				tabla.setVisible(true);

				if (compoSearchA.getVisible() == false) {
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

				loan_user_code = (txtUserCode.getText().length() > 0 ? txtUserCode.getText().replaceAll(" +", " ").trim() : null);
				loan_fistname = (txtFirstName.getText().length() > 0 ? txtFirstName.getText().replaceAll(" +", " ").trim() : null);
				loan_secondname = (txtSecondName.getText().length() > 0 ? txtSecondName.getText().replaceAll(" +", " ").trim() : null);
				loan_fistLast = (txtFirstLast.getText().length() > 0 ? txtFirstLast.getText().replaceAll(" +", " ").trim() : null);
				loan_secondLast = (txtSecondLast.getText().length() > 0 ? txtSecondLast.getText().replaceAll(" +", " ").trim() : null);
				loan_Dni = (textCI.getText().length() > 0 ? textCI.getText().replaceAll(" +", " ").trim() : null);

				UiUtils.get().addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE), txtUserCode.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_NAME), txtFirstName.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_NAME), txtSecondName.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_LAST), txtFirstLast.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_LAST), txtSecondLast.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION), textCI.getText());

				if (UiUtils.getSelected(comboRoom) == null) {
					loan_user_Romm = null;
				} else {
					loan_user_Romm = (Room) UiUtils.getSelected(comboRoom);
					searchC.add(lbRoom.getText());
					searchC.add(comboRoom.getText());
				}

				if (UiUtils.getSelected(comboUserType) == null) {
					loan_user_type_id = null;
				} else {
					loan_user_type_id = (Nomenclator) UiUtils.getSelected(comboUserType);
					searchC.add(lbUserType.getText());
					searchC.add(comboUserType.getText());
				}   

				if (UiUtils.getSelected(comboFaculty) == null) {
					faculty = null;
				} else {
					faculty = (Nomenclator) UiUtils.getSelected(comboFaculty);
					searchC.add(lbFaculty.getText());
					searchC.add(comboFaculty.getText());
				}
				if (UiUtils.getSelected(comboCareer) == null) {
					career = null;
				} else {
					career = (Nomenclator) UiUtils.getSelected(comboCareer);
					searchC.add(lbCareer.getText());
					searchC.add(comboCareer.getText());
				}

				if (UiUtils.getSelected(comboState) == null) {
					loan_user_state = null;
				} else {
					loan_user_state = (Nomenclator) UiUtils.getSelected(comboState);
					searchC.add(lbState.getText());
					searchC.add(comboState.getText());
				}
				orderByString = "person.firstName";
				direction = 1024;
				searchLoanUser(0, tabla.getPageSize());
				if (tabla.getRows().isEmpty()) {
					lbCoincidenceList.setVisible(false);
					tabla.setVisible(false);
					RetroalimentationUtils.showInformationMessage(compoButton1, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				} else 
					tabla.getPaginator().goToFirstPage();
				
				refresh();
			}
		});

		Label separator = new Label(compoButton, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		compoButton1 = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton1);
		compoButton1.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Composite resize1 = new Composite(compoButton1, 0);
		resize1.setVisible(false);
		FormDatas.attach(resize1).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 400);
		

		lbCoincidenceList = new Label(compoButton1, SWT.NONE);
		addHeader(lbCoincidenceList);
		lbCoincidenceList.setVisible(false);

		// -----------------Tabla--------------------------------------

		tabla = new CRUDTreeTable(compoButton1, SWT.NONE);
		add(tabla);
		tabla.setVisible(false);
		tabla.setEntityClass(LoanUser.class);
		tabla.setWatch(true, new ViewAreaUserLoan(controller));
		tabla.setUpdate(true, new EditorAreaUserLoan(controller));
		tabla.setVisualEntityManager(new MessageVisualEntityManager(tabla));
		
		tabla.addListener(SWT.Resize, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				refresh();				
			}
		});
		
		/**
		 * Export list loan object to PDF and Spread sheet
		 */

		CRUDTreeTableUtils.configReports(tabla, MessageUtil.unescape(AbosMessages.get().NAME_CONSULT_LOAN_USER), searchC);

		TreeTableColumn columns[] = { new TreeTableColumn(25, 0, "fullName"), new TreeTableColumn(25, 1, "getLoanUserCode"), new TreeTableColumn(25, 2, "getLoanUserType.getNomenclatorName"), new TreeTableColumn(25, 3, "getLoanUserState.getNomenclatorName") };

		tabla.createTable(columns);

		tabla.setPageSize(10);
		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
	
				searchLoanUser(event.currentPage - 1, event.pageSize);
			}
		});

		tabla.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {
			
				user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
			
				if (column.getIndex() == 5) {
					if (user.getPerson() == null ) {			
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

	private void searchLoanUser(int page, int size) {
		tabla.clearRows();
		Page<LoanUser> listLoanUser = ((AllManagementLoanUserViewController) controller)
				.getManageLoanUser().findLoanUserConsult(loan_user_code,
						loan_user_Romm, loan_user_type_id, loan_user_state,
						faculty, career, loan_fistname, loan_secondname,
						loan_fistLast, loan_secondLast, dateRegister,
						endDateRegister, loan_Dni, library, page, size, direction,
						orderByString);
		tabla.setTotalElements((int) listLoanUser.getTotalElements());
		if ((int) listLoanUser.getTotalElements() > 0) {
			tabla.setRows(listLoanUser.getContent());
			tabla.refresh();
		}

	}

	@Override
	public String getID() {
		return "consultLoanUserID";
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		lbConsultLoanUser.setText(MessageUtil.unescape(AbosMessages.get().NAME_CONSULT_LOAN_USER));
		lbRangeDate.setText(AbosMessages.get().LABEL_DATE_OF_REGISTRATION_RANGE);
		lbUserLoanData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA));
		lbSearchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		lbRoom.setText(AbosMessages.get().LABEL_ROOM);
		lbUserCode.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		lbUserType.setText(AbosMessages.get().LABEL_TYPE_OF_USER);
		lbState.setText(AbosMessages.get().LABEL_STATE);
		lbFirstName.setText(AbosMessages.get().LABEL_FIRST_NAME);
		lbSecondName.setText(AbosMessages.get().LABEL_SECOND_NAME);
		lbFirstLast.setText(AbosMessages.get().LABEL_FIRST_LAST);
		lbSecondLast.setText(AbosMessages.get().LABEL_SECOND_LAST);
		lbPersonData.setText(AbosMessages.get().LABEL_DETAILS_OF_THE_PERSON);
		lbFrom.setText(AbosMessages.get().LABEL_FROM);
		lbUp.setText(AbosMessages.get().LABEL_UP);
		link.setText("<a>" + MessageUtil.unescape(AbosMessages.get().LABEL_ADVANCED_SEARCH) + "</a>");
		btnConsult.setText(AbosMessages.get().BUTTON_CONSULT);
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		lbCoincidenceList.setText(AbosMessages.get().LABEL_COINCIDENCE_LIST);
		lbCI.setText(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		lbFaculty.setText(AbosMessages.get().LABEL_FACULTY);
		lbCareer.setText(AbosMessages.get().LABEL_SPECIALTY);

		initialize(comboFaculty, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.FACULTY));
		initialize(comboCareer, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.SPECIALITY));

		tabla.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		tabla.setColumnHeaders(Arrays.asList(AbosMessages.get().TABLE_NAME_AND_LAST_NAME, MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE), AbosMessages.get().LABEL_TYPE_OF_USER, AbosMessages.get().LABEL_STATE));
		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);

		tabla.l10n();

		initialize(comboUserType, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOANUSER_TYPE));
		initialize(comboState, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOANUSER_STATE));
		initialize(comboRoom, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findRoomByLibrary(library.getLibraryID()));
    
		refresh();
	}

	@Override
	public void setViewController(ViewController controller) {
		this.controller = controller;
	}

	public CRUDTreeTable getTabla() {
		return tabla;
	}
}
