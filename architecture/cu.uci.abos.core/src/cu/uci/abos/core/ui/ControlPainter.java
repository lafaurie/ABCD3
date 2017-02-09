package cu.uci.abos.core.ui;

import org.eclipse.swt.widgets.Control;

public interface ControlPainter {
	<T extends ControlPainter>  T paint(Control control,Integer dimension, Percent percent);
}
