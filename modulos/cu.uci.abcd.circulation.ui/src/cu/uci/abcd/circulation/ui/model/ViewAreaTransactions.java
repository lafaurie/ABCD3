package cu.uci.abcd.circulation.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.circulation.ui.ConsultTransaction;
import cu.uci.abcd.circulation.ui.RegisterRenew;
import cu.uci.abcd.circulation.ui.RegisterReturn;
import cu.uci.abcd.circulation.ui.auxiliary.ViewTransactionsFragmentConsult;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewAreaTransactions extends BaseEditableArea {
	
	private ViewController controller;
	private Transaction entityTransactions;
	private ViewTransactionsFragmentConsult viewTransactionsFragment;
	private Composite parentComposite;
	private RegisterRenew registerRenew;
	private int dimension;
	private CRUDTreeTable tabla;
	private ConsultTransaction consultTransaction;
	private CRUDTreeTable tablaRenew;
	/**
	 * prueba
	 */
	
	RegisterReturn registerReturn;
	
	public ViewAreaTransactions(ViewController controller) {
		this.controller = controller;		
	}
	   
	public ViewAreaTransactions(ViewController controller, RegisterReturn registerReturn) {
		this.controller = controller;
		this.registerReturn = registerReturn;
	}
	public ViewAreaTransactions(ViewController controller,CRUDTreeTable tabla) {
		this.controller = controller;
		this.tabla = tabla;
	}
	public ViewAreaTransactions(ViewController controller,CRUDTreeTable tabla, ConsultTransaction consultTransaction) {
		this.controller = controller;
		this.tabla = tabla;
		this.consultTransaction = consultTransaction;
	}
	public ViewAreaTransactions(ViewController controller,RegisterRenew registerRenew,CRUDTreeTable tablaRenew) {
		super();
		this.controller = controller;
		this.registerRenew = registerRenew;
		this.tablaRenew = tablaRenew;
	}
   
	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		entityTransactions = (Transaction) entity.getRow();
		Transaction transaction = entityTransactions;
		buildMessage(parent);
		viewTransactionsFragment = new ViewTransactionsFragmentConsult(controller, transaction,registerRenew,dimension, manager,consultTransaction,tabla, registerReturn, tablaRenew);
		parentComposite = (Composite) viewTransactionsFragment.createUIControl(parent);
		refresh();
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
