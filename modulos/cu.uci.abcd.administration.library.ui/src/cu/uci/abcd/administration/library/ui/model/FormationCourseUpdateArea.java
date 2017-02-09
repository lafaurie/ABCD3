package cu.uci.abcd.administration.library.ui.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
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
import cu.uci.abcd.administration.library.ui.RegisterFormationCourse;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Enrollment;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
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
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
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

public class FormationCourseUpdateArea extends BaseEditableArea {
	// private RegisterFormationCourseFragment registerFormationCourseFragment;
	// private ViewController controller;
	// private Composite parentComposite;
	// private Map<String, Control> controlsMaps;
	private CRUDTreeTable formationCourseTable;
	private Person profesor;
	List<Enrollment> entities = new ArrayList<>();
	// private Button saveBtn;
	// private FormationCourse formationCourse;
	// private IEnrollmentService enrollmentService;
	// private IPersonService personService;

	List<Long> enrolmentToDelete = new ArrayList<>();

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

	private Label registerFormationCourseLabel;
	private ViewController controller;
	private Library library;
	private String orderByStringPerson = "firstName";
	private Label courseFormationLabel;
	private Label courseNameLabel;
	private Text courseNameText;
	private Label hourQuantityLabel;
	private Text hourQuantityText;
	Composite msgProfessorNotFound;
	Label profesorData;

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
	// private Composite workerDataComposite;
	Composite enrollmentComposite;
	// private int heightTablePersonComposite;
	private int heightTablePerson;
	private int option;
	private RegisterFormationCourse registerFormationCourse;
	// private Composite show;
	private ContributorService contributorService;
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
	// private Composite register;
	// public Composite getRegister() {
	// return register;
	// }

	// public void setRegister(Composite register) {
	// this.register = register;
	// }

	private PagePainter painter;
	private Person person;
	private Combo roomCombo;
	private Label roomLabel;

	// Composite msg;

	// public Composite getMsg() {
	// return msg;
	// }

	// public void setMsg(Composite msg) {
	// this.msg = msg;
	// }

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

	// public int getHeightWorkerDataComposite() {
	// return workerDataComposite.getSize().y;
	// }

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public FormationCourseUpdateArea(ViewController controller) {
		this.controller = controller;
		this.enrollmentService = ServiceProviderUtil.getService(IEnrollmentService.class);
		this.personService = ServiceProviderUtil.getService(IPersonService.class);
	}

	public FormationCourseUpdateArea(ViewController controller, CRUDTreeTable formationCourseTable) {
		this.controller = controller;
		this.setFormationCourseTable(formationCourseTable);
		this.enrollmentService = ServiceProviderUtil.getService(IEnrollmentService.class);
		this.personService = ServiceProviderUtil.getService(IPersonService.class);
	}

