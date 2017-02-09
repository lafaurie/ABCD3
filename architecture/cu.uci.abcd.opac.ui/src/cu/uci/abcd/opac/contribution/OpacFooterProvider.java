/*
 * @(#)FooterProvider.java 1.0.0 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abcd.opac.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;

import cu.uci.abos.ui.api.IPlatformContributor;

public class OpacFooterProvider implements IPlatformContributor {
	public static final String FOOTER_CONTROL = OpacFooterProvider.class.getName()
			+ "#FOOTER";
	public static final int FOOTER_HEIGHT =15 ;
	// NOTE: this value reflects the height of the footer_separator background
	// image set via css
	private static final int SEPARATOR_HEIGHT = 8;

	private String getVersionInfo() {
		Bundle bundle = FrameworkUtil.getBundle(getClass()).getBundleContext()
				.getBundle();
		String name = bundle.getHeaders().get("Bundle-Name");
		Version version = bundle.getVersion();
		return name + " (" + version + ")";
	}

	@Override
	public Control createUIControl(Composite parent) {
		Composite result = new Composite(parent, SWT.BORDER);
		result.setLayout(new FormLayout());

		result.setData(RWT.CUSTOM_VARIANT, "footerComposite");

		Label versionInfo = new Label(result, SWT.NONE);
		versionInfo.setData(RWT.CUSTOM_VARIANT, "customLabel");
		versionInfo.setText(getVersionInfo());
		versionInfo.pack();
		FormData versionInfoData = new FormData();
		versionInfo.setLayoutData(versionInfoData);
		Point size = versionInfo.getSize();
		versionInfoData.top = new FormAttachment(50, -(size.y / 2)
				+ (SEPARATOR_HEIGHT / 2));
		versionInfoData.left = new FormAttachment(50, -(size.x / 2));
		
		return result;
	}

	@Override
	public String getID() {
		return FOOTER_CONTROL;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub

	}
}