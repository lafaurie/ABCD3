package cu.uci.abcd.management.db.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;

public class Administrar_Lista_Seleccion extends ContributorPage implements Contributor{

	@Override
	public Control createUIControl(Composite arg0) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "manageSelectionListID";
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String contributorName() {
		// TODO Auto-generated method stub
	 return "Administrar Lista de Selecci�n";
	}

	@Override
	public void setViewController(ViewController arg0) {
		// TODO Auto-generated method stub
		
	}

}
