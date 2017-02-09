package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.contribution.RegisterScore;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;

public class CreateRatingListener implements Listener{
	private static final long serialVersionUID = 1L;
	
	private ServiceProvider serviceProvider;
	private RecordIsis record;
	private float ponderation;
	
	
	
	public CreateRatingListener(ServiceProvider serviceProvider, RecordIsis record, float ponderation) {
		this.serviceProvider = serviceProvider;
		this.record = record;
		this.ponderation = ponderation;
	}
	
	@Override	
	public void handleEvent(Event arg0) {
		
		final ContributorService pageService = serviceProvider.get(ContributorService.class);


			RegisterScore registerScore = (RegisterScore) ((OpacContributorServiceImpl) pageService).getContributorMap().get("RegisterScoreID");

			registerScore.setRecord(record);
			registerScore.setPonderation(ponderation);

			pageService.selectContributor("RegisterScoreID");

	
	}

}
