package cu.uci.abcd.acquisition;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.acquisition.Desiderata;

public interface IManageDesiderataService {
	
	// Update order
	public Desiderata setDesiderata(Desiderata desiderata);

	// Delete Order	
	public void deleteDesiderata(Long id);
	
	//Check order
	public Desiderata readDesiderata(Long id);
	
	//Search all orders 
	public List<Desiderata> findAll();
	
	//OrderCount
	public Long countDesiderata();
	
	//Search all Desideratas
	public Page<Desiderata> findAllDesideratas(Specification<Desiderata> specification, Pageable pageable);

	//Search Desiderata for Purchase Order
	public List<Desiderata> findDesideratasByPurchaseRequestId(Long purchaseRequestID);
}
