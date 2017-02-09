package cu.uci.abcd.administration.library.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.IEnrollmentService;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.administration.library.ui.model.FormationCourseUpdateArea;
import cu.uci.abcd.administration.library.ui.model.FormationCourseViewArea;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Enrollment;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ConsultFormationCourse extends ContributorPage implements Contributor {
	private Label consultFormationCourse;
	private Label searchCriteriaLabel;
	private Label courseNameLabel;
	private Text courseNameText;
	private Label rangeDateCourseLabel;
	private Label fromDateLabel;
	private DateTime fromDateTime;
	private Label toDateLabel;
	private DateTime toDateTime;
	private Button newSearchButton;
	private Button consultButton;
	private Label formationCourseList;
	private SecurityCRUDTreeTable formationCourseTable;
	private Library library;
	private String orderByString = "courseName";
	int direction = 1024;
	private String courseNameConsult = null;
	private Date fromDateConsult = null;
	private Date toDateConsult = null;
	private int clasificationConsult = 0;
	private Room roomConsult = null;
	private Person proffessorConsult = null;
	private int addressedToConsult = -1;
	
	Composite right;
	private ValidatorUtils validator;
	private Label clasificationLabel;
	private Combo clasificationCombo;
	private Label professorLabel;
	private Combo professorCombo; 
	private Label roomLabel;
	private Combo roomCombo; 
	
	private Label adreesedToLabel;
	private Combo adreesedToCombo; 
	
	
	
	private List<String> searchCriteriaList = new ArrayList<>();
	
	private IEnrollmentService enrollmentService = ServiceProviderUtil.getService(IEnrollmentService.class);
	

	@Override
	public String contributorName() {
		return MessageUtil
				.unescape(AbosMessages.get().CONSULT_FORMATION_COURSE);
	}

	@Override
	public String getID() {
		return "consultFormationCourseID";
	}

	@Override
	public Control createUIControl(final Composite shell) {
		validator = new ValidatorUtils(new CustomControlDecoration());

		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Composite scroll = new Composite(shell,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		

		consultFormationCourse = new Label(shell, SWT.NONE);
		addHeader(consultFormationCourse);
		
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		
		searchCriteriaLabel = new Label(shell, SWT.NONE);
		addHeader(searchCriteriaLabel);

		courseNameLabel = new Label(shell, SWT.NONE);
		add(courseNameLabel);
		
		courseNameText = new Text(shell, SWT.NONE);
		add(courseNameText);
		//validator.applyValidator(courseNameText, 49);
		
		clasificationLabel = new Label(shell, SWT.NONE);
		add(clasificationLabel);
		
		clasificationCombo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		add(clasificationCombo);
		
		br();
		
		professorLabel = new Label(shell, SWT.NONE);
		add(professorLabel);
		
		professorCombo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		add(professorCombo);
		
		roomLabel = new Label(shell, SWT.NONE);
		add(roomLabel);
		
		roomCombo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		add(roomCombo);
		
		br();
		
		adreesedToLabel = new Label(shell, SWT.NONE);
		add(adreesedToLabel);
		
		adreesedToCombo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		add(adreesedToCombo);
		
		br();
		
		rangeDateCourseLabel = new Label(shell, SWT.NONE);
		add(rangeDateCourseLabel);
		br();
		
		fromDateLabel = new Label(shell, SWT.NONE);
		add(fromDateLabel);
		
		fromDateTime = new DateTime(shell, SWT.BORDER | SWT.DROP_DOWN);
		add(fromDateTime);
		//FIXME BORRAR CODIGO COMENTARIADO
		//arreglar, invento
		java.util.Date d = new java.util.Date();
		Date a = new Date(d.getTime() - 899999999 );
		Date b = new Date(a.getTime() - 899999999 );
		Date c = new Date(b.getTime() - 899999999 );
		
		int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy")
				.format(c));
		int fromMonth = Integer.parseInt(new SimpleDateFormat("MM")
				.format(c));
		int fromDay = Integer.parseInt(new SimpleDateFormat("dd")
				.format(c));
		
		fromDateTime.setDate(fromYear, fromMonth - 1, fromDay);
		
		
		
		toDateLabel = new Label(shell, SWT.NONE);
		add(toDateLabel);
		
		toDateTime = new DateTime(shell, SWT.BORDER | SWT.DROP_DOWN);
		add(toDateTime);
		
		
		
		
		newSearchButton = new Button(shell, SWT.NONE);
		add(newSearchButton);
		
		newSearchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5179607102055540891L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				searchCriteriaList.clear();
				formationCourseTable.destroyEditableArea();
				courseNameText.setText("");
				courseNameText.setFocus();
				//FIXME BORRAR CODIGO COMENTARIADO
				//arreglar, invento
				/*
				java.util.Date d = new java.util.Date();
				Date a = new Date(d.getTime() - 899999999 );
				Date b = new Date(a.getTime() - 899999999 );
				Date c = new Date(b.getTime() - 899999999 );
				
				int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy")
						.format(c));
				int fromMonth = Integer.parseInt(new SimpleDateFormat("MM")
						.format(c));
				int fromDay = Integer.parseInt(new SimpleDateFormat("dd")
						.format(c));
				
				fromDateTime.setDate(fromYear, fromMonth - 1, fromDay);
				
				//FIXME BORRAR CODIGO COMENTARIADO
				java.util.Date utilDate = new java.util.Date();
				int year = Integer.parseInt(new SimpleDateFormat("yyyy")
						.format(utilDate));
				int month = Integer.parseInt(new SimpleDateFormat("MM")
						.format(utilDate));
				int day = Integer.parseInt(new SimpleDateFormat("dd")
						.format(utilDate));
				//fromDateTime.setDate(year, month - 1, day);
				toDateTime.setDate(year, month - 1, day);
				*/
				professorCombo.select(0);
				adreesedToCombo.select(0);
				clasificationCombo.select(0);
				roomCombo.select(0);
				
				toDateTime.setBackground(null);
				fromDateTime.setBackground(null);
				
				Auxiliary.goDateTimeToBeforeOneMonth(fromDateTime);
				Auxiliary.goDateTimeToToday(toDateTime);
				Auxiliary.showLabelAndTable(formationCourseList, formationCourseTable, false);
				
			}
		});
		consultButton = new Button(shell, SWT.NONE);
		add(consultButton);
		br();
		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -1055528852127544930L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				formationCourseTable.destroyEditableArea();
				if(Auxiliary.finishDate_MoreThanInitDate_And_LessOrEqualThanToday(fromDateTime, toDateTime)){
				
				formationCourseTable.destroyEditableArea();
				formationCourseTable.clearRows();
				//searchCriteriaList.clear();
				courseNameConsult = courseNameText.getText();
				clasificationConsult = clasificationCombo.getSelectionIndex();
				if (UiUtils.getSelected(roomCombo) == null) {
					roomConsult = null;
				} else {
					roomConsult = (Room) UiUtils.getSelected(roomCombo);
				}
				
				if (UiUtils.getSelected(professorCombo) == null) {
					proffessorConsult = null;
				} else {
					proffessorConsult = (Person) UiUtils.getSelected(professorCombo);
				}
				
				
				
				if (UiUtils.getSelected(adreesedToCombo) == null) {
					addressedToConsult = -1;
				} else {
					Nomenclator pregradeStudent = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.PREGRADE_STUDENT);
					Nomenclator postgradeStudent = ((LibraryViewController) controller)
							.getAllManagementLibraryViewController().getLibraryService()
							.findNomenclatorById(Nomenclator.POSTGRADE_STUDENT);

					Nomenclator professor = ((LibraryViewController) controller)
							.getAllManagementLibraryViewController().getLibraryService()
							.findNomenclatorById(Nomenclator.PROFESSOR);

					Nomenclator investigator = ((LibraryViewController) controller)
							.getAllManagementLibraryViewController().getLibraryService()
							.findNomenclatorById(Nomenclator.INVESTIGATOR);

					Nomenclator bibliotecario = ((LibraryViewController) controller)
							.getAllManagementLibraryViewController().getLibraryService()
							.findNomenclatorById(Nomenclator.BIBLIOTECARIO);

					@SuppressWarnings("unused")
					Nomenclator other = ((LibraryViewController) controller)
							.getAllManagementLibraryViewController().getLibraryService()
							.findNomenclatorById(Nomenclator.OTHER);
					
					if(((Nomenclator) UiUtils.getSelected(adreesedToCombo)).getNomenclatorID()== pregradeStudent.getNomenclatorID()){
								addressedToConsult = 1;
							}else{
								if(((Nomenclator) UiUtils.getSelected(adreesedToCombo)).getNomenclatorID()== postgradeStudent.getNomenclatorID()){
									addressedToConsult = 2;
								}else{
									if(((Nomenclator) UiUtils.getSelected(adreesedToCombo)).getNomenclatorID()== professor.getNomenclatorID()){
										addressedToConsult = 3;
									}else{
										if(((Nomenclator) UiUtils.getSelected(adreesedToCombo)).getNomenclatorID()== bibliotecario.getNomenclatorID()){
											addressedToConsult = 4;
										}else{
											if(((Nomenclator) UiUtils.getSelected(adreesedToCombo)).getNomenclatorID()== investigator.getNomenclatorID()){
												addressedToConsult = 5;
											}else{
												addressedToConsult = 6;
											}
										}
									}
								}
							}
							
				}
				
				int fromYear = fromDateTime.getYear() - 1900;
				int fromMonth = fromDateTime.getMonth();
				int fromDay = fromDateTime.getDay();
				@SuppressWarnings("deprecation")
				Date fromDate = new Date(fromYear, fromMonth, fromDay);
				fromDateConsult = fromDate;
				int toYear = toDateTime.getYear() - 1900;
				int toMonth = toDateTime.getMonth();
				int toDay = toDateTime.getDay();
				@SuppressWarnings("deprecation")
				Date toDate = new Date(toYear, toMonth, toDay);
				toDateConsult = toDate;
				orderByString = "courseName";
				direction = 1024;
				Auxiliary.showLabelAndTable(formationCourseList, formationCourseTable, true);
				//searchFormationCourses(0, formationCourseTable.getPaginator()
						//.getPageSize());
				formationCourseTable.getPaginator().goToFirstPage();
				
				if (formationCourseTable.getRows().isEmpty()) {
					RetroalimentationUtils
							.showInformationMessage(
									right,
									cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					formationCourseList.setVisible(false);
					formationCourseTable.setVisible(false);
				}else{
					formationCourseList.setVisible(true);
					formationCourseTable.setVisible(true);
				}
				
			}else{
				formationCourseList.setVisible(false);
				formationCourseTable.clearRows();
				formationCourseTable.setVisible(false);
			}

				searchCriteriaList.clear();
				UiUtils.get().addSearchCriteria(searchCriteriaList, courseNameLabel.getText(), courseNameText.getText()).
				addSearchCriteria(searchCriteriaList, clasificationLabel.getText(), clasificationCombo).
				addSearchCriteria(searchCriteriaList, professorLabel.getText(), professorCombo).
				addSearchCriteria(searchCriteriaList, roomLabel.getText(), roomCombo).
				addSearchCriteria(searchCriteriaList, adreesedToLabel.getText(), adreesedToCombo).
				addSearchCriteria(searchCriteriaList, fromDateLabel.getText(), fromDateTime).
				addSearchCriteria(searchCriteriaList, toDateLabel.getText(), toDateTime);
				
				//if (!(courseNameConsult.equals(""))) {
					//searchCriteriaList.add(MessageUtil.unescape(AbosMessages
					//		.get().LABEL_COURSE_NAME)
					//		+ ": "
					//		+ courseNameConsult);
				//}
				//courseNameLabel
				//.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_NAME));
				
				
			}

		});
		Label separador = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separador);
		
		right =  new Composite(shell, SWT.NONE);
		addComposite(right);
		right.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(right).atTopTo(separador, 10).atRight(15).withWidth(300).withHeight(5);

		
		formationCourseList = new Label(shell, SWT.NORMAL);
		addHeader(formationCourseList);
		//FIXME BORRAR CODIGO COMENTARIADO
		formationCourseTable = new SecurityCRUDTreeTable(shell, SWT.NONE);
		add(formationCourseTable);
		formationCourseTable.setEntityClass(FormationCourse.class);
		formationCourseTable.setSearch(false);
		formationCourseTable.setSaveAll(false);
		formationCourseTable.setWatch(true, new FormationCourseViewArea(
				controller));
		formationCourseTable.setUpdate(true, new FormationCourseUpdateArea(
				controller, formationCourseTable));
		//formationCourseTable.setDelete("deleteFormationCourseID");
		
		formationCourseTable.setDelete("deleteFormationCourseID");
		
		formationCourseTable.setVisible(true);
		formationCourseTable.setPageSize(10);
		
		formationCourseTable.addListener(SWT.Resize, new Listener() {
            private static final long serialVersionUID = 8817895862824622805L;
            @Override
            public void handleEvent(Event event) {
                    refresh();
            }
           });
		
		CRUDTreeTableUtils.configUpdate(formationCourseTable);
		CRUDTreeTableUtils.configReports(formationCourseTable, contributorName(),
				searchCriteriaList);
		
		formationCourseTable.addDeleteListener(new TreeColumnListener() {
			public void handleEvent(final TreeColumnEvent event) {
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA),
						new DialogCallback() {

							private static final long serialVersionUID = 8415736231132589115L;

							@Override
							public void dialogClosed(int returnCode) {
								if (returnCode == 0) {
									FormationCourse formationCourse = (FormationCourse) event.entity
											.getRow();
									Long idFormationCourse = formationCourse.getFormationCourseID();
									
									List<Enrollment> listEnrollemnt = enrollmentService.findByFormationCourse(idFormationCourse);
									for (Enrollment enrollment : listEnrollemnt) {
										enrollmentService.deleteEnrollment(enrollment.getEnrollmentID());
									}
									((LibraryViewController) controller)
											.deleteFormationCourseById(idFormationCourse);
									RetroalimentationUtils.showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_DELETE));
									//searchHorary();
									
									formationCourseTable.getPaginator().goToPage(formationCourseTable.getPaginator().getCurrentPage());
								}
							}
						});
			}
		});
