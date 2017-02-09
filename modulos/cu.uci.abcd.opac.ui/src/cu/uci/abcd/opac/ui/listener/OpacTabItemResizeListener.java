package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.api.util.ServiceProvider;

public class OpacTabItemResizeListener implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Cache size
//	private int width = -1;
	ServiceProvider serviceProvider;
	String filter;

	public OpacTabItemResizeListener(ServiceProvider serviceProvider,
			String string) {
		this.serviceProvider = serviceProvider;
		filter = string;
	}

	@Override
	public void handleEvent(Event arg0) {
		/*OpacContentProvider contentProvider = serviceProvider
				.get(OpacContentProvider.class);*/
		
		/*final ContributorService pageService = serviceProvider
				.get(ContributorService.class);*/
		/*Map<String, IContributor> ttMap = ((OpacContributorServiceImpl) pageService)
			.getContributorMap();*/
	/*	MainContent mainContent = (MainContent) ((OpacContributorServiceImpl) pageService)
				.getContributorMap().get("MainContentID");
		Text texto = mainContent.getSearchText();
		String currentString = mainContent.getSearchText().getText();
		mainContent.getSearchText().setText(currentString + filter);*/
	
	}

}
