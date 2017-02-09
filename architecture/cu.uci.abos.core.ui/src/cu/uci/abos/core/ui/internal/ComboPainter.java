package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.util.api.FormDatas;

public class ComboPainter extends Painter {

	public ComboPainter(PainterHelper helper) {
		super(helper);
	}

	@Override
	protected Control paintLeftMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLeft(), 10).withHeight(23).withWidth(calculatePercent);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withHeight(23).withWidth(calculatePercent);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer calculatePercentcent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withHeight(23).withWidth(calculatePercentcent);
		helper.setTop(helper.getLeft());
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getTop(), 10).withHeight(23).withWidth(percent);
		helper.setLeft(control);
		helper.setLast(control);
		return control;
	}
}
