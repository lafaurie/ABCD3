package cu.uci.abcd.administration.nomenclators.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.nomenclators.controller.ControllerNomenclator;
import cu.uci.abcd.administration.nomenclators.l10n.AbosMessages;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * 
 * @author Dayana Rivera
 */
public class NomenclatorAddEditableArea extends BaseEditableArea {
	private Library library;
	private Label headerLabel;
	private Label nomenclatorData;
	private Combo comboNomenclatorType;
	private Label nomenclatorType;
	private Label nomenclatorValue;
	private Text textnomenclatorValue;
	private Label nomenclatorDescription;
	private Text textNomenclatorDescription;
	private Button saveBtn;
	private Composite topGroup;
	private List<Nomenclator> nomenclatorTypes;
	private Composite top;


	public NomenclatorAddEditableArea(ViewController controller) {
		super();
		this.controller = controller;
	}

	@Override
	public String getID() {
		return "addNomenclatorID";
	}

	public NomenclatorAddEditableArea(ViewController controller, List<Nomenclator> nomenclatorTypes) {
		super();
		this.controller = controller;
		this.nomenclatorTypes = new ArrayList<Nomenclator>(nomenclatorTypes);
	
	}

	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		top = parent;
		setDimension(parent.getParent().getParent().getBounds().width);
		addComposite(parent);
		topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(topGroup);

		buildMessage(topGroup);

		headerLabel = new Label(topGroup, SWT.NORMAL);
		addHeader(headerLabel);

		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		nomenclatorData = new Label(topGroup, SWT.NORMAL);
		nomenclatorData.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		addHeader(nomenclatorData);
		nomenclatorType = new Label(topGroup, SWT.NONE);
		add(nomenclatorType);

		comboNomenclatorType = new Combo(topGroup, SWT.READ_ONLY);
		add(comboNomenclatorType);
		initialize(comboNomenclatorType, nomenclatorTypes);
		validator.applyValidator(comboNomenclatorType, "comboNomenclatorType", DecoratorType.REQUIRED_FIELD, true);

		nomenclatorValue = new Label(topGroup, SWT.NONE);
		add(nomenclatorValue);

		textnomenclatorValue = new Text(topGroup, SWT.NONE);
		add(textnomenclatorValue);
		validator.applyValidator(textnomenclatorValue, "textnomenclatorValue", DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);
		validator.applyValidator(textnomenclatorValue, "textnomenclatorValue1", DecoratorType.REQUIRED_FIELD, true);
		br();

		nomenclatorDescription = new Label(topGroup, SWT.NORMAL);
		add(nomenclatorDescription);

		textNomenclatorDescription = new Text(topGroup, SWT.WRAP | SWT.V_SCROLL);
		validator.applyValidator(textNomenclatorDescription, 500);
		add(textNomenclatorDescription);
		

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

					library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

					Nomenclator auxNomenclator;

					auxNomenclator = new Nomenclator();

					auxNomenclator.setNomenclatorName(textnomenclatorValue.getText().replaceAll(" +", " ").trim());
					auxNomenclator.setNomenclatorDescription(textNomenclatorDescription.getText().replaceAll(" +", " ").trim());
					auxNomenclator.setOwnerLibrary(library);

					Nomenclator exist = ((ControllerNomenclator) controller).findNomenclatorsByLibraryAndParentAndNomenclatorName(library.getLibraryID(),
							((Nomenclator) UiUtils.getSelected(comboNomenclatorType)).getNomenclatorID(), textnomenclatorValue.getText());

					if (exist == null ) {
						auxNomenclator.setNomenclator((Nomenclator) UiUtils.getSelected(comboNomenclatorType));
						((ControllerNomenclator) controller).registerNomenclator(auxNomenclator);
						manager.add(new BaseGridViewEntity<Nomenclator>(auxNomenclator));
						manager.refresh();
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
		headerLabel.setText(AbosMessages.get().NAME_UI_REGISTER_NOMENCLATOR);
		nomenclatorData.setText(AbosMessages.get().LABEL_NOMENCLATOR_DATA);
		nomenclatorType.setText(AbosMessages.get().LABEL_NOMENCLATOR_TYPE);
		nomenclatorValue.setText(AbosMessages.get().LABEL_NOMENCLATOR_VALUE);
		nomenclatorDescription.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION));
		saveBtn.setText(AbosMessages.get().BUTTON_ACEPT);

	}
}