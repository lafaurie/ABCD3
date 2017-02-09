package cu.uci.abcd.acquisition;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.management.library.Worker;

public interface IManagePurchaseRequestService {

	// RF_AQ9_Registrar Solicitud de Compra
	// RF_AQ11_Edit Purchase Request
	// RF_AQ11_Edit Purchase Request

	public PurchaseRequest setPurchaseRequest(PurchaseRequest purchaseRequest);

	// RF_AQ9.2_Select Orders
	public PurchaseRequest readPurchaseRequest(Long idPurchaseRequest);

	// Delete Purchase Order
	public void deletePurchaseRequest(Long id);

	// RF_AQ13_RF_AQ13_ List all Purchase Orders
	public List<PurchaseRequest> findAll();

	//Search Purchase Request
	public Page<PurchaseRequest> findAllPurchaseRequest(Specification<PurchaseRequest> specification, Pageable pageable);

	//Search all Workers
	public List<Worker> getWorkers();
	
	//Search Worker by Person Id
	public Worker getWorkerByPerson(Long idPerson);
	
	//Search Purchase Request by request number
	public PurchaseRequest findPurchaseRequestByNumber(String requestNumber);
	
	//Search Purchase Request by Purchase Order ID
	public List<PurchaseRequest> findPurchaseRequestByPurchaseOrderID(Long purchaseOrderID);
	
}
