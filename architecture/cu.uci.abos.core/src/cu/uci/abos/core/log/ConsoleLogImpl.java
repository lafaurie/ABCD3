package cu.uci.abos.core.log;

import java.io.PrintStream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import cu.uci.abos.api.log.AbosLog;
import cu.uci.abos.api.log.AbosLogConstant;

public class ConsoleLogImpl implements AbosLog, BundleListener, FrameworkListener, ServiceListener {

	public ConsoleLogImpl() {
		super();
		System.out.println("ConsoleLogImpl created!");
	}

	@Override
	public void log(int level, String message, Throwable throwable) {
		PrintStream print = null;
		if (level > AbosLogConstant.WARNING) {
			print = System.err;
		} else {
			print = System.out;
		}

		print.println("ABOS LOG: " + message);
		if (throwable != null)
			throwable.printStackTrace(print);
	}

	@Override
	public void serviceChanged(ServiceEvent event) {
		Bundle bundle = event.getServiceReference().getBundle();
		int eventType = event.getType();
		String action = "";

		switch (event.getType()) {
		case ServiceEvent.REGISTERED:
			action = "ServiceEvent REGISTERED";
			break;
		case ServiceEvent.MODIFIED:
			action = "ServiceEvent MODIFIED";
			break;
		case ServiceEvent.UNREGISTERING:
			action = "ServiceEvent UNREGISTERING";
			break;
		case ServiceEvent.MODIFIED_ENDMATCH:
			action = "ServiceEvent MODIFIED_ENDMATCH";
			break;
		}
		log(eventType, bundle.getSymbolicName() + " was " + action, null);
	}

	@Override
	public void frameworkEvent(FrameworkEvent event) {
		Bundle bundle = event.getBundle();
		int eventType = event.getType();
		String action = "";

		switch (event.getType()) {
		case FrameworkEvent.ERROR:
			action = " FrameworkEvent ERROR";
			break;
		case FrameworkEvent.STOPPED:
			action = "FrameworkEvent STOPPED";
			break;
		case FrameworkEvent.INFO:
			action = "FrameworkEvent UNREGISTERING";
			break;
		case FrameworkEvent.PACKAGES_REFRESHED:
			action = "FrameworkEvent PACKAGES_REFRESHED";
			break;
		case FrameworkEvent.STARTED:
			action = "FrameworkEvent STARTED";
			break;
		case FrameworkEvent.STARTLEVEL_CHANGED:
			action = "FrameworkEvent STARTLEVEL_CHANGED";
			break;
		case FrameworkEvent.STOPPED_BOOTCLASSPATH_MODIFIED:
			action = "FrameworkEvent STOPPED_BOOTCLASSPATH_MODIFIED";
			break;
		case FrameworkEvent.STOPPED_UPDATE:
			action = "FrameworkEvent STOPPED_UPDATE";
			break;
		case FrameworkEvent.WAIT_TIMEDOUT:
			action = "FrameworkEvent WAIT_TIMEDOUT";
			break;
		case FrameworkEvent.WARNING:
			action = "FrameworkEvent WARNING";
			break;
		}
		log(eventType, bundle.getSymbolicName() + " was " + action, event.getThrowable());
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		Bundle bundle = event.getBundle();
		int eventType = event.getType();
		String action = "";

		switch (event.getType()) {
		case BundleEvent.INSTALLED:
			action = "BundleEvent INSTALLED";
			break;
		case BundleEvent.LAZY_ACTIVATION:
			action = "BundleEvent LAZY_ACTIVATION";
			break;
		case BundleEvent.RESOLVED:
			action = "BundleEvent RESOLVED";
			break;
		case BundleEvent.STARTED:
			action = "BundleEvent STARTED";
			break;
		case BundleEvent.STARTING:
			action = "BundleEvent STARTING";
			break;
		case BundleEvent.STOPPED:
			action = "BundleEvent STOPPED";
			break;
		case BundleEvent.STOPPING:
			action = "BundleEvent STOPPING";
			break;
		case BundleEvent.UNINSTALLED:
			action = "BundleEvent UNINSTALLED";
			break;
		case BundleEvent.UNRESOLVED:
			action = "BundleEvent UNRESOLVED";
			break;
		case BundleEvent.UPDATED:
			action = "BundleEvent UPDATED";
			break;
		}
		log(eventType, bundle.getSymbolicName() + " was " + action, null);

	}

}
