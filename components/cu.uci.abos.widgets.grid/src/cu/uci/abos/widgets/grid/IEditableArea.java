package cu.uci.abos.widgets.grid;

import org.eclipse.swt.widgets.Composite;

public interface IEditableArea {

	Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager);
	
	Composite createButtons(Composite parent, IGridViewEntity entity, IVisualEntityManager manager);
	
	boolean isValid();
	
	boolean closable();
	
	void l10n();

}
