/*******************************************************************************
 * Copyright (c) 2012, 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package cu.uci.abos.core.validation;

import org.eclipse.rap.rwt.scripting.ClientListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

public class ClientCustomBehaviors {

	private static final String RESOURCES_PREFIX = "cu/uci/abos/core/validation/";

	ClientCustomBehaviors() {
		// prevent instantiation
	}

	public static void toUpperCase(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "UpperCase.js");
		ClientListener clientListener = new ClientListener(scriptCode);
		text.addListener(SWT.Verify, clientListener);
	}

	public static void toAlphaNumericsSpaces(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "AlphaNumericsSpaces.js");
		ClientListener clientListener = new ClientListener(scriptCode);
		text.addListener(SWT.Verify, clientListener);
	}

	public static void toAlphaSpaces(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "AlphaSpaces.js");
		ClientListener clientListener = new ClientListener(scriptCode);
		text.addListener(SWT.Verify, clientListener);
	}

	public static void toOnlyDigits(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "DigitsOnly.js");
		ClientListener clientListener = new ClientListener(scriptCode);
		text.addListener(SWT.Modify, clientListener);
	}
	
	public static void toOnlyDigitsAndPoints(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "DigitsAndPoinsOnly.js");
		ClientListener clientListener = new ClientListener(scriptCode);
		text.addListener(SWT.Modify, clientListener);
	}
	
	public static void toOnlyDigitsForce(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "DigitsOnlyForce.js");
		ClientListener clientListener = new ClientListener(scriptCode);
		text.addListener(SWT.Verify, clientListener);
	}

	public static void toNumber(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "NumberOnly.js");
		ClientListener clientListener = new ClientListener(scriptCode);
		text.addListener(SWT.Modify, clientListener);
	}

	public static void toNumberForce(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "NumberOnlyForce.js");
		ClientListener clientListener = new ClientListener(scriptCode);
		text.addListener(SWT.Verify, clientListener);
	}


	public static void toOnlyLetters(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "LettersOnly.js");
		ClientListener clientListener = new ClientListener(scriptCode);
		text.addListener(SWT.Modify, clientListener);
	}

	public static void toOnlyLettersForce(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "LettersOnlyForce.js");
		ClientListener clientListener = new ClientListener(scriptCode);
		text.addListener(SWT.Verify, clientListener);
	}

	public static void toEMail(Text text) {
		String scriptCode = ClientResourceLoaderUtil.readTextContent(RESOURCES_PREFIX + "Email.js");
		ClientListener listener = new ClientListener(scriptCode);
		text.addListener(SWT.Modify, listener);
	}

}
