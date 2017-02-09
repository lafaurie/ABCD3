package cu.uci.abcd.management.security.ui.controller;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abos.api.ui.ViewController;

public class ProfileViewController implements ViewController {

	private AllManagementSecurityViewController allManagementSecurityViewController;

	public AllManagementSecurityViewController getAllManagementSecurityViewController() {
		return allManagementSecurityViewController;
	}

	public void setAllManagementSecurityViewController(AllManagementSecurityViewController allManagementSecurityViewController) {
		this.allManagementSecurityViewController = allManagementSecurityViewController;
	}

	public Profile findOneProfile(long id) {
		return allManagementSecurityViewController.getProfileService().findOneProfile(id);
	}

	public Collection<Profile> findProfileByQuery(String name, Long bibliotecaId) {
		return allManagementSecurityViewController.getProfileService().findProfileByQuery(name, bibliotecaId);
	}

	public Collection<Profile> findProfileByLibrary(Library library) {
		return allManagementSecurityViewController.getProfileService().findProfileByLibarry(library);
	}

	public Page<Permission> findPermissionByParams(String name, Nomenclator module, int page, int size, int direction, String order) {
		return allManagementSecurityViewController.getPermissionService().findAll(name, module, page, size, direction, order);
	}

	public Page<Profile> findProfilesByParams(Library library, String profileNameConsult, Date fromDateTimeConsult, Date toDateTimeConsult, int page, int size, int direction, String orderByString) {
		return allManagementSecurityViewController.getProfileService().findAll(library, profileNameConsult, fromDateTimeConsult, toDateTimeConsult, page, size, direction, orderByString);
	}

	public Profile addProfile(Profile profile) {
		return allManagementSecurityViewController.getProfileService().addProfile(profile);
	}

	public Profile updateProfile(Profile profile) {
		return allManagementSecurityViewController.getProfileService().addProfile(profile);
	}

	public void deleteProfile(Profile profile) {
		allManagementSecurityViewController.getProfileService().deleteProfile(profile);
	}
	
	public Profile findProfileByName(Long idLibrary, String profileName){
		return allManagementSecurityViewController.getProfileService().findProfileByName(idLibrary, profileName);
	}
	
	public Profile findProfileByPermissions(Long idLibrary, List<Permission> permissions){
		return allManagementSecurityViewController.getProfileService().findProfileByPermissions(idLibrary, permissions);
	}
	

}
