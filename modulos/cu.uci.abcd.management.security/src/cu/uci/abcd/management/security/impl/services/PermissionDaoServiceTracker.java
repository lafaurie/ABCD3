package cu.uci.abcd.management.security.impl.services;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.dao.management.security.PermissionDAO;

public class PermissionDaoServiceTracker extends ServiceTracker<PermissionDAO, PermissionDAO> {

	private IPermissionDaoServiceListener listener;

	public PermissionDaoServiceTracker() {
		super(FrameworkUtil.getBundle(PermissionDaoServiceTracker.class).getBundleContext(), PermissionDAO.class, null);
	}

	@Override
	public PermissionDAO addingService(ServiceReference<PermissionDAO> reference) {
		PermissionDAO serviceDao = super.addingService(reference);
		if (listener != null) {
			listener.addPermissionDaoService(serviceDao);
		}
		return serviceDao;
	}

	public void setListener(IPermissionDaoServiceListener listener) {
		this.listener = listener;
	}

}
