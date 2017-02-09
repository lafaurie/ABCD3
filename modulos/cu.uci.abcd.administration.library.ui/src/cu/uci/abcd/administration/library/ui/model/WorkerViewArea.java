package cu.uci.abcd.administration.library.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.administration.library.communFragment.ViewWorkerFragment;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class WorkerViewArea extends BaseEditableArea {
	private ViewWorkerFragment viewWorkerFragment;
	public ViewWorkerFragment getViewWorkerFragment() {
		return viewWorkerFragment;
	}

	public void setViewWorkerFragment(ViewWorkerFragment viewWorkerFragment) {
		this.viewWorkerFragment = viewWorkerFragment;
	}

	private Composite parentComposite;

	public WorkerViewArea(ViewController controller) {
		this.controller = controller;
	}

	@Override
	public Composite createUI(Composite shell, IGridViewEntity entity, IVisualEntityManager manager) {
		Worker worker = (Worker) entity.getRow();
		viewWorkerFragment = new ViewWorkerFragment(worker);
		parentComposite = (Composite) viewWorkerFragment.createUIControl(shell);
		return parentComposite;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1, IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public void l10n() {
		viewWorkerFragment.l10n();
	}
	
	@Override
	public String getID() {
		return "viewWorkerID";
	}

}
