/*
 * @(#)ILayoutContext.java 1.0.0 22/06/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.api.ui;

import org.eclipse.swt.widgets.Control;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0
 * 
 */
public interface LayoutContext {
	/**
	 * 
	 * @param controlKey
	 * @return
	 */
	Control getControl(String controlKey);
}
