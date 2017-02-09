package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.core.util.FormDatas;

public class TabPainter extends Painter {

	public TabPainter(PainterHelper helper) {
		super(helper);
	}

	@Override
	protected Control paintLeftMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeft(5).atRight(5);
		helper.setLast(control);
		reset();
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeft(5).atRight(5);
		helper.setLast(control);
		reset();
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer calculatePercentcent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeft(5).atRight(5);
		helper.setLast(control);
		helper.setTop(helper.getTopByheight());
		reset();
		return control;
	}

	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeft(5).atRight(5);
		helper.setLeft(control);
		helper.setLast(control);
		reset();
		return control;
	}
}
