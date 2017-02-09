package cu.uci.abos.demo;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;

public class LoginEntryPoint implements EntryPoint {

	
	
	
	

	/**
	 * @wbp.parser.entryPoint
	 */
	
	@Override
	public int createUI() 
	{
		Display display = new Display();
		Shell main = new Shell(display, SWT.NONE);
		GridLayout layout=new GridLayout();
		main.setLayout(layout);
		
		
		
		
		
		
		
		
		
	 main.setData(RWT.CUSTOM_VARIANT,"test");
		
		
		main.setMaximized(true);
		Composite parent=new Composite(main, SWT.BORDER);
		
				
		
		GridLayout gridLayout=new GridLayout(4, false);
		
		parent.setLayout(gridLayout);
		parent.setData(RWT.CUSTOM_VARIANT,"Login");
		//arent.setBounds(50, 50, 600, 400);
		
		
		
		
				
		
		
		
		
		
		
		GridData contenedor=new GridData(600, 400);
		GridData data1=new GridData();
		GridData data2=new GridData();
		GridData data4=new GridData();
		GridData data5=new GridData();
		GridData data6=new GridData();
		data4.widthHint=150;
		data2.widthHint=100;
		data1.widthHint=600;
		data1.heightHint=45;
		data1.horizontalSpan=4;
		data5.widthHint=297;
		data5.heightHint=44;
		data5.horizontalAlignment=GridData.CENTER;
		data5.horizontalSpan=4;
		data6.horizontalSpan=4;
		
		
		new Label(parent, SWT.NONE).setLayoutData(data1);
		//new Label(parent, SWT.NONE).setLayoutData(data5);	
	    Label label=new Label(parent,SWT.NONE);
	    label.setLayoutData(data5);
	    label.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
	    String src = RWT.getResourceManager().getLocation( "foo/icon.png" ); 
	    label.setText("<img width='297' height='44' src='" + src + "'/> ");
	   
		Label loginGraficaL=new Label(parent, SWT.NONE);		   
		loginGraficaL.setLayoutData(data6);
		loginGraficaL.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
	    String grafica = RWT.getResourceManager().getLocation( "login/grafica.png" ); 
	    loginGraficaL.setText("<img width='606' height='162' src='" + grafica + "'/> ");
		
		
	    
	    
	    
	    
	    
	    
		
		
		GridData data=new GridData(170, 24);		
		data.horizontalSpan=4;
		data.horizontalAlignment=GridData.CENTER;
		Text text=new Text(parent, SWT.NORMAL);
		text.setMessage("usuario");	
		text.setLayoutData(data);
		
		
		
		GridData Clavegd=new GridData(170,24);
		Clavegd.horizontalSpan=4;
		Clavegd.horizontalAlignment=GridData.CENTER;
		Text clave=new Text(parent, SWT.PASSWORD);	
		clave.setMessage("contraseña");	
		clave.setLayoutData(Clavegd);
	
		  
		GridData chk_recordar=new GridData();
		
		chk_recordar.horizontalSpan=4;
		chk_recordar.horizontalAlignment=GridData.CENTER;
		chk_recordar.verticalIndent=10;
		
		
		Button chkrecordar=new Button(parent, SWT.CHECK);
		chkrecordar.setText(" Recordar contraseña");
		chkrecordar.setLayoutData(chk_recordar);
		
		
		
		
		
		
		GridData btnEntrar=new GridData();
		
		btnEntrar.verticalIndent=10;
		btnEntrar.horizontalAlignment=GridData.CENTER;
		btnEntrar.horizontalSpan=4;
		final Button button=new Button(parent, SWT.PUSH);
		button.setText("Entrar");
		button.setLayoutData(btnEntrar);
		button.addSelectionListener( new SelectionAdapter() {
		      @Override
		      public void widgetSelected( SelectionEvent e ) {
		         
		    	  JavaScriptExecutor executor = RWT.getClient().getService( JavaScriptExecutor.class );
		    	  executor.execute( "window.location='http://localhost:8080/administrador';" );
		        
		      }
		    } );
		
		
		
		
		
		
		GridData lbmensaje=new GridData();
		lbmensaje.horizontalSpan=4;
		lbmensaje.verticalIndent=10;
		lbmensaje.horizontalAlignment=GridData.CENTER;
		
		Label mensajeLabel=new Label(parent, SWT.CENTER);
		mensajeLabel.setText("¿Has olvidado tu contraseña?");
		mensajeLabel.setLayoutData(lbmensaje);
		
		
		
		main.layout();
		main.open();
		return 0;
	}
	
	

}
