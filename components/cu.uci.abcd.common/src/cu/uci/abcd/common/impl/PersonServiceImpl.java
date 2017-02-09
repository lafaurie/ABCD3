package cu.uci.abcd.common.impl;

import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.common.IPersonService;
import cu.uci.abcd.dao.common.PersonDAO;
import cu.uci.abcd.dao.common.UserDAO;
import cu.uci.abcd.dao.specification.CommonSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;

public class PersonServiceImpl implements IPersonService {

	private PersonDAO personDAO;
	private UserDAO userDAO;

	public void bind(PersonDAO personDAO, Map<?, ?> properties) {
		this.personDAO = personDAO;
	}

	@Override
	public Page<Person> findAll(Library library, String firstName, String lastName, String dni, int page, int size, int direction, String order) {
		return personDAO.findAll(CommonSpecification.searchPersonBy(library, firstName, lastName, dni), PageSpecification.getPage(page, size, direction, order));

	}

	@Override
	public User findUserByPersonID(Long personID) {
		return userDAO.findUserByPerson_PersonID(personID);
	}

	public void bindUser(UserDAO userDao, Map<?, ?> properties) {
		this.userDAO = userDao;
	}

	@Override
	public Page<Person> findAll(Library library, String anyThing, int page, int size, int direction, String order) {
		return personDAO.findAll(CommonSpecification.searchPerson(library, anyThing), PageSpecification.getPage(page, size, direction, order));

	}
}
