package cu.uci.abcd.management.security.impl.services;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.spi.LoginModule;

import org.eclipse.equinox.security.auth.ILoginContext;

import cu.uci.abcd.dao.management.library.LibraryDAO;
import cu.uci.abcd.management.security.impl.login.modules.ModuleLogin;
import cu.uci.abos.core.security.AccountPrincipal;
import cu.uci.abos.core.security.LoginChangedListener;
import cu.uci.abos.core.security.LoginException;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.security.SecurityLoginContext;
import cu.uci.abos.core.security.SecurityParams;

public class LoginServiceImpl implements LoginService, IServiceListener<LibraryDAO> {

	private ILoginContext loginContext;
	private Subject currentSubject;
	private List<LoginChangedListener> loginChangedEvent;
	private SecurityParams securityParams;
	private LibraryDAO libraryDao;

	public LoginServiceImpl(SecurityParams securityParams) {
		this.securityParams = securityParams;
		this.loginChangedEvent = new LinkedList<LoginChangedListener>();
		LibraryDaoServiceTracker libraryDaoServiceTracker = new LibraryDaoServiceTracker();
		libraryDaoServiceTracker.setListener(this);
		libraryDaoServiceTracker.open();
	}

	@Override
	public void login(final String username, final String password, final Object...params) throws LoginException {
		try {
			String passwordEncoded = null;
			LoginModule loginModule = null;
			loginModule = new ModuleLogin(params[1]);
			passwordEncoded = password;
			this.loginContext = new SecurityLoginContext(loginModule, new UserPassCallbackHandler(username, passwordEncoded), params[0], params[1],params[2]);
			this.loginContext.login();
			this.currentSubject = this.loginContext.getSubject();
			this.fireLoginChangedEvent();

		} catch (Exception e1) {
			throw new LoginException(e1.getMessage());
		}
	}

	public void logout() throws LoginException {
		try {
			if (this.loginContext != null) {
				this.loginContext.logout();
			}
			this.loginContext = null;
			this.currentSubject = null;
			this.fireLoginChangedEvent();
		} catch (javax.security.auth.login.LoginException e) {
			throw new LoginException(e.getMessage());
		}
	}

	public boolean isLoggedIn() {
		if (this.currentSubject != null) {
			return this.currentSubject.getPrincipals().size() > 0;
		}
		return false;
	}

	public void onLoginChanged(LoginChangedListener listener) {
		this.loginChangedEvent.add(listener);
	}

	public void fireLoginChangedEvent() {
		for (LoginChangedListener listener : this.loginChangedEvent) {
			listener.handle();
		}
	}

	public void clearEvents() {
		this.loginChangedEvent.clear();
	}

	private class UserPassCallbackHandler implements CallbackHandler {

		private String username;
		private String password;

		public UserPassCallbackHandler(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
			for (int i = 0; i < callbacks.length; i++) {
				if (callbacks[i] instanceof NameCallback) {
					NameCallback nc = (NameCallback) callbacks[i];
					nc.setName(this.username);
				} else if (callbacks[i] instanceof PasswordCallback) {
					PasswordCallback pc = (PasswordCallback) callbacks[i];
					pc.setPassword(this.password.toCharArray());
				}
			}
		}
	}

	@Override
	public boolean isEnabled() {
		return this.securityParams.isSecurityEnabled();
	}

	@Override
	public int getSessionTimeout() {
		return this.securityParams.getSessionTimeout();
	}

	public void setSecurityParams(SecurityParams params) {
		this.securityParams = params;
	}

	@Override
	public AccountPrincipal getPrincipal() {
		AccountPrincipal accountPrincipal = (AccountPrincipal) this.currentSubject.getPrincipals().iterator().next();
		return accountPrincipal;
	}

	@Override
	public List<?> loadParams() {
		return (List<?>) libraryDao.findDistinctLibraryByEnabled();
	}

	@Override
	public void addServiceListener(LibraryDAO service) {
		this.libraryDao = service;

	}

}
