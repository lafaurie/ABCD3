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
import cu.uci.abcd.circulation.ui.auxiliary.PenaltyFragment;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class EditorAreaConsultSanction extends BaseEditableArea {

	private ViewController controller;
	private Map<String, Control> controlsMaps;
	private Button updateBtn;
	private PenaltyFragment penaltyFragment;
	private Composite associatePenalty;
	private Button rdb;
	private Label lbEditPenalty;
	private Library library;
	String lastString;
	Group personData;
	List<String> leftList = new LinkedList<>();
	private List<Control> grupControlsPenalty = new ArrayList<>();
	
	public EditorAreaConsultSanction(ViewController controller) {
		super();
		this.controller = controller;	
		
	}
	//FIXME METODO COMPLEJO
	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");		
		setDimension(parent.getParent().getParent().getBounds().width);
		
		Penalty entityPenalty = (Penalty) entity.getRow();
		
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite compoLoanUser = new Composite(parent, SWT.NORMAL);
		addComposite(compoLoanUser);
		compoLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		buildMessage(compoLoanUser);
		
		lbEditPenalty = new Label(compoLoanUser, SWT.NONE);
		addHeader(lbEditPenalty);
		
		Label separator = new Label(compoLoanUser,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
			
		User user = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findUserByPersonID(entityPenalty.getLoanUser().getId());
		lastString = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		
		personData = new Group(compoLoanUser, SWT.NORMAL);
		add(personData);
		
		leftList = new LinkedList<>();
		leftList.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftList.add(AbosMessages.get().LABEL_USER);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftList.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftList.add(AbosMessages.get().LABEL_STATE);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));

		List<String> rigthList = new LinkedList<>();
		rigthList.add(entityPenalty.getLoanUser().fullName());
		rigthList.add(entityPenalty.getLoanUser().getDNI());
		if (user != null) {
			rigthList.add(user.getUsername());
		} else
			rigthList.add(" - ");
		rigthList.add(entityPenalty.getLoanUser().getLoanUserCode());
		rigthList.add(entityPenalty.getLoanUser().getLoanUserType().getNomenclatorName());
		rigthList.add(entityPenalty.getLoanUser().getLoanUserState().getNomenclatorName());
		rigthList.add(Auxiliary.FormatDate(entityPenalty.getLoanUser().getExpirationDate()));
		
		grupControlsPenalty=CompoundGroup.printGroup(entityPenalty.getLoanUser().getPhoto().getImage(), personData, lastString, leftList, rigthList);
		// ----------------------------------------------------------------------
		Composite registerPenalty = new Composite(parent, SWT.NORMAL);
		addComposite(registerPenalty);
		registerPenalty.setData(RWT.CUSTOM_VARIANT, "gray_background");

		associatePenalty = new Composite(registerPenalty, SWT.NORMAL);
		addComposite(associatePenalty);
		associatePenalty.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		penaltyFragment = new PenaltyFragment(controller,entityPenalty,parent.getParent().getParent().getBounds().width);
		Composite compoP = (Composite) penaltyFragment.createUIControl(associatePenalty);
	
		controlsMaps = penaltyFragment.getControls();
		
		rdb = new Button(parent, SWT.RADIO);
		rdb = ((Button) controlsMaps.get("rdbSuspencion"));
	
		l10n();

		return parent;
	}
//FIXME METODO COMPLEJO
	@Override
	public Composite createButtons(Composite parent, final IGridViewEntity entity, final IVisualEntityManager manager) {
		
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		updateBtn = new Button(parent, SWT.PUSH);
		updateBtn.setText(AbosMessages.get().BUTTON_ACEPT);
		updateBtn.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "deprecation" })
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				int fromYear = ((DateTime) controlsMaps.get("dateTime")).getYear() - 1900;
				int fromMonth = ((DateTime) controlsMaps.get("dateTime")).getMonth();
				int fromDay = ((DateTime) controlsMaps.get("dateTime")).getDay();
				
				int fromYear1 = ((DateTime) controlsMaps.get("dateTime1")).getYear() - 1900;
				int fromMonth1= ((DateTime) controlsMaps.get("dateTime1")).getMonth();
				int fromDay1 = ((DateTime) controlsMaps.get("dateTime1")).getDay();
				
				Date startDate = new Date(fromYear, fromMonth,	fromDay);
				Date endDate = new Date(fromYear1, fromMonth1,	fromDay1);
					
				if (penaltyFragment.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}
				else if (penaltyFragment.getValidator().decorationFactory.AllControlDecorationsHide()) {
				
				Penalty entityPenalty = (Penalty) entity.getRow();				
				entityPenalty.setEffectiveDate(startDate);
				entityPenalty.setExpirationDate(endDate);
				entityPenalty.setMotivation(((Text) controlsMaps.get("txtReason")).getText().replaceAll(" +", " ").trim());
					
					if (rdb.getSelection() == true) {
						Nomenclator stateActivo = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.PENALTY_STATE_ACTIVE);
						Nomenclator typeSuspension = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.PENALTY_TYPE_SUSPENCION);

						entityPenalty.setPenaltyType(typeSuspension);
						entityPenalty.setPenaltyState(stateActivo);
					}
					else {

						Nomenclator typeFine = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.PENALTY_TYPE_FINE);
						Nomenclator statePending = ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByID( Nomenclator.PENALTY_STATE_PENDING_PAYMENT);
						
						Double amount = Double.parseDouble(((Text) controlsMaps.get("txt_monto")).getText());
						Nomenclator coinType = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("comboCoin"));
						entityPenalty.setPenaltyType(typeFine);
						entityPenalty.setPenaltyState(statePending);
						entityPenalty.setAmount(amount);
						entityPenalty.setCoinType(coinType);
					}

					Penalty penaltySaved = ((AllManagementLoanUserViewController) controller).getManagePenalty().addPenalty(entityPenalty);
					showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));
					
					manager.save(new BaseGridViewEntity<Penalty>(penaltySaved));
					manager.refresh();					
					
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
	public boolean isValid() {
		return false;
	}

	public Control getControl(String key) {
		//FIXME DEVOLVER UN CONTROL
		return null;
	}

	public void l10n() {
		lbEditPenalty.setText(MessageUtil.unescape(AbosMessages.get().UPDATE_PENALTY));		
		
		leftList.clear();
		leftList.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftList.add(AbosMessages.get().LABEL_USER);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftList.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftList.add(AbosMessages.get().LABEL_STATE);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		CompoundGroup.l10n(grupControlsPenalty, leftList);
		
		lastString = MessageUtil.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		personData.setText(lastString);
		
		penaltyFragment.l10n();
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public String getID() {
		return "updatePenaltyID";
	}
}