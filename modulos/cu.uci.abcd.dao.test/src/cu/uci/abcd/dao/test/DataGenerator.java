package cu.uci.abcd.dao.test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.FineEquation;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.domain.opac.Comment;
import cu.uci.abcd.domain.opac.OPACAction;
import cu.uci.abcd.domain.opac.Recommendation;
import cu.uci.abcd.domain.opac.SelectionList;
import cu.uci.abcd.domain.opac.SelectionListData;
import cu.uci.abcd.domain.opac.Tag;

public class DataGenerator {

	private static DataGenerator instance = new DataGenerator();

	// COMMON
	private List<User> users = new LinkedList<User>();
	private List<Person> persons = new LinkedList<Person>();
	private List<Nomenclator> nomenclators = new LinkedList<Nomenclator>();
	private List<LoanObject> loanObjects = new LinkedList<LoanObject>();

	// circulacion
	private List<LoanUser> loanUsers = new LinkedList<LoanUser>();
	private List<Penalty> penalties = new LinkedList<Penalty>();
	private List<Reservation> reservations = new LinkedList<Reservation>();
	private List<Transaction> transactions = new LinkedList<Transaction>();

	// Adquisicion
	private List<Desiderata> desidaratas = new LinkedList<Desiderata>();
	private List<PurchaseRequest> purchaseRequests = new LinkedList<PurchaseRequest>();
	private List<Suggestion> sugesstions = new LinkedList<Suggestion>();

	// Library
	private List<Library> librarys = new LinkedList<Library>();
	private List<FineEquation> fineEquations = new LinkedList<FineEquation>();

	// Security
	private List<Permission> permissions = new LinkedList<Permission>();
	private List<Profile> profiles = new LinkedList<Profile>();

	// OPAC
	private List<Comment> comments = new LinkedList<Comment>();
	private List<Tag> tags = new LinkedList<Tag>();
	private List<OPACAction> opacActions = new LinkedList<OPACAction>();
	private List<Recommendation> recommendations = new LinkedList<Recommendation>();
	private List<SelectionList> selectionsLists = new LinkedList<SelectionList>();
	private List<SelectionListData> selectionsListDatas = new LinkedList<SelectionListData>();
	
	

	public List<Desiderata> getDesidaratas() {
		if (desidaratas.isEmpty()) {
			generateDesideratas();
		}
		return desidaratas;
	}

	private void generateDesideratas() {
		Desiderata d = new Desiderata();
		d.setAuthor("Autor");
		d.setDesidertaID(0L);
		d.setUsertype(getNomenclators().get(4));
		d.setLibrary(getLibrarys().get(0));
		desidaratas.add(d);
	}

	public List<Penalty> getPenalties() {
		if (penalties.isEmpty()) {
			generatePenalties();
		}
		return penalties;
	}

	private void generatePenalties() {
		Penalty p = new Penalty();
		p.setCoinType(getNomenclators().get(0));
		p.setAmount(new BigDecimal(12));
		p.setPenaltyID(0L);
		p.setPenaltyState(getNomenclators().get(5));
		p.setPenaltyType(getNomenclators().get(1));
		p.setLibrary(getLibrarys().get(0));
		p.setMotivation("Motivacion");
		penalties.add(p);
	}

	public List<Permission> getPermissions() {
		if (permissions.isEmpty()) {
			generatePermissions();
		}
		return permissions;
	}

	private void generatePermissions() {
		Permission p = new Permission();
		p.setId(0L);
		p.setModule("ABCD");
		p.setPermissionName("LOGGED USER");
		permissions.add(p);
	}

	public List<Profile> getProfiles() {
		if (profiles.isEmpty()) {
			generateProfiles();
		}
		return profiles;
	}

	private void generateProfiles() {
		Profile p = new Profile();
		p.setId(0L);
		p.setLibrary(getLibrarys().get(0));
		p.setPermissions(getPermissions());
		p.setProfileName("Profile Name");
		profiles.add(p);

	}

	public List<PurchaseRequest> getPurchaseRequests() {
		if (purchaseRequests.isEmpty()) {
			generatePurchaseRequests();
		}
		return purchaseRequests;
	}

