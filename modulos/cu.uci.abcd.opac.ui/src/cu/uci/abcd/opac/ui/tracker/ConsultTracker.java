package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacDataBaseManager;
import cu.uci.abos.api.util.ServiceListener;

public class ConsultTracker extends ServiceTracker<IOpacDataBaseManager, IOpacDataBaseManager> {
	ServiceListener<Object> consultServiceListener;
	IOpacDataBaseManager service;

	public ConsultTracker() {
		super(FrameworkUtil.getBundle(ConsultTracker.class).getBundleContext(), IOpacDataBaseManager.class, null);
	}

	@Override
	public IOpacDataBaseManager addingService(ServiceReference<IOpacDataBaseManager> reference) {
		service = super.addingService(reference);
		if (consultServiceListener != null) {
			consultServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setConsultServiceListener(ServiceListener<Object> conServiceListener) {
		this.consultServiceListener = conServiceListener;
		if (service != null) {
			conServiceListener.addServiceListener(service);
		}
	}
}
