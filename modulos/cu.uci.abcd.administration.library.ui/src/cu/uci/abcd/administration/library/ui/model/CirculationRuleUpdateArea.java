package cu.uci.abcd.administration.library.ui.model;

import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.communFragment.RegisterCirculationRuleFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.CirculationRuleViewController;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CirculationRuleUpdateArea extends BaseEditableArea{

	private RegisterCirculationRuleFragment saveCirculationRuleFragment;
	private Composite parentComposite;
	private Map<String, Control> controlsMaps;
	private CirculationRule circulationRule;
	private ViewController controller;
	private CRUDTreeTable tableCirculationRule;
	private int dimension;
	
	public CRUDTreeTable getTableCirculationRule() {
		return tableCirculationRule;
	}

	public void setTableCirculationRule(CRUDTreeTable tableCirculationRule) {
		this.tableCirculationRule = tableCirculationRule;
	}

	private Button saveButton;
	
	public CirculationRuleUpdateArea(ViewController controller,
			CRUDTreeTable tableCirculationRule) {
		this.controller = controller;
		this.tableCirculationRule = tableCirculationRule;
	}
	
	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		addComposite(parent);
			CirculationRule circulationRuleToView = (CirculationRule)entity.getRow();
			circulationRule = ((CirculationRuleViewController) controller).getCirculationRuleById(circulationRuleToView.getCirculationRuleID());
		saveCirculationRuleFragment = new RegisterCirculationRuleFragment(controller, circulationRule, dimension, 2);
		parentComposite = (Composite) saveCirculationRuleFragment
				.createUIControl(parent);
		controlsMaps = saveCirculationRuleFragment.getControls();
		return parentComposite;
	}
	
	@Override
	public Composite createButtons(final Composite parent,
			IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveButton = new Button(parent, SWT.PUSH);
		saveButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				Composite msg = saveCirculationRuleFragment.getParent();
				if (saveCirculationRuleFragment.getValidator().decorationFactory.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(
							//msg, 
							MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
				 if (saveCirculationRuleFragment.getValidator().decorationFactory.AllControlDecorationsHide()) {
					Combo recordTypeCombo = (Combo) controlsMaps.get("recordTypeCombo");
	            	Combo loanUserTypeCombo = (Combo) controlsMaps.get("loanUserTypeCombo");
	            	Button activeStateRadio = (Button) controlsMaps.get("activeStateRadio");
	            	Text quantityDayOnLoanText = (Text) controlsMaps.get("quantityDayOnLoanText");
	            	Text quantityDayOnLoanIfQueueText = (Text) controlsMaps.get("quantityDayOnLoanIfQueueText");
	            	Text quantityOfLoanAllowedText = (Text) controlsMaps.get("quantityOfLoanAllowedText");
	            	Text quantityOfRenewedAllowedText = (Text) controlsMaps.get("quantityOfRenewedAllowedText");
	            	Text quantityOfRenewedDayAllowedText = (Text) controlsMaps.get("quantityOfRenewedDayAllowedText");
	            	Button reservationDelayAllowedButton = (Button) controlsMaps.get("reservationDelayAllowedButton");
	            	Button severalMaterialsAllowed = (Button) controlsMaps.get("severalMaterialsAllowed");
	            	Button reservationAllowedButton = (Button) controlsMaps.get("reservationAllowedButton");
	            	Text quantityOfDayToPickUpTheMaterialText = (Text) controlsMaps.get("quantityOfDayToPickUpTheMaterialText");
	            	Text amountPenaltyText = (Text) controlsMaps.get("amountPenaltyText");
	            	Text amountPenaltyByReservedText = (Text) controlsMaps.get("amountPenaltyByReservedText");
	            	Text quantitySuspentionDayByDelayText = (Text) controlsMaps.get("quantitySuspentionDayByDelayText");
	            	Text quantitySuspentionDayByDelayOfReservationText = (Text) controlsMaps.get("quantitySuspentionDayByDelayOfReservationText");
	            	Text amountPenaltyByLostText = (Text) controlsMaps.get("amountPenaltyByLostText");
	            	Text lostSuspensionDayText = (Text) controlsMaps.get("lostSuspensionDayText");
                    Nomenclator recordType = (Nomenclator) UiUtils.getSelected(recordTypeCombo);
	            	Nomenclator loanUserType = (Nomenclator) UiUtils.getSelected(loanUserTypeCombo);
					Library library = (Library) SecurityUtils
							.getService().getPrincipal()
							.getByKey("library");
			        CirculationRule existCirculationRule = ((CirculationRuleViewController) controller).findByLoanUserTypeAndRecordTypeAndLibrary_LibraryID(loanUserType, recordType, library.getLibraryID());
					//List<CirculationRule> allCirculationRule = ((CirculationRuleViewController) controller).findCirculationRuleByLibrary(library.getLibraryID());
					circulationRule.setRecordType(recordType);
	               	circulationRule.setLoanUserType(loanUserType);
	               	circulationRule.setLibrary(library);
	               	if(activeStateRadio.getSelection()){
	               		Nomenclator activeCirculationRule = ((CirculationRuleViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById( Nomenclator.CIRCULATION_RULE_STATE_ACTIVE);
	               		circulationRule.setCirculationRuleState(activeCirculationRule);
	               	}else{
	               		Nomenclator inactiveCirculationRule = ((CirculationRuleViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorById( Nomenclator.CIRCULATION_RULE_STATE_INACTIVE);
	               		
	               		circulationRule.setCirculationRuleState(inactiveCirculationRule);
	               	}
	               	circulationRule.setQuantityDayOnLoan(Integer.parseInt(quantityDayOnLoanText.getText()));
	               	circulationRule.setQuantityDayOnLoanIfQueue(Integer.parseInt(quantityDayOnLoanIfQueueText.getText()));
	               	circulationRule.setQuantityOfLoanAllowed(Integer.parseInt(quantityOfLoanAllowedText.getText()));
	               	circulationRule.setQuantityOfRenewedAllowed(Integer.parseInt(quantityOfRenewedAllowedText.getText()));
	               	circulationRule.setQuantityOfRenewedDayAllowed(Integer.parseInt(quantityOfRenewedDayAllowedText.getText()));
	               	circulationRule.setLostSuspensionDay(Integer.parseInt(lostSuspensionDayText.getText()));
	               	if(reservationDelayAllowedButton.getSelection()){
	               		circulationRule.setReservationDelayAllowed(true);
	               	}else{
	               		circulationRule.setReservationDelayAllowed(false);
	               	}
	               	if(severalMaterialsAllowed.getSelection()){
	               		circulationRule.setSeveralMaterialsAllowed(true);
	               	}else{
	               		circulationRule.setSeveralMaterialsAllowed(false);
	               	}
	               	if(reservationAllowedButton.getSelection()){
	               		circulationRule.setReservationAllowed(true);
	               	}else{
	               		circulationRule.setReservationAllowed(false);
	               	}
	               	circulationRule.setQuantityOfDayToPickUpTheMaterial(Integer.parseInt(quantityOfDayToPickUpTheMaterialText.getText()));
	               	
	               	Double amountPenalty = Double.parseDouble(amountPenaltyText.getText().replace(",", "."));
	               	circulationRule.setAmountPenalty(amountPenalty);
	               	
	               	Double amountPenaltyByReserved = Double.parseDouble(amountPenaltyByReservedText.getText().replace(",", "."));
	               	circulationRule.setAmountPenaltyByReserved(amountPenaltyByReserved);
	               	
	               	circulationRule.setQuantitySuspentionDayByDelay(Integer.parseInt(quantitySuspentionDayByDelayText.getText()));
	               	
	               	circulationRule.setQuantitySuspentionDayByDelayOfReservation(Integer.parseInt(quantitySuspentionDayByDelayOfReservationText.getText()));
	               	
	               	Double amountPenaltyByLost = Double.parseDouble(amountPenaltyByLostText.getText().replace(",", "."));
	               	circulationRule.setAmountPenaltyByLost(amountPenaltyByLost);
	               
	               	if ( ( existCirculationRule == null )
							||  (   circulationRule.getCirculationRuleID() == existCirculationRule
									.getCirculationRuleID() && existCirculationRule!=null ) ) {
					CirculationRule circulationRuleSaved = ((CirculationRuleViewController) controller).saveCirculationRule(circulationRule);
					manager.save(new BaseGridViewEntity<CirculationRule>(
							circulationRuleSaved));
					manager.refresh();
					
					Composite viewSmg = ((CirculationRuleViewArea)tableCirculationRule.getActiveArea()).getMsg();
					
					RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages
									.get().MSG_INF_UPDATE_DATA));
				}else{
					RetroalimentationUtils.showErrorShellMessage(
							//msg, 
							MessageUtil
							.unescape(AbosMessages
									.get().ELEMENT_EXIST));
	               }
			        
				 }else {
						RetroalimentationUtils.showErrorShellMessage(
								//msg, 
								MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_ERROR_INCORRECT_DATA));
					}
			}
			}
		});
		return parent;
	}
	
	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {
		if(circulationRule==null){
			saveButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		}else{
		saveButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		}
		saveButton.getParent().layout(true, true);
		saveButton.getParent().redraw();
		saveButton.getParent().update();
		saveCirculationRuleFragment.l10n();
	}


	@Override
	public String getID() {
		return "updateCirculationRuleID";
	}
	
}
