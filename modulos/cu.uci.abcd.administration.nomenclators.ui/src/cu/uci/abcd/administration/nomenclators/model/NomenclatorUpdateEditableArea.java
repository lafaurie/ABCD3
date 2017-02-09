package cu.uci.abcd.administration.nomenclators.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.nomenclators.controller.ControllerNomenclator;
import cu.uci.abcd.administration.nomenclators.l10n.AbosMessages;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * 
 * @author Dayana Rivera
 * 
 */

public class NomenclatorUpdateEditableArea extends BaseEditableArea {
	private IGridViewEntity fdtEntity;
	private Library library;
	private Label headerLabel;
	private Label nomenclatorData;
	private Label nomenclatorTypeLabel;
	private Label nomenclatorValue;
	private Label nomenclatorDescription;
	private Text textNomenclatorDescription;
	private Text textNomenclatorName;
	private Composite top;
	private Composite topGroup;
	private Button saveBtn;

	public NomenclatorUpdateEditableArea(ViewController controller) {
		super();
		this.controller = controller;
	}

	@Override
	public String getID() {
		return "updateNomenclatorID";
	}

	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {

		fdtEntity =  entity;
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
		add(nomenclatorData);
		br();
		nomenclatorTypeLabel = new Label(topGroup, SWT.NORMAL);
		Label nomenclatorType = new Label(topGroup, SWT.LEFT);
		nomenclatorType.setText(((Nomenclator) fdtEntity.getRow()).getNomenclator().getNomenclatorName());
		add(nomenclatorTypeLabel);
		add(nomenclatorType);
		nomenclatorType.setAlignment(SWT.LEFT);
		br();

		nomenclatorValue = new Label(topGroup, SWT.NONE);
		add(nomenclatorValue);

		textNomenclatorName = new Text(topGroup, SWT.NORMAL);

		add(textNomenclatorName);

		validator.applyValidator(textNomenclatorName, "textNomenclatorName", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(textNomenclatorName, "textNomenclatorName1", DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);
		textNomenclatorName.setText(((Nomenclator) fdtEntity.getRow()).getNomenclatorName());

		nomenclatorDescription = new Label(topGroup, SWT.NONE);
		add(nomenclatorDescription);
		textNomenclatorDescription = new Text(topGroup, SWT.WRAP | SWT.V_SCROLL);
		validator.applyValidator(textNomenclatorDescription, 500);
		add(textNomenclatorDescription);
		textNomenclatorDescription.setText(((Nomenclator) fdtEntity.getRow()).getNomenclatorDescription());
		// FIXME MAL USO DE VALORES

		return parent;
	}

	@Override
	public Composite createButtons(final Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {

		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveBtn = new Button(parent, SWT.PUSH);

		saveBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorMessage(topGroup, MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else if (validator.decorationFactory.AllControlDecorationsHide()) {
					library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

					Nomenclator auxNomenclator = ((ControllerNomenclator) controller).searchNomenclator(library.getLibraryID(), ((Nomenclator) fdtEntity.getRow()).getNomenclatorID());
					auxNomenclator.setNomenclatorName(textNomenclatorName.getText().replaceAll(" +", " ").trim());
					auxNomenclator.setNomenclatorDescription(textNomenclatorDescription.getText().replaceAll(" +", " ").trim());

					Nomenclator exist = ((ControllerNomenclator) controller).findNomenclatorsByLibraryAndParentAndNomenclatorName(library.getLibraryID(),
							((Nomenclator) fdtEntity.getRow()).getNomenclator().getNomenclatorID(), textNomenclatorName.getText());

					if (exist == null || exist.getNomenclatorID().equals(auxNomenclator.getNomenclatorID())) {
						((ControllerNomenclator) controller).registerNomenclator(auxNomenclator);
						((Nomenclator) fdtEntity.getRow()).setNomenclatorName(textNomenclatorName.getText().replaceAll(" +", " ").trim());
						((Nomenclator) fdtEntity.getRow()).setNomenclatorDescription(textNomenclatorDescription.getText().replaceAll(" +", " ").trim());
						manager.save(fdtEntity);
						manager.refresh();
					} else {
						showErrorMessage(AbosMessages.get().MESSAGE_NO_ADD);
					}
				} else {
					showErrorMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

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
		headerLabel.setText(AbosMessages.get().NAME_UI_UPDATE_NOMENCLATOR);
		nomenclatorData.setText(AbosMessages.get().LABEL_NOMENCLATOR_DATA);
		nomenclatorTypeLabel.setText(AbosMessages.get().LABEL_NOMENCLATOR_TYPE + ":");
		nomenclatorValue.setText(AbosMessages.get().LABEL_NOMENCLATOR_VALUE);
		nomenclatorDescription.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION));
		saveBtn.setText(AbosMessages.get().BUTTON_ACEPT);
	}

}
