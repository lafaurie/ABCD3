package cu.uci.abcd.administration.library.ui;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.administration.library.communFragment.RegisterRoomFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterRoom extends ContributorPage{
	private RegisterRoomFragment registerRoom;
	private int dimension;
	
	@Override
	public void setViewController(ViewController controller) {
		this.setController(controller);
	}
	
	@Override
	public String contributorName() {
		return AbosMessages.get().REGISTER_ROOM;
	}

	@Override
	public String getID() {
		return "addRoomID";
	}

	@Override
	public Control createUIControl(Composite parent) {
		dimension = parent.getParent().getParent().getParent().getBounds().width;
		ContributorService contributorService = getContributorService();
		registerRoom =  new RegisterRoomFragment(
				//contributorName(), 
				null, controller, dimension, this, contributorService);
		return registerRoom.createUIControl(parent);
	}

	@Override
	public boolean canClose() {
		addListener(SWT.Dispose, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
			
			}
		});
		return false;
	}

	@Override
	public void l10n() {
		registerRoom.l10n();
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		super.controller = controller;
	}
}
