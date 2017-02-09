package cu.uci.abos.widget.repeatable.field.util;

import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.core.util.FormDatas;

public class MyFieldUpLoad extends Composite {
	
	public MyFieldUpLoad(Composite parent, int style) {
		super(parent, style);
		
		this.setLayout(new FormLayout());
		
		direction = new Text(this, 0);
		FormDatas.attach(direction).withWidth(150).withHeight(10).atTopTo(parent, 0).atLeftTo(parent, 0);
		
		fileUpLoad = new FileUpload(this, SWT.MULTI|SWT.WRAP|SWT.PUSH);
		fileUpLoad.setText("Examinar");
		
		FormDatas.attach(fileUpLoad).atLeftTo(direction, 15).withHeight(20).withWidth(85).atTopTo(parent, 0);
		
		this.getShell().layout(true, true);
		this.getShell().redraw();
		this.getShell().update();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileUpload fileUpLoad;
	private Text direction;
	private byte[] byteArray = null;
	
	public byte[] getByteArray(){
		return this.byteArray;
	}
	
	public void setByteArray(byte[] byteArray){
		this.byteArray = byteArray;
	}
	
	public FileUpload getFileUpload(){return this.fileUpLoad;}
	public Text getDirection(){return this.direction;}

}
