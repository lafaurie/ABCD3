package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.core.util.FormDatas;

public class DateTimePainter extends Painter {

	public DateTimePainter(PainterHelper helper) {
		super(helper);
	}
	@Override
	protected Control paintLeftMiddleControl(Control control, Integer percent) {
		if (control.getStyle()>0) {
			FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeftTo(helper.getLeft(), 10).withHeight(27).withWidth(percent + 21);
		} else {
			FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeftTo(helper.getLeft(), 10).withHeight(27).withWidth(percent + 21);
		}
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer percent) {
		if (control.getStyle()>0) {
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withHeight(27).withWidth(percent +21);
		} else {
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withHeight(27).withWidth(percent +21);
		}
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer percent) {
		if (control.getStyle()>0) {
			FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeftTo(helper.getLast(), 10).withHeight(27).withWidth(percent+21);
		} else {
			FormDatas.attach(control).atTopTo(helper.getTop(), 15).atLeftTo(helper.getLast(), 10).withHeight(27).withWidth(percent+21);
		}
		helper.setLast(control);
		helper.setTop(helper.getTopByheight());
		return control;
	}

	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		if (control.getStyle()>0) {
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeft(10).withHeight(27).withWidth(percent+21);
		} else {
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeft(10).withHeight(27).withWidth(percent+21);
		}
		helper.setLeft(control);
		helper.setLast(control);
		return control;
	}

}
