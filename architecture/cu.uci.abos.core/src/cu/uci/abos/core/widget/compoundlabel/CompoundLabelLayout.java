package cu.uci.abos.core.widget.compoundlabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class CompoundLabelLayout extends Layout {

	private static final long serialVersionUID = 1L;
	// Cashed sizes
	private Point iLabelPoint;
	private Point iControlpPoint;

	// fixed margin and spacing
	public static final int MARGIN = 2;
	public static final int SPACING = 5;
	//
	public int difference = 10;
	public int width = 350;

	public CompoundLabelLayout() {
		super();
	}

	public CompoundLabelLayout(int differenceParam) {
		super();
		this.difference = (differenceParam > 0) ? differenceParam : this.difference;
	}

	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean changed) {
		Control[] children = composite.getChildren();
		if (changed || iLabelPoint == null || iControlpPoint == null) {
			iLabelPoint = children[0].computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
			iControlpPoint = children[1].computeSize(width, SWT.DEFAULT, true);
		}
		int width = iLabelPoint.x + (SPACING + difference) + iControlpPoint.x;
		int height = Math.max(iLabelPoint.y, iControlpPoint.y);
		return new Point(width + 2, height + 2);
	}

	@Override
	protected void layout(Composite composite, boolean changed) {
		Control[] children = composite.getChildren();
		if (changed || iLabelPoint == null || iControlpPoint == null) {
			iLabelPoint = children[0].computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
			iControlpPoint = children[1].computeSize(width, SWT.DEFAULT, true);
		}
		int y = ((iControlpPoint.y - iLabelPoint.y) / 2) + 2;
		children[0].setBounds(SPACING, y, iLabelPoint.x, iLabelPoint.y);
		children[1].setBounds(iLabelPoint.x + SPACING + difference, MARGIN, iControlpPoint.x, iControlpPoint.y);
	}

}
