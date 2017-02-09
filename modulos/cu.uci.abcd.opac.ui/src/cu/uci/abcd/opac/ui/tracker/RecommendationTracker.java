package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacRecommendationService;
import cu.uci.abos.api.util.ServiceListener;

public class RecommendationTracker extends ServiceTracker<IOpacRecommendationService, IOpacRecommendationService> {
	ServiceListener<Object> recommendationServiceListener;
	IOpacRecommendationService service;

	public RecommendationTracker() {
		super(FrameworkUtil.getBundle(RecommendationTracker.class).getBundleContext(), IOpacRecommendationService.class, null);
	}

	@Override
	public IOpacRecommendationService addingService(ServiceReference<IOpacRecommendationService> reference) {
		service = super.addingService(reference);
		if (recommendationServiceListener != null) {
			recommendationServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setRecommendationServiceListener(ServiceListener<Object> recommendationServiceListener) {
		this.recommendationServiceListener = recommendationServiceListener;
		if (service != null) {
			recommendationServiceListener.addServiceListener(service);
		}
	}
}
