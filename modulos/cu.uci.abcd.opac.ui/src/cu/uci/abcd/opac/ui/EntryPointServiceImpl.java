package cu.uci.abcd.opac.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.client.WebClient;

import cu.uci.abos.api.ui.EntryPointService;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.security.LoginServiceFactory;
import cu.uci.abos.core.security.LoginServiceFactoryListener;
import cu.uci.abos.core.security.LoginServiceFactoryTracker;

public class EntryPointServiceImpl implements EntryPointService, LoginServiceFactoryListener {

	@Override
	public String getPath() {
		return "/opac";
	}

	@Override
	public Class<? extends EntryPoint> getEntryPointClass() {
		return OpacEntryPoint.class;
	}

	@Override
	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(WebClient.PAGE_TITLE, "ABCD OPAC");
		return properties;
	}

	@Override
	public void setLoginServiceFactory(LoginServiceFactory loginServiceFactory) {
		
			if (SecurityUtils.getService() == null) {
				LoginService loginService = loginServiceFactory.createLoginService();
				RWT.getUISession().getHttpSession().setMaxInactiveInterval(loginService.getSessionTimeout());
				SecurityUtils.setService(loginService);
			} 
	}

	public EntryPointServiceImpl() {
		super();
		LoginServiceFactoryTracker tracker = new LoginServiceFactoryTracker();
		tracker.setListener(this);
		tracker.open();
	}

}
