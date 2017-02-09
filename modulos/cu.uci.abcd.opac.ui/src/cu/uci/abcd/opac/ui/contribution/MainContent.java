package cu.uci.abcd.opac.ui.contribution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionAND;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionNOT;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionOR;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.auxiliary.Auxiliary;
import cu.uci.abcd.opac.ui.auxiliary.ResultComponent;
import cu.uci.abcd.opac.ui.controller.ConsultMaterialsController;
import cu.uci.abcd.opac.ui.listener.FilterMenuListener;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.widget.advanced.query.ColorType;
import cu.uci.abos.widget.advanced.query.QueryComponent;
import cu.uci.abos.widget.advanced.query.domain.QueryStructure;

public class MainContent extends ContributorPage {

	private ServiceProvider serviceProvider;
	private OpacFilterMenu opacFilterMenu;

	private List<Option> options = new ArrayList<Option>();

	private int filterOneSelection = 0;

	private LoginService login = null;
	boolean isLogin = false;

	private Composite result;
	private Composite AdvancesOptions;
	private Composite search;
	private Composite actionBar;
	private Composite selection;
	private Composite addToAndOrderBy;
	private Composite countResultCompo;
	private Composite notifications;
	private Composite show;
	private Composite paginado;

	private Button selectAllBtn;

	private Label countResultLabel;

	private Label notification;
	private Label othersNotifications;

	private Button searchBtn;
	private Button advanceSearchBtn;
	private Combo filterOne;

	private Combo order;
	private Combo addTo;

	private List<Library> allLibrary = new ArrayList<Library>();
	private List<Library> selectedLibraries = new ArrayList<Library>();
	private List<String> dataBaseName = new ArrayList<String>();

	private Combo field1;
	private Text txtfield1;
	private Combo andOr1;
	private Combo field2;
	private Text txtfield2;
	private Combo andOr2;
	private String[] values;
	private int filterGroup;

	// **Advance Search**\\
	private Label searchResultLbl;
	private Button findAdvanceBtn;
	private Button newAdvanceBtn;
	private Button cancelAdvanceBtn;
	private Option option;
	private OptionAND optionAND;
	private OptionOR optionOR;
	private OptionNOT optionNOT;
	private String term;
	private List<String> filter;
	private Text searchText;

	// LLenar
	String libraryIsisDatabasesHomeFolder;

	Integer after;
	Integer before;
	private boolean findByDate = false;

	List<RecordIsis> records = new ArrayList<RecordIsis>();
	ResultComponent component;
	Composite lastComponent;

	// //..Paginado..\\ \\

	private int numberResultsOfUserOnPage = 10;
	int countResutsOnPage = 0;
	int totalOfResults = 0;
	boolean rest = false;
	int cantRest = 0;
	Button numPag;
	Button currentPage;
	int cantButtonsPaged;
	private Button next;
	private Button previous;
	Button lastButton;
	int totalOfPage = 1;
	int resultNumeration;
	int currenrPageSelection = 1;

	String nameResult;
	String AutorResult;

	QueryComponent advanceOptionsPlus;
	List<QueryStructure> plusTemp;

	public List<RecordIsis> selectedRecord = new ArrayList<RecordIsis>();
	public List<RecordIsis> tempRecord = new ArrayList<RecordIsis>();
	private boolean select = false;

	// FIXME FALTAN COMENTARIOS DE INTERFACE

	// FIXME REPETICION DE CODIGO

	// ... ** Flags ** ... \\
	public boolean simpleSearch = false;
	boolean paginatorState;
	boolean actionBarState;
	boolean countResultState;
	boolean notificationState;
	boolean mainLogoState;

