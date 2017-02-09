package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacPenaltyService;
import cu.uci.abos.api.util.ServiceListener;

public class PenaltyTracker extends ServiceTracker<IOpacPenaltyService, IOpacPenaltyService> {
	ServiceListener<Object> penaltyServiceListener;
	IOpacPenaltyService service;

	public PenaltyTracker() {
		super(FrameworkUtil.getBundle(PenaltyTracker.class).getBundleContext(), IOpacPenaltyService.class, null);
	}

	@Override
	public IOpacPenaltyService addingService(ServiceReference<IOpacPenaltyService> reference) {
		service = super.addingService(reference);
		if (penaltyServiceListener != null) {
			penaltyServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setPenaltyServiceListener(ServiceListener<Object> penaltyServiceListener) {
		this.penaltyServiceListener = penaltyServiceListener;
		if (service != null) {
			penaltyServiceListener.addServiceListener(service);
		}
	}
}