package cu.uci.abcd.administration.nomenclators.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import cu.uci.abcd.administration.nomenclators.controller.ControllerNomenclator;
import cu.uci.abcd.administration.nomenclators.l10n.AbosMessages;
import cu.uci.abcd.administration.nomenclators.model.NomenclatorAddEditableArea;
import cu.uci.abcd.administration.nomenclators.model.NomenclatorUpdateEditableArea;
import cu.uci.abcd.administration.nomenclators.model.NomenclatorWatchEditableArea;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.core.domain.Row;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.IActionDenied;
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

/**
 * 
 * @author Dayana Rivera
 * 
 */

public class ManageNomenclator extends ContributorPage {

	private Label searchCriteria;
	private Label headLabel;
	private Label nomenclatorType;
	private Combo comboNomenclatorType;
	private Label nomenclatorValue;
	private Button btnConsult;
	private Button btnNewSearch;
	private Label list;
	private Label separator;

	private SecurityCRUDTreeTable tabla;
	private Library library;
	private List<String> searchCriteriaList = new ArrayList<>();

	private List<Nomenclator> finalList;
	private String nomenclatorNameConsult = null;

	private Text textnomenclator;
	private List<Nomenclator> nomenclatorTypes;

	public ManageNomenclator() {
		super();

	}

	@Override
	public String contributorName() {
		return AbosMessages.get().NAME_UI_MANAGE_NOMENCLATOR;
	}

	@Override
	public String getID() {
		return "manageNomenclatorID";
	}

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite test = new Composite(shell, SWT.NORMAL);
		addComposite(test);
		test.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Composite resize = new Composite(shell, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height-20);
		
		headLabel= new Label(test, SWT.NONE);
		addHeader(headLabel);
		
		addSeparator(new Label(test, SWT.SEPARATOR | SWT.HORIZONTAL));

		searchCriteria = new Label(test, SWT.NONE);
		addHeader(searchCriteria);

		nomenclatorType = new Label(test, SWT.NONE);
		add(nomenclatorType);

		comboNomenclatorType = new Combo(test, SWT.READ_ONLY);
		add(comboNomenclatorType);

		nomenclatorValue = new Label(test, SWT.NONE);
		add(nomenclatorValue);

		textnomenclator = new Text(test, SWT.NONE);
		add(textnomenclator);
		br();

		btnNewSearch = new Button(test, SWT.PUSH);
		add(btnNewSearch);

		btnConsult = new Button(test, SWT.PUSH);
		add(btnConsult);
		br();

		separator = new Label(test, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		Composite test1 = new Composite(shell, SWT.NORMAL);
		addComposite(test1);
		test1.setData(RWT.CUSTOM_VARIANT, "gray_background");

		list = new Label(test1, SWT.NORMAL);
		addHeader(list);

		createTable(test1);

		configureButtons(test1);

		l10n();

		return shell;
	}

