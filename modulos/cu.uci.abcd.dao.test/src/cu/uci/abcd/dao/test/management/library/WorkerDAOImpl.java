package cu.uci.abcd.dao.test.management.library;

import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.dao.management.library.WorkerDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.domain.management.library.Worker;

public class WorkerDAOImpl extends DaoUtil<Worker> implements WorkerDAO {

	@Override
	public List<Worker> findDistinctWorkerByLibrary_LibraryID(Long library) {
		List<Worker> result = new LinkedList<Worker>();
		for (Worker worker : data) {
			if (library.equals(worker.getLibrary().getLibraryID())) {
				result.add(worker);
			}
		}
		return result;
	}
}
