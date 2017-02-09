/*
 * @(#)ContentProvider.java 1.0.0 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abcd.opac.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abos.ui.api.IContributorService;
import cu.uci.abos.ui.api.IPlatformContributor;
import cu.uci.abos.util.api.IServiceProvider;

public class OpacContentProvider implements IPlatformContributor {
	public static final String CONTENT_CONTROL = OpacContentProvider.class
			.getName() + "#CONTENT";

	private final IServiceProvider serviceProvider;

	public OpacContentProvider(IServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	@Override
	public Control createUIControl(Composite parent) {
		Composite result = new Composite(parent, SWT.None);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		result.setLayout(new FillLayout());

		IContributorService pageService = serviceProvider
				.get(IContributorService.class);

		pageService.registerContentParent(result);
		return result;
	}

	@Override
	public String getID() {
		return CONTENT_CONTROL;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub

	}
}