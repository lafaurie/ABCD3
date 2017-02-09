package cu.uci.abcd.management.security.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.domain.management.security.AccessRecord;
import cu.uci.abcd.management.security.communFragment.ViewAccessControlFragment;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class AccessControlViewArea extends BaseEditableArea {

	ViewAccessControlFragment viewAccessControlFragment;
	public ViewAccessControlFragment getViewAccessControlFragment() {
		return viewAccessControlFragment;
	}

	public void setViewAccessControlFragment(
			ViewAccessControlFragment viewAccessControlFragment) {
		this.viewAccessControlFragment = viewAccessControlFragment;
	}

	Composite parentComposite;


	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		AccessRecord accessRecord = (AccessRecord) entity.getRow();
		viewAccessControlFragment = new ViewAccessControlFragment(accessRecord);
		parentComposite = (Composite) viewAccessControlFragment.createUIControl(parent);
		return parentComposite;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void l10n() {
		
	}
	
	@Override
	public String getID() {
		return "viewAccessControlID";
	}

}
