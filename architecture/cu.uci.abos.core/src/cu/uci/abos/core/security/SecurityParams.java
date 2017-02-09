package cu.uci.abos.core.security;

public class SecurityParams {

	private boolean securityEnabled;
	private int sessionTimeout;
	private AuthenticationType authenticationType;


	public SecurityParams(String authenticationType) {
		if (authenticationType == null || authenticationType.isEmpty() || authenticationType.equals("${security.authentication.type}")) {
			this.authenticationType = AuthenticationType.DATABASE;
		} else {
			this.authenticationType = AuthenticationType.valueOf(authenticationType.toUpperCase());
		}
	}

	public boolean isSecurityEnabled() {
		return securityEnabled;
	}

	public void setSecurityEnabled(boolean securityEnabled) {
		this.securityEnabled = securityEnabled;
	}

	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public AuthenticationType getAuthenticationType() {
		return this.authenticationType;
	}

}
