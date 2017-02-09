package cu.uci.abcd.cataloguing.listener;

import java.util.List;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.IAuthoritiesRecord;
import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.cataloguing.IExemplaryRecord;
import cu.uci.abcd.cataloguing.ILoanObjectCreation;
import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.AllRecordsView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.template.util.BibliographicConstant;
import cu.uci.abos.widget.template.util.Util;

public class EventDeleteRecord implements Listener{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private ProxyController controller;
	private Record record;
	private String dataBaseName;
	private Browser browser;
	private long firstRecordMFN;
	private Text mfnText;
	private Combo pftCombo;
	private IJisisDataProvider service;
	private String isisDefHome;
	private Label recordsCountLabel;
	private ToolItem editRecord;
	private ToolItem addCopies;
	private ToolItem processingExemplary;
	private ToolItem deleteRecord;
	private ToolItem searchRecord;
	private ToolItem start;
	private ToolItem back;
	private ToolItem toward;
	private ToolItem last;
	private AllRecordsView allRecordsView;
	private boolean canDelete = false;

	public EventDeleteRecord(ProxyController controller, String dataBaseName, Browser browser,
			Text mfnText, Combo pftCombo, Label recordsCountLabel,
			ToolItem editRecord, ToolItem addCopies, ToolItem processingExemplary, 
			ToolItem deleteRecord, ToolItem searchRecord, ToolItem start, ToolItem back,
			ToolItem toward, ToolItem last, AllRecordsView allRecordsView){
        this.browser = browser;
		this.controller = controller;
		this.service = controller.getDataBaseManagerService().getService();
		this.dataBaseName = dataBaseName;
		this.mfnText = mfnText;
		this.pftCombo = pftCombo;
		this.isisDefHome = Util.getDefHome();
		this.recordsCountLabel = recordsCountLabel;
		this.editRecord = editRecord;
		this.addCopies = addCopies;
		this.processingExemplary = processingExemplary;
		this.deleteRecord = deleteRecord;
		this.searchRecord = searchRecord;
		this.start = start;
		this.back = back;
		this.toward = toward;
		this.last = last;
		this.allRecordsView = allRecordsView;
	}

