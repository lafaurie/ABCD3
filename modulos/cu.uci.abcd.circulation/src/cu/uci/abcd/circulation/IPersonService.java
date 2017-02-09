package cu.uci.abcd.circulation;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;

/**
 * 
 * @author Yanet Crespo Diaz
 * 
 */
public interface IPersonService {

	/**
	 * find One Person
	 * 
	 * @param idPerson
	 * @return
	 */
	public Person findOnePerson(Long idPerson);

	/**
	 * Find All Person
	 * 
	 * @param specification
	 * @param pageable
	 * @return
	 */
	public Page<Person> findAllPerson(Library library,String param, int page, int size, int direction, String order);

	//FIXME FALTAN COMENTARIOS DE INTERFACE
	public Worker findWorkerbyPersonID(long personID);

}
