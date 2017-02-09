package cu.uci.abcd.statistic.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.statistic.ui.controller.AllManagementController;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abcd.statistic.ui.model.IndicatorAddEditableArea;
import cu.uci.abcd.statistic.ui.model.IndicatorUpdateEditableArea;
import cu.uci.abcd.statistic.ui.model.IndicatorWatchEditableArea;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class AdminIndicator extends ContributorPage {

	private Label list;
	private Label nameIndicator;
	private Label numIndicator;
	private Label separator;
	private Label searchCriteria;
	private Label headerLabel;

	private Text textindicatorName;
	private Text textNumIndicator;

	private Button btnConsult;
	private Button btnNewSearch;

	private SecurityCRUDTreeTable tabla;
	private String indicatorName = null;
	private String numberIndicator = null;
	private int direction = 1024;
	private String orderbyId = "indicatorId";

	private List<String> searchCriteriaList = new ArrayList<>();

	@Override
	public String contributorName() {
		return AbosMessages.get().MENU_ADMIN_INDICATOR;
	}

	public AdminIndicator() {
		super();
	}

	public Control createUIControl(Composite shell) {

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

		nameIndicator = new Label(test, SWT.NONE);
		add(nameIndicator);

		textindicatorName = new Text(test, SWT.NONE);
		add(textindicatorName);

		numIndicator = new Label(test, SWT.NONE);
		add(numIndicator);

		textNumIndicator = new Text(test, SWT.NONE);
		add(textNumIndicator);
		br();

		btnNewSearch = new Button(test, SWT.NONE);
		add(btnNewSearch);

		btnConsult = new Button(test, SWT.NONE);
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
		tabla.setEntityClass(Indicator.class);
		tabla.setVisualEntityManager(new MessageVisualEntityManager(tabla));
		tabla.setSaveAll(false);

		tabla.setAdd(true, new IndicatorAddEditableArea(controller));
		tabla.setWatch(true, new IndicatorWatchEditableArea(controller));
		tabla.setUpdate(true, new IndicatorUpdateEditableArea(controller));
		tabla.setDelete("deleteIndicatorID");
		tabla.setPageSize(10);

		tabla.setSearchHintText(AbosMessages.get().BUTTON_SEARCH);
		tabla.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		tabla.setSaveAllButtonText(AbosMessages.get().BUTTON_SAVE);
		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);

		CRUDTreeTableUtils.configUpdate(tabla);
		CRUDTreeTableUtils.configReports(tabla, contributorName(), searchCriteriaList);

		tabla.addDeleteListener(new TreeColumnListener() {
			public void handleEvent(final TreeColumnEvent event) {

				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION),
						MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), new DialogCallback() {
							private static final long serialVersionUID = 8415736231132589115L;

							@Override
							public void dialogClosed(int returnCode) {
								if (returnCode == 0) {
									Indicator indicator = (Indicator) event.entity.getRow();
									if (!(((AllManagementController) controller).getIndicator().deleteIndicator(indicator.getId()))) {
										RetroalimentationUtils.showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_ERROR_USED_DATA));
									} else {
										RetroalimentationUtils.showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_DELETE));
									}
									searchIndicator(0, tabla.getPaginator().getPageSize());
								}
							}

						});

			}
		});

		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderbyId = "nameIndicator";
						break;

					}
				}
				searchIndicator(event.currentPage - 1, event.pageSize);
			}
		});

		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR), AbosMessages.get().LABEL_NAME, AbosMessages.get().LABEL_CONSULT_QUERY));
		TreeTableColumn columns[] = { new TreeTableColumn(15, 0, "getIndicatorId"), new TreeTableColumn(40, 1, "getNameIndicator"), new TreeTableColumn(45, 2, "getQueryText") };
		tabla.createTable(columns);
	}

	@Override
	public String getID() {
		return "manageIndicatorID";
	}

	@Override
	public void l10n() {
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR), AbosMessages.get().LABEL_NAME, AbosMessages.get().LABEL_CONSULT_QUERY));
		headerLabel.setText(AbosMessages.get().LABEL_MANAGE_INDICATOR);
		list.setText(AbosMessages.get().LABEL_LIST_OF_COINCIDENCES);
		searchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		numIndicator.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR));
		nameIndicator.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		btnConsult.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		tabla.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		tabla.l10n();
	}

	private void searchIndicator(int page, int size) {
		Page<Indicator> list = ((AllManagementController) controller).ListAllIndicator(indicatorName, numberIndicator, page, size, direction, orderbyId);
		tabla.clearRows();
		tabla.setTotalElements((int) list.getTotalElements());
		tabla.setRows(list.getContent());
		tabla.refresh();

	}

	private void configureButtons(final Composite test1) {
		btnNewSearch.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				tabla.destroyEditableArea();
				searchCriteriaList.clear();
				textNumIndicator.setText("");
				textNumIndicator.setFocus();
				textindicatorName.setText("");
				textindicatorName.setFocus();
				indicatorName = null;
				numberIndicator = null;
				tabla.refresh();
				tabla.getPaginator().goToFirstPage();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});
		btnConsult.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				tabla.clearRows();
				tabla.destroyEditableArea();
				indicatorName = textindicatorName.getText().trim();
				numberIndicator = textNumIndicator.getText().trim();

				if (indicatorName.equalsIgnoreCase("") || indicatorName.equalsIgnoreCase(null) || indicatorName.equalsIgnoreCase("*")) {
					indicatorName = null;
				}
				if (numberIndicator.equalsIgnoreCase("") || numberIndicator.equalsIgnoreCase(null) || numberIndicator.equalsIgnoreCase("*")) {
					numberIndicator = null;
				}
				searchCriteriaList.clear();
				orderbyId = "indicatorId";
				direction = 1024;

				if (!(textindicatorName.getText().equals(""))) {
					searchCriteriaList.add(nameIndicator.getText());
					searchCriteriaList.add(textindicatorName.getText());
				}
				if (!(textNumIndicator.getText().equals(""))) {
					searchCriteriaList.add(" " + numIndicator.getText());
					searchCriteriaList.add(textNumIndicator.getText());

				}
				tabla.getPaginator().goToFirstPage();
				if (tabla.getEntities().size() == 0) {
					RetroalimentationUtils.showInformationMessage(test1, AbosMessages.get().MESSAGE_COINCIDENT);
				}
			}

		});
	}

}