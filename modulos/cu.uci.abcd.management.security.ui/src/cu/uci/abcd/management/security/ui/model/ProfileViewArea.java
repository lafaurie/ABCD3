package cu.uci.abcd.management.security.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.communFragment.ViewProfileFragment;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ProfileViewArea extends BaseEditableArea {

	ViewProfileFragment viewProfileFragment;
	Composite parentComposite;
	Composite msg;
	
	public Composite getMsg() {
		return msg;
	}

	public void setMsg(Composite msg) {
		this.msg = msg;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		Profile profile = (Profile) entity.getRow();
		viewProfileFragment = new ViewProfileFragment(profile);
		parentComposite = (Composite) viewProfileFragment.createUIControl(parent);
		msg = viewProfileFragment.getMsg();
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
		viewProfileFragment.l10n();
	}
	
	@Override
	public String getID() {
		return "viewProfileID";
	}

}
