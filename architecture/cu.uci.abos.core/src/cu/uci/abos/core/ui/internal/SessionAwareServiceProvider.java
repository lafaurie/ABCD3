/*
 * @(#)SessionAwareServiceProvider.java 1.0.0 26/06/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.ui.internal;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.SingletonUtil;

import cu.uci.abos.api.util.ServiceProvider;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0
 * 
 * 
 */
public class SessionAwareServiceProvider implements ServiceProvider {

	@Override
	public <T> T register(Class<T> service, T instance) {
		return getServiceProvider().register(service, instance);
	}

	@Override
	public <T> T get(Class<T> service) {
		return getServiceProvider().get(service);
	}

	private ServiceProvider getServiceProvider() {
		return SingletonUtil.getUniqueInstance(ServiceProviderImpl.class, RWT.getUISession());
	}

}
