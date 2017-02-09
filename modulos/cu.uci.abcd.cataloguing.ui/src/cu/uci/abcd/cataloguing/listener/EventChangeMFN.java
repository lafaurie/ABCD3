package cu.uci.abcd.cataloguing.listener;

import java.util.List;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.ui.AllRecordsView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.template.util.Util;

public class EventChangeMFN implements KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Browser browser;
	private String dataBaseName;
	private IJisisDataProvider service;
	private Text mfnText;
	private Combo combo;
	private long firstMFN;
	private long lastMFN;
	private long currentMFN;
	private ToolItem start;
	private ToolItem toward;
	private ToolItem last;
	private ToolItem back;
	private AllRecordsView allRecordsView;
	
	public EventChangeMFN(Browser browser, String dataBaseName, IJisisDataProvider service,
			Text mfnText, Combo combo, ToolItem start, ToolItem toward, ToolItem last, ToolItem back,
			AllRecordsView allRecordsView){
		this.browser = browser;
		this.dataBaseName = dataBaseName;
		this.service = service;
		this.mfnText = mfnText;
		this.combo = combo;
		this.start = start;
		this.toward = toward;
		this.last = last;
		this.back = back;
		this.allRecordsView = allRecordsView;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent service) {
		
		if(service.keyCode == 13){
		  firstMFN = allRecordsView.getFirstRecord().getMfn();
		  lastMFN = allRecordsView.getLastRecord().getMfn();
		  currentMFN = allRecordsView.getCurrentRecord().getMfn();
			
          String number = mfnText.getText();
          
          boolean validate = false;
          long mfn = -1;
          
          try {
        	  mfn = Long.parseLong(number);
        	  validate = true;
			
		} catch (Exception e) {
			validate = false;
			RetroalimentationUtils.showErrorShellMessage(
					"El MFN debe ser un número entero comprendido entre "+String.valueOf(firstMFN)+" y "+String.valueOf(lastMFN)+".");
		}
         
          if(validate){
        	  
        	  if(mfn >= firstMFN && mfn <= lastMFN){
        		  
        		  String isisDefHome = Util.getDefHome();
        		  Record record = null;
        		  
        		  try {
					record = this.service.findByMfn(mfn, dataBaseName, isisDefHome);
				} catch (JisisDatabaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		  
        		if(record == null){
        			 RetroalimentationUtils.showErrorShellMessage(
           					"No existe ningún registro con el MFN: "+String.valueOf(mfn)+".");
        		}
        		else{
        			String htmlString = "";
          			List<String> dataBaseFormats = null;
          			try {
          				dataBaseFormats = this.service.getDatabaseFormats(dataBaseName, isisDefHome);
          				String format = dataBaseFormats.get(0);
          				
          				FormattedRecord formattedRecord = this.service.getFormattedRecord(dataBaseName, record, format, isisDefHome);
          				htmlString = formattedRecord.getRecord();
          				
          			} catch (JisisDatabaseException e) {
          				// TODO Auto-generated catch block
          				e.printStackTrace();
          			}
          			
          			allRecordsView.setCurrentRecord(record);
          			
                    mfnText.setText(String.valueOf(mfn));
                    combo.select(0);
                    
                    if(firstMFN != lastMFN){
                    	 if(record.getMfn() == firstMFN){
                          start.setEnabled(false);
                          back.setEnabled(false);
                          toward.setEnabled(true);
                          last.setEnabled(true);
                         }
                         else if(record.getMfn() == lastMFN){
                          start.setEnabled(true);
                          back.setEnabled(true);
                          toward.setEnabled(false);
                          last.setEnabled(false);
                         }
                         else{
                          start.setEnabled(true);
                          back.setEnabled(true);
                          toward.setEnabled(true);
                          last.setEnabled(true);
                         }
                    }
                    else{
                    	start.setEnabled(false);
                        back.setEnabled(false);
                        toward.setEnabled(false);
                        last.setEnabled(false);
                    }
                    
          			browser.setText(htmlString);
          			
          			browser.getShell().layout(true, true);
          			browser.getShell().redraw();
          			browser.getShell().update();
        		}
        		  
        	  }
        	  else{
        		  mfnText.setText(String.valueOf(currentMFN));
        		  if(firstMFN != lastMFN)
        		  RetroalimentationUtils.showErrorShellMessage(
      					"El MFN debe ser un número entero comprendido entre "+String.valueOf(firstMFN)+" y "+String.valueOf(lastMFN)+".");
        		  else
        			  RetroalimentationUtils.showErrorShellMessage(
            					"El único valor de MFN es: "+String.valueOf(lastMFN)+".");
        	  }   
          }      
	   }
	}
}
