package cu.uci.abcd.statistic.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.statistic.ui.controller.AllManagementController;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class IndicatorAddEditableArea extends BaseEditableArea {
	private Label headerLabel;
	private Label indicatorData;
	private Label nameIndicator;
	private Label numIndicator;
	private Label consulQuery;
	private Text textConsultQuery;
	private Text textNameIndicator;
	private Text textNumIndicator;

	private Button saveBtn;
	private Composite topGroup;
	private String orderByString = "indicatorId";
	private int direction = 1024;
	private Composite top;
	

	public IndicatorAddEditableArea(ViewController controller) {
		super();
		this.controller = controller;
	}

	@Override
	public String getID() {
		return "addIndicatorID";
	}

	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager arg2) {
		top = parent;
		setDimension(parent.getParent().getParent().getBounds().width);
		addComposite(parent);

		topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(topGroup);
		
		buildMessage(topGroup);
		
		headerLabel = new Label(topGroup, SWT.NORMAL);
		addHeader(headerLabel);

		addSeparator(new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL));

		indicatorData = new Label(topGroup, SWT.NORMAL);
		indicatorData.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		addHeader(indicatorData);

		nameIndicator = new Label(topGroup, SWT.NONE);
		add(nameIndicator);

		textNameIndicator = new Text(topGroup, SWT.NONE);
		add(textNameIndicator);
		validator.applyValidator(textNameIndicator, 10000);
		validator.applyValidator(textNameIndicator, "textNameIndicator1", DecoratorType.REQUIRED_FIELD, true);

		numIndicator = new Label(topGroup, SWT.NONE);
		add(numIndicator);

		textNumIndicator = new Text(topGroup, SWT.NONE);
		add(textNumIndicator);
		validator.applyValidator(textNumIndicator, "textNumIndicator", DecoratorType.NUMBER_POINT_ONLY, true, 10000 );
		validator.applyValidator(textNumIndicator, "textNumIndicator1", DecoratorType.REQUIRED_FIELD, true);
		br();

		consulQuery = new Label(topGroup, SWT.NONE);
		add(consulQuery);

		textConsultQuery = new Text(topGroup, SWT.V_SCROLL | SWT.WRAP);
		validator.applyValidator(textConsultQuery, 10000);
		add(textConsultQuery);

		return parent;
	}

	@Override
	public Composite createButtons(final Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {

		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED);
				} else if (validator.decorationFactory.AllControlDecorationsHide()) {

					Indicator indicator = new Indicator();

					indicator.setNameIndicator(textNameIndicator.getText().replaceAll(" +", " ").trim());
					indicator.setIndicatorId(textNumIndicator.getText().replaceAll(" +", " ").trim());
					indicator.setQueryText(textConsultQuery.getText().replaceAll(" +", " ").trim());

					
					Indicator exist = ((AllManagementController) controller).getIndicator().findIndicatorByNumberAndName(indicator.getIndicatorId(), indicator.getNameIndicator());
					if (exist == null) {
						if (((AllManagementController) controller).getIndicator().validate(indicator)) {
							((AllManagementController) controller).getIndicator().addIndicator(indicator);
							manager.add(new BaseGridViewEntity<Indicator>(indicator));
							manager.refresh();
						} else {
							showErrorMessage(AbosMessages.get().MESSAGE_INCORRECT_SQL);
						}
					} else {
						showErrorMessage(AbosMessages.get().MESSAGE_NO_ADD);
					}

				} else {
					showErrorMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA);
				}
			}
		});
		return parent;
	}

	public void dispose() {
		if (top != null && !top.isDisposed()) {
			top.dispose();
		}
	}

	@Override
	public void l10n() {
		indicatorData.setText(AbosMessages.get().LABEL_INDICATOR_DATA);
		nameIndicator.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		consulQuery.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CONSULT_QUERY));
		numIndicator.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR));
		headerLabel.setText(AbosMessages.get().LABEL_ADD_INDICATOR);
		saveBtn.setText(AbosMessages.get().BUTTON_ACEPT);
	}
}
