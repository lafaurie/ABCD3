package cu.uci.abcd.acquisition.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.acquisition.IManagePurchaseOrderService;
import cu.uci.abcd.dao.acquisition.PurchaseOrderDAO;
import cu.uci.abcd.dao.management.library.ProviderDAO;
import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.management.library.Provider;

public class ManagePurchaseOrderService implements IManagePurchaseOrderService {

	private PurchaseOrderDAO purchaseOrderDao;
	private ProviderDAO providerDao;

	public void bind(PurchaseOrderDAO purchaseOrderDao, Map<?, ?> properties) {
		this.purchaseOrderDao = purchaseOrderDao;
	}

	public void bind1(ProviderDAO providerDao, Map<?, ?> properties) {
		this.providerDao = providerDao;
	}

	@Override
	public void deletePurchaseOrder(Long id) {
		purchaseOrderDao.delete(id);

	}

	@Override
	public List<PurchaseOrder> findAll() {
		return (List<PurchaseOrder>) purchaseOrderDao.findAll();
	}

	@Override
	public List<Provider> findAllProviders() {
		return (List<Provider>) providerDao.findAll();
	}

	@Override
	public Page<PurchaseOrder> findAllPurchaseOrders(Specification<PurchaseOrder> specification, Pageable pageable) {
		return purchaseOrderDao.findAll(specification, pageable);
	}

	@Override
	public PurchaseOrder setPurchaseOrder(PurchaseOrder purchaseOrder) {
		return purchaseOrderDao.save(purchaseOrder);
	}

	@Override
	public PurchaseOrder readPurchaseOrder(Long id) {
		return purchaseOrderDao.findOne(id);
	}

	@Override
	public PurchaseOrder findPurchaseOrderByNumber(String orderNumber) {
		return purchaseOrderDao.findByOrderNumber(orderNumber);
	}
}
