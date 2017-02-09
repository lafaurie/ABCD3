package cu.uci.abcd.opac.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
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

public class OpacAdquisitionMenu implements IPlatformContributor {
	public static final String ADQUISITION_MENU_CONTROL = OpacAdquisitionMenu.class
			.getName() + "#ADQUISITIONMENU";
	public static final int ADQUISITION_MENU_WIDTH = 140;
	private final IServiceProvider serviceProvider;

	public Composite composite;

	public OpacAdquisitionMenu(IServiceProvider serviceProvider) {
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
		result.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(result).withWidth(100).withHeight(25).atTopTo(control, 5).atLeftTo(parent, 10).withHeight(25).withWidth(120);
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
		lbFilter.setText("Adquisicion");
		//composite.setVisible(false);
		

		Button adquisicion = createMenuButton(composite, pageService, 
				"AdquisicionID", "Sugerir", lbFilter);
		
		Button listViewSugerencia = createMenuButton(composite, pageService, 
				"listViewSugerenciaID", "Sugerencias", adquisicion);
		
		listViewSugerencia.getBackground();
		return composite;
	}

	@Override
	public String getID() {
		return ADQUISITION_MENU_CONTROL;
	}

	@Override
	public void l10n() {

	}
}
