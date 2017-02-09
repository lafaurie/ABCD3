package cu.uci.abcd.management.security;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IPersonService {

	public Person readPerson(Long idPerson);

	public Page<Person> findAll(Library library, String firstName,
			String secondName, String firstSurname, String secondSurname,
			String String, int page, int size, int direction, String order);

	public Page<Person> findAll(Library library, String param, int page,
			int size, int direction, String order);

	public Person findOnePerson(Long idPerson);

	public Person addPerson(Person person);

	public Nomenclator findNomenclatorById(Long code);

	public void deletePerson(Person person);

	public User findUserByPersonID(Long personID);

	public List<Nomenclator> findNomenclatorByCode(Long idLibrary, Long idParent);

	public Person findPersonByIdentification(String identification,
			Long idLibrary);
}
