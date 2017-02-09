package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacTagService;
import cu.uci.abos.api.util.ServiceListener;

public class TagTracker extends ServiceTracker<IOpacTagService, IOpacTagService> {
	ServiceListener<Object> tagServiceListener;
	IOpacTagService service;

	public TagTracker() {
		super(FrameworkUtil.getBundle(TagTracker.class).getBundleContext(), IOpacTagService.class, null);
	}

	@Override
	public IOpacTagService addingService(ServiceReference<IOpacTagService> reference) {
		service = super.addingService(reference);
		if (tagServiceListener != null) {
			tagServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setTagServiceListener(ServiceListener<Object> tagServiceListener) {
		this.tagServiceListener = tagServiceListener;
		if (service != null) {
			tagServiceListener.addServiceListener(service);
		}
	}
}
