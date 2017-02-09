/*
 * @(#)ShellConfigurator.java 1.0.0 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abcd.opac.ui;

import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.ServiceReference;

import cu.uci.abos.core.ui.ContributorTracker;
import cu.uci.abos.ui.api.ConfiguratorTracker;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IContributorFactory;
import cu.uci.abos.ui.api.IContributorService;
import cu.uci.abos.ui.api.ILayoutProvider;
import cu.uci.abos.ui.api.IPlatformContributor;
import cu.uci.abos.util.api.IServiceProvider;

public class OpacShellConfigurator {
	private static final String UI_CONTRIBUTOR_TYPE_PAGE = "opac";

	private final IServiceProvider serviceProvider;

	public OpacShellConfigurator(IServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Shell configure(IPlatformContributor[] pageStructureProviders,
			ILayoutProvider layoutProvider) {
		OpacLayoutContextImpl layoutContext = new OpacLayoutContextImpl();
		OpacContributorServiceImpl pageService = new OpacContributorServiceImpl(layoutProvider,
				layoutContext);
		serviceProvider.register(IContributorService.class, pageService);
		trackContributions(pageService);

		OpacShellProvider shellProvider = new OpacShellProvider(pageStructureProviders,
				layoutProvider, layoutContext);
		return shellProvider.createShell();
	}

	private void trackContributions(final OpacContributorServiceImpl pageService) {
		new ContributorTracker() {

//			private boolean initialized;

			@Override
			public void addingService(
					ServiceReference<IContributorFactory> reference,
					IContributor contributor) {
				if (ConfiguratorTracker.matchesType(
						UI_CONTRIBUTOR_TYPE_PAGE, reference)) {
					pageService.addUIContributor(contributor);
					if (contributor.getID()=="MainContentID") {
						pageService.selectDefaultContributor();
					}
					/*if (!initialized) {
						if (contributor.getID()=="MainContentID") {
							pageService.selectDefaultContributor();
						}
						initialized = true;
					}*/
				}
			}

			@Override
			public void removedService(
					ServiceReference<IContributorFactory> reference,
					IContributor service) {
				if (ConfiguratorTracker.matchesType(UI_CONTRIBUTOR_TYPE_PAGE,
						reference)) {
					pageService.removeUIContributor(service);
				}
			}
		};
	}
}
