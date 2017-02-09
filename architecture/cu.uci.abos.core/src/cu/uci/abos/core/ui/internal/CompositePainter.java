package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.core.util.FormDatas;

public class CompositePainter extends Painter {

	public CompositePainter(PainterHelper helper) {
		super(helper);
	}

	@Override
	protected Control paintLeftMiddleControl(Control control, Integer calculatePercent) {
		if (control.getVisible()) {
			FormDatas.attach(control).atTopTo(helper.getTop(), 0).atLeftTo(helper.getLeft(), 0).withWidth(calculatePercent );
		} else {
			FormDatas.attach(control).atTopTo(helper.getTop(), 0).atLeftTo(helper.getLeft(),0).withHeight(0).withWidth(calculatePercent);
		}
	
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer calculatePercent) {
		if (control.getVisible()) {
			FormDatas.attach(control).atTopTo(helper.getTop(), 0).atLeftTo(helper.getLast(), 0).withWidth(calculatePercent);
		} else {
			FormDatas.attach(control).atTopTo(helper.getTop(), 0).atLeftTo(helper.getLast(), 0).withWidth(calculatePercent).withHeight(0);
		}
		FormDatas.attach(control).atTopTo(helper.getTop(), 0).atLeftTo(helper.getLast(), 0).withWidth(calculatePercent);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer calculatePercentcent) {
		if (control.getVisible()) {
			FormDatas.attach(control).atTopTo(helper.getTop(), 0).atLeftTo(helper.getLast(), 0).withWidth(calculatePercentcent);
		} else {
			FormDatas.attach(control).atTopTo(helper.getTop(), 0).atLeftTo(helper.getLast(), 0).withWidth(calculatePercentcent).withHeight(0);
		}
		helper.setLast(control);
		helper.setTop(helper.getTopByheight());
		reset();
		return control;
	}

	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		if (control.getVisible()) {
			FormDatas.attach(control).atTopTo(helper.getTop(), 0).atLeftTo(helper.getTop(), 0).withWidth(percent);
		} else {
			FormDatas.attach(control).atTopTo(helper.getTop(), 0).atLeftTo(helper.getTop(), 0).withWidth(percent).withHeight(0);
		}
		
		helper.setLeft(control);
		helper.setLast(control);
		return control;
	}
}
