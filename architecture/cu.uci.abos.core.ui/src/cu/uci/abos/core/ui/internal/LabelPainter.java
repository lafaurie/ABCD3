package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.util.api.FormDatas;

public class LabelPainter extends Painter{


	public LabelPainter(PainterHelper helper) {
		super(helper);
	}

	@Override
	protected Control paintLeftMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLeft(), 10).withHeight(23).withWidth(calculatePercent);
		((Label)control).setAlignment(SWT.RIGHT);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withHeight(23).withWidth(calculatePercent-50);
		helper.setLast(control);
		((Label)control).setAlignment(SWT.RIGHT);
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer calculatePercentcent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withHeight(23).withWidth(calculatePercentcent);
		helper.setTop(helper.getLeft());
		helper.setLast(control);
		((Label)control).setAlignment(SWT.RIGHT);
		return control;
	}

	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeft(15).withHeight(23).withWidth(percent-50);
		helper.setLeft(control);
		helper.setLast(control);
		((Label)control).setAlignment(SWT.RIGHT);
		return control;
	}
}
