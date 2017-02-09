package cu.uci.abcd.demo.dao.mock;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cu.uci.abcd.demo.dao.common.PersonDAO;
import cu.uci.abcd.demo.domain.common.Person;

public class MockPersonDAO implements PersonDAO {
	private final Map<Long, Person> personList = new LinkedHashMap<Long, Person>();
	private long currentID = 0;

	@Override
	public Iterable<Person> findAll(Sort arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Page<Person> findAll(Pageable arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long count() {
		return personList.size();
	}

	@Override
	public void delete(Long arg0) {
		personList.remove(arg0);
	}

	@Override
	public void delete(Person arg0) {
		personList.remove(arg0);
	}

	@Override
	public void delete(Iterable<? extends Person> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAll() {
		personList.clear();
	}

	@Override
	public boolean exists(Long arg0) {
		return personList.containsKey(arg0);
	}

	@Override
	public Iterable<Person> findAll() {
		return personList.values();
	}

	@Override
	public Person findOne(Long arg0) {
		return personList.get(arg0);
	}

	@Override
	public Person save(Person arg0) {
		Long id = arg0.getActorID();
		if (id == null) {
			id = generateID();
			arg0.setActorID(id);
		}
		return personList.put(id, arg0);
	}

	@Override
	public Iterable<Person> save(Iterable<? extends Person> arg0) {
		throw new UnsupportedOperationException();
	}

	private long generateID() {
		return currentID++;
	}
}
