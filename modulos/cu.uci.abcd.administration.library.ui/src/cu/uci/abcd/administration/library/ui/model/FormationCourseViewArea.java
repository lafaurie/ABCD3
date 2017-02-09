package cu.uci.abcd.administration.library.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.communFragment.ViewFormationCourseFragment;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class FormationCourseViewArea extends BaseEditableArea{
	@SuppressWarnings("unused")
	private ViewController controller;
	FormLayout form;
	Composite parent;
	Label dataCourseLabel;
	Table dataCourseTable;
	TableColumn generalDataCourseColumn1;
	TableColumn generalDataCourseColumn2;
	
	TableItem nameTableItem;
	TableItem hoursQuantityTableItem;
	TableItem externalTeachersQuantityTableItem;
	TableItem workerTeachersQuantityTableItem;
	TableItem externalStudentQuantityTableItem;
	TableItem workerStudentQuantityTableItem;
	TableItem startDateTableItem;
	TableItem endDateTableItem;
	Text descriptionText;
	
	
	Button exportarPdf;
	Button exportarExcel;
	
	Label descriptionLabel;
	
	
	public FormationCourseViewArea(ViewController controller) {
		this.controller = controller;
	}
	
	ViewFormationCourseFragment viewFormationCourseFragment;
	public ViewFormationCourseFragment getViewFormationCourseFragment() {
		return viewFormationCourseFragment;
	}

	public void setViewFormationCourseFragment(
			ViewFormationCourseFragment viewFormationCourseFragment) {
		this.viewFormationCourseFragment = viewFormationCourseFragment;
	}

	Composite parentComposite = null;
	
	@Override
	public Composite createUI(Composite shell, IGridViewEntity entity,
			IVisualEntityManager manager) {
		FormationCourse formationCourseToView = (FormationCourse)entity.getRow();
		viewFormationCourseFragment = new ViewFormationCourseFragment(formationCourseToView);
		parentComposite = (Composite) viewFormationCourseFragment.createUIControl(shell);
		return parentComposite;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1,
			IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		// TODO Auto-generated method stub
		return parent;
	}
	
	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void l10n() {
		//Composite x = parentComposite;
		//if(parentComposite!=null){
			viewFormationCourseFragment.l10n();
		//}
		
		
	}
	
	@Override
	public String getID() {
		return "viewFormationCourseID";
	}

}
