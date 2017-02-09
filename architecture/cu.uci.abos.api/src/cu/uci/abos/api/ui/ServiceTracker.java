package cu.uci.abos.api.ui;

import org.osgi.framework.ServiceReference;

public interface ServiceTracker {
	void removedService(ServiceReference<ContributorFactory> reference, Contributor service);

	void addingService(ServiceReference<ContributorFactory> reference, Contributor service);
}
