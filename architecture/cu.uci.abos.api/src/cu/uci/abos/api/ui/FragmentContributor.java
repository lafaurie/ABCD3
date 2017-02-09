/*
 * @(#)IFragmentContributor.java 1.0.0 27/11/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.api.ui;

/**
 * This interface represents a fragment of common interfaces controls
 * 
 * @author Victor Marin Martinez
 * @version 1.0.0 27/11/2014
 * 
 */

import org.eclipse.swt.widgets.Control;

public interface FragmentContributor extends BaseContributor {

	/**
	 * 
	 * @return The control that matches with the key passed as method parameter
	 * 
	 */

	public Control getControl(String control);

}
