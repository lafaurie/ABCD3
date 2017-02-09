package cu.uci.abcd.management.db.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;

public class RegisterDatabaseSelectionTable implements FragmentContributor{

	PagePainter painter;
	Composite up;
	
	@Override
	public Control createUIControl(Composite parent) {
		painter = new FormPagePainter();
		up = new Composite(parent, SWT.NONE);
		painter.addComposite(up);
		up.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Label cuatro = new Label(up, SWT.NONE);
		cuatro.setText("cuatro");
		painter.addHeader(cuatro);
		
		return up;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Control getControl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
