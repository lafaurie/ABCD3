package cu.uci.abcd.statistic.ui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.domain.statistic.Report;
import cu.uci.abcd.statistic.ui.controller.AllManagementController;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ReportAddEditableArea extends BaseEditableArea {

	private Label headerLabel;
	private Label reportData;
	private Label labelNameReport;
	private Label indicators;
	private Text textlabelNameReport;
	private Label indicatorsAssociated;
	private CRUDTreeTable tablaIndicator;
	private CRUDTreeTable tablaAsociate;

	private Button saveBtn;
	private Composite topGroup;
	private Composite top;
	private List<Indicator> indicatorList = new ArrayList<>();
	private List<Indicator> list = new ArrayList<>();
	

	public ReportAddEditableArea(ViewController controller) {
		super();
		this.controller = controller;
	}

	@Override
	public String getID() {
		return "addReportID";
	}

	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager arg2) {
		indicatorList.clear();
		list.clear();
		
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

		reportData = new Label(topGroup, SWT.NORMAL);
		reportData.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		addHeader(reportData);

		labelNameReport = new Label(topGroup, SWT.NONE);
		add(labelNameReport);

		textlabelNameReport = new Text(topGroup, SWT.NONE);
		add(textlabelNameReport);
		validator.applyValidator(textlabelNameReport, "textlabelNameReport", DecoratorType.ALPHA_NUMERICS_SPACES, true, 255);
		validator.applyValidator(textlabelNameReport, "textlabelNameReport1", DecoratorType.REQUIRED_FIELD, true);
		br();

		indicators = new Label(topGroup, SWT.NORMAL);
		add(indicators, Percent.W100);
		indicators.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		indicators.setAlignment(SWT.LEFT);

		createTables(topGroup);
		return parent;

	}

	private void createTables(final Composite parent) {
		list = ((AllManagementController) controller).ListIndicator( null, null);
		tablaIndicator = new CRUDTreeTable(parent, SWT.NONE);
		add(tablaIndicator, Percent.W100);
		tablaIndicator.addListener(SWT.Resize, new Listener() {

			private static final long serialVersionUID = 7298377232294447094L;

			@Override
			public void handleEvent(Event arg0) {
				ReportAddEditableArea.this.refresh();

			}
		});
		tablaIndicator.setEntityClass(Indicator.class);
		tablaIndicator.setSearch(false);
		tablaIndicator.setSaveAll(false);
		tablaIndicator.setPageSize(5);

		indicatorsAssociated = new Label(parent, SWT.NORMAL);
		add(indicatorsAssociated, Percent.W100);

		indicatorsAssociated.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		indicatorsAssociated.setAlignment(SWT.LEFT);
		br();

		tablaAsociate = new CRUDTreeTable(parent, SWT.NONE);
		add(tablaAsociate, Percent.W100);
		tablaAsociate.addListener(SWT.Resize, new Listener() {

			private static final long serialVersionUID = 520965948574695389L;

			@Override
			public void handleEvent(Event arg0) {
				ReportAddEditableArea.this.refresh();

			}
		});
		tablaAsociate.setEntityClass(Indicator.class);
		tablaAsociate.setDelete(true);
		tablaAsociate.setSearch(false);
		tablaAsociate.setSaveAll(false);
		tablaAsociate.setPageSize(5);

		tablaIndicator.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					switch (event.sortData.columnIndex) {
					case 1:
						break;

					}
				}
				searchIndicator(event.currentPage - 1, event.pageSize);
			}
		});

		tablaAsociate.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					switch (event.sortData.columnIndex) {
					case 1:
						break;

					}
				}

				page(event.currentPage - 1, event.pageSize);
			}
		});

		Column checked = new Column("right-arrow", parent.getDisplay(), CRUDTreeTableUtils.move(tablaAsociate, indicatorList));
		checked.setToolTipText(MessageUtil.unescape(AbosMessages.get().BUTTON_ASSOCIATE_SELECTION));
		tablaIndicator.addActionColumn(checked);
		tablaIndicator.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR), AbosMessages.get().LABEL_NAME));

		TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getIndicatorId"), new TreeTableColumn(80, 1, "getNameIndicator") };
		tablaIndicator.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR), AbosMessages.get().LABEL_NAME));
		tablaIndicator.createTable(columns);

		tablaAsociate.setSearchHintText(AbosMessages.get().BUTTON_SEARCH);
		tablaAsociate.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		tablaAsociate.setSaveAllButtonText(AbosMessages.get().BUTTON_SAVE);
		tablaAsociate.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);

		tablaAsociate.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR), AbosMessages.get().LABEL_NAME));
		TreeTableColumn columns1[] = { new TreeTableColumn(20, 0, "getIndicatorId"), new TreeTableColumn(80, 1, "getNameIndicator") };
		tablaAsociate.createTable(columns1);

		tablaAsociate.addDeleteListener(CRUDTreeTableUtils.addRemove(tablaIndicator, indicatorList, list));

		tablaAsociate.getPaginator().setPageSize(5);
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
					if (!(indicatorList.isEmpty())) {
						Report report = new Report();
						report.setNameReport(textlabelNameReport.getText().replaceAll(" +", " ").trim());
						report.setIndicators(indicatorList);
						Report exist = ((AllManagementController) controller).getReport().findReportByName(report.getNameReport());
						if (exist == null) {
							((AllManagementController) controller).getReport().addReport(report);
							manager.add(new BaseGridViewEntity<Report>(report));
							manager.refresh();
						} else {
							showErrorMessage(AbosMessages.get().MESSAGE_NO_ADD);
						}
					} else {
						showErrorMessage(AbosMessages.get().MESSAGE_EMPTY_REPORT);
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

		reportData.setText(AbosMessages.get().LABEL_REPORT_DATA);
		labelNameReport.setText(AbosMessages.get().LABEL_NAME);
		headerLabel.setText(AbosMessages.get().LABEL_ADD_NEW_REPORT);
		indicators.setText(AbosMessages.get().LABEL_INDICATORS);
		indicatorsAssociated.setText(AbosMessages.get().LABEL_INDICATORS_ASSOCIATED);
		saveBtn.setText(AbosMessages.get().BUTTON_ACEPT);
		tablaAsociate.l10n();
		tablaIndicator.l10n();

	}

	private void searchIndicator(int page, int size) {
		
		tablaIndicator.clearRows();
		for (Iterator<Indicator> iterator = list.iterator(); iterator.hasNext();) {
			Indicator indicator = (Indicator) iterator.next();
			for (Iterator<Indicator> iterator2 = indicatorList.iterator(); iterator2.hasNext();) {
				Indicator indicator2 = (Indicator) iterator2.next();
				if (indicator.getId().equals(indicator2.getId()))
					iterator.remove();
			}
		}
		Collections.sort(list, new Comparator<Indicator>() {

			@Override
			public int compare(Indicator o1, Indicator o2) {
				return o1.getIndicatorId().compareToIgnoreCase(o2.getIndicatorId());
			}
		});
	
		tablaIndicator.setTotalElements((int) list.size());
		if (list.size() <= page * size + size) {
			tablaIndicator.setRows(list.subList(page * size, list.size()));
		} else {
			tablaIndicator.setRows(list.subList(page * size, page * size + size));
		}
		tablaIndicator.refresh();

	}

	private void page(int page, int size) {
		tablaAsociate.clearRows();
		Collections.sort(indicatorList, new Comparator<Indicator>() {

			@Override
			public int compare(Indicator o1, Indicator o2) {
				return o1.getIndicatorId().compareToIgnoreCase(o2.getIndicatorId());
			}
		});
		tablaAsociate.setTotalElements((int) indicatorList.size());
		if (indicatorList.size() <= page * size + size) {
			tablaAsociate.setRows(indicatorList.subList(page * size, indicatorList.size()));
		} else {
			tablaAsociate.setRows(indicatorList.subList(page * size, page * size + size));
		}
		tablaAsociate.refresh();
		tablaIndicator.refresh();
	}

}