package cu.uci.abcd.administration.library.communFragment;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.CirculationRuleViewController;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
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

public class RegisterCirculationRuleFragment extends ContributorPage implements
		FragmentContributor {

	private Label registerCirculationRuleLabel;
	private ViewController controller;
	private Map<String, Control> controls = new HashMap<String, Control>();
	private Composite parent;
	private Label generalDataLabel;
	private Label materialTypeLabel;
	private Combo recordTypeCombo;
	private Combo loanUserTypeCombo;
	private Label loanUserTypeLabel;
	private Label circulationRuleStateLabel;
	private Button activeStateRadio;
	private Button inactiveStateRadio;
	private Label loanDataLabel;
	private Label quantityDayOnLoanLabel;
	private Text quantityDayOnLoanText;
	private Label quantityDayOnLoanIfQueueLabel;
	private Text quantityDayOnLoanIfQueueText;
	private Label quantityOfLoanAllowedLabel;
	private Text quantityOfLoanAllowedText;
	private Label quantityOfRenewedAllowedLabel;
	private Text quantityOfRenewedAllowedText;
	private Label quantityOfRenewedDayAllowedLabel;
	private Text quantityOfRenewedDayAllowedText;
	private Button reservationDelayAllowedButton;
	private Button severalMaterialsAllowed;
	private Label reserveDataLabel;
	private Button reservationAllowedButton;
	private Label quantityOfDayToPickUpTheMaterialLabel;
	private Text quantityOfDayToPickUpTheMaterialText;
	private Label penaltyDataLabel;
	private Label amountPenaltyLabel;
	private Text amountPenaltyText;
	private Label amountPenaltyByReservedLabel;
	private Text amountPenaltyByReservedText;
	private Label quantitySuspentionDayByDelayLabel;
	private Text quantitySuspentionDayByDelayText;
	private Label quantitySuspentionDayByDelayOfReservationLabel;
	private Text quantitySuspentionDayByDelayOfReservationText;
	private Label amountPenaltyByLostLabel;
	private Text amountPenaltyByLostText;
	private Label lostSuspensionDayLabel;
	private Text lostSuspensionDayText;
	private ValidatorUtils validator;
	private CirculationRule circulationRule;
	private PagePainter painter;
	private int dimension;
	private int option;
	private Library library;

	public RegisterCirculationRuleFragment(ViewController controller,
			CirculationRule circulationRule, int dimension, int option) {
		this.circulationRule = circulationRule;
		this.controller = controller;
		this.dimension = dimension;
		this.option = option;
	}

	public RegisterCirculationRuleFragment(ViewController controller,
			int dimension, int option) {
		this.setController(controller);
		this.dimension = dimension;
		this.option = option;
	}

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		painter = new FormPagePainter();
		validator = new ValidatorUtils(new CustomControlDecoration());
		painter.setDimension(dimension);
		painter.addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");
		parent = new Composite(shell, SWT.NORMAL);
		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		registerCirculationRuleLabel = new Label(parent, SWT.NONE);
		painter.addHeader(registerCirculationRuleLabel);
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);
		generalDataLabel = new Label(parent, SWT.NONE);
		painter.addHeader(generalDataLabel);
		materialTypeLabel = new Label(parent, SWT.NONE);
		painter.add(materialTypeLabel);
		recordTypeCombo = new Combo(parent, SWT.SIMPLE | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		controls.put("recordTypeCombo", recordTypeCombo);
		painter.add(recordTypeCombo);
		loanUserTypeLabel = new Label(parent, SWT.NONE);
		painter.add(loanUserTypeLabel);
		loanUserTypeCombo = new Combo(parent, SWT.SIMPLE | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		controls.put("loanUserTypeCombo", loanUserTypeCombo);
		painter.add(loanUserTypeCombo);
		painter.reset();
		circulationRuleStateLabel = new Label(parent, SWT.NONE);
		painter.add(circulationRuleStateLabel);
		activeStateRadio = new Button(parent, SWT.RADIO);
		activeStateRadio.setSelection(true);
		controls.put("activeStateRadio", activeStateRadio);
		painter.add(activeStateRadio);
		inactiveStateRadio = new Button(parent, SWT.RADIO);
		controls.put("inactiveStateRadio", inactiveStateRadio);
		painter.add(inactiveStateRadio);
		painter.reset();
		Label space = new Label(parent, SWT.NONE);
		painter.add(space, Percent.W100);
		Label separador = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separador);
		loanDataLabel = new Label(parent, SWT.NONE);
		painter.addHeader(loanDataLabel);
		quantityDayOnLoanLabel = new Label(parent, SWT.NONE);
		painter.add(quantityDayOnLoanLabel);
		quantityDayOnLoanText = new Text(parent, SWT.NONE);
		controls.put("quantityDayOnLoanText", quantityDayOnLoanText);
		painter.add(quantityDayOnLoanText);
		validator.applyValidator(quantityDayOnLoanText,
				"quantityDayOnLoanOnlyNumber",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		validator
				.applyValidator(quantityDayOnLoanText,
						"quantityDayOnLoanRequired",
						DecoratorType.REQUIRED_FIELD, true);
		quantityOfLoanAllowedLabel = new Label(parent, SWT.NONE);
		painter.add(quantityOfLoanAllowedLabel);
		quantityOfLoanAllowedText = new Text(parent, SWT.NONE);
		controls.put("quantityOfLoanAllowedText", quantityOfLoanAllowedText);
		painter.add(quantityOfLoanAllowedText);
		validator.applyValidator(quantityOfLoanAllowedText,
				"quantityOfLoanAllowedNumber",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		validator.applyValidator(quantityOfLoanAllowedText,
				"quantityOfLoanAllowedRequired", DecoratorType.REQUIRED_FIELD,
				true);
		painter.reset();
		quantityDayOnLoanIfQueueLabel = new Label(parent, SWT.NONE);
		painter.add(quantityDayOnLoanIfQueueLabel);
		quantityDayOnLoanIfQueueText = new Text(parent, SWT.NONE);
		controls.put("quantityDayOnLoanIfQueueText",
				quantityDayOnLoanIfQueueText);
		painter.add(quantityDayOnLoanIfQueueText);
		validator.applyValidator(quantityDayOnLoanIfQueueText,
				"quantityDayOnLoanIfQueueNumber",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		validator.applyValidator(quantityDayOnLoanIfQueueText,
				"quantityDayOnLoanIfQueueText", DecoratorType.REQUIRED_FIELD,
				true);
		quantityOfRenewedAllowedLabel = new Label(parent, SWT.NONE);
		painter.add(quantityOfRenewedAllowedLabel);
		quantityOfRenewedAllowedText = new Text(parent, SWT.NONE);
		controls.put("quantityOfRenewedAllowedText",
				quantityOfRenewedAllowedText);
		painter.add(quantityOfRenewedAllowedText);
		validator.applyValidator(quantityOfRenewedAllowedText,
				"quantityOfRenewedAllowedNumber",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		validator.applyValidator(quantityOfRenewedAllowedText,
				"quantityOfRenewedAllowedText", DecoratorType.REQUIRED_FIELD,
				true);
		painter.reset();
		quantityOfRenewedDayAllowedLabel = new Label(parent, SWT.NONE);
		painter.add(quantityOfRenewedDayAllowedLabel);
		quantityOfRenewedDayAllowedText = new Text(parent, SWT.NONE);
		controls.put("quantityOfRenewedDayAllowedText",
				quantityOfRenewedDayAllowedText);
		painter.add(quantityOfRenewedDayAllowedText);
		validator.applyValidator(quantityOfRenewedDayAllowedText,
				"quantityOfRenewedDayAllowedNumber",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		validator.applyValidator(quantityOfRenewedDayAllowedText,
				"quantityOfRenewedDayAllowedText",
				DecoratorType.REQUIRED_FIELD, true);
		painter.reset();
		reservationDelayAllowedButton = new Button(parent, SWT.CHECK);
		controls.put("reservationDelayAllowedButton",
				reservationDelayAllowedButton);
		painter.add(reservationDelayAllowedButton, Percent.W100);
		painter.reset();
		severalMaterialsAllowed = new Button(parent, SWT.CHECK);
		controls.put("severalMaterialsAllowed", severalMaterialsAllowed);
		painter.add(severalMaterialsAllowed, Percent.W100);
		Label separador1 = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separador1);
		reserveDataLabel = new Label(parent, SWT.NONE);
		painter.addHeader(reserveDataLabel);
		reservationAllowedButton = new Button(parent, SWT.CHECK);
		controls.put("reservationAllowedButton", reservationAllowedButton);
		painter.add(reservationAllowedButton);
		painter.reset();
		quantityOfDayToPickUpTheMaterialLabel = new Label(parent, SWT.CHECK);
		painter.add(quantityOfDayToPickUpTheMaterialLabel, Percent.W33);
		quantityOfDayToPickUpTheMaterialText = new Text(parent, SWT.NONE);
		controls.put("quantityOfDayToPickUpTheMaterialText",
				quantityOfDayToPickUpTheMaterialText);
		painter.add(quantityOfDayToPickUpTheMaterialText);
		validator.applyValidator(quantityOfDayToPickUpTheMaterialText,
				"quantityOfDayToPickUpTheMaterialNumber",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		validator.applyValidator(quantityOfDayToPickUpTheMaterialText,
				"quantityOfDayToPickUpTheMaterialText",
				DecoratorType.REQUIRED_FIELD, true);
		Label spaces = new Label(parent, SWT.NONE);
		painter.add(spaces, Percent.W100);
		painter.reset();
		Label separador2 = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separador2);
		penaltyDataLabel = new Label(parent, SWT.NONE);
		painter.addHeader(penaltyDataLabel);
		amountPenaltyLabel = new Label(parent, SWT.WRAP);
		painter.add(amountPenaltyLabel);
		amountPenaltyText = new Text(parent, SWT.NONE);
		controls.put("amountPenaltyText", amountPenaltyText);
		painter.add(amountPenaltyText);
		validator.applyValidator(amountPenaltyText, "amountPenaltyNumber",
				DecoratorType.DOUBLE, true, 6);
		validator.applyValidator(amountPenaltyText, "amountPenaltyText",
				DecoratorType.REQUIRED_FIELD, true);
		quantitySuspentionDayByDelayLabel = new Label(parent, SWT.WRAP);
		painter.add(quantitySuspentionDayByDelayLabel);
		quantitySuspentionDayByDelayText = new Text(parent, SWT.NONE);
		controls.put("quantitySuspentionDayByDelayText",
				quantitySuspentionDayByDelayText);
		painter.add(quantitySuspentionDayByDelayText);
		validator.applyValidator(quantitySuspentionDayByDelayText,
				"quantitySuspentionDayByDelayNumber",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		validator.applyValidator(quantitySuspentionDayByDelayText,
				"quantitySuspentionDayByDelayText",
				DecoratorType.REQUIRED_FIELD, true);
		painter.reset();
		amountPenaltyByReservedLabel = new Label(parent, SWT.WRAP);
		painter.add(amountPenaltyByReservedLabel);
		amountPenaltyByReservedText = new Text(parent, SWT.NONE);
		controls.put("amountPenaltyByReservedText", amountPenaltyByReservedText);
		painter.add(amountPenaltyByReservedText);
		validator.applyValidator(amountPenaltyByReservedText,
				"amountPenaltyByReservedNumber", DecoratorType.DOUBLE, true, 6);
		validator.applyValidator(amountPenaltyByReservedText,
				"amountPenaltyByReservedText", DecoratorType.REQUIRED_FIELD,
				true);
		quantitySuspentionDayByDelayOfReservationLabel = new Label(parent,
				SWT.WRAP);
		painter.add(quantitySuspentionDayByDelayOfReservationLabel);
		quantitySuspentionDayByDelayOfReservationText = new Text(parent,
				SWT.NONE);
		controls.put("quantitySuspentionDayByDelayOfReservationText",
				quantitySuspentionDayByDelayOfReservationText);
		painter.add(quantitySuspentionDayByDelayOfReservationText);
		validator.applyValidator(quantitySuspentionDayByDelayOfReservationText,
				"quantitySuspentionDayByDelayOfReservationNumber",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		validator.applyValidator(quantitySuspentionDayByDelayOfReservationText,
				"quantitySuspentionDayByDelayOfReservationText",
				DecoratorType.REQUIRED_FIELD, true);
		painter.reset();
		amountPenaltyByLostLabel = new Label(parent, SWT.NONE);
		painter.add(amountPenaltyByLostLabel);
		amountPenaltyByLostText = new Text(parent, SWT.NONE);
		controls.put("amountPenaltyByLostText", amountPenaltyByLostText);
		painter.add(amountPenaltyByLostText);
		validator.applyValidator(amountPenaltyByLostText,
				"amountPenaltyByLostNumber", DecoratorType.DOUBLE, true, 6);
		validator.applyValidator(amountPenaltyByLostText,
				"amountPenaltyByLostText", DecoratorType.REQUIRED_FIELD, true);
		lostSuspensionDayLabel = new Label(parent, SWT.NONE);
		painter.add(lostSuspensionDayLabel);
		lostSuspensionDayText = new Text(parent, SWT.NONE);
		controls.put("lostSuspensionDayText", lostSuspensionDayText);
		painter.add(lostSuspensionDayText);
		validator.applyValidator(lostSuspensionDayText,
				"lostSuspensionDayNumber",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		validator.applyValidator(lostSuspensionDayText,
				"lostSuspensionDayText", DecoratorType.REQUIRED_FIELD, true);
		painter.reset();
		initialize(
				recordTypeCombo,
				((CirculationRuleViewController) controller)
						.getAllManagementLibraryViewController()
						.getLibraryService()
						.findNomenclatorByCode(library.getLibraryID(),
								Nomenclator.LOANOBJECT_TYPE));
		initialize(
				loanUserTypeCombo,
				((CirculationRuleViewController) controller)
						.getAllManagementLibraryViewController()
						.getLibraryService()
						.findNomenclatorByCode(library.getLibraryID(),
								Nomenclator.LOANUSER_TYPE));
		validator.applyValidator(loanUserTypeCombo, "loanUserTypeCombo",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(recordTypeCombo, "recordTypeCombo",
				DecoratorType.REQUIRED_FIELD, true);
		LoadCirculationRuleData();
		l10n();
		return shell;
	}

	private void LoadCirculationRuleData() {
		if (circulationRule != null) {
			UiUtils.selectValue(recordTypeCombo,
					circulationRule.getRecordType());
			UiUtils.selectValue(loanUserTypeCombo,
					circulationRule.getLoanUserType());
			Nomenclator activeCirculationRule = ((CirculationRuleViewController) controller)
					.getAllManagementLibraryViewController()
					.getLibraryService()
					.findNomenclatorById(
							Nomenclator.CIRCULATION_RULE_STATE_ACTIVE);
			if (circulationRule.getCirculationRuleState().equals(
					activeCirculationRule)) {
				activeStateRadio.setSelection(true);
				inactiveStateRadio.setSelection(false);
			} else {
				activeStateRadio.setSelection(false);
				inactiveStateRadio.setSelection(true);
			}
			quantityDayOnLoanText.setText(circulationRule
					.getQuantityDayOnLoan().toString());
			quantityOfLoanAllowedText.setText(circulationRule
					.getQuantityOfLoanAllowed().toString());
			quantityDayOnLoanIfQueueText.setText(circulationRule
					.getQuantityDayOnLoanIfQueue().toString());
			quantityOfRenewedAllowedText.setText(circulationRule
					.getQuantityOfRenewedAllowed().toString());
			quantityOfRenewedDayAllowedText.setText(circulationRule
					.getQuantityOfRenewedDayAllowed().toString());
			if (circulationRule.isReservationDelayAllowed()) {
				reservationDelayAllowedButton.setSelection(true);
			} else {
				reservationDelayAllowedButton.setSelection(false);
			}

			if (circulationRule.isSeveralMaterialsAllowed()) {
				severalMaterialsAllowed.setSelection(true);
			} else {
				severalMaterialsAllowed.setSelection(false);
			}
			if (circulationRule.isReservationAllowed()) {
				reservationAllowedButton.setSelection(true);
			} else {
				reservationAllowedButton.setSelection(false);
			}
			quantityOfDayToPickUpTheMaterialText.setText(circulationRule
					.getQuantityOfDayToPickUpTheMaterial().toString());
			amountPenaltyText.setText(Auxiliary.doubleValue(amountPenaltyText,
					circulationRule.getAmountPenalty()));
			quantitySuspentionDayByDelayText.setText(circulationRule
					.getQuantitySuspentionDayByDelay().toString());
			amountPenaltyByReservedText.setText(Auxiliary.doubleValue(
					amountPenaltyByReservedText,
					circulationRule.getAmountPenaltyByReserved()));
			quantitySuspentionDayByDelayOfReservationText
					.setText(circulationRule
							.getQuantitySuspentionDayByDelayOfReservation()
							.toString());
			amountPenaltyByLostText.setText(Auxiliary.doubleValue(
					amountPenaltyByLostText,
					circulationRule.getAmountPenaltyByLost()));
			lostSuspensionDayText.setText(circulationRule
					.getLostSuspensionDay().toString());
		}

	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		if (option == 1) {
			registerCirculationRuleLabel
					.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_CIRCULATION_RULE));
		} else {
			registerCirculationRuleLabel
					.setText(MessageUtil.unescape(AbosMessages.get().LABEL_UPDATE_CIRCULATION_RULE));
		}
		generalDataLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_GENERAL_DATA));
		materialTypeLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_MATERIAL));
		loanUserTypeLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_USER));
		circulationRuleStateLabel.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_STATE_OF_THE_RULE));
		activeStateRadio
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACTIVE));
		inactiveStateRadio
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_INACTIVE));
		loanDataLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LOAN_DATA));
		quantityOfLoanAllowedLabel.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_LOANS_ALLOWED));
		quantityDayOnLoanLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NORMAL_TIME_LAPSE));
		quantityDayOnLoanIfQueueLabel.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_TIME_LAPSE_IN_QUEUE));
		quantityOfRenewedAllowedLabel.setText(MessageUtil.unescape(AbosMessages
				.get().PERMITTED_RENEWALS));
		quantityOfRenewedDayAllowedLabel.setText(MessageUtil
				.unescape(AbosMessages.get().UNIT));
		reservationDelayAllowedButton.setText(MessageUtil.unescape(AbosMessages
				.get().RESERVATIONS_ALLOW_EVEN_IF_USER_IS_LATE));
		severalMaterialsAllowed
				.setText(MessageUtil.unescape(AbosMessages.get().LOAN_SEVERAL_COPIES_OF_THE_SAME_RECORD));
		reserveDataLabel
				.setText(MessageUtil.unescape(AbosMessages.get().FACTS_RESERVATION));
		reservationAllowedButton.setText(MessageUtil.unescape(AbosMessages
				.get().ALLOW_RESERVATIONS));
		quantityOfDayToPickUpTheMaterialLabel
				.setText(MessageUtil.unescape(AbosMessages.get().DAYS_TO_REMOVE_CLASSIFIED_MATERIAL));
		penaltyDataLabel
				.setText(MessageUtil.unescape(AbosMessages.get().FACTS_PUNISHMENT_OR_PENALTY));
		amountPenaltyLabel
				.setText(MessageUtil.unescape(AbosMessages.get().PENALTY_FOR_DELAY_DAY));
		quantitySuspentionDayByDelayLabel.setText(MessageUtil
				.unescape(AbosMessages.get().DAY_SUSPENSION_FOR_DAYS_LATE));
		amountPenaltyByReservedLabel.setText(MessageUtil.unescape(AbosMessages
				.get().DAY_PENALTY_FOR_DELAY_IF_RESERVED));
		quantitySuspentionDayByDelayOfReservationLabel
				.setText(MessageUtil.unescape(AbosMessages.get().DAY_SUSPENSION_FOR_DAYS_PAST_DUE_IF_RESERVED));
		amountPenaltyByLostLabel.setText(MessageUtil.unescape(AbosMessages
				.get().PENALTY_FOR_LOSS));
		lostSuspensionDayLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LOST_SUSPENSION_DAY));
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

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public Composite getParent() {
		return parent;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public void setViewController(ViewController controller) {
		this.setController(controller);
	}
}
