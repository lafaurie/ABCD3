package cu.uci.abcd.statistic.ui;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.domain.statistic.Report;
import cu.uci.abcd.domain.statistic.ReportGroup;
import cu.uci.abcd.statistic.ui.controller.AllManagementController;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.ReportType;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.URLUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;
import cu.uci.abos.reports.CollectEntities;
import cu.uci.abos.reports.DocumentParam;
import cu.uci.abos.reports.Entity;
import cu.uci.abos.reports.EntityParam;
import cu.uci.abos.reports.PDFReportGenerator;
import cu.uci.abos.reports.ReportParam;
import cu.uci.abos.reports.TabularParam;
import cu.uci.abos.reports.XLSReportGenerator;

/**
 * @author Dayana Rivera Muñoz
 * 
 */
public class GenerateReport extends ContributorPage {
	private Library library;
	private String libraryJisis;
	private CRUDTreeTable tabla;
	private Label generalData;
	private Label headerLabel;
	private Label academicCourse;
	private Label sixmonths;
	private Label code;
	private Label organism;
	private Label typeReport;
	private Label rangeOfDate;
	private Label separator;
	private Label start_date;
	private Label end_date;
	private Label libraryName;
	private Text academicCourseText;
	private Text sixmonthsText;
	private Text codeText;
	private Text organismText;

	private Combo combTypeReport;
	private Text textLibraryName;
	ValidatorUtils validator;

	private DateTime timeFrom;
	private DateTime timeTo;
	private Button btnGenerate;
	private Button generatePDF;
	private Button generateEXEL;
	private Button cancel;
	private List<String> reportData = new ArrayList<>();
	private Date startDate;
	private Date endDate;
	private List<Indicator> collection = new ArrayList<>();
	private List<Indicator> auxiliarList = new ArrayList<>();

	@Override
	public String contributorName() {
		return AbosMessages.get().MENU_GENERAR_REPORT;
	}

	@Override
	public String getID() {
		return "generateReportID";
	}

	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		libraryJisis = library.getIsisDefHome();

		validator = new ValidatorUtils(new CustomControlDecoration());

		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");		
		
		final Composite test = new Composite(shell, SWT.NORMAL);
		addComposite(test);
		test.setData(RWT.CUSTOM_VARIANT, "gray_background");
		headerLabel = new Label(test, SWT.NORMAL);
		addHeader(headerLabel);
		   
		Composite resize = new Composite(shell, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height-20);

		
		addSeparator(new Label(test, SWT.SEPARATOR | SWT.HORIZONTAL));
		
		generalData = new Label(test, SWT.NONE);
		addHeader(generalData);

		academicCourse = new Label(test, SWT.NORMAL);
		add(academicCourse);

		academicCourseText = new Text(test, SWT.NORMAL);
		add(academicCourseText);

		sixmonths = new Label(test, SWT.NORMAL);
		add(sixmonths);

		sixmonthsText = new Text(test, SWT.NORMAL);
		add(sixmonthsText);
		br();

		code = new Label(test, SWT.NORMAL);
		add(code);

		codeText = new Text(test, SWT.NORMAL);
		add(codeText);

		libraryName = new Label(test, SWT.NORMAL);
		add(libraryName);

		textLibraryName = new Text(test, SWT.NORMAL);
		textLibraryName.setText(library.getLibraryName());
		add(textLibraryName);
		textLibraryName.setEditable(false);
		br();

		organism = new Label(test, SWT.NORMAL);
		add(organism);

		organismText = new Text(test, SWT.NORMAL);
		add(organismText);

		typeReport = new Label(test, SWT.NORMAL);
		add(typeReport);

		combTypeReport = new Combo(test, SWT.READ_ONLY);
		add(combTypeReport);
		initialize(combTypeReport, ((AllManagementController) controller).getReport().findAllReport());
		validator.applyValidator(combTypeReport, "combTypeReport", DecoratorType.REQUIRED_FIELD, true);

		br();

		rangeOfDate = new Label(test, SWT.NORMAL);
		add(rangeOfDate);
		br();

		start_date = new Label(test, SWT.NONE);
		add(start_date);

		timeFrom = new DateTime(test, SWT.BORDER | SWT.DROP_DOWN);
		add(timeFrom);

		end_date = new Label(test, SWT.NONE);
		add(end_date);

		timeTo = new DateTime(test, SWT.BORDER | SWT.DROP_DOWN);
		add(timeTo);
		br();

		btnGenerate = new Button(test, SWT.PUSH);
		add(btnGenerate);
		br();

