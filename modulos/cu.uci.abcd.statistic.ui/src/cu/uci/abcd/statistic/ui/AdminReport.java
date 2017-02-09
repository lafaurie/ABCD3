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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.statistic.Report;
import cu.uci.abcd.statistic.ui.controller.AllManagementController;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abcd.statistic.ui.model.ReportAddEditableArea;
import cu.uci.abcd.statistic.ui.model.ReportUpdateEditableArea;
import cu.uci.abcd.statistic.ui.model.ReportWatchEditableArea;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class AdminReport extends ContributorPage {

	private Label searchCriteria;
	private Label list;
	private Label separator;
	private Label labelNameReport;
	private Text textNameReport;
	private Label headerLabel;

	private Button btnConsult;
	private Button btnNewSearch;

	private SecurityCRUDTreeTable tabla;
	private List<String> searchCriteriaList = new ArrayList<>();
	private String reportName = null;

	@Override
	public String contributorName() {
		return AbosMessages.get().MENU_ADMIN_REPORT;
	}

	@Override
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

		labelNameReport = new Label(test, SWT.NONE);
		add(labelNameReport);

		textNameReport = new Text(test, SWT.NONE);
		add(textNameReport);
		br();

		btnNewSearch = new Button(test, SWT.NONE);
		add(btnNewSearch);

		btnConsult = new Button(test, SWT.NONE);
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

		l10n();
		return shell;
	}

	private void configureButtons(final Composite test1) {
		btnNewSearch.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				tabla.destroyEditableArea();
				searchCriteriaList.clear();
				textNameReport.setText("");
				textNameReport.setFocus();
				reportName = null;
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
				reportName = textNameReport.getText().trim();

				if (reportName.equalsIgnoreCase("") || reportName.equalsIgnoreCase(null) || reportName.equalsIgnoreCase("*")) {
					reportName = null;
				}
				searchCriteriaList.clear();
				
				if (!(textNameReport.getText().equals(""))) {
					searchCriteriaList.add( labelNameReport.getText() ) ; searchCriteriaList.add(textNameReport.getText());
				}
					tabla.getPaginator().goToFirstPage();
					if (tabla.getEntities().size()==0) {
						 RetroalimentationUtils.showInformationMessage(test1, AbosMessages.get().MESSAGE_COINCIDENT);
					}
				
			}
		});
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
		tabla.setEntityClass(Report.class);
		tabla.setVisualEntityManager(new MessageVisualEntityManager(tabla));
		tabla.setSaveAll(false);
		tabla.setAdd(true, new ReportAddEditableArea(controller));
		tabla.setWatch(true, new ReportWatchEditableArea(controller));
		tabla.setUpdate(true, new ReportUpdateEditableArea(controller));
		tabla.setDelete("deleteReportID");
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
				Report report = (Report) event.entity.getRow();
				((AllManagementController) controller).getReport().deleteReport(report.getId());
			}
		});
		tabla.addPageChangeListener(new PageChangeListener() {

			@Override
			public void pageChanged(final PageChangedEvent event) {
				searchReports( event.getSortExpression().isEmpty()?"nameReport":event.getSortExpression(),event.getSortDirection(), event.currentPage - 1, event.pageSize);
			}
		});
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NAME)));
		TreeTableColumn columns[] = { new TreeTableColumn(100, 0, "getNameReport") };
		tabla.createTable(columns);
	}

	@Override
	public String getID() {
		return "manageReportID";
	}

	@Override
	public void l10n() {
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NAME)));
		headerLabel.setText(AbosMessages.get().LABEL_MANAGE_REPORT);
		searchCriteria.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		labelNameReport.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		btnNewSearch.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		btnConsult.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		list.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_COINCIDENCES));
		tabla.setActionButtonText("exportPDFButton", AbosMessages.get().BUTTON_EXPORT_TO_PDF);
		tabla.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		tabla.l10n();

	}

	private void searchReports(String orderBy, int direction, int page, int size) {
		Page<Report> lis = ((AllManagementController) controller).ListAllReport(reportName, page, size, direction, orderBy);
			tabla.clearRows();
			tabla.setTotalElements((int) lis.getTotalElements());
			tabla.setRows(lis.getContent());
			tabla.refresh();
	}

}
