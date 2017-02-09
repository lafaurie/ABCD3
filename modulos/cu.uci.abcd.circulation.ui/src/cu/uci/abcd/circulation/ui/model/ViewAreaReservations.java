package cu.uci.abcd.circulation.ui.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.circulation.ui.auxiliary.ViewReservationFragment;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewAreaReservations extends BaseEditableArea {

	private Map<String, Control> controls;
	private ViewController controller;
	private Reservation entityReservation;
	private ViewReservationFragment viewReservationFragment;
	private Composite parentComposite;
	private int dimension;
	
	public ViewAreaReservations(ViewController controller) {
		super();
		controls = new HashMap<String, Control>();
		this.controller = controller;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		entityReservation = (Reservation) entity.getRow();
		Reservation reservation = entityReservation;
		buildMessage(parent);
		viewReservationFragment = new ViewReservationFragment(controller, reservation,dimension);
		parentComposite = (Composite) viewReservationFragment.createUIControl(parent);

		return parentComposite;

	}

	@Override
	public boolean isValid() {
		return false;
	}

	public Control getControl(String key) {
		return this.controls.get(key);
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public void l10n() {
		viewReservationFragment.l10n();
	}
	@Override
	public String getID() {
		return "viewReservationID";
	}
}
