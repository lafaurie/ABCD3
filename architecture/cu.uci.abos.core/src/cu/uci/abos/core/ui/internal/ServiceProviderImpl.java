/*
 * @(#)ServiceProviderImpl.java 1.0.0 26/06/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.ui.internal;

import java.util.HashMap;
import java.util.Map;

import cu.uci.abos.api.util.ServiceProvider;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0
 * 
 */
public class ServiceProviderImpl implements ServiceProvider {
	private Map<Class<?>, Object> services;

	public ServiceProviderImpl() {
		services = new HashMap<Class<?>, Object>();
	}

	@Override
	public <T> T register(Class<T> service, T instance) {
		services.put(service, instance);
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> service) {
		return (T) services.get(service);
	}

}
