/*
 * @(#)CoreActivator.java 1.0.0 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.ui.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.Version;

import cu.uci.abos.api.log.AbosLog;
import cu.uci.abos.api.log.AbosLogConstant;
import cu.uci.abos.api.log.LogListener;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.log.LogTracker;

public class CoreActivator implements BundleActivator, LogListener {
	private ServiceRegistration<ServiceProvider> serviceProviderRegistration;
	private ServiceRegistration<ContributorTrackerService> trackerServiceRegistration;
	private ContributorTrackerService service;
	private LogTracker logTracker;
	private AbosLog logService;
	private String bundleName;
	private Version bundleVersion;

	@Override
	public void start(BundleContext arg0) throws Exception {
		
		/*SecurityManager sm = System.getSecurityManager();
		if (sm== null) {
			throw new BundleException("Not Security Manager Configured");
		}else{
			sm.checkPermission(new AllPermission());
		}*/
		bundleName = arg0.getBundle().getHeaders().get("Bundle-SymbolicName");
		bundleVersion = arg0.getBundle().getVersion();
		logTracker = new LogTracker();
		logTracker.setListener(this);
		logTracker.open();
		if (logService != null)
			logService.log(AbosLogConstant.INFO, "Starting bundle " + bundleName + " version " + bundleVersion, null);
		registerContributorTrackerService(arg0, logService);
		if (logService != null)
			logService.log(AbosLogConstant.INFO, "Publishing service under class " + ContributorTrackerService.class, null);
		registerServiceProvider(arg0);
		if (logService != null) {
			logService.log(AbosLogConstant.INFO, "Publishing service under class " + ServiceProvider.class, null);
			logService.log(AbosLogConstant.INFO, "Started bundle " + bundleName + " version " + bundleVersion, null);
		}
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		logService.log(AbosLogConstant.INFO, "Stoped bundle " + bundleName + " version " + bundleVersion, null);
		serviceProviderRegistration.unregister();
		trackerServiceRegistration.unregister();
		logTracker.close();
	}

	private void registerServiceProvider(BundleContext context) {
		SessionAwareServiceProvider serviceProvider = new SessionAwareServiceProvider();
		Class<ServiceProvider> type = ServiceProvider.class;
		serviceProviderRegistration = context.registerService(type, serviceProvider, null);
	}

	private void registerContributorTrackerService(BundleContext context, AbosLog logService) {
		service = new ContributorTrackerService(context, logService);
		Class<ContributorTrackerService> type = ContributorTrackerService.class;
		trackerServiceRegistration = context.registerService(type, service, null);
	}

	@Override
	public void addLogService(AbosLog log) {
		logService = log;
		if (service != null)
			service.setLogger(log);
	}

}
