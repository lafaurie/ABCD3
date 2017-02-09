package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.core.util.FormDatas;

public class GroupPainter extends Painter {

	public GroupPainter(PainterHelper helper) {
		super(helper);
	}

	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		/*Group group =(Group) control;
		group.setLayout(new FormLayout());
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeft(10).withWidth(percent);
		group.redraw();
		helper.setLeft(control);
		helper.setLast(control);
		*/
		
		Group group =(Group) control;
		group.setLayout(new FormLayout());
		if (helper.getDimension()>840){
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeft(10).withWidth(percent);
			group.redraw();
			helper.setLeft(control);
			helper.setLast(control);
		}else{
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeft(10).withWidth(percent-100);
			group.redraw();
			helper.setLeft(control);
			helper.setLast(control);
		}	
		
		return control;
	}
	@Override
	protected Control paintLeftMiddleControl(Control control, Integer calculatePercent) {
		Group group =(Group) control;
		group.setLayout(new FormLayout());
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLeft(), 10).withWidth(calculatePercent);
		group.redraw();
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer calculatePercent) {
		Group group =(Group) control;
		group.setLayout(new FormLayout());
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withWidth(calculatePercent);
		group.redraw();
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer calculatePercentcent) {
		Group group =(Group) control;
		group.setLayout(new FormLayout());
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).atRight(15);
		helper.setLast(control);
		group.redraw();
		helper.setTop(helper.getTopByheight());
		reset();
		return control;
	}

}
