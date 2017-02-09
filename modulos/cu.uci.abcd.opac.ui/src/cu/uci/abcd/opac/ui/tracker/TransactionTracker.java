package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacTransactionService;
import cu.uci.abos.api.util.ServiceListener;

public class TransactionTracker extends ServiceTracker<IOpacTransactionService, IOpacTransactionService> {
	ServiceListener<Object> transactionServiceListener;
	IOpacTransactionService service;
   
	public TransactionTracker() {
		super(FrameworkUtil.getBundle(TransactionTracker.class).getBundleContext(), IOpacTransactionService.class, null);
	}

	@Override
	public IOpacTransactionService addingService(ServiceReference<IOpacTransactionService> reference) {
		service = super.addingService(reference);
		if (transactionServiceListener != null) {
			transactionServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setTransactionServiceListener(ServiceListener<Object> transactionServiceListener) {
		this.transactionServiceListener = transactionServiceListener;
		if (service != null) {
			transactionServiceListener.addServiceListener(service);
		}
	}
}