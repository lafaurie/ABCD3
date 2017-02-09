package cu.uci.abcd.circulation.ui.model;

import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.circulation.ui.ConsultSanction;
import cu.uci.abcd.circulation.ui.auxiliary.ViewLoanUserFragment;
import cu.uci.abcd.circulation.ui.auxiliary.ViewPenaltyFragment;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewAreaPenaltyConsult extends BaseEditableArea {

	private Map<String, Control> controls;
	private ViewController controller;

	private Penalty entityPenaltyH;
	private ViewPenaltyFragment viewPenaltyFragment;
	private Composite parentComposite;
	private int dimension;
	private CRUDTreeTable tabla;
	private ConsultSanction consultSanction;
	private ViewLoanUserFragment viewLoanUserFragment;   
	
	public ViewAreaPenaltyConsult(ViewController controller,CRUDTreeTable tabla, ConsultSanction consultSanction) {
		this.controller = controller;
		this.tabla = tabla;
		this.consultSanction = consultSanction;
	}
	
	public ViewAreaPenaltyConsult(ViewController controller,CRUDTreeTable tabla, ViewLoanUserFragment viewLoanUserFragment) {
		this.controller = controller;
		this.tabla = tabla;
		this.viewLoanUserFragment = viewLoanUserFragment;
	}
	
	public ViewAreaPenaltyConsult(ViewController controller,CRUDTreeTable tabla) {
		this.controller = controller;
		this.tabla = tabla;
	}	

	public ViewAreaPenaltyConsult(ViewController controller) {
		this.controller = controller;		
	}
	
	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		entityPenaltyH = (Penalty) entity.getRow();
		Penalty penalty = entityPenaltyH;
		buildMessage(parent);
		viewPenaltyFragment = new ViewPenaltyFragment(controller, penalty,dimension, tabla, consultSanction,viewLoanUserFragment);
		parentComposite = (Composite) viewPenaltyFragment.createUIControl(parent);

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
		viewPenaltyFragment.l10n();
	}

	@Override
	public String getID() {
		return "viewPenaltyID";
	}
}
