package cu.uci.abcd.cron.task;

import org.osgi.framework.BundleContext;

import cu.uci.abos.core.util.ServiceProviderUtil;

public class TaskUtils {
 
	static public <T> T getService(Class<T> clazz){
		BundleContext bc =ServiceProviderUtil.getBundleContext(TaskUtils.class);
		return ServiceProviderUtil.getBundleContext(TaskUtils.class).getService(bc.getServiceReference(clazz));
	}
}
