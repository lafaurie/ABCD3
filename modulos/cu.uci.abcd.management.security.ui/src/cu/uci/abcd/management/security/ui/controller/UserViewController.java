package cu.uci.abcd.management.security.ui.controller;

import java.sql.Date;
import java.util.Collection;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abos.api.ui.ViewController;

public class UserViewController implements ViewController {

	private AllManagementSecurityViewController allManagementSecurityViewController;

	public AllManagementSecurityViewController getAllManagementSecurityViewController() {
		return allManagementSecurityViewController;
	}

	public void setAllManagementSecurityViewController(AllManagementSecurityViewController allManagementSecurityViewController) {
		this.allManagementSecurityViewController = allManagementSecurityViewController;
	}

	public Person addPerson(Person person) {
		return allManagementSecurityViewController.getPersonService().addPerson(person);
	}

	public Collection<Profile> SearchProfiles(String name) {
		return allManagementSecurityViewController.getProfileService().searchProfile(name);
	}

	public Profile findOneProfile(Long idProfile) {
		return allManagementSecurityViewController.getProfileService().findOneProfile(idProfile);
	}

	// public Collection<Person> findAllPerson() {
	// return allManagementSecurityViewController.getPersonService().findAll();
	// }
	/*
	 * public void deleteAccount(Long id){ Account a =
	 * allManagementSecurityViewController.getUserService().findOneAccount(id);
	 * allManagementSecurityViewController.getUserService().deleteAccount(a); }
	 * 
	 * public Account findOneAccount(Long idAccount) { return
	 * allManagementSecurityViewController
	 * .getUserService().findOneAccount(idAccount); }
	 */
	//public boolean changePsw(String user, String oldPsw, String newPsw) {
	//	return allManagementSecurityViewController.getUserService().changePsw(user, oldPsw, newPsw);
	//}

	public Page<User> findUsersByParams(Library library, int withPerson, boolean autenticated, String firstNameConsult, String secondNameConsult, String firstSurnameConsult, String secondSurnameConsult,
			String identificationConsult, String userConsult, boolean opacConsult, boolean systemConsult, Date fromDateTimeConsult, Date toDateTimeConsult, int page, int size, int direction,
			String order) {
		return allManagementSecurityViewController.getUserService().findAll(library, withPerson, autenticated, firstNameConsult, secondNameConsult, firstSurnameConsult, secondSurnameConsult,
				identificationConsult, userConsult, opacConsult, systemConsult, fromDateTimeConsult, toDateTimeConsult, page, size, direction, order);
	}

	/*
	 * public Page<User> findAutenticatedUsers(String firstNameConsult, String
	 * secondNameConsult, String firstSurnameConsult, String
	 * secondSurnameConsult, String identificationConsult, String userConsult,
	 * int page, int size, int direction, String order) {
	 * 
	 * return allManagementSecurityViewController.getUserService().findAll(
	 * SecuritySpecification.searchUsers(1, 1, firstNameConsult,
	 * secondNameConsult, firstSurnameConsult, secondSurnameConsult,
	 * identificationConsult, userConsult, false, false, null, null),
	 * PageSpecification.getPage(page, size, direction, order)); }
	 */

	/* Loan User */
	/*
	 * private IUserService manageUser; private IPersonService managePerson;
	 * private IProfileService manageProfile;
	 * 
	 * public void bindPersonService(IPersonService personService, Map<?, ?>
	 * properties) { this.managePerson = personService;
	 * 
	 * } public void bindUserService(IUserService userService, Map<?, ?>
	 * properties) { this.manageUser = userService; } public void
	 * bindProfileService(IProfileService profileService, Map<?, ?> properties)
	 * { this.manageProfile = profileService; }
	 * 
	 * public Collection<Account> findAccount() { return manageUser.findAll(); }
	 * 
	 * public void deleteAccount(Long id){ Account a =
	 * manageUser.findOneAccount(id); manageUser.deleteAccount(a); }
	 * 
	 * public boolean changePsw(String user, String oldPsw, String newPsw){
	 * return manageUser.changePsw(user, oldPsw, newPsw); }
	 * 
	 * public Account findOneLoanUser(Long id) { return
	 * manageUser.findOneAccount(id); }
	 * 
	 * /* PERSON
	 */
	/*
	 * public Collection<Person> findAllPerson() { return
	 * managePerson.findAll(); }
	 * 
	 * 
	 * 
	 * public Person findOnePerson(Long idPerson) { return
	 * managePerson.findOnePerson(idPerson); } public Collection<Profile>
	 * findAllProfile() { return manageProfile.findAll(); } public Profile
	 * findOneProfile(Long idProfile) { return
	 * manageProfile.findOneProfile(idProfile); } public Collection<Profile>
	 * SearchProfiles(String name) { return manageProfile.searchProfile(name); }
	 * 
	 * public Person addPerson(Person person) { return
	 * managePerson.addPerson(person); } public Account findOneAccount(Long
	 * idAccount) { return manageUser.findOneAccount(idAccount); }
	 */

	public User addUser(User user) {
		return allManagementSecurityViewController.getUserService().addUser(user);
	}

	public User readUser(Long idUser) {
		return allManagementSecurityViewController.getUserService().readUser(idUser);
	}

	public void deleteUser(User user) {
		allManagementSecurityViewController.getUserService().deleteUser(user);
	}
	
	public User findLocalUser(String userName, String password, Library library) {
		return allManagementSecurityViewController.getUserService()
				.findLocalUser(userName, password, library);
	}

	public User findDomainUser(String userName, Library library, Ldap ldap){
		return allManagementSecurityViewController.getUserService().findDomainUser(userName, library, ldap);
	}

}
