package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
      
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.IOpacUserService;
import cu.uci.abos.api.util.ServiceListener;

public class UserTracker extends ServiceTracker<IOpacUserService, IOpacUserService> {
	ServiceListener<Object> userServiceListener;
	IOpacUserService service;

	public UserTracker() {
		super(FrameworkUtil.getBundle(User.class).getBundleContext(), IOpacUserService.class, null);
	}

	@Override
	public IOpacUserService addingService(ServiceReference<IOpacUserService> reference) {
		service = super.addingService(reference);
		if (userServiceListener != null) {
			userServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setUserService(ServiceListener<Object> userServiceListener) {
		this.userServiceListener = userServiceListener;
		if (service != null) {
			userServiceListener.addServiceListener(service);
		}
	}
}