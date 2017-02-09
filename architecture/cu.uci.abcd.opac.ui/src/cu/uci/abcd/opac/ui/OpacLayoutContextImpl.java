/*
 * @(#)LayoutContextImpl.java 1.0.0 26/06/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abcd.opac.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import cu.uci.abos.ui.api.ILayoutContext;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0
 * @see ILayoutContext
 */
public class OpacLayoutContextImpl implements ILayoutContext {

	private final Map<String, Control> controls;
	private Shell shell;

	public OpacLayoutContextImpl() {
		this.controls = new HashMap<String, Control>();
	}

	@Override
	public Control getControl(String controlKey) {
		return controls.get(controlKey);
	}

	public void setControl(String key, Control control) {
		controls.put(key, control);
		shell = control.getShell();
	}

	public void layoutShell() {
		if (shell != null) {
			shell.layout(true, true);
		}
	}

}
