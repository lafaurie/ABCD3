/*
 * @(#)ShellConfigurator.java 1.0.0 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abcd.opac.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import cu.uci.abcd.opac.ui.contribution.Acquisition;
import cu.uci.abcd.opac.ui.contribution.Circulation;
import cu.uci.abcd.opac.ui.contribution.ConfigurationDataSources;
import cu.uci.abcd.opac.ui.contribution.ConfigurationOption;
import cu.uci.abcd.opac.ui.contribution.MainContent;
import cu.uci.abcd.opac.ui.contribution.MyCurrentState;
import cu.uci.abcd.opac.ui.contribution.MyHistoryState;
import cu.uci.abcd.opac.ui.contribution.MyRecommendation;
import cu.uci.abcd.opac.ui.contribution.MySelectionListContent;
import cu.uci.abcd.opac.ui.contribution.MySelectionLists;
import cu.uci.abcd.opac.ui.contribution.MyTags;
import cu.uci.abcd.opac.ui.contribution.RecentAcquisitions;
import cu.uci.abcd.opac.ui.contribution.Register;
import cu.uci.abcd.opac.ui.contribution.RegisterScore;
import cu.uci.abcd.opac.ui.contribution.SaveInSelectionList;
import cu.uci.abcd.opac.ui.contribution.Selection;
import cu.uci.abcd.opac.ui.contribution.SendEmail;
import cu.uci.abcd.opac.ui.contribution.SendRecomendation;
import cu.uci.abcd.opac.ui.contribution.SuggestionList;
import cu.uci.abcd.opac.ui.contribution.ViewLog;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abcd.opac.ui.controller.ConsultMaterialsController;
import cu.uci.abcd.opac.ui.controller.ProxyController;
import cu.uci.abcd.opac.ui.controller.RegisterUserViewController;
import cu.uci.abcd.opac.ui.controller.ReservationViewController;
import cu.uci.abcd.opac.ui.controller.SelectionListViewController;
import cu.uci.abcd.opac.ui.controller.SuggestionViewController;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.LayoutProvider;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.security.LoginChangedListener;

public class OpacShellConfigurator {
	private final ServiceProvider serviceProvider;
	private final Map<String, ViewController> controllersMap = new HashMap<>();
	private OpacContributorServiceImpl pageService;

	public OpacShellConfigurator(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Shell configure(PlatformContributor[] pageStructureProviders, LayoutProvider layoutProvider) {
		OpacLayoutContextImpl layoutContext = new OpacLayoutContextImpl();
		pageService = new OpacContributorServiceImpl(layoutProvider, layoutContext);
		serviceProvider.register(ContributorService.class, pageService);
		// initialize controllers
		initializeControllers();
		// initialize contributions page
		trackContributions(pageService);

		OpacShellProvider shellProvider = new OpacShellProvider(pageStructureProviders, layoutProvider, layoutContext);
		return shellProvider.createShell();
	}

	private void trackContributions(final OpacContributorServiceImpl pageService) {

		final Circulation circulationContributor = new Circulation(serviceProvider);
		circulationContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		RecentAcquisitions recentAcquisitionsContributor = new RecentAcquisitions();
		recentAcquisitionsContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		final Acquisition adquisicionContributor = new Acquisition(serviceProvider);
		adquisicionContributor.setViewController(controllersMap.get("suggestionController"));

		final SuggestionList sugerenciasListContributor = new SuggestionList();
		sugerenciasListContributor.setViewController(controllersMap.get("suggestionController"));

		MainContent mainContentContributor = new MainContent(serviceProvider);
		mainContentContributor.setViewController(controllersMap.get("consultMaterialsController"));

		final MySelectionLists mySelectionListsContributor = new MySelectionLists(serviceProvider);
		mySelectionListsContributor.setViewController(controllersMap.get("selectionListViewControler"));

		Register registerContributor = new Register();
		registerContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		final MyCurrentState myCurrentStateContributor = new MyCurrentState();
		myCurrentStateContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		final MyHistoryState myHistoricalStateContributor = new MyHistoryState();
		myHistoricalStateContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		Selection seleccionContributor = new Selection(serviceProvider);
		seleccionContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		final SaveInSelectionList saveInSelectionListContributor = new SaveInSelectionList();
		saveInSelectionListContributor.setViewController(controllersMap.get("selectionListViewControler"));

		final MySelectionListContent selectionListContentContributor = new MySelectionListContent(serviceProvider);
		selectionListContentContributor.setViewController(controllersMap.get("selectionListViewControler"));

		final MyTags myTagsContributor = new MyTags(serviceProvider);
		myTagsContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		RegisterScore registerScoreContributor = new RegisterScore(serviceProvider);
		registerScoreContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		final ViewLog viewLogContributor = new ViewLog(serviceProvider);
		viewLogContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		SendEmail sendEmailContributor = new SendEmail();
		sendEmailContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		final SendRecomendation sendRecomendationContributor = new SendRecomendation();
		sendRecomendationContributor.setViewController(controllersMap.get("allManagementOpacViewController"));

		final MyRecommendation myRecommendationContributor = new MyRecommendation();
		myRecommendationContributor.setViewController(controllersMap.get("allManagementOpacViewController"));
    
		final ConfigurationOption configurationOptionContributor = new ConfigurationOption(serviceProvider);
		configurationOptionContributor.setViewController(controllersMap.get("registerUserViewController"));
		
		final ConfigurationDataSources configurationDataSourcesContributor = new ConfigurationDataSources();
		configurationDataSourcesContributor.setViewController(controllersMap.get("allManagementOpacViewController"));
				

		pageService.setDefaultContributor(mainContentContributor);
		pageService.addUContributor(adquisicionContributor);
		pageService.addUContributor(mySelectionListsContributor);
		pageService.addUContributor(circulationContributor);
		pageService.addUContributor(myCurrentStateContributor);
		pageService.addUContributor(myHistoricalStateContributor);
		pageService.addUContributor(configurationOptionContributor);
		pageService.addUContributor(myTagsContributor);
		pageService.addUContributor(seleccionContributor);
		pageService.addUContributor(registerContributor);
		pageService.addUContributor(sugerenciasListContributor);
		pageService.addUContributor(saveInSelectionListContributor);
		pageService.addUContributor(selectionListContentContributor);
		pageService.addUContributor(recentAcquisitionsContributor);
		pageService.addUContributor(registerScoreContributor);
		pageService.addUContributor(viewLogContributor);
		pageService.addUContributor(sendEmailContributor);
		pageService.addUContributor(sendRecomendationContributor);
		pageService.addUContributor(myRecommendationContributor);
		pageService.addUContributor(configurationDataSourcesContributor);
		      
		SecurityUtils.getService().onLoginChanged(new LoginChangedListener() {
        
			@Override
			public void handle() {
				mySelectionListsContributor.notifyListeners(SWT.Dispose, new Event());
				configurationDataSourcesContributor.notifyListeners(SWT.Dispose, new Event());
				sugerenciasListContributor.notifyListeners(SWT.Dispose, new Event());
				adquisicionContributor.notifyListeners(SWT.Dispose, new Event());
				myCurrentStateContributor.notifyListeners(SWT.Dispose, new Event());
				myHistoricalStateContributor.notifyListeners(SWT.Dispose, new Event());
				myTagsContributor.notifyListeners(SWT.Dispose, new Event());
				sendRecomendationContributor.notifyListeners(SWT.Dispose, new Event());
				myRecommendationContributor.notifyListeners(SWT.Dispose, new Event());
				viewLogContributor.notifyListeners(SWT.Dispose, new Event());
				circulationContributor.notifyListeners(SWT.Dispose, new Event());
				selectionListContentContributor.notifyListeners(SWT.Dispose, new Event());
				saveInSelectionListContributor.notifyListeners(SWT.Dispose, new Event());
				configurationOptionContributor.notifyListeners(SWT.Dispose, new Event());
			}
		});

	}

	/**
	 * initialize
	 */
	private void initializeControllers() {

		ProxyController proxyController = new ProxyController();

		AllManagementOpacViewController allManagementOpacViewController = new AllManagementOpacViewController();
		allManagementOpacViewController.setProxyController(proxyController);
		controllersMap.put("allManagementOpacViewController", allManagementOpacViewController);

		ReservationViewController reservationViewController = new ReservationViewController();
		reservationViewController.setProxyController(proxyController);
		controllersMap.put("reservationController", reservationViewController);

		SuggestionViewController suggestionViewController = new SuggestionViewController();
		suggestionViewController.setProxyController(proxyController);
		controllersMap.put("suggestionController", suggestionViewController);

		ConsultMaterialsController consultMaterialsController = new ConsultMaterialsController();
		consultMaterialsController.setProxyController(proxyController);
		controllersMap.put("consultMaterialsController", consultMaterialsController);

		SelectionListViewController selectionListViewController = new SelectionListViewController();
		selectionListViewController.setProxyController(proxyController);
		controllersMap.put("selectionListViewControler", selectionListViewController);

		RegisterUserViewController registerUserViewController = new RegisterUserViewController();
		registerUserViewController.setProxyController(proxyController);
		controllersMap.put("registerUserViewController", registerUserViewController);

		/*
		 * TopicStatisticController topicStatisticController = new
		 * TopicStatisticController();
		 * topicStatisticController.setProxyController(proxyController);
		 * controllersMap.put("topicStatisticController",
		 * topicStatisticController);
		 */
	}

	public void selectDefaultContributor() {
		pageService.selectDefaultContributor();
	}
}
