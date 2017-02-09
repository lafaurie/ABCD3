package cu.uci.abcd.management.security.impl.services;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.dao.common.UserDAO;

public class AccountDaoServiceTracker extends ServiceTracker<UserDAO, UserDAO> {

	private IAccountDaoServiceListener listener;

	public AccountDaoServiceTracker() {
		super(FrameworkUtil.getBundle(AccountDaoServiceTracker.class).getBundleContext(), UserDAO.class, null);
	}

	public void setListener(IAccountDaoServiceListener listener) {
		this.listener = listener;
	}

	@Override
	public UserDAO addingService(ServiceReference<UserDAO> reference) {
		UserDAO accountDAO = super.addingService(reference);
		if (this.listener != null) {
			listener.addAccountDaoService(accountDAO);
		}
		return accountDAO;
	}
}
