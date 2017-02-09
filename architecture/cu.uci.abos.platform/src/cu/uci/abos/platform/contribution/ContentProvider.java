/*
 * @(#)ContentProvider.java 1.0.0 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.platform.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.util.ServiceProvider;

public class ContentProvider implements PlatformContributor {
	public static final String CONTENT_CONTROL = ContentProvider.class.getName() + "#CONTENT";

	private final ServiceProvider serviceProvider;

	public ContentProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	@Override
	public Control createUIControl(Composite parent) {
		Composite result = new Composite(parent, SWT.None);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		result.setLayout(new FillLayout());

		ContributorService pageService = serviceProvider.get(ContributorService.class);
		pageService.registerContentParent(result);

		return result;
	}

	@Override
	public String getID() {
		return CONTENT_CONTROL;
	}

	@Override
	public void l10n() {
	}
}