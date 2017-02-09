package cu.uci.abcd.acquisition.ui.updateArea;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewPurchaseRequestArea extends BaseEditableArea{

	private int dimension;
	private ViewPurchaseRequestFragment viewPurchaseRequestFragment;
	private PurchaseRequest purchaseRequest;
	private Composite parentComposite;

	public ViewPurchaseRequestArea(ViewController controller) {
		this.controller = controller;
		}
	
	@Override
	public boolean closable() {	
		return true;
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
		purchaseRequest = (PurchaseRequest) entity.getRow();
		viewPurchaseRequestFragment = new ViewPurchaseRequestFragment(controller, purchaseRequest,dimension);
		parentComposite = (Composite) viewPurchaseRequestFragment.createUIControl(parent);

			return parent;
}

	@Override
	public boolean isValid() {
		
		return false;
	}

	@Override
	public void l10n() {
		viewPurchaseRequestFragment.l10n();
	}
}