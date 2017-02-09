/*
 * @(#)ConfiguratorTracker.java 1.0.0 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abos.api.log.AbosLog;
import cu.uci.abos.api.log.AbosLogConstant;
import cu.uci.abos.api.log.LogListener;
import cu.uci.abos.api.ui.ContributorFactory;
import cu.uci.abos.api.ui.EntryPointService;
import cu.uci.abos.core.log.LogTracker;
import cu.uci.abos.core.util.ServiceProviderUtil;

public class ConfiguratorTracker extends ServiceTracker<ApplicationConfiguration, ApplicationConfiguration> implements LogListener {
	private static final String CONFIGURATOR_REFERENCE = "ApplicationReference";
	private final Application application;
	private final ApplicationConfiguration configurator;
	private ServiceTracker<EntryPointService, EntryPointService> entryPointServiceTracker;
	private AbosLog log;

	public ConfiguratorTracker(Application configuration, ApplicationConfiguration configurator) {
		super(ServiceProviderUtil.getBundleContext(ConfiguratorTracker.class), ApplicationConfiguration.class, null);
		this.application = configuration;
		this.configurator = configurator;
		LogTracker logTracker = new LogTracker();
		logTracker.setListener(this);
		logTracker.open();
		entryPointServiceTracker = new ServiceTracker<EntryPointService, EntryPointService>(context, EntryPointService.class, null) {
			@Override
			public EntryPointService addingService(ServiceReference<EntryPointService> reference) {
				EntryPointService entryPointService = super.addingService(reference);
				application.addEntryPoint(entryPointService.getPath(), entryPointService.getEntryPointClass(), entryPointService.getProperties());
				if (log != null) {
					log.log(AbosLogConstant.INFO, entryPointService.getClass() + " registered!", null);
				}
				return entryPointService;
			}

		};
		entryPointServiceTracker.open();
		open();
	}

	@Override
	public ApplicationConfiguration addingService(ServiceReference<ApplicationConfiguration> reference) {
		ApplicationConfiguration result = super.addingService(reference);
		if (isTrackedConfiguration(result)) {
			storeConfigurationServiceReference(reference);
			close();
		}
		return result;
	}

	public static boolean matches(ServiceReference<ContributorFactory> contributorReference) {
		boolean result = true;
		if (hasApplicationConfiguration()) {
			try {
				result = doMatches(contributorReference);
			} catch (InvalidSyntaxException shouldNotHappen) {
				throw new IllegalStateException(shouldNotHappen);
			}
		}
		return result;
	}

	public static boolean matchesType(String value, ServiceReference<ContributorFactory> contributorReference) {
		try {
			String expression = createFilterExpression("type", value);
			Filter filter = FrameworkUtil.createFilter(expression);
			return filter.match(contributorReference);
		} catch (InvalidSyntaxException shouldNotHappen) {
			throw new IllegalStateException(shouldNotHappen);
		}
	}

	private static boolean doMatches(ServiceReference<ContributorFactory> contributorReference) throws InvalidSyntaxException {
		String expression = createFilterExpression();
		Filter filter = FrameworkUtil.createFilter(expression);
		return filter.match(contributorReference);
	}

	private static boolean hasApplicationConfiguration() {
		@SuppressWarnings("rawtypes")
		ServiceReference serviceReference = getConfiguratorReference();
		return null != serviceReference.getProperty(getConfiguratorKey());
	}

	private static String createFilterExpression() {
		@SuppressWarnings("rawtypes")
		ServiceReference serviceReference = getConfiguratorReference();
		String value = (String) serviceReference.getProperty(getConfiguratorKey());
		String key = getConfiguratorKey();
		return createFilterExpression(key, value);
	}

	private static String createFilterExpression(String key, String value) {
		return "(" + key + "=" + value + ")";
	}

	@SuppressWarnings("rawtypes")
	private static ServiceReference getConfiguratorReference() {
		return (ServiceReference) RWT.getApplicationContext().getAttribute(CONFIGURATOR_REFERENCE);
	}

	private static String getConfiguratorKey() {
		return ApplicationConfiguration.class.getSimpleName();
	}

	private void storeConfigurationServiceReference(ServiceReference<ApplicationConfiguration> ref) {
		application.setAttribute(ConfiguratorTracker.CONFIGURATOR_REFERENCE, ref);
	}

	private boolean isTrackedConfiguration(ApplicationConfiguration result) {
		return result == configurator;
	}

	@Override
	public void addLogService(AbosLog log) {
		this.log = log;
	}
}
