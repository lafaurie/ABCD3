package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.contribution.SendRecomendation;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;

public class CreateRecommendListener implements Listener {

	private static final long serialVersionUID = 1L;

	ServiceProvider serviceProvider;
	RecordIsis record;

	public CreateRecommendListener(ServiceProvider serviceProvider, RecordIsis record) {
		this.serviceProvider = serviceProvider;
		this.record = record;
	}

	@Override
	public void handleEvent(Event arg0) {

		try {
	   		
				final ContributorService pageService = serviceProvider.get(ContributorService.class);

				SendRecomendation sendRecomendation  = (SendRecomendation) ((OpacContributorServiceImpl) pageService).getContributorMap().get("sendRecommendationID");

				sendRecomendation.setRecord(record);

				pageService.selectContributor("sendRecommendationID");		
				

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
