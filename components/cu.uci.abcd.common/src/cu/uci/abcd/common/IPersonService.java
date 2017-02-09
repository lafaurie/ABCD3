package cu.uci.abcd.common;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;

public interface IPersonService {
	
	public Page<Person> findAll(Library library, String firstName, String lastName, String dni, int page, int size, int direction, String order);

	public User findUserByPersonID(Long personID);
	
	public Page<Person> findAll(Library library, String anyThing, int page, int size, int direction, String order);


}
