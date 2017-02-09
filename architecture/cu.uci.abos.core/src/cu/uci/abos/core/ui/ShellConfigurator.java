package cu.uci.abos.core.ui;

import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.ServiceReference;

import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorFactory;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.LayoutProvider;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.security.SecurityAspect;
import cu.uci.abos.core.ui.internal.ContributorServiceImpl;
import cu.uci.abos.core.ui.internal.LayoutContextImpl;

public class ShellConfigurator {
	private static final String UI_CONTRIBUTOR_TYPE_PAGE = "abcd";

	private final ServiceProvider serviceProvider;
	private boolean securityEnabled;
	SecurityAspect security;

	public ShellConfigurator(ServiceProvider serviceProvider, boolean securityEnabledParam) {
		this.serviceProvider = serviceProvider;
		this.security = new SecurityAspect();

		this.securityEnabled = securityEnabledParam;
	}

	public Shell configure(PlatformContributor[] pageStructureProviders, LayoutProvider layoutProvider) {
		LayoutContextImpl layoutContext = new LayoutContextImpl();
		ContributorServiceImpl pageService = new ContributorServiceImpl(layoutProvider, layoutContext);
		serviceProvider.register(ContributorService.class, pageService);
		trackContributions(pageService);

		ShellProvider shellProvider = new ShellProvider(pageStructureProviders, layoutProvider, layoutContext);
		return shellProvider.createShell();
	}

	private void trackContributions(final ContributorServiceImpl pageService) {
		new ContributorTracker() {

			// private boolean initialized;

			@Override
			public void addingService(ServiceReference<ContributorFactory> reference, Contributor contributor) {
				if (ConfiguratorTracker.matchesType(UI_CONTRIBUTOR_TYPE_PAGE, reference)) {
					if (securityEnabled) {
						security.setService(serviceProvider.get(LoginService.class));
						if (security.verifyPermission(contributor)) {
							pageService.addUContributor(contributor);
						}
					} else {
						pageService.addUContributor(contributor);
					}

					// initialized = true;
				}
			}

			@Override
			public void removedService(ServiceReference<ContributorFactory> reference, Contributor service) {
				if (ConfiguratorTracker.matchesType(UI_CONTRIBUTOR_TYPE_PAGE, reference)) {
					pageService.removeUContributor(service);
				}
			}
		};
	}
}
