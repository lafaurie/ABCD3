package cu.uci.abcd.administration.library.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.IEnrollmentService;
import cu.uci.abcd.administration.library.communFragment.ViewFormationCourseFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.administration.library.ui.model.EnrollmentSaveArea;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Enrollment;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.Column;
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

public class RegisterFormationCourse extends ContributorPage implements Contributor {
	int flag;
	private Label registerFormationCourseLabel;

	private Library library;
	private String orderByStringPerson = "firstName";
	private Label courseFormationLabel;
	private Label courseNameLabel;
	private Text courseNameText;
	private Label hourQuantityLabel;
	private Text hourQuantityText;
	Composite msgProfessorNotFound;
	Label profesorData;
	List<Enrollment> entities = new ArrayList<>();

	public List<Enrollment> getTmp() {
		List<Enrollment> tmp = new ArrayList<>();
		for (int i = 0; i < tableEnrollment.getRows().size(); i++) {
			tmp.add((Enrollment) tableEnrollment.getRows().get(i).getRow());
		}
		return tmp;
	}

	public List<Enrollment> getEntities() {
		return entities;
	}

	public void setEntities(List<Enrollment> entities) {
		this.entities = entities;
	}

	ControlDecoration controlDecoration1;
	ControlDecoration controlDecoration2;

	public Text getWorkerQuantityText() {
		return workerQuantityText;
	}

	public void setWorkerQuantityText(Text workerQuantityText) {
		this.workerQuantityText = workerQuantityText;
	}

	public Text getExternalQuantityText() {
		return externalQuantityText;
	}

	public void setExternalQuantityText(Text externalQuantityText) {
		this.externalQuantityText = externalQuantityText;
	}

	private Text descriptionText;
	private DateTime fromDateTime;
	private DateTime toDateTime;
	private Button saveBtn;

	private Button cancelBtn;
	private Label descriptionLabel;
	private Label fromLabel;
	private Label toLabel;
	private Text workerQuantityText;
	private Label workerQuantityLabel;
	private Text externalQuantityText;
	private Label externalQuantityLabel;
	private Nomenclator pregradeStudent;
	private Nomenclator postgradeStudent;
	private Nomenclator professor;
	private Nomenclator investigator;
	private Nomenclator bibliotecario;
	private Nomenclator other;
	private Nomenclator carrera;
	private Nomenclator facultad;
	private Nomenclator departament;
	private Nomenclator investigationCenter;
	private Nomenclator studyCenter;
	private Nomenclator internalArea;
	private Nomenclator externalArea;
	private Nomenclator diplomadeProgram;
	private Nomenclator masterProgram;
	private EnrollmentSaveArea enrollmentSaveArea;
	private Combo coursetypeCombo;
	private Label coursetypeLabel;
	private Button imparted;
	private Label sugestLabel;
	private Text searchText;
	private Button searchButton;
	private CRUDTreeTable tablePerson;
	private CRUDTreeTable tableEnrollment;
	private int direction = 1024;
	private String orderByString = "firstName";
	private String searchTextConsult = null;
	private List<String> searchCriteriaList = new ArrayList<>();
	private IPersonService personService;
	private IEnrollmentService enrollmentService;
	private Button recived;
	private FormationCourse formationCourse;
	private Map<String, Control> mapControls = new HashMap<String, Control>();
	private Label enrollmentListLabel;
	// private Label personDataLabel;
	private Label birthdayLabel;
	private Label sexLabel;
	private Label fullNameLabel;
	private Button unassociate;
	private Label identificationLabel;
	private Composite personDataComposite;
	private Composite viewPersonDataComposite;
	private Composite searchPersonComposite;
	private Composite tablePersonComposite;
	Composite enrollmentComposite;
	private int heightTablePerson;
	private RegisterFormationCourse registerFormationCourse;
	private ViewFormationCourseFragment viewFormationCourseFragment;
	private Label fullName;
	private Label user;
	private Label birthday;
	private Label userLabel;
	private Label sex;
	private Label identification;
	private Label pictureLabel;
	private ValidatorUtils validator;
	private Group personGroup;
	private Person person;
	private Combo roomCombo;
	private Label roomLabel;

	public int getHeightTablePerson() {
		return heightTablePerson;
	}

	public void setHeightTablePerson(int heightTablePerson) {
		this.heightTablePerson = heightTablePerson;
	}

	public int getHeightPersonDataComposite() {
		return personDataComposite.getSize().y;
	}

	public int getHeightViewPersonDataComposite() {
		return viewPersonDataComposite.getSize().y;
	}

	public int getHeightSearchPersonComposite() {
		return searchPersonComposite.getSize().y;
	}

