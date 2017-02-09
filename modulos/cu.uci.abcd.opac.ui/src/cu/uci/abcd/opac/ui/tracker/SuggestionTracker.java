package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacSuggestionService;
import cu.uci.abos.api.util.ServiceListener;

public class SuggestionTracker extends ServiceTracker<IOpacSuggestionService, IOpacSuggestionService> {
	ServiceListener<Object> suggestionServiceListener;
	IOpacSuggestionService service;

	public SuggestionTracker() {
		super(FrameworkUtil.getBundle(SuggestionTracker.class).getBundleContext(), IOpacSuggestionService.class, null);
	}

	@Override
	public IOpacSuggestionService addingService(ServiceReference<IOpacSuggestionService> reference) {
		service = super.addingService(reference);
		if (suggestionServiceListener != null) {
			suggestionServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setSuggestionServiceListener(ServiceListener<Object> suggestionServiceListener) {
		this.suggestionServiceListener = suggestionServiceListener;
		if (service != null) {
			suggestionServiceListener.addServiceListener(service);
		}
	}

}
