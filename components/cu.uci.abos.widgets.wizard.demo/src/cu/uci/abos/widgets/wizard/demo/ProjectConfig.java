package cu.uci.abos.widgets.wizard.demo;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;

public class ProjectConfig implements ApplicationConfiguration {

	@Override
	public void configure(Application arg0) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(WebClient.PAGE_TITLE, "Web JISIS");
		arg0.addEntryPoint("/wizard", DemoEntryPoint.class, properties);
	}

}
