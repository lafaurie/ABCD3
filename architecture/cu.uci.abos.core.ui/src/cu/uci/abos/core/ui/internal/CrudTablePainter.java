package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.util.api.FormDatas;

public class CrudTablePainter extends Painter {

	public CrudTablePainter(PainterHelper helper) {
		super(helper);
	}

	@Override
	protected Control paintLeftMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeft(15).atRight(15);
		helper.setLast(control);
		reset();
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeft(15).atRight(15);
		helper.setLast(control);
		reset();
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer calculatePercentcent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeft(15).atRight(15);
		helper.setTop(helper.getLeft());
		helper.setLast(control);
		reset();
		return control;
	}

	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeft(15).atRight(15);
		helper.setLeft(control);
		helper.setLast(control);
		reset();
		return control;
	}
}
