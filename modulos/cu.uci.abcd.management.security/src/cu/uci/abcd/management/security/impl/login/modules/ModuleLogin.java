package cu.uci.abcd.management.security.impl.login.modules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPResponse;
import com.novell.ldap.LDAPResponseQueue;

import cu.uci.abcd.dao.common.UserDAO;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.IPermissionService;
import cu.uci.abcd.management.security.impl.services.AccountDaoServiceTracker;
import cu.uci.abcd.management.security.impl.services.IAccountDaoServiceListener;
import cu.uci.abos.core.security.AccountPrincipal;
import cu.uci.abos.core.security.GenericLoginModule;
import cu.uci.abos.core.util.Digest;
import cu.uci.abos.core.util.ServiceProviderUtil;

public class ModuleLogin extends GenericLoginModule implements LoginModule, IAccountDaoServiceListener {

	private Object domain;
	private Object library;
	private Object app;
	private UserDAO userDao;

	public ModuleLogin(Object domain) {
		super();
		AccountDaoServiceTracker tracker = new AccountDaoServiceTracker();
		tracker.setListener(this);
		tracker.open();
		this.domain = domain;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);
		this.library = sharedState.get("library");
		this.app = sharedState.get("app");
	}

	protected void verifyCredentialsOPAC(String username, String password, Object... params) throws LoginException {
		this.success = false;
		LDAPConnection connection = new LDAPConnection();
		LDAPResponseQueue responseQueue = null;
		byte[] passwordBytes;
		try {
			Ldap ldap = (Ldap) this.domain;
			User user;
			Library library = null;
			if (params[0] != null) {
				library = ((Library) params[0]);
			}
			if (ldap.getLdapID() == null) {
				// local
				if (username.equals("superadmin")) {
					user = userDao.findUser(username, Digest.digest(password, "SHA1"));
				} else {
					user = userDao.findLocalOpacUser(username, Digest.digest(password, "SHA1"), library);
				}
			} else {
				// domain
				passwordBytes = password.getBytes("UTF8");
				String loginDN = String.format("%s@%s", username, ldap.getDomain());
				connection.connect(ldap.getHost(), ldap.getPort());
				responseQueue = connection.bind(LDAPConnection.LDAP_V3, loginDN, passwordBytes, (LDAPResponseQueue) null);
				LDAPResponse response = (LDAPResponse) responseQueue.getResponse();
				int bindResult = response.getResultCode();
				if (bindResult != LDAPException.SUCCESS) {
					throw new FailedLoginException();
				} else {
					user = userDao.findDomainOpacUser(username, library, ldap);
				}
				connection.disconnect();
			}
			if (user != null) {
				List<String> permissionss = new ArrayList<String>();
				boolean in = false;
				if (user.getUsername().equals("superadmin") && this.domain != null) {
					in = true;
					if (params[0] == null) {
						List<Permission> temp = user.getAsignedProfiless().get(0).getAsignedPermissions();
						for (int i = 0; i < temp.size(); i++) {
							permissionss.add(temp.get(i).getPermissionName());
						}
					} else {
						IPermissionService permissionService = ServiceProviderUtil.getService(IPermissionService.class);
						List<Permission> temp = (List<Permission>) permissionService.findAll();
						for (int i = 0; i < temp.size(); i++) {
							permissionss.add(temp.get(i).getPermissionName());
						}
					}
				} else {
					if (params[0] != null && this.domain != null) {
						in = true;
						List<Profile> asignedProfilesList = user.getAsignedProfiless();
						for (Profile profile : asignedProfilesList) {
							List<Permission> asignedPermissionList = profile.getAsignedPermissions();
							for (Permission permission : asignedPermissionList) {
								if (!permissionss.contains(permission.getPermissionName())) {
									permissionss.add(permission.getPermissionName());
								}
							}
						}
					}
				}
				if (in) {
					this.accountPrincipal = new AccountPrincipal(user.getUsername());
					this.accountPrincipal.setObject("permissions", permissionss);
					this.accountPrincipal.setObject("user", user);
					this.accountPrincipal.setObject("username", user.getUsername());
					this.accountPrincipal.setObject("library", (params[0] != null) ? params[0] : null);
					this.accountPrincipal.setObject("image", (user.getPerson() != null) ? user.getPerson().getPhoto().getImage(60, 60) : null);
					this.accountPrincipal.setObject("domain", this.domain);

					this.success = true;
				} else {
					throw new FailedLoginException();
				}
			} else {
				throw new FailedLoginException();
			}
		} catch (Exception e) {
			throw new FailedLoginException(e.getMessage());
		}
	}
	
	protected void verifyCredentialsABCD(String username, String password, Object... params) throws LoginException {
		this.success = false;
		LDAPConnection connection = new LDAPConnection();
		LDAPResponseQueue responseQueue = null;
		byte[] passwordBytes;
		try {
			Ldap ldap = (Ldap) this.domain;
			User user;
			Library library = null;
			if (params[0] != null) {
				library = ((Library) params[0]);
			}
			if (ldap.getLdapID() == null) {
				// local
				if (username.equals("superadmin")) {
					user = userDao.findUser(username, Digest.digest(password, "SHA1"));
				} else {
					user = userDao.findLocalSystemUser(username, Digest.digest(password, "SHA1"), library);
				}
			} else {
				// domain
				passwordBytes = password.getBytes("UTF8");
				String loginDN = String.format("%s@%s", username, ldap.getDomain());
				connection.connect(ldap.getHost(), ldap.getPort());
				responseQueue = connection.bind(LDAPConnection.LDAP_V3, loginDN, passwordBytes, (LDAPResponseQueue) null);
				LDAPResponse response = (LDAPResponse) responseQueue.getResponse();
				int bindResult = response.getResultCode();
				if (bindResult != LDAPException.SUCCESS) {
					throw new FailedLoginException();
				} else {
					user = userDao.findDomainSystemUser(username, library, ldap);
				}
				connection.disconnect();
			}
			if (user != null) {
				List<String> permissionss = new ArrayList<String>();
				boolean in = false;
				if (user.getUsername().equals("superadmin") && this.domain != null) {
					in = true;
					if (params[0] == null) {
						List<Permission> temp = user.getAsignedProfiless().get(0).getAsignedPermissions();
						for (int i = 0; i < temp.size(); i++) {
							permissionss.add(temp.get(i).getPermissionName());
						}
					} else {
						IPermissionService permissionService = ServiceProviderUtil.getService(IPermissionService.class);
						List<Permission> temp = (List<Permission>) permissionService.findAll();
						for (int i = 0; i < temp.size(); i++) {
							permissionss.add(temp.get(i).getPermissionName());
						}
					}
				} else {
					if (params[0] != null && this.domain != null) {
						in = true;
						List<Profile> asignedProfilesList = user.getAsignedProfiless();
						for (Profile profile : asignedProfilesList) {
							List<Permission> asignedPermissionList = profile.getAsignedPermissions();
							for (Permission permission : asignedPermissionList) {
								if (!permissionss.contains(permission.getPermissionName())) {
									permissionss.add(permission.getPermissionName());
								}
							}
						}
					}
				}
				if (in) {
					this.accountPrincipal = new AccountPrincipal(user.getUsername());
					this.accountPrincipal.setObject("permissions", permissionss);
					this.accountPrincipal.setObject("user", user);
					this.accountPrincipal.setObject("username", user.getUsername());
					this.accountPrincipal.setObject("library", (params[0] != null) ? params[0] : null);
					this.accountPrincipal.setObject("image", (user.getPerson() != null) ? user.getPerson().getPhoto().getImage(60, 60) : null);
					this.accountPrincipal.setObject("domain", this.domain);

					this.success = true;
				} else {
					throw new FailedLoginException();
				}
			} else {
				throw new FailedLoginException();
			}
		} catch (Exception e) {
			throw new FailedLoginException(e.getMessage());
		}
	}

	@Override
	public void addAccountDaoService(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean login() throws LoginException {
		try {
			this.callbackHandler.handle(new Callback[] { this.nameCallback, this.passwordCallback });
			String username = this.nameCallback.getName();
			char[] tempPassword = this.passwordCallback.getPassword();
			if (tempPassword == null) {
				tempPassword = new char[0];
			}
			String password = new String(tempPassword);
			this.passwordCallback.clearPassword();
			this.verifyCredentials(username, password, this.library, this.domain, this.app);
			return true;
		} catch (IOException e) {
			throw new LoginException("IOException" + e.toString());
		} catch (UnsupportedCallbackException e) {
			throw new LoginException("ERROR: " + e.getCallback().toString() + " incapaz de obtener información sobre el usuario.");
		}
	}

	@Override
	protected void verifyCredentials(String username, String password, Object... params) throws LoginException {
		if ( params.length>=3 && params[2] != null && params[2].equals("OPAC")) {
			verifyCredentialsOPAC(username, password, params);
		} else {
			 verifyCredentialsABCD(username, password, params);
		}
	}

}
