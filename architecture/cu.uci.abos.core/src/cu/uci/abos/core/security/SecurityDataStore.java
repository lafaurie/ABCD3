/*
 * @(#)ISecurityDataStore.java 1.0.0 16/02/2015 
 * Copyright (c) 2015 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.security;

/**
 * Simple data store for storing security data per session
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0 16/02/2015
 * 
 */
public interface SecurityDataStore {
	/**
	 * Store object data in the security data store
	 * 
	 * @param dataObjectKey
	 *            The object key that identify in the data store
	 * @param dataObject
	 *            The object to be stored in the data store
	 * @return The object stored.
	 */
	public Object putSecurityDataObject(String dataObjectKey, Object dataObject);

	/**
	 * Get the related object in the data store
	 * 
	 * @param dataObjectKey
	 *            The object key that identify in the data store
	 * @return The object stored.
	 */
	public Object getSecurityDataObject(String dataObjectKey);

	/**
	 * Remove the related object in the data store
	 * 
	 * @param dataObjectKey
	 *            The object key that identify in the data store
	 * @return The object removed in the data store
	 */
	public Object removeSecurityDataObject(String dataObjectKey);

	/**
	 * Remove all the object stored in the data store
	 */
	public void clearSecurityDataStore();
}
