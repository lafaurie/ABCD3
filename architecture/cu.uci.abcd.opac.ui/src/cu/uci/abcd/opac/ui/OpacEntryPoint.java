package cu.uci.abcd.opac.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.widgets.Shell;

import cu.uci.abcd.opac.contribution.OpacAdquisitionMenu;
import cu.uci.abcd.opac.contribution.OpacBackgroundProvider;
import cu.uci.abcd.opac.contribution.OpacCirculationMenu;
import cu.uci.abcd.opac.contribution.OpacContentProvider;
import cu.uci.abcd.opac.contribution.OpacFilterMenu;
import cu.uci.abcd.opac.contribution.OpacFooterProvider;
import cu.uci.abcd.opac.contribution.OpacHeaderProvider;
import cu.uci.abcd.opac.contribution.OpacLoginProvider;
import cu.uci.abcd.opac.contribution.OpacMenuBarProvider;
import cu.uci.abos.ui.api.ILayoutProvider;
import cu.uci.abos.ui.api.IPlatformContributor;
import cu.uci.abos.util.api.IServiceProvider;
import cu.uci.abos.util.api.ServiceProviderUtil;

public class OpacEntryPoint implements EntryPoint {
	private IServiceProvider serviceProvider;
	private Shell shell;

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public int createUI() {
		initializeServiceProvider();

		configureShell();
		openShell();

		shell.setData(RWT.CUSTOM_VARIANT, "Principal");
		return 0;
	}

	private void configureShell() 
	{
		OpacFilterMenu filterMenu=new OpacFilterMenu(serviceProvider);
		OpacContentProvider contentProvider=new OpacContentProvider(serviceProvider);
		OpacAdquisitionMenu adquisitionMenu=new OpacAdquisitionMenu(serviceProvider);
		OpacCirculationMenu circulationMenu   =new OpacCirculationMenu(serviceProvider);
		OpacMenuBarProvider menuBarProvider=new OpacMenuBarProvider(serviceProvider);
		OpacLoginProvider loginProvider=new OpacLoginProvider(serviceProvider);
		OpacHeaderProvider headerProvider=new OpacHeaderProvider(serviceProvider);
		IPlatformContributor[] pageStructureProviders = new IPlatformContributor[] {
				headerProvider, menuBarProvider,
				filterMenu,adquisitionMenu,circulationMenu,loginProvider,
				contentProvider, new OpacFooterProvider(),
				new OpacBackgroundProvider() };
		
	
		
		serviceProvider.register(OpacLoginProvider.class, loginProvider);
		serviceProvider.register(OpacHeaderProvider.class, headerProvider);
		serviceProvider.register(OpacAdquisitionMenu.class, adquisitionMenu);
		serviceProvider.register(OpacCirculationMenu.class, circulationMenu);
		
		
		
		ILayoutProvider layoutProvider = new OpacLayoutProviderImpl();
		
		OpacShellConfigurator configurator = new OpacShellConfigurator(serviceProvider);
		shell = configurator.configure(pageStructureProviders, layoutProvider);
		shell.layout(true);
	}

	private void initializeServiceProvider() {
		getServiceProvider();
	}

	private void getServiceProvider() {
		Class<IServiceProvider> type = IServiceProvider.class;
		serviceProvider = ServiceProviderUtil.getService(type);
	}

	private void openShell() {
		

		shell.open();
	}
}