	public int getHeightTablePersonComposite() {
		return tablePersonComposite.getSize().y;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

	@Override
	public Control createUIControl(final Composite parent) {

		registerFormationCourse = this;
		formationCourse = null;

		Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		final ContributorService contributorService = getContributorService();
		this.personService = ServiceProviderUtil.getService(IPersonService.class);
		this.enrollmentService = ServiceProviderUtil.getService(IEnrollmentService.class);

		validator = new ValidatorUtils(new CustomControlDecoration());
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(parent);

		Composite scroll = new Composite(parent, SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(parent, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);

		registerFormationCourseLabel = new Label(parent, SWT.NONE);
		addHeader(registerFormationCourseLabel);

		Label separador = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separador);

		courseFormationLabel = new Label(parent, SWT.NONE);
		addHeader(courseFormationLabel);

		imparted = new Button(parent, SWT.RADIO);
		imparted.setSelection(true);
		add(imparted);

		recived = new Button(parent, SWT.RADIO);
		add(recived);

		recived.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 6724578212995896612L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				dissociate();
				recived();
				// saveBtn.getShell().setDefaultButton(saveBtn);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		imparted.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = -4186307840238390263L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				personDataComposite.setVisible(true);
				if (person == null) {
					ajustRezise(personDataComposite, getHeightSearchPersonComposite() + getHeightTablePersonComposite());
				} else {
					ajustRezise(personDataComposite, 177);
				}
				refresh(personDataComposite.getParent().getShell());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		br();

		courseNameLabel = new Label(parent, SWT.NONE);
		add(courseNameLabel);

		courseNameText = new Text(parent, SWT.NONE);
		add(courseNameText);

		validator.applyValidator(courseNameText, "courseNameText", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(courseNameText, "courseNameText1", DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);

		hourQuantityLabel = new Label(parent, SWT.NONE);
		add(hourQuantityLabel);

		hourQuantityText = new Text(parent, SWT.NONE);
		add(hourQuantityText);
		validator.applyValidator(hourQuantityText, "hourQuantityText", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(hourQuantityText, "hourQuantityNumber", DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		workerQuantityLabel = new Label(parent, SWT.NONE);
		add(workerQuantityLabel);

		workerQuantityText = new Text(parent, SWT.NONE);
		add(workerQuantityText);
		validator.applyValidator(workerQuantityText, "workerQuantityNumber", DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		coursetypeLabel = new Label(parent, SWT.NONE);
		add(coursetypeLabel);

		coursetypeCombo = new Combo(parent, SWT.READ_ONLY);
		add(coursetypeCombo);

		externalQuantityLabel = new Label(parent, SWT.NONE);
		add(externalQuantityLabel);

		externalQuantityText = new Text(parent, SWT.NONE);
		add(externalQuantityText);

		validator.applyValidator(externalQuantityText, "externalQuantityNumber1", DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		roomLabel = new Label(parent, SWT.NONE);
		add(roomLabel);

		roomCombo = new Combo(parent, SWT.READ_ONLY);
		add(roomCombo);

		fromLabel = new Label(parent, SWT.NONE);
		add(fromLabel);

		fromDateTime = new DateTime(parent, SWT.BORDER | SWT.DROP_DOWN);
		add(fromDateTime);

		validator.applyRangeDateValidator(fromDateTime, "fromDateTime1", DecoratorType.DATE_RANGE, -50, 0, 0, 1, 0, 0, true);

		toLabel = new Label(parent, SWT.NONE);
		add(toLabel);

		toDateTime = new DateTime(parent, SWT.BORDER | SWT.DROP_DOWN);
		add(toDateTime);

		validator.applyRangeDateValidator(toDateTime, "toDateTime1", DecoratorType.DATE_RANGE, -50, 0, 0, 1, 0, 0, true);

		br();

		descriptionLabel = new Label(parent, SWT.NONE);
		add(descriptionLabel);

		descriptionText = new Text(parent, SWT.NONE | SWT.WRAP | SWT.V_SCROLL);
		add(descriptionText);

		validator.applyValidator(descriptionText, 250);

		personDataComposite = new Composite(parent, SWT.NONE);
		personDataComposite.setLayout(new FormLayout());
		personDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personDataComposite).atTopTo(descriptionText).atLeft(15).atRight(15);

		viewPersonDataComposite = new Composite(personDataComposite, SWT.NONE);
		viewPersonDataComposite.setLayout(new FormLayout());
		viewPersonDataComposite.setVisible(false);
		viewPersonDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(viewPersonDataComposite).atTopTo(personDataComposite).withHeight(100);

		personGroup = new Group(viewPersonDataComposite, SWT.NONE);
		personGroup.setLayout(new FormLayout());
		personGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personGroup).atTopTo(viewPersonDataComposite, 15).atLeft(0);

		pictureLabel = new Label(personGroup, SWT.BORDER);
		FormDatas.attach(pictureLabel).atTopTo(personGroup, 10).atLeft(15);
		Image image = new Image(Display.getDefault(), AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/photo.png", false).getImageData().scaledTo(100, 100));
		pictureLabel.setImage(image);

		fullNameLabel = new Label(personGroup, SWT.NONE);
		fullNameLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(fullNameLabel).atTopTo(personGroup, 10).atLeftTo(pictureLabel, 15);
		fullName = new Label(personGroup, SWT.NONE);
		FormDatas.attach(fullName).atTopTo(personGroup, 10).atLeftTo(fullNameLabel, 5).atRight(15);

		Label firstSeparator = new Label(personGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(firstSeparator).atTopTo(fullName, 1).atLeftTo(pictureLabel, 5).atRight(5);

		userLabel = new Label(personGroup, SWT.NONE);
		userLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(userLabel).atTopTo(fullNameLabel, 6).atRightTo(fullName, 5);

		user = new Label(personGroup, SWT.NONE);
		FormDatas.attach(user).atTopTo(fullNameLabel, 6).atLeftTo(userLabel, 5);

		Label secondSeparator = new Label(personGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(secondSeparator).atTopTo(user, 1).atLeftTo(pictureLabel, 5).atRight(5);

		birthdayLabel = new Label(personGroup, SWT.NONE);
		birthdayLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(birthdayLabel).atTopTo(userLabel, 6).atRightTo(user, 5);

		birthday = new Label(personGroup, SWT.NONE);
		FormDatas.attach(birthday).atTopTo(userLabel, 6).atLeftTo(birthdayLabel, 5);

		Label thirdSeparator = new Label(personGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(thirdSeparator).atTopTo(birthday, 1).atLeftTo(pictureLabel, 5).atRight(5);

		sexLabel = new Label(personGroup, SWT.NONE);
		sexLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(sexLabel).atTopTo(birthdayLabel, 6).atRightTo(birthday, 5);

		sex = new Label(personGroup, SWT.NONE);
		FormDatas.attach(sex).atTopTo(birthdayLabel, 6).atLeftTo(sexLabel, 5);

		Label fourthSeparator = new Label(personGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(fourthSeparator).atTopTo(sex, 1).atLeftTo(pictureLabel, 5).atRight(5);

		identificationLabel = new Label(personGroup, SWT.NONE);
		identificationLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(identificationLabel).atTopTo(sexLabel, 6).atRightTo(sex, 5);

		identification = new Label(personGroup, SWT.NONE);
		FormDatas.attach(identification).atTopTo(sexLabel, 6).atLeftTo(identificationLabel, 5);

		unassociate = new Button(viewPersonDataComposite, SWT.NONE);
		FormDatas.attach(unassociate).atTopTo(personGroup, -25).withHeight(23).atLeftTo(personGroup, 5);
		unassociate.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 7932356419459592823L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				dissociate();
			}
		});

		searchPersonComposite = new Composite(personDataComposite, SWT.NONE);
		searchPersonComposite.setLayout(new FormLayout());
		searchPersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(searchPersonComposite).atTopTo(personDataComposite);

		profesorData = new Label(searchPersonComposite, SWT.NONE);
		profesorData.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(profesorData).atTopTo(parent, 15).atLeft(0);

		sugestLabel = new Label(searchPersonComposite, SWT.NONE);
		FormDatas.attach(sugestLabel).atTopTo(profesorData, 10).atLeft(0);

		searchText = new Text(searchPersonComposite, 0);
		FormDatas.attach(searchText).atTopTo(sugestLabel, 5).withHeight(10).atLeft(0).withWidth(250);

		searchText.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void focusLost(FocusEvent arg0) {
				searchButton.getShell().setDefaultButton(null);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				searchButton.getShell().setDefaultButton(searchButton);
			}
		});

		searchButton = new Button(searchPersonComposite, SWT.NONE);
		FormDatas.attach(searchButton).atTopTo(sugestLabel, 5).withHeight(21).atLeftTo(searchText, 10);

		msgProfessorNotFound = new Composite(personDataComposite, SWT.NONE);
		msgProfessorNotFound.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(msgProfessorNotFound).atTopTo(sugestLabel, 50).withHeight(50).withWidth(250).atRight(0);

		searchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8115718469128343452L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				Search();
			}
		});

		tablePersonComposite = new Composite(personDataComposite, SWT.NONE);
		tablePersonComposite.setLayout(new FormLayout());
		tablePersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(tablePersonComposite).atTopTo(searchPersonComposite, 15).atLeft(0).atRight(0);

		tablePerson = new SecurityCRUDTreeTable(tablePersonComposite, SWT.NONE);
		tablePerson.setEntityClass(Person.class);
		tablePerson.setSearch(false);
		tablePerson.setSaveAll(false);
		tablePerson.setPageSize(10);

		tablePerson.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 8817895862824622805L;

			@Override
			public void handleEvent(Event event) {
				refresh();
			}
		});