	private void generatePurchaseRequests() {
		PurchaseRequest p = new PurchaseRequest();
		p.setArea(getLibrarys().get(0));
		p.setPurchaserequestID(0L);
		p.setRequestidentifier("Request Identifier");
		p.setState(getNomenclators().get(3));
		purchaseRequests.add(p);

	}

	public List<Reservation> getReservations() {
		if (reservations.isEmpty()) {
			generateReservations();
		}
		return reservations;
	}

	private void generateReservations() {
		Reservation r = new Reservation();
		r.setReservationID(0L);
		r.setLoanObject(getLoanObjects().get(0));
		r.setLoanUser(getLoanUsers().get(0));
		r.setState(getNomenclators().get(3));
		r.setCreationDate(getDate());
		reservations.add(r);

	}

	private Timestamp getDate() {
		return new Timestamp(GregorianCalendar.getInstance().getTimeInMillis());
	}

	public List<Suggestion> getSugesstions() {
		if (sugesstions.isEmpty()) {
			generateSuggestions();
		}
		return sugesstions;
	}

	private void generateSuggestions() {
		Suggestion s = new Suggestion();
		s.setSuggestionID(0L);
		s.setAuthor("Autor");
		s.setEditorial("Editorial");
		s.setState(getNomenclators().get(3));
		s.setTitle("title");
		s.setUser(getUsers().get(0));
		s.setNote("Note");
		sugesstions.add(s);
	}

	public List<Transaction> getTransactions() {
		if (transactions.isEmpty()) {
			generateTransactions();
		}
		return transactions;
	}

	private void generateTransactions() {
		Transaction t = new Transaction();
		t.setLoanObject(getLoanObjects().get(0));
		t.setLoanType(getNomenclators().get(4));
		t.setTransactionID(0L);
		t.setState(getNomenclators().get(3));
		t.setTransactionDateTime(getDate());
		t.setLoanUser(getLoanUsers().get(0));
		transactions.add(t);
	}

	public static DataGenerator getInstance() {
		return instance;
	}

	private DataGenerator() {
		super();
	}

	public List<LoanUser> getLoanUsers() {
		if (loanUsers.isEmpty()) {
			generateLoanUsers();
		}
		return loanUsers;
	}

	private void generateLoanUsers() {
		LoanUser lu = new LoanUser();
		lu.setAddress("Adresss");
		lu.setLibrary(getLibrarys().get(0));
		lu.setDNI("84021234446");
		lu.setLoanUserState(getNomenclators().get(3));
		lu.setLoanUserType(getNomenclators().get(2));
		lu.setEmailAddress("abcd@uci.cu");
		lu.setFirstName("ABCD");
		lu.setPersonID(0L);
		lu.setRemarks("Remark");
		loanUsers.add(lu);

	}

	public List<LoanObject> getLoanObjects() {
		if (loanObjects.isEmpty()) {
			generateLoanObjects();
		}
		return loanObjects;
	}

	private void generateLoanObjects() {
		LoanObject lo = new LoanObject();
		lo.setLoanObjectID(0L);
		lo.setIsisDBName("MARC");
		lo.setTitle("Loan Object");
		lo.setAuthor("Autor X");
		lo.setLibraryOwner(getLibrarys().get(0));
		lo.setControlNumber("1");
		lo.setRecordType(getNomenclators().get(6));
		lo.generateCreationDate();
		loanObjects.add(lo);
	}

	public List<Nomenclator> getNomenclators() {
		if (nomenclators.isEmpty()) {
			generateNomenclators();
		}
		return nomenclators;
	}

