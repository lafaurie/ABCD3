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
import cu.uci.abcd.circulation.ui.listener.EventConsultReservation;
import cu.uci.abcd.circulation.ui.model.EditorAreaConsultSanction;
import cu.uci.abcd.circulation.ui.model.ViewAreaPenaltyConsult;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
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
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.IActionDenied;
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConsultSanction extends ContributorPage implements Contributor {

	private Label lbConsultPenalty;
	private Label lbSearchCriteria;
	private Label lbSanctionsType;
	private Label lbState;
	private Label lbUserType;
	private Label lbUserCode;
	private Label lbDataSanction;
	private Label lbFrom;
	private Label lbUp;
	private Label lbUserDataAsiciatedLoan;
	private Label lbFirstName;
	private Label lbSecondName;
	private Label lbFirstLast;
	private Label lbSecondLast;
	private Label lbObjectDateAsociatedLoan;
	private Label lbTitle;
	private Label lbAuthor;
	private Label lbControlNumber;
	private Label lbCoincidenceList;
	private Label lbRangeDate;
	private DateTime dateTime;
	private DateTime dateTime1;
	private Link link;
	private Button btnObjectRelatedLoan;
	private Button btnConsult;
	private Button btnNewSearch;
	private CRUDTreeTable tabla;
	private Combo comboUserType;
	private Combo comboSactionType;
	private Combo comboState;
	private Text txtUserCode;
	private Text txtFirstName;
	private Text txtSecondName;
	private Text txtFirstLast;
	private Text txtSecondLast;
	private Text txtTitle;
	private Text txtAuthor;
	private Text txtControlNumber;

	private List<String> searchC = new ArrayList<>();

	private String loan_user_code = null;
	private Nomenclator penalty_type = null;
	private Nomenclator penalty_state = null;
	private Nomenclator loan_user_type_id = null;
	private Date dateRegister = null;
	private Date endDateRegister = null;
	private String first_Name = null;
	private String second_Name = null;
	private String first_Surname = null;
	private String second_Surname = null;
	private String title = null;
	private String author = null;
	private String control_number = null;

	private Composite compoLoanUserAdvanced;
	private Composite compoPenalty;
	private Composite compoPenaltyAdvanced;
	private Composite compoLoanObject;
	private Composite compoLoanObjectAdvanced;

	private String orderByString = "loanUser.person.firstName";
	private int direction = 1024;

	private Composite compoButton;
	private Composite compoButton1;
	private Library library;
	private User user;
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().NAME_UI_CONSULT_PENALTY);
	}

	@Override
	public String getID() {
		return "consultPenaltyID";
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("serial")
	@Override
	// FIXME METODO COMPLEJO
	public Control createUIControl(Composite parent) {
		
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
   
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);
	
		// ----------------Compo
		// Penalty---------------------------------------------

		compoPenalty = new Composite(parent, SWT.NORMAL);
		compoPenalty.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoPenalty);

		lbConsultPenalty = new Label(compoPenalty, SWT.NONE);
		addHeader(lbConsultPenalty);

		Label separatorHeader = new Label(compoPenalty,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separatorHeader);
	
		lbSearchCriteria = new Label(compoPenalty, SWT.NONE);
		addHeader(lbSearchCriteria);

		lbDataSanction = new Label(compoPenalty, SWT.NONE);
		addHeader(lbDataSanction);

		lbSanctionsType = new Label(compoPenalty, SWT.NONE);
		add(lbSanctionsType);
		comboSactionType = new Combo(compoPenalty, SWT.READ_ONLY);
		add(comboSactionType);

		lbState = new Label(compoPenalty, SWT.NONE);
		add(lbState);
		comboState = new Combo(compoPenalty, SWT.READ_ONLY);
		add(comboState);

		compoPenaltyAdvanced = new Composite(parent, SWT.NORMAL);
		compoPenaltyAdvanced.setVisible(false);
		compoPenaltyAdvanced.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoPenaltyAdvanced);

		lbRangeDate = new Label(compoPenaltyAdvanced, SWT.RIGHT);
		addHeader(lbRangeDate);

		lbFrom = new Label(compoPenaltyAdvanced, SWT.NONE);
		add(lbFrom);
		dateTime = new DateTime(compoPenaltyAdvanced, SWT.BORDER | SWT.DROP_DOWN);
		add(dateTime);

		cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToBeforeOneMonth(dateTime);
		
	
		lbUp = new Label(compoPenaltyAdvanced, SWT.NONE);
		add(lbUp);
		dateTime1 = new DateTime(compoPenaltyAdvanced, SWT.BORDER | SWT.DROP_DOWN);
		add(dateTime1);

		// ----------------Compo
		// LoanUser-------------------------------------------------------
		Composite compoLoanUser = new Composite(parent, SWT.NORMAL);
		compoLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoLoanUser);

		lbUserDataAsiciatedLoan = new Label(compoLoanUser, SWT.NONE);
		addHeader(lbUserDataAsiciatedLoan);

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

		compoLoanObject = new Composite(parent, SWT.NORMAL);
		compoLoanObject.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoLoanObject);

		lbObjectDateAsociatedLoan = new Label(compoLoanObject, SWT.NONE);
		addHeader(lbObjectDateAsociatedLoan);

		br();
		btnObjectRelatedLoan = new Button(compoLoanObject, SWT.CHECK);
		add(btnObjectRelatedLoan);

		compoLoanObjectAdvanced = new Composite(parent, SWT.NORMAL);
		compoLoanObjectAdvanced.setVisible(false);
		compoLoanObjectAdvanced.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(compoLoanObjectAdvanced);

		lbTitle = new Label(compoLoanObjectAdvanced, SWT.NONE);
		add(lbTitle);
		txtTitle = new Text(compoLoanObjectAdvanced, SWT.NORMAL);
		add(txtTitle);

		lbAuthor = new Label(compoLoanObjectAdvanced, SWT.NONE);
		add(lbAuthor);
		txtAuthor = new Text(compoLoanObjectAdvanced, SWT.NORMAL);
		add(txtAuthor);

		br();

		lbControlNumber = new Label(compoLoanObjectAdvanced, SWT.NONE);
		add(lbControlNumber);
		txtControlNumber = new Text(compoLoanObjectAdvanced, SWT.NORMAL);
		add(txtControlNumber);

		btnObjectRelatedLoan.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnObjectRelatedLoan.getSelection() == false) {
					compoLoanObjectAdvanced.setVisible(false);
					insertComposite(compoLoanObjectAdvanced, compoLoanObject);
				
					compoLoanObjectAdvanced.getShell().layout(true, true);
					compoLoanObjectAdvanced.getShell().redraw();
					compoLoanObjectAdvanced.getShell().update();
					
					refresh();

				}
				else					
				{
				compoLoanObjectAdvanced.setVisible(true);
				insertComposite(compoLoanObjectAdvanced, compoLoanObject);
			
				compoLoanObjectAdvanced.getShell().layout(true, true);
				compoLoanObjectAdvanced.getShell().redraw();
				compoLoanObjectAdvanced.getShell().update();
		
				refresh();
			}
				}
		});

		// -------------Button-------------------------------------------------

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

				txtUserCode.setText("");
				txtFirstLast.setText("");
				txtFirstName.setText("");
				txtSecondLast.setText("");
				txtSecondName.setText("");
				txtTitle.setText("");
				txtAuthor.setText("");
				txtControlNumber.setText("");

				comboSactionType.select(0);
				comboState.select(0);
				comboUserType.select(0);

				tabla.clearRows();
				lbCoincidenceList.setVisible(false);
				tabla.setVisible(false);
				tabla.destroyEditableArea();

				cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToBeforeOneMonth(dateTime);
				cu.uci.abcd.circulation.ui.auxiliary.Auxiliary.goDateTimeToToday(dateTime1);
			}
		});

		btnConsult.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// FIXME METODO COMPLEJO
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchC.clear();

				tabla.clearRows();
				lbCoincidenceList.setVisible(true);
				tabla.setVisible(true);
				tabla.destroyEditableArea();

				if (compoPenaltyAdvanced.getVisible() == false) {
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
				author = txtAuthor.getText().length() > 0 ? txtAuthor.getText().replaceAll(" +", " ").trim() : null;
				control_number = txtControlNumber.getText().length() > 0 ? txtControlNumber.getText().replaceAll(" +", " ").trim() : null;
				loan_user_code = (txtUserCode.getText().length() > 0 ? txtUserCode.getText().replaceAll(" +", " ").trim() : null);
				first_Name = (txtFirstName.getText().length() > 0 ? txtFirstName.getText().replaceAll(" +", " ").trim() : null);
				second_Name = (txtSecondName.getText().length() > 0 ? txtSecondName.getText().replaceAll(" +", " ").trim() : null);
				first_Surname = (txtFirstLast.getText().length() > 0 ? txtFirstLast.getText().replaceAll(" +", " ").trim() : null);
				second_Surname = (txtSecondLast.getText().length() > 0 ? txtSecondLast.getText().replaceAll(" +", " ").trim() : null);

				UiUtils.get().addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE), txtUserCode.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_TITLE), txtTitle.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR), txtAuthor.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_CONTROL_NUMBER), txtControlNumber.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_NAME), txtFirstName.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_NAME), txtSecondName.getText())
						.addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_LAST), txtFirstLast.getText()).addSearchCriteria(searchC, MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_LAST), txtSecondLast.getText());

				if (UiUtils.getSelected(comboUserType) == null) {
					loan_user_type_id = null;
				} else {
					loan_user_type_id = (Nomenclator) UiUtils.getSelected(comboUserType);
					searchC.add(lbUserType.getText());
					searchC.add(comboUserType.getText());
				}

				if (UiUtils.getSelected(comboSactionType) == null) {
					penalty_type = null;
				} else {
					penalty_type = (Nomenclator) UiUtils.getSelected(comboSactionType);
					searchC.add(lbSanctionsType.getText());
					searchC.add(comboSactionType.getText());
				}

				if (UiUtils.getSelected(comboState) == null) {
					penalty_state = null;
				} else {
					penalty_state = (Nomenclator) UiUtils.getSelected(comboState);
					searchC.add(lbState.getText());
					searchC.add(comboState.getText());
				}

				orderByString = "loanUser.person.firstName";
				direction = 1024;
				searchPenalty(0, tabla.getPageSize());

				if (tabla.getRows().isEmpty()) {
					lbCoincidenceList.setVisible(false);
					tabla.setVisible(false);
					RetroalimentationUtils.showInformationMessage(compoButton1, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				} else
					tabla.getPaginator().goToFirstPage();
			}
		});

		link.addListener(SWT.Selection, new EventConsultReservation(compoPenalty, compoPenaltyAdvanced, compoLoanUser, compoLoanUserAdvanced, link, this));

		Label separator = new Label(compoButton, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		compoButton1 = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton1);
		compoButton1.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbCoincidenceList = new Label(compoButton1, SWT.NONE);
		lbCoincidenceList.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		addHeader(lbCoincidenceList);
		lbCoincidenceList.setVisible(false);

		// -----------------Tabla--------------------------------------

		tabla = new CRUDTreeTable(compoButton1, SWT.NONE);
		add(tabla);
		tabla.setVisible(false);
		tabla.setEntityClass(Penalty.class);
		tabla.setWatch(true, new ViewAreaPenaltyConsult(controller, tabla, this));
		tabla.setUpdate(true, new EditorAreaConsultSanction(controller));
		tabla.setDelete(true);
		tabla.setPageSize(10);
		tabla.setVisualEntityManager(new MessageVisualEntityManager(tabla));

		tabla.addListener(SWT.Resize, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		CRUDTreeTableUtils.configReports(tabla, MessageUtil.unescape(AbosMessages.get().NAME_CONSULT_PENALTY), searchC);
		CRUDTreeTableUtils.configUpdate(tabla);

		CRUDTreeTableUtils.configRemove(tabla, new IActionCommand() {
			@Override
			public void execute(TreeColumnEvent event) {
				Penalty penalty = (Penalty) event.entity.getRow();
				((AllManagementLoanUserViewController) controller).getManagePenalty().deletePenalty(penalty.getPenaltyID());
			}
		});

		TreeTableColumn columns[] = {
				new TreeTableColumn(24, 0, "getLoanUser.fullName"),
				new TreeTableColumn(17, 1, "getPenaltyType.getNomenclatorName"),
				new TreeTableColumn(17, 2, "getPenaltyState.getNomenclatorName"),
				new TreeTableColumn(14, 3, "getEffectiveDate"),
				new TreeTableColumn(14, 4, "getExpirationDate"),
				new TreeTableColumn(10, 5, "getDay") };
		tabla.createTable(columns);

		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
		
				searchPenalty(event.currentPage - 1, event.pageSize);
			}
		});

		tabla.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {
				user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
						
				Penalty penalty = (Penalty) row;
				if (column.getIndex() == 7) {
						
					if (penalty.getPenaltyState().getNomenclatorID().equals(Nomenclator.PENALTY_STATE_PAID) || penalty.getPenaltyState().getNomenclatorID().equals(Nomenclator.PENALTY_STATE_INACTIVE)) {
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
				if (column.getIndex() == 8) {
						
					if (penalty.getPenaltyState().getNomenclatorID().equals(Nomenclator.PENALTY_STATE_PAID) || penalty.getPenaltyState().getNomenclatorID().equals(Nomenclator.PENALTY_STATE_INACTIVE)) {
						return true;
					}
					else if (user.getPerson() == null) {			
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
		// ----------------------------------------------------------------------

		l10n();
		return parent;
	}

	@Override
	public void l10n() {
		refresh();
		lbConsultPenalty.setText(MessageUtil.unescape(AbosMessages.get().NAME_CONSULT_PENALTY));
		lbRangeDate.setText(AbosMessages.get().LABEL_DATE_OF_REGISTRATION_RANGE);
		lbSearchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		lbSanctionsType.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_SANCTION));
		lbState.setText(AbosMessages.get().LABEL_STATE);
		lbUserType.setText(AbosMessages.get().LABEL_TYPE_OF_USER);
		lbUserCode.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		lbDataSanction.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATA_SANCTION));
		lbFrom.setText(AbosMessages.get().LABEL_FROM);
		lbUp.setText(AbosMessages.get().LABEL_UP);
		lbUserDataAsiciatedLoan.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_DATE_ASOCCIATED_LOAN));
		lbFirstName.setText(AbosMessages.get().LABEL_FIRST_NAME);
		lbSecondName.setText(AbosMessages.get().LABEL_SECOND_NAME);
		lbFirstLast.setText(AbosMessages.get().LABEL_FIRST_LAST);
		lbSecondLast.setText(AbosMessages.get().LABEL_SECOND_LAST);
		btnObjectRelatedLoan.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_RELATED_LOAN));
		lbObjectDateAsociatedLoan.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBJECT_DATE_ASOCCIATED_LOAN));
		lbTitle.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		lbAuthor.setText(AbosMessages.get().LABEL_AUTHOR);
		lbControlNumber.setText(AbosMessages.get().LABEL_CONTROL_NUMBER);
		link.setText("<a>" + MessageUtil.unescape(AbosMessages.get().LABEL_ADVANCED_SEARCH) + "</a>");
		btnConsult.setText(AbosMessages.get().BUTTON_CONSULT);
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		lbCoincidenceList.setText(AbosMessages.get().LABEL_COINCIDENCE_LIST);

		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);
		
		tabla.setColumnHeaders(Arrays.asList(AbosMessages.get().TABLE_NAME_AND_LAST_NAME, MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_SANCTION), AbosMessages.get().LABEL_STATE, AbosMessages.get().LABEL_FROM, AbosMessages.get().LABEL_UP,
				MessageUtil.unescape(AbosMessages.get().LABEL_DAYS_LATE)));

		tabla.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		tabla.l10n();
		initialize(comboUserType, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.LOANUSER_TYPE));
		initialize(comboSactionType, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.PENALTY_TYPE));
		initialize(comboState, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.PENALTY_STATE));

		refresh();

	}

	@Override
	public void setViewController(ViewController controller) {
		super.setViewController(controller);
	}

	public void searchPenalty(int page, int size) {
		tabla.clearRows();
		Page<Penalty> listPenalty = ((AllManagementLoanUserViewController) controller)
				.getManagePenalty().findAllPenaltyConsult(penalty_type,
						penalty_state, loan_user_type_id, loan_user_code,
						first_Name, second_Name, first_Surname, second_Surname,
						dateRegister, endDateRegister, title, author,
						control_number, library,page, size, direction, orderByString);
		tabla.setTotalElements((int) listPenalty.getTotalElements());

		if ((int) listPenalty.getTotalElements() > 0) {
			tabla.setRows(listPenalty.getContent());
			tabla.refresh();
		}

	}

}
