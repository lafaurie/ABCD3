package cu.uci.abos.core.util;

import org.eclipse.rap.rwt.RWT;

import cu.uci.abos.core.security.AccountPrincipal;
import cu.uci.abos.core.security.LoginService;

public class SecurityUtils {
	 final static String LOGINSERVICE ="loginService";
	public static LoginService getService() {
		return (LoginService) RWT.getUISession().getHttpSession().getAttribute(LOGINSERVICE);
	}

	public static boolean check(String permission) {
		if (getPrincipal() != null) {
			return getPrincipal().getPermission().contains(permission);
		}
		return false;
	}
	
	public static void setService(LoginService service){
		RWT.getUISession().getHttpSession().setAttribute(LOGINSERVICE, service);
	}

	public static AccountPrincipal getPrincipal() {
		if (RWT.getUISession().getHttpSession().getAttribute(LOGINSERVICE) != null) {
			return getService().getPrincipal();
		}
		return null;
	}
}
