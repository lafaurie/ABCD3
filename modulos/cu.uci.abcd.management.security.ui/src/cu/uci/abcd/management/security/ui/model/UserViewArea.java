package cu.uci.abcd.management.security.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.management.security.communFragment.ViewUserFragment;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class UserViewArea extends BaseEditableArea {

	ViewUserFragment viewUserFragment;
	Composite parentComposite;
	
	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		User user = (User) entity.getRow();
		viewUserFragment = new ViewUserFragment(user);
		parentComposite = (Composite) viewUserFragment.createUIControl(parent);
		return parentComposite;
	}
	
	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1,
			IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public void l10n() {
		
	}
	
	@Override
	public String getID() {
		return "viewUserID";
	}

}
