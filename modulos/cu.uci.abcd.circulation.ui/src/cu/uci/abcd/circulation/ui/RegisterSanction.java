package cu.uci.abcd.circulation.ui;

import java.sql.Date;
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
import cu.uci.abcd.circulation.ui.auxiliary.AssociateLoanUserFragment;
import cu.uci.abcd.circulation.ui.auxiliary.PenaltyFragment;
import cu.uci.abcd.circulation.ui.auxiliary.ViewPenaltyFragment;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.Contributor;
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

public class RegisterSanction extends ContributorPage implements Contributor {

	private Composite compoRegister;
	private Penalty penalty = null;
	private int tempo = 0;

	private Composite registerPenalty;
	private Button saveBtn;
	private Composite viewPenaltySave;
	private ViewPenaltyFragment viewPenaltyFragment;
	private Composite associatePenalty;
	private LoanUser representant = null;
	private Map<String, Control> controlsMaps;
	private AssociateLoanUserFragment associateLoanUserFragment;
	private Composite associatePersonComposite;
	private Button rdb;
	private PenaltyFragment penaltyFragment;
	private Composite compoButtons;
	private Button cancelBtn;
	private Composite compoParent;
	private int dimension;
	private Library library;
	private User user;
	//private Composite msg;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().NAME_UI_PENALTY);
	}

	@Override
	public String getID() {
		return "addPenaltyID";
	}

	// FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(final Composite parent) {

		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");

		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		dimension = parent.getParent().getParent().getParent().getBounds().width;
		
		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 60);
	
		if (user.getPerson() == null) {			
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);			
		}
		else{
		
		long idPerson = user.getPerson().getPersonID();

		Worker workerLoggin = ((AllManagementLoanUserViewController) controller).getManagePerson().findWorkerbyPersonID(idPerson);
		if (workerLoggin == null) {
			RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_USER_NOT_WORKER);
		}
		else{
	
		compoParent = new Composite(parent, SWT.NORMAL);
		addComposite(compoParent);
		compoParent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		compoRegister = new Composite(compoParent, SWT.NORMAL);
		addComposite(compoRegister);
		compoRegister.setData(RWT.CUSTOM_VARIANT, "gray_background");

		registerPenalty = new Composite(compoRegister, SWT.NORMAL);

		compoButtons = new Composite(compoParent, SWT.NORMAL);
		addComposite(compoButtons);
		compoButtons.setData(RWT.CUSTOM_VARIANT, "gray_background");
		compoButtons.setVisible(false);

		associatePersonComposite = new Composite(compoRegister, SWT.NORMAL);
		addComposite(associatePersonComposite);
		associatePersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");

		associateLoanUserFragment = new AssociateLoanUserFragment(controller, representant, registerPenalty, tempo, compoButtons, dimension);
		TreeColumnListener treeColumnListener = new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				LoanUser loanUser = (LoanUser) event.entity.getRow();
				associateLoanUserFragment.setLoanUser(loanUser);
				associateLoanUserFragment.showDataLoanUser(loanUser);
				registerPenalty.setVisible(true);
				compoButtons.setVisible(true);

			}
		};
		associateLoanUserFragment.setTreeColumnListener(treeColumnListener);
		Composite a = (Composite) associateLoanUserFragment.createUIControl(associatePersonComposite);

		// *****************Fragment
		// Penalty***********************************************
		addComposite(registerPenalty);
		registerPenalty.setData(RWT.CUSTOM_VARIANT, "gray_background");
		registerPenalty.setVisible(false);
    
		associatePenalty = new Composite(registerPenalty, SWT.NORMAL);
		addComposite(associatePenalty);
		associatePenalty.setData(RWT.CUSTOM_VARIANT, "gray_background");

		penaltyFragment = new PenaltyFragment(controller, penalty, dimension, this);
		Composite compoP = (Composite) penaltyFragment.createUIControl(associatePenalty);

		controlsMaps = penaltyFragment.getControls();

		rdb = ((Button) controlsMaps.get("rdbSuspencion"));

		Composite compo = ((Composite) controlsMaps.get("registerPenalty"));
		
		// *******************************		
		   
		cancelBtn = new Button(compoButtons, SWT.PUSH);
		add(cancelBtn);
		saveBtn = new Button(compoButtons, SWT.PUSH);
		add(saveBtn);	

		br();
		add(new Label(compoButtons, 0),Percent.W100);
		
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "deprecation" })
			@Override
			public void widgetSelected(SelectionEvent e) {
				synchronized (this) {
					
				
				int fromYear = ((DateTime) controlsMaps.get("dateTime")).getYear() - 1900;
				int fromMonth = ((DateTime) controlsMaps.get("dateTime")).getMonth();
				int fromDay = ((DateTime) controlsMaps.get("dateTime")).getDay();

				int fromYear1 = ((DateTime) controlsMaps.get("dateTime1")).getYear() - 1900;
				int fromMonth1 = ((DateTime) controlsMaps.get("dateTime1")).getMonth();
				int fromDay1 = ((DateTime) controlsMaps.get("dateTime1")).getDay();

				Date startDate = new Date(fromYear, fromMonth, fromDay);
				Date endDate = new Date(fromYear1, fromMonth1, fromDay1);

				if (penaltyFragment.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
					((DateTime) controlsMaps.get("dateTime")).setBackground(null);
					if (penaltyFragment.getValidator().decorationFactory.AllControlDecorationsHide()) {

						LoanUser loanUserSaved = associateLoanUserFragment.getLoanUser();

						Long idLoanUser = Long.parseLong(loanUserSaved.getId().toString());

						LoanUser loanUser = new LoanUser();
						loanUser = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findOneLoanUser(idLoanUser);

						Penalty penalty = new Penalty();
						penalty.setEffectiveDate(startDate);
						penalty.setExpirationDate(endDate);
						penalty.setMotivation(((Text) controlsMaps.get("txtReason")).getText().replaceAll(" +", " ").trim());
						penalty.setLoanUser(loanUser);
						penalty.setLibrary(library);
						penalty.setLibrarian(user);

						if (rdb.getSelection() == true) {

							Nomenclator stateActivo = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_STATE_ACTIVE);

							Nomenclator typeSuspension = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_TYPE_SUSPENCION);

							penalty.setPenaltyType(typeSuspension);
							penalty.setPenaltyState(stateActivo);
						}

						else {

							Nomenclator typeFine = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_TYPE_FINE);
							Nomenclator statePending = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID(Nomenclator.PENALTY_STATE_PENDING_PAYMENT);

							Double amount = Double.parseDouble(((Text) controlsMaps.get("txt_monto")).getText());

							Nomenclator coinType = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboCoin"));

							penalty.setPenaltyType(typeFine);
							penalty.setPenaltyState(statePending);
							penalty.setAmount(amount);
							penalty.setCoinType(coinType);

						}

						Penalty penaltySaved = ((AllManagementLoanUserViewController) controller).getManagePenalty().addPenalty(penalty);

						//penaltyFragment.cleanField();
						associateLoanUserFragment.dispose();
						ContributorService contributorService = getContributorService();

						viewPenaltyFragment = new ViewPenaltyFragment(controller, penaltySaved, compoParent, dimension, RegisterSanction.this, contributorService);
						viewPenaltySave = (Composite) viewPenaltyFragment.createUIControl(parent);
						viewPenaltySave.setData(RWT.CUSTOM_VARIANT, "gray_background");

						compoParent.setVisible(false);
						viewPenaltySave.setVisible(true);

						insertComposite(viewPenaltySave, parent);
						viewPenaltySave.getParent().layout(true, true);
						viewPenaltySave.getParent().redraw();
						viewPenaltySave.getParent().update();
						// ---------------Mensaje

						RetroalimentationUtils.showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
					
						cleanComponentAssociateLoanUser();
						cleanComponentRegisterPenalty();
						cleanComponentButtons();
						createUI();
						
					} else
						RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
				}

				refresh();
				
			 }
			}
		});

		cancelBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
					private static final long serialVersionUID = 1L;

					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {
							RegisterSanction.this.notifyListeners(SWT.Dispose, new Event());
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
	public boolean canClose() {
		return true;
	}

	@Override
	public void l10n() {
		String nameRegister = MessageUtil.unescape(AbosMessages.get().NAME_UI_REGISTER_PENALTY);
		associateLoanUserFragment.setTextRegister(nameRegister);
		associateLoanUserFragment.l10n();
		penaltyFragment.l10n();
		cancelBtn.setText(AbosMessages.get().BUTTON_CANCEL);
		saveBtn.setText(AbosMessages.get().BUTTON_ACEPT);

		refresh();
	}

	public void cleanComponentAssociateLoanUser() {
		try {
			Control[] temp = associatePersonComposite.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		}
	}

	public void cleanComponentRegisterPenalty() {
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
	
	public void createUI() {
		createUIControl(compoParent);
		compoParent.getParent().layout(true, true);
		compoParent.getParent().redraw();
		compoParent.getParent().update();		
	}
	
	@Override
	public void setViewController(ViewController controller) {
		super.setViewController(controller);
	}
}