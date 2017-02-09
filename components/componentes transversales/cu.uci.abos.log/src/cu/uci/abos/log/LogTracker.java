package cu.uci.abos.log;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class LogTracker extends ServiceTracker<IAbosLog, IAbosLog> {

	private ILogListener listener;

	public LogTracker() {
		super(FrameworkUtil.getBundle(LogTracker.class).getBundleContext(), IAbosLog.class, null);
	}

	public void setListener(ILogListener listener) {
		this.listener = listener;
	}

	@Override
	public IAbosLog addingService(ServiceReference<IAbosLog> reference) {
		IAbosLog log = super.addingService(reference);
		if (listener != null) {
			listener.addLogService(log);
		}
		return log;
	}

}
