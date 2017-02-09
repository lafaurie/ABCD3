package cu.uci.abcd.circulation.ui.auxiliary;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.RegisterLoanUser;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.listener.EventEditLoanUserType;
import cu.uci.abcd.circulation.ui.model.EditorAreaUserLoan;
import cu.uci.abcd.domain.circulation.CirculationNomenclator;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

public class LoanUserFragment extends ContributorPage implements
		FragmentContributor {

	private LoanUser loanUser;
	private Map<String, Control> controls = new HashMap<String, Control>();

	private Composite compoLoanUserTypeStudents;
	private Composite compoLoanUserTypeProfessor;
	private Composite compoLoanUserTypePostgraduate;
	private Composite compoLoanUserTypeLibrarian;
	private Composite compoLoanUserTypeLoanInterLibrarian;

	private Label lbUserCode;
	private Label lbRoom;
	private Label lbRoomLibrarian;
	private Label lbUserType;
	private Combo comboFaculty;
	private Combo comboFaculty1;
	private Combo comboFaculty2;
	private Combo comboDepartment;
	private Combo comboSpecialty;
	private Combo comboSpecialty2;
	private Combo comboModality;
	private Label lbUniversity;
	private Label lbModality;
	private Label lbFaculty;
	private Label lbFaculty1;
	private Label lbFaculty2;
	private Label lbSpecialty;
	private Label lbSpecialty2;
	private Label lbObservation;
	private Label lbObservation1;
	private Label lbObservation2;
	private Label lbObservationLibrarian;
	private Label lbUniversity1;
	private Label lbUniversity2;
	private Label lbUniversity3;
	private Label lbDepartment;
	private Label lbUserLoanData;
	private Label lbObservationOther;
	private Combo comboUserType;
	private Composite registerLoanUser;
	private Combo comboRoom;
	private Combo comboRoomLibrarian;
	private Label lbValidUp;
	private DateTime dateTime;
	private String orderByString = "firstName";
	private int direction = 1024;
	private Text txtObservations;
	private Text txtUniversity;
	private Text txtUniversity1;
	private Text txtUniversity2;
	private Text txtUniversity3;
	private Text txtObservations1;
	private Text txtObservations2;
	private Text txtUserCode;
	private Label lbObservationInterLibrarian;
	private Text txtObservationInterLibrarian;
	private Composite compoOther;
	private Text txtObservationsOther;
	private Text txtObservationLibrarian;
	private ValidatorUtils validator;
	private Label lbModalityEst;
	private Combo comboModalityEst;
	private int dimension;
	private Library library;
	private Label lbAuthorizingOfficial;
	private Text txtAuthorizingOfficial;
	private Label lbCountry;
	private Button rdbCuba;
	private Button rdbExternal;
	private Nomenclator loanUserTypeSelect;
	private boolean select = false;

	private Label lbRemarkDisableEst;
	private Text txtDisable;
	private Button btnDisable;

	private Label lbRemarkDisableProf;
	private Text txtDisable1;
	private Button btnDisable1;

	private Label lbRemarkDisableEstPost;
	private Text txtDisable2;
	private Button btnDisable2;

	private Label lbRemarkDisableOther;
	private Text txtDisableOT;
	private Button btnDisableOT;

	private Label lbRemarkDisableLybrarian;
	private Text txtDisableLybrarian;
	private Button btnDisableLybrarian;

	private Label lbRemarkDisableInterLybrarian;
	private Text txtDisableInterLybrarian;
	private Button btnDisableInterLybrarian;

	private EditorAreaUserLoan editorAreaUserLoan;
	private RegisterLoanUser registerLoanUserContrib;

	public LoanUserFragment(ViewController controller, LoanUser loanUser,
			int dimension) {
		this.controller = controller;
		this.loanUser = loanUser;
		this.dimension = dimension;
	}

	public LoanUserFragment(ViewController controller, LoanUser loanUser,
			int dimension, EditorAreaUserLoan editorAreaUserLoan) {
		this.controller = controller;
		this.loanUser = loanUser;
		this.dimension = dimension;
		this.editorAreaUserLoan = editorAreaUserLoan;
	}

	public LoanUserFragment(ViewController controller, LoanUser loanUser,
			int dimension, RegisterLoanUser registerLoanUser) {
		this.controller = controller;
		this.loanUser = loanUser;
		this.dimension = dimension;
		this.registerLoanUserContrib = registerLoanUser;
	}

	// FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(final Composite parent) {
		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");

		addComposite(parent);

		setDimension(dimension);

		validator = new ValidatorUtils(new CustomControlDecoration());

		registerLoanUser = new Composite(parent, SWT.NORMAL);
		addComposite(registerLoanUser);
		registerLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Label separador = new Label(registerLoanUser, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		addSeparator(separador);

		lbUserLoanData = new Label(registerLoanUser, SWT.WRAP);
		addHeader(lbUserLoanData);

		lbUserCode = new Label(registerLoanUser, SWT.WRAP);
		add(lbUserCode);
		txtUserCode = new Text(registerLoanUser, 0);
		controls.put("txtUserCode", txtUserCode);
		add(txtUserCode);
		validator.applyValidator(txtUserCode, "txtUserCode",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtUserCode, "txtUserCode1",
				DecoratorType.ALPHA_NUMERIC, true, 20);

		lbRoom = new Label(registerLoanUser, SWT.WRAP);
		add(lbRoom);
		comboRoom = new Combo(registerLoanUser, SWT.READ_ONLY);
		controls.put("comboRoom", comboRoom);
		add(comboRoom);
		validator.applyValidator(comboRoom, "comboRoom",
				DecoratorType.REQUIRED_FIELD, true);

		lbUserType = new Label(registerLoanUser, SWT.WRAP);
		add(lbUserType);
		comboUserType = new Combo(registerLoanUser, SWT.READ_ONLY);
		controls.put("comboUserType", comboUserType);
		add(comboUserType);
		validator.applyValidator(comboUserType, "comboUserType",
				DecoratorType.REQUIRED_FIELD, true);

		lbValidUp = new Label(registerLoanUser, SWT.WRAP);
		add(lbValidUp);

		dateTime = new DateTime(registerLoanUser, SWT.BORDER | SWT.DROP_DOWN);
		controls.put("dateTime", dateTime);

		add(dateTime);

		compoLoanUserTypeStudents = new Composite(parent, SWT.NORMAL);
		compoLoanUserTypeProfessor = new Composite(parent, SWT.NORMAL);
		compoLoanUserTypePostgraduate = new Composite(parent, SWT.NORMAL);
		compoLoanUserTypeLibrarian = new Composite(parent, SWT.NORMAL);
		compoLoanUserTypeLoanInterLibrarian = new Composite(parent, SWT.NORMAL);
		compoOther = new Composite(parent, SWT.NORMAL);

		comboUserType.addListener(SWT.Selection, new EventEditLoanUserType(
				comboUserType, registerLoanUser, compoLoanUserTypeStudents,
				compoLoanUserTypeProfessor, compoLoanUserTypePostgraduate,
				compoLoanUserTypeLibrarian,
				compoLoanUserTypeLoanInterLibrarian, compoOther, this));

		comboUserType.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (registerLoanUserContrib != null)
					registerLoanUserContrib.refresh();

				if (editorAreaUserLoan != null)
					editorAreaUserLoan.refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// ********Composite Loan User Type Students*************************

		compoLoanUserTypeStudents.setVisible(false);
		addComposite(compoLoanUserTypeStudents);
		compoLoanUserTypeStudents
				.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbUniversity = new Label(compoLoanUserTypeStudents, SWT.WRAP);
		add(lbUniversity);
		txtUniversity = new Text(compoLoanUserTypeStudents, SWT.NORMAL);
		controls.put("txtUniversity", txtUniversity);
		add(txtUniversity);
		validator.applyValidator(txtUniversity, "txtUniversity",
				DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);

		lbFaculty = new Label(compoLoanUserTypeStudents, SWT.WRAP);
		add(lbFaculty);
		comboFaculty = new Combo(compoLoanUserTypeStudents, SWT.READ_ONLY);
		controls.put("comboFaculty", comboFaculty);
		add(comboFaculty);

		lbSpecialty = new Label(compoLoanUserTypeStudents, SWT.WRAP);
		add(lbSpecialty);
		comboSpecialty = new Combo(compoLoanUserTypeStudents, SWT.READ_ONLY);
		controls.put("comboSpecialty", comboSpecialty);
		add(comboSpecialty);
		validator.applyValidator(comboSpecialty, "comboSpecialty",
				DecoratorType.REQUIRED_FIELD, true);

		lbModalityEst = new Label(compoLoanUserTypeStudents, SWT.WRAP);
		add(lbModalityEst);
		comboModalityEst = new Combo(compoLoanUserTypeStudents, SWT.READ_ONLY);
		controls.put("comboModalityEst", comboModalityEst);
		add(comboModalityEst);
		validator.applyValidator(comboModalityEst, "comboModalityEst",
				DecoratorType.REQUIRED_FIELD, true);

		lbObservation = new Label(compoLoanUserTypeStudents, SWT.WRAP);
		add(lbObservation);
		txtObservations = new Text(compoLoanUserTypeStudents, SWT.V_SCROLL
				| SWT.WRAP);
		controls.put("txtObservations", txtObservations);
		validator.applyValidator(txtObservations, 500);
		add(txtObservations, Percent.W45);

		br();

		add(new Label(compoLoanUserTypeStudents, 0), Percent.W75);

		btnDisable = new Button(compoLoanUserTypeStudents, SWT.CHECK);
		add(btnDisable);
		btnDisable.setVisible(false);

		br();

		lbRemarkDisableEst = new Label(compoLoanUserTypeStudents, SWT.WRAP);
		lbRemarkDisableEst.setVisible(false);
		add(lbRemarkDisableEst);
		txtDisable = new Text(compoLoanUserTypeStudents, SWT.V_SCROLL
				| SWT.WRAP);
		txtDisable.setVisible(false);
		controls.put("txtDisable", txtDisable);
		validator.applyValidator(txtDisable, "txtDisableEst",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtDisable, 500);
		add(txtDisable, Percent.W45);

		add(new Label(compoLoanUserTypeStudents, 0), Percent.W100);

		// *********Composite Loan User Type
		// Professor***************************************************

		compoLoanUserTypeProfessor.setVisible(false);
		addComposite(compoLoanUserTypeProfessor);
		compoLoanUserTypeProfessor.setData(RWT.CUSTOM_VARIANT,
				"gray_background");

		lbUniversity1 = new Label(compoLoanUserTypeProfessor, SWT.WRAP);
		add(lbUniversity1);
		txtUniversity1 = new Text(compoLoanUserTypeProfessor, SWT.NORMAL);
		controls.put("txtUniversity1", txtUniversity1);
		add(txtUniversity1);
		validator.applyValidator(txtUniversity1, "txtUniversity1",
				DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);

		lbDepartment = new Label(compoLoanUserTypeProfessor, SWT.WRAP);
		add(lbDepartment);
		comboDepartment = new Combo(compoLoanUserTypeProfessor, SWT.READ_ONLY);
		controls.put("comboDepartment", comboDepartment);
		add(comboDepartment);
		validator.applyValidator(comboDepartment, "comboDepartment",
				DecoratorType.REQUIRED_FIELD, true);

		br();

		lbFaculty1 = new Label(compoLoanUserTypeProfessor, SWT.WRAP);
		add(lbFaculty1);
		lbFaculty1
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FACULTY_OR_ADMINISTRATIVE_AREA));
		comboFaculty1 = new Combo(compoLoanUserTypeProfessor, SWT.READ_ONLY);
		controls.put("comboFaculty1", comboFaculty1);
		add(comboFaculty1);
		validator.applyValidator(comboFaculty1, "comboFaculty1",
				DecoratorType.REQUIRED_FIELD, true);

		add(new Label(compoLoanUserTypeProfessor, 0), Percent.W50);

		lbObservation1 = new Label(compoLoanUserTypeProfessor, SWT.WRAP);
		add(lbObservation1);
		txtObservations1 = new Text(compoLoanUserTypeProfessor, SWT.V_SCROLL
				| SWT.WRAP);
		controls.put("txtObservations1", txtObservations1);
		validator.applyValidator(txtObservations1, 500);
		add(txtObservations1, Percent.W45);

		add(new Label(compoLoanUserTypeProfessor, 0), Percent.W75);

		btnDisable1 = new Button(compoLoanUserTypeProfessor, SWT.CHECK);
		btnDisable1.setVisible(false);
		add(btnDisable1);

		br();

		lbRemarkDisableProf = new Label(compoLoanUserTypeProfessor, SWT.WRAP);
		lbRemarkDisableProf.setVisible(false);
		add(lbRemarkDisableProf);
		txtDisable1 = new Text(compoLoanUserTypeProfessor, SWT.V_SCROLL
				| SWT.WRAP);
		txtDisable1.setVisible(false);
		controls.put("txtDisable1", txtDisable1);
		validator.applyValidator(txtDisable1, "txtDisableProf",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtDisable1, 500);
		add(txtDisable1, Percent.W45);

		add(new Label(compoLoanUserTypeProfessor, 0), Percent.W100);
		refreshFragment();

		// **************Composite Loan User Type
		// Postgraduate*********************************

		compoLoanUserTypePostgraduate.setVisible(false);
		addComposite(compoLoanUserTypePostgraduate);
		compoLoanUserTypePostgraduate.setData(RWT.CUSTOM_VARIANT,
				"gray_background");

		lbModality = new Label(compoLoanUserTypePostgraduate, SWT.WRAP);
		add(lbModality);
		comboModality = new Combo(compoLoanUserTypePostgraduate, SWT.READ_ONLY);
		controls.put("comboModality", comboModality);
		add(comboModality);
		validator.applyValidator(comboModality, "comboModality",
				DecoratorType.REQUIRED_FIELD, true);

		lbFaculty2 = new Label(compoLoanUserTypePostgraduate, SWT.WRAP);
		add(lbFaculty2);
		comboFaculty2 = new Combo(compoLoanUserTypePostgraduate, SWT.READ_ONLY);
		controls.put("comboFaculty2", comboFaculty2);
		add(comboFaculty2);

		lbSpecialty2 = new Label(compoLoanUserTypePostgraduate, SWT.WRAP);
		add(lbSpecialty2);
		comboSpecialty2 = new Combo(compoLoanUserTypePostgraduate,
				SWT.READ_ONLY);
		controls.put("comboSpecialty2", comboSpecialty2);
		add(comboSpecialty2);
		validator.applyValidator(comboSpecialty2, "comboSpecialty2",
				DecoratorType.REQUIRED_FIELD, true);

		add(new Label(compoLoanUserTypePostgraduate, 0), Percent.W50);

		lbObservation2 = new Label(compoLoanUserTypePostgraduate, SWT.WRAP);
		add(lbObservation2);
		txtObservations2 = new Text(compoLoanUserTypePostgraduate, SWT.V_SCROLL
				| SWT.WRAP);
		controls.put("txtObservations2", txtObservations2);
		validator.applyValidator(txtObservations2, 500);
		add(txtObservations2, Percent.W45);

		add(new Label(compoLoanUserTypePostgraduate, 0), Percent.W75);

		btnDisable2 = new Button(compoLoanUserTypePostgraduate, SWT.CHECK);
		btnDisable2.setVisible(false);
		add(btnDisable2);

		br();

		lbRemarkDisableEstPost = new Label(compoLoanUserTypePostgraduate,
				SWT.WRAP);
		lbRemarkDisableEstPost.setVisible(false);
		add(lbRemarkDisableEstPost);
		txtDisable2 = new Text(compoLoanUserTypePostgraduate, SWT.V_SCROLL
				| SWT.WRAP);
		validator.applyValidator(txtDisable2, "txtDisableEstP",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtDisable2, 500);
		controls.put("txtDisable2", txtDisable2);
		add(txtDisable2, Percent.W45);
		txtDisable2.setVisible(false);

		add(new Label(compoLoanUserTypePostgraduate, 0), Percent.W100);

		// **************Composite Loan User Type
		// Librarian*********************************

		compoLoanUserTypeLibrarian.setVisible(false);
		addComposite(compoLoanUserTypeLibrarian);
		compoLoanUserTypeLibrarian.setData(RWT.CUSTOM_VARIANT,
				"gray_background");

		lbUniversity2 = new Label(compoLoanUserTypeLibrarian, SWT.WRAP);
		add(lbUniversity2);
		txtUniversity2 = new Text(compoLoanUserTypeLibrarian, SWT.NORMAL);
		controls.put("txtUniversity2", txtUniversity2);
		add(txtUniversity2);
		validator.applyValidator(txtUniversity2, "txtUniversity2",
				DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);

		lbRoomLibrarian = new Label(compoLoanUserTypeLibrarian, SWT.WRAP);
		add(lbRoomLibrarian);
		comboRoomLibrarian = new Combo(compoLoanUserTypeLibrarian,
				SWT.READ_ONLY);
		controls.put("comboRoomLibrarian", comboRoomLibrarian);
		add(comboRoomLibrarian);
		validator.applyValidator(comboRoomLibrarian, "comboRoomLibrarian",
				DecoratorType.REQUIRED_FIELD, true);

		lbObservationLibrarian = new Label(compoLoanUserTypeLibrarian, SWT.WRAP);
		add(lbObservationLibrarian);
		txtObservationLibrarian = new Text(compoLoanUserTypeLibrarian,
				SWT.V_SCROLL | SWT.WRAP);
		validator.applyValidator(txtObservationLibrarian, 500);
		controls.put("txtObservationLibrarian", txtObservationLibrarian);
		add(txtObservationLibrarian, Percent.W45);

		add(new Label(compoLoanUserTypeLibrarian, 0), Percent.W75);

		btnDisableLybrarian = new Button(compoLoanUserTypeLibrarian, SWT.CHECK);
		add(btnDisableLybrarian);
		btnDisableLybrarian.setVisible(false);

		br();

		lbRemarkDisableLybrarian = new Label(compoLoanUserTypeLibrarian,
				SWT.WRAP);
		lbRemarkDisableLybrarian.setVisible(false);
		add(lbRemarkDisableLybrarian);
		txtDisableLybrarian = new Text(compoLoanUserTypeLibrarian, SWT.V_SCROLL
				| SWT.WRAP);
		txtDisableLybrarian.setVisible(false);
		controls.put("txtDisableLybrarian", txtDisableLybrarian);
		validator.applyValidator(txtDisableLybrarian, "txtDisableLybrarian",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtDisableLybrarian, 500);
		add(txtDisableLybrarian, Percent.W45);

		add(new Label(compoLoanUserTypeLibrarian, 0), Percent.W100);

		// **************Composite Loan User Type Loan
		// InterLibrarian*********************************

		compoLoanUserTypeLoanInterLibrarian.setVisible(false);
		addComposite(compoLoanUserTypeLoanInterLibrarian);
		compoLoanUserTypeLoanInterLibrarian.setData(RWT.CUSTOM_VARIANT,
				"gray_background");

		lbUniversity3 = new Label(compoLoanUserTypeLoanInterLibrarian, SWT.WRAP);
		add(lbUniversity3);
		txtUniversity3 = new Text(compoLoanUserTypeLoanInterLibrarian,
				SWT.NORMAL);
		controls.put("txtUniversity3", txtUniversity3);
		add(txtUniversity3);
		validator.applyValidator(txtUniversity3, "txtUniversity3",
				DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);

		lbAuthorizingOfficial = new Label(compoLoanUserTypeLoanInterLibrarian,
				SWT.WRAP);
		add(lbAuthorizingOfficial);
		txtAuthorizingOfficial = new Text(compoLoanUserTypeLoanInterLibrarian,
				SWT.NONE);
		controls.put("txtAuthorizingOfficial", txtAuthorizingOfficial);
		add(txtAuthorizingOfficial);
		validator.applyValidator(txtAuthorizingOfficial,
				"txtAuthorizingOfficial", DecoratorType.REQUIRED_FIELD, true);
		validator
				.applyValidator(txtAuthorizingOfficial,
						"txtAuthorizingOfficial1", DecoratorType.ALPHA_SPACES,
						true, 50);

		if (dimension < 840) {
			lbCountry = new Label(compoLoanUserTypeLoanInterLibrarian, SWT.WRAP);
			add(lbCountry);
			br();
			rdbCuba = new Button(compoLoanUserTypeLoanInterLibrarian, SWT.RADIO);
			add(rdbCuba);
			rdbCuba.setSelection(true);
			controls.put("rdbCuba", rdbCuba);

			rdbExternal = new Button(compoLoanUserTypeLoanInterLibrarian,
					SWT.RADIO);
			add(rdbExternal);
			controls.put("rdbExternal", rdbExternal);
			br();

		} else {
			lbCountry = new Label(compoLoanUserTypeLoanInterLibrarian, SWT.WRAP);
			add(lbCountry);

			rdbCuba = new Button(compoLoanUserTypeLoanInterLibrarian, SWT.RADIO);
			add(rdbCuba);
			rdbCuba.setSelection(true);
			controls.put("rdbCuba", rdbCuba);

			rdbExternal = new Button(compoLoanUserTypeLoanInterLibrarian,
					SWT.RADIO);
			add(rdbExternal);
			controls.put("rdbExternal", rdbExternal);
			br();

		}
		lbObservationInterLibrarian = new Label(
				compoLoanUserTypeLoanInterLibrarian, SWT.WRAP);
		add(lbObservationInterLibrarian);
		txtObservationInterLibrarian = new Text(
				compoLoanUserTypeLoanInterLibrarian, SWT.V_SCROLL | SWT.WRAP);
		controls.put("txtObservationInterLibrarian",
				txtObservationInterLibrarian);
		validator.applyValidator(txtObservationInterLibrarian, 500);
		add(txtObservationInterLibrarian, Percent.W45);

		add(new Label(compoLoanUserTypeLoanInterLibrarian, 0), Percent.W75);

		btnDisableInterLybrarian = new Button(
				compoLoanUserTypeLoanInterLibrarian, SWT.CHECK);
		add(btnDisableInterLybrarian);
		btnDisableInterLybrarian.setVisible(false);

		br();
		lbRemarkDisableInterLybrarian = new Label(
				compoLoanUserTypeLoanInterLibrarian, SWT.WRAP);
		lbRemarkDisableInterLybrarian.setVisible(false);
		add(lbRemarkDisableInterLybrarian);
		txtDisableInterLybrarian = new Text(
				compoLoanUserTypeLoanInterLibrarian, SWT.V_SCROLL | SWT.WRAP);
		txtDisableInterLybrarian.setVisible(false);
		controls.put("txtDisableInterLybrarian", txtDisableInterLybrarian);
		validator.applyValidator(txtDisableInterLybrarian,
				"txtDisableInterLybrarian", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtDisableInterLybrarian, 500);
		add(txtDisableInterLybrarian, Percent.W45);

		add(new Label(compoLoanUserTypeLoanInterLibrarian, 0), Percent.W100);

		// ***************************Other***************************************************

		compoOther.setVisible(false);
		addComposite(compoOther);
		compoOther.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbObservationOther = new Label(compoOther, SWT.WRAP);
		add(lbObservationOther);
		txtObservationsOther = new Text(compoOther, SWT.V_SCROLL | SWT.WRAP);
		controls.put("txtObservationsOther", txtObservationsOther);
		validator.applyValidator(txtObservationsOther, 500);
		add(txtObservationsOther, Percent.W45);

		add(new Label(compoOther, 0), Percent.W75);

		btnDisableOT = new Button(compoOther, SWT.CHECK);
		btnDisableOT.setVisible(false);
		add(btnDisableOT);
		br();

		lbRemarkDisableOther = new Label(compoOther, SWT.WRAP);
		lbRemarkDisableOther.setVisible(false);
		add(lbRemarkDisableOther);
		txtDisableOT = new Text(compoOther, SWT.V_SCROLL | SWT.WRAP);
		txtDisableOT.setVisible(false);
		controls.put("txtDisableOT", txtDisableOT);
		validator.applyValidator(txtDisableOT, "txtDisableOT",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtDisableOT, 500);
		add(txtDisableOT, Percent.W45);

		add(new Label(compoOther, 0), Percent.W100);

		comboUserType.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				loanUserTypeSelect = (Nomenclator) UiUtils
						.getSelected(comboUserType);

				if (loanUser != null) {
					btnDisable.setVisible(true);
					btnDisable1.setVisible(true);
					btnDisable2.setVisible(true);
					btnDisableOT.setVisible(true);
					btnDisableInterLybrarian.setVisible(true);
					btnDisableLybrarian.setVisible(true);
				}
				if (loanUserTypeSelect != null) {

					if (loanUserTypeSelect.getNomenclatorID().equals(
							Nomenclator.LOANUSER_TYPE_INVESTIGATOR)) {
						lbFaculty1.setText(MessageUtil.unescape(AbosMessages
								.get().LABEL_INVESTIGATION_CENTER_OR_ADMINISTRATIVE_AREA));
						// lbFaculty1.pack();
						// refreshFragment();
						/*
						 * initialize( comboFaculty1,
						 * ((AllManagementLoanUserViewController) controller)
						 * .getManageLoanUser() .findByNomenclators(
						 * library.getLibraryID(),
						 * Nomenclator.CENTER_INVESTIGATION,
						 * Nomenclator.ADMINISTRATIVE_AREA));
						 */
					} else {
						lbFaculty1.setText(MessageUtil.unescape(AbosMessages
								.get().LABEL_FACULTY_OR_ADMINISTRATIVE_AREA));
						// refreshFragment();
						/*
						 * initialize( comboFaculty1,
						 * ((AllManagementLoanUserViewController) controller)
						 * .getManageLoanUser() .findByNomenclators(
						 * library.getLibraryID(), Nomenclator.FACULTY,
						 * Nomenclator.ADMINISTRATIVE_AREA));
						 */
					}

				}
				if (editorAreaUserLoan != null)
					editorAreaUserLoan.refresh();

			}
		});

		l10n();

		LoadLoanUserData();

		validator.applyRangeDateValidator(dateTime, "dateTime",
				DecoratorType.DATE_RANGE, 0, 0, 0, 50, 0, 0, true);

		return parent;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	@Override
	public String getID() {

		return null;
	}

	@Override
	public void l10n() {
		lbUserLoanData
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA));
		lbRoom.setText(AbosMessages.get().LABEL_ROOM);
		lbValidUp
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		lbUserType.setText(AbosMessages.get().LABEL_TYPE_OF_USER);
		lbUserCode
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));

		lbObservationOther.setText(AbosMessages.get().LABEL_OBSERVATIONS);

		lbUniversity.setText(AbosMessages.get().LABEL_CENTER);
		lbFaculty.setText(AbosMessages.get().LABEL_FACULTY);
		lbSpecialty.setText(AbosMessages.get().LABEL_SPECIALTY);
		lbObservation.setText(AbosMessages.get().LABEL_OBSERVATIONS);

		lbUniversity1.setText(AbosMessages.get().LABEL_CENTER);
		lbDepartment.setText(AbosMessages.get().LABEL_DEPARTMENT_OR_AGENCY);
		lbObservation1.setText(AbosMessages.get().LABEL_OBSERVATIONS);

		lbModality.setText(AbosMessages.get().LABEL_NAME_COURSE);
		lbModalityEst.setText(AbosMessages.get().LABEL_MODALITY);
		lbFaculty2.setText(AbosMessages.get().LABEL_FACULTY);
		lbSpecialty2.setText(AbosMessages.get().LABEL_SPECIALTY);
		lbObservation2.setText(AbosMessages.get().LABEL_OBSERVATIONS);

		lbRoomLibrarian.setText(AbosMessages.get().LABEL_ROOM_OR_AGENCY);
		lbUniversity2.setText(AbosMessages.get().LABEL_CENTER);
		lbObservationLibrarian.setText(AbosMessages.get().LABEL_OBSERVATIONS);

		lbUniversity3.setText(AbosMessages.get().LABEL_CENTER);
		lbCountry
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COUNTRY));
		lbAuthorizingOfficial.setText(AbosMessages.get().LABEL_AUTHORIZING);

		lbObservationInterLibrarian
				.setText(AbosMessages.get().LABEL_OBSERVATIONS);

		rdbCuba.setText(AbosMessages.get().LABEL_CUBA);
		rdbExternal
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FOREIGN));

		btnDisable.setText(AbosMessages.get().LABEL_DISABLE);
		lbRemarkDisableEst.setText(AbosMessages.get().LABEL_REASON);

		lbRemarkDisableProf.setText(AbosMessages.get().LABEL_REASON);
		btnDisable1.setText(AbosMessages.get().LABEL_DISABLE);

		lbRemarkDisableEstPost.setText(AbosMessages.get().LABEL_REASON);
		btnDisable2.setText(AbosMessages.get().LABEL_DISABLE);

		btnDisableLybrarian.setText(AbosMessages.get().LABEL_DISABLE);
		lbRemarkDisableLybrarian.setText(AbosMessages.get().LABEL_REASON);

		lbRemarkDisableOther.setText(AbosMessages.get().LABEL_REASON);
		btnDisableOT.setText(AbosMessages.get().LABEL_DISABLE);

		btnDisableInterLybrarian.setText(AbosMessages.get().LABEL_DISABLE);
		lbRemarkDisableInterLybrarian.setText(AbosMessages.get().LABEL_REASON);

		initialize(
				comboUserType,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findByNomenclator(
								library.getLibraryID(),
								Nomenclator.LOANUSER_TYPE));
		initialize(
				comboFaculty,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findByNomenclator(
								library.getLibraryID(), Nomenclator.FACULTY));

		initialize(
				comboFaculty1,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findByNomenclators(
								library.getLibraryID(), Nomenclator.FACULTY,
								Nomenclator.ADMINISTRATIVE_AREA));

		initialize(
				comboFaculty2,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findByNomenclator(
								library.getLibraryID(), Nomenclator.FACULTY));
		initialize(
				comboModalityEst,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findByNomenclator(
								library.getLibraryID(),
								Nomenclator.MODALITY_STUDENT));
		initialize(
				comboModality,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findByNomenclator(
								library.getLibraryID(),
								Nomenclator.MODALITY_STUDENT_POSTGRADUATE));

		initialize(
				comboDepartment,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findByNomenclators(
								library.getLibraryID(), Nomenclator.DEPARTMENT,
								Nomenclator.DEPENDENCE));
		initialize(
				comboRoomLibrarian,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findRoomByLibrary(
								library.getLibraryID()));

		initialize(
				comboSpecialty,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findByNomenclator(
								library.getLibraryID(), Nomenclator.SPECIALITY));
		initialize(
				comboSpecialty2,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findByNomenclator(
								library.getLibraryID(), Nomenclator.SPECIALITY));
		initialize(
				comboRoom,
				((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findRoomByLibrary(
								library.getLibraryID()));

		if (loanUser != null) {

			UiUtils.selectValue(comboRoom, loanUser.getRegisteredatroom());
			UiUtils.selectValue(comboUserType, loanUser.getLoanUserType());

			if (loanUser.getLoanUserType().getNomenclatorID()
					.equals(Nomenclator.LOANUSER_TYPE_STUDENT)) {

				if (loanUser.getFaculty() != null) {
					UiUtils.selectValue(comboFaculty, loanUser.getFaculty());

				}
				UiUtils.selectValue(comboSpecialty, loanUser.getSpeciality());
				UiUtils.selectValue(comboModalityEst, loanUser.getModality());

			}

			if (loanUser.getLoanUserType().getNomenclatorID()
					.equals(CirculationNomenclator.LOANUSER_TYPE_PROFESOR)
					|| loanUser
							.getLoanUserType()
							.getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_EXECUTIVE)
					|| loanUser
							.getLoanUserType()
							.getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_OTHER_WORKERS)) {

				UiUtils.selectValue(comboFaculty1, loanUser.getFaculty());
				UiUtils.selectValue(comboDepartment, loanUser.getDepartment());
			}

			if (loanUser.getLoanUserType().getNomenclatorID()
					.equals(CirculationNomenclator.LOANUSER_TYPE_INVESTIGATOR)) {

				UiUtils.selectValue(comboFaculty1, loanUser.getFaculty());
				UiUtils.selectValue(comboDepartment, loanUser.getDepartment());

			}

			if (loanUser.getLoanUserType().getNomenclatorID()
					.equals(Nomenclator.LOANUSER_TYPE_POSTGRADUATE)) {

				UiUtils.selectValue(comboModality, loanUser.getModality());
				if (loanUser.getFaculty() != null) {
					UiUtils.selectValue(comboFaculty2, loanUser.getFaculty());
				}
				UiUtils.selectValue(comboSpecialty2, loanUser.getSpeciality());
			}

			if (loanUser.getLoanUserType().getNomenclatorID()
					.equals(Nomenclator.LOANUSER_TYPE_LIBRARIAN)) {

				UiUtils.selectValue(comboRoomLibrarian,
						loanUser.getRoomlibrarian());

			}
		}
	}

	@Override
	public Control getControl(String arg0) {

		return null;
	}

	public Map<String, Control> getControls() {
		return controls;
	}

	public void setControls(Map<String, Control> controls) {
		this.controls = controls;
	}

	@Override
	public String contributorName() {
		return null;
	}

	public void LoadLoanUserData() {
		if (loanUser != null) {

			txtUserCode.setText(loanUser.getLoanUserCode());

			UiUtils.initialize(
					comboRoom,
					((AllManagementLoanUserViewController) controller)
							.getManageLoanUser().findRoomByLibrary(
									library.getLibraryID()));
			UiUtils.selectValue(comboRoom, loanUser.getRegisteredatroom());

			int year1 = Integer.parseInt(new SimpleDateFormat("yyyy")
					.format(loanUser.getExpirationDate()));
			int month1 = Integer.parseInt(new SimpleDateFormat("MM")
					.format(loanUser.getExpirationDate()));
			int day1 = Integer.parseInt(new SimpleDateFormat("dd")
					.format(loanUser.getExpirationDate()));
			dateTime.setDate(year1, month1 - 1, day1);

			if (loanUser.getLoanUserType().getNomenclatorID()
					.equals(Nomenclator.LOANUSER_TYPE_STUDENT)) {
				btnDisable.setVisible(true);

				txtUniversity.setText(loanUser.getUniversity());

				UiUtils.initialize(
						comboFaculty,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser().findByNomenclator(
										library.getLibraryID(),
										Nomenclator.FACULTY));
				if (loanUser.getFaculty() != null) {
					UiUtils.selectValue(comboFaculty, loanUser.getFaculty());

				}

				UiUtils.initialize(
						comboSpecialty,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser().findByNomenclator(
										library.getLibraryID(),
										Nomenclator.SPECIALITY));
				UiUtils.selectValue(comboSpecialty, loanUser.getSpeciality());

				UiUtils.initialize(
						comboModalityEst,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser().findByNomenclator(
										library.getLibraryID(),
										Nomenclator.MODALITY_STUDENT));
				UiUtils.selectValue(comboModalityEst, loanUser.getModality());

				if (loanUser.getRemarks().length() != 0) {
					txtObservations.setText(loanUser.getRemarks());
				} else
					txtObservations.setText("");
			}

			if (loanUser.getLoanUserType().getNomenclatorID()
					.equals(CirculationNomenclator.LOANUSER_TYPE_PROFESOR)
					|| loanUser
							.getLoanUserType()
							.getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_EXECUTIVE)
					|| loanUser
							.getLoanUserType()
							.getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_OTHER_WORKERS)) {
				btnDisable1.setVisible(true);
				lbFaculty1
						.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FACULTY_OR_ADMINISTRATIVE_AREA));
				txtUniversity1.setText(loanUser.getUniversity());

				UiUtils.initialize(
						comboFaculty1,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser().findByNomenclators(
										library.getLibraryID(),
										Nomenclator.FACULTY,
										Nomenclator.ADMINISTRATIVE_AREA));
				UiUtils.selectValue(comboFaculty1, loanUser.getFaculty());

				UiUtils.initialize(
						comboDepartment,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser().findByNomenclator(
										library.getLibraryID(),
										Nomenclator.DEPARTMENT));
				UiUtils.selectValue(comboDepartment, loanUser.getDepartment());

				if (loanUser.getRemarks().length() != 0) {
					txtObservations1.setText(loanUser.getRemarks());
				} else
					txtObservations1.setText("");
			}

			if (loanUser.getLoanUserType().getNomenclatorID()
					.equals(CirculationNomenclator.LOANUSER_TYPE_INVESTIGATOR)) {
				btnDisable1.setVisible(true);
				lbFaculty1
						.setText(MessageUtil.unescape(AbosMessages.get().LABEL_INVESTIGATION_CENTER_OR_ADMINISTRATIVE_AREA));
				txtUniversity1.setText(loanUser.getUniversity());

				UiUtils.initialize(
						comboFaculty1,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser().findByNomenclators(
										library.getLibraryID(),
										Nomenclator.CENTER_INVESTIGATION,
										Nomenclator.ADMINISTRATIVE_AREA));
				UiUtils.selectValue(comboFaculty1, loanUser.getFaculty());

				UiUtils.initialize(
						comboDepartment,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser().findByNomenclator(
										library.getLibraryID(),
										Nomenclator.DEPARTMENT));
				UiUtils.selectValue(comboDepartment, loanUser.getDepartment());

				if (loanUser.getRemarks().length() != 0) {
					txtObservations1.setText(loanUser.getRemarks());
				} else
					txtObservations1.setText("");
			}

			if (loanUser.getLoanUserType().getNomenclatorID()
					.equals(Nomenclator.LOANUSER_TYPE_POSTGRADUATE)) {
				btnDisable2.setVisible(true);

				UiUtils.initialize(
						comboModality,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser()
								.findByNomenclator(
										library.getLibraryID(),
										Nomenclator.MODALITY_STUDENT_POSTGRADUATE));
				UiUtils.selectValue(comboModality, loanUser.getModality());

				UiUtils.initialize(
						comboFaculty2,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser().findByNomenclator(
										library.getLibraryID(),
										Nomenclator.FACULTY));
				if (loanUser.getFaculty() != null) {
					UiUtils.selectValue(comboFaculty2, loanUser.getFaculty());
				}

				UiUtils.initialize(
						comboSpecialty2,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser().findByNomenclator(
										library.getLibraryID(),
										Nomenclator.SPECIALITY));
				UiUtils.selectValue(comboSpecialty2, loanUser.getSpeciality());

				if (loanUser.getRemarks().length() != 0) {
					txtObservations2.setText(loanUser.getRemarks());
				} else
					txtObservations2.setText("");

			}

			if (loanUser.getLoanUserType().getNomenclatorID()
					.equals(Nomenclator.LOANUSER_TYPE_LIBRARIAN)) {
				btnDisableLybrarian.setVisible(true);

				UiUtils.initialize(
						comboRoomLibrarian,
						((AllManagementLoanUserViewController) controller)
								.getManageLoanUser().findRoomByLibrary(
										library.getLibraryID()));
				UiUtils.selectValue(comboRoomLibrarian,
						loanUser.getRoomlibrarian());

				if (loanUser.getUniversity().length() != 0) {
					txtUniversity2.setText(loanUser.getUniversity());
				} else
					txtUniversity2.setText("");

				if (loanUser.getRemarks().length() != 0) {
					txtObservationLibrarian.setText(loanUser.getRemarks());
				} else
					txtObservationLibrarian.setText("");
			}

			if (loanUser.getLoanUserType().getNomenclatorID() == Nomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY) {
				btnDisableInterLybrarian.setVisible(true);
				txtAuthorizingOfficial.setText(loanUser
						.getAuthorizingOfficial());

				if (loanUser.getUniversity().length() != 0) {
					txtUniversity3.setText(loanUser.getUniversity());
				} else
					txtUniversity3.setText("");

				if (loanUser.getRemarks().length() != 0) {
					txtObservationInterLibrarian.setText(loanUser.getRemarks());
				} else
					txtObservationInterLibrarian.setText("");

				if (loanUser.getCountry().equals(AbosMessages.get().LABEL_CUBA)) {
					rdbCuba.setSelection(true);
					rdbExternal.setSelection(false);
				} else if (loanUser.getCountry().equals(
						AbosMessages.get().LABEL_EXTERNAL)) {
					rdbExternal.setSelection(true);
					rdbCuba.setSelection(false);
				}
			}

			if (!(loanUser.getLoanUserType().getNomenclatorID()
					.equals(CirculationNomenclator.LOANUSER_TYPE_STUDENT))
					|| !(loanUser.getLoanUserType().getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_PROFESOR))
					|| !(loanUser.getLoanUserType().getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_POSTGRADUATE))
					|| !(loanUser.getLoanUserType().getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_LIBRARIAN))
					|| !(loanUser.getLoanUserType().getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY))
					|| !(loanUser.getLoanUserType().getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_INVESTIGATOR))) {

				btnDisableOT.setVisible(true);

				if (loanUser.getRemarks().length() != 0) {
					txtObservationsOther.setText(loanUser.getRemarks());
				}
			}

			if (loanUser.getLoanUserState().getNomenclatorID() == Nomenclator.LOANUSER_STATE_INACTIVE) {
				lbRemarkDisableEst.setVisible(true);
				txtDisable.setVisible(true);
				btnDisable.setSelection(true);

				lbRemarkDisableProf.setVisible(true);
				txtDisable1.setVisible(true);
				btnDisable1.setSelection(true);

				lbRemarkDisableEstPost.setVisible(true);
				txtDisable2.setVisible(true);
				btnDisable2.setSelection(true);

				txtDisableOT.setVisible(true);
				lbRemarkDisableOther.setVisible(true);
				btnDisableOT.setSelection(true);

				txtDisableInterLybrarian.setVisible(true);
				lbRemarkDisableInterLybrarian.setVisible(true);
				btnDisableInterLybrarian.setSelection(true);

				txtDisableLybrarian.setVisible(true);
				lbRemarkDisableLybrarian.setVisible(true);
				btnDisableLybrarian.setSelection(true);

				if (loanUser.getDisabilitationremarks().equals("")
						|| loanUser.getDisabilitationremarks() == null) {
					txtDisable.setText("");
					txtDisable1.setText("");
					txtDisable2.setText("");
					txtDisableOT.setText("");
					txtDisableInterLybrarian.setText("");
					txtDisableLybrarian.setText("");
				} else {
					txtDisable.setText(loanUser.getDisabilitationremarks());
					txtDisable1.setText(loanUser.getDisabilitationremarks());
					txtDisable2.setText(loanUser.getDisabilitationremarks());
					txtDisableOT.setText(loanUser.getDisabilitationremarks());
					txtDisableInterLybrarian.setText(loanUser
							.getDisabilitationremarks());
					txtDisableLybrarian.setText(loanUser
							.getDisabilitationremarks());
				}

			}

			btnDisable.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btnDisable.getSelection() == false) {
						txtDisable.setVisible(false);
						lbRemarkDisableEst.setVisible(false);
						select = false;
					} else {
						txtDisable.setVisible(true);
						lbRemarkDisableEst.setVisible(true);
						select = true;
					}
				}
			});

			btnDisable1.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btnDisable1.getSelection() == false) {
						lbRemarkDisableProf.setVisible(false);
						txtDisable1.setVisible(false);
						select = false;
					} else {
						lbRemarkDisableProf.setVisible(true);
						txtDisable1.setVisible(true);
						select = true;
						refreshFragment();
					}
				}
			});

			btnDisable2.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btnDisable2.getSelection() == false) {
						lbRemarkDisableEstPost.setVisible(false);
						txtDisable2.setVisible(false);
						select = false;
					} else {
						lbRemarkDisableEstPost.setVisible(true);
						txtDisable2.setVisible(true);
						select = true;
					}
				}
			});

			btnDisableLybrarian.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btnDisableLybrarian.getSelection() == false) {
						lbRemarkDisableLybrarian.setVisible(false);
						txtDisableLybrarian.setVisible(false);
						select = false;
					} else {
						lbRemarkDisableLybrarian.setVisible(true);
						txtDisableLybrarian.setVisible(true);
						select = true;
					}
				}
			});

			btnDisableInterLybrarian
					.addSelectionListener(new SelectionAdapter() {
						private static final long serialVersionUID = 1L;

						@Override
						public void widgetSelected(SelectionEvent e) {
							if (btnDisableInterLybrarian.getSelection() == false) {
								lbRemarkDisableInterLybrarian.setVisible(false);
								txtDisableInterLybrarian.setVisible(false);
								select = false;
							} else {
								lbRemarkDisableInterLybrarian.setVisible(true);
								txtDisableInterLybrarian.setVisible(true);
								select = true;
							}
						}
					});

			btnDisableOT.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btnDisableOT.getSelection() == false) {
						lbRemarkDisableOther.setVisible(false);
						txtDisableOT.setVisible(false);
						select = false;
					} else {
						txtDisableOT.setVisible(true);
						lbRemarkDisableOther.setVisible(true);
						select = true;
					}
				}
			});

			UiUtils.initialize(
					comboUserType,
					((AllManagementLoanUserViewController) controller)
							.getManageLoanUser().findByNomenclator(
									library.getLibraryID(),
									Nomenclator.LOANUSER_TYPE));
			UiUtils.selectValue(comboUserType, loanUser.getLoanUserType());

			Nomenclator loanUserType = (Nomenclator) UiUtils
					.getSelected(comboUserType);

			if (loanUserType.getNomenclatorID().equals(
					CirculationNomenclator.LOANUSER_TYPE_STUDENT)) {
				compoLoanUserTypeStudents.setVisible(true);
				insertComposite(compoLoanUserTypeStudents, registerLoanUser);

				compoLoanUserTypeProfessor.setVisible(false);
				insertComposite(compoLoanUserTypeProfessor, registerLoanUser);

				compoLoanUserTypePostgraduate.setVisible(false);
				insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);

				compoLoanUserTypeLibrarian.setVisible(false);
				insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);

				compoLoanUserTypeLoanInterLibrarian.setVisible(false);
				insertComposite(compoLoanUserTypeLoanInterLibrarian,
						registerLoanUser);

				compoOther.setVisible(false);
				insertComposite(compoOther, registerLoanUser);

				compoLoanUserTypeStudents.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeStudents.getShell().getShell().redraw();
				compoLoanUserTypeStudents.getShell().getShell().update();

				compoLoanUserTypeProfessor.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeProfessor.getShell().getShell().redraw();
				compoLoanUserTypeProfessor.getShell().getShell().update();

				compoLoanUserTypePostgraduate.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypePostgraduate.getShell().getShell().redraw();
				compoLoanUserTypePostgraduate.getShell().getShell().update();

				compoLoanUserTypeLibrarian.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeLibrarian.getShell().getShell().redraw();
				compoLoanUserTypeLibrarian.getShell().getShell().update();

				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.redraw();
				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.update();

				compoOther.getShell().getShell().layout(true, true);
				compoOther.getShell().getShell().redraw();
				compoOther.getShell().getShell().update();

			} else if (loanUserType.getNomenclatorID().equals(
					CirculationNomenclator.LOANUSER_TYPE_PROFESOR)
					|| loanUserType.getNomenclatorID().equals(
							CirculationNomenclator.LOANUSER_TYPE_EXECUTIVE)
					|| loanUserType.getNomenclatorID().equals(
							CirculationNomenclator.LOANUSER_TYPE_OTHER_WORKERS)
					|| loanUserType.getNomenclatorID().equals(
							CirculationNomenclator.LOANUSER_TYPE_INVESTIGATOR)) {

				compoLoanUserTypeProfessor.setVisible(true);
				insertComposite(compoLoanUserTypeProfessor, registerLoanUser);

				compoLoanUserTypeStudents.setVisible(false);
				insertComposite(compoLoanUserTypeStudents, registerLoanUser);

				compoLoanUserTypePostgraduate.setVisible(false);
				insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);

				compoLoanUserTypeLibrarian.setVisible(false);
				insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);

				compoLoanUserTypeLoanInterLibrarian.setVisible(false);
				insertComposite(compoLoanUserTypeLoanInterLibrarian,
						registerLoanUser);

				compoOther.setVisible(false);
				insertComposite(compoOther, registerLoanUser);

				compoLoanUserTypeStudents.getShell().layout(true, true);
				compoLoanUserTypeStudents.getShell().redraw();
				compoLoanUserTypeStudents.getShell().update();

				compoLoanUserTypeProfessor.getShell().layout(true, true);
				compoLoanUserTypeProfessor.getShell().redraw();
				compoLoanUserTypeProfessor.getShell().update();

				compoLoanUserTypePostgraduate.getShell().layout(true, true);
				compoLoanUserTypePostgraduate.getShell().redraw();
				compoLoanUserTypePostgraduate.getShell().update();

				compoLoanUserTypeLibrarian.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeLibrarian.getShell().getShell().redraw();
				compoLoanUserTypeLibrarian.getShell().getShell().update();

				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.redraw();
				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.update();

				compoOther.getShell().layout(true, true);
				compoOther.getShell().redraw();
				compoOther.getShell().update();

			} else if (loanUserType.getNomenclatorID().equals(
					CirculationNomenclator.LOANUSER_TYPE_POSTGRADUATE)) {
				compoLoanUserTypePostgraduate.setVisible(true);
				insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);

				compoLoanUserTypeStudents.setVisible(false);
				insertComposite(compoLoanUserTypeStudents, registerLoanUser);

				compoLoanUserTypeProfessor.setVisible(false);
				insertComposite(compoLoanUserTypeProfessor, registerLoanUser);

				compoLoanUserTypeLibrarian.setVisible(false);
				insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);

				compoLoanUserTypeLoanInterLibrarian.setVisible(false);
				insertComposite(compoLoanUserTypeLoanInterLibrarian,
						registerLoanUser);

				compoOther.setVisible(false);
				insertComposite(compoOther, registerLoanUser);

				compoLoanUserTypeStudents.getShell().layout(true, true);
				compoLoanUserTypeStudents.getShell().redraw();
				compoLoanUserTypeStudents.getShell().update();

				compoLoanUserTypeProfessor.getShell().layout(true, true);
				compoLoanUserTypeProfessor.getShell().redraw();
				compoLoanUserTypeProfessor.getShell().update();

				compoLoanUserTypePostgraduate.getShell().layout(true, true);
				compoLoanUserTypePostgraduate.getShell().redraw();
				compoLoanUserTypePostgraduate.getShell().update();

				compoLoanUserTypeLibrarian.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeLibrarian.getShell().getShell().redraw();
				compoLoanUserTypeLibrarian.getShell().getShell().update();

				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.redraw();
				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.update();

				compoOther.getShell().layout(true, true);
				compoOther.getShell().redraw();
				compoOther.getShell().update();

			} else if (loanUserType.getNomenclatorID().equals(
					CirculationNomenclator.LOANUSER_TYPE_LIBRARIAN)) {
				compoLoanUserTypeLibrarian.setVisible(true);
				insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);

				compoLoanUserTypeStudents.setVisible(false);
				insertComposite(compoLoanUserTypeStudents, registerLoanUser);

				compoLoanUserTypeProfessor.setVisible(false);
				insertComposite(compoLoanUserTypeProfessor, registerLoanUser);

				compoLoanUserTypePostgraduate.setVisible(false);
				insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);

				compoLoanUserTypeLoanInterLibrarian.setVisible(false);
				insertComposite(compoLoanUserTypeLoanInterLibrarian,
						registerLoanUser);

				compoOther.setVisible(false);
				insertComposite(compoOther, registerLoanUser);

				compoLoanUserTypeStudents.getShell().layout(true, true);
				compoLoanUserTypeStudents.getShell().redraw();
				compoLoanUserTypeStudents.getShell().update();

				compoLoanUserTypeProfessor.getShell().layout(true, true);
				compoLoanUserTypeProfessor.getShell().redraw();
				compoLoanUserTypeProfessor.getShell().update();

				compoLoanUserTypePostgraduate.getShell().layout(true, true);
				compoLoanUserTypePostgraduate.getShell().redraw();
				compoLoanUserTypePostgraduate.getShell().update();

				compoLoanUserTypeLibrarian.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeLibrarian.getShell().getShell().redraw();
				compoLoanUserTypeLibrarian.getShell().getShell().update();

				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.redraw();
				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.update();

				compoOther.getShell().layout(true, true);
				compoOther.getShell().redraw();
				compoOther.getShell().update();

			} else if (loanUserType.getNomenclatorID().equals(
					CirculationNomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY)) {

				compoLoanUserTypeStudents.setVisible(false);
				insertComposite(compoLoanUserTypeStudents, registerLoanUser);

				compoLoanUserTypeProfessor.setVisible(false);
				insertComposite(compoLoanUserTypeProfessor, registerLoanUser);

				compoLoanUserTypePostgraduate.setVisible(false);
				insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);

				compoLoanUserTypeLibrarian.setVisible(false);
				insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);

				compoLoanUserTypeLoanInterLibrarian.setVisible(true);
				insertComposite(compoLoanUserTypeLoanInterLibrarian,
						registerLoanUser);

				compoOther.setVisible(false);
				insertComposite(compoOther, registerLoanUser);

				compoLoanUserTypeLoanInterLibrarian.getShell().layout(true,
						true);
				compoLoanUserTypeLoanInterLibrarian.getShell().redraw();
				compoLoanUserTypeLoanInterLibrarian.getShell().update();

				compoLoanUserTypeStudents.getShell().layout(true, true);
				compoLoanUserTypeStudents.getShell().redraw();
				compoLoanUserTypeStudents.getShell().update();

				compoLoanUserTypeProfessor.getShell().layout(true, true);
				compoLoanUserTypeProfessor.getShell().redraw();
				compoLoanUserTypeProfessor.getShell().update();

				compoLoanUserTypePostgraduate.getShell().layout(true, true);
				compoLoanUserTypePostgraduate.getShell().redraw();
				compoLoanUserTypePostgraduate.getShell().update();

				compoLoanUserTypeLibrarian.getShell().layout(true, true);
				compoLoanUserTypeLibrarian.getShell().redraw();
				compoLoanUserTypeLibrarian.getShell().update();

				compoOther.getShell().layout(true, true);
				compoOther.getShell().redraw();
				compoOther.getShell().update();

			} else if (!(loanUserType.getNomenclatorID()
					.equals(CirculationNomenclator.LOANUSER_TYPE_STUDENT))
					|| !(loanUserType.getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_PROFESOR))
					|| !(loanUserType.getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_POSTGRADUATE))
					|| !(loanUserType.getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_LIBRARIAN))
					|| !(loanUserType.getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY))
					|| !(loanUserType.getNomenclatorID()
							.equals(CirculationNomenclator.LOANUSER_TYPE_INVESTIGATOR))) {

				compoOther.setVisible(true);
				insertComposite(compoOther, registerLoanUser);

				compoLoanUserTypeStudents.setVisible(false);
				insertComposite(compoLoanUserTypeStudents, registerLoanUser);

				compoLoanUserTypeProfessor.setVisible(false);
				insertComposite(compoLoanUserTypeProfessor, registerLoanUser);

				compoLoanUserTypePostgraduate.setVisible(false);
				insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);

				compoLoanUserTypeLibrarian.setVisible(false);
				insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);

				compoLoanUserTypeLoanInterLibrarian.setVisible(false);
				insertComposite(compoLoanUserTypeLoanInterLibrarian,
						registerLoanUser);

				compoLoanUserTypeStudents.getShell().layout(true, true);
				compoLoanUserTypeStudents.getShell().redraw();
				compoLoanUserTypeStudents.getShell().update();

				compoLoanUserTypeProfessor.getShell().layout(true, true);
				compoLoanUserTypeProfessor.getShell().redraw();
				compoLoanUserTypeProfessor.getShell().update();

				compoLoanUserTypePostgraduate.getShell().layout(true, true);
				compoLoanUserTypePostgraduate.getShell().redraw();
				compoLoanUserTypePostgraduate.getShell().update();

				compoLoanUserTypeLibrarian.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeLibrarian.getShell().getShell().redraw();
				compoLoanUserTypeLibrarian.getShell().getShell().update();

				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.layout(true, true);
				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.redraw();
				compoLoanUserTypeLoanInterLibrarian.getShell().getShell()
						.update();

				compoOther.getShell().layout(true, true);
				compoOther.getShell().redraw();
				compoOther.getShell().update();
			}
		}
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public void refreshFragment() {
		registerLoanUser.computeSize(registerLoanUser.getBounds().width,
				SWT.DEFAULT);
		registerLoanUser.pack();
		registerLoanUser.layout(true, true);
		registerLoanUser.update();
		registerLoanUser.redraw();
	}
}
