package cu.uci.abcd.administration.library;

import java.sql.Date;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IWorkerService {

	public Worker addWorker(Worker worker);

	public Worker readWorker(Long idWorker);

	public void deleteWorker(Long idWorker);

	public Page<Worker> findAll(Library library, String firstName,
			String secondName, String firstSurname, String secondSurname,
			String dni, Nomenclator workerType, Nomenclator gender,
			Date fromDate, Date toDate, int page, int size, int direction,
			String order);

	public Worker readWorkerByPerson(Long idPerson);

}
