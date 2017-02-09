package cu.uci.abcd.statistic.ui;

import java.util.ArrayList;
import java.util.Arrays;
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

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.statistic.Variable;
import cu.uci.abcd.statistic.ui.controller.AllManagementController;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abcd.statistic.ui.model.VariableAddEditableArea;
import cu.uci.abcd.statistic.ui.model.VariableUpdateEditableArea;
import cu.uci.abcd.statistic.ui.model.VariableWatchEditableArea;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
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
 * 
 */
public class AdminVariable extends ContributorPage {

	private Label headerLabel;
	private Label searchCriteria;
	private Label field;
	private Label leaded;
	private Label formatOfExtraction;
	private Label selecDatabase;
	private Label list;
	private Label separator;
	private Combo comboField;
	private Combo comboNameDatabase;
	private Text textLeaded;
	private Library library;
	private String libraryJisis;
	private String textLeadedConsult = null;
	private String fieldConsult = null;
	private String databaseName = null;
	private SecurityCRUDTreeTable tabla;
	private Button btnNewSearch;
	private Button btnConsult;
	private List<String> searchCriteriaList = new ArrayList<>();
	private String orderBy = "id";
	private int direction = 1024;

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		libraryJisis = library.getIsisDefHome();

		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite test = new Composite(shell, SWT.NORMAL);
		addComposite(test);
		test.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Composite resize = new Composite(shell, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height-20);
		
		headerLabel = new Label(test, SWT.NORMAL);
		addHeader(headerLabel);
		
		addSeparator(new Label(test, SWT.SEPARATOR | SWT.HORIZONTAL));
		searchCriteria = new Label(test, SWT.NONE);
		addHeader(searchCriteria);

		selecDatabase = new Label(test, SWT.NONE);
		add(selecDatabase);
		
		comboNameDatabase = new Combo(test, SWT.NORMAL | SWT.READ_ONLY);
		initialize(comboNameDatabase, ((AllManagementController)
				 controller).getManageDatabase().getDataBaseNames(libraryJisis));
		add(comboNameDatabase);
		
		field = new Label(test, SWT.NONE);
		add(field);

		comboField = new Combo(test, SWT.NORMAL | SWT.READ_ONLY);
		add(comboField);
		
		
		leaded = new Label(test, SWT.NONE);
		add(leaded);

		textLeaded = new Text(test, SWT.NONE);
		add(textLeaded);
		br();

		btnNewSearch = new Button(test, SWT.PUSH);
		add(btnNewSearch);

		btnConsult = new Button(test, SWT.PUSH);
		add(btnConsult);
		br();
		
