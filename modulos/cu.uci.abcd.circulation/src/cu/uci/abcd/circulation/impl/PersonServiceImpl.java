package cu.uci.abcd.circulation.impl;

import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.IPersonService;
import cu.uci.abcd.dao.common.PersonDAO;
import cu.uci.abcd.dao.management.library.WorkerDAO;
import cu.uci.abcd.dao.specification.CommonSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;

/**
 * 
 * @author Yanet Crespo Diaz
 * 
 */
public class PersonServiceImpl implements IPersonService {

	private PersonDAO personDAO;
	private WorkerDAO workerDAO;

	@Override
	public Person findOnePerson(Long idPerson) {
		return personDAO.findOne(idPerson);
	}

	@Override
	public Page<Person> findAllPerson(Library library, String param, int page, int size, int direction, String order) {
		return personDAO.findAll(CommonSpecification.searchPerson(library, param), PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public Worker findWorkerbyPersonID(long personID) {
		return workerDAO.findDistinctWorkerByPersonPersonID(personID);
	}

	public void bindPersonDao(PersonDAO personDAO, Map<?, ?> properties) {
		this.personDAO = personDAO;
	}

	public void bindWorkerDao(WorkerDAO workerDAO, Map<?, ?> properties) {
		this.workerDAO = workerDAO;
	}

}
