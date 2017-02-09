package cu.uci.abcd.management.security.ui.controller;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;

public class PersonViewController implements ViewController {

	private AllManagementSecurityViewController allManagementSecurityViewController;

	public AllManagementSecurityViewController getAllManagementSecurityViewController() {
		return allManagementSecurityViewController;
	}

	public void setAllManagementSecurityViewController(AllManagementSecurityViewController allManagementSecurityViewController) {
		this.allManagementSecurityViewController = allManagementSecurityViewController;
	}

	public void deletePerson(Long id) {
		Person a = allManagementSecurityViewController.getPersonService().findOnePerson(id);
		allManagementSecurityViewController.getPersonService().deletePerson(a);
	}

	public Person findOnePerson(Long id) {
		return allManagementSecurityViewController.getPersonService().findOnePerson(id);
	}

	public Page<Person> findPersonsByParams(Library library, String firstNameConsult, String secondNameConsult, String firstSurnameConsult, String secondSurnameConsult, String identificationConsult,
			int page, int size, int direction, String orderByString) {
		return allManagementSecurityViewController.getPersonService().findAll(library, firstNameConsult, secondNameConsult, firstSurnameConsult, secondSurnameConsult, identificationConsult, page,
				size, direction, orderByString);
	}

	public Page<Person> findPersonByParams(Library library, String param, int page, int size, int direction, String order) {
		return allManagementSecurityViewController.getPersonService().findAll(library, param, page, size, direction, order);
	}

	/*
	 * public Collection<Person> findAllPersonByQuery(String name, String lname,
	 * String dni, int age) { List<Person> persons =
	 * (List<Person>)allManagementSecurityViewController
	 * .getPersonService().findAll(); List<Person>tmp = new ArrayList<>(); for
	 * (Person person : persons) {
	 * 
	 * if(person.getFirstName().compareTo(name) == 0 || name.compareTo("") == 0)
	 * if(person.getLastName().compareTo(lname) == 0 || lname.compareTo("") ==
	 * 0) if(person.getDNI().compareTo(dni) == 0 || dni.compareTo("") == 0){ int
	 * cy = new java.util.Date().getYear(); int cm = new
	 * java.util.Date().getMonth(); int cd = new java.util.Date().getDay();
	 * 
	 * int edad = cy - person.getBirthDate().getYear(); if(cm < cy -
	 * person.getBirthDate().getMonth()) edad = edad - 1; else if(cm == cy -
	 * person.getBirthDate().getMonth() && cd < person.getBirthDate().getDay()){
	 * edad = edad - 1; } if(age == edad || age == -1) tmp.add(person); } }
	 * return tmp; }
	 */
	public Person updatePerson(Person person) {
		return allManagementSecurityViewController.getPersonService().addPerson(person);
	}

	public Person addPerson(Person person) {
		return allManagementSecurityViewController.getPersonService().addPerson(person);
	}

	/*
	 * private IPersonService managePerson;
	 * 
	 * 
	 * public void bindPersonService(IPersonService personService, Map<?, ?>
	 * properties) { this.managePerson = personService;
	 * 
	 * } public void deletePerson(Long id){ Person a =
	 * managePerson.findOnePerson(id); managePerson.deletePerson(a); }
	 * 
	 * public Collection<Person> findAllPerson() { return
	 * managePerson.findAll(); } public Person findOnePerson(Long id) { return
	 * managePerson.findOnePerson(id); }
	 * 
	 * 
	 * public Collection<Person> findAllPersonByQuery(String name, String lname,
	 * String dni, int age) { List<Person> persons =
	 * (List<Person>)managePerson.findAll(); List<Person>tmp = new
	 * ArrayList<>(); for (Person person : persons) {
	 * 
	 * if(person.getFirstName().compareTo(name) == 0 || name.compareTo("") == 0)
	 * if(person.getLastName().compareTo(lname) == 0 || lname.compareTo("") ==
	 * 0) if(person.getDNI().compareTo(dni) == 0 || dni.compareTo("") == 0){ int
	 * cy = new java.util.Date().getYear(); int cm = new
	 * java.util.Date().getMonth(); int cd = new java.util.Date().getDay();
	 * 
	 * int edad = cy - person.getBirthDate().getYear(); if(cm < cy -
	 * person.getBirthDate().getMonth()) edad = edad - 1; else if(cm == cy -
	 * person.getBirthDate().getMonth() && cd < person.getBirthDate().getDay()){
	 * edad = edad - 1; } if(age == edad || age == -1) tmp.add(person); } }
	 * return tmp; }
	 * 
	 * public Person updatePerson(Person person){ return
	 * managePerson.addPerson(person); } public Person addPerson(Person person)
	 * { return managePerson.addPerson(person); }
	 */

	public List<Nomenclator> findNomenclatorByCode(Long idLibrary, Long idParent) {
		return allManagementSecurityViewController.getPersonService().findNomenclatorByCode(idLibrary, idParent);
	}

	public Person findPersonByIdentification(String identification, Long idLibrary) {
		return allManagementSecurityViewController.getPersonService().findPersonByIdentification(identification, idLibrary);
	}
}
