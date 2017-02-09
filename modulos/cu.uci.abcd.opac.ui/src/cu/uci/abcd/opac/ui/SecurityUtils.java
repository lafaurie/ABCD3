package cu.uci.abcd.opac.ui;

import org.eclipse.rap.rwt.RWT;

import cu.uci.abos.core.security.AccountPrincipal;
import cu.uci.abos.core.security.LoginService;

public class SecurityUtils {
	
	static final String OPACLOGINSERVICE ="opacLoginService";
	   
	public static LoginService getService() {
		return (LoginService) RWT.getUISession().getHttpSession().getAttribute(SecurityUtils.OPACLOGINSERVICE);
	}

	public static boolean check(String permission) {
		if (getPrincipal() != null) {
			return getPrincipal().getPermission().contains(permission);
		}
		return false;
	}
	
	public static void setService(LoginService service){
		RWT.getUISession().getHttpSession().setAttribute(OPACLOGINSERVICE, service);
	}

	public static AccountPrincipal getPrincipal() {
		if (getService() != null) {
			return getService().getPrincipal();
		}
		return null;
	}
}