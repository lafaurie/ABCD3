package cu.uci.abos.core.security;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public abstract class GenericLoginModule implements LoginModule {
	protected Subject subject;
	protected CallbackHandler callbackHandler;
	protected NameCallback nameCallback;
	protected PasswordCallback passwordCallback;
	protected boolean success;
	protected AccountPrincipal accountPrincipal;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.nameCallback = new NameCallback("user:");
		this.passwordCallback = new PasswordCallback("password:", false);
	}

	@Override
	public boolean commit() throws LoginException {
		if (this.success) {
			if (!this.subject.getPrincipals().contains(this.accountPrincipal)) {
				this.subject.getPrincipals().add(this.accountPrincipal);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean abort() throws LoginException {
		if (!this.success) {
			return false;
		} else {
			this.logout();
		}
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		this.accountPrincipal.clear();
		this.subject.getPrincipals().clear();
		this.accountPrincipal = null;
		return true;
	}

	protected abstract void verifyCredentials(String username, String password, Object ...params) throws LoginException;
}
