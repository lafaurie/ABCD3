package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;

public class CrudTablePainter extends Painter {

	public CrudTablePainter(PainterHelper helper) {
		super(helper);
	}

	@Override
	protected Control paintLeftMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeftTo(helper.getLast(), 10).withWidth(calculatePercent-15);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeftTo(helper.getLast(), 10).withWidth(calculatePercent-15);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer percent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeftTo(helper.getLast(), 10).withWidth(percent-15);
		helper.setLast(control);
		helper.setTop(helper.getTopByheight());
		return control;
	}

	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		if (percent.equals(calculatePercent(helper.getDimension(), Percent.W100))) {
			FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeft(15).atRight(15);
		}else{
			FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeft(15).withWidth(percent-15);
		}
		helper.setLeft(control);
		helper.setLast(control);
		return control;
	}
}
