package cu.uci.abcd.circulation.ui.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.circulation.ui.auxiliary.ViewLoanUserFragment;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewAreaUserLoan extends BaseEditableArea {

	private Map<String, Control> controls;
	private ViewController controller;
	private LoanUser entityLoanUser;
	private ViewLoanUserFragment viewLoanUserFragment;
	private Composite parentComposite;
	private int dimension;
	
	public ViewAreaUserLoan(ViewController controller) {
		super();
		controls = new HashMap<String, Control>();
		this.controller = controller;		
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		entityLoanUser = (LoanUser) entity.getRow();
		LoanUser loanUser = entityLoanUser;
		buildMessage(parent);
		viewLoanUserFragment = new ViewLoanUserFragment(controller, loanUser,dimension);

		parentComposite = (Composite) viewLoanUserFragment.createUIControl(parent);

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
	public boolean closable() {
		return true;
	}

	@Override
	public void l10n() {
		viewLoanUserFragment.l10n();
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1, IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public String getID() {
		return "viewLoanUserID";
	}
}