	private void generateNomenclators() {
		Nomenclator n = new Nomenclator();
		n.setNomenclatorID(0L);
		n.setNomenclatorDescription("Description 1");
		n.setNomenclatorName("Coint 1");
		n.setOwnerLibrary(getLibrarys().get(0));
		n.setNomenclatorCode(Nomenclator.COINTYPE);

		Nomenclator n1 = new Nomenclator();
		n1.setNomenclatorID(1L);
		n1.setNomenclatorDescription("Description 2");
		n1.setNomenclatorName("Loan State 1");
		n1.setOwnerLibrary(getLibrarys().get(0));
		n1.setNomenclatorCode(Nomenclator.LOAN_STATE);

		Nomenclator n2 = new Nomenclator();
		n2.setNomenclatorID(2L);
		n2.setNomenclatorDescription("Description 3");
		n2.setNomenclatorName("Loan Type 1");
		n2.setOwnerLibrary(getLibrarys().get(0));
		n2.setNomenclatorCode(Nomenclator.LOAN_TYPE);

		Nomenclator n3 = new Nomenclator();
		n3.setNomenclatorID(3L);
		n3.setNomenclatorDescription("Description 4");
		n3.setNomenclatorName("Loan user state 1");
		n3.setOwnerLibrary(getLibrarys().get(0));
		n3.setNomenclatorCode(Nomenclator.LOANUSER_STATE);

		Nomenclator n4 = new Nomenclator();
		n4.setNomenclatorID(4L);
		n4.setNomenclatorDescription("Description 4");
		n4.setNomenclatorName("Loan User Type 1");
		n4.setOwnerLibrary(getLibrarys().get(0));
		n4.setNomenclatorCode(Nomenclator.LOANUSERTYPE);

		Nomenclator n6 = new Nomenclator();
		n6.setNomenclatorID(5L);
		n6.setNomenclatorDescription("Description 5");
		n6.setNomenclatorName("Penalty 1");
		n6.setOwnerLibrary(getLibrarys().get(0));
		n6.setNomenclatorCode(Nomenclator.PENALTY_STATE);

		Nomenclator n7 = new Nomenclator();
		n7.setNomenclatorID(6L);
		n7.setNomenclatorDescription("Description 6");
		n7.setNomenclatorName("RecotType 1");
		n7.setOwnerLibrary(getLibrarys().get(0));
		n7.setNomenclatorCode(Nomenclator.RECORDTYPE);

		Nomenclator n8 = new Nomenclator();
		n8.setNomenclatorID(7L);
		n8.setNomenclatorDescription("Description 7");
		n8.setNomenclatorName("Reservation Stage 1");
		n8.setOwnerLibrary(getLibrarys().get(0));
		n8.setNomenclatorCode(Nomenclator.RESERVATION_STATE);

		Nomenclator n9 = new Nomenclator();
		n9.setNomenclatorID(8L);
		n9.setNomenclatorDescription("Description 8");
		n9.setNomenclatorName("Workert Type 1");
		n9.setOwnerLibrary(getLibrarys().get(0));
		n9.setNomenclatorCode(Nomenclator.WORKERTYPE);
		
		Nomenclator n10 = new Nomenclator();
		n10.setNomenclatorID(9L);
		n10.setNomenclatorDescription("Sexo");
		n10.setNomenclatorName("Femenino");
		n10.setOwnerLibrary(getLibrarys().get(0));
		n10.setNomenclatorCode("sexo");
		
		Nomenclator n11 = new Nomenclator();
		n11.setNomenclatorID(10L);
		n11.setNomenclatorDescription("Sexo");
		n11.setNomenclatorName("Masculino");
		n11.setOwnerLibrary(getLibrarys().get(0));
		n11.setNomenclatorCode("sexo");
		nomenclators.add(n);
		nomenclators.add(n1);
		nomenclators.add(n2);
		nomenclators.add(n3);
		nomenclators.add(n4);
		nomenclators.add(n6);
		nomenclators.add(n7);
		nomenclators.add(n8);
		nomenclators.add(n9);
		nomenclators.add(n10);
		nomenclators.add(n11);
	}

	public List<FineEquation> getFineEquations() {
		if (fineEquations.isEmpty()) {
			FineEquation fineEquation = new FineEquation();
			fineEquation.setDelayAmount(new BigDecimal(12f));
			fineEquation.setLostAmount(new BigDecimal(10f));
			fineEquation.setFineEquationID(0L);
			fineEquations.add(fineEquation);
		}
		return fineEquations;
	}

