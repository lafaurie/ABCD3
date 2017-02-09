package cu.uci.abcd.statistic.ui.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.Statistic;
import cu.uci.abcd.domain.management.library.Library;
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

public class TableAddEditableArea extends BaseEditableArea {

	private Map<String, Control> controls;
	private ViewController controller;
	Label selecBD;
	Label headerLabel;
	Label valueCol;
	Label headCol;
	Label valueRow;
	Label headRow;
	Label separator;
	DateTime timeFrom;
	DateTime timeto;
	Label salveHow;
	Label name;
	Label to;
	Label codeOut;
	Text text_to;
	Text text_from;

	Label from;
	Label description;
	Label testData;
	Label rangeMFN;
	Label tableData;
	Text texHeadColum;
	Text texHeadRow;
	Text texDescription;
	Library library;
	String libraryJisis;

	Combo combDatabaseName;
	Combo comboValueCol;
	Combo comboValueRow;

	Button bdIsis;
	Button bdPos;
	Button aceptar;
	Button test;
	Button cancelar;
	Button generateOut;
	Button saveBtn;
	int direction = 1024;
	String ordeString = "tableName";
	int page = 0;

	private CustomControlDecoration customControlDecorationFactory;

	public TableAddEditableArea(ViewController controller) {
		controls = new HashMap<String, Control>();
		this.controller = controller;
	}

	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
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

		Label separator = new Label(agrupa, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(separator).atTopTo(headerLabel, 10).atLeft(15).atRight(15);

		tableData = new Label(agrupa, SWT.NORMAL);
		tableData.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(tableData).atTopTo(separator, 15).atLeftTo(agrupa, 15);

		combDatabaseName = new Combo(agrupa, SWT.NONE);
		combDatabaseName.setLayoutData(new FormData());
		FormDatas.attach(combDatabaseName).atTopTo(tableData, 15).withWidth(270).withHeight(23).atRight(middle);
		controls.put("combDatabaseName", combDatabaseName);
		try {
			initializeCombo();
		} catch (JisisDatabaseException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		combDatabaseName.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (combDatabaseName.getSelectionIndex() >= 0)

					try {
						initializeComboValueColum();
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

		DecoratedControl[] decoratedRequiredDatabase = new DecoratedControl[] { new DecoratedControl(combDatabaseName, "combDatabaseName", AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED) };
		customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequiredDatabase, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT);

		selecBD = new Label(agrupa, SWT.None);
		FormDatas.attach(selecBD).atTopTo(tableData, 20).atRightTo(combDatabaseName, 10);

		texHeadColum = new Text(agrupa, SWT.NONE);
		FormDatas.attach(texHeadColum).atTopTo(combDatabaseName, 15).atRight(middle).withWidth(250).withHeight(10);
		controls.put("texHeadColum", texHeadColum);

		headCol = new Label(agrupa, SWT.None);
		FormDatas.attach(headCol).atTopTo(combDatabaseName, 20).atRightTo(texHeadColum, 10);

		comboValueCol = new Combo(agrupa, SWT.NONE);
		comboValueCol.setLayoutData(new FormData());
		FormDatas.attach(comboValueCol).atTopTo(texHeadColum, 15).withWidth(270).withHeight(23).atRight(middle);
		controls.put("comboValueCol", comboValueCol);

		valueCol = new Label(agrupa, SWT.None);
		valueCol.setLayoutData(new FormData());
		FormDatas.attach(valueCol).atTopTo(texHeadColum, 20).atRightTo(comboValueCol, 10);

		texHeadRow = new Text(agrupa, SWT.NONE);
		FormDatas.attach(texHeadRow).atTopTo(combDatabaseName, 15).atRight(15).withWidth(250).withHeight(10);
		controls.put("texHeadRow", texHeadRow);

		headRow = new Label(agrupa, SWT.None);
		headRow.setLayoutData(new FormData());
		FormDatas.attach(headRow).atTopTo(combDatabaseName, 20).atRightTo(texHeadRow, 10);

		comboValueRow = new Combo(agrupa, SWT.NONE);
		comboValueRow.setLayoutData(new FormData());
		comboValueRow.setItems(new String[] {});
		FormDatas.attach(comboValueRow).atTopTo(texHeadRow, 15).withWidth(270).withHeight(23).atRight(15);
		controls.put("comboValueRow", comboValueRow);

		valueRow = new Label(agrupa, SWT.None);
		valueRow.setLayoutData(new FormData());
		FormDatas.attach(valueRow).atTopTo(texHeadRow, 20).atRightTo(comboValueRow, 10);

		separator = new Label(agrupa, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(separator).atTopTo(valueCol, 20).atTopTo(comboValueRow, 20).atLeft(15).atRight(15);

		salveHow = new Label(agrupa, SWT.None);
		FormDatas.attach(salveHow).atTopTo(separator, 20).atLeftTo(agrupa, 15);

		final Text texName = new Text(agrupa, SWT.NONE);
		FormDatas.attach(texName).atTopTo(salveHow, 15).atRight(middle).withWidth(250).withHeight(10);
		controls.put("texName", texName);

		name = new Label(agrupa, SWT.None);
		FormDatas.attach(name).atTopTo(salveHow, 20).atRightTo(texName, 10);

		description = new Label(agrupa, SWT.None);
		FormDatas.attach(description).atTopTo(texName, 10).atLeftTo(agrupa, 15);

		final Text texDescription = new Text(agrupa, SWT.V_SCROLL | SWT.WRAP);
		FormDatas.attach(texDescription).atTopTo(description, 5).atLeftTo(agrupa, 15).withHeight(50).atLeft(15).atRight(15);
		controls.put("texDescription", texDescription);

		DecoratedControl[] decoratedRequiredDatabaseName = new DecoratedControl[] { new DecoratedControl(combDatabaseName, "combDatabaseName", AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED) };
		customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequiredDatabaseName, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT);

		DecoratedControl[] decoratedRequiredDescription = new DecoratedControl[] { new DecoratedControl(texDescription, "txtDescriptionTable", AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED) };
		customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequiredDescription, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT);
		DecoratedControl[] decoratedRequiredNameTable = new DecoratedControl[] { new DecoratedControl(texName, "txtNameTable", AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED) };
		customControlDecorationFactory.createDecorator(customControlDecorationFactory, decoratedRequiredNameTable, DecoratorType.REQUIRED_FIELD, 0, SWT.RIGHT);
		return parent;
	}