	FormationCourseUpdateArea formationCourseUpdateArea;

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {

		formationCourseUpdateArea = this;

		FormationCourse formationCourseToView = (FormationCourse) entity.getRow();
		formationCourse = ((LibraryViewController) controller).getFormationCourseById(formationCourseToView.getFormationCourseID());
		person = formationCourse.getProfessor();
		if (formationCourse.getProfessor() != null) {
			profesor = formationCourse.getProfessor();
		} else {
			profesor = null;
		}
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

		painter = new FormPagePainter();
		validator = new ValidatorUtils(new CustomControlDecoration());

		int dimension = parent.getParent().getParent().getBounds().width;
		painter.setDimension(dimension);
		parent.setLayout(new FormLayout());
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(parent);

		// parent.setLayout(new FormLayout());
		// parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		// painter.addComposite(parent);

		// int total = parent.getDisplay().getBounds().width;
		// double middle1 = (total * 0.375);
		// int middle = Integer.valueOf((int) Math.round(middle1));

		// register = new Composite(parent, SWT.NONE);
		// register.setData(RWT.CUSTOM_VARIANT, "gray_background");
		// painter.addComposite(register);

		registerFormationCourseLabel = new Label(parent, SWT.NONE);
		painter.addHeader(registerFormationCourseLabel);

		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		// registerFormationCourseLabel.setData(RWT.CUSTOM_VARIANT,
		// "groupLeft");
		// FormDatas.attach(registerFormationCourseLabel)
		// .atTopTo(register, 25).atLeft(15);

		// workerDataComposite = new Composite(register, SWT.NONE);
		// workerDataComposite.setLayout(new FormLayout());
		// workerDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		// FormDatas.attach(workerDataComposite).atTopTo(registerFormationCourseLabel,
		// 5).atLeft(15)
		// .atRight(15);

		// msg = new Composite(workerDataComposite, SWT.NONE);
		// msg.setLayout(new FormLayout());
		// msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		// FormDatas.attach(msg).atTopTo(workerDataComposite,
		// 20).withWidth(320).withHeight(100).atRight(0);
		// workerDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		// painter.addComposite(workerDataComposite);

		courseFormationLabel = new Label(parent, SWT.NONE);
		painter.addHeader(courseFormationLabel);

		imparted = new Button(parent, SWT.RADIO);
		imparted.setSelection(true);
		painter.add(imparted);

		recived = new Button(parent, SWT.RADIO);
		painter.add(recived);

		recived.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 6724578212995896612L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				dissociate();
				recived();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		imparted.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = -4186307840238390263L;

			// FIXME BORRAR CODIGO COMENTARIADO
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				personDataComposite.setVisible(true);
				if (person == null) {
					// ajustRezise(personDataComposite, 150);

					ajustRezise(personDataComposite, getHeightSearchPersonComposite() + getHeightTablePersonComposite());
					// refresh(personDataComposite.getParent().getShell());

				} else {
					ajustRezise(personDataComposite, 177);
				}
				refresh(personDataComposite.getParent().getShell());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		painter.reset();

		courseNameLabel = new Label(parent, SWT.NONE);
		painter.add(courseNameLabel);
		// FormDatas.attach(courseNameLabel).atTopTo(recived, 20)
		// .atRightTo(courseNameText, 5);

		courseNameText = new Text(parent, SWT.NONE);
		painter.add(courseNameText);
		// FormDatas.attach(courseNameText).atTopTo(recived, 15).atRight(middle)
		// .withWidth(250).withHeight(10);
		validator.applyValidator(courseNameText, "courseNameText", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(courseNameText, "courseNameText1", DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);

		hourQuantityLabel = new Label(parent, SWT.NONE);
		painter.add(hourQuantityLabel);

		hourQuantityText = new Text(parent, SWT.NONE);
		painter.add(hourQuantityText);
		// FormDatas.attach(hourQuantityText).atTopTo(recived, 15).atRight(15)
		// .withWidth(250).withHeight(10);
		validator.applyValidator(hourQuantityText, "hourQuantityText", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(hourQuantityText, "hourQuantityNumber", DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);

		workerQuantityLabel = new Label(parent, SWT.NONE);
		painter.add(workerQuantityLabel);
		// FormDatas.attach(workerQuantityLabel).atTopTo(courseNameText, 20)
		// .atRightTo(workerQuantityText, 5);

		workerQuantityText = new Text(parent, SWT.NONE);
		painter.add(workerQuantityText);
		// FormDatas.attach(workerQuantityText).atTopTo(courseNameText, 15)
		// .atRight(middle).withWidth(250).withHeight(10);
		validator.applyValidator(workerQuantityText, "workerQuantityNumber", DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		// workerQuantityText.setData("grupalValidation", true);

		coursetypeLabel = new Label(parent, SWT.NONE);
		painter.add(coursetypeLabel);
		// FormDatas.attach(coursetypeLabel).atTopTo(courseNameText, 20)
		// .atRightTo(coursetypeCombo, 5);

		coursetypeCombo = new Combo(parent, SWT.READ_ONLY);
		painter.add(coursetypeCombo);
		// FormDatas.attach(coursetypeCombo).atTopTo(courseNameText, 15)
		// .atRight(15).withWidth(270).withHeight(23);

		externalQuantityLabel = new Label(parent, SWT.NONE);
		painter.add(externalQuantityLabel);
		// FormDatas.attach(externalQuantityLabel).atTopTo(workerQuantityText,
		// 20)
		// .atRightTo(externalQuantityText, 5);

		externalQuantityText = new Text(parent, SWT.NONE);
		painter.add(externalQuantityText);
		// FormDatas.attach(externalQuantityText).atTopTo(workerQuantityText,
		// 15)
		// .atRight(middle).withWidth(250).withHeight(10);
		validator.applyValidator(externalQuantityText, "externalQuantityNumber", DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		// externalQuantityText.setData("grupalValidation", true);

		roomLabel = new Label(parent, SWT.NONE);
		painter.add(roomLabel);
		// FormDatas.attach(roomLabel).atTopTo(coursetypeCombo, 15)
		// .atRightTo(roomCombo, 5);

		roomCombo = new Combo(parent, SWT.READ_ONLY);
		painter.add(roomCombo);

		fromLabel = new Label(parent, SWT.NONE);
		painter.add(fromLabel);

		fromDateTime = new DateTime(parent, SWT.BORDER | SWT.DROP_DOWN);
		painter.add(fromDateTime);
		// FormDatas.attach(fromDateTime).atTopTo(externalQuantityText, 15)
		// .atRight(middle).withWidth(270).withHeight(25);
		validator.applyRangeDateValidator(fromDateTime, "fromDateTime2", DecoratorType.DATE_RANGE, -50, 0, 0, 1, 0, 0, true);

		toLabel = new Label(parent, SWT.NONE);
		painter.add(toLabel);
		// FormDatas.attach(toLabel).atTopTo(externalQuantityText, 20)
		// .atRightTo(toDateTime, 5);

		toDateTime = new DateTime(parent, SWT.BORDER | SWT.DROP_DOWN);
		painter.add(toDateTime);

		validator.applyRangeDateValidator(toDateTime, "toDateTime2", DecoratorType.DATE_RANGE, -50, 0, 0, 1, 0, 0, true);

		// FormDatas.attach(toDateTime).atTopTo(externalQuantityText, 15)
		// .atRight(15).withWidth(270).withHeight(25);

		painter.reset();

		descriptionLabel = new Label(parent, SWT.NONE);
		painter.add(descriptionLabel);
		// FormDatas.attach(descriptionLabel).atTopTo(fromDateTime, 15)
		// .atRightTo(fromDateTime, 5);

		descriptionText = new Text(parent, SWT.NONE | SWT.WRAP | SWT.V_SCROLL);
		painter.add(descriptionText);
		// FormDatas.attach(descriptionText).atTopTo(fromDateTime, 15)
		// .withHeight(30).atLeftTo(descriptionLabel, 5).withWidth(240);
		validator.applyValidator(descriptionText, 250);

		/*
		 * courseNameText = new Text(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(courseNameText).atTopTo(recived, 15).atRight(middle)
		 * .withWidth(250).withHeight(10);
		 * validator.applyValidator(courseNameText, "courseNameText",
		 * DecoratorType.REQUIRED_FIELD, true, 49);
		 * validator.applyValidator(courseNameText, "courseNameText1",
		 * DecoratorType.ALPHA_NUMERICS_SPACES, true);
		 * 
		 * courseNameLabel = new Label(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(courseNameLabel).atTopTo(recived, 20)
		 * .atRightTo(courseNameText, 5);
		 * 
		 * hourQuantityText = new Text(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(hourQuantityText).atTopTo(recived, 15).atRight(15)
		 * .withWidth(250).withHeight(10);
		 * validator.applyValidator(hourQuantityText, "hourQuantityText",
		 * DecoratorType.REQUIRED_FIELD, true, 5);
		 * validator.applyValidator(hourQuantityText, "hourQuantityNumber",
		 * DecoratorType.NUMBER_ONLY, true);
		 * 
		 * hourQuantityLabel = new Label(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(hourQuantityLabel).atTopTo(recived, 20)
		 * .atRightTo(hourQuantityText, 5);
		 * 
		 * //bibliotecarios workerQuantityText = new Text(workerDataComposite,
		 * SWT.NONE);
		 * FormDatas.attach(workerQuantityText).atTopTo(courseNameText, 15)
		 * .atRight(middle).withWidth(250).withHeight(10);
		 * validator.applyValidator(workerQuantityText, "workerQuantityNumber",
		 * DecoratorType.NUMBER_ONLY, true, 5);
		 * workerQuantityText.setData("grupalValidation", true);
		 * 
		 * workerQuantityLabel = new Label(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(workerQuantityLabel).atTopTo(courseNameText, 20)
		 * .atRightTo(workerQuantityText, 5);
		 * 
		 * coursetypeCombo = new Combo(workerDataComposite, SWT.READ_ONLY);
		 * FormDatas.attach(coursetypeCombo).atTopTo(courseNameText, 15)
		 * .atRight(15).withWidth(270).withHeight(23);
		 * 
		 * 
		 * //coursetypeCombo.setBackground(null);
		 * 
		 * 
		 * coursetypeLabel = new Label(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(coursetypeLabel).atTopTo(courseNameText, 20)
		 * .atRightTo(coursetypeCombo, 5);
		 * 
		 * //externos externalQuantityText = new Text(workerDataComposite,
		 * SWT.NONE);
		 * FormDatas.attach(externalQuantityText).atTopTo(workerQuantityText,
		 * 15) .atRight(middle).withWidth(250).withHeight(10);
		 * validator.applyValidator(externalQuantityText,
		 * "externalQuantityNumber", DecoratorType.NUMBER_ONLY, true, 5);
		 * externalQuantityText.setData("grupalValidation", true);
		 * 
		 * externalQuantityLabel = new Label(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(externalQuantityLabel).atTopTo(workerQuantityText,
		 * 20) .atRightTo(externalQuantityText, 5);
		 * 
		 * 
		 * roomCombo = new Combo(workerDataComposite, SWT.READ_ONLY);
		 * FormDatas.attach(roomCombo).atTopTo(coursetypeCombo, 10)
		 * .atRight(15).withWidth(270).withHeight(23);
		 * 
		 * roomLabel = new Label(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(roomLabel).atTopTo(coursetypeCombo, 15)
		 * .atRightTo(roomCombo, 5);
		 * 
		 * 
		 * 
		 * fromDateTime = new DateTime(workerDataComposite, SWT.BORDER |
		 * SWT.DROP_DOWN);
		 * FormDatas.attach(fromDateTime).atTopTo(externalQuantityText, 15)
		 * .atRight(middle).withWidth(270).withHeight(25);
		 * 
		 * fromLabel = new Label(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(fromLabel).atTopTo(externalQuantityText, 20)
		 * .atRightTo(fromDateTime, 5);
		 * 
		 * toDateTime = new DateTime(workerDataComposite, SWT.BORDER |
		 * SWT.DROP_DOWN);
		 * FormDatas.attach(toDateTime).atTopTo(externalQuantityText, 15)
		 * .atRight(15).withWidth(270).withHeight(25);
		 * 
		 * 
		 * //validator.applyValidator(courseNameText, "courseNameText",
		 * //DecoratorType.REQUIRED_FIELD, true, 49);
		 * 
		 * 
		 * toLabel = new Label(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(toLabel).atTopTo(externalQuantityText, 20)
		 * .atRightTo(toDateTime, 5);
		 * 
		 * descriptionLabel = new Label(workerDataComposite, SWT.NONE);
		 * FormDatas.attach(descriptionLabel).atTopTo(fromDateTime, 15)
		 * .atRightTo(fromDateTime, 5);
		 * 
		 * descriptionText = new Text(workerDataComposite, SWT.NONE | SWT.WRAP |
		 * SWT.V_SCROLL);
		 * FormDatas.attach(descriptionText).atTopTo(fromDateTime, 15)
		 * .withHeight(30).atLeftTo(descriptionLabel, 5).withWidth(240);
		 * validator.applyValidator(descriptionText, 499);
		 */

		personDataComposite = new Composite(parent, SWT.NONE);
		personDataComposite.setLayout(new FormLayout());
		personDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personDataComposite).atTopTo(descriptionText).atLeft(15).atRight(15);

		viewPersonDataComposite = new Composite(personDataComposite, SWT.NONE);
		viewPersonDataComposite.setLayout(new FormLayout());
		viewPersonDataComposite.setVisible(false);
		viewPersonDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(viewPersonDataComposite).atTopTo(personDataComposite).withHeight(100);

		// personDataLabel = new Label(viewPersonDataComposite, SWT.NONE);
		// personDataLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		// FormDatas.attach(personDataLabel).atTopTo(viewPersonDataComposite,
		// 15)
		// .atLeft(0);

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

		tableEnrollment.setAdd(true, new EnrollmentSaveArea(formationCourseUpdateArea, controller, tableEnrollment, formationCourse));
		enrollmentSaveArea = new EnrollmentSaveArea(this, controller, tableEnrollment, formationCourse);
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

							if (enrollment.getEnrollmentID() != null) {
								enrolmentToDelete.add(enrollment.getEnrollmentID());
							}
							getEntities().remove(enrollment);
							tableEnrollment.getPaginator().goToFirstPage();

							/*
							 * 
							 * tableEnrollment.destroyEditableArea(); Enrollment
							 * enrollment = (Enrollment) event.entity.getRow();
							 * if (enrollment.getEnrollmentID() == null) { //si
							 * es new
							 * 
							 * List<Enrollment> entities = new ArrayList<>();
							 * //for (int i = 0; i <
							 * tableEnrollment.getRows().size(); i++) { //
							 * IGridViewEntity a =
							 * tableEnrollment.getRows().get(i); // Enrollment b
							 * = (Enrollment) a.getRow(); //}
							 * 
							 * 
							 * for (IGridViewEntity iGridViewEntity :
							 * tableEnrollment .getRows()) { Enrollment pepe =
							 * (Enrollment) iGridViewEntity.getRow(); if
							 * (!pepe.equals(enrollment)) {
							 * entities.add((Enrollment)
							 * iGridViewEntity.getRow()); } }
							 * tableEnrollment.clearRows();
							 * tableEnrollment.setRows(entities); } else { Long
							 * idEnrollment = enrollment.getEnrollmentID();
							 * ((LibraryViewController) controller)
							 * .getAllManagementLibraryViewController()
							 * .getEnrollmentService()
							 * .deleteEnrollment(idEnrollment);
							 * //tableEnrollment.getPaginator().goToFirstPage();
							 * //tableEnrollment.refresh();
							 * 
							 * tableEnrollment.clearRows();
							 * tableEnrollment.setRows(enrollmentService
							 * .findByFormationCourse(formationCourse
							 * .getFormationCourseID()));
							 * tableEnrollment.refresh();
							 * 
							 * }
							 */
							RetroalimentationUtils.showInformationShellMessage(
							// enrollmentComposite,
									MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_DELETE));

						}
					}
				});

			}
		});
		/*
		 * CRUDTreeTableUtils.configRemove(tableEnrollment, new IActionCommand()
		 * {
		 * 
		 * @Override public void execute(final TreeColumnEvent event) {
		 * tableEnrollment.destroyEditableArea(); Enrollment enrollment =
		 * (Enrollment) event.entity.getRow(); if (enrollment.getEnrollmentID()
		 * == null) { //si es new List<Enrollment> entities = new ArrayList<>();
		 * for (IGridViewEntity iGridViewEntity : tableEnrollment .getRows()) {
		 * if (!((Enrollment) iGridViewEntity).equals(enrollment)) {
		 * entities.add((Enrollment) iGridViewEntity.getRow()); } }
		 * tableEnrollment.clearRows(); tableEnrollment.setRows(entities); }
		 * else { Long idEnrollment = enrollment.getEnrollmentID();
		 * ((LibraryViewController) controller)
		 * .getAllManagementLibraryViewController() .getEnrollmentService()
		 * .deleteEnrollment(idEnrollment);
		 * tableEnrollment.getPaginator().goToFirstPage(); }
		 * 
		 * } });
		 */

		CRUDTreeTableUtils.configReports(tableEnrollment, MessageUtil.unescape(AbosMessages.get().LABEL_ENROLLMENT), searchCriteriaList);
		TreeTableColumn columns[] = { new TreeTableColumn(25, 0, "getAddressedTo.getNomenclatorName"), new TreeTableColumn(50, 1, "getArea"), new TreeTableColumn(25, 2, "getQuantity") };
		tableEnrollment.createTable(columns);

		tableEnrollment.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				searchEnrolment(getEntities(), event.currentPage - 1, event.pageSize);
			}
		});

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
			/**
			 * 
			 */
			private static final long serialVersionUID = -7865194782880370261L;

			@Override
			public void modifyText(ModifyEvent arg0) {
				// externalQuantityText.setBackground(null);

				if (Auxiliary.isNumber(externalQuantityText) || externalQuantityText.getText().length() == 0) {
					externalQuantityText.setBackground(null);
				}

			}
		});

		externalQuantityText.addModifyListener(new ModifyListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1070661658849174440L;

			@Override
			public void modifyText(ModifyEvent arg0) {
				// workerQuantityText.setBackground(null);
				if (Auxiliary.isNumber(workerQuantityText) || workerQuantityText.getText().length() == 0) {
					workerQuantityText.setBackground(null);
				}
			}
		});

		Label espacio = new Label(enrollmentComposite, SWT.NORMAL);
		FormDatas.attach(espacio).atTopTo(descriptionLabel, 40);

		initialize(coursetypeCombo, ((LibraryViewController) controller).findNomenclatorByCode(library.getLibraryID(), Nomenclator.COURSE_TYPE));
		validator.applyValidator(coursetypeCombo, 5);
		validator.applyValidator(coursetypeCombo, "coursetypeCombo", DecoratorType.REQUIRED_FIELD, true);

		initialize(roomCombo, ((LibraryViewController) controller).getAllManagementLibraryViewController().getRoomService().findAll(library.getLibraryID()));

		loadAreas();
		LoadFormationCourseData();
		// l10n();

		//
		return parent;

		// registerFormationCourseFragment = new
		// RegisterFormationCourseFragment(controller,
		// formationCourse.getLibrary(), profesor, formationCourse, 2);
		// parentComposite = (Composite)
		// registerFormationCourseFragment.createUIControl(shell);
		// controlsMaps = registerFormationCourseFragment.getMapControls();
		// return parentComposite;
	}

	/*
	 * public void loadAddressedtoarea() { List<Nomenclator> listAddressedtoarea
	 * = ((LibraryViewController) controller).findNomenclatorByCode(((Library)
	 * SecurityUtils .getService().getPrincipal()
	 * .getByKey("library")).getLibraryID(), Nomenclator.UBICATION); String[]
	 * comboStrings = new String[listAddressedtoarea.size()]; List<Nomenclator>
	 * type = new LinkedList<Nomenclator>(); for (int i = 0; i <
	 * listAddressedtoarea.size(); i++) { Nomenclator addressedtoarea =
	 * listAddressedtoarea.get(i); type.add(addressedtoarea); comboStrings[i] =
	 * addressedtoarea.getNomenclatorName(); }
	 * addressedtoareaCombo.setData(type);
	 * addressedtoareaCombo.setItems(comboStrings);
	 * 
	 * }
	 */
	/*
	 * public void loadAddressedToUserType() { List<Nomenclator>
	 * listAddressedToUserType = ((LibraryViewController)
	 * controller).findNomenclatorByCode(((Library) SecurityUtils
	 * .getService().getPrincipal() .getByKey("library")).getLibraryID(),
	 * Nomenclator.ADDRESSED_TO_USER_TYPE); String[] comboStrings = new
	 * String[listAddressedToUserType.size()]; List<Nomenclator> type = new
	 * LinkedList<Nomenclator>(); for (int i = 0; i <
	 * listAddressedToUserType.size(); i++) { Nomenclator addressedToUserType =
	 * listAddressedToUserType.get(i); type.add(addressedToUserType);
	 * comboStrings[i] = addressedToUserType.getNomenclatorName(); }
	 * addressedToUserTypeCombo.setData(type);
	 * addressedToUserTypeCombo.setItems(comboStrings); }
	 */
	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2615553092700551346L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableEnrollment.destroyEditableArea();
				if (workerQuantityText.getText().length() == 0 && externalQuantityText.getText().length() == 0) {
					workerQuantityText.setBackground(new Color(workerQuantityText.getDisplay(), 255, 204, 153));
					externalQuantityText.setBackground(new Color(externalQuantityText.getDisplay(), 255, 204, 153));
				}

				if (getValidator().decorationFactory.isRequiredControlDecorationIsVisible() || workerQuantityText.getText().length() == 0 && externalQuantityText.getText().length() == 0) {

					if (!getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
						RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape("Debe especificar al menos uno de estos campos: Cantidad de bibliotecarios y/o Estudiantes Externos."));
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

								if (Auxiliary.initDateLessOrEqualThanFinishDate((fromDateTime), (toDateTime))) {

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
											Nomenclator coursetype = (Nomenclator) UiUtils.getSelected((coursetypeCombo));
											Room room = (Room) UiUtils.getSelected(roomCombo);

											boolean received = true;
											if (imparted.getSelection()) {
												received = false;
											} else {
												received = true;
											}
											String description = descriptionText.getText().replaceAll(" +", " ").trim();
											Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
											Person professor = null;
											if (getPerson() != null) {
												Long personId = getPerson().getPersonID();
												professor = personService.findOnePerson(personId);
											}

											if (formationCourse == null) {
												formationCourse = new FormationCourse();

											}
											// int workerQuantity = null;
											if (workerQuantityText.getText().length() > 0) {
												// workerQuantity = Integer
												// .parseInt(workerQuantityText.getText());
												formationCourse.setWorkersQuantity(Integer.parseInt(workerQuantityText.getText()));
											} else {
												formationCourse.setWorkersQuantity(null);
											}
											// int externalQuantity = null;
											if (externalQuantityText.getText().length() > 0) {
												// externalQuantity = Integer
												// .parseInt(externalQuantityText
												// .getText());
												formationCourse.setExternalWorkersQuantity(Integer.parseInt(externalQuantityText.getText()));
											} else {
												formationCourse.setExternalWorkersQuantity(null);
											}
											formationCourse.setProfessor(professor);
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

											String pregradeStudentt = "0";
											String postgradeStudentt = "0";
											String professorr = "0";
											String librarian = "0";
											String investigatorr = "0";
											String other = "0";
											/*
											 * List<Enrollment> tmp = new
											 * ArrayList<>(); for (int i = 0; i
											 * <
											 * tableEnrollment.getRows().size();
											 * i++) { tmp.add((Enrollment)
											 * tableEnrollment
											 * .getRows().get(i).getRow()); }
											 */
											for (Enrollment enrollment : getEntities()) {

												if (enrollment.getAddressedTo().getNomenclatorID() == getPregradeStudent().getNomenclatorID()) {
													pregradeStudentt = "1";
												} else {
													if (enrollment.getAddressedTo().getNomenclatorID() == getPostgradeStudent().getNomenclatorID()) {
														postgradeStudentt = "1";
													} else {
														if (enrollment.getAddressedTo().getNomenclatorID() == getProfessor().getNomenclatorID()) {
															professorr = "1";
														} else {
															if (enrollment.getAddressedTo().getNomenclatorID() == getBibliotecario().getNomenclatorID()) {
																librarian = "1";
															} else {
																if (enrollment.getAddressedTo().getNomenclatorID() == getInvestigator().getNomenclatorID()) {
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
											for (int i = 0; i < enrolmentToDelete.size(); i++) {
												enrollmentService.deleteEnrollment(enrolmentToDelete.get(i));
												// enrolmentToDelete
											}
											enrolmentToDelete.clear();
											String code = pregradeStudentt + postgradeStudentt + professorr + librarian + investigatorr + other;
											FormationCourseSaved.setCode(code);
											((LibraryViewController) controller).saveFormationCourse(FormationCourseSaved);

											formationCourse = null;
											manager.save(new BaseGridViewEntity<FormationCourse>(FormationCourseSaved));
											manager.refresh();

											Composite viewSmg = ((FormationCourseViewArea) formationCourseTable.getActiveArea()).getViewFormationCourseFragment().getMsg();

											RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));

										} else {
											RetroalimentationUtils.showErrorShellMessage(
											// registerFormationCourseFragment.getRegister(),
													MessageUtil.unescape(AbosMessages.get().ENROLLMENT_NOT_MACH));
										}

									} else {
										RetroalimentationUtils.showErrorShellMessage(
										// registerFormationCourseFragment.getRegister(),
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
						// registerFormationCourseFragment.getRegister(),
								MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
					}
				}
			}

		});
		return parent;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean closable() {
		return true;
	}

	// @Override
	// public void l10n() {
	// saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
	// registerFormationCourseFragment.l10n();
	// }

	public CRUDTreeTable getFormationCourseTable() {
		return formationCourseTable;
	}

	public void setFormationCourseTable(CRUDTreeTable formationCourseTable) {
		this.formationCourseTable = formationCourseTable;
	}

	public Person getProfesor() {
		return profesor;
	}

	public void setProfesor(Person profesor) {
		this.profesor = profesor;
	}

	@Override
	public String getID() {
		return "updateFormationCourseID";
	}

	private void LoadFormationCourseData() {

		if (formationCourse != null) {

			UiUtils.selectValue(coursetypeCombo, formationCourse.getCoursetype());
			if (formationCourse.getRoom() != null) {
				UiUtils.selectValue(roomCombo, formationCourse.getRoom());
			}

			courseNameText.setText(formationCourse.getCourseName());
			hourQuantityText.setText(formationCourse.getHourQuantity().toString());
			descriptionText.setText(formationCourse.getObservations());

			if (formationCourse.isReceived()) {
				recived.setSelection(true);
				imparted.setSelection(false);
				recived();
			} else {
				imparted.setSelection(true);
				recived.setSelection(false);
			}

			java.util.Date fromDate = new java.util.Date(formationCourse.getStartDate().getTime());
			int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(fromDate));
			int fromMonth = Integer.parseInt(new SimpleDateFormat("MM").format(fromDate));
			int fromDay = Integer.parseInt(new SimpleDateFormat("dd").format(fromDate));
			fromDateTime.setDate(fromYear, fromMonth - 1, fromDay);

			java.util.Date toDate = new java.util.Date(formationCourse.getEndDate().getTime());
			int toYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(toDate));
			int toMonth = Integer.parseInt(new SimpleDateFormat("MM").format(toDate));
			int toDay = Integer.parseInt(new SimpleDateFormat("dd").format(toDate));
			toDateTime.setDate(toYear, toMonth - 1, toDay);

			externalQuantityText.setText(formationCourse.getExternalWorkersQuantity() != null ? formationCourse.getExternalWorkersQuantity().toString() : "");

			workerQuantityText.setText(formationCourse.getWorkersQuantity() != null ? formationCourse.getWorkersQuantity().toString() : "");

			if (getPerson() != null) {
				associate();
				showPerson();
			} else {
				dissociate();
				ajustRezise(personDataComposite, 0);
				refresh(personDataComposite.getParent().getShell());
			}
			// tableEnrollment.clearRows();
			// tableEnrollment.setRows(enrollmentService
			// .findByFormationCourse(formationCourse
			// .getFormationCourseID()));
			entities = enrollmentService.findByFormationCourse(formationCourse.getFormationCourseID());
			tableEnrollment.getPaginator().goToFirstPage();
		} else {
			// tablePerson.getPaginator().goToFirstPage();
		}

	}

	public EnrollmentSaveArea getEnrollmentSaveArea() {
		return enrollmentSaveArea;
	}

	public void setEnrollmentSaveArea(EnrollmentSaveArea enrollmentSaveArea) {
		this.enrollmentSaveArea = enrollmentSaveArea;
	}

	// @Override
	// public String getID() {
	// return null;
	// }

	// @Override
	// public Control getControl(String arg0) {
	// return null;
	// }

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

		// if (option == 1) {
		// registerFormationCourseLabel
		// .setText(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_FORMATION_COURSE));
		// } else {
		registerFormationCourseLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_UPDATE_FORMATION_COURSE));
		// }
		enrollmentListLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_ENROLLMENT));
		// personDataLabel
		// .setText(MessageUtil.unescape(AbosMessages.get().VIEW_PERSON));
		personGroup.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA));
		fullNameLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME) + " : ");
		userLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER) + " : ");
		birthdayLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_BIRTHDAY) + " : ");
		sexLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEX) + " : ");
		identificationLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION) + " : ");
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

		// saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		/*
		 * int positionCourseType = coursetypeCombo.getSelectionIndex();
		 * initialize(coursetypeCombo, ((LibraryViewController) controller)
		 * .findNomenclatorByCode(library.getLibraryID(),
		 * Nomenclator.COURSE_TYPE));
		 * coursetypeCombo.select(positionCourseType);
		 * 
		 * int positionRoom = roomCombo.getSelectionIndex();
		 * initialize(roomCombo, ((LibraryViewController) controller)
		 * .getAllManagementLibraryViewController().getRoomService()
		 * .findAll(library.getLibraryID())); roomCombo.select(positionRoom);
		 */
		saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		// if (option == 1) {
		// saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		// cancelBtn
		// .setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		// }
		// if (viewFormationCourseFragment != null) {
		// viewFormationCourseFragment.l10n();
		// }
		// initialize(coursetypeCombo,
		// ((LibraryViewController) controller)
		// .findNomenclatorByCode(Nomenclator.COURSE_TYPE));
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

	public void refreshTable() {
		tableEnrollment.clearRows();
		// tableEnrollment.setRows(entities);
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
