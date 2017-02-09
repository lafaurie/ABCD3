package cu.uci.abcd.administration.library.ui.model;

import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.administration.library.communFragment.ViewRoomFragment;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RoomViewArea extends BaseEditableArea {
	@SuppressWarnings("unused")
	private Label header;
	private Group dataGroup;
	@SuppressWarnings("unused")
	private String titleGroup;
	@SuppressWarnings("unused")
	private List<String> leftList;
	@SuppressWarnings("unused")
	private List<Control> grupControls;
	
	private ViewRoomFragment viewRoomFragment;
	public ViewRoomFragment getViewRoomFragment() {
		return viewRoomFragment;
	}

	private Composite parentComposite;

	
	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1,
			IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		Room room = (Room) entity.getRow();
		viewRoomFragment = new ViewRoomFragment(room);
		parentComposite = (Composite) viewRoomFragment.createUIControl(parent);
		return parentComposite;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {
		viewRoomFragment.l10n();
	}

	@Override
	public boolean closable() {
		return true;
	}

	public Group getDataGroup() {
		return dataGroup;
	}

	public void setDataGroup(Group dataGroup) {
		this.dataGroup = dataGroup;
	}

	@Override
	public String getID() {
		return "viewRoomID";
	}

}
