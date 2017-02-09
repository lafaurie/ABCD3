package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.core.util.FormDatas;

public class LinkPainter extends Painter{


	public LinkPainter(PainterHelper helper) {
		super(helper);
	}
	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).withHeight(23).atRight(15);
		helper.setLeft(control);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintLeftMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atRightTo(helper.getLeft(), 10).withHeight(23);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atRightTo(helper.getLast(), 10).withHeight(23);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer calculatePercentcent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atRightTo(helper.getLast(), 10).withHeight(23);
		helper.setLast(control);
		helper.setTop(helper.getTopByheight());
		return control;
	}

}
