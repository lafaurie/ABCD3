package cu.uci.abcd.administration.library.impl;

import java.sql.Date;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.IWorkerService;
import cu.uci.abcd.dao.management.library.WorkerDAO;
import cu.uci.abcd.dao.specification.LibrarySpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class WorkerServiceImpl implements IWorkerService {

	private WorkerDAO workerDAO;

	public void bind(WorkerDAO workerDAO, Map<?, ?> properties) {
		this.workerDAO = workerDAO;
	}

	@Override
	public Worker addWorker(Worker worker) {
		return workerDAO.save(worker);
	}

	@Override
	public Worker readWorker(Long idWorker) {
		return workerDAO.findOne(idWorker);
	}

	@Override
	public void deleteWorker(Long idWorker) {
		workerDAO.delete(idWorker);
	}

	@Override
	public Page<Worker> findAll(Library library, String firstName,
			String secondName, String firstSurname, String secondSurname,
			String dni, Nomenclator workerType, Nomenclator gender,
			Date fromDate, Date toDate, int page, int size, int direction,
			String order) {
		return workerDAO.findAll(LibrarySpecification.searchWorker(library,
				firstName, secondName, firstSurname, secondSurname, dni,
				workerType, gender, fromDate, toDate), PageSpecification
				.getPage(page, size, direction, order));
	}

	@Override
	public Worker readWorkerByPerson(Long idPerson) {
		return workerDAO.findDistinctWorkerByPersonPersonID(idPerson);
	}

}
