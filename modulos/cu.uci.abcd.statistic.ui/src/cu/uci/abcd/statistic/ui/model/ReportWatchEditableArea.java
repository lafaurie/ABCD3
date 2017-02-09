package cu.uci.abcd.statistic.ui.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.domain.statistic.Report;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ReportWatchEditableArea extends BaseEditableArea {

	private Label headerLabel;
	Label labelNameReport;
	Text textlabelNameReport;
	private Label indicators;
	private CRUDTreeTable tabla;
	private Report report;
	private Composite top;

	public ReportWatchEditableArea(ViewController controller) {
		super();
		this.controller = controller;
	}

	@Override
	public String getID() {
		return "viewReportID";
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager arg2) {
		report = entity.getRow();
		top = parent;
		setDimension(parent.getParent().getParent().getBounds().width);
		addComposite(parent);

		Composite topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(topGroup);

		buildMessage(topGroup);

		headerLabel = new Label(topGroup, SWT.NORMAL);
		addHeader(headerLabel);
		addSeparator(new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL));

		labelNameReport = new Label(topGroup, SWT.NONE);
		add(labelNameReport);

		textlabelNameReport = new Text(topGroup, SWT.NONE);
		add(textlabelNameReport);
		textlabelNameReport.setText(report.getNameReport());
		textlabelNameReport.setEditable(false);
		br();

		indicators = new Label(topGroup, SWT.NONE);
		addHeader(indicators);
		br();
		createTable(topGroup);
		return parent;
	}

	@Override
	public void l10n() {
		headerLabel.setText(AbosMessages.get().LABEL_VIEW_REPORT);
		labelNameReport.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		indicators.setText(AbosMessages.get().LABEL_INDICATORS);
		tabla.l10n();

	}

	public void dispose() {
		if (top != null && !top.isDisposed()) {
			top.dispose();
		}
	}

	private void createTable(Composite test) {
		tabla = new CRUDTreeTable(test, SWT.NONE);
		add(tabla, Percent.W100);
		tabla.setEntityClass(Indicator.class);
		tabla.setSaveAll(false);
		tabla.setPageSize(10);

		tabla.setSearchHintText(AbosMessages.get().BUTTON_SEARCH);
		tabla.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);

		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				searchIndicator(event.currentPage - 1, event.pageSize);
			}
		});

		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR), AbosMessages.get().LABEL_NAME));
		TreeTableColumn columns[] = { new TreeTableColumn(15, 0, "getIndicatorId"), new TreeTableColumn(40, 1, "getNameIndicator") };
		tabla.createTable(columns);
	}

	private void searchIndicator(int page, int size) {
		tabla.clearRows();
		Collections.sort(report.getIndicators(), new Comparator<Indicator>() {

			@Override
			public int compare(Indicator o1, Indicator o2) {
				return o1.getIndicatorId().compareToIgnoreCase(o2.getIndicatorId());
			}
		});
		tabla.setTotalElements(report.getIndicators().size());
		tabla.setRows(report.getIndicators().subList(page * size, page * size + size < report.getIndicators().size() ? page * size + size : report.getIndicators().size()));
		tabla.refresh();
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		super.createButtons(parent, entity, manager);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

}
