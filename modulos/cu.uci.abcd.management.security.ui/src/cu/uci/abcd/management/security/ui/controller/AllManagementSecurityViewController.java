package cu.uci.abcd.management.security.ui.controller;

import java.util.Map;

import cu.uci.abcd.management.security.IAccessRecordService;
import cu.uci.abcd.management.security.ILdapService;
import cu.uci.abcd.management.security.IPermissionService;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abcd.management.security.IProfileService;
import cu.uci.abcd.management.security.IUserService;
import cu.uci.abos.api.ui.ViewController;

public class AllManagementSecurityViewController implements ViewController {

	private IPersonService personService;
	private IProfileService profileService;
	private IUserService userService;
	private IAccessRecordService accessRecordService;
	private IPermissionService permissionService;
	private ILdapService ldapService;

	public IPermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(IPermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public IProfileService getProfileService() {
		return profileService;
	}

	public void setProfileService(IProfileService profileService) {
		this.profileService = profileService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IPersonService getPersonService() {
		return personService;
	}

	public void setPersonService(IPersonService personService) {
		this.personService = personService;
	}

	public IAccessRecordService getAccessRecordService() {
		return accessRecordService;
	}

	public void setAccessRecordService(IAccessRecordService accessRecordService) {
		this.accessRecordService = accessRecordService;
	}
	
	public ILdapService getLdapService() {
		return ldapService;
	}

	public void setLdapService(ILdapService ldapService) {
		this.ldapService = ldapService;
	}

	public void bindPersonService(IPersonService personService, Map<?, ?> properties) {
		this.setPersonService(personService);
	}

	public void bindProfileService(IProfileService profileService, Map<?, ?> properties) {
		this.setProfileService(profileService);
	}

	public void bindUserService(IUserService userService, Map<?, ?> properties) {
		this.setUserService(userService);
	}

	public void bindAccessRecordService(IAccessRecordService accessRecordService, Map<?, ?> properties) {
		this.setAccessRecordService(accessRecordService);
	}

	public void bindPermissionService(IPermissionService permissionService, Map<?, ?> properties) {
		this.setPermissionService(permissionService);
	}
	
	public void bindLdapService(ILdapService ldapService, Map<?, ?> properties) {
		this.setLdapService(ldapService);
	}

}