		separator = new Label(test, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		br();

		Composite test1 = new Composite(shell, SWT.NORMAL);
		addComposite(test1);
		test1.setData(RWT.CUSTOM_VARIANT, "gray_background");

		list = new Label(test1, SWT.NORMAL);
		addHeader(list);
		
		createTable(test1);

		configureButtons(test1);
		
		comboNameDatabase.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectedIndexDatabaseName = comboNameDatabase.getSelectionIndex();
				if (selectedIndexDatabaseName >= 0) {
					comboField.setVisible(true);
					field.setVisible(true);
					textLeaded.setVisible(true);
					leaded.setVisible(true);
					btnConsult.setEnabled(true);
					btnNewSearch.setEnabled(true);
					tabla.setVisible(true);
					try {
						initialize(comboField,  ((AllManagementController)
								 controller).getAdminVariable().findVariableByDatabase(comboNameDatabase.getItem(selectedIndexDatabaseName), libraryJisis));
					} catch (JisisDatabaseException e2) {
						e2.printStackTrace();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		l10n();
		return shell;
	}
	
	private void createTable(Composite test) {
		tabla = new SecurityCRUDTreeTable(test, SWT.NONE);
		add(tabla, Percent.W100);
		tabla.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 8817895862824622805L;
			@Override
			public void handleEvent(Event event) {
				refresh();
			}
		});

		tabla.setEntityClass(Variable.class);
		tabla.setVisualEntityManager(new MessageVisualEntityManager(tabla));
		tabla.setAdd(true, new VariableAddEditableArea(controller));
		tabla.setUpdate(true, new VariableUpdateEditableArea(controller));
		tabla.setWatch(true, new VariableWatchEditableArea(controller));
		tabla.setDelete("deleteVariableID");
		tabla.setSaveAll(false);
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
				Variable variable = (Variable) event.entity.getRow();
				((AllManagementController) controller).getAdminVariable().deleteVariable(variable.getId());
			}
		});
		tabla.addPageChangeListener(new PageChangeListener() {

			@Override
			public void pageChanged(final PageChangedEvent event) {
				searchVariable( event.currentPage - 1, event.pageSize);
			}
		});
		tabla.setColumnHeaders(Arrays.asList(AbosMessages.get().LABEL_HEADER, AbosMessages.get().LABEL_FIELD,  AbosMessages.get().LABEL_NAME_DATABASE));
		TreeTableColumn columns[] = { new TreeTableColumn(50, 0, "getHeader"), new TreeTableColumn(30, 1, "getField"), new TreeTableColumn(20, 2, "getDatabaseName") };
		tabla.createTable(columns);
	}
	
	private void configureButtons(final Composite test1) {
		btnNewSearch.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
			textLeaded.setText("");
			comboField.deselectAll();
			comboNameDatabase.deselectAll();
			textLeadedConsult = null;
			fieldConsult = null;
			tabla.refresh();
		}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

	});
		btnConsult.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
			tabla.clearRows();
			tabla.setVisible(true);

			int selec = comboNameDatabase.getSelectionIndex();
			if (selec == -1) {
                databaseName=null;
			} else {
				databaseName = comboNameDatabase.getItem(selec);
			}

			int selectedIndex = comboField.getSelectionIndex();
			if (selectedIndex == -1) {
				 fieldConsult=null;
			} else {
				fieldConsult = comboField.getItem(selectedIndex);
			}

			if (textLeaded.getText().trim().length() != 0) {
				searchCriteriaList.clear();
				searchCriteriaList.add(field.getText() + ":" + textLeaded.getText());
			} 
			 if(textLeaded.getText().trim().length()==0 && (selectedIndex==-1)){
			
				 showInformationMessage(MessageUtil.unescape(AbosMessages.get().MESSAGE_COINCIDENT)); 
			 }
			 
		   orderBy = "id";
			direction = 1024;
			searchVariable( 0, tabla.getPaginator().getPageSize());
		}
	});
	}

	public void searchVariable(int page, int size) {
		tabla.clearRows();
		Page<Variable> lista = ((AllManagementController) controller).FindAllVariableSearch(databaseName, textLeaded.getText(), fieldConsult, page, size, direction, orderBy);
		tabla.setTotalElements((int) lista.getTotalElements());
		tabla.setRows(lista.getContent());
		tabla.refresh();

	}

	@Override
	public String getID() {
		return "manageVariableID";
	}

	@Override
	public void l10n() {
		headerLabel.setText(AbosMessages.get().MENU_ADMIN_VARIABLE_STATIST);
		searchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		selecDatabase.setText(AbosMessages.get().LABEL_NAME_DATABASE);
		field.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FIELD));
		leaded.setText(MessageUtil.unescape(AbosMessages.get().LABEL_HEADER));
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		btnConsult.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		tabla.setColumnHeaders(Arrays.asList(AbosMessages.get().LABEL_HEADER, AbosMessages.get().LABEL_FIELD,  AbosMessages.get().LABEL_NAME_DATABASE));

	}

	@Override
	public String contributorName() {
		return AbosMessages.get().MENU_ADMIN_VARIABLE_STATIST;
	}

}
