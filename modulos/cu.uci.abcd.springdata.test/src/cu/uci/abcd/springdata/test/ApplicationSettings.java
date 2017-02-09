package cu.uci.abcd.springdata.test;

import java.util.HashMap;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;

public class ApplicationSettings implements ApplicationConfiguration {

	@Override
	public void configure(Application app) {
	app.addEntryPoint("/loanuser", LoanUserDataTest.class, new HashMap<String, String>());
	app.addEntryPoint("/person", PersonDataTest.class, new HashMap<String, String>());
	
	}

}
