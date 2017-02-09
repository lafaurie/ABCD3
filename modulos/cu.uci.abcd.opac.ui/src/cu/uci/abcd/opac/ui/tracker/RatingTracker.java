package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacWeigUpService;
import cu.uci.abos.api.util.ServiceListener;

public class RatingTracker extends ServiceTracker<IOpacWeigUpService, IOpacWeigUpService> {
	ServiceListener<Object> ratingServiceListener;
	IOpacWeigUpService service;

	public RatingTracker() {
		super(FrameworkUtil.getBundle(RatingTracker.class).getBundleContext(), IOpacWeigUpService.class, null);
	}

	@Override
	public IOpacWeigUpService addingService(ServiceReference<IOpacWeigUpService> reference) {
		service = super.addingService(reference);
		if (ratingServiceListener != null) {
			ratingServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setRatingServiceListener(ServiceListener<Object> ratingServiceListener) {
		this.ratingServiceListener = ratingServiceListener;
		if (service != null) {
			ratingServiceListener.addServiceListener(service);
		}
	}

}
