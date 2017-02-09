/*
 * @(#)AbstractFragmentContributor.java 1.0.1 27/11/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.api.ui;

/**
 * This class represents a abstract representation of a visual common control fragment
 * 
 * @author Victor Marin Martinez
 * @version 1.0.0 27/11/2014
 * 
 */

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Control;

public abstract class AbstractFragmentContributor implements FragmentContributor {

	public final Map<String, Control> controlMap = new HashMap<String, Control>();

	/**
	 * @see FragmentContributor
	 */
	public Control getControl(String control) {
		return controlMap.get(control);
	}

}
