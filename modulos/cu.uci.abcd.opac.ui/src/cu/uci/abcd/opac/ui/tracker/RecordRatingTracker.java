package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacRecordRatingService;
import cu.uci.abos.api.util.ServiceListener;

public class RecordRatingTracker extends ServiceTracker<IOpacRecordRatingService, IOpacRecordRatingService> {
	ServiceListener<Object> recordRatingServiceListener;
	IOpacRecordRatingService service;

	public RecordRatingTracker() {
		super(FrameworkUtil.getBundle(RecordRatingTracker.class).getBundleContext(), IOpacRecordRatingService.class, null);
	}

	@Override
	public IOpacRecordRatingService addingService(ServiceReference<IOpacRecordRatingService> reference) {
		service = super.addingService(reference);
		if (recordRatingServiceListener != null) {
			recordRatingServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setRecordRatingServiceListener(ServiceListener<Object> recordRatingServiceListener) {
		this.recordRatingServiceListener = recordRatingServiceListener;
		if (service != null) {
			recordRatingServiceListener.addServiceListener(service);
		}
	}

}