	private void configureButtons(final Composite test1) {
		btnNewSearch.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				searchCriteriaList.clear();
				textnomenclator.setText(""); // FIXME MAL USO DE VALORES
				textnomenclator.setFocus();
				comboNomenclatorType.select(0);
				nomenclatorNameConsult = null;
				tabla.destroyEditableArea();
				tabla.refresh();
				tabla.getPaginator().goToFirstPage();
			}

		});
		// Funcionalidad Consultar Nomenclador
		btnConsult.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				tabla.destroyEditableArea();
				nomenclatorNameConsult = textnomenclator.getText();
				if (nomenclatorNameConsult.equalsIgnoreCase("") || nomenclatorNameConsult.equalsIgnoreCase(null) || nomenclatorNameConsult.equalsIgnoreCase("*")) {
					nomenclatorNameConsult = null;
				}
				searchCriteriaList.clear();
				UiUtils.get().addSearchCriteria(searchCriteriaList, nomenclatorType.getText(), comboNomenclatorType)
						.addSearchCriteria(searchCriteriaList, nomenclatorValue.getText(), textnomenclator.getText());

				tabla.getPaginator().goToFirstPage();
				if (tabla.getEntities().size() == 0) {
					RetroalimentationUtils.showInformationShellMessage(AbosMessages.get().MESSAGE_COINCIDENT);
				}

			}

		});
		
		Display.getCurrent().getActiveShell().setDefaultButton(btnConsult);
	}

	@Override
	public void l10n() {

		headLabel.setText(AbosMessages.get().LABEL_MANAGE_NOMENCLATOR );
		searchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		nomenclatorValue.setText(AbosMessages.get().LABEL_NOMENCLATOR_VALUE);
		nomenclatorType.setText(AbosMessages.get().LABEL_NOMENCLATOR_TYPE);
		btnConsult.setText(AbosMessages.get().BUTTON_CONSULT);
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		list.setText(AbosMessages.get().LABEL_NOMENCLATOR_LIST);
		tabla.setColumnHeaders(Arrays.asList(AbosMessages.get().LABEL_NOMENCLATOR_TYPE, AbosMessages.get().LABEL_VALUE, MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION)));
		tabla.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);
		tabla.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		tabla.getPaginator().goToFirstPage();
		tabla.l10n();
		initialize(comboNomenclatorType, ((ControllerNomenclator) controller).listManagedTypes(library));

	}

	private void createTable(Composite test1) {
		tabla = new SecurityCRUDTreeTable(test1, SWT.NONE);
		add(tabla, Percent.W100);
		tabla.addListener(SWT.Resize, new Listener() {
	
			private static final long serialVersionUID = 8817895862824622805L;

			@Override
			public void handleEvent(Event event) {
				refresh();
			}
		});

		tabla.setEntityClass(Nomenclator.class);
		tabla.setVisualEntityManager(new MessageVisualEntityManager(tabla));
		tabla.setSearch(false);
		initializeCollection();
		tabla.setSaveAll(false);
		tabla.setAdd(true, new NomenclatorAddEditableArea(controller, nomenclatorTypes));
		tabla.setWatch(true, new NomenclatorWatchEditableArea(controller));
		tabla.setUpdate(true, new NomenclatorUpdateEditableArea(controller));
		tabla.setDelete("deleteNomenclatorID");
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
				Nomenclator nomenclator = (Nomenclator) event.entity.getRow();
				((ControllerNomenclator) controller).deleteNomenclator(nomenclator.getNomenclatorID());
			}
		});

		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				
				searchNomenclators(event.getSortExpression().isEmpty()?"nomenclatorName":event.getSortExpression(),event.getSortDirection(), event.currentPage - 1, event.pageSize);
			}
		});

		tabla.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {
				Nomenclator nomenclator = (Nomenclator) row;
				if (column.getIndex() == 4) {
					if ( nomenclator.getManageable() < 8)  {
						return true;
					}

				}
				if (column.getIndex() == 5) {
					if (nomenclator.getManageable() < 8) {
						return true;
					}
				}
				return false;
			}
		});

		tabla.setColumnHeaders(Arrays.asList(AbosMessages.get().LABEL_NOMENCLATOR_TYPE, AbosMessages.get().LABEL_VALUE, MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION)));
		TreeTableColumn columns[] = { new TreeTableColumn(30, 0, "getNomenclator.getNomenclatorName"), new TreeTableColumn(30, 1, "getNomenclatorName"),
				new TreeTableColumn(40, 2, "getNomenclatorDescription") };
		tabla.createTable(columns);
	}

	private void searchNomenclators(String orderExpression, int direction,int page, int size) {

		Nomenclator selected = (Nomenclator) UiUtils.getSelected(comboNomenclatorType);
		Page<Nomenclator> listNomenclator;

		if (selected != null) {
			listNomenclator = ((ControllerNomenclator) controller).listAll(library, selected.getNomenclatorID(), nomenclatorNameConsult, page, size, direction, orderExpression);
		} else {
			listNomenclator = ((ControllerNomenclator) controller).listAll(library, null, nomenclatorNameConsult, page, size, direction, orderExpression);
		}
		tabla.clearRows();
		tabla.setTotalElements((int) listNomenclator.getTotalElements());
		finalList = listNomenclator.getContent();

		tabla.setRows(finalList);
		tabla.refresh();

	}

	private void initializeCollection() {
		nomenclatorTypes = ((ControllerNomenclator) controller).listManagedTypes(library);
		
	}
}
