package cu.uci.abcd.opac.ui;

import java.util.Locale;

import javax.servlet.http.Cookie;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.internal.service.ContextProvider;
import org.eclipse.swt.widgets.Shell;

import cu.uci.abcd.opac.ui.contribution.OpacBackgroundProvider;
import cu.uci.abcd.opac.ui.contribution.OpacContentProvider;
import cu.uci.abcd.opac.ui.contribution.OpacFilterMenu;
import cu.uci.abcd.opac.ui.contribution.OpacFooterProvider;
import cu.uci.abcd.opac.ui.contribution.OpacHeaderProvider;
import cu.uci.abcd.opac.ui.contribution.OpacLoginProvider;
import cu.uci.abcd.opac.ui.contribution.OpacMenuBarProvider;
import cu.uci.abcd.opac.ui.contribution.OpacPerfilMenu;
import cu.uci.abos.api.ui.LayoutProvider;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.util.ServiceProviderUtil;

public class OpacEntryPoint implements EntryPoint {
	private ServiceProvider serviceProvider;
	private Shell shell;

	@Override
	public int createUI() {
		RWT.setLocale(new Locale("es", "ES", ""));
		initializeServiceProvider();
		cookiesAndHeaders();
		configureShell();
		openShell();
		shell.setData(RWT.CUSTOM_VARIANT, "Principal");
		return 0;
	}

	private void configureShell() {
		OpacFilterMenu filterMenu = new OpacFilterMenu(serviceProvider);
		OpacContentProvider contentProvider = new OpacContentProvider(serviceProvider);
		OpacPerfilMenu perfilMenu = new OpacPerfilMenu(serviceProvider);
		OpacMenuBarProvider menuBarProvider = new OpacMenuBarProvider(serviceProvider);
		OpacLoginProvider loginProvider = new OpacLoginProvider(serviceProvider);
		OpacHeaderProvider headerProvider = new OpacHeaderProvider(serviceProvider);
		PlatformContributor[] pageStructureProviders = new PlatformContributor[] { menuBarProvider, filterMenu, perfilMenu, headerProvider, loginProvider, contentProvider, new OpacFooterProvider(), new OpacBackgroundProvider() };

		serviceProvider.register(OpacLoginProvider.class, loginProvider);
		serviceProvider.register(OpacHeaderProvider.class, headerProvider);
		serviceProvider.register(OpacPerfilMenu.class, perfilMenu);
		serviceProvider.register(OpacMenuBarProvider.class, menuBarProvider);
		serviceProvider.register(OpacFilterMenu.class, filterMenu);

		LayoutProvider layoutProvider = new OpacLayoutProviderImpl();

		OpacShellConfigurator configurator = new OpacShellConfigurator(serviceProvider);
		shell = configurator.configure(pageStructureProviders, layoutProvider);
		shell.layout(true);
		configurator.selectDefaultContributor();
	}	

	private void initializeServiceProvider() {
		getServiceProvider();
	}

	private void getServiceProvider() {
		Class<ServiceProvider> type = ServiceProvider.class;
		serviceProvider = ServiceProviderUtil.getService(type);
	}

	private void openShell() {

		shell.open();
	}   
	
	private void cookiesAndHeaders() {	

		RWT.getUISession().getHttpSession().setMaxInactiveInterval(900);
		Cookie[] cookies= ContextProvider.getRequest().getCookies();
		if (cookies!=null) {
			for (int i = 0; i < cookies.length; i++) {
				cookies[i].setHttpOnly(true);
				ContextProvider.getResponse().addCookie(cookies[i]);
			}
		}        
		    
		ContextProvider.getResponse().setHeader("X-Frame-Options", "SAMEORIGIN");

		ContextProvider.getResponse().setHeader("X-Content-Type-Options", "nosniff");
		ContextProvider.getResponse().setHeader("X-XSS-Protection", "1; mode=block");
		ContextProvider.getResponse().setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' 'unsafe-eval'; style-src 'self' 'unsafe-inline'; frame-src *; img-src * data: blob:; font-src 'self' data:; media-src *; connect-src *");
			//new 2016-12-01
		ContextProvider.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		ContextProvider.getResponse().setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		ContextProvider.getResponse().setHeader("Access-Control-Max-Age", "3600");
		
	}

}