		Column columnPerson = new Column("associate", parent.getDisplay(), new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				person = (Person) event.entity.getRow();
				associate();
				showPerson();
			}
		});
		columnPerson.setToolTipText(MessageUtil.unescape(AbosMessages.get().BUTTON_ASSOCIATE));
		tablePerson.addActionColumn(columnPerson);

		TreeTableColumn columnsPerson[] = { new TreeTableColumn(60, 0, "getFullName"), new TreeTableColumn(20, 1, "getDNI"), new TreeTableColumn(20, 2, "getSex.getNomenclatorDescription") };
		tablePerson.createTable(columnsPerson);

		tablePerson.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "dni";
						break;
					}
				}
				searchPerson(event.currentPage - 1, event.pageSize);
				int rowsCount = tablePerson.getRows().size();

				heightTablePerson = (rowsCount * 28) + 120;
				ajustRezise(tablePersonComposite, heightTablePerson);
				ajustRezise(personDataComposite, getHeightSearchPersonComposite() + getHeightTablePersonComposite());
				refresh(personDataComposite.getParent().getShell());

				refresh();
			}

		});
		FormDatas.attach(tablePerson).atTopTo(tablePersonComposite).atLeft().atRight();
		enrollmentComposite = new Composite(parent, SWT.NONE);
		enrollmentComposite.setLayout(new FormLayout());
		enrollmentComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(enrollmentComposite).atTopTo(personDataComposite, 5).atLeft(15).atRight(15);

		enrollmentListLabel = new Label(enrollmentComposite, SWT.NONE);
		FormDatas.attach(enrollmentListLabel).atTopTo(descriptionText, 15).atLeft(0);
		enrollmentListLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");

		tableEnrollment = new CRUDTreeTable(enrollmentComposite, SWT.NONE);
		FormDatas.attach(tableEnrollment).atTopTo(enrollmentListLabel, 5).atLeft(0).atRight(0);

		tableEnrollment.setEntityClass(Enrollment.class);
		tableEnrollment.setSearch(false);
		tableEnrollment.setSaveAll(false);

		enrollmentSaveArea = new EnrollmentSaveArea(registerFormationCourse, controller, tableEnrollment, formationCourse);

		tableEnrollment.setAdd(true, enrollmentSaveArea);
		tableEnrollment.setUpdate(true, enrollmentSaveArea);
		tableEnrollment.setVisible(true);
		tableEnrollment.setDelete(true);
		tableEnrollment.setPageSize(10);
		CRUDTreeTableUtils.configUpdate(tableEnrollment);

		tableEnrollment.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 8817895862824622805L;

			@Override
			public void handleEvent(Event event) {
				refresh();
			}
		});

		tableEnrollment.addDeleteListener(new TreeColumnListener() {
			public void handleEvent(final TreeColumnEvent event) {
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), new DialogCallback() {
					private static final long serialVersionUID = 8415736231132589115L;

					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {
							Enrollment enrollment = (Enrollment) event.entity.getRow();
							getEntities().remove(enrollment);
							tableEnrollment.getPaginator().goToFirstPage();
							RetroalimentationUtils.showInformationShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_DELETE));

						}
					}
				});
			}
		});

		tableEnrollment.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				searchEnrolment(getEntities(), event.currentPage - 1, event.pageSize);
			}
		});

		CRUDTreeTableUtils.configReports(tableEnrollment, MessageUtil.unescape(AbosMessages.get().LABEL_ENROLLMENT), searchCriteriaList);
		TreeTableColumn columns[] = { new TreeTableColumn(25, 0, "getAddressedTo.getNomenclatorName"), new TreeTableColumn(50, 1, "getArea"), new TreeTableColumn(25, 2, "getQuantity") };
		tableEnrollment.createTable(columns);

		mapControls.put("courseNameText", courseNameText);
		mapControls.put("hourQuantityText", hourQuantityText);
		mapControls.put("coursetypeCombo", coursetypeCombo);
		mapControls.put("roomCombo", roomCombo);

		mapControls.put("descriptionText", descriptionText);
		mapControls.put("fromDateTime", fromDateTime);
		mapControls.put("toDateTime", toDateTime);
		mapControls.put("imparted", imparted);
		mapControls.put("recived", recived);
		mapControls.put("workerQuantityText", workerQuantityText);
		mapControls.put("externalQuantityText", externalQuantityText);
		mapControls.put("tableEnrollment", tableEnrollment);

		workerQuantityText.addModifyListener(new ModifyListener() {
			private static final long serialVersionUID = -7865194782880370261L;

			@Override
			public void modifyText(ModifyEvent arg0) {

				if (Auxiliary.isNumber(externalQuantityText) || externalQuantityText.getText().length() == 0) {
					externalQuantityText.setBackground(null);
				}

			}
		});

		externalQuantityText.addModifyListener(new ModifyListener() {
			private static final long serialVersionUID = 1070661658849174440L;

			@Override
			public void modifyText(ModifyEvent arg0) {
				if (Auxiliary.isNumber(workerQuantityText) || workerQuantityText.getText().length() == 0) {
					workerQuantityText.setBackground(null);
				}

			}
		});

		// if (option == 0) {
		cancelBtn = new Button(enrollmentComposite, SWT.PUSH);
		FormDatas.attach(cancelBtn).atTopTo(tableEnrollment, 5).atRight(0).withHeight(23);
		cancelBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
					private static final long serialVersionUID = 1L;

					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {

							registerFormationCourse.notifyListeners(SWT.Dispose, new Event());
						}
					}
				});
			}
		});

		saveBtn = new Button(enrollmentComposite, SWT.PUSH);
		FormDatas.attach(saveBtn).atTopTo(tableEnrollment, 5).atRightTo(cancelBtn, 5).withHeight(23);

		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2615553092700551346L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableEnrollment.destroyEditableArea();
				flag = 0;

				if (workerQuantityText.getText().length() == 0 && externalQuantityText.getText().length() == 0) {
					workerQuantityText.setBackground(new Color(workerQuantityText.getDisplay(), 255, 204, 153));
					externalQuantityText.setBackground(new Color(externalQuantityText.getDisplay(), 255, 204, 153));
				}
				if (getValidator().decorationFactory.isRequiredControlDecorationIsVisible() || workerQuantityText.getText().length() == 0 && externalQuantityText.getText().length() == 0) {

					if (!getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
						RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape("Debe especificar al menos uno de estos campos: Cantidad de bibliotecarios y/o Estudiantes Externos."));
						flag = 1;
					} else {
						RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
					}

					if (workerQuantityText.getText().length() == 0 && externalQuantityText.getText().length() == 0) {
						workerQuantityText.setBackground(new Color(workerQuantityText.getDisplay(), 255, 204, 153));
						externalQuantityText.setBackground(new Color(externalQuantityText.getDisplay(), 255, 204, 153));
					}

				} else {
					if (getValidator().decorationFactory.AllControlDecorationsHide()) {

						int fromYear = fromDateTime.getYear() - 1900;
						int fromMonth = fromDateTime.getMonth();
						int fromDay = fromDateTime.getDay();

						Date startDate = new Date(fromYear, fromMonth, fromDay);

						int toYear = toDateTime.getYear() - 1900;
						int toMonth = toDateTime.getMonth();
						int toDay = toDateTime.getDay();

						Date endDate = new Date(toYear, toMonth, toDay);
						Long toStartDate = startDate.getTime();
						Long toEndDate = endDate.getTime();
						Long dif = toEndDate - toStartDate;
						Double days = Math.floor(dif / (1000 * 60 * 60 * 24));
						double hours = days * 8 + 8;

						double quantity = Double.parseDouble(hourQuantityText.getText());
						if (quantity <= hours) {

							String formationCourseName = courseNameText.getText().replaceAll(" +", " ").trim();
							Library libraryMy = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

							FormationCourse exist = ((LibraryViewController) controller).getFormationCourseByName(libraryMy.getLibraryID(), formationCourseName);

							if (exist == null || (formationCourse != null && formationCourse.getFormationCourseID() == exist.getFormationCourseID())) {
								if (Auxiliary.initDateLessOrEqualThanFinishDate(fromDateTime, toDateTime)) {
									if ((!(getPerson() == null) && imparted.getSelection()) || (recived.getSelection())) {

										int workerQuantityy = (workerQuantityText.getText().length() > 0) ? Integer.parseInt(workerQuantityText.getText()) : 0;
										int externalQuantityy = (externalQuantityText.getText().length() > 0) ? Integer.parseInt(externalQuantityText.getText()) : 0;
										int cont = 0;
										for (Enrollment enrollment : getEntities()) {
											cont = cont + enrollment.getQuantity();
										}
										if (cont == 0 || (workerQuantityy + externalQuantityy == cont)
										// 2==2
										) {

											String courseName = courseNameText.getText().replaceAll(" +", " ").trim();
											Integer hourQuantity = Integer.parseInt(hourQuantityText.getText());

											Nomenclator coursetype = (Nomenclator) UiUtils.getSelected(coursetypeCombo);

											Room room = (Room) UiUtils.getSelected(roomCombo);

											boolean received = true;
											if (imparted.getSelection()) {
												received = false;
											} else {
												received = true;
											}
											String description = descriptionText.getText().replaceAll(" +", " ").trim();
											Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
											if (formationCourse == null) {
												formationCourse = new FormationCourse();
											}
											if (!(getPerson() == null)) {
												Long personId = person.getPersonID();
												Person professor = personService.findOnePerson(personId);
												formationCourse.setProfessor(professor);
											}
											if (workerQuantityText.getText().length() > 0) {
												int workerQuantity = Integer.parseInt(workerQuantityText.getText());
												formationCourse.setWorkersQuantity(workerQuantity);
											}
											if (externalQuantityText.getText().length() > 0) {
												int externalQuantity = Integer.parseInt(externalQuantityText.getText());
												formationCourse.setExternalWorkersQuantity(externalQuantity);
											}
											formationCourse.setLibrary(library);
											formationCourse.setCourseName(courseName);
											formationCourse.setHourQuantity(hourQuantity);
											formationCourse.setStartDate(startDate);
											formationCourse.setEndDate(endDate);
											formationCourse.setReceived(received);
											formationCourse.setCoursetype(coursetype);
											formationCourse.setRoom(room);

											formationCourse.setObservations(description);
											FormationCourse FormationCourseSaved = ((LibraryViewController) controller).saveFormationCourse(formationCourse);
											setPerson(null);
											String pregradeStudentt = "0";
											String postgradeStudentt = "0";
											String professorr = "0";
											String librarian = "0";
											String investigatorr = "0";
											String other = "0";

											for (Enrollment enrollment : getEntities()) {

												if (enrollment.getAddressedTo().getNomenclatorID() == pregradeStudent.getNomenclatorID()) {
													pregradeStudentt = "1";
												} else {
													if (enrollment.getAddressedTo().getNomenclatorID() == postgradeStudent.getNomenclatorID()) {
														postgradeStudentt = "1";
													} else {
														if (enrollment.getAddressedTo().getNomenclatorID() == professor.getNomenclatorID()) {
															professorr = "1";
														} else {
															if (enrollment.getAddressedTo().getNomenclatorID() == bibliotecario.getNomenclatorID()) {
																librarian = "1";
															} else {
																if (enrollment.getAddressedTo().getNomenclatorID() == investigator.getNomenclatorID()) {
																	investigatorr = "1";
																} else {
																	other = "1";
																}
															}
														}
													}
												}

												enrollment.setFormationCourse(FormationCourseSaved);
												enrollmentService.addEnrollment(enrollment);
											}

											String code = pregradeStudentt + postgradeStudentt + professorr + librarian + investigatorr + other;
											FormationCourseSaved.setCode(code);
											FormationCourse formationCourseSaved = ((LibraryViewController) controller).saveFormationCourse(FormationCourseSaved);

											tableEnrollment.clearRows();
											entities.clear();
											tableEnrollment.refresh();
											registerFormationCourse.notifyListeners(SWT.Dispose, new Event());
											contributorService.selectContributor("viewFormationCourse", formationCourseSaved, registerFormationCourse, contributorService);

										} else {
											RetroalimentationUtils.showErrorShellMessage(
											// register,
													MessageUtil.unescape(AbosMessages.get().ENROLLMENT_NOT_MACH));
										}
									} else {

										RetroalimentationUtils.showErrorShellMessage(
										// register,
												MessageUtil.unescape(AbosMessages.get().SHOULD_SELECT_PERSON_AS_PROFFESOR));

									}
								}

							} else {
								RetroalimentationUtils.showErrorShellMessage(
								// register,
										MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST));
								// flag = true;
							}
						} else {
							RetroalimentationUtils.showErrorShellMessage(
							// register,
									MessageUtil.unescape(AbosMessages.get().HOUR_QUANTITY_MORE));
						}
					} else {

						RetroalimentationUtils.showErrorShellMessage(
						// register,
								MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));

					}
				}
				// }
				// }
			}
		});
		initialize(coursetypeCombo, ((LibraryViewController) controller).findNomenclatorByCode(library.getLibraryID(), Nomenclator.COURSE_TYPE));
		validator.applyValidator(coursetypeCombo, 5);

		validator.applyValidator(coursetypeCombo, "coursetypeCombo", DecoratorType.REQUIRED_FIELD, true);

		initialize(roomCombo, ((LibraryViewController) controller).getAllManagementLibraryViewController().getRoomService().findAll(library.getLibraryID()));

		loadAreas();
		// LoadFormationCourseData();
		l10n();
		// saveBtn.getShell().setDefaultButton(saveBtn);
		//
		return parent;

	}

	@Override
	public String getID() {
		return "addFormationCourseID";
	}

	@Override
	public boolean canClose() {
		return true;
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().REGISTER_FORMATION_COURSE);
	}

	public EnrollmentSaveArea getEnrollmentSaveArea() {
		return enrollmentSaveArea;
	}

	public void setEnrollmentSaveArea(EnrollmentSaveArea enrollmentSaveArea) {
		this.enrollmentSaveArea = enrollmentSaveArea;
	}

	public void loadAreas() {
		pregradeStudent = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.PREGRADE_STUDENT);

		postgradeStudent = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.POSTGRADE_STUDENT);

		professor = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.PROFESSOR);

		investigator = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.INVESTIGATOR);

		bibliotecario = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.BIBLIOTECARIO);

		other = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.OTHER);

		carrera = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.CARRERA);

		facultad = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.FACULTY);

		departament = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.DEPARTMENT);

		investigationCenter = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.INVESTIGATION_CENTER);

		studyCenter = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.STUDY_CENTER);

		internalArea = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.ADDRESSED_TO_AREA_INTERNAL);

		externalArea = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.ADDRESSED_TO_AREA_IXTERNAL);

		diplomadeProgram = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.DIPLOMADE_PROGRAM);

		masterProgram = ((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.MASTER_PROGRAM);

		((LibraryViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById(Nomenclator.DOCTOR_PROGRAM);
	}

	private void searchPerson(int page, int size) {
		tablePerson.clearRows();

		Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		Page<Person> list = personService.findAll(library, searchTextConsult, page, size, size, orderByStringPerson);
		tablePerson.setTotalElements((int) list.getTotalElements());
		tablePerson.setRows(list.getContent());
		tablePerson.refresh();
	}

	public void ajustRezise(Composite parent, int height) {
		parent.setSize(parent.getSize().x, height);
		FormData tempo = (FormData) parent.getLayoutData();
		tempo.height = height;
		parent.setLayoutData(tempo);

		parent.setLayoutData(tempo);
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	public void refresh(Composite parent) {
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	public void Search() {

		viewPersonDataComposite.setVisible(false);
		tablePersonComposite.setVisible(true);
		tablePerson.clearRows();
		searchTextConsult = (searchText.getText() != "") ? searchText.getText() : null;
		orderByStringPerson = "firstName";
		direction = 1024;
		tablePerson.getPaginator().goToFirstPage();

		if (tablePerson.getRows().isEmpty()) {
			RetroalimentationUtils.showInformationMessage(msgProfessorNotFound, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
		}
		// refresh();

	}

	public void associate() {
		fullName.setText(getPerson().getFullName());
		Image image;
		if (getPerson().getPhoto() == null) {
			image = new Image(Display.getDefault(), AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/photo.png", false).getImageData().scaledTo(100, 100));
		} else {
			image = getPerson().getPhoto().getImage();
		}
		pictureLabel.setImage(image);
		user.setText((getPerson().getUser() != null) ? getPerson().getUser().getUsernameToString() : "-");
		birthday.setText((getPerson().getBirthDate() != null) ? new SimpleDateFormat("dd-MM-yyyy").format(getPerson().getBirthDate()) : "-");

		identification.setText(getPerson().getDNI());
		sex.setText(getPerson().getSex().getNomenclatorName());

		viewPersonDataComposite.setVisible(true);
		ajustRezise(viewPersonDataComposite, 187);
		refresh(personDataComposite.getParent().getShell());

		viewPersonDataComposite.layout(true, true);
		viewPersonDataComposite.redraw();
		viewPersonDataComposite.update();

		searchPersonComposite.setVisible(false);
		tablePersonComposite.setVisible(false);
	}

	public void recived() {
		// dissociate();
		personDataComposite.setVisible(false);
		ajustRezise(personDataComposite, 0);
		refresh(personDataComposite.getParent().getShell());
	}

	// FIXME BORRAR CODIGO COMENTARIADO
	public void dissociate() {
		tablePerson.getPaginator().goToFirstPage();
		viewPersonDataComposite.setVisible(false);
		searchPersonComposite.setVisible(true);
		tablePersonComposite.setVisible(true);
		person = null;
		// heightTablePersonComposite = heightTablePerson;
		ajustRezise(personDataComposite, getHeightSearchPersonComposite() + getHeightTablePersonComposite());
		refresh(personDataComposite.getParent().getShell());

		refresh();
	}

	public void showPerson() {
		heightTablePerson = tablePerson.getSize().y;
		ajustRezise(personDataComposite, getHeightViewPersonDataComposite() + 5);
		refresh(personDataComposite.getParent().getShell());
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Button getImparted() {
		return imparted;
	}

	public Button getRecived() {
		return recived;
	}

	@Override
	public void l10n() {
		tablePerson.l10n();
		tableEnrollment.l10n();

		tableEnrollment.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		tableEnrollment.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		tableEnrollment.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		registerFormationCourseLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_FORMATION_COURSE));

		enrollmentListLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_ENROLLMENT));

		personGroup.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA));
		fullNameLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME) + ": ");
		userLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER) + ": ");
		birthdayLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_BIRTHDAY) + ": ");
		sexLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEX) + ": ");
		identificationLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION) + ": ");
		unassociate.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_UNASSOCIATE));
		profesorData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PROFESSOR_DATA));
		sugestLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_INDICATE_PERSON_AS_PROFESSOR));
		searchText.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_NAME) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_LAST_NAME) + " | " + MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		searchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		tableEnrollment.setCancelButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));

		tablePerson.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME), MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION), MessageUtil.unescape(AbosMessages.get().LABEL_SEX)));

		tableEnrollment.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_DIRECTED_TO), MessageUtil.unescape(AbosMessages.get().LABEL_UBICATION), MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY)));

		courseFormationLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PORMATION_COURSE_DATA));
		courseNameLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_NAME));
		hourQuantityLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_QUANTITY));
		descriptionLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION));
		workerQuantityLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIBRARIAN_QUANTITY));
		coursetypeLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COURSE_TYPE));
		externalQuantityLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EXTERNAL_STUDENT_QUANTITY));

		roomLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ROOM));

		fromLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));

		toLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TO));

		imparted.setText(MessageUtil.unescape(AbosMessages.get().INTERNAL));
		recived.setText(MessageUtil.unescape(AbosMessages.get().EXTERNAL));

		saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		cancelBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));

		if (viewFormationCourseFragment != null) {
			viewFormationCourseFragment.l10n();
		}

		descriptionText.getParent().getParent().layout(true, true);
		descriptionText.getParent().getParent().redraw();
		descriptionText.getParent().getParent().update();
	}

	public Nomenclator getPregradeStudent() {
		return pregradeStudent;
	}

	public void setPregradeStudent(Nomenclator pregradeStudent) {
		this.pregradeStudent = pregradeStudent;
	}

	public Nomenclator getPostgradeStudent() {
		return postgradeStudent;
	}

	public void setPostgradeStudent(Nomenclator postgradeStudent) {
		this.postgradeStudent = postgradeStudent;
	}

	public Nomenclator getProfessor() {
		return professor;
	}

	public void setProfessor(Nomenclator professor) {
		this.professor = professor;
	}

	public Nomenclator getInvestigator() {
		return investigator;
	}

	public void setInvestigator(Nomenclator investigator) {
		this.investigator = investigator;
	}

	public Nomenclator getOther() {
		return other;
	}

	public void setOther(Nomenclator other) {
		this.other = other;
	}

	public Nomenclator getCarrera() {
		return carrera;
	}

	public void setCarrera(Nomenclator carrera) {
		this.carrera = carrera;
	}

	public Nomenclator getFacultad() {
		return facultad;
	}

	public void setFacultad(Nomenclator facultad) {
		this.facultad = facultad;
	}

	public Nomenclator getDepartament() {
		return departament;
	}

	public void setDepartament(Nomenclator departament) {
		this.departament = departament;
	}

	public Nomenclator getInvestigationCenter() {
		return investigationCenter;
	}

	public void setInvestigationCenter(Nomenclator investigationCenter) {
		this.investigationCenter = investigationCenter;
	}

	public Nomenclator getBibliotecario() {
		return bibliotecario;
	}

	public void setBibliotecario(Nomenclator bibliotecario) {
		this.bibliotecario = bibliotecario;
	}

	public Nomenclator getStudyCenter() {
		return studyCenter;
	}

	public void setStudyCenter(Nomenclator studyCenter) {
		this.studyCenter = studyCenter;
	}

	public Nomenclator getExternalArea() {
		return externalArea;
	}

	public void setExternalArea(Nomenclator externalArea) {
		this.externalArea = externalArea;
	}

	public Nomenclator getDiplomadeProgram() {
		return diplomadeProgram;
	}

	public void setDiplomadeProgram(Nomenclator diplomadeProgram) {
		this.diplomadeProgram = diplomadeProgram;
	}

	public Nomenclator getMasterProgram() {
		return masterProgram;
	}

	public void setMasterProgram(Nomenclator masterProgram) {
		this.masterProgram = masterProgram;
	}

	public Nomenclator getInternalArea() {
		return internalArea;
	}

	public void setInternalArea(Nomenclator internalArea) {
		this.internalArea = internalArea;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String getOrderByString() {
		return orderByString;
	}

	public void setOrderByString(String orderByString) {
		this.orderByString = orderByString;
	}

	public Map<String, Control> getMapControls() {
		return mapControls;
	}

	public void setMapControls(Map<String, Control> mapControls) {
		this.mapControls = mapControls;
	}

	public boolean IsNumber(Text workerQuantityText) {
		for (int i = 0; i < workerQuantityText.getText().length(); i++) {
			char c = workerQuantityText.getText().charAt(i);
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	public void refreshTable() {
		tableEnrollment.clearRows();
		tableEnrollment.refresh();
		tableEnrollment.destroyEditableArea();
		tableEnrollment.getPaginator().goToFirstPage();
	}

	public void searchEnrolment(List<Enrollment> list, int page, int size) {

		tableEnrollment.setTotalElements((int) list.size());
		if (list.size() <= page * size + size) {
			tableEnrollment.setRows(list.subList(page * size, list.size()));
		} else {
			tableEnrollment.setRows(list.subList(page * size, page * size + size));
		}
		tableEnrollment.refresh();
	}
}
