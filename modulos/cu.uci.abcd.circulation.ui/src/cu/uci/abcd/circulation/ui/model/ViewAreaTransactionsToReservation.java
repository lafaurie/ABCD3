package cu.uci.abcd.circulation.ui.model;

import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.circulation.ui.auxiliary.ViewTransactionsFragment;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewAreaTransactionsToReservation extends BaseEditableArea {
	
	private ViewController controller;
	private ViewTransactionsFragment viewTransactionsFragment;
	private Composite parentComposite;
	private int dimension;
	private List<Transaction> listTransaction;
	
	public ViewAreaTransactionsToReservation(ViewController controller) {
		this.controller = controller;
	}
	public ViewAreaTransactionsToReservation(ViewController controller,List<Transaction> listTransaction) {
		this.controller = controller;
		this.listTransaction = listTransaction;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		buildMessage(parent);
		viewTransactionsFragment = new ViewTransactionsFragment(controller,listTransaction, dimension);
		parentComposite = (Composite) viewTransactionsFragment.createUIControl(parent);

		return parentComposite;

	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1, IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	public Control getControl(String arg0) {
		return null;
		//FIXME DEBE DEVOLVER UN CONTROL
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean closable() {
		return true;
	}
	
	@Override
	public String getID() {
		return "viewTransactionID";
	}
	@Override
	public void l10n() {
		viewTransactionsFragment.l10n();
	}
}