	public List<Library> getLibrarys() {
		if (librarys.isEmpty()) {
			Library library = new Library();
			library.setLibraryID(0L);
			library.setLibraryName("UCITEST");
			library.setAddress("Carretera San Antonio de los Baños, km 2,5, Torrent, La Lisa.");
			library.setFineEquation(getFineEquations().get(0));
			getFineEquations().get(0).setLibrary(library);
			librarys.add(library);
		}
		return librarys;
	}

	public List<User> getUsers() {
		if (users.isEmpty()) {
			generateUsers();
		}
		return users;
	}

	private void generateUsers() {
		User user = new User();
		user.setUserID(0L);
		user.setUsername("admin");
		user.setUserPassword("81fe8bfe87576c3ecb22426f8e57847382917acf");
		user.setPerson(getPersons().get(0));
		user.setEnabled(true);
		user.setAuthenticated(false);

		User user2 = new User();
		user2.setUserID(1L);
		user2.setUsername("admin2");
		user2.setUserPassword("81fe8bfe87576c3ecb22426f8e57847382917acf");
		user2.setPerson(getPersons().get(1));
		user2.setEnabled(true);
		user2.setAuthenticated(false);
		
		User user3 = new User();
		user3.setUserID(2L);
		user3.setUsername("admin3");
		user3.setUserPassword("81fe8bfe87576c3ecb22426f8e57847382917acf");
		user3.setPerson(getPersons().get(1));
		user3.setEnabled(true);
		user3.setAuthenticated(false);
		
		User user4= new User();
		user4.setUserID(3L);
		user4.setUsername("admin4");
		user4.setUserPassword("81fe8bfe87576c3ecb22426f8e57847382917acf");
		user4.setPerson(getPersons().get(1));
		user4.setEnabled(true);
		user4.setAuthenticated(false);
		
		users.add(user);
		users.add(user2);
		users.add(user3);
		users.add(user4);
	}

	public List<Person> getPersons() {
		if (persons.isEmpty()) {
			generatePersons();
		}
		return persons;
	}

	@SuppressWarnings("deprecation")
	private void generatePersons() {
		Person person = new Person();
		person.setPersonID(0L);
		person.setFirstName("FirsName 1");
		person.setSecondName("SecondName 1");
		person.setFirStsurname("FirStsurname 1");
		person.setSecondSurname("SecondSurname1");
		person.setEmailAddress("person1@uci.cu");
		person.setAddress("Addres 1");
		person.setBirthDate(new Date(2000, 01, 10));
		person.setSex(getNomenclators().get(9));
		
		person.setDNI("84021223446");
		person.setLibrary(getLibrarys().get(0));

		Person person1 = new Person();
		person1.setPersonID(1L);
		person1.setFirstName("FirsName 2");
		person1.setSecondName("SecondName 2");
		person1.setFirStsurname("FirStsurname 2");
		person1.setSecondSurname("SecondSurname2");
		person1.setEmailAddress("person2@uci.cu");
		person1.setAddress("Addres 2");
		person1.setDNI("84021223446");
		person1.setBirthDate(new Date(2000, 01, 10));
		person1.setSex(getNomenclators().get(9));
		person1.setLibrary(getLibrarys().get(0));

		Person person2 = new Person();
		person2.setPersonID(2L);
		person2.setFirstName("FirsName 3");
		person2.setSecondName("SecondName 3");
		person2.setFirStsurname("FirStsurname 3");
		person2.setSecondSurname("SecondSurname3");
		person2.setEmailAddress("person3@uci.cu");
		person2.setAddress("Addres 3");
		person2.setDNI("84021223446");
		person2.setBirthDate(new Date(2000, 01, 10));
		person2.setSex(getNomenclators().get(10));
		person2.setLibrary(getLibrarys().get(0));

		Person person3 = new Person();
		person3.setPersonID(3L);
		person3.setFirstName("FirsName 4");
		person3.setSecondName("SecondName 4");
		person3.setFirStsurname("FirStsurname 4");
		person3.setSecondSurname("SecondSurname4");
		person3.setEmailAddress("person4@uci.cu");
		person3.setBirthDate(new Date(2000, 01, 10));
		person3.setSex(getNomenclators().get(10));
		person3.setAddress("Addres 4");
		person3.setDNI("84021223446");
		person3.setLibrary(getLibrarys().get(0));

		persons.add(person);
		persons.add(person1);
		persons.add(person2);
		persons.add(person3);
	}

