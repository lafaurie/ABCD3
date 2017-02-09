package cu.uci.abos.core.ui.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.core.util.FormDatas;

public class LabelPainter extends Painter{


	public LabelPainter(PainterHelper helper) {
		super(helper);
	}

	@Override
	protected Control paintLeftMiddleControl(Control control, Integer calculatePercent) {
		if (helper.getDimension()>840){
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLeft(), 10).withHeight(SWT.DEFAULT).withWidth(calculatePercent-50);
			((Label)control).setAlignment(SWT.RIGHT);
		}else{
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLeft(), 10).withHeight(SWT.DEFAULT).withWidth(calculatePercent-70);
			((Label)control).setAlignment(SWT.LEFT);
		}
		helper.setLast(control);
		return control;
	}

	@Override
	protected Control paintRightMiddleControl(Control control, Integer calculatePercent) {
		
		if (helper.getDimension()>840){
			((Label)control).setAlignment(SWT.RIGHT);
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withHeight(SWT.DEFAULT).withWidth(calculatePercent-50);
			helper.setLast(control);
		}
		else{
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withHeight(SWT.DEFAULT).withWidth(calculatePercent-70);
			helper.setLast(control);
			((Label)control).setAlignment(SWT.LEFT);
		}
		return control;
	}

	@Override
	protected Control paintRightControl(Control control, Integer calculatePercentcent) {
		if (helper.getDimension()>840){
			((Label)control).setAlignment(SWT.RIGHT);
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withHeight(SWT.DEFAULT).withWidth(calculatePercentcent-50);
		helper.setLast(control);
		helper.setTop(helper.getTopByheight());
		}else{
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeftTo(helper.getLast(), 10).withHeight(SWT.DEFAULT).withWidth(calculatePercentcent-70);
			helper.setLast(control);
			helper.setTop(helper.getTopByheight());
			((Label)control).setAlignment(SWT.LEFT);
		}
			
		return control;
	}

	@Override
	protected Control paintLeftControl(Control control, Integer percent) {
		if (helper.getDimension()>840){
			((Label)control).setAlignment(SWT.RIGHT);
		FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeft(15).withHeight(SWT.DEFAULT).withWidth(percent-50);
		helper.setLeft(control);
		helper.setLast(control);
		}else{
			((Label)control).setAlignment(SWT.LEFT);
			FormDatas.attach(control).atTopTo(helper.getTop(), 20).atLeft(15).withHeight(SWT.DEFAULT).withWidth(percent-70);
			helper.setLeft(control);
			helper.setLast(control);
		}
		return control;
	}
}
