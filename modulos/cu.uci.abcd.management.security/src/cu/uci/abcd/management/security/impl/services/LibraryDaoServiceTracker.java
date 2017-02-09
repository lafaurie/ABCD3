package cu.uci.abcd.management.security.impl.services;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.dao.management.library.LibraryDAO;

public class LibraryDaoServiceTracker extends ServiceTracker<LibraryDAO, LibraryDAO> {

	private IServiceListener<LibraryDAO> listener;

	public LibraryDaoServiceTracker() {
		super(FrameworkUtil.getBundle(LibraryDaoServiceTracker.class).getBundleContext(), LibraryDAO.class, null);
	}

	public void setListener(IServiceListener<LibraryDAO> listener) {
		this.listener = listener;
	}

	@Override
	public LibraryDAO addingService(ServiceReference<LibraryDAO> reference) {
		LibraryDAO serviceDao = super.addingService(reference);
		if (listener != null) {
			listener.addServiceListener(serviceDao);
		}
		return serviceDao;
	}

}
