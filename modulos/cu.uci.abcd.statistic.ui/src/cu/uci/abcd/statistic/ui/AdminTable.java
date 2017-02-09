package cu.uci.abcd.statistic.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;
import org.unesco.jisis.corelib.common.FieldDefinitionTable;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.Statistic;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.statistic.Variable;
import cu.uci.abcd.statistic.ui.controller.AdminTableController;
import cu.uci.abcd.statistic.ui.controller.AllManagementController;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abcd.statistic.ui.model.TableAddEditableArea;
import cu.uci.abcd.statistic.ui.model.TableUpdateEditableArea;
import cu.uci.abcd.statistic.ui.model.TableWatchEditableArea;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

/**
 * @author Dayana Rivera Muñoz
 **/
public class AdminTable extends ContributorPage {
	private Variable variable;
	private Composite newReport;

	private Label searchCriteria;
	private Label tableData;
	private Label labelType;
	private Label to;
	private Label from;
	private Label rangeMFN;
	private Label selecBD;
	private Label tableNames;
	private Label headRow;
	private Label headCol;
	private Label separator;
	private Label separator1;
	private Label valueRow;
	private Label list;
	private Label valueCol;
	private Label codeOut;

	private Combo comboType;
	private Combo comboDataBase;
	private Combo comboValueRow;
	private Combo comboValueColum;
	private Combo comboNameDatabase;
	private Combo comboTestColum;
	private Combo comboTestRow;

	private Button btnConsult;
	private Button btnNewSearch;
	private Button btnewRerpot;
	private Button btCancel;
	private Button btGeneratOut;
	private Button aceptar;
	private Button exportPDF;
	private Button exportEXCEL;

	private Text textName;
	private Text texHeadRow;
	private Text text_to;
	private Text text_from;
	private Text codeText;
	private Text texHead;

	private int selectedIndex;
	private SecurityCRUDTreeTable tabla;
	private Library library;
	private String libraryJisis;
	private List<String> searchCriteriaList = new ArrayList<>();
	private String tableNameConsult = null;
	private String converter = null;
	private String description = null;
	private String tableName = null;
	private String databasetype = null;
	private String state = null;
	private String orderByString = "tableID";
	private int direction = 1024;
	private CustomControlDecoration customControlDecorationFactory;
	private ValidatorUtils validator;
	private Composite test;

	@Override
	public Control createUIControl(Composite shell) {
		customControlDecorationFactory = new CustomControlDecoration();
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		libraryJisis = library.getIsisDefHome();

		validator = new ValidatorUtils(new CustomControlDecoration());
		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		test = new Composite(shell, SWT.NORMAL);
		addComposite(test);
		test.setData(RWT.CUSTOM_VARIANT, "gray_background");
		searchCriteria = new Label(test, SWT.NONE);
		addHeader(searchCriteria);

		labelType = new Label(test, SWT.NONE);
		add(labelType);

		comboNameDatabase = new Combo(test, SWT.NORMAL | SWT.READ_ONLY);
		initialize(comboNameDatabase, ((AllManagementController) controller).getManageDatabase().getDataBaseNames(libraryJisis));
		add(comboNameDatabase);

		tableNames = new Label(test, SWT.NONE);
		add(tableNames);

		textName = new Text(test, SWT.NONE);
		add(textName);
		br();

		btnNewSearch = new Button(test, SWT.NONE);
		add(btnNewSearch);

		btnConsult = new Button(test, SWT.NONE);
		add(btnConsult);

		btnewRerpot = new Button(test, SWT.PUSH);
		add(btnewRerpot);
		br();

		separator = new Label(test, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		br();

		newReport = new Composite(shell, SWT.NONE);
		newReport.setData(RWT.CUSTOM_VARIANT, "gray_background");
		newReport.setVisible(false);
		addComposite(newReport);

		list = new Label(test, SWT.NORMAL);
		addHeader(list);

		tabla = new SecurityCRUDTreeTable(test, SWT.NONE);
		tabla.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 8817895862824622805L;

			@Override
			public void handleEvent(Event event) {
				refresh();
			}
		});
		tabla.setEntityClass(Statistic.class);
		tabla.setVisualEntityManager(new MessageVisualEntityManager(tabla));
		add(tabla, Percent.W100);
		TreeTableColumn columns[] = { new TreeTableColumn(40, 0, "gettableName"), new TreeTableColumn(30, 1, "getDatabaseType"), new TreeTableColumn(30, 2, "getDescription") };
		tabla.createTable(columns);
		tabla.setSaveAll(false);
		tabla.setAdd(true, new TableAddEditableArea(controller));
		tabla.setUpdate(true, new TableUpdateEditableArea(controller));
		tabla.setWatch(true, new TableWatchEditableArea(controller));
		tabla.setDelete("deleteTableID");
		tabla.setPageSize(10);

