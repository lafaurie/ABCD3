/*
 * @(#)IContributor.java 1.2.0 01/11/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.api.ui;

/**
 * This interface represents a contribution to the platform
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.2.0 24/11/2014
 * 
 */
public interface Contributor extends BaseContributor {
	/**
	 * 
	 * @return The name of the requirement that contributes. The name most be
	 *         unique
	 */

	public String contributorName();

	/**
	 * 
	 * @return True if can close false if not
	 */
	public boolean canClose();

	/**
	 * 
	 * @param controller
	 *            Represents the presentation controller
	 */
	public void setViewController(ViewController controller);
	
}
