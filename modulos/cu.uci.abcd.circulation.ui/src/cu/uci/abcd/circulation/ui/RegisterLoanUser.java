package cu.uci.abcd.circulation.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
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
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.auxiliary.AssociatePersonFragment;
import cu.uci.abcd.circulation.ui.auxiliary.LoanUserFragmentRegister;
import cu.uci.abcd.circulation.ui.auxiliary.ViewLoanUserFragment;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.domain.circulation.CirculationNomenclator;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;

public class RegisterLoanUser extends ContributorPage {

	private Date date;
	private Composite registerLoanUser;
	private Composite compoRegister;
	private Composite associatePersonComposite;
	private String orderByString = "firstName";
	private int direction = 1024;
	private AssociatePersonFragment associatePersonFragment;
	private Person representant = null;
	private Composite compoButtons;
	private Composite associateLoanUser;
	private LoanUser loanUser = null;
	private LoanUserFragmentRegister loanUserFragment;
	private Map<String, Control> controlsMaps;
	private Button saveBtn;
	private Button cancelBtn;
	private LoanUser loanUserSaved;
	private ViewLoanUserFragment viewLoanUserFragment;
	private Composite viewLoanUserSave;
	private Text txtCode;
	private Composite compoParent;
	private int dimension;
	private Library library;
	private User user;
	private Worker workerLoggin;
	private Button rdb;
	private String country;
	//private Composite msg;

	@Override
	public String contributorName() {

		return MessageUtil.unescape(AbosMessages.get().NAME_UI_LOAN_USER);
	}
    
	@Override
	// FIXME METODO COMPLEJO
	public Control createUIControl(final Composite parent) {
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		/*msg = new Composite(parent, SWT.NORMAL);
		msg.setLayout(new FormLayout());
		msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(msg).atTopTo(parent).withWidth(320).withHeight(30).atRight(0);		*/

		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
	
		if (user.getPerson() == null) {			
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);			
		}
		else{
		long idPerson = user.getPerson().getPersonID();

		workerLoggin = ((AllManagementLoanUserViewController) controller).getManagePerson().findWorkerbyPersonID(idPerson);

		if (workerLoggin == null) {
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);
		}
		else{
		dimension = parent.getParent().getParent().getParent().getBounds().width;

		compoParent = new Composite(parent, SWT.NORMAL);
		addComposite(compoParent);
		compoParent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		compoRegister = new Composite(compoParent, SWT.NORMAL);
		addComposite(compoRegister);
		compoRegister.setData(RWT.CUSTOM_VARIANT, "gray_background");

		registerLoanUser = new Composite(compoRegister, SWT.NORMAL);

		compoButtons = new Composite(compoParent, SWT.NORMAL);
		addComposite(compoButtons);
		compoButtons.setData(RWT.CUSTOM_VARIANT, "gray_background");
		compoButtons.setVisible(false);

		createComponentAssociateLoanUser(compoRegister);

		addComposite(registerLoanUser);
		registerLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");
		registerLoanUser.setVisible(false);
		
		Composite resize = new Composite(registerLoanUser, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 370);
		

		associateLoanUser = new Composite(registerLoanUser, SWT.NORMAL);
		addComposite(associateLoanUser);
		associateLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");

		loanUserFragment = new LoanUserFragmentRegister(controller, loanUser, dimension, this);
		Composite compoP = (Composite) loanUserFragment.createUIControl(associateLoanUser);

		cancelBtn = new Button(compoButtons, SWT.PUSH);
		add(cancelBtn);

		saveBtn = new Button(compoButtons, SWT.PUSH);
		add(saveBtn);

		br();
		add(new Label(compoButtons, 0),Percent.W100);
		
		controlsMaps = loanUserFragment.getControls();
		txtCode = (Text) controlsMaps.get("txtUserCode");
		rdb = ((Button) controlsMaps.get("rdbCuba"));

		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				Person persona = associatePersonFragment.getPerson();
				Nomenclator state = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOANUSER_STATE_ACTIVE);

