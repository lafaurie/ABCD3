package cu.uci.abos.core.util;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class ServiceProviderUtil {
	/**
	 * 
	 * @param classContext
	 * @return
	 */
	public static BundleContext getBundleContext(Class<?> classContext) {
		return FrameworkUtil.getBundle(classContext).getBundleContext();
	}

	/**
	 * 
	 * @param classType
	 *            The <code>Class</code>
	 * @return The instance of the service
	 */
	public static <T> T getService(Class<T> classType) {
		BundleContext context = getBundleContext(ServiceProviderUtil.class);
		ServiceReference<T> serviceReference = context.getServiceReference(classType);
		return context.getService(serviceReference);
	}
}
