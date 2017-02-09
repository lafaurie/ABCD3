package cu.uci.abcd.administration.library.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.administration.library.communFragment.RegisterRoomFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RoomUpdateArea extends BaseEditableArea {

	private CRUDTreeTable tableRoom;
	private ViewController controller;
	Button saveButton;
	Room room = null;
	RegisterRoomFragment saveRoomFragment;
	Composite parentComposite;
	int dimension;

	public RoomUpdateArea(ViewController controller, CRUDTreeTable tableRoom) {
		this.controller = controller;
		this.tableRoom = tableRoom;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public Composite createButtons(final Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveButton = new Button(parent, SWT.PUSH);
		saveButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				Room roomSaved = saveRoomFragment.saveRoom();
				if (roomSaved!=null) {
					
					room = null;
					manager.save(new BaseGridViewEntity<Room>(
							roomSaved));
					manager.refresh();
					
					Composite viewSmg = ((RoomViewArea)tableRoom.getActiveArea()).getViewRoomFragment().getMsg();
					
					RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages
									.get().MSG_INF_UPDATE_DATA));
					//tableRoom.destroyEditableArea();

				}
			}
		});
		return parent;

	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		addComposite(parent);
		if (entity != null) {
			Room roomToView = (Room) entity.getRow();
			room = ((LibraryViewController) controller).readRoom(roomToView.getRoomID());
		}
		saveRoomFragment = new RegisterRoomFragment(room, controller, dimension);
		parentComposite = (Composite) saveRoomFragment.createUIControl(parent);
		return parentComposite;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void l10n() {
		if (room == null) {
		} else {
			saveButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		}
		saveButton.getParent().layout(true, true);
		saveButton.getParent().redraw();
		saveButton.getParent().update();
		saveRoomFragment.l10n();

	}
	
	@Override
	public String getID() {
		return "updateRoomID";
	}

}