	@Override
	public void handleEvent(Event arg0) {

		MessageDialogUtil.openQuestion(browser.getShell(), "Pregunta", "¿Deseas eliminar el elemento?", new DialogCallback() {

			/**
			 * Created by Basilio Puentes Rodríguez
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void dialogClosed(int arg0) {

				if(arg0 == 0){
					firstRecordMFN = allRecordsView.getFirstRecord().getMfn();
					record = allRecordsView.getCurrentRecord();
					
					String controlNumber = null;
					try {
						controlNumber = record.getField(BibliographicConstant.CONTROL_NUMBER).getStringFieldValue();
					} catch (DbException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(controlNumber != null){
						
						IExemplaryRecord exemplaryRecord = controller.getExemplaryRecordService();
						ILoanObjectCreation loanObjectCreation = controller.getLoanObjectCreationService();
						
						List<LoanObject> loanObjects = loanObjectCreation.findAllByControlNumber(controlNumber);
						
						boolean canRemove = exemplaryRecord.canEditAndRemove(loanObjects);
						
						if(canRemove){
							long recordsCount = -1;
							long backMFN = record.getMfn();
							
							IDataBaseManager dataBaseManager = controller.getDataBaseManagerService();

							//removedRecord
							if(dataBaseName.equals(BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE)){
								canDelete = exemplaryRecord.deleteExemplaryRecord(dataBaseManager, record, dataBaseName, loanObjectCreation);
								recordsCount = service.totalRecords(dataBaseName, isisDefHome);
							}
							else{
								IAuthoritiesRecord authoritiesRecord = controller.getAuthoritiesRecordService();
								authoritiesRecord.deleteAuthoritiesRecord(dataBaseManager, record, dataBaseName);
								recordsCount = service.totalRecords(dataBaseName, isisDefHome);
							}
							
							if(canDelete){
								//updates first and last record
								allRecordsView.setFirstRecord(null);
								allRecordsView.setLastRecord(null);
								
								try {						
									allRecordsView.setFirstRecord(service.getFirstRecord(dataBaseName, isisDefHome));
									allRecordsView.setLastRecord(service.getLastRecord(dataBaseName, isisDefHome));
									
								} catch (JisisDatabaseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								//update view
								Record currentRecord = null;
								
								if(recordsCount > 0){
									//removed the first record
									if(backMFN - 1 < firstRecordMFN){
										while(currentRecord == null){
											backMFN += 1;
											currentRecord = getRecord(backMFN);
										}
									}
									else{
										while(currentRecord == null){
											backMFN -= 1;
											currentRecord = getRecord(backMFN);
										}
									}
									
									long recordsCountU = service.totalRecords(dataBaseName, isisDefHome);
									AllRecordsView.recordsCountLabel.setText(String.valueOf(recordsCountU));
									
									//show
									String htmlString = "";
									List<String> dataBaseFormats = null;
									try {
										dataBaseFormats = service.getDatabaseFormats(dataBaseName, isisDefHome);
										String format = dataBaseFormats.get(0);
										
										FormattedRecord formattedRecord = service.getFormattedRecord(dataBaseName, currentRecord, format, isisDefHome);
										htmlString = formattedRecord.getRecord();
										
									} catch (JisisDatabaseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									//updates currentRecord
									allRecordsView.setCurrentRecord(currentRecord);
									
									//updates mfnText
						            mfnText.setText(String.valueOf(backMFN));
						            pftCombo.select(0);
						            
						            //updates label
						            recordsCountLabel.setText(String.valueOf(recordsCount));
						           
						            //updates navigateBar 
						           long firstMFN = allRecordsView.getFirstRecord().getMfn();
						           long lastMFN = allRecordsView.getLastRecord().getMfn();
						           
						           recordsCount = service.totalRecords(dataBaseName, isisDefHome);
						           
						           if(recordsCount == 1){
						        	   start.setEnabled(false);
						        	   back.setEnabled(false);
						        	   toward.setEnabled(false);
						        	   last.setEnabled(false);
						           }
						           else{
						        	   if(currentRecord.getMfn() == firstMFN){
						        		   start.setEnabled(false);
							        	   back.setEnabled(false);
							        	   toward.setEnabled(true);
							        	   last.setEnabled(true);
						        	   }
						        	   else if(currentRecord.getMfn() == lastMFN){
						        		   start.setEnabled(true);
							        	   back.setEnabled(true);
							        	   toward.setEnabled(false);
							        	   last.setEnabled(false);
						        	   }
						           }
						           
									browser.setText(htmlString);
									
									browser.getShell().layout(true, true);
									browser.getShell().redraw();
									browser.getShell().update();
									
								}
								//la base de datos tenia un solo registro
								else{
									allRecordsView.setFirstRecord(null);
									allRecordsView.setLastRecord(null);
									allRecordsView.setCurrentRecord(null);
									
									//updates mfnText
									mfnText.setText("0");
									mfnText.setEnabled(false);
							        pftCombo.setEnabled(false);
							        
							        //updates label
							        recordsCountLabel.setText("La base de datos no tiene registros");
							        
							        //disable paginator
							        start.setEnabled(false);
							        back.setEnabled(false);
							        toward.setEnabled(false);
							        last.setEnabled(false);
							       
							        //allow only the option Add Register
									editRecord.setEnabled(false);
									
									if(addCopies != null)
										addCopies.setEnabled(false);
									
									if(processingExemplary != null)
										processingExemplary.setEnabled(false);
									
									deleteRecord.setEnabled(false);
									
									if(searchRecord != null)
									searchRecord.setEnabled(false);
									
									browser.setText("La base de datos " +dataBaseName+ ", no tiene registros.");
							     }
							
							}
						}
						else{
							RetroalimentationUtils.showErrorShellMessage("El registro posee alguna copia prestada y no puede ser eliminado.");
						}
					}
					else{
						RetroalimentationUtils.showErrorShellMessage("El registro no posee número de control.");
					}
					
				}
			}
		});
	}
	
	private Record getRecord(long mfn){
		Record response = null;
		 try {
			   response = service.findByMfn(mfn, dataBaseName, isisDefHome);
			 } catch (JisisDatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return response;
	}
}
