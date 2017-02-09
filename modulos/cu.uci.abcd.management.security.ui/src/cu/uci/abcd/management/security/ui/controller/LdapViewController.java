package cu.uci.abcd.management.security.ui.controller;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abos.api.ui.ViewController;

public class LdapViewController implements ViewController{

	private AllManagementSecurityViewController allManagementSecurityViewController;
	
	public AllManagementSecurityViewController getAllManagementSecurityViewController() {
		return allManagementSecurityViewController;
	}

	public void setAllManagementSecurityViewController(AllManagementSecurityViewController allManagementSecurityViewController) {
		this.allManagementSecurityViewController = allManagementSecurityViewController;
	}
	
	public Ldap saveLdap(Ldap ldap) {
		return allManagementSecurityViewController.getLdapService().addLdap(ldap);
	}
	
	public void deleteLdapById(Long idLdap) {
		allManagementSecurityViewController.getLdapService().deleteLdap(idLdap);
	}

	public Ldap getLdapById(Long idLdap) {
		return allManagementSecurityViewController.getLdapService().findOneLdap(idLdap);
	}
	
	public Ldap getLdapByDomain(Long idLibrary, String domain) {
		return allManagementSecurityViewController.getLdapService().getLdapByDomain(idLibrary, domain);
	}
	
	public Ldap getLdapByHostAndPort(Long idLibrary, String host, int port) {
		return allManagementSecurityViewController.getLdapService().getLdapByHostAndPort(idLibrary, host, port);
	}
	
	public Library getLibraryById(Long idLibrary) {
		return allManagementSecurityViewController.getLdapService().findOneLibrary(idLibrary);
	}
	
	public Library saveLibrary(Library library) {
		return allManagementSecurityViewController.getLdapService().addLibrary(library);
	}
	
	public List<Ldap> findAllByLibrary(Long idLibrary) {
		return allManagementSecurityViewController.getLdapService().findAllByLibrary(idLibrary);
	}
	
	public Page<Ldap> findLdapByParams(Library library, int page, int size, int direction, String order) {
		return allManagementSecurityViewController.getLdapService().findAll(library, page, size, direction,
				order);
	}
	
}
