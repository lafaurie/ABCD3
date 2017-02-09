package cu.uci.abos.dataprovider.jisis.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

import cu.uci.abos.dataprovider.jisis.internal.impl.JisisAdpter;
import cu.uci.abos.dataprovider.jisis.services.IJisisAdapter;


public class JisisAdapaterFactory implements ServiceFactory<Object> {

	IJisisAdapter adapter = new JisisAdpter();

	@Override
	public Object getService(Bundle arg0, ServiceRegistration<Object> arg1) {
		return adapter;
	}

	@Override
	public void ungetService(Bundle arg0, ServiceRegistration<Object> arg1,
			Object arg2) {
		// TODO Auto-generated method stub
		
	}

}
