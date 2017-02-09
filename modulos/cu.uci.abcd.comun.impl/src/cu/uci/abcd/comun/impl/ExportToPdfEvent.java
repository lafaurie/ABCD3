package cu.uci.abcd.comun.impl;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.UrlLauncher;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class ExportToPdfEvent implements SelectionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		UrlLauncher launcher = RWT.getClient().getService(UrlLauncher.class);
		launcher.openURL(createDownloadUrl());		
	}
	
	private String createDownloadUrl() {
		StringBuilder url = new StringBuilder();
		url.append(RWT.getServiceManager().getServiceHandlerUrl("downloadServiceHandler"));
		url.append('&').append("filename").append('=').append("nombre.pdf");
		return url.toString();
	}

}
