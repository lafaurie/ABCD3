package cu.uci.abcd.acquisition.ui.updateArea;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewPurchaseOrderArea extends BaseEditableArea{
	
	private PurchaseOrder purchaseOrder;
	private int dimension;
	private ViewPurchaseOrderFragment viewPurchaseOrderFragment;
	private Composite parentComposite;

	@Override
	public boolean closable() {
		return true;
	}

	public ViewPurchaseOrderArea(ViewController controller) {
		this.controller = controller;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1,
			IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager arg2) {
		
		dimension = parent.getParent().getParent().getBounds().width;
		purchaseOrder = (PurchaseOrder) entity.getRow();
		viewPurchaseOrderFragment = new ViewPurchaseOrderFragment(controller, purchaseOrder,dimension);
		parentComposite = (Composite) viewPurchaseOrderFragment.createUIControl(parent);

		return parent;
	}


	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {
		viewPurchaseOrderFragment.l10n();
	}

}
