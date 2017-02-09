package cu.uci.abcd.data.injector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cu.uci.abcd.dao.circulation.LoanUserTypeDAO;
import cu.uci.abcd.dao.common.AccountDAO;
import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.common.PersonDAO;
import cu.uci.abcd.dao.management.security.PermissionDAO;
import cu.uci.abcd.domain.circulation.LoanUserType;
import cu.uci.abcd.domain.common.Account;
import cu.uci.abcd.domain.common.Gender;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.domain.management.security.Profile;

public class DataInjector {
	private PersonDAO personDAO;
	private LoanUserTypeDAO loanUserTypeDAO;
	private AccountDAO accountDAO;
	private PermissionDAO permissionDAO;
	private NomenclatorDAO nomenclatorDAO;

	public void bindPersonDAO(PersonDAO personDAO, Map<?, ?> properties) {
		this.personDAO = personDAO;
		populatePersonTable();
	}

	public void bindLoanUserTypeDAO(LoanUserTypeDAO loanUserTypeDAO,
			Map<?, ?> properties) {
		this.loanUserTypeDAO = loanUserTypeDAO;
		// populateLoanUserTypeTable();
	}

	public void bindPermission(PermissionDAO permissionDAO,
			Map<String, String> properties) {
		this.permissionDAO = permissionDAO;
		// generatePermission();
	}

	public void bindAccountDAO(AccountDAO accountDAO, Map<?, ?> properties) {
		this.accountDAO = accountDAO;
	}

	public void bindNomenclator(NomenclatorDAO nomenclatorDAO,
			Map<?, ?> properties) {
		this.nomenclatorDAO = nomenclatorDAO;
		/*
		 * List<Nomenclator> list = this.nomenclatorDAO
		 * .findByOwnerLibrary((long) 2); for (Iterator iterator =
		 * list.iterator(); iterator.hasNext();) { Nomenclator nomenclator =
		 * (Nomenclator) iterator.next(); System.out.println("Nomenclador " +
		 * nomenclator.getNomenclatorName()); }
		 */
	}

	private void populatePersonTable() {
		Person auxPerson = createPerson("Administrador", "Administrador",
				Gender.MALE, "85030626924");
		Account auxAccount = createAccount("admin",
				"81fe8bfe87576c3ecb22426f8e57847382917acf");
		Library library = createLibrary("Biblioteca UCI de prueba");
		Profile profile = new Profile();
		profile.setOwner(library);
		List<Permission> list = generatePermission();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Permission permission = (Permission) iterator.next();
			profile.addPermission(permission);
		}
		auxAccount.addAssignedProfile(profile);
		auxAccount.setAccountLibrary(library);
		auxAccount.setOwner(auxPerson);
		personDAO.save(auxPerson);
		personDAO.save(createPerson("Yaksel", "Duran Rivas", Gender.MALE,
				"87041028268"));
		personDAO.save(createPerson("Victor Ernesto", "Marin Martinez",
				Gender.MALE, "89012542746"));
		personDAO.save(createPerson("Andy", "Cabrera Medina", Gender.MALE,
				"86040701220"));
		personDAO.save(createPerson("Reynier", "Carbonell Sanchez",
				Gender.MALE, "90070726165"));
	}

	private Person createPerson(String firstName, String lastName,
			Gender gender, String dNI) {
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setGender(gender);
		person.setDNI(dNI);
		return person;
	}

	private void populateLoanUserTypeTable() {
		loanUserTypeDAO.save(createLoanUserType("TIPO USUARIO PRESTAMO",
				"Profesor"));
		loanUserTypeDAO.save(createLoanUserType("TIPO USUARIO PRESTAMO",
				"Estudiante"));
		loanUserTypeDAO.save(createLoanUserType("TIPO USUARIO PRESTAMO",
				"Especialista"));
	}

	private LoanUserType createLoanUserType(String code, String description) {
		LoanUserType loanUserType = new LoanUserType();
		loanUserType.setNomenclatorCode(code);
		loanUserType.setNomenclatorDescription(description);
		return loanUserType;
	}

	private Permission createPermission(String identifier) {
		Permission permission = new Permission();
		permission.setFunctionalityIdentifier(identifier);
		return permission;
	}

	private Profile createProfile(String profileName, String profileDescription) {
		Profile profile = new Profile();
		profile.setProfileName(profileName);
		profile.setDescription(profileDescription);
		return profile;
	}

	private Library createLibrary(String libraryName) {
		Library library = new Library();
		library.setLibraryName(libraryName);
		return library;
	}

	private List<Permission> generatePermission() {
		List<Permission> permissions = new ArrayList<>();
		String[] permissionsList = new String[] { "associateThesaurusTermsID",
				"attachDigitalFileID", "attachDigitalFile2ID",
				"newSuscriptionID", "newSuscription1ID", "newSuscription2ID",
				"predictionPatternID", "registerAuthoritiesRecordID",
				"registerExemplaryRecordID", "searchAuthoritiesID",
				"findServersZ3950ID", "advanceQueryID",
				"subscriptionPeriodicalPublicationQueryID",
				"materialsCatalogerQueryID", "catalogQueryID",
				"authoritiesQueryID", "manageNomenclatorID", "registerLoanID",
				"registerRenewID", "registerReturnID", "registerPenaltyID",
				"registerLoanUserID", "reservationQueryID",
				"transactionQueryID", "loanUserQueryID", "loanObjectQueryID",
				"penaltyQueryID", "manageLibraryID", "manageProvidersID",
				"manageCoinsID", "registerWorkerLibraryID",
				"registerCourseLibraryID", "workerLibraryQueryID",
				"courseLibraryQueryID", "configureCalendarID",
				"configureSheduleID", "configureFineEquationID",
				"configureCirculationRuleID", "updateRecordOfAcquisitionID",
				"approveRejectPurchaseOrderID", "approveRejectSuggesttionsID",
				"approveRejectSelectedSuggesttionsID",
				"associatePurchaseOrderID", "associateOrderID",
				"chekcPurchaseOrderID", "checkOrderID",
				"checkRegistryAcquisitionID", "checkSuggestionsID",
				"editDataAcquisitionOfExemplaryID", "editPurchaseOrderID",
				"modifyOrderID", "registerLogAcquisitionID",
				"purchaseOrderRegisterID",
				"recordOrderWithSuggestionsAssociatedID",
				"recordOrderWithoutSuggestionsAssociatedID",
				"seeOrderingInformationID",
				"seeOrderingNoSuggestionAssociatedID",
				"seeDetailsPurchaseOrderID", "seeLogAcquisitionID",
				"seeRecordCopyOfAcquisitionID" };
		for (int i = 0; i < permissionsList.length; i++) {
			Permission auxPermission = new Permission();
			auxPermission.setFunctionalityIdentifier(permissionsList[i]);
			permissions.add(auxPermission);
		}
		// permissionDAO.save(permissions);
		return permissions;
	}

	private Account createAccount(String userName, String password) {
		Account account = new Account();
		account.setUserName(userName);
		account.setPassword(password);
		return account;
	}
}
