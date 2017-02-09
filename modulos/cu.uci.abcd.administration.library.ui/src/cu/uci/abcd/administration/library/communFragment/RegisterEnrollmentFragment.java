package cu.uci.abcd.administration.library.communFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Enrollment;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterEnrollmentFragment extends ContributorPage implements
		FragmentContributor {

	private ViewController controller;
	private Map<String, Control> controls = new HashMap<String, Control>();
	private PagePainter painter;
	private Composite parent;
	private Label enrollmentData;
	private Label adreesedTo;
	private Combo adreesedToCombo;
	private Label firstUbication;
	private Combo firstUbicationToCombo;
	private Label secondUbication;
	private Combo secondUbicationToCombo;
	private Label thirdUbication;
	private Combo thirdUbicationToCombo;
	private Label program;
	private Combo programCombo;
	private Label programName;
	private Text programNameText;
	private Label quantity;
	private Text quantityText;
	private Nomenclator pregradeStudent;
	private Nomenclator postgradeStudent;
	private Nomenclator professor;
	private Nomenclator investigator;
	private Nomenclator internalArea;
	private Nomenclator externalArea;
	private ValidatorUtils validator;
	private ValidatorUtils validatorPrincipal;
	private Nomenclator addressedTo;
	private Composite left;
	private Composite right;
	@SuppressWarnings("unused")
	private boolean isPostgradeEdit;
	private int dimension;
	private Enrollment enrolment;
	private Library library;
	private Composite msg;

	public RegisterEnrollmentFragment(Enrollment enrolment,
			ViewController controller, boolean isPostgradeEdit, int dimension) {
		this.controller = controller;
		this.isPostgradeEdit = isPostgradeEdit;
		this.dimension = dimension;
		this.enrolment = enrolment;
	}

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		painter = new FormPagePainter();
		validator = new ValidatorUtils(new CustomControlDecoration());
		validatorPrincipal = new ValidatorUtils(new CustomControlDecoration());
		painter.addComposite(shell);
		painter.setDimension(dimension);

		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");
		parent = new Composite(shell, SWT.NORMAL);
		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		msg = new Composite(parent, SWT.NONE);
		msg.setLayout(new FormLayout());
		msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(msg).atTopTo(parent, 10).atLeft(0).withHeight(50)
				.atRight(0);

		enrollmentData = new Label(parent, SWT.NONE);
		painter.addHeader(enrollmentData);
		@SuppressWarnings("unused")
		int derecha = msg.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		left = new Composite(parent, SWT.NONE);
		painter.addComposite(left, Percent.W50);
		left.setData(RWT.CUSTOM_VARIANT, "gray_background");
		adreesedTo = new Label(left, SWT.NONE);
		painter.add(adreesedTo);

		int width = left.getParent().getParent().getParent().getParent()
				.getBounds().width;

		FormDatas.attach(left).atLeft(10).atTopTo(enrollmentData)
				.withWidth((int) (width / 2.5));

		adreesedToCombo = new Combo(left, SWT.READ_ONLY);
		painter.add(adreesedToCombo);
		controls.put("adreesedToCombo", adreesedToCombo);
		FormDatas.attach(adreesedToCombo).atTopTo(left, 15)
				.atLeftTo(adreesedTo, 10).atRight(15).withWidth(width / 4)
				.withHeight(23);
		FormDatas.attach(adreesedTo).atRightTo(adreesedToCombo, 5).atLeft(5)
				.atTopTo(left, 20);
		painter.reset();
		quantity = new Label(left, SWT.NONE);
		painter.add(quantity);
		FormDatas.attach(quantity).atTopTo(adreesedToCombo, 15)
				.atRightTo(adreesedToCombo, 5);

		quantityText = new Text(left, SWT.NONE);
		painter.add(quantityText);
		controls.put("quantityText", quantityText);

		validatorPrincipal.applyValidator(quantityText, 5);
		validatorPrincipal.applyValidator(quantityText, "quantityText",
				DecoratorType.REQUIRED_FIELD, true);
		FormDatas.attach(quantityText).atTopTo(adreesedToCombo, 15)
				.atLeftTo(adreesedTo, 5).atRight(15).withHeight(13);
		validatorPrincipal.applyValidator(quantityText, "quantityNumber",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true);
		right = new Composite(parent, SWT.NONE);
		painter.addComposite(right, Percent.W50);
		controls.put("right", right);
		FormDatas.attach(right).atRight(15).atTopTo(enrollmentData)
				.atLeftTo(left);
		right.setData(RWT.CUSTOM_VARIANT, "gray_background");

		firstUbicationToCombo = new Combo(right, SWT.READ_ONLY);
		painter.add(firstUbicationToCombo);
		controls.put("firstUbicationToCombo", firstUbicationToCombo);
		FormDatas.attach(firstUbicationToCombo).atTopTo(right, 15).atRight(15)
				.withWidth((int) (width / 4.1)).withHeight(23);

		firstUbication = new Label(right, SWT.NONE | SWT.WRAP);
		controls.put("firstUbication", firstUbication);
		painter.add(firstUbication);
		FormDatas.attach(firstUbication).atTopTo(right, 15)
				.atRightTo(firstUbicationToCombo, 5);

		painter.reset();

		secondUbicationToCombo = new Combo(right, SWT.READ_ONLY);
		painter.add(secondUbicationToCombo);
		controls.put("secondUbicationToCombo", secondUbicationToCombo);
		FormDatas.attach(secondUbicationToCombo)
				.atTopTo(firstUbicationToCombo, 15).atRight(15)
				.atLeftTo(firstUbication, 5).withHeight(23);

		secondUbication = new Label(right, SWT.NONE | SWT.WRAP);
		controls.put("secondUbication", secondUbication);
		painter.add(secondUbication);
		FormDatas.attach(secondUbication).atTopTo(firstUbicationToCombo, 20)
				.atRightTo(secondUbicationToCombo, 5);

		painter.reset();

		thirdUbicationToCombo = new Combo(right, SWT.READ_ONLY);
		painter.add(thirdUbicationToCombo);
		controls.put("thirdUbicationToCombo", thirdUbicationToCombo);
		FormDatas.attach(thirdUbicationToCombo)
				.atTopTo(secondUbicationToCombo, 15)
				.atLeftTo(firstUbication, 5).atRight(15).withHeight(23);

		thirdUbication = new Label(right, SWT.NONE | SWT.WRAP);
		thirdUbication
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_STUDY_OR_INVESTIGATION_CENTER)
						+ "sf \n df");
		controls.put("thirdUbication", thirdUbication);
		painter.add(thirdUbication);
		FormDatas.attach(thirdUbication).atTopTo(secondUbicationToCombo, 20)
				.atRightTo(thirdUbicationToCombo, 5);

		painter.reset();

		programCombo = new Combo(right, SWT.READ_ONLY);
		painter.add(programCombo);
		controls.put("programCombo", programCombo);
		FormDatas.attach(programCombo).atTopTo(right, 15)
				.atLeftTo(firstUbication, 5).atRight(13).withHeight(23);

		program = new Label(right, SWT.NONE | SWT.WRAP);
		painter.add(program);
		controls.put("program", program);
		FormDatas.attach(program).atTopTo(right, 20).atRightTo(programCombo, 5);

		painter.reset();

		programNameText = new Text(right, SWT.NONE);
		painter.add(programNameText);
		controls.put("programNameText", programNameText);
		FormDatas.attach(programNameText).atTopTo(programCombo, 15)
				.atLeftTo(program, 5).atRight(15).withHeight(13);

		programName = new Label(right, SWT.NONE | SWT.WRAP);
		controls.put("programName", programName);
		painter.add(programName);
		FormDatas.attach(programName).atTopTo(programCombo, 20)
				.atRightTo(programNameText, 5);
		painter.reset();

		adreesedToCombo.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = -4186307840238390263L;

			public void widgetSelected(SelectionEvent arg0) {

				int selectedAddressedToUserTypeIndex = adreesedToCombo
						.getSelectionIndex();
				if (selectedAddressedToUserTypeIndex > 0) {
					addressedTo = (Nomenclator) UiUtils
							.getSelected(adreesedToCombo);
					loadAddressedtoData(addressedTo);

				} else {
					hideUbications();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		hideUbications();
		loadAreas();

		right.setSize(right.getSize().x, 125);
		FormData tempo = (FormData) right.getLayoutData();
		tempo.height = 125;
		right.setLayoutData(tempo);

		initialize(
				adreesedToCombo,
				((LibraryViewController) controller)
						.getAllManagementLibraryViewController()
						.getLibraryService()
						.findNomenclatorIdOrderByCode(library.getLibraryID(),
								Nomenclator.ADDRESSED_TO_USER_TYPE));

		validatorPrincipal.applyValidator(adreesedToCombo, "adreesedToCombo",
				DecoratorType.REQUIRED_FIELD, true);
		LoadEnrolmentDataData();
		l10n();

		return shell;
	}

	private void LoadEnrolmentDataData() {

		if (enrolment != null) {
			UiUtils.selectValue(adreesedToCombo, enrolment.getAddressedTo());
			quantityText.setText(enrolment.getQuantity().toString());
			Nomenclator nomenclator = (Nomenclator) UiUtils
					.getSelected(adreesedToCombo);
			loadAddressedtoData(nomenclator);
			UiUtils.selectValue(firstUbicationToCombo,
					enrolment.getFirstUbication());
			UiUtils.selectValue(secondUbicationToCombo,
					enrolment.getSecondUbication());
			UiUtils.selectValue(thirdUbicationToCombo,
					enrolment.getThirdUbication());
			UiUtils.selectValue(programCombo, enrolment.getProgramm());
			programNameText.setText((enrolment.getProgramName() == null) ? ""
					: enrolment.getProgramName().toString());
		}

	}

	@Override
	public String getID() {
		return null;
	}

	public void hideUbications() {
		firstUbication.setVisible(false);
		secondUbication.setVisible(false);
		thirdUbication.setVisible(false);
		program.setVisible(false);
		programName.setVisible(false);
		firstUbicationToCombo.setVisible(false);
		validator.decorationFactory
				.removeControlDecoration("firstUbicationToComboRequiredOne");
		secondUbicationToCombo.setVisible(false);
		validator.decorationFactory
				.removeControlDecoration("secondUbicationToComboRequiredOne");
		thirdUbicationToCombo.setVisible(false);
		validator.decorationFactory
				.removeControlDecoration("thirdUbicationToComboRequiredOne");
		programCombo.setVisible(false);
		validator.decorationFactory
				.removeControlDecoration("programComboRequiredOne");
		programNameText.setVisible(false);
		validator.decorationFactory
				.removeControlDecoration("programNameTextRequiredOne");
		validator.decorationFactory
				.removeControlDecoration("programNameTextRequiredOne1");

	}

	public void loadAreas() {
		pregradeStudent = ((LibraryViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorById(Nomenclator.PREGRADE_STUDENT);
		postgradeStudent = ((LibraryViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorById(Nomenclator.POSTGRADE_STUDENT);
		professor = ((LibraryViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorById(Nomenclator.PROFESSOR);
		investigator = ((LibraryViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorById(Nomenclator.INVESTIGATOR);
		internalArea = ((LibraryViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorById(Nomenclator.ADDRESSED_TO_AREA_INTERNAL);
		externalArea = ((LibraryViewController) controller)
				.getAllManagementLibraryViewController().getLibraryService()
				.findNomenclatorById(Nomenclator.ADDRESSED_TO_AREA_IXTERNAL);
	}

	public void loadAddressedtoData(Nomenclator addressedTo) {
		if (addressedTo != null) {
			firstUbication.setVisible(true);
			secondUbication.setVisible(true);
			thirdUbication.setVisible(true);
			program.setVisible(true);
			programName.setVisible(true);

			firstUbicationToCombo.clearSelection();
			secondUbicationToCombo.clearSelection();
			thirdUbicationToCombo.clearSelection();
			programCombo.clearSelection();
			programNameText.setText("");

			validator.decorationFactory.removeAllControlDecoration();

			if (addressedTo.equals(pregradeStudent)) {
				firstUbication
						.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FACULTY));
				secondUbication
						.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CARRERA));
				thirdUbication.setText("");
				program.setText("");
				programName.setText("");

				firstUbicationToCombo.setVisible(true);
				secondUbicationToCombo.setVisible(true);

				thirdUbicationToCombo.setVisible(false);
				programCombo.setVisible(false);
				programNameText.setVisible(false);
				thirdUbication.setVisible(false);
				program.setVisible(false);
				programName.setVisible(false);

				loadCombo(firstUbicationToCombo, Nomenclator.FACULTY);
				loadCombo(secondUbicationToCombo, Nomenclator.CARRERA);

				validator.applyValidator(firstUbicationToCombo,
						"firstUbicationToComboRequiredOne",
						DecoratorType.REQUIRED_FIELD, true);
				validator.applyValidator(secondUbicationToCombo,
						"secondUbicationToComboRequiredOne",
						DecoratorType.REQUIRED_FIELD, true);

				right.layout(true, true);
				right.redraw();
				right.update();

			} else {
				if (addressedTo.equals(postgradeStudent)) {

					thirdUbication.setText("");
					program.setVisible(true);
					program.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PROGRAM_TYPE));
					programName
							.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PROGRAM_NAME));

					firstUbicationToCombo.setVisible(false);
					secondUbicationToCombo.setVisible(false);
					thirdUbicationToCombo.setVisible(false);

					firstUbication.setVisible(false);
					secondUbication.setVisible(false);
					thirdUbication.setVisible(false);

					programCombo.setVisible(true);
					programNameText.setVisible(true);

					loadCombo(programCombo, Nomenclator.PROGRAM);

					validator.applyValidator(programCombo,
							"programComboRequiredOne",
							DecoratorType.REQUIRED_FIELD, true);

					validator.applyValidator(programNameText,
							"programNameTextRequiredOne",
							DecoratorType.REQUIRED_FIELD, true);

					validator.applyValidator(programNameText,
							"programNameTextRequiredOne1",
							DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);

					right.layout(true, true);
					right.redraw();
					right.update();

				} else {
					if (addressedTo.equals(professor)) {

						firstUbication.setText(MessageUtil
								.unescape(AbosMessages.get().LABEL_FACULTY));
						secondUbication.setText(MessageUtil
								.unescape(AbosMessages.get().LABEL_DEPARTMENT));
						thirdUbication.setVisible(true);
						thirdUbication
								.setText(MessageUtil.unescape(AbosMessages
										.get().LABEL_STUDY_OR_INVESTIGATION_CENTER));
						program.setText("");
						programName.setText("");

						firstUbicationToCombo.setVisible(true);
						secondUbicationToCombo.setVisible(true);
						thirdUbicationToCombo.setVisible(true);

						program.setVisible(false);
						programName.setVisible(false);
						programCombo.setVisible(false);
						programNameText.setVisible(false);

						loadCombo(firstUbicationToCombo, Nomenclator.FACULTY);
						loadCombo(secondUbicationToCombo,
								Nomenclator.DEPARTMENT);
						List<Nomenclator> listInvestigationCenter = ((LibraryViewController) controller)
								.findNomenclatorByCode(library.getLibraryID(),
										Nomenclator.INVESTIGATION_CENTER);
						List<Nomenclator> listStudyCenter = ((LibraryViewController) controller)
								.findNomenclatorByCode(library.getLibraryID(),
										Nomenclator.STUDY_CENTER);
						listInvestigationCenter.addAll(listStudyCenter);
						loadCombo(thirdUbicationToCombo,
								listInvestigationCenter);

						validator.applyValidator(firstUbicationToCombo,
								"firstUbicationToComboRequiredOne",
								DecoratorType.REQUIRED_FIELD, true);
						validator.applyValidator(secondUbicationToCombo,
								"secondUbicationToComboRequiredOne",
								DecoratorType.REQUIRED_FIELD, true);
						validator.applyValidator(thirdUbicationToCombo,
								"thirdUbicationToComboRequiredOne",
								DecoratorType.REQUIRED_FIELD, true);

						firstUbicationToCombo.setVisible(true);
						secondUbicationToCombo.setVisible(true);
						thirdUbicationToCombo.setVisible(true);

						firstUbication.setVisible(true);

						right.layout(true, true);
						right.redraw();
						right.update();

					} else {
						if (addressedTo.equals(investigator)) {

							firstUbication
									.setText(MessageUtil.unescape(AbosMessages
											.get().LABEL_FACULTY));
							secondUbication
									.setText(MessageUtil.unescape(AbosMessages
											.get().LABEL_INVESTIGATION_CENTER));
							thirdUbication.setText("");
							program.setText("");
							programName.setText("");

							firstUbicationToCombo.setVisible(true);
							secondUbicationToCombo.setVisible(true);

							thirdUbicationToCombo.setVisible(false);
							programCombo.setVisible(false);
							programNameText.setVisible(false);
							thirdUbication.setVisible(false);
							program.setVisible(false);
							programName.setVisible(false);

							loadCombo(firstUbicationToCombo,
									Nomenclator.FACULTY);
							loadCombo(secondUbicationToCombo,
									Nomenclator.INVESTIGATION_CENTER);

							validator.applyValidator(firstUbicationToCombo,
									"firstUbicationToComboRequiredOne",
									DecoratorType.REQUIRED_FIELD, true);
							validator.applyValidator(secondUbicationToCombo,
									"secondUbicationToComboRequiredOne",
									DecoratorType.REQUIRED_FIELD, true);

							right.layout(true, true);
							right.redraw();
							right.update();

						} else {
							firstUbication
									.setText(MessageUtil.unescape(AbosMessages
											.get().LABEL_UBICATION));
							secondUbication.setText("");
							thirdUbication.setText("");
							program.setText("");
							programName.setText("");

							firstUbicationToCombo.setVisible(true);

							secondUbicationToCombo.setVisible(false);
							thirdUbicationToCombo.setVisible(false);
							programCombo.setVisible(false);
							programNameText.setVisible(false);
							program.setVisible(false);
							programName.setVisible(false);

							List<Nomenclator> listArea = new Vector<Nomenclator>();

							listArea.add(internalArea);
							listArea.add(externalArea);

							loadCombo(firstUbicationToCombo, listArea);

							validator.applyValidator(firstUbicationToCombo,
									"firstUbicationToComboRequiredOne",
									DecoratorType.REQUIRED_FIELD, true);

							right.layout(true, true);
							right.redraw();
							right.update();

						}
					}
				}

			}

			right.layout(true, true);
			right.redraw();
			right.update();
		}
	}

	private void loadCombo(Combo combo, List<Nomenclator> list) {
		initialize(combo, list);
	}

	private void loadCombo(Combo combo, long nomenclatorType) {
		initialize(combo,
				((LibraryViewController) controller).findNomenclatorByCode(
						library.getLibraryID(), nomenclatorType));
	}

	@Override
	public void l10n() {
		enrollmentData
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ENROLLMENT_DATA));
		adreesedTo
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DIRECTED_TO));
		program.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PROGRAM_TYPE));
		programName
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PROGRAM_NAME));
		quantity.setText(MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY));
		loadAddressedtoData(addressedTo);
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public Map<String, Control> getControls() {
		return controls;
	}

	public void setControls(Map<String, Control> controls) {
		this.controls = controls;
	}

	public ValidatorUtils getValidatorPrincipal() {
		return validatorPrincipal;
	}

	public void setValidatorPrincipal(ValidatorUtils validatorPrincipal) {
		this.validatorPrincipal = validatorPrincipal;
	}

	public Composite getMsg() {
		return msg;
	}

	public void setMsg(Composite msg) {
		this.msg = msg;
	}
}
