
package cu.uci.abos.core.ui;

import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.ServiceReference;

import cu.uci.abcd.management.security.ILoginService;
import cu.uci.abcd.management.security.impl.principals.SecurityAspect;
import cu.uci.abos.core.ui.internal.ContributorServiceImpl;
import cu.uci.abos.core.ui.internal.LayoutContextImpl;
import cu.uci.abos.ui.api.ConfiguratorTracker;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IContributorFactory;
import cu.uci.abos.ui.api.IContributorService;
import cu.uci.abos.ui.api.ILayoutProvider;
import cu.uci.abos.ui.api.IPlatformContributor;
import cu.uci.abos.util.api.IServiceProvider;

public class ShellConfigurator {
	private static final String UI_CONTRIBUTOR_TYPE_PAGE = "abcd";

	private final IServiceProvider serviceProvider;
	private boolean securityEnabled;
	SecurityAspect security;

	public ShellConfigurator(IServiceProvider serviceProvider, boolean securityEnabledParam) {
		this.serviceProvider = serviceProvider;
		this.security = new SecurityAspect();

		this.securityEnabled = securityEnabledParam;
	}

	public Shell configure(IPlatformContributor[] pageStructureProviders, ILayoutProvider layoutProvider) {
		LayoutContextImpl layoutContext = new LayoutContextImpl();
		ContributorServiceImpl pageService = new ContributorServiceImpl(layoutProvider, layoutContext);
		serviceProvider.register(IContributorService.class, pageService);
		trackContributions(pageService);

		ShellProvider shellProvider = new ShellProvider(pageStructureProviders, layoutProvider, layoutContext);
		return shellProvider.createShell();
	}

	private void trackContributions(final ContributorServiceImpl pageService) {
		new ContributorTracker() {

			// private boolean initialized;

			@Override
			public void addingService(ServiceReference<IContributorFactory> reference, IContributor contributor) {
				if (ConfiguratorTracker.matchesType(UI_CONTRIBUTOR_TYPE_PAGE, reference)) {
					if (securityEnabled) {
						security.setService(serviceProvider.get(ILoginService.class));
						if (security.verifyPermission(contributor)) {
							pageService.addUIContributor(contributor);
						}
					} else {
						pageService.addUIContributor(contributor);
					}

					// initialized = true;
				}
			}

			@Override
			public void removedService(ServiceReference<IContributorFactory> reference, IContributor service) {
				if (ConfiguratorTracker.matchesType(UI_CONTRIBUTOR_TYPE_PAGE, reference)) {
					pageService.removeUIContributor(service);
				}
			}
		};
	}
}
