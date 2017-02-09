package cu.uci.abcd.opac.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
//import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.ui.api.IContributorService;
import cu.uci.abos.ui.api.IPlatformContributor;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.util.api.IServiceProvider;

public class OpacCirculationMenu implements IPlatformContributor {
	public static final String CIRCULATION_MENU_CONTROL = OpacCirculationMenu.class
			.getName() + "#CIRCULATIONMENU";
	public static final int CIRCULATION_MENU_WIDTH = 170;
	private final IServiceProvider serviceProvider;

	public Composite composite;

	public OpacCirculationMenu(IServiceProvider serviceProvider) {
		super();
		this.serviceProvider = serviceProvider;
	}
	
	
	Button createMenuButton(Composite parent,
			final IContributorService pageService, final String pageId,
			String buttonName, Control control) {

		parent.setLayout(new FormLayout());
		/*
		 * FormData data1 = new FormData(100, 25); data1.left = new
		 * FormAttachment(parent, 7); data1.top = new FormAttachment(parent, 7);
		 */

		Button result = new Button(parent, SWT.PUSH);

		FormDatas.attach(result).withWidth(100).withHeight(25)
				.atLeftTo(control, 7).atTop(20);
		result.setText(buttonName);
		// result.setLayoutData(data1);
		result.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent evt) {
				pageService.selectContributor(pageId);
			}
		});

		return result;
	}

	@Override
	public Control createUIControl(Composite parent) {

		final IContributorService pageService = serviceProvider
				.get(IContributorService.class);
		
		
	    composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new FormLayout());
		composite.setData(RWT.CUSTOM_VARIANT, "body_background");
		Label lbFilter = new Label(composite, SWT.NONE);
		lbFilter.setText("Circulacion");
		//composite.setVisible(false);
		
		Button circulation=createMenuButton(composite, pageService, 
				"CirculationID", "Reservar", composite);
		
		circulation.getBackground();
		
		return composite;
	}

	@Override
	public String getID() {
		return CIRCULATION_MENU_CONTROL;
	}

	@Override
	public void l10n() {

	}
}
