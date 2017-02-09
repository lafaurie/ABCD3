package cu.uci.abcd.demo.service.impl;

import java.util.Collection;
import java.util.Map;

import cu.uci.abcd.demo.dao.common.PersonDAO;
import cu.uci.abcd.demo.domain.common.Person;
import cu.uci.abcd.demo.service.IPersonService;

public class PersonServiceImpl implements IPersonService {
	private PersonDAO personDAO;

	@Override
	public Person save(Person person) {
		return personDAO.save(person);
	}

	@Override
	public Collection<Person> findAll() {
		return (Collection<Person>) personDAO.findAll();
	}

	public void bind(PersonDAO personDAO, Map<?, ?> properties) {
		this.personDAO = personDAO;
	}
}
