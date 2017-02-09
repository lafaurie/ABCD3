package cu.uci.abcd.opac.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.FormDatas;

public class MisEtiquetas implements IContributor {

	@Override
	public Control createUIControl(Composite parent) {
		
		
		Composite result = new Composite(parent, 0);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		result.setLayout(new FormLayout());	
		
		FormDatas.attach(result).atLeft(0).atRight(0).atTop(0).atBottom(0);
		
		Label lbconfig=new Label(result, 0);
		lbconfig.setText("Etiquetas Test...");
		FormDatas.attach(lbconfig).atTop(100).atLeft(200);
		
       
	    
		

		return result;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "MisEtiquetasID";
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canClose() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String contributorName() {
		// TODO Auto-generated method stub
		return "Mis Etiquetas";
	}

	@Override
	public void setViewController(IViewController arg0) {
		// TODO Auto-generated method stub

	}

}
