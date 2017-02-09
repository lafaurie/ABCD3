package cu.uci.abcd.dao.acquisition;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.acquisition.PurchaseRequest;

public interface PurchaseRequestDAO extends PagingAndSortingRepository<PurchaseRequest, Long>, JpaSpecificationExecutor<PurchaseRequest> {

	public PurchaseRequest findByRequestNumber(String requestNumber);
	
	public List<PurchaseRequest> findDistinctPurchaseRequestBypurchaseorder_purchaseOrderID(Long purchaseOrderID);
	
}
