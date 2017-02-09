package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacLoanUserService;
import cu.uci.abos.api.util.ServiceListener;

public class LoanUserTracker extends ServiceTracker<IOpacLoanUserService, IOpacLoanUserService> {
	ServiceListener<Object> loanUserServiceListener;
	IOpacLoanUserService service;

	public LoanUserTracker() {
		super(FrameworkUtil.getBundle(LoanUserTracker.class).getBundleContext(), IOpacLoanUserService.class, null);
	}

	@Override
	public IOpacLoanUserService addingService(ServiceReference<IOpacLoanUserService> reference) {
		service = super.addingService(reference);
		if (loanUserServiceListener != null) {
			loanUserServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setLoanUserServiceListener(ServiceListener<Object> loanUserServiceListener) {
		this.loanUserServiceListener = loanUserServiceListener;
		if (service != null) {
			loanUserServiceListener.addServiceListener(service);
		}
	}
}