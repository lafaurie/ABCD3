package cu.uci.abcd.management.security.impl.services;

import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.security.LoginServiceFactory;
import cu.uci.abos.core.security.SecurityParams;

public class CustomLoginServiceFactory implements LoginServiceFactory {

	private SecurityParams securityParams;

	public CustomLoginServiceFactory(SecurityParams securityParams) {
		this.securityParams = securityParams;
	}

	@Override
	public LoginService createLoginService() {
		return new LoginServiceImpl(this.securityParams);
	}

}
