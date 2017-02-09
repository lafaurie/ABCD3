package cu.uci.abcd.statistic.ui.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.common.FieldDefinitionTable;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.statistic.Variable;
import cu.uci.abcd.statistic.ui.controller.AllManagementController;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratedControl;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class VariableAddEditableArea extends BaseEditableArea {
	private Map<String, Control> controls;
	private ViewController controller;
	int page = 0;
	Library library;
	String libraryJisis;
	Label headerLabel;
	Label labelDatabaseName;

	Label field;
	Label leaded;
	Label prefix;
	Label formatOfExtraction;
	Combo comboField;
	Combo comboDatabase;

	Button salve;
	Button aceptar;
	Button cancelar;
	int direction = 1024;
	String orderByString = "variableCode";

	private CustomControlDecoration customControlDecorationFactory;

	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return false;
	}

	public VariableAddEditableArea(ViewController controller) {
		super();
		this.controller = controller;
		
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		libraryJisis = library.getIsisDefHome();

		customControlDecorationFactory = new CustomControlDecoration();

		FormLayout form = new FormLayout();
		parent.setLayout(form);
		FormDatas.attach(parent).atLeft(0).atRight(0).atTop(10);

		int total = parent.getDisplay().getBounds().width;
		double middle1 = (total * 0.375);
		int middle = Integer.valueOf((int) Math.round(middle1));
		Composite agrupa = new Composite(parent, SWT.NORMAL);
		agrupa.setData(RWT.CUSTOM_VARIANT, "gray_background");
		agrupa.setLayout(form);
		FormDatas.attach(agrupa).atLeft(0).atRight(0);

		headerLabel = new Label(agrupa, SWT.NORMAL);
		FormDatas.attach(headerLabel).atTopTo(agrupa, 15).atLeftTo(agrupa, 15);
		headerLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");

		comboDatabase = new Combo(agrupa, SWT.NONE);
		FormDatas.attach(comboDatabase).atTopTo(headerLabel, 10).atRight(middle).withHeight(23).withWidth(270);
		controls.put("comboDatabase", comboDatabase);
		try {
			initializeCombo();
		} catch (JisisDatabaseException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		comboDatabase.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (comboDatabase.getSelectionIndex() >= 0)
					try {
						ini();

					} catch (JisisDatabaseException e) {
						// TODO Bloque catch generado automáticamente
						e.printStackTrace();
					}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Apéndice de método generado automáticamente

			}
		});
		labelDatabaseName = new Label(agrupa, SWT.NONE);
		FormDatas.attach(labelDatabaseName).atTopTo(headerLabel, 15).atRightTo(comboDatabase, 10);

		comboField = new Combo(agrupa, SWT.NORMAL);
		FormDatas.attach(comboField).atTopTo(labelDatabaseName, 15).withHeight(23).withWidth(270).atRight(middle);
		controls.put("comboField", comboField);

		field = new Label(agrupa, SWT.NONE);
		FormDatas.attach(field).atTopTo(labelDatabaseName, 15).atRightTo(comboField, 10);

		final Text textLeaded = new Text(agrupa, SWT.BORDER);
		FormDatas.attach(textLeaded).atTopTo(comboField, 10).withWidth(250).withHeight(10).atRight(middle);
		controls.put("textLeaded", textLeaded);

		leaded = new Label(agrupa, SWT.NONE);
		FormDatas.attach(leaded).atTopTo(field, 15).atRightTo(textLeaded, 10);

		final Text textPrefix = new Text(agrupa, SWT.NONE);
		FormDatas.attach(textPrefix).atTopTo(headerLabel, 10).withHeight(10).withWidth(250).atRight(15);
		controls.put("textPrefix", textPrefix);

		prefix = new Label(agrupa, SWT.NONE);
		FormDatas.attach(prefix).atTopTo(headerLabel, 15).atRightTo(textPrefix, 10);

		final Text textformatOfExtraction = new Text(agrupa, SWT.NONE);
		FormDatas.attach(textformatOfExtraction).atTopTo(textPrefix, 10).atRight(15).withWidth(250).withHeight(50);
		controls.put("textformatOfExtraction", textformatOfExtraction);

		formatOfExtraction = new Label(agrupa, SWT.NONE);
		FormDatas.attach(formatOfExtraction).atTopTo(prefix, 15).atRightTo(textformatOfExtraction, 10);

		DecoratedControl[] decoratedRequiredNameDatabase = new DecoratedControl[] { new DecoratedControl(comboDatabase, "comboDatabase", AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED) };
		customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequiredNameDatabase, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT);
		DecoratedControl[] decoratedRequiredPrefix = new DecoratedControl[] { new DecoratedControl(textPrefix, "txtPrefixTable", AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED) };
		customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequiredPrefix, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT);
		DecoratedControl[] decoratedRequiredHeaded = new DecoratedControl[] { new DecoratedControl(textLeaded, "txtHeadedTable", AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED) };
		customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequiredHeaded, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT);
		DecoratedControl[] decoratedRequiredFormatOfExtration = new DecoratedControl[] { new DecoratedControl(textformatOfExtraction, "textFormatOfExtration", AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED) };
		customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequiredFormatOfExtration, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT);
		DecoratedControl[] decoratedRequiredField = new DecoratedControl[] { new DecoratedControl(comboField, "comboField", AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED) };
		customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequiredField, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT);

		l10n();
		return parent;
	}

	@Override
	public Composite createButtons(final Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {

		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		aceptar = new Button(parent, SWT.PUSH);
		aceptar.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));

		cancelar = new Button(parent, SWT.PUSH);
		cancelar.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));

		aceptar.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (customControlDecorationFactory.AllControlDecorationsHide()) {
					Variable variable = new Variable();
					variable.setOutputFormat((((Text) controls.get("textLeaded")).getText()));
					variable.setHeader(((Text) controls.get("textPrefix")).getText());
					variable.setField(comboField.getText());
					variable.setDatabaseName(comboDatabase.getText());
					// variable.setId((long)5);
					//variable.setId(((AllManagementController) controller).generateVariableID());

					((AllManagementController) controller).getAdminVariable().addVariable(variable);

					MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), "Information", AbosMessages.get().MESSAGE_ADD, null);
				} else
					MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), "Error", AbosMessages.get().MESSAGE_MISSING_FIELDS, null);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		return parent;
	}

	public Control getControl(String key) {

		return this.controls.get(key);
	}

	public void dispose() {
		for (Control control : controls.values()) {
			control.dispose();
		}
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void l10n() {
		headerLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CREATE_NEW_VARIABLE));
		field.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FIELD));
		leaded.setText(MessageUtil.unescape(AbosMessages.get().LABEL_HEADER));
		prefix.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PREFIX));
		formatOfExtraction.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FORMAT_OF_EXTENTION));
		labelDatabaseName.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_DATABASE));

	}

	private void initializeCombo() throws JisisDatabaseException {
		List<String> listDatabase = ((AllManagementController) controller).getManageDatabase().getDataBaseNames(libraryJisis);
		String[] comboStrings = new String[listDatabase.size()];
		List<String> type = new LinkedList<String>();
		for (int i = 0; i < listDatabase.size(); i++) {
			String report = listDatabase.get(i);
			type.add(report);
			comboStrings[i] = listDatabase.get(i);
		}
		comboDatabase.setData(type);
		comboDatabase.setItems(comboStrings);
	}

	private void ini() throws JisisDatabaseException {
		int selectedIndex = ((Combo) controls.get("comboDatabase")).getSelectionIndex();
		String nameDatabase = comboDatabase.getItem(selectedIndex);
		FieldDefinitionTable listDatabase = null;
		try {
			listDatabase = ((AllManagementController) controller).getManageDatabase().getFieldDefinitionTable(nameDatabase, libraryJisis);

		} catch (Exception e) {
			e.printStackTrace();

		}

		String[] comboStrings = new String[listDatabase.getFieldsCount()];

		for (int i = 0; i < listDatabase.getFieldsCount(); i++) {

			comboStrings[i] = listDatabase.getFieldByIndex(i).getName();
		}
		comboField.setData(listDatabase.getFieldsCount());
		comboField.setItems(comboStrings);
	}

}