				int fromYear = ((DateTime) controlsMaps.get("dateTime")).getYear() - 1900;
				int fromMonth = ((DateTime) controlsMaps.get("dateTime")).getMonth();
				int fromDay = ((DateTime) controlsMaps.get("dateTime")).getDay();

				@SuppressWarnings("deprecation")
				Date endDate = new Date(fromYear, fromMonth, fromDay);

				java.util.Date fecha = new java.util.Date();
				Date fechaSQL = new Date(fecha.getTime());

					String code = ((Text) controlsMaps.get("txtUserCode")).getText().trim();

					LoanUser validateCode = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByLoanUserCode(code);

					if (loanUserFragment.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
						RetroalimentationUtils.showErrorShellMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED);
					} else if (loanUserFragment.getValidator().decorationFactory.AllControlDecorationsHide()) {
						if (validateCode == null) {

								Nomenclator loanUserType = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboUserType"));

								Room room = (Room) UiUtils.getSelected((Combo) controlsMaps.get("comboRoom"));

								LoanUser loanUserRegister = new LoanUser();
								loanUserRegister.setPerson(persona);
								loanUserRegister.setRegistrationDate(fechaSQL);
								loanUserRegister.setExpirationDate(endDate);
								loanUserRegister.setLoanUserState(state);
								loanUserRegister.setLoanUserCode(code);
								loanUserRegister.setLoanUserType(loanUserType);
								loanUserRegister.setRegisteredatroom(room);
								loanUserRegister.setRegisterby(workerLoggin);

								if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_STUDENT)) {

									Nomenclator faculty = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboFaculty"));
									Nomenclator speciality = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboSpecialty"));
									Nomenclator modalityEst = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboModalityEst"));

									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservations")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setUniversity(((Text) controlsMaps.get("txtUniversity")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setModality(modalityEst);
									loanUserRegister.setFaculty(faculty);
									loanUserRegister.setSpeciality(speciality);

									loanUserSaved = ((AllManagementLoanUserViewController) controller).getManageLoanUser().addLoanUser(loanUserRegister);

								}

								else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_PROFESOR) || loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_EXECUTIVE)
										|| loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_OTHER_WORKERS) || loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_INVESTIGATOR)) {

									Nomenclator faculty1 = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboFaculty1"));
									Nomenclator depatment = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboDepartment"));

									loanUserRegister.setFaculty(faculty1);
									loanUserRegister.setUniversity(((Text) controlsMaps.get("txtUniversity1")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setDepartment(depatment);
									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservations1")).getText().replaceAll(" +", " ").trim());

									loanUserSaved = ((AllManagementLoanUserViewController) controller).getManageLoanUser().addLoanUser(loanUserRegister);

								}

								else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_POSTGRADUATE)) {

									Nomenclator modality = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboModality"));
									Nomenclator faculty2 = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboFaculty2"));
									Nomenclator speciality = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboSpecialty2"));

									loanUserRegister.setFaculty(faculty2);
									loanUserRegister.setModality(modality);
									loanUserRegister.setSpeciality(speciality);
									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservations2")).getText().replaceAll(" +", " ").trim());

									loanUserSaved = ((AllManagementLoanUserViewController) controller).getManageLoanUser().addLoanUser(loanUserRegister);

								} else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_LIBRARIAN)) {

									Room roomLibrarian = (Room) UiUtils.getSelected((Combo) controlsMaps.get("comboRoomLibrarian"));

									loanUserRegister.setUniversity(((Text) controlsMaps.get("txtUniversity2")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setRoomlibrarian(roomLibrarian);
									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservationLibrarian")).getText().replaceAll(" +", " ").trim());

									loanUserSaved = ((AllManagementLoanUserViewController) controller).getManageLoanUser().addLoanUser(loanUserRegister);

								} else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY)) {

									if (rdb.getSelection() == true) {
										country = MessageUtil.unescape(AbosMessages.get().LABEL_CUBA);
									} else
										country = MessageUtil.unescape(AbosMessages.get().LABEL_EXTERNAL);

									loanUserRegister.setUniversity(((Text) controlsMaps.get("txtUniversity3")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setAuthorizingOfficial(((Text) controlsMaps.get("txtAuthorizingOfficial")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setCountry(country);
									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservationInterLibrarian")).getText().replaceAll(" +", " ").trim());

									loanUserSaved = ((AllManagementLoanUserViewController) controller).getManageLoanUser().addLoanUser(loanUserRegister);

								} else {

									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservationsOther")).getText().replaceAll(" +", " ").trim());
									loanUserSaved = ((AllManagementLoanUserViewController) controller).getManageLoanUser().addLoanUser(loanUserRegister);
								}
    
								ContributorService contributorService = getContributorService();
								viewLoanUserFragment = new ViewLoanUserFragment(controller, loanUserSaved, compoParent, dimension, RegisterLoanUser.this, contributorService);
								viewLoanUserSave = (Composite) viewLoanUserFragment.createUIControl(parent);
								viewLoanUserSave.setData(RWT.CUSTOM_VARIANT, "gray_background");

								compoParent.setVisible(false);
								viewLoanUserSave.setVisible(true);

								// ---------------Mensaje
    
								/**
								 * Se cambio el mensaje por el del Shell.
								 */
								
								RetroalimentationUtils.showInformationShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
							   
								
								cleanComponentAssociateLoanUser();
								cleanComponentRegisterLoanUser();
								cleanComponentButtons();
								createComponentAssociateLoanUser(parent);
								createUI();
							
						} else {
							txtCode.setText("");
							txtCode.setFocus();

							showErrorMessage(AbosMessages.get().MSJE_CODE_UNIQUE);

						}
					} else
						RetroalimentationUtils.showErrorShellMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA);
				
				
				refresh();
			}
		});

		cancelBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {
							RegisterLoanUser.this.notifyListeners(SWT.Dispose, new Event());
							
						}
					}

				});

			}
		});

		l10n();
		}}
		return parent;
	}

	@Override
	public String getID() {
		return "addLoanUserID";
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {

		saveBtn.setText(AbosMessages.get().BUTTON_ACEPT);
		cancelBtn.setText(AbosMessages.get().BUTTON_CANCEL);
		if (associatePersonFragment != null) {
			associatePersonFragment.l10n();
		}

		loanUserFragment.l10n();		
		refresh();

	}

	@Override
	public void setViewController(ViewController controller) {
		super.setViewController(controller);
	}

	public String FormatDate() {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}

	public void cleanComponentAssociateLoanUser() {
		try {
			Control[] temp = associatePersonComposite.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		}
	}

	public void cleanComponentRegisterLoanUser() {
		try {
			Control[] temp = compoRegister.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		}
	}

	public void cleanComponentButtons() {
		try {
			Control[] temp = compoButtons.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		}
	}      

	public void createComponentAssociateLoanUser(Composite parent) {
		associatePersonComposite = new Composite(compoRegister, SWT.NORMAL);
		addComposite(associatePersonComposite);
		associatePersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");

		associatePersonFragment = new AssociatePersonFragment(representant, registerLoanUser, compoButtons, dimension, parent);
		TreeColumnListener treeColumnListener = new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				Person person = (Person) event.entity.getRow();
				associatePersonFragment.setPerson(person);
				Nomenclator loanUserState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.LOANUSER_STATE_ACTIVE);
				LoanUser validateLoanUser = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findLoanUserByPersonIDAndState(person.getPersonID(), loanUserState.getNomenclatorID());

				if (validateLoanUser == null) {
					associatePersonFragment.showDataLoanUser(person);
					registerLoanUser.setVisible(true);
					compoButtons.setVisible(true);
				} else
					RetroalimentationUtils.showInformationShellMessage(AbosMessages.get().MSJE_LOANUSER_CREATED_ACTIVE);
			}
		};
		associatePersonFragment.setTreeColumnListener(treeColumnListener);
		Composite a = (Composite) associatePersonFragment.createUIControl(associatePersonComposite);

	}

	public void createUI() {
		createUIControl(compoParent);
		compoParent.getParent().layout(true, true);
		compoParent.getParent().redraw();
		compoParent.getParent().update();		
	}
}