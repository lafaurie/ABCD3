package cu.uci.abos.core.security;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class LoginServiceFactoryTracker extends ServiceTracker<LoginServiceFactory, LoginServiceFactory> {

	private LoginServiceFactoryListener listener;

	public LoginServiceFactoryTracker() {
		super(FrameworkUtil.getBundle(LoginServiceFactoryTracker.class).getBundleContext(), LoginServiceFactory.class, null);
	}

	public void setListener(LoginServiceFactoryListener listener) {
		this.listener = listener;
	}

	@Override
	public LoginServiceFactory addingService(ServiceReference<LoginServiceFactory> reference) {
		LoginServiceFactory loginServiceFactory = super.addingService(reference);
		if (this.listener != null) {
			listener.setLoginServiceFactory(loginServiceFactory);
		}
		return loginServiceFactory;
	}
}
