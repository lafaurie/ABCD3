package cu.uci.abos.core.widget.grid;

import org.eclipse.swt.widgets.Composite;

import cu.uci.abos.core.util.SecurityUtils;

public class SecurityCRUDTreeTable extends CRUDTreeTable {

	private static final long serialVersionUID = 3524954866307217254L;
	public SecurityCRUDTreeTable(Composite parent, int style, String removePermission) {
		super(parent, style);
		setDelete(removePermission);
	}
	
	public SecurityCRUDTreeTable(Composite parent, int style) {
		super(parent, style);
		setDelete(false);
	}


	@Override
	public void setAdd(boolean add, IEditableArea editableArea) {
		if (SecurityUtils.check(((BaseEditableArea)editableArea).getID())) {
			super.setAdd(add, editableArea);	
		}else{
			super.setAdd(false, editableArea);
		}
		
	}

	@Override
	public void setWatch(boolean watch, IEditableArea editableArea) {
		if (SecurityUtils.check(((BaseEditableArea)editableArea).getID())) {
			super.setWatch(watch, editableArea);	
		}else{
			super.setWatch(false, editableArea);
		}
	}

	@Override
	public void setUpdate(boolean update, IEditableArea editableArea) {
		if (SecurityUtils.check(((BaseEditableArea)editableArea).getID())) {
			super.setUpdate(update, editableArea);	
		}else{
			super.setUpdate(false, editableArea);
		}
	}

	public void setDelete(String permission) {
		if (SecurityUtils.check(permission)) {
			setDelete(true);	
		}else{
			setDelete(false);
		}
	}
	
}