	public MainContent(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Text getSearchText() {
		return searchText;
	}

	public List<Option> getOptions() {
		return options;
	}

	public int getNumberResultsOfUserOnPage() {
		return numberResultsOfUserOnPage;
	}

	public void setNumberResultsOfUserOnPage(int numberResultsOfUserOnPage) {
		this.numberResultsOfUserOnPage = numberResultsOfUserOnPage;
	}

	@Override
	public Control createUIControl(final Composite parent) {

		final ContributorService pageService = serviceProvider.get(ContributorService.class);
		opacFilterMenu = serviceProvider.get(OpacFilterMenu.class);

		try {

			if (SecurityUtils.getService() != null)
				login = SecurityUtils.getService();

			isLogin = true;

		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		
		result = parent;

		Composite resize = new Composite(result, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);

		
		search = new Composite(result, SWT.NORMAL);
		search.setBackground(result.getBackground());
		search.setLayout(new FormLayout());
		search.setVisible(true);

		countResultCompo = new Composite(result, 0);

		actionBar = new Composite(result, SWT.NORMAL);
		actionBar.setBackground(result.getBackground());
		actionBar.setLayout(new FormLayout());
		actionBar.setVisible(false);

		AdvancesOptions = new Composite(result, SWT.V_SCROLL);
		AdvancesOptions.setBackground(result.getBackground());
		AdvancesOptions.setLayout(new FormLayout());

		FormDatas.attach(AdvancesOptions).atTop(0).atLeft(30).atRight(0);

		AdvancesOptions.setVisible(false);

		filterOne = new Combo(result, SWT.READ_ONLY);
		FormDatas.attach(filterOne).atTop(11).atLeft(30);

		Composite bar = new Composite(result, SWT.NONE);
		bar.setBackground(result.getBackground());
		bar.setLayout(new FormLayout());
		FormDatas.attach(bar).atLeft(250).atTop(130).withWidth(350).withHeight(200);

		FormDatas.attach(bar).atTop(10).atRight(50);

		Image searchImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("search"));

		searchBtn = new Button(bar, SWT.PUSH);
		searchBtn.setImage(searchImage);
		searchBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(searchBtn).atTop().atLeft();

		Image advanceSearchImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("search-plus"));

		advanceSearchBtn = new Button(bar, SWT.PUSH);
		advanceSearchBtn.setImage(advanceSearchImage);
		advanceSearchBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(advanceSearchBtn).atTop().atLeftTo(searchBtn);

		searchText = new Text(result, SWT.SEARCH | SWT.ICON_SEARCH);
		FormDatas.attach(searchText).withHeight(16).atTop(11).atLeftTo(filterOne, 20).atRightTo(bar, 20);
		searchText.addListener(SWT.Selection, new FilterMenuListener());

		// //.....Cants of Results.....\\\\

		countResultCompo.setLayout(new FormLayout());
		countResultCompo.setVisible(false);
		FormDatas.attach(countResultCompo).atTopTo(searchText, 5).atLeft(350);

		countResultLabel = new Label(countResultCompo, SWT.NORMAL);
		countResultLabel.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(countResultLabel).atTop().atLeft();

		// //.....Action Bar.....\\\\

		FormDatas.attach(actionBar).atLeft(20).atRight(0).atTopTo(searchText, 20).withHeight(35);

		selection = new Composite(actionBar, SWT.FLAT);
		selection.setLayout(new FormLayout());
		selection.setVisible(true);
		FormDatas.attach(selection).atTop(5).atLeft(5);

		selectAllBtn = new Button(selection, SWT.CHECK);
		FormDatas.attach(selectAllBtn).atTop(5).atLeft();

		addToAndOrderBy = new Composite(actionBar, SWT.NORMAL);
		addToAndOrderBy.setLayout(new FormLayout());
		addToAndOrderBy.setVisible(true);
		FormDatas.attach(addToAndOrderBy).atTop().atRight(5);

		order = new Combo(addToAndOrderBy, SWT.READ_ONLY);
		FormDatas.attach(order).atTop(3).atRight().withHeight(23).withWidth(150);

		addTo = new Combo(addToAndOrderBy, SWT.READ_ONLY);
		FormDatas.attach(addTo).atTop(3).atRightTo(order, 10).withHeight(23).withWidth(170);

		// ///.....Notifications of not Results .....\\\\

		notifications = new Composite(result, SWT.FLAT);
		notifications.setLayout(new FormLayout());
		notifications.setVisible(false);

		FormDatas.attach(notifications).atLeft(50).atTopTo(actionBar, 20);

		notification = new Label(notifications, SWT.NORMAL);
		notification.setFont(new Font(result.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(notification).atLeft(50).atTop();

		othersNotifications = new Label(notifications, SWT.NORMAL);
		othersNotifications.setFont(new Font(result.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(othersNotifications).atLeft(50).atTopTo(notification, 10);

		// ///.....Resultsssssssssss.....\\\\

		show = new Composite(result, SWT.V_SCROLL);
		show.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		show.setLayout(new FormLayout());
		FormDatas.attach(show).atLeft(0).atRight(0).atTopTo(actionBar).atBottom(25);

		paginado = new Composite(result, 0);
		paginado.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		paginado.setLayout(new FormLayout());
		paginado.setVisible(false);

		if (result.getShell().getDisplay().getBounds().width > 1090)
			FormDatas.attach(paginado).atTopTo(show).atLeft(125).atRight(125).withHeight(20).atBottom();
		else
			FormDatas.attach(paginado).atTopTo(show).atLeft(10).atRight(10).withHeight(20).atBottom();

		previous = new Button(paginado, SWT.NONE);
		previous.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_PREVIOUS));
		previous.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(previous).atTop().atLeft(5).withHeight(15);

		// /////..... Listeners .....\\\\\ \\

		searchBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				actionBar.setVisible(true);
				paginado.setVisible(false);
				currenrPageSelection = 1;

				term = searchText.getText();

				filter = getFilter(filterOne.getSelectionIndex());

				find();
				result.layout(true, true);
				result.redraw();
				result.update();
				refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		searchText.addKeyListener(new KeyListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void keyReleased(KeyEvent event) {

				if (event.keyCode == 13) {					
					

					actionBar.setVisible(true);
					paginado.setVisible(false);
					currenrPageSelection = 1;

					term = searchText.getText();

					filter = getFilter(filterOne.getSelectionIndex());

					find();
					result.layout(true, true);
					result.redraw();
					result.update();
					refresh();
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});

		advanceSearchBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				opacFilterMenu.getLeftLogo().setVisible(false);
				opacFilterMenu.getFilters().setVisible(true);
				opacFilterMenu.findLibrary();
				opacFilterMenu.cleanYears();

				Control[] temp = show.getChildren();
				for (int i = 0; i < temp.length; i++)
					temp[i].dispose();

				countResultState = countResultCompo.getVisible();
				paginatorState = paginado.getVisible();
				actionBarState = actionBar.getVisible();

				search.setVisible(false);
				countResultCompo.setVisible(false);
				actionBar.setVisible(false);
				paginado.setVisible(false);
				AdvancesOptions.setVisible(true);

				createAdvanceSearch();
				l10n();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		selectAllBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (select) {
					tempRecord.clear();
					select = false;
					selectAllBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SELECT_ALL));
				} else {
					select = true;
					selectAllBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_UNSELECT_ALL));
				}

