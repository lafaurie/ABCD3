package cu.uci.abcd.administration.library.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.CalendarViewController;
import cu.uci.abcd.administration.library.ui.model.CalendarAddArea;
import cu.uci.abcd.administration.library.ui.model.CalendarUpdateArea;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Calendar;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ConfigureCalendar extends ContributorPage implements Contributor {
	private Label configureCalendarLabel;
	private SecurityCRUDTreeTable calendarOnlyThisYearTable;
	private SecurityCRUDTreeTable calendarAllYearTable;
	private Label allYearLabel;
	private Label onlyThisYearLabel;
	private List<String> searchCriteriaListOnlyThisYear = new ArrayList<>();
	private List<String> searchCriteriaListAllYear = new ArrayList<>();
	private Library library;
	private int direction = 1024;
	private String orderByString = "calendarDay";
	private Composite onlyThisYearComposite;
	private Composite allYearComposite;
	private Button presentYearButton;
	private Button allYearButton;

	@Override
	public String contributorName() {
		return AbosMessages.get().CALENDAR;
	}

	@Override
	public String getID() {
		return "configureCalendarID";
	}

	@Override
	public Control createUIControl(Composite shell) {
		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");
		Composite scroll = new Composite(shell, SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0)
				.withHeight(Display.getCurrent().getBounds().height - 172);
		configureCalendarLabel = new Label(shell, SWT.NORMAL);
		addHeader(configureCalendarLabel);
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		allYearComposite = new Composite(shell, SWT.NONE);
		allYearComposite.setLayout(new FormLayout());
		allYearComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(allYearComposite);
		FormDatas.attach(allYearComposite).atTopTo(configureCalendarLabel)
				.atLeft(0).atRight(0);
		allYearLabel = new Label(allYearComposite, SWT.NORMAL);
		allYearLabel.setLayoutData(new FormData());
		addHeader(allYearLabel);
		calendarAllYearTable = new SecurityCRUDTreeTable(allYearComposite,
				SWT.NONE);
		add(calendarAllYearTable);
		calendarAllYearTable.setEntityClass(Calendar.class);
		calendarAllYearTable.setSearch(false);
		calendarAllYearTable.setSaveAll(false);
		calendarAllYearTable.setAdd(true, new CalendarAddArea(
				calendarAllYearTable, controller, 1));
		calendarAllYearTable.setUpdate(true, new CalendarUpdateArea(
				calendarAllYearTable, controller, 1));
		calendarAllYearTable.setDelete("deleteCalendarID");
		calendarAllYearTable.setVisible(true);
		calendarAllYearTable.setPageSize(10);
		CRUDTreeTableUtils.configUpdate(calendarAllYearTable);
		CRUDTreeTableUtils.configReports(calendarAllYearTable,
				contributorName(), searchCriteriaListAllYear);
		CRUDTreeTableUtils.configRemove(calendarAllYearTable,
				new IActionCommand() {
					@Override
					public void execute(final TreeColumnEvent event) {
						calendarAllYearTable.destroyEditableArea();
						Calendar calendar = (Calendar) event.entity.getRow();
						Long calendarId = calendar.getCalendarID();
						((CalendarViewController) controller)
								.deleteCalendarById(calendarId);
						Nomenclator dayType = ((CalendarViewController) controller)
								.getAllManagementLibraryViewController()
								.getLibraryService()
								.findNomenclatorById(
										Nomenclator.DAY_TYPE_ALL_YEAR);
						searchCalendar(calendarAllYearTable, dayType, false, 0,
								calendarAllYearTable.getPageSize());
					}
				});

		TreeTableColumn columnsAllYear[] = {
				new TreeTableColumn(20, 0, "getDayAndMonthToString"),
				new TreeTableColumn(30, 1, "getCalendarName"),
				new TreeTableColumn(50, 2, "getDescription") };

		calendarAllYearTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 8817895862824622805L;

			@Override
			public void handleEvent(Event event) {
				refresh();
			}
		});

		calendarAllYearTable.createTable(columnsAllYear);
		calendarAllYearTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "description";
						break;
					case 2:
						orderByString = "description";
						break;
					}
				}
				Nomenclator dayType = ((CalendarViewController) controller)
						.getAllManagementLibraryViewController()
						.getLibraryService()
						.findNomenclatorById(Nomenclator.DAY_TYPE_ALL_YEAR);
				searchCalendar(calendarAllYearTable, dayType, false,
						event.currentPage - 1, event.pageSize);
			}
		});
		onlyThisYearComposite = new Composite(shell, SWT.NONE);
		onlyThisYearComposite.setLayout(new FormLayout());
		onlyThisYearComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(onlyThisYearComposite);
		FormDatas.attach(onlyThisYearComposite).atTopTo(allYearComposite)
				.atLeft(0).atRight(0);
		onlyThisYearLabel = new Label(onlyThisYearComposite, SWT.NORMAL);
		addHeader(onlyThisYearLabel);
		presentYearButton = new Button(onlyThisYearComposite, SWT.RADIO);
		presentYearButton.setSelection(true);
		add(presentYearButton);
		presentYearButton.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = -4186307840238390263L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				calendarOnlyThisYearTable.getPaginator().goToFirstPage();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		allYearButton = new Button(onlyThisYearComposite, SWT.RADIO);
		add(allYearButton);
		br();
		allYearButton.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = -4186307840238390263L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				calendarOnlyThisYearTable.getPaginator().goToFirstPage();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		calendarOnlyThisYearTable = new SecurityCRUDTreeTable(
				onlyThisYearComposite, SWT.NONE);
		add(calendarOnlyThisYearTable);
		calendarOnlyThisYearTable.setEntityClass(Calendar.class);
		calendarOnlyThisYearTable.setSearch(false);
		calendarOnlyThisYearTable.setSaveAll(false);
		calendarOnlyThisYearTable.setAdd(true, new CalendarAddArea(
				calendarOnlyThisYearTable, controller, 2));
		calendarOnlyThisYearTable.setUpdate(true, new CalendarUpdateArea(
				calendarOnlyThisYearTable, controller, 2));
		calendarOnlyThisYearTable.setDelete("deleteCalendarID");
		calendarOnlyThisYearTable.setVisible(true);
		calendarOnlyThisYearTable.setPageSize(10);
		calendarOnlyThisYearTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 8817895862824622805L;

			@Override
			public void handleEvent(Event event) {
				refresh();
			}
		});
		CRUDTreeTableUtils.configUpdate(calendarOnlyThisYearTable);
		CRUDTreeTableUtils.configReports(calendarOnlyThisYearTable,
				contributorName(), searchCriteriaListOnlyThisYear);
		TreeTableColumn columnsThisYear[] = {
				new TreeTableColumn(20, 0, "getDateToString"),
				new TreeTableColumn(30, 1, "getCalendarName"),
				new TreeTableColumn(50, 2, "getDescription") };
		calendarOnlyThisYearTable.createTable(columnsThisYear);
		CRUDTreeTableUtils.configRemove(calendarOnlyThisYearTable,
				new IActionCommand() {
					@Override
					public void execute(final TreeColumnEvent event) {
						calendarOnlyThisYearTable.destroyEditableArea();
						Calendar calendar = (Calendar) event.entity.getRow();
						Long calendarId = calendar.getCalendarID();
						((CalendarViewController) controller)
								.deleteCalendarById(calendarId);
						Nomenclator dayType = ((CalendarViewController) controller)
								.getAllManagementLibraryViewController()
								.getLibraryService()
								.findNomenclatorById(
										Nomenclator.DAY_TYPE_THIS_YEAR);
						searchCalendar(calendarOnlyThisYearTable, dayType,
								presentYearButton.getSelection(), 0,
								calendarOnlyThisYearTable.getPageSize());
					}
				});
		calendarOnlyThisYearTable
				.addPageChangeListener(new PageChangeListener() {
					@Override
					public void pageChanged(final PageChangedEvent event) {
						if (event.sortData != null) {
							direction = event.sortData.sortDirection;
							switch (event.sortData.columnIndex) {
							case 0:
								orderByString = "calendarDay";
								break;
							case 1:
								orderByString = "calendarName";
								break;
							case 2:
								orderByString = "description";
								break;
							}
						}
						Nomenclator dayType = ((CalendarViewController) controller)
								.getAllManagementLibraryViewController()
								.getLibraryService()
								.findNomenclatorById(
										Nomenclator.DAY_TYPE_THIS_YEAR);

						searchCalendar(calendarOnlyThisYearTable, dayType,
								presentYearButton.getSelection(),
								event.currentPage - 1, event.pageSize);
					}
				});
		calendarOnlyThisYearTable.getPaginator().goToFirstPage();
		calendarAllYearTable.getPaginator().goToFirstPage();
		l10n();
		return shell;
	}

	private void searchCalendar(SecurityCRUDTreeTable table,
			Nomenclator dayType, boolean thisYear, int page, int size) {
		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");
		Page<Calendar> list = ((CalendarViewController) controller)
				.findCalendarByLibraryAndDayType(library, dayType, thisYear,
						page, size, direction, orderByString);
		table.clearRows();
		table.setTotalElements((int) list.getTotalElements());
		table.setRows(list.getContent());
		table.refresh();
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		presentYearButton
				.setText(MessageUtil.unescape(AbosMessages.get().ACTUAL_YEAR));
		allYearButton
				.setText(MessageUtil.unescape(AbosMessages.get().ALL_YEAR));
		configureCalendarLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CONFIGURE_CALENDAR));
		allYearLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ALL_YEAR));
		java.util.Date d = new java.util.Date();
		Date c = new Date(d.getTime());
		int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(c));
		onlyThisYearLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ONLY_THIS_YEAR)
						+ " " + fromYear);
		calendarAllYearTable.setAddButtonText(MessageUtil.unescape(AbosMessages
				.get().BUTTON_ADD));
		calendarOnlyThisYearTable.setAddButtonText(MessageUtil
				.unescape(AbosMessages.get().BUTTON_ADD));
		calendarAllYearTable.setCancelButtonText(MessageUtil
				.unescape(AbosMessages.get().BUTTON_CANCEL));
		calendarOnlyThisYearTable.setCancelButtonText(MessageUtil
				.unescape(AbosMessages.get().BUTTON_CANCEL));
		calendarAllYearTable.setActionButtonText("exportPDFButton",
				MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		calendarAllYearTable
				.setActionButtonText("exportExcelButton", MessageUtil
						.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		calendarOnlyThisYearTable.setActionButtonText("exportPDFButton",
				MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		calendarOnlyThisYearTable
				.setActionButtonText("exportExcelButton", MessageUtil
						.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		calendarOnlyThisYearTable.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE),
				MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION)));
		calendarAllYearTable.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_DATE),
				MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),
				MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION)));
		calendarAllYearTable.l10n();
		calendarOnlyThisYearTable.l10n();
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

}
