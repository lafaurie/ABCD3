package cu.uci.abos.platform.listener;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.UrlLauncher;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import cu.uci.abos.api.ui.ContributorService;

public class HelpListener implements SelectionListener {

	private static final long serialVersionUID = 4592298462110732664L;
	private ContributorService contributorService;

	public HelpListener(ContributorService contributorService) {
		super();
		this.contributorService = contributorService;
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		UrlLauncher service = RWT.getClient().getService(UrlLauncher.class);
		if (service != null) {
			//FIXME OIGRES SACAR AFUERA DIRECCION DE AYUDA, PARA HACERLA CONFIG
			service.openURL("http://localhost:8080/help/#"+RWT.getLocale() +"/"+ contributorService.getCurrentContributorID());
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		// TODO Auto-generated method stub
		
	}


}
