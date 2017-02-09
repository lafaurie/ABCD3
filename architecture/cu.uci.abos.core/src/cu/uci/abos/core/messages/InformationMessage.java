package cu.uci.abos.core.messages;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import cu.uci.abos.core.util.FormDatas;

public class InformationMessage {

	private Label icon;
	private Label message;
	private Composite box;
	private Shell shell;

	public InformationMessage(String text, Image type) {
		super();
		setShell(new Shell(Display.getCurrent(), SWT.NORMAL));
		getShell().setLayout(new FormLayout());
		int heigth = 60;
		switch (text.length() / 32) {
	
		case 2:
			heigth = 100;
			break;
		case 3:
			heigth = 130;
			break;
		case 4:
			heigth = 170;
			break;

		case 5:
			heigth = 200;
			break;
		}
		
		getShell().setBounds(Display.getCurrent().getClientArea().width - 345, 108, 304, heigth);
		box = new Composite(getShell(), SWT.INHERIT_FORCE);
		box.setData(RWT.CUSTOM_VARIANT, "gray_background");
		box.setLayout(new FormLayout());
		FormDatas.attach(box).atTop(2).atRight(2).atBottom(2).withWidth(300);
		icon = new Label(box, SWT.NONE);
		icon.setImage(type);
		FormDatas.attach(icon).atLeft(2).atTop(5).withWidth(SWT.DEFAULT);
		this.message = new Label(box, SWT.NORMAL | SWT.WRAP);
		this.message.setText(text);
		FormDatas.attach(message).atRight(15).atTop(10).atBottom(10).withWidth(250).atLeftTo(icon, 10);
		box.pack(true);
		getShell().open();
		getShell().layout(true, true);
		getShell().setFocus();
	}

	public InformationMessage(final Composite parent, String text, Image type) {
		super();
		box = new Composite(parent, SWT.INHERIT_FORCE);
		box.setData(RWT.CUSTOM_VARIANT, "gray_background");
		box.setLayout(new FormLayout());
		FormDatas.attach(box).atTop(5).atRight(15).withWidth(300);
		icon = new Label(box, SWT.NONE);
		icon.setImage(type);
		FormDatas.attach(icon).atLeft(2).atTop(5).withWidth(SWT.DEFAULT);
		this.message = new Label(box, SWT.NORMAL | SWT.WRAP);
		this.message.setText(text);
		FormDatas.attach(message).atRight(15).atTop(10).atBottom(10).withWidth(250).atLeftTo(icon, 10);
		box.pack(true);
		box.pack(true);
		parent.update();
		parent.redraw();
		parent.getShell().layout(true, true);
		box.setFocus();
	}

	public void setFocus() {
		box.setFocus();
	}

	public InformationMessage(final Composite parent, Control reference, String text, Image type) {
		super();
		box = new Composite(parent, SWT.NONE);
		box.setData(RWT.CUSTOM_VARIANT, "gray_background");
		box.setLayout(new FormLayout());
		FormDatas.attach(box);
		icon = new Label(box, SWT.NONE);
		icon.setImage(type);
		FormDatas.attach(icon).atLeft(2).atTop(5).withWidth(SWT.DEFAULT);
		this.message = new Label(box, SWT.NORMAL | SWT.WRAP);
		this.message.setText(text);
		FormDatas.attach(message).atRight(15).atTop(10).atBottom(10).withWidth(250).atLeftTo(icon, 10);
		Display display = reference.getDisplay();
		Point position = display.map(reference.getParent(), null, reference.getLocation());
		box.setBounds(position.x - 1, position.y + 1, 300, SWT.DEFAULT);
		box.pack(true);
		parent.update();
		parent.redraw();
		parent.getShell().layout(true, true);
		box.setFocus();
	}

	public void dispose() {
		Composite parent = box.getParent();
		if (!box.isDisposed()) {
			box.dispose();
		}
		parent.update();
		parent.redraw();
		parent.getShell().layout(true, true);

	}

	public void disposeShell() {
		if (!getShell().isDisposed()) {
			getShell().setVisible(false);
			getShell().close();
		}
		Display.getCurrent().getActiveShell().redraw();

	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}
}
