package cu.uci.abos.platform.listener;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class LocaleResetListener implements Listener {

	private static final long serialVersionUID = 3416824929323594491L;

	@Override
	public void handleEvent(Event arg0) {
		RWT.setLocale(null);
	}

}
