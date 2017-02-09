package cu.uci.abcd.circulation.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.circulation.ui.auxiliary.ViewLoanObjectFragment;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewAreaLoanObject extends BaseEditableArea {
	
	private ViewController controller;
	private LoanObject entityLoanObject;
	private ViewLoanObjectFragment viewLoanObjectFragment;
	private Composite parentComposite;
	private int dimension;
	
	public ViewAreaLoanObject(ViewController controller) {
		this.controller = controller;
		}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1, IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		entityLoanObject = (LoanObject) entity.getRow();
		LoanObject loanObject = entityLoanObject;
		buildMessage(parent);
		viewLoanObjectFragment = new ViewLoanObjectFragment(controller, loanObject,dimension);
		parentComposite = (Composite) viewLoanObjectFragment.createUIControl(parent);		
		return parentComposite;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public String getID() {
		return "viewLoanObjectID";
	}

	@Override
	public void l10n() {
		viewLoanObjectFragment.l10n();	
	}

}
