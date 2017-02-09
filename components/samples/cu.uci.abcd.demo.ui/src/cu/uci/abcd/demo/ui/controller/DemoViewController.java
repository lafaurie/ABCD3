package cu.uci.abcd.demo.ui.controller;

import java.util.Collection;
import java.util.Map;

import cu.uci.abcd.demo.domain.common.Person;
import cu.uci.abcd.demo.service.IPersonService;
import cu.uci.abos.ui.api.IViewController;

public class DemoViewController implements IViewController {
	private IPersonService personService;

	public Person save(Person person) {
		return personService.save(person);
	}

	public Collection<Person> findAll() {
		return personService.findAll();
	}

	public void bind(IPersonService personService, Map<?, ?> properties) {
		this.personService = personService;
	}
}