				createPaged();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		addTo.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (tempRecord.size() > 0) {

					if (addTo.getSelectionIndex() == 1) {

						for (int i = 0; i < tempRecord.size(); i++)
							if (!selectedRecord.contains(tempRecord.get(i)))
								selectedRecord.add(tempRecord.get(i));

						Selection selectionPage = (Selection) ((OpacContributorServiceImpl) pageService).getContributorMap().get("SelectionID");
						OpacMenuBarProvider opacMenuBarProvider = serviceProvider.get(OpacMenuBarProvider.class);

						for (int i = 0; i < tempRecord.size(); i++)
							if (!selectionPage.records.contains(tempRecord.get(i)))
								selectionPage.records.add(tempRecord.get(i));

						opacMenuBarProvider.cantSelection = "" + selectionPage.records.size();
						opacMenuBarProvider.l10n();

						selectionPage.records = selectedRecord;

					} else if (addTo.getSelectionIndex() == 2 && !tempRecord.isEmpty() && login.isLoggedIn()) {

						selectedRecord.clear();

						for (int i = 0; i < tempRecord.size(); i++)
							if (!selectedRecord.contains(tempRecord.get(i)))
								selectedRecord.add(tempRecord.get(i));

						SaveInSelectionList saveInSelectionList = (SaveInSelectionList) ((OpacContributorServiceImpl) pageService).getContributorMap().get("SaveInSelectionList");
						saveInSelectionList.record = null;
						saveInSelectionList.records = selectedRecord;
						pageService.selectContributor("SaveInSelectionList");

					} else
						MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), "Información", "Debe estar autenticado para ver este cotenido", null);
				} else
					MainContent.this.showErrorMessage("Debe seleccionar al menos 1 elemento");

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		previous.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				currenrPageSelection--;
				tempRecord.clear();
				selectAllBtn.setSelection(false);
				select = false;
				selectAllBtn.setText("<a>Seleccionar Todo</a>");
				createPaged();
				l10n();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		order.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				Auxiliary auxiliary = new Auxiliary(controller);

				switch (order.getSelectionIndex()) {
				case 1:
					records = auxiliary.quickSortByTitle(records, 0, records.size() - 1);
					break;
				case 2:
					records = auxiliary.quickSortByAuthor(records, 0, records.size() - 1);
					break;
				case 3:
					records = auxiliary.quickSortByRelevance(records, 0, records.size() - 1);
					break;
				default:
					records = auxiliary.quickSortByPublicationDate(records, 0, records.size() - 1);
				}

				createPaged();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		l10n();
		searchText.setFocus();
		return parent;
	}

	public void find() {

		simpleSearch = true;
		allLibrary.clear();
		opacFilterMenu.getLeftLogo().setVisible(false);
		opacFilterMenu.getFilters().setVisible(true);
		records.clear();
		tempRecord.clear();
		select = false;
		totalOfResults = 0;
		cantRest = 0;
		rest = false;
		countResutsOnPage = numberResultsOfUserOnPage;
		filterGroup = 1;

		options.addAll(opacFilterMenu.getFilterOptions());
		allLibrary.addAll(opacFilterMenu.getSelectedLibraries());

		cleanNotDefHomeDataBase(allLibrary);

		if (filterOne.getSelectionIndex() == 0) {

			for (int i = 0; i < allLibrary.size(); i++) {

				dataBaseName.clear();

				try {

					dataBaseName = ((ConsultMaterialsController) controller).getAllDataSourcesByLibrary(allLibrary.get(i).getLibraryID());

					for (int j = 0; j < dataBaseName.size(); j++) {

						try {
							if (records == null)
								records = ((ConsultMaterialsController) controller).find(term, dataBaseName.get(j), allLibrary.get(i).getIsisDefHome(), allLibrary.get(i), options);
							else
								records.addAll(((ConsultMaterialsController) controller).find(term, dataBaseName.get(j), allLibrary.get(i).getIsisDefHome(), allLibrary.get(i), options));

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		} else {

			for (int i = 0; i < filter.size(); i++) {

				if (i == 0) {
					option = new Option(filter.get(i), term, filterGroup);
					options.add(option);
				} else {
					option = new OptionOR(filter.get(i), term, filterGroup);
					options.add(option);
				}
			}

			for (int i = 0; i < allLibrary.size(); i++) {

				dataBaseName.clear();

				try {

					dataBaseName = ((ConsultMaterialsController) controller).getAllDataSourcesByLibrary(allLibrary.get(i).getLibraryID());

					for (int j = 0; j < dataBaseName.size(); j++) {

						try {
							if (records == null)
								records = ((ConsultMaterialsController) controller).findByOptions(options, dataBaseName.get(j), allLibrary.get(i).getIsisDefHome(), allLibrary.get(i));
							else
								records.addAll(((ConsultMaterialsController) controller).findByOptions(options, dataBaseName.get(j), allLibrary.get(i).getIsisDefHome(), allLibrary.get(i)));

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}

		if (findByDate)
			records = ((ConsultMaterialsController) controller).filterByDate(after, before, records);

		totalOfResults = records.size();

		if (totalOfResults == 0) {
			diposeShow();
			notifications.setVisible(true);
			paginado.setVisible(false);
			hideActionBar();
			l10n();

		} else {
			Control[] hijosControls = result.getShell().getChildren();
			hijosControls[1].setVisible(true);

			notifications.setVisible(false);
			actionBar.setVisible(true);
			countResultCompo.setVisible(true);
			unHideActionBar();

			lastComponent = actionBar;

			if (countResutsOnPage > totalOfResults)
				countResutsOnPage = totalOfResults;
			else
				countResutsOnPage = numberResultsOfUserOnPage; // Datos de
																// usuario

			createPaged();
		}

		options.clear();
		refresh();
	}

	public void findAdvance() {

		if (goodConfig() == true) {

			simpleSearch = false;
			options.addAll(opacFilterMenu.getFilterOptions());
			selectedLibraries.addAll(opacFilterMenu.getSelectedLibraries());

			records = new ArrayList<RecordIsis>();
			Option option;
			boolean find = false;
			filterGroup = 1;

			if (!txtfield1.getText().equals("") && stringEmpty(txtfield1.getText())) {

				term = putQuotes(txtfield1.getText());
				
				filter = getAdvanceFilter(field1.getSelectionIndex());

				for (int i = 0; i < filter.size(); i++) {

					if (i == 0) {
						option = new Option(filter.get(i), term);
						option.setGroup(filterGroup);
						options.add(option);
					} else {
						option = new OptionOR(filter.get(i), term);
						option.setGroup(filterGroup);
						options.add(option);
					}
				}

				find = true;
			}

			if (!txtfield2.getText().equals("") && stringEmpty(txtfield2.getText())) {

				if (andOr1.getSelectionIndex() == 0)
					options.add(new OptionAND("", ""));
				else if (andOr1.getSelectionIndex() == 1)
					options.add(new OptionOR("", ""));
				else
					options.add(new OptionNOT("", ""));

				filterGroup++;

				term = putQuotes(txtfield2.getText());
				filter = getAdvanceFilter(field2.getSelectionIndex());

				option = new Option(filter.get(0), term);
				option.setGroup(filterGroup);
				options.add(option);

				for (int i = 1; i < filter.size(); i++)
					addOption(1, filter.get(i), term);

				find = true;
			}

			plusTemp = new ArrayList<QueryStructure>();

			try {
				plusTemp = advanceOptionsPlus.getChildrens();

			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int i = 0; i < plusTemp.size(); i++)
				try {
					if (!plusTemp.get(i).getTerm().getText().equals("") && stringEmpty(plusTemp.get(i).getTerm().getText())) {

						if (i == 0) {
							if (andOr2.getSelectionIndex() == 0)
								options.add(new OptionAND("", ""));
							else if (andOr2.getSelectionIndex() == 1)
								options.add(new OptionOR("", ""));
							else
								options.add(new OptionNOT("", ""));
						} else {
							if (plusTemp.get(i - 1).getAndOr().getSelectionIndex() == 0)
								options.add(new OptionAND("", ""));
							else if (plusTemp.get(i - 1).getAndOr().getSelectionIndex() == 1)
								options.add(new OptionOR("", ""));
							else
								options.add(new OptionNOT("", ""));
						}
   
						filterGroup++;

						term = putQuotes(plusTemp.get(i).getTerm().getText());
						filter = getAdvanceFilter(plusTemp.get(i).getClave().getSelectionIndex());

						option = new Option(filter.get(0), term);
						option.setGroup(filterGroup);
						options.add(option);

						for (int j = 1; j < filter.size(); j++)
							addOption(1, filter.get(j), term);

						find = true;
					}

				} catch (Exception e) {

				}

			if (find)
				for (int i = 0; i < selectedLibraries.size(); i++) {

					dataBaseName.clear();

					try {

						dataBaseName = ((ConsultMaterialsController) controller).getAllDataSourcesByLibrary(selectedLibraries.get(i).getLibraryID());

						for (int j = 0; j < dataBaseName.size(); j++) {

							try {
								if (records == null)
									records = ((ConsultMaterialsController) controller).findByOptions(options, dataBaseName.get(j), selectedLibraries.get(i).getIsisDefHome(), selectedLibraries.get(i));
								else
									records.addAll(((ConsultMaterialsController) controller).findByOptions(options, dataBaseName.get(j), selectedLibraries.get(i).getIsisDefHome(), selectedLibraries.get(i)));

							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			if (findByDate)
				records = ((ConsultMaterialsController) controller).filterByDate(after, before, records);

			totalOfResults = records.size();
			search.setVisible(true);
			AdvancesOptions.setVisible(false);
			tempRecord.clear();
			select = false;
			cantRest = 0;
			rest = false;
			countResutsOnPage = numberResultsOfUserOnPage;

			if (totalOfResults == 0) {
				diposeShow();
				notifications.setVisible(true);
				paginado.setVisible(false);
				hideActionBar();
				l10n();

			} else {
				Control[] hijosControls = result.getShell().getChildren();
				hijosControls[1].setVisible(true);

				notifications.setVisible(false);
				actionBar.setVisible(true);
				unHideActionBar();
				l10n();

				lastComponent = actionBar;

				if (countResutsOnPage > totalOfResults)
					countResutsOnPage = totalOfResults;
				else
					countResutsOnPage = numberResultsOfUserOnPage; // Datos de
																	// usuario
				try {
					createPaged();
				} catch (Exception e) {

				}
			}

			options.clear();
			selectedLibraries.clear();
			l10n();
		} else
			showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_BAD_CONFIGURATION));
	}

	public void createPaged() {

		paginado.setVisible(true);

		if (!select) {
			selectAllBtn.setSelection(false);
			selectAllBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SELECT_ALL));
		}

		diposeShow();

		int temp = currenrPageSelection;

		try {

			temp = (Integer.parseInt(currentPage.getText()));

		} catch (Exception e) {
			currentPage = new Button(paginado, SWT.NORMAL);
			currentPage.setText("1");
		}

		try {
			isLogin = login.isLoggedIn();
		} catch (Exception e) {
			e.printStackTrace();
		}

		resultNumeration = (temp - 1) * numberResultsOfUserOnPage;

		cantRest = totalOfResults % numberResultsOfUserOnPage;
		totalOfPage = totalOfResults / numberResultsOfUserOnPage;

		if (cantRest != 0) {
			rest = true;
			totalOfPage++;
		}

		if (rest && temp == totalOfPage) {

			ResultComponent a = null;
			Composite anterior = actionBar;

			for (int i = 1; i <= cantRest; i++) {

				a = new ResultComponent(controller, records.get(resultNumeration + i - 1).getLibrary().getLibraryID(), show, SWT.NORMAL, resultNumeration + i, records.get(resultNumeration + i - 1), select, serviceProvider, isLogin);

				FormDatas.attach(a).atLeft(20).atRight(0).atTopTo(anterior).withHeight(120);

				refresh();

				anterior = a;

				if (select)
					if (!tempRecord.contains(records.get(resultNumeration + i - 1)))
						tempRecord.add(records.get(resultNumeration + i - 1));
			}
		} else {

			ResultComponent a = null;
			Composite anterior = actionBar;

			for (int i = 1; i <= countResutsOnPage; i++) {

				a = new ResultComponent(controller, records.get(resultNumeration + i - 1).getLibrary().getLibraryID(), show, SWT.NORMAL, resultNumeration + i, records.get(resultNumeration + i - 1), select, serviceProvider, isLogin);

				FormDatas.attach(a).atLeft(20).atRight(0).atTopTo(anterior).withHeight(120);

				refresh();

				anterior = a;

				if (select)
					if (!tempRecord.contains(records.get(resultNumeration + i - 1)))
						tempRecord.add(records.get(resultNumeration + i - 1));
			}
		}

		show.notifyListeners(SWT.Resize, new Event());
		result.notifyListeners(SWT.Resize, new Event());

		FormDatas.attach(show).atLeft(0).atRight(0).atTopTo(actionBar).atBottom(25).withHeight(show.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

		if (result.getShell().getDisplay().getBounds().width > 1090)
			FormDatas.attach(paginado).atTopTo(show).atLeft(125).atRight(125).withHeight(20).atBottom();
		else
			FormDatas.attach(paginado).atTopTo(show).atLeft(10).atRight(10).withHeight(20).atBottom();

		refresh();
		diposePagenated();

		lastButton = previous;
		int tempEnable = 1;

		if (temp > 6)
			tempEnable = temp - 4;

		if (totalOfPage != 1) {

			for (int i = tempEnable; i <= totalOfPage; i++) {

				numPag = new Button(paginado, SWT.NONE);
				numPag.setText(i + "");
				numPag.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.BOLD));
				FormDatas.attach(numPag).atTop().atLeftTo(lastButton, 5).withHeight(15);

				numPag.addSelectionListener(new SelectionListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void widgetSelected(SelectionEvent even) {

						currentPage = ((Button) even.widget);
						currenrPageSelection = Integer.parseInt(currentPage.getText());
						tempRecord.clear();
						selectAllBtn.setSelection(false);
						select = false;
						createPaged();
						l10n();

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});

				if (Integer.parseInt(numPag.getText()) == temp)
					numPag.setEnabled(false);

				lastButton = numPag;
				if (i == (tempEnable + 7))
					break;
			}

			next = new Button(paginado, SWT.NONE);
			next.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEXT));
			next.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.NONE));
			FormDatas.attach(next).atTop().atLeftTo(lastButton, 5).withHeight(15);

			next.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {

					currenrPageSelection++;
					tempRecord.clear();
					selectAllBtn.setSelection(false);
					select = false;
					selectAllBtn.setText("<a>Seleccionar Todo</a>");
					createPaged();
					l10n();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

			if (temp == totalOfPage)
				next.setVisible(false);
		}

		if (temp == 1)
			previous.setVisible(false);
		else
			previous.setVisible(true);

		l10n();
		refresh();
	}

	public void diposeShow() {
		try {
			Control[] tempShow = show.getChildren();
			for (int i = 0; i < tempShow.length; i++)
				tempShow[i].dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author Alberto Alejandro Arias Benitez
	 * @version 1.0
	 * 
	 */

	public void createAdvanceSearch() {

		try {
			Control[] temp = AdvancesOptions.getChildren();
			for (int i = 1; i < temp.length; i++)
				temp[i].dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}

		searchResultLbl = new Label(AdvancesOptions, 0);
		searchResultLbl.setFont(new Font(result.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(searchResultLbl).atTop(10).atLeft(30);

		Link horSeparator = new Link(AdvancesOptions, SWT.NORMAL);
		horSeparator
				.setText("________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
		horSeparator.setFont(new Font(result.getDisplay(), "Arial", 5, SWT.NONE));
		FormDatas.attach(horSeparator).atTopTo(searchResultLbl, -10).atLeft(28);

		field1 = new Combo(AdvancesOptions, SWT.READ_ONLY);
		FormDatas.attach(field1).atTopTo(horSeparator, 10).atLeftTo(AdvancesOptions).withWidth(230).withHeight(23);

		txtfield1 = new Text(AdvancesOptions, SWT.NORMAL);
		FormDatas.attach(txtfield1).atTopTo(horSeparator, 10).atLeftTo(field1, 10).withWidth(230).withHeight(10);

		andOr1 = new Combo(AdvancesOptions, SWT.READ_ONLY);
		FormDatas.attach(andOr1).atTopTo(horSeparator, 10).atLeftTo(txtfield1, 10).withHeight(23);

		field2 = new Combo(AdvancesOptions, SWT.READ_ONLY);
		FormDatas.attach(field2).atTopTo(field1, 10).atLeftTo(AdvancesOptions).withWidth(230).withHeight(23);

		txtfield2 = new Text(AdvancesOptions, SWT.NORMAL);
		FormDatas.attach(txtfield2).atTopTo(field1, 10).atLeftTo(field2, 10).withWidth(230).withHeight(10);

		andOr2 = new Combo(AdvancesOptions, SWT.READ_ONLY);
		FormDatas.attach(andOr2).atTopTo(field1, 10).atLeftTo(txtfield2, 10).withHeight(23);

		values = new String[] { MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_TITLE), MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_AUTHOR), MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_MATERIA),
				MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_ISBN), MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_ISSN), MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_SIG_TOP), MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_INST_NAME),
				MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_EDITORIAL), MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_EDITION), MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_PUB_PLACE),
				MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_NUM_VOL), MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_SERIE_TITLE) };

		field1.setItems(values);
		field2.setItems(values);

		advanceOptionsPlus = new QueryComponent(AdvancesOptions, SWT.NORMAL, values, ColorType.White);
		FormDatas.attach(advanceOptionsPlus).atTopTo(field2, 10).atLeft();

		advanceOptionsPlus.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {

				show.notifyListeners(SWT.Resize, new Event());
				result.notifyListeners(SWT.Resize, new Event());

				refresh();

			}
		});

		Image findAdvanceImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("search"));

		findAdvanceBtn = new Button(AdvancesOptions, SWT.PUSH);
		findAdvanceBtn.setImage(findAdvanceImage);
		findAdvanceBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(findAdvanceBtn).atTopTo(advanceOptionsPlus, 20).atLeft(30);

		Image newAdvanceBtnImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("trash"));

		newAdvanceBtn = new Button(AdvancesOptions, SWT.PUSH);
		newAdvanceBtn.setImage(newAdvanceBtnImage);
		newAdvanceBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(newAdvanceBtn).atTopTo(advanceOptionsPlus, 20).atLeftTo(findAdvanceBtn, 10);

		Image cancelAdvanceImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("redcross"));

		cancelAdvanceBtn = new Button(AdvancesOptions, SWT.PUSH);
		cancelAdvanceBtn.setImage(cancelAdvanceImage);
		cancelAdvanceBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(cancelAdvanceBtn).atTopTo(advanceOptionsPlus, 20).atLeftTo(newAdvanceBtn, 10);

		findAdvanceBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				searchText.setText("");
				findAdvance();
				refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		newAdvanceBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				field1.select(0);
				field2.select(0);
				txtfield1.setText("");
				txtfield2.setText("");
				andOr1.select(0);
				andOr2.select(0);

				Control[] component = ((Composite) (advanceOptionsPlus.getChildren())[0]).getChildren();
				for (int i = 0; i < component.length - 1; i++)
					component[i].dispose();

				Control[] newCompo = ((Composite) (advanceOptionsPlus.getChildren())[0]).getChildren();
				Control[] otherCompo = ((Composite) (newCompo[0])).getChildren();
				((Combo) otherCompo[0]).select(0);
				((Text) otherCompo[1]).setText("");

				opacFilterMenu.cleanMaterialType();
				opacFilterMenu.findLibrary();
				opacFilterMenu.cleanYears();

				refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		cancelAdvanceBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {

					search.setVisible(true);
					countResultCompo.setVisible(countResultState);
					paginado.setVisible(paginatorState);
					actionBar.setVisible(actionBarState);
					AdvancesOptions.setVisible(false);

					if (records != null && records.size() != 0)
						createPaged();
					else {
						opacFilterMenu.getFilters().setVisible(false);
						opacFilterMenu.getLeftLogo().setVisible(true);
					}

					Control[] otros = ((Composite) (advanceOptionsPlus.getChildren())[0]).getChildren();
					for (int i = 0; i < otros.length - 1; i++)
						otros[i].dispose();

					Control[] newCompo = ((Composite) (advanceOptionsPlus.getChildren())[0]).getChildren();
					Control[] otherCompo = ((Composite) (newCompo[0])).getChildren();
					((Text) otherCompo[1]).setText("");

					l10n();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	public void hideActionBar() {
		try {
			Control[] tempActionBar = actionBar.getChildren();
			tempActionBar[0].setVisible(false);
			tempActionBar[1].setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unHideActionBar() {
		try {
			Control[] tempActionBar = actionBar.getChildren();
			tempActionBar[0].setVisible(true);
			tempActionBar[1].setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void diposePagenated() {
		try {
			Control[] tempPaged = paginado.getChildren();
			for (int i = 1; i < tempPaged.length; i++)
				tempPaged[i].dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getFilter(int selection) {

		// ** Standard marc21**\\
		/*
		 * 1 -- Title 245^a (Revista 222^a) 2 -- Author 100^a 110^a 111^a 3 --
		 * Materia 600 610 611 630 650 651 653 655 4 -- Resume 520^a
		 */

		List<String> temp = new ArrayList<String>();

		switch (selection) {
		case 0:
			temp.add("Cualquier Campo");
			return temp;
		case 1:
			temp.add("245");
			temp.add("222");
			return temp;
		case 2:
			temp.add("100");
			temp.add("110");
			temp.add("111");
			return temp;
		case 3:
			temp.add("600");
			temp.add("610");
			temp.add("611");
			temp.add("630");
			temp.add("650");
			temp.add("651");
			temp.add("653");
			temp.add("655");
			return temp;
		default:
			temp.add("520");
			return temp;
		}

	}

	public List<String> getAdvanceFilter(int selection) {

		// ** Standard marc21**\\
		/*
		 * 0 -- Title 245^a (Revista 222^a) 1 -- Author 100^a 110^a 111^a 2 --
		 * Materia 600 610 611 630 650 651 653 655 3 -- ISBN 20^a 4 -- ISSN 22^a
		 * 5 -- Sígnatura topográfica 82^a 6 -- Nombre de la institución 110^a 7
		 * -- Editorial 260^b 8 -- Edición 250^a 9 -- Lugar de publicación 260^a
		 * 10 -- Título de la Serie 440^a
		 */

		List<String> temp = new ArrayList<String>();

		switch (selection) {
		case 0:
			temp.add("245");
			temp.add("222");
			return temp;
		case 1:
			temp.add("100");
			temp.add("110");
			temp.add("111");
			return temp;
		case 2:
			temp.add("600");
			temp.add("610");
			temp.add("611");
			temp.add("630");
			temp.add("650");
			temp.add("651");
			temp.add("653");
			temp.add("655");
			return temp;
		case 3:
			temp.add("20");
			return temp;
		case 4:
			temp.add("22");
			return temp;
		case 5:
			temp.add("82");
			return temp;
		case 6:
			temp.add("110");
			return temp;
		case 7:
			temp.add("260");
			return temp;
		case 8:
			temp.add("250");
			return temp;
		case 9:
			temp.add("260");
			return temp;
		default:
			temp.add("440");
			return temp;
		}
	}

	public void addOption(int selection, String filter, String term) {

		if (selection == 0) {
			optionAND = new OptionAND(filter, term);
			optionAND.setGroup(filterGroup);
			options.add(optionAND);
		} else if (selection == 1) {
			optionOR = new OptionOR(filter, term);
			optionOR.setGroup(filterGroup);
			options.add(optionOR);
		} else {
			optionNOT = new OptionNOT(filter, term);
			optionNOT.setGroup(filterGroup);
			options.add(optionNOT);
		}
	}

	public boolean stringEmpty(String chain) {

		for (int i = 1; i < chain.length(); i++)
			if (!chain.substring(i - 1, i).equals(" "))
				return true;

		if (chain.length() == 1 && chain != " ")
			return true;

		return false;
	}

	public boolean goodConfig() {

		if ((txtfield1.getText() == null || txtfield1.getText().equals("")) || !stringEmpty(txtfield1.getText()))
			return false;

		plusTemp = new ArrayList<QueryStructure>();

		try {

			plusTemp = advanceOptionsPlus.getChildrens();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			if (!plusTemp.get(0).getTerm().getText().equals("") && stringEmpty(plusTemp.get(0).getTerm().getText()))
				if ((txtfield2.getText() == null || txtfield2.getText().equals("")) || !stringEmpty(txtfield2.getText()))
					return false;
		} catch (Exception e) {

		}
		for (int i = 0; i < plusTemp.size(); i++) {
			try {
				if (!(!plusTemp.get(i).getTerm().getText().equals("") && stringEmpty(plusTemp.get(i).getTerm().getText()))) {
					if (i + 1 != plusTemp.size())
						return false;
				}
			} catch (Exception e) {

			}
		}

		return true;
	}

	@Override
	public String getID() {
		return "MainContentID";
	}

	@Override
	public void l10n() {		
		
		advanceSearchBtn.setText(AbosMessages.get().BUTTON_ADVANCE_SEARCH);


		if (filterOne.getSelectionIndex() != -1)
			filterOneSelection = filterOne.getSelectionIndex();

		filterOne.setItems(new String[] { (MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_ANY_FIELD)), (MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_TITLE)), (MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_AUTHOR)),
				(MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_MATERIA)), (MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_RESUME)) });
		filterOne.select(filterOneSelection);

		if (AdvancesOptions.getVisible()) {

			searchResultLbl.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_ADVANCE_HEADER));

			values = new String[] { MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_TITLE), MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_AUTHOR), MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_MATERIA),
					MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_ISBN), MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_ISSN), MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_SIG_TOP), MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_INST_NAME),
					MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_EDITORIAL), MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_EDITION), MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_PUB_PLACE),
					MessageUtil.unescape(AbosMessages.get().COMBO_ADVANCE_FILTER_SERIE_TITLE) };

			field1.select(0);
			field2.select(0);
			andOr1.setItems(new String[] { MessageUtil.unescape(AbosMessages.get().COMBO_CONCATENATER_AND), MessageUtil.unescape(AbosMessages.get().COMBO_CONCATENATER_OR), MessageUtil.unescape(AbosMessages.get().COMBO_CONCATENATER_NOT) });
			andOr1.select(0);
			andOr2.setItems(new String[] { MessageUtil.unescape(AbosMessages.get().COMBO_CONCATENATER_AND), MessageUtil.unescape(AbosMessages.get().COMBO_CONCATENATER_OR), MessageUtil.unescape(AbosMessages.get().COMBO_CONCATENATER_NOT) });
			andOr2.select(0);

			findAdvanceBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ADVANCE_BUTTON_SEARCH));
			newAdvanceBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ADVANCE_NEW_SEARCH));
			cancelAdvanceBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ADVANCE_SEARCH_CANCEL));

		}

		if (notifications.getVisible()) {
			notification.setText("¡" + MessageUtil.unescape(AbosMessages.get().NOTIFICATION_NOT_RESULTS) + "!");
			othersNotifications.setText(MessageUtil.unescape(AbosMessages.get().NOTIFICATION_OTHER_NOT_RESULTS));
		}

		if (selectAllBtn.getSelection())
			selectAllBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_UNSELECT_ALL));
		else
			selectAllBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SELECT_ALL));

		countResultLabel.setText(totalOfResults + " " + MessageUtil.unescape(AbosMessages.get().COUNT_RESULT_LABEL));

		addTo.setItems(new String[] { MessageUtil.unescape(AbosMessages.get().COMBO_ADD_TO), MessageUtil.unescape(AbosMessages.get().COMBO_ADD_TO_SELECTION), MessageUtil.unescape(AbosMessages.get().COMBO_ADD_TO_SELECTION_LIST) });
		addTo.select(0);

		order.setItems(new String[] { MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_BY), MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_BY_TITLE), MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_BY_AUTHOR), MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_BY_RELEVANT),
				MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_BY_PUBLICATION_DATE) });
		order.select(0);

		show.computeSize(show.getBounds().width, SWT.DEFAULT);
		show.pack();
		show.layout(true, true);
		show.update();
		show.redraw();

		result.computeSize(show.getBounds().width, SWT.DEFAULT);
		result.pack();
		result.layout(true, true);
		result.update();
		result.redraw();

		refresh();
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_MAIN_CONTENT);
	}

	public ViewController getController() {
		return controller;
	}

	// ** .. Properties .. ** \\

	public void setFindByDate(boolean findByDate) {
		this.findByDate = findByDate;
	}
	public boolean getFindByDate() {
		return findByDate;
	}

	public boolean getAdvancesOptionsVisibility() {
		return AdvancesOptions.getVisible();
	}

	private void cleanNotDefHomeDataBase(List<Library> librarys) {
		for (int i = 0; i < librarys.size(); i++)
			if (librarys.get(i).getIsisDefHome() == null || librarys.get(i).getIsisDefHome() == "")
				librarys.get(i--);

	}
	   
	private String putQuotes(String text){
		   
		if(text.split(" ").length > 1)
			if(!(text.substring(0, 1).equals("\"")))
					text = "\"" + text + "\"";			
		
		return text;
	}
}
