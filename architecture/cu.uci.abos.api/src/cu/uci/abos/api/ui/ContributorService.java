/*
 * @(#)IContributorService.java 1.1.0 24/06/2014
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.api.ui;

import org.eclipse.swt.widgets.Composite;


/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @author Victor Marin Martinez
 * 
 * @version 1.1.0
 * 
 */
public interface ContributorService {
	/**
	 * Register the content parent when the contributors will be rendered
	 * 
	 * @param contentParent
	 *            The <code>Composite</code> instance container
	 */
	public void registerContentParent(Composite contentParent);

	/**
	 * Add <code>IContributorTracker</code> listener
	 * 
	 * @param moduleUIContributorTracker
	 *            The instance <code>IContributorTracker</code> that will be
	 *            added as listener of <code>IContributor</code>
	 */
	public void addContributorTracker(ContributorTracker contributorTracker);

	/**
	 * Remove <code>IContributorTracker</code> listener
	 * 
	 * @param moduleUIContributorTracker
	 *            The instance <code>IContributorTracker</code> that will be
	 *            removed as listener of <code>IContributor</code>
	 */
	public void removeContributorTracker(ContributorTracker contributorTracker);

	/**
	 * 
	 * @return All contributors ID
	 */
	public String[] getContributorsID();

	/**
	 * Change the content of the platform with the content of the selected
	 * contributor
	 * 
	 * 
	 * @param contributorName
	 *            The name of the contributor that will be be selected
	 */
	public void selectContributor(String contributorName);

	/**
	 * Change the content of the platform with the content of the selected
	 * contributor
	 * 
	 * 
	 * @param contributorName
	 *            The name of the contributor that will be be selected
	 * @param params
	 *            object to inject in contributor.
	 */
	public void selectContributor(String contributorName, Object... params);

	/**
	 * Select the default contributor
	 */
	public void selectDefaultContributor();

	/**
	 * Internationalize opened contributions when language change
	 */
	public void l10n();
	
	/**
	 * Get the current contributor ID
	 * @return current Contributor ID
	 */
	public String getCurrentContributorID();
	
	/**
	 * For refresh the user interface
	 */
	public void refresh();
}
