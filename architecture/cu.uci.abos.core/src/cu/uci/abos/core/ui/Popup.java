package cu.uci.abos.core.ui;

import org.eclipse.jface.window.Window;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class Popup extends Window {

	private static final long serialVersionUID = 3764629772141137995L;
	private final Control contentProxy;
	private static String title;
	private Rectangle bounds;
	protected final SelectionListener listener;
	protected PagePainter painter;
	private boolean takeFocusOnOpen = true;
	protected Control dialogArea;

	protected Control getFocusControl() {
		return dialogArea;
	}

	public Popup(Shell parentShell, int shellStyle, String title, Control contentProxy, SelectionListener listener) {
		super(parentShell);

		this.contentProxy = contentProxy;
		this.bounds = null;
		this.title = title;
		this.listener = listener;
		painter = new FormPagePainter();
		parentShell.setLayout(new FormLayout());

		if ((shellStyle & SWT.NO_TRIM) != 0) {
			shellStyle &= ~(SWT.NO_TRIM | SWT.SHELL_TRIM);
		}
		
		setShellStyle(shellStyle);
		setBlockOnOpen(false);
	}
	
	
	

	protected void adjustBounds() {
		if (bounds != null) {
			getShell().setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		} else {
			// Default
			Display display = contentProxy.getDisplay();
			Point position = display.map(contentProxy.getParent(), null, contentProxy.getLocation());
			getShell().setBounds(position.x - 1, position.y + 1, contentProxy.getSize().x + 500, contentProxy.getSize().y + 200);
		}
		if (title != null) {
			getShell().setText(title);
		}
		
		painter.setDimension(getShell().getBounds().width);
	}

	@Override
	public int open() {

		Shell shell = getShell();
		if (shell == null || shell.isDisposed()) {
			shell = null;
			create();
			shell = getShell();
		}

		adjustBounds();

		constrainShellSize();

		if (takeFocusOnOpen) {
			shell.open();
			getFocusControl().setFocus();
		} else {
			shell.setVisible(true);
		}

		return OK;

	}

	@Override
	protected Control createContents(final Composite parent) {
		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FormLayout());
		dialogArea = composite;
		return createUI(composite);
	}

	public void setBounds(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
	}

	public abstract Control createUI(Composite parent);

}
