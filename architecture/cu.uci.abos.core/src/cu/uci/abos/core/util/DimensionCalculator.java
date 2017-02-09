package cu.uci.abos.core.util;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class DimensionCalculator {

	public static int getValue(int percent, Composite parent) {
		return parent.getSize().y * (percent / 100);
	}

	int getTextDimemsion(Display display) {
		return 0;
	}

}
