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
import cu.uci.abos.api.log.AbosLog;
import cu.uci.abos.api.log.AbosLogConstant;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorFactory;

/**
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0
 * 
 */
public class ContributorTrackerService {
	final ServiceTracker<ContributorFactory, ContributorFactory> serviceTracker;
	final Map<ServiceReference<ContributorFactory>, ContributorFactory> contributors;
	final Map<cu.uci.abos.api.ui.ServiceTracker, ContributorTrackerAdapter> trackers;
	final Object lock;
	private ContributorTrackerAdapter trackerAdapter;
	private AbosLog logger;

	private static class ContributorTrackerAdapter implements cu.uci.abos.api.ui.ServiceTracker {
		private final ConcurrentHashMap<ServiceReference<ContributorFactory>, Contributor> contribs;
		private final cu.uci.abos.api.ui.ServiceTracker tracker;

		public ContributorTrackerAdapter(cu.uci.abos.api.ui.ServiceTracker tracker) {
			super();
			this.tracker = tracker;
			contribs = new ConcurrentHashMap<ServiceReference<ContributorFactory>, Contributor>();
		}

		@Override
		public void removedService(ServiceReference<ContributorFactory> reference, Contributor service) {
			contribs.remove(reference);
			tracker.removedService(reference, service);
		}

		@Override
		public void addingService(ServiceReference<ContributorFactory> reference, Contributor service) {
			tracker.addingService(reference, service);
			contribs.put(reference, service);
		}

		public Contributor getContributor(ServiceReference<ContributorFactory> reference) {
			return contribs.get(reference);
		}

	}

	public ContributorTrackerService(BundleContext context, AbosLog log) {
		Class<ContributorFactory> type = ContributorFactory.class;
		this.lock = new Object();
		this.logger = log;
		this.trackers = new HashMap<cu.uci.abos.api.ui.ServiceTracker, ContributorTrackerAdapter>();
		this.contributors = new HashMap<ServiceReference<ContributorFactory>, ContributorFactory>();
		this.serviceTracker = new ServiceTracker<ContributorFactory, ContributorFactory>(context, type, null) {

			@Override
			public ContributorFactory addingService(ServiceReference<ContributorFactory> reference) {
				ContributorFactory result = super.addingService(reference);
				try {
					result.injectServiceReference(reference);

					Object[] trackerList;
					synchronized (lock) {
						contributors.put(reference, result);
						trackerList = trackers.values().toArray();
					}
					for (int i = 0; i < trackerList.length; i++) {
						((cu.uci.abos.api.ui.ServiceTracker) trackerList[i]).addingService(reference, result.create());
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
			public void removedService(ServiceReference<ContributorFactory> reference, ContributorFactory service) {
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
	public void addServiceTracker(cu.uci.abos.api.ui.ServiceTracker tracker) {
		Object[] references;
		Object[] services;
		trackerAdapter = new ContributorTrackerAdapter(tracker);
		synchronized (lock) {
			trackers.put(tracker, trackerAdapter);
			references = contributors.keySet().toArray();
			services = contributors.values().toArray();
		}
		for (int i = 0; i < services.length; i++) {
			ServiceReference<ContributorFactory> reference = (ServiceReference<ContributorFactory>) references[i];
			try {
				trackerAdapter.addingService(reference, ((ContributorFactory) services[i]).create());
			} catch (AbosInstantiationException e) {
				if (logger != null) {
					logger.log(AbosLogConstant.ERROR, "ERROR traing to instantiate class with name" + reference.getProperty("class"), e);
				}
			}
		}
	}

	public void removeServiceTracker(cu.uci.abos.api.ui.ServiceTracker tracker) {
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
			ServiceReference<ContributorFactory> reference = (ServiceReference<ContributorFactory>) references[i];
			Contributor contributor = trackerAdapter.getContributor(reference);
			trackerAdapter.removedService(reference, contributor);
		}
	}

	public void setLogger(AbosLog logger) {
		this.logger = logger;
	}

}
