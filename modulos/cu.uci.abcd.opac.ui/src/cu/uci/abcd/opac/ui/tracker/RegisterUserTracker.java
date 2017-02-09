package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacRegisterUserService;
import cu.uci.abos.api.util.ServiceListener;

public class RegisterUserTracker extends ServiceTracker<IOpacRegisterUserService, IOpacRegisterUserService> {
	ServiceListener<Object> registerUserServiceListener;
	IOpacRegisterUserService service;

	public RegisterUserTracker() {
		super(FrameworkUtil.getBundle(RegisterUserTracker.class).getBundleContext(), IOpacRegisterUserService.class, null);
	}

	@Override
	public IOpacRegisterUserService addingService(ServiceReference<IOpacRegisterUserService> reference) {
		service = super.addingService(reference);
		if (registerUserServiceListener != null) {
			registerUserServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setRegisterUserServiceListener(ServiceListener<Object> registerUserServiceListener) {
		this.registerUserServiceListener = registerUserServiceListener;
		if (service != null) {
			registerUserServiceListener.addServiceListener(service);
		}
	}
}
