package cu.uci.abcd.dao.management.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.library.Worker;

public interface WorkerDAO extends PagingAndSortingRepository<Worker, Long>, JpaSpecificationExecutor<Worker> {
  
	@Query("select w from Worker w where w.person.library = ?1")
    public  List<Worker> searchLibraryWorkers(Long library);
    
    public  Worker findDistinctWorkerByPersonPersonID(Long personID);

}
