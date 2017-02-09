/*
 * @(#)IServiceProvider.java 1.0.0 22/06/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.api.util;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0
 */
public interface ServiceProvider {
	/**
	 * 
	 * @param service
	 * @param instance
	 * @return
	 */
	<T> T register(Class<T> service, T instance);

	/**
	 * 
	 * @param service
	 * @return
	 */
	<T> T get(Class<T> service);
}
