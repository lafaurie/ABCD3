package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacSelectionListService;
import cu.uci.abos.api.util.ServiceListener;

public class SelectionListTracker extends ServiceTracker<IOpacSelectionListService, IOpacSelectionListService> {
	ServiceListener<Object> selectionListServiceListener;
	IOpacSelectionListService service;

	public SelectionListTracker() {
		super(FrameworkUtil.getBundle(SelectionListTracker.class).getBundleContext(), IOpacSelectionListService.class, null);
	}

	@Override
	public IOpacSelectionListService addingService(ServiceReference<IOpacSelectionListService> reference) {
		service = super.addingService(reference);
		if (selectionListServiceListener != null) {
			selectionListServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setSelectionListServiceListener(ServiceListener<Object> selectionListServiceListener) {
		this.selectionListServiceListener = selectionListServiceListener;
		if (service != null) {
			selectionListServiceListener.addServiceListener(service);
		}
	}
}