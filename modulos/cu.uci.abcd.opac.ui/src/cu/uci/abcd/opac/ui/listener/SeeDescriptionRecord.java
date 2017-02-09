package cu.uci.abcd.opac.ui.listener;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.unesco.jisis.corelib.record.Record;

public class SeeDescriptionRecord implements Listener {
	private static final long serialVersionUID = 1L;	
	
	Record record;
	
	public SeeDescriptionRecord(Record record) {
		this.record = record;		
	}
	
	@Override
	public void handleEvent(Event arg0) {
		
		

	}

}
