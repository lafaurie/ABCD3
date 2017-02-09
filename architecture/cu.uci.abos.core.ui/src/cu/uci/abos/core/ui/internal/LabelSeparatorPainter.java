package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.util.api.FormDatas;

public class LabelSeparatorPainter extends Painter{

	public LabelSeparatorPainter(PainterHelper helper) {
		super(helper);
	}

	@Override
	protected Control paintLeftMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 5).atLeftTo(helper.getLeft(), 5).withWidth(SWT.DEFAULT).atRight(5).atLeft(5);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer calculatePercent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 5).atLeftTo(helper.getLast(), 5).withWidth(SWT.DEFAULT).atRight(5).atLeft(5);
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer calculatePercentcent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 5).atLeftTo(helper.getLast(), 5).withWidth(SWT.DEFAULT).atRight(5).atLeft(5);
		helper.setTop(helper.getLeft());
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		FormDatas.attach(control).atTopTo(helper.getTop(), 5).atLeftTo(helper.getTop(), 5).withWidth(SWT.DEFAULT).atRight(5).atLeft(5);
		helper.setLeft(control);
		helper.setLast(control);
		return control;
	}

}
