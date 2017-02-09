/*
 * @(#)ContributorTracker.java 1.0.0 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.rap.rwt.service.UISessionEvent;
import org.eclipse.rap.rwt.service.UISessionListener;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorFactory;
import cu.uci.abos.core.ui.internal.ContributorTrackerService;
import cu.uci.abos.core.util.ServiceProviderUtil;

public class ContributorTracker {
	final Display display;
	final ContributorTrackerService contributorTrackerService;
	final ServiceTrackerImpl tracker;
	final ServerPushSession pushSession;

	class ServiceTrackerImpl implements cu.uci.abos.api.ui.ServiceTracker {

		@Override
		public void removedService(final ServiceReference<ContributorFactory> reference, final Contributor service) {
			if (!display.isDisposed()) {
				display.asyncExec(new Runnable() {

					@Override
					public void run() {
						if (canRun(reference)) {
							ContributorTracker.this.removedService(reference, service);
						}
					}
				});
			}
		}

		@Override
		public void addingService(final ServiceReference<ContributorFactory> reference, final Contributor service) {
			if (!display.isDisposed()) {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						if (canRun(reference)) {
							ContributorTracker.this.addingService(reference, service);
						}
					}
				});
			}
		}

		boolean canRun(final ServiceReference<ContributorFactory> reference) {
			return display.getThread() == Thread.currentThread() && ConfiguratorTracker.matches(reference);
		}
	}

	private class ContributorTrackerServiceTracker extends ServiceTracker<ContributorTrackerService, ContributorTrackerService> {
		ContributorTrackerServiceTracker() {
			super(ServiceProviderUtil.getBundleContext(ContributorTracker.class), ContributorTrackerService.class, null);
		}
	}

	private ContributorTrackerService getContributorTrackerService() {
		ContributorTrackerServiceTracker trackerServiceTracker = new ContributorTrackerServiceTracker();
		trackerServiceTracker.open();
		ContributorTrackerService resulTrackerService = trackerServiceTracker.getService();
		trackerServiceTracker.close();
		return resulTrackerService;
	}

	private boolean closeOnSessionTimeout() {
		return RWT.getUISession().addUISessionListener(new UISessionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void beforeDestroy(UISessionEvent arg0) {
				contributorTrackerService.removeServiceTracker(tracker);
				pushSession.stop();
			}
		});
	}

	public ContributorTracker() {
		display = Display.getDefault();
		pushSession = new ServerPushSession();
		pushSession.start();
		contributorTrackerService = getContributorTrackerService();
		tracker = new ServiceTrackerImpl();
		contributorTrackerService.addServiceTracker(tracker);
		closeOnSessionTimeout();
	}

	public void removedService(ServiceReference<ContributorFactory> reference, Contributor service) {
		// subclasses may override
	}

	public void addingService(ServiceReference<ContributorFactory> reference, Contributor service) {
		// subclasses may override
	}
}
