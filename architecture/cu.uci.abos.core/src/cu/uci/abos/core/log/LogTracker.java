package cu.uci.abos.core.log;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abos.api.log.AbosLog;
import cu.uci.abos.api.log.LogListener;

public class LogTracker extends ServiceTracker<AbosLog, AbosLog> {

	private LogListener listener;

	public LogTracker() {
		super(FrameworkUtil.getBundle(LogTracker.class).getBundleContext(), AbosLog.class, null);
	}

	public void setListener(LogListener listener) {
		this.listener = listener;
	}

	@Override
	public AbosLog addingService(ServiceReference<AbosLog> reference) {
		AbosLog log = super.addingService(reference);
		if (listener != null) {
			listener.addLogService(log);
		}
		return log;
	}

}
