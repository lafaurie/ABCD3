package cu.uci.abcd.cataloguing.listener;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class EventAcquisitionType implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;
	private CTabFolder acquisitionType;
	private Combo acquisitionTypeCombo;

	public EventAcquisitionType(CTabFolder acquisitionType, Combo acquisitionTypeCombo){
		this.acquisitionType = acquisitionType;
		this.acquisitionTypeCombo = acquisitionTypeCombo;
	}

	@Override
	public void handleEvent(Event arg0) {

		String value = acquisitionTypeCombo.getText();

		if(!value.equals("Seleccione")){

			if(value.equals("Compra")){
				acquisitionType.setSelection(0);

				CTabItem tabItemSelected = acquisitionType.getItem(0);

				Composite composite = (Composite)tabItemSelected.getControl();

				Control[] controls = composite.getChildren();
				int controlsSize = controls.length;

				for (int i = 0; i < controlsSize; i++) {
					Control control = controls[i];
					control.setEnabled(true);
					control.forceFocus();
				}

				tabItemSelected.setControl(composite);

				acquisitionType.getShell().layout(true, true);
				acquisitionType.getShell().redraw();
				acquisitionType.getShell().update();
			}
			else if(value.equals("Donación")){
				acquisitionType.setSelection(1);
			}
			else if(value.equals("Canje")){
				acquisitionType.setSelection(2);
			}
		}
		else
			acquisitionType.setUnselectedCloseVisible(false);
	}

}
