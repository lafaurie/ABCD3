package cu.uci.abcd.cataloguing.listener;

import java.util.List;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.ui.AllRecordsView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.l10n.cataloguing.AbosMessages;
import cu.uci.abos.widget.template.util.Util;

public class EventLastRecord implements Listener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Browser browser;
	private String dataBaseName;
	private IJisisDataProvider service;
	private Text mfnText;
	private Record lastRecord;
	private ToolItem start;
	private ToolItem toward;
	private ToolItem last;
	private ToolItem back;
	private AllRecordsView allRecordView;
	
	public EventLastRecord(Browser browser, String dataBaseName, IJisisDataProvider service,
			Text mfnText, ToolItem start, ToolItem toward, ToolItem last,
			ToolItem back, AllRecordsView allRecordView){
		this.browser = browser;
		this.dataBaseName = dataBaseName;
		this.service = service;
		this.mfnText = mfnText;
		this.start = start;
		this.toward = toward;
		this.last = last;
		this.back = back;
		this.allRecordView = allRecordView;
	}

	@Override
	public void handleEvent(Event arg0) {
		String currentView = allRecordView.getCurrentView();
		if(currentView.equals(AbosMessages.get().VALUE_COMBO_MARC_VIEW))
			currentView = "RAW";
		
		lastRecord = allRecordView.getLastRecord();
		String isisDefHome = Util.getDefHome();
		String htmlString = null;
		
		List<String> dataBaseFormats = null;
		try {
			dataBaseFormats = service.getDatabaseFormats(dataBaseName, isisDefHome);
			int size = dataBaseFormats.size();
			int position = -1;
			for (int i = 0; i < size; i++) {
				String value = dataBaseFormats.get(i);
				if(value.equals(currentView)){
					position = i;
					break;
				}
			}
			
			String format = null;
			
			if(position != -1)
			format = dataBaseFormats.get(position);
			else
				format = dataBaseFormats.get(0);
			
			FormattedRecord formattedRecord = service.getFormattedRecord(dataBaseName, lastRecord, format, isisDefHome);
			htmlString = formattedRecord.getRecord();
			
		} catch (JisisDatabaseException e) {
			e.printStackTrace();
		}
		
		allRecordView.setCurrentRecord(lastRecord);
		
		mfnText.setText(String.valueOf(lastRecord.getMfn()));
		
		//updatesNavigateBar
        back.setEnabled(true);
		start.setEnabled(true);
        	
		last.setEnabled(false);
        toward.setEnabled(false);
        
		browser.setText(htmlString);
		
		browser.getShell().layout(true, true);
		browser.getShell().redraw();
		browser.getShell().update();	
	}
}
