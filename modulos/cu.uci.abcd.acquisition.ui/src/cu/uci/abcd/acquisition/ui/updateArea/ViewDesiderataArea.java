package cu.uci.abcd.acquisition.ui.updateArea;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewDesiderataArea extends BaseEditableArea {

	private ViewDesiderataFragment viewDesiderataFragment;
	private Desiderata desiderata;
	private Composite parentComposite;
	private int dimension;
	private ViewController controller;
	
	@Override
	public boolean closable() {

		return true;
	}

	public ViewDesiderataArea(ViewController controller) {
		this.controller = controller;
		}
	
	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1, IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager arg2) {
		
		dimension = parent.getParent().getParent().getBounds().width;
		desiderata = (Desiderata) entity.getRow();
		viewDesiderataFragment = new ViewDesiderataFragment(desiderata,controller,dimension);
		parentComposite = (Composite) viewDesiderataFragment.createUIControl(parent);

		return parentComposite;
	}

	
	@Override
	public boolean isValid() {

		return false;
	}

	@Override
	public void l10n() {
		viewDesiderataFragment.l10n();
	}
}