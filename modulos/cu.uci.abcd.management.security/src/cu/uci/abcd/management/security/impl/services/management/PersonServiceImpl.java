package cu.uci.abcd.management.security.impl.services.management;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.common.PersonDAO;
import cu.uci.abcd.dao.common.UserDAO;
import cu.uci.abcd.dao.specification.CommonSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.dao.specification.SecuritySpecification;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.management.security.IPersonService;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class PersonServiceImpl implements IPersonService {

	private PersonDAO personDAO;
	private NomenclatorDAO nomenclatorDAO;
	private UserDAO userDAO;

	public void bindPersonDao(PersonDAO personDAO, Map<?, ?> properties) {
		this.personDAO = personDAO;
	}

	public void bindNomenclatorDao(NomenclatorDAO nomenclatorDAO, Map<?, ?> properties) {
		this.nomenclatorDAO = nomenclatorDAO;
	}
	
	public void bindUserDao(UserDAO userDAO, Map<?, ?> properties) {
		this.userDAO = userDAO;
	}

	@Override
	public Person readPerson(Long idPerson) {
		return personDAO.findOne(idPerson);
	}

	public Page<Person> findAll(Specification<Person> specification, Pageable pageable) {
		return personDAO.findAll(specification, pageable);
	}

	@Override
	public Person findOnePerson(Long idPerson) {
		return personDAO.findOne(idPerson);
	}

	@Override
	public Person addPerson(Person person) {
		return personDAO.save(person);
	}

	@Override
	public void deletePerson(Person person) {
		this.personDAO.delete(person);
	}

	@Override
	public Page<Person> findAll(Library library, String param, int page, int size, int direction,
			String order) {
		return personDAO.findAll(CommonSpecification.searchPerson(library,
				param), PageSpecification.getPage(page,
				size, direction, order));
	}

	@Override
	public Page<Person> findAll(Library library, String firstName, String secondName, String firstSurname, String secondSurname, String identification, int page, int size, int direction, String order) {
		return personDAO.findAll(SecuritySpecification.searchPerson(library, firstName, secondName, firstSurname, secondSurname, identification),
				PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public User findUserByPersonID(Long personID) {
		return userDAO.findUserByPerson_PersonID(personID);
	}

	@Override
	public Nomenclator findNomenclatorById(Long code) {
		return nomenclatorDAO.findOne(code);
	}

	@Override
	public List<Nomenclator> findNomenclatorByCode(Long idLibrary, Long idParent) {
		return nomenclatorDAO.findNomenclatorsByLibraryOrLibraryNullAndParent(idLibrary, idParent);
	}

	@Override
	public Person findPersonByIdentification(String identification,
			Long idLibrary) {
		return personDAO.findPersonByDniIgnoreCaseAndLibrary_LibraryID(identification, idLibrary);
	}

}
