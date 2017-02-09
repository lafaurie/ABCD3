/*
 * @(#)IBaseContributor.java 1.0.0 24/11/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.api.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This interface represents a base contribution to the platform
 * 
 * @author Victor Marin Martinez
 * @version 1.0.0 24/11/2014
 * 
 */
public interface BaseContributor {

	/**
	 * 
	 * @param parent
	 *            Parent component over which goes to show
	 * 
	 * @return Result of creating the contribution
	 */
	public Control createUIControl(Composite parent);

	/**
	 * Gets the ID of the current contributor
	 * 
	 * @return <code>String</code> contributor ID
	 */
	public String getID();

	/**
	 * update the text of the contribution when the language changes
	 */
	public void l10n();

}
