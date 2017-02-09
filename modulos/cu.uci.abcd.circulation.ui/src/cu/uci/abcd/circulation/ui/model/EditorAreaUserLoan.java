package cu.uci.abcd.circulation.ui.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.auxiliary.LoanUserFragment;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.domain.circulation.CirculationNomenclator;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class EditorAreaUserLoan extends BaseEditableArea {

	private Map<String, Control> controlsMaps;
	private ViewController controller;
	private String country;
	private Composite registerLoanUser;
	
	private int dimension;
	
	private Label lbUpdateLoanUser;
	private Composite associateLoanUser;
	private LoanUserFragment loanUserFragment;
	
	private Button updateBtn;
	private Text txtUserCode;
	private Button rdbCuba;
	private Library library;
	private User user;
	private Worker workerLoggin;
	private LoanUser loanUserSaved;
	private Nomenclator loanUserState = null;
	private List<Control> grupControlsLoanUser = new ArrayList<>();
	private List<String> leftList = new LinkedList<>();
	private String lastString;
	private Group personData;
	public EditorAreaUserLoan(ViewController controller) {
		this.controller = controller;		
	}

	@Override
//FIXME METODO COMPLEJO	
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		user = (User)SecurityUtils.getService().getPrincipal().getByKey("user");
		long  idPerson = user.getPerson().getPersonID();
		
		workerLoggin = ((AllManagementLoanUserViewController)controller).getManagePerson().findWorkerbyPersonID(idPerson);
		
		dimension = parent.getParent().getParent().getBounds().width;
		setDimension(dimension);
			
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");		
	
		registerLoanUser = new Composite(parent, SWT.NORMAL);
		addComposite(registerLoanUser);
		registerLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		buildMessage(registerLoanUser);
		
		lbUpdateLoanUser = new Label(registerLoanUser, SWT.NONE);
		addHeader(lbUpdateLoanUser);
		
		Label separator = new Label(registerLoanUser,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		
		LoanUser entityLoanUser = (LoanUser) entity.getRow();
	
		lastString = AbosMessages.get().LABEL_DETAILS_OF_THE_PERSON;
		personData = new Group(registerLoanUser, SWT.NORMAL);
		add(personData);

		int edad = cu.uci.abcd.domain.util.Auxiliary.getAge(entityLoanUser.getBirthDate());
		String aux = Integer.toString(edad);

		leftList = new LinkedList<>();
		leftList.add(AbosMessages.get().LABEL_NAME);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftList.add(AbosMessages.get().TABLE_AGE);
		leftList.add(AbosMessages.get().TABLE_SEX);

		List<String> rigthList = new LinkedList<>();
		rigthList.add(entityLoanUser.getFirstName());
		rigthList.add(entityLoanUser.getDNI());
		rigthList.add(aux);
		rigthList.add(entityLoanUser.getSex().getNomenclatorName());

		grupControlsLoanUser=CompoundGroup.printGroup(entityLoanUser.getPhoto().getImage(), personData, lastString, leftList, rigthList);

		//------------------
		
		Composite registerLoanUserFragment = new Composite(parent, SWT.NORMAL);
		addComposite(registerLoanUserFragment);
		registerLoanUserFragment.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		associateLoanUser = new Composite(registerLoanUserFragment, SWT.NORMAL);
		addComposite(associateLoanUser);
		associateLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		loanUserFragment = new LoanUserFragment(controller,entityLoanUser,parent.getParent().getParent().getBounds().width, this);
		Composite compoP = (Composite) loanUserFragment.createUIControl(associateLoanUser);
	
		controlsMaps = loanUserFragment.getControls();
		
		txtUserCode = (Text) controlsMaps.get("txtUserCode");
		rdbCuba = ((Button) controlsMaps.get("rdbCuba"));
		
		l10n();
		return parent;
	}

	public void l10n() {
		lbUpdateLoanUser.setText(MessageUtil.unescape(AbosMessages.get().UPDATE_LOAN_USER));
	
		lastString = AbosMessages.get().LABEL_DETAILS_OF_THE_PERSON;
		leftList.clear();
		leftList.add(AbosMessages.get().LABEL_NAME);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftList.add(AbosMessages.get().TABLE_AGE);
		leftList.add(AbosMessages.get().TABLE_SEX);
		CompoundGroup.l10n(grupControlsLoanUser, leftList);
		personData.setText(lastString);
	
		loanUserFragment.l10n();	
	}

	@Override
	public boolean isValid() {
		return false;
	}

	
	@Override
	public Composite createButtons(Composite parent, final IGridViewEntity entity, final IVisualEntityManager manager) {
		
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		updateBtn = new Button(parent, SWT.PUSH);
		updateBtn.setText(AbosMessages.get().BUTTON_ACEPT);

		updateBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				List<Transaction> listTransactionLoanUser= new ArrayList<>();
				List<Transaction> listTransactionNotReturnLoanUser= new ArrayList<>();
				//List<Penalty> listPenaltyLoanUser= new ArrayList<>();
				List<Penalty> listPenaltyByLoanUserFine = new ArrayList<>();
				Penalty penalty;
				
				
		
				LoanUser fdtEntity = (LoanUser) entity.getRow();
				
				Nomenclator state = ((AllManagementLoanUserViewController) controller)
						.getManageLoanUser().findByID(
								Nomenclator.LOANUSER_STATE_ACTIVE);

				int fromYear = ((DateTime) controlsMaps.get("dateTime")).getYear() - 1900;
				int fromMonth = ((DateTime) controlsMaps.get("dateTime")).getMonth();
				int fromDay = ((DateTime) controlsMaps.get("dateTime")).getDay();
			
				@SuppressWarnings("deprecation")
				Date endDate = new Date(fromYear, fromMonth, fromDay);

				java.util.Date fecha = new java.util.Date();
				Date fechaSQL = new Date(fecha.getTime());

				LoanUser loanUserRegister = fdtEntity;
				
				Nomenclator penaltyTypeFine = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_TYPE_FINE);
				Nomenclator penaltyStatePengingPaid = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_STATE_PENDING_PAYMENT);
				listPenaltyByLoanUserFine = ((AllManagementLoanUserViewController) controller).getManagePenalty().findPenaltyByLoanUserIdAndPenaltyTypeAndPenaltyState(loanUserRegister.getPersonID(), penaltyTypeFine, penaltyStatePengingPaid);

				Nomenclator stateActive = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOANUSER_STATE_ACTIVE);
				Nomenclator stateInactive = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOANUSER_STATE_INACTIVE);
			
				Nomenclator stateRenew = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOAN_STATE_RENEW);
				Nomenclator stateLoan = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOAN_STATE_BORROWED);
				Nomenclator stateLate = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOAN_STATE_LATE);
				
				//Nomenclator penaltyStatePendingPay = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.PENALTY_STATE_PENDING_PAYMENT);
				//Nomenclator stateNotReturn = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOAN_STATE_NOT_DELIVERED);

			   listTransactionLoanUser = ((AllManagementLoanUserViewController) controller).getManageTransaction().searchTransactionsByLoanUser(loanUserRegister, stateLoan,stateRenew,stateLate);
			 /*  listTransactionNotReturnLoanUser = ((AllManagementLoanUserViewController) controller).getManageTransaction().searchTransactionsNotReturnLoanByLoanUser(loanUserRegister, stateNotReturn);
				
			   for (int i = 0; i < listTransactionNotReturnLoanUser.size(); i++) {
				
				   penalty = ((AllManagementLoanUserViewController) controller).getManagePenalty().searchPenaltyByLoanUserAndLoanObject(penaltyStatePendingPay, listTransactionNotReturnLoanUser.get(i).getLoanUser(),listTransactionNotReturnLoanUser.get(i).getLoanObject());				
				   listPenaltyLoanUser.add(penalty);
			   }*/
			   
				    String code = ((Text) controlsMaps.get("txtUserCode")).getText().trim();
					LoanUser validateCodeUnique = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByLoanUserCode(code);
	
					if (loanUserFragment.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
						showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
					}
					else if (loanUserFragment.getValidator().decorationFactory.AllControlDecorationsHide()) 
					{
						
						if ( validateCodeUnique == null || validateCodeUnique.getId().equals(loanUserRegister.getId())) {
											
								if (((listTransactionLoanUser.size()==0 && listPenaltyByLoanUserFine.size()==0)&& (loanUserFragment.isSelect() == true|| loanUserFragment.isSelect() == false)) || ((listTransactionLoanUser.size()!=0 || listPenaltyByLoanUserFine.size()!=0) && loanUserFragment.isSelect() == false)) {
									
								Nomenclator loanUserType =(Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboUserType"));
							
								Room room = (Room) UiUtils.getSelected((Combo) controlsMaps.get("comboRoom"));
															
								loanUserRegister.setPerson(loanUserRegister.getPerson());
								loanUserRegister.setRegistrationDate(loanUserRegister.getRegistrationDate());
								loanUserRegister.setExpirationDate(endDate);
								loanUserRegister.setLoanUserState(state);
								loanUserRegister.setLoanUserCode(code);
								loanUserRegister.setLoanUserType(loanUserType);
								loanUserRegister.setRegisteredatroom(room);
								loanUserRegister.setRegisterby(workerLoggin);
										
							if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_STUDENT)) 
							{	
									Nomenclator faculty = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboFaculty"));																	
									Nomenclator speciality = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboSpecialty"));																	
									Nomenclator modalityEst = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboModalityEst"));
																		
									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservations")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setUniversity(((Text) controlsMaps.get("txtUniversity")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setModality(modalityEst);
									loanUserRegister.setFaculty(faculty);
									loanUserRegister.setSpeciality(speciality);
	
									if (((Text) controlsMaps.get("txtDisable")).getVisible() == true) {
										loanUserRegister.setDisabilitationremarks(((Text) controlsMaps.get("txtDisable")).getText().replaceAll(" +", " ").trim());
										loanUserRegister.setLoanUserState(stateInactive);
										loanUserState=null;
									} else{
										loanUserRegister.setDisabilitationremarks("");
										loanUserRegister.setLoanUserState(stateActive);
										loanUserState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOANUSER_STATE_ACTIVE);
									}									
									if (!fdtEntity.getLoanUserType().getNomenclatorID().equals(Nomenclator.LOANUSER_TYPE_STUDENT)) {
										loanUserRegister.setDepartment(null);	
										loanUserRegister.setRoomlibrarian(null);
										loanUserRegister.setAuthorizingOfficial("");
										loanUserRegister.setCountry("");
										
									}							
							}
							
								else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_PROFESOR) ||
										loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_EXECUTIVE) ||
										loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_OTHER_WORKERS)||
										loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_INVESTIGATOR)
										) 
								{																
									Nomenclator faculty1 = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboFaculty1"));
									Nomenclator depatment = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboDepartment"));
										
									loanUserRegister.setFaculty(faculty1);
									loanUserRegister.setUniversity(((Text) controlsMaps.get("txtUniversity1")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setDepartment(depatment);
 									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservations1")).getText().replaceAll(" +", " ").trim());
	
									if (((Text) controlsMaps.get("txtDisable1")).getVisible() == true) {
										loanUserRegister.setDisabilitationremarks(((Text) controlsMaps.get("txtDisable1")).getText().replaceAll(" +", " ").trim());
										loanUserRegister.setLoanUserState(stateInactive);
										loanUserState=null;
									}else{
										loanUserRegister.setDisabilitationremarks("");
										loanUserRegister.setLoanUserState(stateActive);
										loanUserState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOANUSER_STATE_ACTIVE);
										
									}
									
									if (!fdtEntity.getLoanUserType().getNomenclatorID().equals(Nomenclator.LOANUSER_TYPE_PROFESOR)) {
										loanUserRegister.setModality(null);	
										loanUserRegister.setSpeciality(null);					
										loanUserRegister.setRoomlibrarian(null);
										loanUserRegister.setAuthorizingOfficial("");
										loanUserRegister.setCountry("");
									}								
								}
	
								else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_POSTGRADUATE)) {
	
									Nomenclator modality =(Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboModality")); 
									Nomenclator faculty2 = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboFaculty2")); 
									Nomenclator speciality = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboSpecialty2")); 
																			
									loanUserRegister.setFaculty(faculty2);
									loanUserRegister.setModality(modality);
									loanUserRegister.setSpeciality(speciality);
									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservations2")).getText().replaceAll(" +", " ").trim());
	
									if (((Text) controlsMaps.get("txtDisable2")).getVisible() == true) {
										loanUserRegister.setDisabilitationremarks(((Text) controlsMaps.get("txtDisable2")).getText().replaceAll(" +", " ").trim());
										loanUserRegister.setLoanUserState(stateInactive);
										loanUserState=null;
									} else
									{
										loanUserRegister.setDisabilitationremarks("");
										loanUserRegister.setLoanUserState(stateActive);
										loanUserState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOANUSER_STATE_ACTIVE);
									}

									if (!fdtEntity.getLoanUserType().getNomenclatorID().equals(Nomenclator.LOANUSER_TYPE_POSTGRADUATE)) {
										loanUserRegister.setDepartment(null);		
										loanUserRegister.setRoomlibrarian(null);
										loanUserRegister.setAuthorizingOfficial("");
										loanUserRegister.setCountry("");
										loanUserRegister.setUniversity("");
									}
									
								}else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_LIBRARIAN)) {	
														
									Room roomLibrarian = (Room) UiUtils.getSelected((Combo) controlsMaps.get("comboRoomLibrarian")); 
																		
									loanUserRegister.setUniversity(((Text) controlsMaps.get("txtUniversity2")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setRoomlibrarian(roomLibrarian);
									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservationLibrarian")).getText().replaceAll(" +", " ").trim());
	
									if (((Text) controlsMaps.get("txtDisableLybrarian")).getVisible() == true) {
										loanUserRegister.setDisabilitationremarks(((Text) controlsMaps.get("txtDisableLybrarian")).getText().replaceAll(" +", " ").trim());
										loanUserRegister.setLoanUserState(stateInactive);
										loanUserState=null;
									} else
									{
										loanUserRegister.setDisabilitationremarks("");
										loanUserRegister.setLoanUserState(stateActive);
										loanUserState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOANUSER_STATE_ACTIVE);
									}
									
									if (!fdtEntity.getLoanUserType().getNomenclatorID().equals(Nomenclator.LOANUSER_TYPE_LIBRARIAN)) {
										loanUserRegister.setDepartment(null);	
										loanUserRegister.setAuthorizingOfficial("");
										loanUserRegister.setCountry("");
										loanUserRegister.setFaculty(null);
										loanUserRegister.setModality(null);
										loanUserRegister.setSpeciality(null);
									}
								} 
								else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY)) {
									
									
									if (rdbCuba.getSelection() == true)
									{
										country = MessageUtil.unescape(AbosMessages.get().LABEL_CUBA);
									}
									else
										country = MessageUtil.unescape(AbosMessages.get().LABEL_EXTERNAL);
																		
									loanUserRegister.setUniversity(((Text) controlsMaps.get("txtUniversity3")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setAuthorizingOfficial(((Text) controlsMaps.get("txtAuthorizingOfficial")).getText().replaceAll(" +", " ").trim());
									loanUserRegister.setCountry(country);
									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservationInterLibrarian")).getText().replaceAll(" +", " ").trim());
	
									if (((Text) controlsMaps.get("txtDisableInterLybrarian")).getVisible() == true) {
										loanUserRegister.setDisabilitationremarks(((Text) controlsMaps.get("txtDisableInterLybrarian")).getText().replaceAll(" +", " ").trim());
										loanUserRegister.setLoanUserState(stateInactive);
										loanUserState=null;
									} else
									{
										loanUserRegister.setDisabilitationremarks("");
										loanUserRegister.setLoanUserState(stateActive);
										loanUserState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOANUSER_STATE_ACTIVE);
										
									}
									
									if (!fdtEntity.getLoanUserType().getNomenclatorID().equals(Nomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY)) {
										loanUserRegister.setDepartment(null);		
										loanUserRegister.setFaculty(null);
										loanUserRegister.setModality(null);
										loanUserRegister.setSpeciality(null);
										loanUserRegister.setRoomlibrarian(null);
									}								
									
								} 
								else {
								
									loanUserRegister.setRemarks(((Text) controlsMaps.get("txtObservationsOther")).getText().replaceAll(" +", " ").trim());
									
									if (((Text) controlsMaps.get("txtDisableOT")).getVisible() == true) {
										loanUserRegister.setDisabilitationremarks(((Text) controlsMaps.get("txtDisableOT")).getText().replaceAll(" +", " ").trim());
										loanUserRegister.setLoanUserState(stateInactive);
										loanUserState=null;
									}
									else
									{
										loanUserRegister.setDisabilitationremarks("");
										loanUserRegister.setLoanUserState(stateActive);
										loanUserState = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.LOANUSER_STATE_ACTIVE);
										
									}
									
									
									if (!fdtEntity.getLoanUserType().getNomenclatorID().equals(Nomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY) ||
											!fdtEntity.getLoanUserType().getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_PROFESOR) ||
											!fdtEntity.getLoanUserType().getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_EXECUTIVE) ||
											!fdtEntity.getLoanUserType().getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_OTHER_WORKERS)||
											!fdtEntity.getLoanUserType().getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_INVESTIGATOR)||
											!fdtEntity.getLoanUserType().getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_STUDENT)||
											!fdtEntity.getLoanUserType().getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_POSTGRADUATE)||
											!fdtEntity.getLoanUserType().getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_LIBRARIAN)
											)
									{
										loanUserRegister.setUniversity("");
										loanUserRegister.setDepartment(null);		
										loanUserRegister.setAuthorizingOfficial("");
										loanUserRegister.setCountry("");
										loanUserRegister.setFaculty(null);
										loanUserRegister.setModality(null);
										loanUserRegister.setSpeciality(null);
										loanUserRegister.setRoomlibrarian(null);
									}
								}
							
							if (loanUserState != null) {
									LoanUser validateLoanUser = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findLoanUserByPersonIDAndState(loanUserRegister.getPerson().getPersonID(), loanUserState.getNomenclatorID());
									if (validateLoanUser != null) {									
									
										if (!validateLoanUser.getId().equals(loanUserRegister.getId())) 
										{
											RetroalimentationUtils.showInformationMessage(MessageUtil.unescape(AbosMessages.get().MSJE_LOANUSER_CREATED_ACTIVE_EXIST));
										
										} 
										else
										{
											loanUserSaved=((AllManagementLoanUserViewController) controller).getManageLoanUser().addLoanUser(loanUserRegister);
											showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));
											
											manager.save(new BaseGridViewEntity<LoanUser>(loanUserSaved));
											manager.refresh();	
										}
									}
									else
									{							
										loanUserSaved=((AllManagementLoanUserViewController) controller).getManageLoanUser().addLoanUser(loanUserRegister);
										showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));
								   
										manager.save(new BaseGridViewEntity<LoanUser>(loanUserSaved));
										manager.refresh();	
									}	
								}
							else{
								loanUserSaved=((AllManagementLoanUserViewController) controller).getManageLoanUser().addLoanUser(loanUserRegister);
								showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));
								
								manager.save(new BaseGridViewEntity<LoanUser>(loanUserSaved));
								manager.refresh();	
								}
							
							
							}
							
							else{ 
								listTransactionLoanUser.clear();
								showInformationMessage(AbosMessages.get().MSG_USER_TRANSACCION_PENDING);}
						
								} else {
									txtUserCode.setText("");
									txtUserCode.setFocus();
									showErrorMessage(AbosMessages.get().MSJE_CODE_UNIQUE);
									
								}			
						
						}
							else
								showErrorMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA);
						}
						
			
						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {

						}
					});

		return parent;
	}

	@Override
	public boolean closable() {
		return true;
	}



	@Override
	public String getID() {
		return "updateLoanUserID";
	}
}
