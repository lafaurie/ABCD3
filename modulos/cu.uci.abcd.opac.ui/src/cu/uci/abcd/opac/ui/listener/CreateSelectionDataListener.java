package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.contribution.OpacMenuBarProvider;
import cu.uci.abcd.opac.ui.contribution.Selection;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;

public class CreateSelectionDataListener implements Listener {

	private static final long serialVersionUID = 1L;

	ServiceProvider serviceProvider;
	RecordIsis record;
    
	public CreateSelectionDataListener(ServiceProvider serviceProvider, RecordIsis record) {
		this.serviceProvider = serviceProvider;
		this.record = record;
	}
    
	@Override
	public void handleEvent(Event arg0) {

		final ContributorService pageService = serviceProvider.get(ContributorService.class);

		Selection seleccion = (Selection) ((OpacContributorServiceImpl) pageService).getContributorMap().get("SelectionID");

		if (!seleccion.records.contains(record)) {
			seleccion.records.add(record);
     
			OpacMenuBarProvider opacMenuBarProvider = serviceProvider.get(OpacMenuBarProvider.class);
			opacMenuBarProvider.cantSelection = "" + seleccion.records.size();
			opacMenuBarProvider.l10n();
		}
	}
}