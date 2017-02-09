package cu.uci.abcd.dao.acquisition;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.acquisition.Desiderata;

public interface DesiderataDAO extends PagingAndSortingRepository<Desiderata, Long>,JpaSpecificationExecutor<Desiderata>{

	public List<Desiderata> findDistinctDesiderataByPurchaseRequest_purchaseRequestID(Long purchaseRequestID);
	
}