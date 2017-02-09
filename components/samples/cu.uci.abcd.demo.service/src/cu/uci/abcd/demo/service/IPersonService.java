package cu.uci.abcd.demo.service;

import java.util.Collection;

import cu.uci.abcd.demo.domain.common.Person;

public interface IPersonService {
	public Person save(Person person);

	public Collection<Person> findAll();
}
