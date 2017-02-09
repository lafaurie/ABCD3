package cu.uci.abos.core.util;

import org.eclipse.swt.events.SelectionListener;

import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.listener.CancelContributorListener;
import cu.uci.abos.core.ui.listener.CloseContributorListener;

public class ContributorEventUtils {

	public static SelectionListener registerCancel(ContributorPage page) {
		return new CancelContributorListener(page);
	}

	public static SelectionListener registerClose(ContributorPage page) {
		return new CloseContributorListener(page);
	}

}
