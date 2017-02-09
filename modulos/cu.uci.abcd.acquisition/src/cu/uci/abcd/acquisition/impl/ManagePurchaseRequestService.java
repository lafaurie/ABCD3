package cu.uci.abcd.acquisition.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.acquisition.IManagePurchaseRequestService;
import cu.uci.abcd.dao.acquisition.PurchaseRequestDAO;
import cu.uci.abcd.dao.management.library.WorkerDAO;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.management.library.Worker;

public class ManagePurchaseRequestService implements IManagePurchaseRequestService {

	private PurchaseRequestDAO purchaseRequestDao;
	private WorkerDAO workerDao;

	@Override
	// RF_AQ9_Register Purchase Order
	public PurchaseRequest setPurchaseRequest(PurchaseRequest purchaseRequest) {
		return purchaseRequestDao.save(purchaseRequest);
	}

	@Override
	public PurchaseRequest readPurchaseRequest(Long id) {
		return purchaseRequestDao.findOne(id);
	}

	@Override
	public void deletePurchaseRequest(Long id) {
		purchaseRequestDao.delete(id);

	}

	@Override
	public List<PurchaseRequest> findAll() {
		return (List<PurchaseRequest>) purchaseRequestDao.findAll();
	}

	@Override
	public List<Worker> getWorkers() {
		return (List<Worker>) workerDao.findAll();
	}

	@Override
	public Page<PurchaseRequest> findAllPurchaseRequest(Specification<PurchaseRequest> specification, Pageable pageable) {
		return purchaseRequestDao.findAll(specification, pageable);
	}

	public void bind(PurchaseRequestDAO purchaseRequestDao, Map<?, ?> properties) {
		this.purchaseRequestDao = purchaseRequestDao;
	}

	public void bind1(WorkerDAO workerDao, Map<?, ?> properties) {
		this.workerDao = workerDao;
	}

	@Override
	public Worker getWorkerByPerson(Long idPerson) {
		return workerDao.findDistinctWorkerByPersonPersonID(idPerson);
	}

	@Override
	public PurchaseRequest findPurchaseRequestByNumber(String requestNumber) {
		return purchaseRequestDao.findByRequestNumber(requestNumber);
	}

	@Override
	public List<PurchaseRequest> findPurchaseRequestByPurchaseOrderID(
			Long purchaseOrderID) {
		return purchaseRequestDao.findDistinctPurchaseRequestBypurchaseorder_purchaseOrderID(purchaseOrderID);
	}

}
