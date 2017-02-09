/*
 * @(#)ContributorTrackerService.java 1.0.0 08/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.ui.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abos.api.exception.AbosInstantiationException;
import cu.uci.abos.log.AbosLogConstant;
import cu.uci.abos.log.IAbosLog;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IContributorFactory;
import cu.uci.abos.ui.api.IServiceTracker;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0
 * 
 */
public class ContributorTrackerService {
	final ServiceTracker<IContributorFactory, IContributorFactory> serviceTracker;
	final Map<ServiceReference<IContributorFactory>, IContributorFactory> contributors;
	final Map<IServiceTracker, ContributorTrackerAdapter> trackers;
	final Object lock;
	private ContributorTrackerAdapter trackerAdapter;
	private IAbosLog logger;

	private static class ContributorTrackerAdapter implements IServiceTracker {
		private final ConcurrentHashMap<ServiceReference<IContributorFactory>, IContributor> contribs;
		private final IServiceTracker tracker;

		public ContributorTrackerAdapter(IServiceTracker tracker) {
			super();
			this.tracker = tracker;
			contribs = new ConcurrentHashMap<ServiceReference<IContributorFactory>, IContributor>();
		}

		@Override
		public void removedService(ServiceReference<IContributorFactory> reference, IContributor service) {
			contribs.remove(reference);
			tracker.removedService(reference, service);
		}

		@Override
		public void addingService(ServiceReference<IContributorFactory> reference, IContributor service) {
			tracker.addingService(reference, service);
			contribs.put(reference, service);
		}

		public IContributor getContributor(ServiceReference<IContributorFactory> reference) {
			return contribs.get(reference);
		}

	}

	public ContributorTrackerService(BundleContext context, IAbosLog log) {
		Class<IContributorFactory> type = IContributorFactory.class;
		this.lock = new Object();
		this.logger = log;
		this.trackers = new HashMap<IServiceTracker, ContributorTrackerAdapter>();
		this.contributors = new HashMap<ServiceReference<IContributorFactory>, IContributorFactory>();
		this.serviceTracker = new ServiceTracker<IContributorFactory, IContributorFactory>(context, type, null) {

			@Override
			public IContributorFactory addingService(ServiceReference<IContributorFactory> reference) {
				IContributorFactory result = super.addingService(reference);
				try {
					result.injectServiceReference(reference);

					Object[] trackerList;
					synchronized (lock) {
						contributors.put(reference, result);
						trackerList = trackers.values().toArray();
					}
					for (int i = 0; i < trackerList.length; i++) {
						((IServiceTracker) trackerList[i]).addingService(reference, result.create());
					}
					return result;
				} catch (ClassNotFoundException e) {
					if (logger != null) {
						logger.log(AbosLogConstant.ERROR, "ERROR adding IContrbutorFactory service with class " + reference.getProperty("class"), e);
					}
				} catch (AbosInstantiationException e) {
					if (logger != null) {
						logger.log(AbosLogConstant.ERROR, "ERROR traing to instantiate class with name" + reference.getProperty("class"), e);
					}
				}
				return result;
			}

			@Override
			public void removedService(ServiceReference<IContributorFactory> reference, IContributorFactory service) {
				Object[] trackerList;
				synchronized (lock) {
					contributors.remove(reference);
					trackerList = trackers.values().toArray();
				}
				for (int i = 0; i < trackerList.length; i++) {
					ContributorTrackerAdapter tracker = (ContributorTrackerAdapter) trackerList[i];
					tracker.removedService(reference, tracker.getContributor(reference));
				}
				super.removedService(reference, service);
			}

		};
		serviceTracker.open();
	}

	@SuppressWarnings("unchecked")
	public void addServiceTracker(IServiceTracker tracker) {
		Object[] references;
		Object[] services;
		trackerAdapter = new ContributorTrackerAdapter(tracker);
		synchronized (lock) {
			trackers.put(tracker, trackerAdapter);
			references = contributors.keySet().toArray();
			services = contributors.values().toArray();
		}
		for (int i = 0; i < services.length; i++) {
			ServiceReference<IContributorFactory> reference = (ServiceReference<IContributorFactory>) references[i];
			try {
				trackerAdapter.addingService(reference, ((IContributorFactory) services[i]).create());
			} catch (AbosInstantiationException e) {
				if (logger != null) {
					logger.log(AbosLogConstant.ERROR, "ERROR traing to instantiate class with name" + reference.getProperty("class"), e);
				}
			}
		}
	}

	public void removeServiceTracker(IServiceTracker tracker) {
		Object[] references;
		Object[] services;
		ContributorTrackerAdapter trackerAdapter;
		synchronized (lock) {
			trackerAdapter = trackers.remove(tracker);
			references = contributors.keySet().toArray();
			services = contributors.values().toArray();
		}
		for (int i = 0; i < services.length; i++) {
			@SuppressWarnings("unchecked")
			ServiceReference<IContributorFactory> reference = (ServiceReference<IContributorFactory>) references[i];
			IContributor contributor = trackerAdapter.getContributor(reference);
			trackerAdapter.removedService(reference, contributor);
		}
	}

	public void setLogger(IAbosLog logger) {
		this.logger = logger;
	}

}
