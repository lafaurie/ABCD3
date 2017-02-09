package cu.uci.abcd.opac.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;

import cu.uci.abcd.opac.listener.OpacTabItemResizeListener;
import cu.uci.abos.ui.api.IPlatformContributor;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.util.api.IServiceProvider;

public class OpacFilterMenu implements IPlatformContributor {
	public static final String MAIN_MENU_CONTROL = OpacFilterMenu.class
			.getName() + "#MAINMENU";
	public static final int MAIN_MENU_WIDTH = 170;
	private final IServiceProvider serviceProvider;
	
	public Composite composite;

	public OpacFilterMenu(IServiceProvider serviceProvider) {
		super();
		this.serviceProvider = serviceProvider;
	}

	@Override
	public Control createUIControl(Composite parent) {

	    composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new FormLayout());
		composite.setData(RWT.CUSTOM_VARIANT, "body_background");

		Link currentYear = new Link(composite, SWT.NORMAL);
		currentYear.setText("<a>2015</a>");
		currentYear.setData("currentYear", "2015");

		currentYear.addListener(SWT.Selection, new OpacTabItemResizeListener(
				serviceProvider, "2015"));
		FormDatas.attach(currentYear).atTop(5).atLeft(5);
		composite.setVisible(false);
		return composite;
	}

	@Override
	public String getID() {
		return MAIN_MENU_CONTROL;
	}

	@Override
	public void l10n() {

	}
}
