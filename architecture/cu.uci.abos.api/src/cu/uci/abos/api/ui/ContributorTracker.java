/*
 * @(#)IContributorTracker.java 1.0.0 22/06/2014
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.api.ui;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0
 * 
 */
public interface ContributorTracker {
	/**
	 * 
	 * @param contributor
	 *            The instance of <code>IContributor</code> that will be added
	 */
	public void uiContributorAdded(Contributor contributor);

	/**
	 * 
	 * @param contributor
	 *            The instance of <code>IContributor</code> that will be removed
	 */
	public void uiContributorRemoved(Contributor contributor);
}