	public List<Tag> getTags() {
		if (tags.isEmpty()) {
			generateTags();
		}
		return tags;
	}

	@SuppressWarnings("deprecation")
	private void generateTags() {
		Tag t = new Tag();
		t.setId(0L);
		t.setDuser("duser");
		t.setTagName("tagName");
		t.setMaterial("material");
		t.setAction_date(new Date(2015, 5, 24));
		tags.add(t);
	}

	public List<Recommendation> getRecommendations() {
		if (recommendations.isEmpty()) {
			generateRecommendations();
		}
		return recommendations;
	}

	@SuppressWarnings("deprecation")
	private void generateRecommendations() {
		Recommendation r = new Recommendation();
		r.setAction_date(new Date(2015, 5, 24));
		r.setId(0L);
		r.setMaterial("Material");
		r.setDuser("duser");
		r.setUserid(0L);
		recommendations.add(r);
	}

	public List<OPACAction> getOpacActions() {
		if (opacActions.isEmpty()) {
			generateOpacActions();
		}
		return opacActions;
	}

	@SuppressWarnings("deprecation")
	private void generateOpacActions() {
		OPACAction o= new OPACAction();
		o.setDuser("duser");
		o.setId(0L);
		o.setAction_date(new Date(2015, 5, 24));
		o.setMaterial("Material");
		opacActions.add(o);
	}
	
	

	public List<SelectionList> getSelectionsLists() {
		if (selectionsLists.isEmpty()) {
			generateSelectionLists();
		}
		return selectionsLists;
	}

	@SuppressWarnings("deprecation")
	private void generateSelectionLists() {
		SelectionList s= new SelectionList();
		s.addListData(getSelectionsListDatas().get(2));
		s.setAction_date(new Date(2015, 5, 24));
		s.setDuser("duser");
		s.setMaterial("material");
		s.setSelectionListName("selectionListName");
		s.setId(0L);
		
		SelectionList s1= new SelectionList();
		s1.addListData(getSelectionsListDatas().get(2));
		s1.setAction_date(new Date(2015, 5, 24));
		s1.setDuser("duser1");
		s1.setMaterial("material1");
		s1.setSelectionListName("selectionListName1");
		s1.setId(1L); 
		
		selectionsLists.add(s);
		selectionsLists.add(s1);
		
	}

	public List<SelectionListData> getSelectionsListDatas() {
		if (selectionsListDatas.isEmpty()) {
			generateSelectionListDatas();
		}
		return selectionsListDatas;
	}

	private void generateSelectionListDatas() {
		SelectionListData d= new SelectionListData();
		d.setIsisdatabasename("ICOMOS");
		d.setIsisRecordID(0L);
		
		SelectionListData d2= new SelectionListData();
		d2.setIsisdatabasename("ICOMOS");
		d2.setIsisRecordID(1L);
		
		SelectionListData d3= new SelectionListData();
		d3.setIsisdatabasename("ICOMOS");
		d3.setIsisRecordID(2L);
		selectionsListDatas.add(d);
		selectionsListDatas.add(d2);
		selectionsListDatas.add(d3);
		
	}

	public List<Comment> getComments() {
		if (comments.isEmpty()) {
			generateComments();
			
		}
		return comments;
	}

	@SuppressWarnings("deprecation")
	private void generateComments() {
		Comment c = new Comment();
		c.setAction_date(new Date(2015, 5, 24));
		c.setDescription("Descripcion");
		c.setDuser("duser");
		c.setMaterial("material");
		c.setState(1);
		c.setId(0L);
		
		Comment c1 = new Comment();
		c1.setAction_date(new Date(2015, 5, 24));
		c1.setDescription("Descripcion");
		c1.setDuser("duser");
		c1.setMaterial("material");
		c1.setState(1);
		c1.setId(1L);
	   comments.add(c1);
		
	}
	
	
	
	

}
