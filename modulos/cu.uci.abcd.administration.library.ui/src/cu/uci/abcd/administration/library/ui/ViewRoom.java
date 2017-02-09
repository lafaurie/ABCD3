package cu.uci.abcd.administration.library.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.administration.library.communFragment.ViewRoomFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ViewRoom extends ContributorPage implements Contributor{

	private Room room;
	private ViewRoomFragment viewRoomFragment;
	private Composite parentComposite;
	//private RegisterRoom registerRoom;
	private ContributorService contributorService;
	
	@Override
	public void setParameters(Object... parameters) {
		room = ((Room)parameters[0]);
		//registerRoom = ((RegisterRoom)parameters[1]);
		contributorService = ((ContributorService)parameters[2]);

	}
	
	@Override
	public Control createUIControl(Composite parent) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		viewRoomFragment = new ViewRoomFragment(room, true, this, contributorService);
		parentComposite = (Composite) viewRoomFragment.createUIControl(parent);
		viewRoomFragment.l10n();
		RetroalimentationUtils.showInformationMessage(viewRoomFragment.getMsg(), MessageUtil
				.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
		
		return parentComposite;
	}
	
	@Override
	public String getID() {
		return "viewRoom";
	}
	
	@Override
	public void l10n() {
		viewRoomFragment.l10n();
		Auxiliary.refresh(parentComposite);
	}
	
	@Override
	public String contributorName() {
		return AbosMessages.get().REGISTER_ROOM;
	}


}
