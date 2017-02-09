package cu.uci.abcd.opac.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Text;

import cu.uci.abos.ui.api.IContributorService;
import cu.uci.abos.ui.api.IPlatformContributor;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.util.api.IServiceProvider;

public class OpacMenuBarProvider implements IPlatformContributor {
	public static final String MENU_BAR_CONTROL = OpacMenuBarProvider.class
			.getName() + "#MENUBAR";
	// NOTE: this value reflects the height of the menubar_background image set
	// via css
	public static final int MENU_BAR_HEIGHT = 41;
	private static final String MENUBAR_BACKGROUND = "menubar_background";

	// private static final String MENU_BUTTON = "menu_button";

	private IServiceProvider serviceProvider;

	public OpacMenuBarProvider(IServiceProvider serviceProvider) {
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
				.atLeftTo(control, 7).atTop(7);
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

		final Composite result = new Composite(parent, SWT.None);
		result.layout(true, true);
		result.setData(RWT.CUSTOM_VARIANT, MENUBAR_BACKGROUND);
		result.setLayout(new FormLayout());
		
		
		Button ajustar = new Button(result, SWT.None);
		FormDatas.attach(ajustar).withWidth(150).atLeft(50).atTop(7);
		ajustar.setVisible(false);
		   
		    
		 
		Button seleccion=createMenuButton(result, pageService, 
				"SeleccionID", "Seleccion", ajustar);
		
		Button misCitas=createMenuButton(result, pageService, 
				"MisCitasID", "Mis Citas", seleccion);
		
		
		Button misAlertas=createMenuButton(result, pageService, 
				"MisAlertasID", "Mis Alertas", misCitas);
		
		Button misEtiquetas=createMenuButton(result, pageService, 
				"MisEtiquetasID", "Mis Etiquetas", misAlertas);
		
		Button listasDeSeleccion=createMenuButton(result, pageService, 
				"ListasDeSeleccionID", "Listas", misEtiquetas);
		
		Button configuracion = createMenuButton(result, pageService, 
				"ConfigurationOptionID", "Configuration", listasDeSeleccion);
		
		
		configuracion.getBackground();
		
		
		
		

		//Button misSele=createMenuButton(result, pageService, "MisEtiquetasID", "Mis Etiquetas", misAlertas);

		/*
		 * /Text text = new Text(result, SWT.SEARCH | SWT.ICON_SEARCH |
		 * SWT.ICON_CANCEL);
		 */
		FormData searchData = new FormData(275, 15);
		int width = getMaximumwidht();

		searchData.left = new FormAttachment(width / 11, 100);
		searchData.top = new FormAttachment(result, 5);
		// text.setLayoutData(searchData);

		return result;
	}

	private int getMaximumwidht() {
		int width;
		Rectangle bounds = Display.getCurrent().getBounds();
		width = bounds.width;
		int result = 0;
		if (width > 1024) {
			result = width * 55 / 100;
		} else {
			result = width * 62 / 100;
		}

		return result;
	}

	@Override
	public String getID() {
		return MENU_BAR_CONTROL;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub

	}

}