		tabla.setSearchHintText(AbosMessages.get().BUTTON_SEARCH);
		tabla.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		tabla.setSaveAllButtonText(AbosMessages.get().BUTTON_SAVE);
		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);

		CRUDTreeTableUtils.configUpdate(tabla);

		CRUDTreeTableUtils.configReports(tabla, contributorName(), searchCriteriaList);
		CRUDTreeTableUtils.configRemove(tabla, new IActionCommand() {

			@Override
			public void execute(TreeColumnEvent event) {
				Statistic statisticTable = (Statistic) event.entity.getRow();
				((AllManagementController) controller).getAdminTable().deleteStatistic(statisticTable.getTableID());
			}
		});

		/*---------Boton nuevo ---------*/

		separator1 = new Label(newReport, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator1);

		tableData = new Label(newReport, SWT.NORMAL);
		addHeader(tableData);

		selecBD = new Label(newReport, SWT.NORMAL);
		add(selecBD);

		comboDataBase = new Combo(newReport, SWT.NONE | SWT.READ_ONLY);
		initialize(comboDataBase, ((AllManagementController) controller).getManageDatabase().getDataBaseNames(libraryJisis));
		add(comboDataBase);
		validator.applyValidator(comboDataBase, "comboDataBase", DecoratorType.REQUIRED_FIELD, true);
		br();

		headCol = new Label(newReport, SWT.None);
		add(headCol);
		headCol.setVisible(false);

		texHead = new Text(newReport, SWT.NONE);
		add(texHead);
		texHead.setVisible(false);
		validator.applyValidator(texHead, "texHead", DecoratorType.REQUIRED_FIELD, true);

		valueCol = new Label(newReport, SWT.None);
		add(valueCol);
		valueCol.setVisible(false);

		comboValueColum = new Combo(newReport, SWT.NONE | SWT.READ_ONLY);
		add(comboValueColum);
		comboValueColum.setVisible(false);
		validator.applyValidator(comboValueColum, "comboValueColum", DecoratorType.REQUIRED_FIELD, true);

		headRow = new Label(newReport, SWT.None);
		add(headRow);
		headRow.setVisible(false);

		texHeadRow = new Text(newReport, SWT.NONE);
		add(texHeadRow);
		texHeadRow.setVisible(false);
		validator.applyValidator(texHeadRow, "texHeadRow", DecoratorType.REQUIRED_FIELD, true);

		valueRow = new Label(newReport, SWT.None);
		add(valueRow);
		valueRow.setVisible(false);

		comboValueRow = new Combo(newReport, SWT.NONE | SWT.READ_ONLY);
		add(comboValueRow);
		comboValueRow.setVisible(false);
		validator.applyValidator(comboValueRow, "comboValueRow", DecoratorType.REQUIRED_FIELD, true);

		rangeMFN = new Label(newReport, SWT.NONE);
		addHeader(rangeMFN);

		from = new Label(newReport, SWT.NONE);
		add(from);

		text_from = new Text(newReport, SWT.NONE);
		add(text_from);

		to = new Label(newReport, SWT.NONE);
		add(to);

		text_to = new Text(newReport, SWT.NONE);
		add(text_to);
		br();
		codeOut = new Label(newReport, SWT.NONE);
		add(codeOut);

		codeText = new Text(newReport, SWT.V_SCROLL | SWT.WRAP);
		add(codeText);
		br();
		btCancel = new Button(newReport, SWT.PUSH);
		add(btCancel);

		exportPDF = new Button(newReport, SWT.PUSH);
		add(exportPDF);

		exportEXCEL = new Button(newReport, SWT.PUSH);
		add(exportEXCEL);

		exportPDF.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

			
					if (customControlDecorationFactory.AllControlDecorationsHide()) {
						//inicializeGenerateOutPDF();
						
						//FIXME OIGRESS HACER
					 // TabularStatistic tb =((AllManagementController)	controller).getAdminTable().getValuesFromLucene(arg0, arg1, arg2)
                     //MOSTRAR LA TABLA 
						//or
				     // TabularStatistic tb =((AllManagementController)	controller).getAdminTable().getValuesFromLuceneAndMFN(arg0, arg1, arg2, arg3, arg4)
					 //MOSTRAR LA TABLA  
						//or	
				     // TabularStatistic tb = ((AllManagementController)	controller).getAdminTable().getValuesFromMFN(arg0, arg1, arg2, arg3)
					 //MOSTRAR LA TABLA 
					} else {
						MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION),
								AbosMessages.get().MESSAGE_MISSING_FIELDS, null);
					}
				

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		exportEXCEL.addSelectionListener(new SelectionListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {
					if (customControlDecorationFactory.AllControlDecorationsHide()) {
						inicializeGenerateOutEXCEL();

					} else {
						MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION),
								AbosMessages.get().MESSAGE_MISSING_FIELDS, null);
					}
				} catch (JisisDatabaseException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		btCancel.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				newReport.setVisible(false);
				btnewRerpot.setVisible(true);
				btnConsult.setVisible(true);
				btnNewSearch.setVisible(true);
				comboNameDatabase.setEnabled(true);
			}
		});

		btnewRerpot.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				newReport.setVisible(true);
				btnewRerpot.setVisible(false);
				btnConsult.setVisible(false);
				btnNewSearch.setVisible(false);
				comboNameDatabase.setEnabled(false);
				textName.setEditable(false);
				insertComposite(newReport, test);
				tabla.setVisible(false);

				refresh();

			}
		});

		comboDataBase.addSelectionListener(new SelectionListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				texHead.setVisible(true);
				headCol.setVisible(true);
				comboValueColum.setVisible(true);
				texHeadRow.setVisible(true);
				valueRow.setVisible(true);
				valueCol.setVisible(true);
				comboValueRow.setVisible(true);
				headRow.setVisible(true);
				try {
					if (comboDataBase.getSelectionIndex() >= 0) {
						initializeCombos();
					}

				} catch (JisisDatabaseException e1) {
					e1.printStackTrace();
				}
				try {
					initializeText();
				} catch (JisisDatabaseException e1) {
					e1.printStackTrace();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		/*----------------TABLA----------------------------*/

		CRUDTreeTableUtils.configUpdate(tabla);

		CRUDTreeTableUtils.configReports(tabla, contributorName(), searchCriteriaList);
		CRUDTreeTableUtils.configRemove(tabla, new IActionCommand() {
			@Override
			public void execute(TreeColumnEvent event) {
				Statistic statisticTable = (Statistic) event.entity.getRow();
				((AdminTableController) controller).DeleteTable(statisticTable.getTableID());
			}
		});

		tabla.addPageChangeListener(new PageChangeListener() {

			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "tableName";
						break;
					case 2:
						orderByString = "description";
						break;
					case 3:
						orderByString = "databasetype";
						break;
					}
				}

				searchTables(event.currentPage - 1, event.pageSize);
			}
		});

		btnNewSearch.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				textName.setText("");
				comboNameDatabase.deselectAll();
				databasetype = null;
				tableName = null;
				tabla.refresh();
				btnewRerpot.setVisible(true);

			}
		});

		btnConsult.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent e) {
				tabla.clearRows();
				list.setVisible(true);
				tabla.setVisible(true);

				int selectedIndex = comboNameDatabase.getSelectionIndex();

				if (selectedIndex == -1) {

				} else {
					databasetype = ((LinkedList<String>) ((Combo) comboNameDatabase).getData()).get(selectedIndex);
				}
				tableName = textName.getText().trim();

				if (tableName.length() != 0) {
					searchCriteriaList.clear();
					searchCriteriaList.add(labelType.getText() + ":" + textName.getText());

				}
				if (selectedIndex == -1 && (tableName.length() == 0)) {
					MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION),
							MessageUtil.unescape(AbosMessages.get().MESSAGE_COINCIDENT), null);
				}
				orderByString = "tableID";
				direction = 1024;
				searchTables(0, tabla.getPaginator().getPageSize());
			}
		});

		l10n();
		return shell;
	}

	public void searchTables(int page, int size) {
		tabla.clearRows();

		Page<Statistic> lis = ((AllManagementController) controller).FindAllStatisticSearch(databasetype, tableName, page, size, direction, orderByString);
		tabla.setTotalElements((int) lis.getTotalElements());
		tabla.setRows(lis.getContent());
		tabla.refresh();
	}

	@Override
	public void l10n() {
		searchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		labelType.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SELEC_DATABASE));
		btnConsult.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		list.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_COINCIDENCES));
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_DATABASE), MessageUtil.unescape(AbosMessages.get().LABEL_NAME),
				MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION)));
		tabla.setAddButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_ADD));
		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);
		tableNames.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		btnewRerpot.setText(AbosMessages.get().BUTTON_NEW);
		btCancel.setText(AbosMessages.get().BUTTON_CANCEL);
		exportEXCEL.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		exportPDF.setText(AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tabla.l10n();
		tableData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_TABLE));
		valueCol.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COLUMN_VALUE));
		rangeMFN.setText(MessageUtil.unescape(AbosMessages.get().LABEL_RANGE_MFN));
		valueRow.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ROW_VALUE));
		selecBD.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SELEC_DATABASE));
		headCol.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COLUMN_HEADING));
		headRow.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ROW_HEADING));
		to.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TO));
		from.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
		codeOut.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CODE_OUT));

	}

	private void initializeCombos() throws JisisDatabaseException {

		int selectedIndex = comboDataBase.getSelectionIndex();

		String nameDatabase = comboDataBase.getItem(selectedIndex);
		initialize(comboValueColum, ((AllManagementController) controller).getAdminVariable().findVariableByDatabase(nameDatabase, libraryJisis));
		initialize(comboValueRow, ((AllManagementController) controller).getAdminVariable().findVariableByDatabase(nameDatabase, libraryJisis));
		/*
		 * List<Variable> b = (List<Variable>) ((AllManagementController)
		 * controller).getAdminVariable().findVariableByDatabase(nameDatabase,
		 * libraryJisis);
		 * 
		 * 
		 * int cont = 1; for (int i = 0; i < b.size(); i++) { if
		 * (b.get(i).getDatabaseName().equals(nameDatabase)) { cont++; } }
		 * String[] nombreVariable = new String[cont]; String varTemporal = "";
		 * int indice = 0; boolean bandera = false;
		 * 
		 * for (int i = 0; i < b.size(); i++) {
		 * if(b.get(i).getDatabaseName().equals(nameDatabase)) { varTemporal =
		 * b.get(i).getField(); for (int j = 0; j < nombreVariable.length; j++)
		 * if (varTemporal.equals(nombreVariable[j])) bandera = true; if
		 * (!bandera) {
		 * 
		 * nombreVariable[indice] = varTemporal;
		 * 
		 * indice++;
		 * 
		 * }
		 * 
		 * } bandera = false; } for (int i = 0; i < nombreVariable.length; i++)
		 * if (nombreVariable[i] != null) {
		 * comboValueRow.add(nombreVariable[i]);
		 * comboValueColum.add(nombreVariable[i]); }
		 */

	}

	public void initializeText() throws JisisDatabaseException {

		int inicieCombo = comboDataBase.getSelectionIndex();
		String nameDatabase = comboDataBase.getItem(inicieCombo);
		Record lasRecor = ((AllManagementController) controller).getManageDatabase().getLastRecord(nameDatabase, libraryJisis);
		int a = (int) (lasRecor.getMfn());
		text_to.setText(a + "");
		text_from.setText("1");

	}

	private void inicializeGenerateOutPDF() throws JisisDatabaseException {

		int inicieCombo1 = comboDataBase.getSelectionIndex();
		String nameDatabase = comboDataBase.getItem(inicieCombo1);
		int selectedIndexColum = comboValueColum.getSelectionIndex();
		int selectedIndexRow = comboValueRow.getSelectionIndex();

		String nameValueColum = comboValueColum.getItem(selectedIndexColum);
		String nameValueRow = comboValueRow.getItem(selectedIndexRow);
		int desde = Integer.parseInt(text_from.getText());
		int hasta = Integer.parseInt(text_to.getText());
		int tagNameValueColumn = -1;
		int tagNameValueRow = -1;
		FieldDefinitionTable listWorSheet = null;
		try {
			listWorSheet = ((AllManagementController) controller).getManageDatabase().getFieldDefinitionTable(nameDatabase, libraryJisis);

		} catch (Exception e) {
			e.printStackTrace();

		}
		for (int i = 0; i < listWorSheet.getFieldsCount(); i++) {
			if (tagNameValueColumn != -1 && tagNameValueRow != -1)
				break;
			if (listWorSheet.getFieldByIndex(i).getName().equals(nameValueColum))

				tagNameValueColumn = listWorSheet.getFieldByIndex(i).getTag();
			if (listWorSheet.getFieldByIndex(i).getName().equals(nameValueRow))
				tagNameValueRow = listWorSheet.getFieldByIndex(i).getTag();
		}

		// Los del MFn

		if (desde > hasta) {
			MessageDialogUtil.openWarning(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION), AbosMessages.get().MESSAGE_DELETE,
					null);
		}

		long[] tempMfn = new long[hasta + 1 - desde];
		int pos = 0;
		for (int i = desde; i <= hasta; i++)
			tempMfn[pos++] = i;
		List<Record> listRecords = ((AllManagementController) controller).getManageDatabase().findByMfns(tempMfn, nameDatabase, libraryJisis);
		List<String> nameOfTagColum = new ArrayList<>();
		List<String> nameOfTagRow = new ArrayList<>();

		String valueColumTemp = "";
		String valueRowTemp = "";

		try {
			for (int j = 0; j < listRecords.size(); j++) {
				valueColumTemp = listRecords.get(j).getField(tagNameValueColumn).getStringFieldValue();
				valueRowTemp = listRecords.get(j).getField(tagNameValueRow).getStringFieldValue();
				if (!nameOfTagColum.contains(valueColumTemp))
					nameOfTagColum.add(valueColumTemp);
				if (!nameOfTagRow.contains(valueRowTemp))
					nameOfTagRow.add(valueRowTemp);
			}
			int[][] matrix = new int[nameOfTagColum.size()][nameOfTagRow.size()];

			for (int j = 0; j < listRecords.size(); j++) {
				String valueTagC = listRecords.get(j).getField(tagNameValueColumn).getStringFieldValue();
				String valueTagR = listRecords.get(j).getField(tagNameValueRow).getStringFieldValue();
				int positionR = nameOfTagRow.indexOf(valueTagR);
				int positionC = nameOfTagColum.indexOf(valueTagC);
				matrix[positionR][positionC]++;
			}

			nameOfTagColum.add("Total");
			nameOfTagRow.add("Total");
			String[][] result = new String[nameOfTagRow.size()][nameOfTagColum.size()];

			for (int i = 0; i < nameOfTagRow.size() - 1; i++) {
				String valor = "";
				for (int j = 0; j < nameOfTagColum.size() - 1; j++) {
					valor += matrix[i][j];
					result[i][j] = valor;
					valor = "";
				}
			}
			int tDerecha = 0;
			List<Integer> listDerecha = new ArrayList<>();

			for (int i = 0; i < nameOfTagRow.size() - 1; i++) {
				for (int j = 0; j < nameOfTagColum.size() - 1; j++) {
					tDerecha += Integer.parseInt(result[i][j]);
				}
				listDerecha.add(tDerecha);
				tDerecha = 0;
			}
			int tIzquierda = 0;
			List<Integer> listIzquierda = new ArrayList<>();

			for (int i = 0; i < nameOfTagColum.size() - 1; i++) {
				for (int j = 0; j < nameOfTagRow.size() - 1; j++) {
					tIzquierda += Integer.parseInt(result[j][i]);
				}
				listIzquierda.add(tIzquierda);
				tIzquierda = 0;
			}
			for (int j = 0; j < nameOfTagColum.size() - 1; j++) {
				result[nameOfTagRow.size() - 1][j] = listIzquierda.get(j).toString();
				;
			}

			for (int j = 0; j < nameOfTagRow.size() - 1; j++) {
				result[j][nameOfTagColum.size() - 1] = listDerecha.get(j).toString();
			}

			int total = 0;
			for (int i = 0; i < listDerecha.size(); i++) {
				total += listDerecha.get(i);
			}
			for (int i = 0; i < listIzquierda.size(); i++) {
				total += listIzquierda.get(i);
			}
			result[nameOfTagRow.size() - 1][nameOfTagColum.size() - 1] = "" + total;

			/*
			 * ByteArrayOutputStream outputStream = ((AllManagementController)
			 * controller
			 * ).getPdfGenerator().generatePDFStatistic(texHead.getText(),
			 * nameOfTagColum, texHeadRow.getText(), nameOfTagRow, result);
			 * URLUtil.generateDownloadReport(ReportType.PDF, outputStream);
			 */

		} catch (Exception e) {

		}

	}

	private void inicializeGenerateOutEXCEL() throws JisisDatabaseException {

		int inicieCombo2 = comboDataBase.getSelectionIndex();
		String nameDatabase = comboDataBase.getItem(inicieCombo2);
		int selectedIndexColum = comboValueColum.getSelectionIndex();
		int selectedIndexRow = comboValueRow.getSelectionIndex();

		String nameValueColum = comboValueColum.getItem(selectedIndexColum);
		String nameValueRow = comboValueRow.getItem(selectedIndexRow);
		int desde = Integer.parseInt(text_from.getText());
		int hasta = Integer.parseInt(text_to.getText());
		int tagNameValueColumn = -1;
		int tagNameValueRow = -1;
		FieldDefinitionTable listWorSheet = null;
		listWorSheet = ((AllManagementController) controller).getManageDatabase().getFieldDefinitionTable(nameDatabase, libraryJisis);
		for (int i = 0; i < listWorSheet.getFieldsCount(); i++) {
			if (tagNameValueColumn != -1 && tagNameValueRow != -1)
				break;
			if (listWorSheet.getFieldByIndex(i).getName().equals(nameValueColum))
				tagNameValueColumn = listWorSheet.getFieldByIndex(i).getTag();
			if (listWorSheet.getFieldByIndex(i).getName().equals(nameValueRow))
				tagNameValueRow = listWorSheet.getFieldByIndex(i).getTag();
		}

		// Los del MFn

		if (desde > hasta) {
			MessageDialogUtil.openWarning(Display.getCurrent().getActiveShell(), "Information", AbosMessages.get().MESSAGE_DELETE, null);
		}

		long[] tempMfn = new long[hasta + 1 - desde];
		int pos = 0;
		for (int i = desde; i <= hasta; i++)
			tempMfn[pos++] = i;

		List<Record> listRecords = ((AllManagementController) controller).getManageDatabase().findByMfns(tempMfn, nameDatabase, libraryJisis);
		List<String> nameOfTagColum = new ArrayList<>();
		List<String> nameOfTagRow = new ArrayList<>();

		String valueColumTemp = "";
		String valueRowTemp = "";

		try {
			for (int j = 0; j < listRecords.size(); j++) {
				valueColumTemp = listRecords.get(j).getField(tagNameValueColumn).getStringFieldValue();
				valueRowTemp = listRecords.get(j).getField(tagNameValueRow).getStringFieldValue();
				if (!nameOfTagColum.contains(valueColumTemp))
					nameOfTagColum.add(valueColumTemp);
				if (!nameOfTagRow.contains(valueRowTemp))
					nameOfTagRow.add(valueRowTemp);
			}
			if (nameOfTagColum.size() == 0 | nameOfTagRow.size() == 0) {
				MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), "Information", AbosMessages.get().MESSAGE_MISSING_FIELDS, null);
			}
			int[][] matrix = new int[nameOfTagColum.size()][nameOfTagRow.size()];

			for (int j = 0; j < listRecords.size(); j++) {
				String valueTagC = listRecords.get(j).getField(tagNameValueColumn).getStringFieldValue();
				String valueTagR = listRecords.get(j).getField(tagNameValueRow).getStringFieldValue();
				int positionR = nameOfTagRow.indexOf(valueTagR);
				int positionC = nameOfTagColum.indexOf(valueTagC);
				matrix[positionR][positionC]++;

			}

			nameOfTagColum.add("Total");
			nameOfTagRow.add("Total");
			String[][] result = new String[nameOfTagRow.size()][nameOfTagColum.size()];

			for (int i = 0; i < nameOfTagRow.size() - 1; i++) {
				String valor = "";
				for (int j = 0; j < nameOfTagColum.size() - 1; j++) {
					valor += matrix[i][j];
					result[i][j] = valor;
					valor = "";
				}
			} //
			int tDerecha = 0;
			List<Integer> listDerecha = new ArrayList<>();

			for (int i = 0; i < nameOfTagRow.size() - 1; i++) {
				for (int j = 0; j < nameOfTagColum.size() - 1; j++) {
					tDerecha += Integer.parseInt(result[i][j]);
				}
				listDerecha.add(tDerecha);
				tDerecha = 0;
			}
			int tIzquierda = 0;
			List<Integer> listIzquierda = new ArrayList<>();

			for (int i = 0; i < nameOfTagColum.size() - 1; i++) {
				for (int j = 0; j < nameOfTagRow.size() - 1; j++) {
					tIzquierda += Integer.parseInt(result[j][i]);
				}
				listIzquierda.add(tIzquierda);
				tIzquierda = 0;
			}
			for (int j = 0; j < nameOfTagColum.size() - 1; j++) {
				result[nameOfTagRow.size() - 1][j] = listIzquierda.get(j).toString();
				;
			}

			for (int j = 0; j < nameOfTagRow.size() - 1; j++) {
				result[j][nameOfTagColum.size() - 1] = listDerecha.get(j).toString();
			}

			int total = 0;
			for (int i = 0; i < listDerecha.size(); i++) {
				total += listDerecha.get(i);
			}
			for (int i = 0; i < listIzquierda.size(); i++) {
				total += listIzquierda.get(i);
			}
			result[nameOfTagRow.size() - 1][nameOfTagColum.size() - 1] = "" + total;

			/*
			 * ByteArrayOutputStream outputStream = ((AllManagementController)
			 * controller
			 * ).getSpreadsheetGenerator().generateSpreadsheetStatistic(texHead
			 * .getText(), nameOfTagColum, texHeadRow.getText(), nameOfTagRow,
			 * result); URLUtil.generateDownloadReport(ReportType.SPREADSHEET,
			 * outputStream);
			 */

		} catch (Exception e) {

		}

	}

	@Override
	public String getID() {
		return "manageTableID";
	}

	@Override
	public String contributorName() {
		return AbosMessages.get().MENU_ADMIN_TABLE_ROPORT_STATISTIC;
	}

}
