package cu.uci.abos.widget.repeatable.field.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.rap.fileupload.DiskFileUploadReceiver;
import org.eclipse.rap.fileupload.FileDetails;
import org.eclipse.rap.fileupload.FileUploadEvent;
import org.eclipse.rap.fileupload.FileUploadHandler;
import org.eclipse.rap.fileupload.FileUploadListener;
import org.eclipse.rap.rwt.service.ServerPushSession;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.repeatable.field.ControlType;

public class CreateControlType {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private Control control;
	private Composite content;
	private Label label;
	private ControlType controlType;
	private ServerPushSession pushSession;
	private String urlFile= "";
	private boolean simple;
	private String text;

	public CreateControlType(Control control, ControlType controlType) {
		this.control = control;
		this.controlType = controlType;
	}

	public Control create(String defaultValue, Composite content, Label label, boolean simple,
			String text, String[] comboList){
		
		this.simple = simple;
		this.text = text;
		this.content = content;
		this.label = label;

		switch (controlType) {
		case Text:
			control = new Text(content, SWT.PUSH);
			((Text)control).setEditable(true);

			if(defaultValue != null)
				((Text)control).setText(defaultValue);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(10);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(10);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(250).withHeight(10);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case Combo:
			control = new Combo(content, SWT.READ_ONLY);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(270).withHeight(23);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(content, 10).withWidth(270).withHeight(23);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(270).withHeight(23);

			int count = comboList.length;

			((Combo)control).add("-seleccione-");

			int selectPosition = 0;

			for (int i = 0; i < count; i++) {
				String item = comboList[i].replaceAll("\n","");
				((Combo)control).add(item);
				if(defaultValue != null && !defaultValue.equals("")){
					if(defaultValue.equals(item)){
						selectPosition = i+1;
					}
				}
			}

			((Combo)control).select(selectPosition);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case DateTime:
			if(defaultValue.equals("") || defaultValue == null)
			control = new MyDateTime(content, 0);
			else{
				control = new Text(content, 0);
				((Text)control).setText(defaultValue);
			}		
			if(!simple){
				if(!text.equals("")){
					if(defaultValue.equals("") || defaultValue == null)
						FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
					else
						FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(10);
				}
				else{
					if(defaultValue.equals("") || defaultValue == null)
						FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
					else
						FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(10);
				}
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(365).withHeight(23);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case Date:
			if(defaultValue.equals("") || defaultValue == null)
			control = new MyDate(content, 0);
			else{
				control = new Text(content, 0);
				((Text)control).setText(defaultValue);
			}		

			if(!simple){
				if(!text.equals("")){
					if(defaultValue.equals("") || defaultValue == null)
						FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
					else
						FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(10);
				}
				else{
					if(defaultValue.equals("") || defaultValue == null)
						FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
					else
						FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(10);
				}
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(365).withHeight(23);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case Time:
			control = new MyTime(content, 0);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(23);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(365).withHeight(23);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case TextArea:
			control = new Text(content, SWT.MULTI|SWT.WRAP);

			if(defaultValue != null)
				((Text)control).setText(defaultValue);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(100);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(content, 10).withWidth(250).withHeight(100);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(350).withHeight(100);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;

		case NW:
			control = new Text(content, SWT.MULTI|SWT.WRAP);
			((Text)control).setEditable(false);

			if(defaultValue != null)
				((Text)control).setText(defaultValue);

			if(!simple){
				if(!text.equals(""))
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(250).withHeight(10);
				else
					FormDatas.attach(control).atTopTo(content, 10).atLeftTo(content, 10).withWidth(250).withHeight(10);
			}
			else
				FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(350).withHeight(100);

			if(!simple)
				FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
			else
				FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);

			break;
			
		case FileUpLoad:
			  showFileUpLoad();

			break;	

		default:
			break;
		}

		return control;

	}
	
	private String startUploadReceiver() {
		final DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
		FileUploadHandler uploadHandler = new FileUploadHandler(receiver);
		
		uploadHandler.addUploadListener(new FileUploadListener() {

			public void uploadProgress( FileUploadEvent event ) {
				System.out.println("SUBIENDOOOOOOOOOO");		      
			}

			public void uploadFailed( FileUploadEvent event ) {
				addToLog( "upload failed: " + event.getException() );
			}

			public void uploadFinished( FileUploadEvent event ) {
				System.out.println( "PPATH"+receiver.getTargetFiles()[0].getAbsolutePath());
				urlFile = receiver.getTargetFiles()[0].getAbsolutePath();
				for( FileDetails file : event.getFileDetails() ) {
					addToLog( "received: " + file.getFileName() );          
				}
					showImage();
			}
		} );

		return uploadHandler.getUploadUrl();
	}
	
	private void addToLog( final String message ) {		   
		System.out.println(message);
		pushSession.stop();
	}
	
	private void showImage() {

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				((MyFieldUpLoad)control).getDirection().setText(urlFile);
				//convert pdf to bytes array
				byte[] byteArray = null;
				byteArray = convertPdfToByteArray(urlFile);
				((MyFieldUpLoad)control).setByteArray(byteArray);
				pushSession.stop();
			}
		});
	}
	
	//este es el mio
	private void showFileUpLoad(){
		
		control = new MyFieldUpLoad(content, 0);
		
		if(!simple){
			if(!text.equals("")){
				FormDatas.attach(control).atTopTo(content, 10).atLeftTo(label, 10).withWidth(365).withHeight(30);
			}
			else{
				FormDatas.attach(control).atTopTo(content, 10).atLeftTo(content, 10).withWidth(365).withHeight(10);
			}
		}
		else
			FormDatas.attach(control).atTopTo(content, 5).atLeftTo(label, 10).withWidth(350).withHeight(100);

		if(!simple)
			FormDatas.attach(label).atTopTo(content, 12).atRightTo(control, 10);
		else
			FormDatas.attach(label).atTopTo(content, 10).atRightTo(control, 10);
		
		FileUpload fileUpLoad = ((MyFieldUpLoad)control).getFileUpload();
		
		fileUpLoad.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileUpload fileUpLoad = ((MyFieldUpLoad)control).getFileUpload();
				boolean pdf = (fileUpLoad).getFileName().contains(".pdf");
				if (pdf) {	
					final String url = startUploadReceiver();
					pushSession = new ServerPushSession();
					pushSession.start();
					fileUpLoad.submit(url);
					refresh();
					
				} else {
					RetroalimentationUtils.showErrorMessage(control.getShell(),
							"El archivo seleccionado no es un pdf.");					
				}
			}
		});
	}
	
	private void refresh() {
		content.layout(true, true);
		content.redraw();
		content.update();
	}
	
	private byte[] convertPdfToByteArray(String sourcePath) {

	      byte[] byteArray=null;
	            try {
	                  InputStream inputStream = new FileInputStream(sourcePath);

	                  String inputStreamToString = inputStream.toString();
	                  byteArray = inputStreamToString.getBytes();

	                  inputStream.close();
	            } catch (FileNotFoundException e) {
	                 System.out.println("File Not found"+e);
	            } catch (IOException e) {
	            	System.out.println("IO Ex"+e);
	            }
	            return byteArray;
	      }
}