		separator = new Label(test, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		br();

		cancel = new Button(test, SWT.PUSH);
		add(cancel);
		cancel.setVisible(false);

		generatePDF = new Button(test, SWT.PUSH);
		add(generatePDF);
		generatePDF.setVisible(false);

		generateEXEL = new Button(test, SWT.PUSH);
		add(generateEXEL);
		generateEXEL.setVisible(false);

		tabla = new CRUDTreeTable(test, SWT.NONE);
		tabla.setTouchEnabled(true);
		tabla.setVisible(false);
		add(tabla, Percent.W100);
		tabla.addListener(SWT.Resize, new Listener() {

			private static final long serialVersionUID = 520965948574695389L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();

			}
		});
		tabla.setEntityClass(Indicator.class);
		tabla.setPageSize(100);

		CRUDTreeTableUtils.configUpdate(tabla);

		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR), AbosMessages.get().TABLE_CONCEPT, AbosMessages.get().TABLE_COUNT));
		TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getIndicatorId"), new TreeTableColumn(60, 1, "getNameIndicator"), new TreeTableColumn(20, 2, "getValue") };
		tabla.createTable(columns);

		tabla.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				searchIndicator(event.currentPage - 1, event.pageSize);
			}
		});

		generateEXEL.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 7299996306711846602L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (UiUtils.getSelected(combTypeReport) != null) {
					List<ReportParam> params = new LinkedList<>();
					CollectEntities ec = new CollectEntities();
					EntityParam ep = new EntityParam();
					ep.setHeader(MessageUtil.unescape(AbosMessages.get().LABEL_REPORT_DATA));
					List<Entity> items = new LinkedList<>();

					for (Iterator<String> iterator = generalData().iterator(); iterator.hasNext();) {
						items.add(new Entity(iterator.next(), iterator.next()));
					}
					ep.setItems(items);
					List<EntityParam> p = new ArrayList<>();
					p.add(ep);
					ec.setItems(p);
					params.add(ec);
					TabularParam tab = new TabularParam(UiUtils.getSelected(combTypeReport).toString(), tabla.getColumnHeaders(), tabla.getCellValues());
					params.add(tab);

					DocumentParam param = new DocumentParam(UiUtils.getSelected(combTypeReport).toString(), params);
					URLUtil.generateDownloadReport(
							ReportType.SPREADSHEET,
							ServiceProviderUtil.getService(XLSReportGenerator.class).generateReport(ServiceProviderUtil.getService(XLSReportGenerator.class).getStyle(),
									UiUtils.getSelected(combTypeReport).toString(), ServiceProviderUtil.getService(XLSReportGenerator.class).getReportTemplate(), param));

				}
			}

		});
		generatePDF.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 6134730240104351316L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (UiUtils.getSelected(combTypeReport) != null) {
					List<ReportParam> params = new LinkedList<>();
					CollectEntities ec = new CollectEntities();
					EntityParam ep = new EntityParam();
					ep.setHeader(MessageUtil.unescape(AbosMessages.get().LABEL_REPORT_DATA));
					List<Entity> items = new LinkedList<>();

					for (Iterator<String> iterator = generalData().iterator(); iterator.hasNext();) {
						items.add(new Entity(iterator.next(), iterator.next()));
					}
					ep.setItems(items);
					List<EntityParam> p = new ArrayList<>();
					p.add(ep);
					ec.setItems(p);
					params.add(ec);
					TabularParam tab = new TabularParam(UiUtils.getSelected(combTypeReport).toString(), tabla.getColumnHeaders(), tabla.getCellValues());
					params.add(tab);

					DocumentParam param = new DocumentParam(UiUtils.getSelected(combTypeReport).toString(), params);
					URLUtil.generateDownloadReport(
							ReportType.PDF,
							ServiceProviderUtil.getService(PDFReportGenerator.class).generateReport(ServiceProviderUtil.getService(PDFReportGenerator.class).getStyle(),
									UiUtils.getSelected(combTypeReport).toString(), ServiceProviderUtil.getService(PDFReportGenerator.class).getReportTemplate(), param));

				}
			}

		});

		btnGenerate.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (validator.decorationFactory.AllControlDecorationsHide()) {
					

					
					int fromYear = timeFrom.getYear() - 1900;
					int fromMonth = timeFrom.getMonth();
					int fromDay = timeFrom.getDay();

					startDate = new Date(fromYear, fromMonth, fromDay);

					int toYear = timeTo.getYear() - 1900;
					int toMonth = timeTo.getMonth();
					int toDay = timeTo.getDay();

					endDate = new Date(toYear, toMonth, toDay);
					
					if (!startDate.before(endDate) && !(startDate.equals(endDate))) {
						showInformationMessage(AbosMessages.get().MESSAGE_REPORT);
					} else {

						tabla.clearRows();
						collection.removeAll(auxiliarList);
						Report report = (Report) UiUtils.getSelected(combTypeReport);

						collection = ((AllManagementController) controller).getReport().generateReportValues(report, new Object[] { library.getLibraryID(), startDate, endDate, libraryJisis })
								.getIndicators();
						insertParents();
						tabla.getPaginator().goToFirstPage();
						tabla.setVisible(true);
						generateEXEL.setVisible(true);
						generatePDF.setVisible(true);
						cancel.setVisible(true);
						refresh();
					}

				} else {
					RetroalimentationUtils.showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}
			}
		});

		cancel.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			public void widgetSelected(SelectionEvent e) {
				GenerateReport.this.notifyListeners(SWT.Dispose, new Event());
				ContributorService contributorService = getContributorService();
				contributorService.selectContributor("generateReportID");
				
			}
		});

		l10n();
		return shell;
	}

	@Override
	public void l10n() {
		headerLabel.setText(AbosMessages.get().LABEL_GENERATE_REPORT);
		generalData.setText(AbosMessages.get().LABEL_REPORT_DATA);
		academicCourse.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACADEMIC_COURSE));
		sixmonths.setText(AbosMessages.get().LABEL_SIXMONTHS);
		libraryName.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIBRARY_NAME));
		code.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CODE));
		organism.setText(AbosMessages.get().LABEL_ORGANISM);
		cancel.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		generateEXEL.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		generatePDF.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		typeReport.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		rangeOfDate.setText(AbosMessages.get().LABEL_RANGE_OF_DATE);
		start_date.setText(MessageUtil.unescape(AbosMessages.get().LABEL_FROM));
		end_date.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TO));
		btnGenerate.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_GENERATE));
		tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR), AbosMessages.get().TABLE_CONCEPT, AbosMessages.get().TABLE_COUNT));
		tabla.l10n();

	}

	private List<String> generalData() {
		reportData.clear();

		UiUtils.get().addSearchCriteria(reportData, academicCourse.getText(), academicCourseText.getText().replaceAll(" +", " ").trim()).addSearchCriteria(reportData, sixmonths.getText(), sixmonthsText.getText().replaceAll(" +", " ").trim())
				.addSearchCriteria(reportData, code.getText(), codeText.getText().replaceAll(" +", " ").trim()).addSearchCriteria(reportData, organism.getText(), organismText.getText().replaceAll(" +", " ").trim())
				.addSearchCriteria(reportData, libraryName.getText(), textLibraryName.getText().replaceAll(" +", " ").trim()).addSearchCriteria(reportData, start_date.getText(), FormatDate(startDate))
				.addSearchCriteria(reportData, end_date.getText(), FormatDate(endDate));

		return reportData;
	}

	private void searchIndicator(int page, int size) {
		tabla.clearRows();
		Collections.sort(collection, new Comparator<Indicator>() {

			@Override
			public int compare(Indicator o1, Indicator o2) {
				return o1.getIndicatorId().compareToIgnoreCase(o2.getIndicatorId());
			}
		});

		tabla.setTotalElements((int) collection.size());
		if (collection.size() <= page * size + size) {
			tabla.setRows(collection.subList(page * size, collection.size()));
		} else {
			tabla.setRows(collection.subList(page * size, page * size + size));
		}
		tabla.refresh();

	}

	public static String FormatDate(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);

	}

	private void insertParents() {
		List<ReportGroup> parents = ((AllManagementController) controller).getIndicator().searchParents();
		auxiliarList.clear();
		for (int i = 0; i < parents.size(); i++) {
			Indicator auxiliar = new Indicator();
			auxiliar.setIndicatorId(parents.get(i).getIndicatorList());
			auxiliar.setNameIndicator(parents.get(i).getPosition());
			auxiliar.setValue("");
			auxiliar.setQueryText("");
			int count = parents.get(i).getIndicatorList().length();
			for (int j = 0; j < collection.size(); j++) {
				if (collection.get(j).getIndicatorId().length() > count) {
					String subString = collection.get(j).getIndicatorId().substring(0, count);
					if (auxiliar.getIndicatorId().equals(subString)) {
						auxiliarList.add(auxiliar);
						break;
					}
				}

			}
		}
		collection.addAll(auxiliarList);

	}

}
