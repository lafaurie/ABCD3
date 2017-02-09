package cu.uci.abos.core.security;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.eclipse.equinox.security.auth.ILoginContext;
import org.eclipse.equinox.security.auth.ILoginContextListener;

public class SecurityLoginContext implements ILoginContext {
	private CallbackHandler callbackHandler;
	private Subject subject;
	private LoginModule loginModule;
	private Object library;

	public Object getLibrary() {
		return library;
	}

	public void setLibrary(Object library) {
		this.library = library;
	}

	public SecurityLoginContext(LoginModule loginModule, CallbackHandler callbackHandler, Object library, Object domain, Object app) throws LoginException {
		this.loginModule = loginModule;
		this.callbackHandler = callbackHandler;
		this.subject = new Subject();
		Map<String, Object> libraries = new HashMap<String, Object>();
		libraries.put("library", library);
		libraries.put("app", app);
		this.loginModule.initialize(this.subject, this.callbackHandler, libraries, null);
	}

	@Override
	public LoginContext getLoginContext() throws LoginException {
		return null;
	}

	@Override
	public Subject getSubject() {
		return this.subject;
	}

	@Override
	public void login() throws LoginException {
		this.loginModule.login();
		this.loginModule.commit();
	}

	@Override
	public void logout() throws LoginException {
		this.loginModule.logout();
	}

	@Override
	public void registerListener(ILoginContextListener ctxListener) {
	}

	@Override
	public void unregisterListener(ILoginContextListener ctxListener) {
	}

}
