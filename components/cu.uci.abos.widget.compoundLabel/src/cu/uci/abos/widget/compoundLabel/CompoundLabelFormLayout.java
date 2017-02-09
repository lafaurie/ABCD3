package cu.uci.abos.widget.compoundLabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class CompoundLabelFormLayout extends Layout {

	private static final long serialVersionUID = 1L;
	// fixed margin and spacing
	public static final int MARGIN = 4;
	public static final int SPACING = 2;
	// cache
	Point[] sizes;
	int maxWidth, totalHeight;

	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean changed) {
		Control children[] = composite.getChildren();
		if (changed || sizes == null || sizes.length != children.length) {
			initialize(children);
		}
		int width = wHint, height = hHint;
		if (wHint == SWT.DEFAULT)
			width = maxWidth;
		if (hHint == SWT.DEFAULT)
			height = totalHeight;
		return new Point(width + 2 * MARGIN, height + 2 * MARGIN);
	}

	@Override
	protected void layout(Composite composite, boolean changed) {
		Control children[] = composite.getChildren();
		if (changed || sizes == null || sizes.length != children.length) {
			initialize(children);
		}
		Rectangle rect = composite.getClientArea();
		int x = MARGIN, y = MARGIN;
		int width = Math.max(rect.width - 2 * MARGIN, maxWidth);
		for (int i = 0; i < children.length; i++) {
			int height = sizes[i].y;
			children[i].setBounds(x, y, width, height);
			y += height + SPACING;
		}
	}

	void initialize(Control children[]) {
		maxWidth = 0;
		totalHeight = 0;
		sizes = new Point[children.length];
		for (int i = 0; i < children.length; i++) {
			sizes[i] = children[i].computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
			maxWidth = Math.max(maxWidth, sizes[i].x);
			totalHeight += sizes[i].y;
		}
		totalHeight += (children.length - 1) * SPACING;
	}
}
