package cu.uci.abcd.administration.library.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.FineEquationViewController;
import cu.uci.abcd.domain.management.library.FineEquation;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ConfigureFineEquation extends ContributorPage {
	Label configureFineEquationLabel;
	private ViewController controller;
	private Text mma;
	private Text mmp;
	private Library library;
	private ValidatorUtils validator;
	private Label delayLabel;
	private Label fineAmountMMALabel;
	private Label fineAmountMMPLabel;
	private Label lossLabel;
	private Label equationTotalAmountPayableMMALabel;
	private Label equationTotalAmountPayableMMPLabel;
	private Label forAMaterialThatIsNotReserved;
	private Label forAMaterialThatIsReserved;
	private Label mtpLabel;
	private Label legendLabel;
	private Label totalAmountPayableLabel;
	private Label firstLegendLabel;
	private Label secondLegendLabel;
	private Label thirdLegendLabel;
	private Label fourthLegendLabel;
	private Label fivethLegendLabel;
	private Button acceptButton;
	private Button cancelButton;
	private ConfigureFineEquation configureFineEquation;
	private Composite test;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().PENALTY_EQUATION);
	}

	@Override
	public String getID() {
		return "configureFineEquationID";
	}

	@Override
	public Control createUIControl(Composite shell) {
		this.configureFineEquation = this;
		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		validator = new ValidatorUtils(new CustomControlDecoration());
		FormLayout form = new FormLayout();
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");
		shell.setLayout(form);
		Composite scroll = new Composite(shell, SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0)
				.withHeight(Display.getCurrent().getBounds().height - 172);
		test = new Composite(shell, SWT.NORMAL);
		test.setData(RWT.CUSTOM_VARIANT, "gray_background");
		test.setLayout(form);
		FormDatas.attach(test).atLeft(0).atRight(0).atTopTo(shell);
		configureFineEquationLabel = new Label(test, SWT.NONE);
		configureFineEquationLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(configureFineEquationLabel).atTopTo(test, 25)
				.atLeft(15);
		Label separator = new Label(test, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(separator).atTopTo(configureFineEquationLabel, 12)
				.atLeft(15).atRight(15);
		delayLabel = new Label(test, SWT.NONE);
		FormDatas.attach(delayLabel).atTopTo(separator, 25).atLeft(15);
		fineAmountMMALabel = new Label(test, SWT.NONE);
		FormDatas.attach(fineAmountMMALabel).atTopTo(delayLabel, 5).atLeft(15);
		mma = new Text(test, SWT.NONE);
		FormDatas.attach(mma).atTopTo(fineAmountMMALabel, 5).atLeft(15)
				.withWidth(100);
		validator.applyValidator(mma, "mmaNumber", DecoratorType.DOUBLE, true,
				6);
		validator
				.applyValidator(mma, "mma", DecoratorType.REQUIRED_FIELD, true);
		lossLabel = new Label(test, SWT.NONE);
		FormDatas.attach(lossLabel).atTopTo(mma, 5).atLeft(15);

		fineAmountMMPLabel = new Label(test, SWT.NONE);
		FormDatas.attach(fineAmountMMPLabel).atTopTo(lossLabel, 5).atLeft(15);
		mmp = new Text(test, SWT.NONE);
		FormDatas.attach(mmp).atTopTo(fineAmountMMPLabel, 5).atLeft(15)
				.withWidth(100);
		validator.applyValidator(mmp, "mmpNumber", DecoratorType.DOUBLE, true,
				6);
		validator
				.applyValidator(mmp, "mmp", DecoratorType.REQUIRED_FIELD, true);
		equationTotalAmountPayableMMALabel = new Label(test, SWT.NORMAL);
		FormDatas.attach(equationTotalAmountPayableMMALabel)
				.atTopTo(delayLabel, 5).atLeftTo(fineAmountMMALabel, 30);
		forAMaterialThatIsNotReserved = new Label(test, SWT.NORMAL);
		FormDatas.attach(forAMaterialThatIsNotReserved)
				.atTopTo(equationTotalAmountPayableMMALabel, 5)
				.atLeftTo(fineAmountMMALabel, 30);
		forAMaterialThatIsReserved = new Label(test, SWT.NORMAL);
		FormDatas.attach(forAMaterialThatIsReserved)
				.atTopTo(forAMaterialThatIsNotReserved, 5)
				.atLeftTo(fineAmountMMALabel, 30);
		equationTotalAmountPayableMMPLabel = new Label(test, SWT.NORMAL);
		FormDatas.attach(equationTotalAmountPayableMMPLabel)
				.atTopTo(lossLabel, 5).atLeftTo(fineAmountMMPLabel, 30);
		mtpLabel = new Label(test, SWT.NORMAL);
		FormDatas.attach(mtpLabel)
				.atTopTo(equationTotalAmountPayableMMPLabel, 5)
				.atLeftTo(fineAmountMMPLabel, 30);
		Label separador = new Label(test, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(separador).atTopTo(mmp, 10).atLeft(15).atRight(15);
		legendLabel = new Label(test, SWT.NORMAL);
		FormDatas.attach(legendLabel).atTopTo(separador, 5).atLeft(15);
		totalAmountPayableLabel = new Label(test, SWT.NORMAL);
		FormDatas.attach(totalAmountPayableLabel).atTopTo(legendLabel, 5)
				.atLeft(15);
		firstLegendLabel = new Label(test, SWT.NORMAL);
		FormDatas.attach(firstLegendLabel).atTopTo(totalAmountPayableLabel, 5)
				.atLeft(15);
		secondLegendLabel = new Label(test, SWT.NORMAL);
		FormDatas.attach(secondLegendLabel).atTopTo(firstLegendLabel, 5)
				.atLeft(15);
		thirdLegendLabel = new Label(test, SWT.NORMAL);
		FormDatas.attach(thirdLegendLabel).atTopTo(secondLegendLabel, 5)
				.atLeft(15);
		fourthLegendLabel = new Label(test, SWT.NORMAL);
		FormDatas.attach(fourthLegendLabel).atTopTo(thirdLegendLabel, 5)
				.atLeft(15);
		fivethLegendLabel = new Label(test, SWT.NORMAL);
		FormDatas.attach(fivethLegendLabel).atTopTo(fourthLegendLabel, 5)
				.atLeft(15);
		Label separador1 = new Label(test, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(separador1).atTopTo(fivethLegendLabel, 10).atLeft(15)
				.atRight(15);
		cancelButton = new Button(test, SWT.PUSH);
		FormDatas.attach(cancelButton).atTopTo(separador1, 5).atRight(15)
				.withHeight(23);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialogUtil.openConfirm(Display.getCurrent()
						.getActiveShell(),
						MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages
								.get().MESSAGE_QUESTION), MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_WARN_CANCEL_OPERATION),
						new DialogCallback() {
							private static final long serialVersionUID = 1L;

							@Override
							public void dialogClosed(int returnCode) {
								if (returnCode == 0) {
									try {
										configureFineEquation.notifyListeners(
												SWT.Dispose, new Event());
									} catch (Exception e2) {
										RetroalimentationUtils
												.showInformationShellMessage((cu.uci.abos.core.l10n.AbosMessages
														.get().MESSAGE_ERROR_USED_DATA));
									}
								}
							}
						});
			}
		});

		acceptButton = new Button(test, SWT.PUSH);
		FormDatas.attach(acceptButton).atTopTo(separador1, 5)
				.atRightTo(cancelButton, 15).withHeight(23);
		acceptButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5678610456502374298L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (getValidator().decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
					if (getValidator().decorationFactory
							.AllControlDecorationsHide()) {
						FineEquation penaltyEquation = ((FineEquationViewController) controller)
								.findFineEquationByLibrary(library
										.getLibraryID());
						if (penaltyEquation == null) {
							penaltyEquation = new FineEquation();
						}
						penaltyEquation.setDelayAmount(mma.getText());
						penaltyEquation.setLostAmount(mmp.getText());
						penaltyEquation.setLibrary(library);

						((FineEquationViewController) controller)
								.saveFineEquation(penaltyEquation);

						RetroalimentationUtils.showInformationMessage(MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_INF_UPDATE_DATA));

					} else {
						RetroalimentationUtils.showErrorShellMessage(MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_ERROR_INCORRECT_DATA));
					}
				}
			}
		});
		LoadPenaltyEquation();
		l10n();
		return shell;
	}

	private void LoadPenaltyEquation() {
		FineEquation fineEquation = ((FineEquationViewController) controller)
				.findFineEquationByLibrary(library.getLibraryID());
		if (fineEquation != null) {
			mma.setText(fineEquation.getDelayAmountToString());
			mmp.setText(fineEquation.getLostAmountToString());
		}
	}

	@Override
	public boolean canClose() {
		addListener(SWT.Dispose, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {

			}
		});
		return false;
	}

	@Override
	public void l10n() {
		configureFineEquationLabel.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_CONFIGURE_PENALTY_EQUATION));
		delayLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DELAY));
		fineAmountMMALabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FINE_AMOUNT_MMA));
		lossLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LOSS));
		fineAmountMMPLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FINE_AMOUNT_MMP));
		equationTotalAmountPayableMMALabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EQUATION_TOTAL_AMOUNT_PAYABLE_MMA));
		forAMaterialThatIsNotReserved.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_FOR_MATERIAL_THAT_IS_NOT_RRSERVED));
		forAMaterialThatIsReserved.setText(MessageUtil.unescape(AbosMessages
				.get().LABEL_FOR_MATERIAL_THAT_IS_RESERVED));
		equationTotalAmountPayableMMPLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EQUATION_TOTAL_AMOUNT_PAYABLEMMP));
		mtpLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_MTP));
		legendLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LEGEND));
		totalAmountPayableLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_AMOUNT_PAYABLE));
		firstLegendLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_LEGEND));
		secondLegendLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SECOND_LEGEND));
		thirdLegendLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_THIRD_LEGEND));
		fourthLegendLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FOURTH_LEGEND));
		fivethLegendLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FIVETH_LEGEND));
		acceptButton.setText(AbosMessages.get().BUTTON_ACEPT);
		cancelButton.setText(AbosMessages.get().BUTTON_CANCEL);
	}

	@Override
	public void setViewController(ViewController controller) {
		this.controller = controller;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

}