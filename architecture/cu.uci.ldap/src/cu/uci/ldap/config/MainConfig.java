package cu.uci.ldap.config;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;

import cu.uci.ldap.pages.LoginEntryPoint;

public class MainConfig implements ApplicationConfiguration{

	@Override
	public void configure(Application arg0) {
		Map<String, String> map1 = new HashMap<String, String>();
		
		map1.put(WebClient.PAGE_TITLE, "Login");
		arg0.addEntryPoint("/login", LoginEntryPoint.class, map1);
	}

}