	@Override
	public Composite createButtons(final Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {

		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.setText(AbosMessages.get().BUTTON_ACEPT);

		cancelar = new Button(parent, SWT.PUSH);
		cancelar.setText(AbosMessages.get().BUTTON_CANCEL);

		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (customControlDecorationFactory.AllControlDecorationsHide()) {
					Statistic statistic = new Statistic();
					statistic.setDatabaseType(combDatabaseName.getText());
					statistic.setTableName((((Text) controls.get("texName")).getText()));
					statistic.setDescription((((Text) controls.get("texDescription")).getText()));
					statistic.setTableID(((AllManagementController) controller).generateStatisticID());
					statistic.setHeadRow(((Text) controls.get("texHeadRow")).getText());
					statistic.setHeadColumn(((Text) controls.get("texHeadColum")).getText());
					statistic.setValueColumn(comboValueRow.getText());
					statistic.setValueRow(comboValueRow.getText());

					((AllManagementController) controller).getAdminTable().addStatistic(statistic);
					MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), "Information", AbosMessages.get().MESSAGE_ADD, null);
				} else
					MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), "Error", AbosMessages.get().MESSAGE_MISSING_FIELDS, null);

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
		// TODO Apéndice de método generado automáticamente
		return false;
	}

	@Override
	public void l10n() {
		headerLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CREATE_NEW_TABLE));
		tableData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_TABLE));
		selecBD.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_DATABASE));
		headCol.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COLUMN_HEADING));

		valueCol.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COLUMN_VALUE));
		headRow.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ROW_HEADING));
		valueRow.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ROW_VALUE));
		//salveHow.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SAVE));
		name.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		description.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION));
		// saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		// aceptar.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));

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
		combDatabaseName.setData(type);
		combDatabaseName.setItems(comboStrings);

	}

	private void initializeComboValueColum() throws JisisDatabaseException {

		int selectedIndex = ((Combo) controls.get("combDatabaseName")).getSelectionIndex();

		String nameDatabase = combDatabaseName.getItem(selectedIndex);

		
				comboValueRow.add(nameDatabase);
				comboValueCol.add(nameDatabase);
	}
			

}