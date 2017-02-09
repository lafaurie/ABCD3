package cu.uci.abcd.cataloguing.listener;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.Record;
import cu.uci.abcd.cataloguing.ui.AllRecordsView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.widget.template.util.Util;

public class EventChangeView implements Listener{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	private static final long serialVersionUID = 1L;
	
	private Browser browser;
	private IJisisDataProvider service;
	private String dataBaseName;
	private Combo combo;
	private AllRecordsView allRecordsView;
	
	public EventChangeView(Browser browser, IJisisDataProvider service,
			String dataBaseName, Combo combo, AllRecordsView allRecordsView){
		this.browser = browser;
		this.service = service;
		this.dataBaseName = dataBaseName;
		this.combo = combo;
		this.allRecordsView = allRecordsView;
	}

	@Override
	public void handleEvent(Event arg0) {
		String isisDefHome = Util.getDefHome();
		Record record = allRecordsView.getCurrentRecord();
		
		String format = combo.getText();

		if(format.equals("Vista marc"))
			format = "RAW";

		FormattedRecord formattedRecord = null;

		try {
			formattedRecord = service.getFormattedRecord(dataBaseName, record, format, isisDefHome);
		} catch (JisisDatabaseException e) {

			e.printStackTrace();
		}

		String htmlString = formattedRecord.getRecord();
        allRecordsView.setCurrentView(format);
		
		browser.setText(htmlString);

		browser.getShell().layout(true, true);
		browser.getShell().redraw();
		browser.getShell().update();
		
	}

}
