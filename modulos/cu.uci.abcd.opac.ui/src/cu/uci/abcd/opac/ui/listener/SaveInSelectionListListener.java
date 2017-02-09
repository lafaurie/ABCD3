package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.contribution.SaveInSelectionList;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;

public class SaveInSelectionListListener implements Listener {
	private static final long serialVersionUID = 1L;

	ServiceProvider serviceProvider;
	RecordIsis record;

	public SaveInSelectionListListener(ServiceProvider serviceProvider, RecordIsis record) {
		this.serviceProvider = serviceProvider;
		this.record = record;
	}

	@Override
	public void handleEvent(Event arg0) {

		final ContributorService pageService = serviceProvider.get(ContributorService.class);

		SaveInSelectionList saveInSelectionList = (SaveInSelectionList) ((OpacContributorServiceImpl) pageService).getContributorMap().get("SaveInSelectionList");
   
		try {
			saveInSelectionList.record = record;
		} catch (Exception e) {
			e.printStackTrace();
		}

		pageService.selectContributor("SaveInSelectionList");
	}
}