package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.opac.IOpacLoanObjectService;
import cu.uci.abos.api.util.ServiceListener;

public class LoanObjectTracker extends ServiceTracker<IOpacLoanObjectService, IOpacLoanObjectService> {
	ServiceListener<Object> loanObjectServiceListener;
	IOpacLoanObjectService service;

	public LoanObjectTracker() {
		super(FrameworkUtil.getBundle(LoanObject.class).getBundleContext(), IOpacLoanObjectService.class, null);
	}

	@Override
	public IOpacLoanObjectService addingService(ServiceReference<IOpacLoanObjectService> reference) {
		service = super.addingService(reference);
		if (loanObjectServiceListener != null) {
			loanObjectServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setLoanObjectServiceListener(ServiceListener<Object> loanObjectServiceListener) {
		this.loanObjectServiceListener = loanObjectServiceListener;
		if (service != null) {
			loanObjectServiceListener.addServiceListener(service);
		}
	}
}