/*
		CRUDTreeTableUtils.configRemove(formationCourseTable, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				formationCourseTable.destroyEditableArea();
				FormationCourse formationCourse = (FormationCourse) event.entity
						.getRow();
				Long idFormationCourse = formationCourse.getFormationCourseID();
				
				List<Enrollment> listEnrollemnt = enrollmentService.findByFormationCourse(idFormationCourse);
				for (Enrollment enrollment : listEnrollemnt) {
					enrollmentService.deleteEnrollment(enrollment.getEnrollmentID());
				}
				((LibraryViewController) controller)
						.deleteFormationCourseById(idFormationCourse);
				//formationCourseTable.getPaginator().goToFirstPage();
				//searchFormationCourses(0, formationCourseTable.getPaginator().getPageSize());
			}
		});
		*/
		TreeTableColumn columns[] = {
				new TreeTableColumn(28, 0, "getCourseName"),
				new TreeTableColumn(18, 1, "getCoursetype.getNomenclatorName"),
				new TreeTableColumn(23, 2, "getHourQuantity"),
				new TreeTableColumn(19, 3, "getStartDateToString") ,
		        new TreeTableColumn(12, 4, "getEnrollment") };
		formationCourseTable.createTable(columns);
		
		formationCourseTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					
					case 0:
						orderByString = "courseName";
						break;
					case 1:
						orderByString = "professor.firstName";
						break;
					case 2:
						orderByString = "startDate";
						break;
					case 3:
						orderByString = "endDate";
						break;
					}
				}
				searchFormationCourses(event.currentPage - 1, event.pageSize);
			}
		});
		Auxiliary.showLabelAndTable(formationCourseList, formationCourseTable, false);
		l10n();
		//consultButton.getShell().setDefaultButton(consultButton);
		return shell;
	}

	private void searchFormationCourses(int page, int size) {

		Page<FormationCourse> list = ((LibraryViewController) controller)
				.findFormationCourseByParams(library, courseNameConsult, clasificationConsult, roomConsult,
						addressedToConsult, proffessorConsult, fromDateConsult, toDateConsult, page, size, direction,
						orderByString);
		formationCourseTable.clearRows();
		formationCourseTable.setTotalElements((int) list.getTotalElements());
		formationCourseTable.setRows(list.getContent());
		formationCourseTable.refresh();
		
		
		
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		int positionClasification = clasificationCombo.getSelectionIndex();
		List<String> clasification = new Vector<>();
		clasification.add(MessageUtil.unescape(AbosMessages.get().INTERNAL));
		clasification.add(MessageUtil.unescape(AbosMessages.get().EXTERNAL));
		UiUtils.initialize(clasificationCombo, clasification);
		clasificationCombo.select(positionClasification);
		
		int positionAdreesedTo = adreesedToCombo.getSelectionIndex();
		initialize(
				adreesedToCombo,
				((LibraryViewController) controller)
						.findNomenclatorByCode(library.getLibraryID(), Nomenclator.ADDRESSED_TO_USER_TYPE));
		adreesedToCombo.select(positionAdreesedTo);
		
		
		adreesedToLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DIRECTED_TO));
		//FIXME BORRAR CODIGO COMENTARIADO
		Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		List<Person> listTemp = new Vector<>();
		List<FormationCourse> formationCList = ((LibraryViewController) controller)
				.getAllManagementLibraryViewController()
				.getFormationCourseService()
				.findFormationCourseByLibrary(library.getLibraryID());
		for (int i = 0; i < formationCList.size(); i++) {
			if(  ( formationCList.get(i).getProfessor()!=null )  &&  ( !listTemp.contains(formationCList.get(i).getProfessor()) ) ){
				listTemp.add(formationCList.get(i).getProfessor());
			}
		}
		
		int positionProfesor = professorCombo.getSelectionIndex();
		UiUtils.initialize(professorCombo, listTemp);
		professorCombo.select(positionProfesor);
		
		int positionRoom = roomCombo.getSelectionIndex();
		initialize(roomCombo, ((LibraryViewController) controller).getAllManagementLibraryViewController().getRoomService().findAll(library.getLibraryID()));
		roomCombo.select(positionRoom);
		//UiUtils.initialize(roomCombo, new Vector<>(library.getRooms()));
		
		
		
		consultFormationCourse.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CONSULT_FORMATION_COURSE));
		searchCriteriaLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		courseNameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_NAME));
		clasificationLabel.setText(MessageUtil.unescape(AbosMessages.get().CLASSIFICATION));
		
		
		professorLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PROFESSOR));
		roomLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ROOM));
		
		
		rangeDateCourseLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_DATE_RANGE));
		fromDateLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
		toDateLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TO));
		newSearchButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		consultButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		formationCourseList
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_MATCHES));
		formationCourseTable.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_NAME),
				MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_TYPE),
				MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_QUANTITY),
				MessageUtil.unescape(AbosMessages.get().LABEL_FROM),
				
				MessageUtil.unescape(AbosMessages.get().LABEL_ENROLLMENT)));
		formationCourseTable.setCancelButtonText(MessageUtil
				.unescape(AbosMessages.get().BUTTON_CANCEL));
		//
		
		formationCourseTable.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		formationCourseTable.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		
		formationCourseTable.l10n();
		searchCriteriaLabel.getParent().layout(true, true);
		searchCriteriaLabel.getParent().redraw();
		searchCriteriaLabel.getParent().update();
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}
	
	
	
}
