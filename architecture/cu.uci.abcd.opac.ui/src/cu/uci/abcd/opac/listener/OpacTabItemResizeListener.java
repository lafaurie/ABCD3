package cu.uci.abcd.opac.listener;

//import java.util.Map;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
/*
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.opac.contribution.MainContent;
import cu.uci.abcd.opac.contribution.OpacContentProvider;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IContributorService;
*/
import cu.uci.abos.util.api.IServiceProvider;

public class OpacTabItemResizeListener implements Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Cache size
//	private int width = -1;
	IServiceProvider serviceProvider;
	String filter;

	public OpacTabItemResizeListener(IServiceProvider serviceProvider,
			String string) {
		this.serviceProvider = serviceProvider;
		filter = string;
	}

	@Override
	public void handleEvent(Event arg0) {
//		OpacContentProvider contentProvider = serviceProvider
			//	.get(OpacContentProvider.class);
		
	//	final IContributorService pageService = serviceProvider
	//			.get(IContributorService.class);
	//	Map<String, IContributor> ttMap = ((OpacContributorServiceImpl) pageService)
	//			.getContributorMap();
	//	MainContent mainContent = (MainContent) ((OpacContributorServiceImpl) pageService)
	//			.getContributorMap().get("MainContentID");
	/*	Text texto = mainContent.getSearchBox();
		String currentString = mainContent.getSearchBox().getText();
		mainContent.getSearchBox().setText(currentString + filter);
		String ttString = "";*/
	}

}
