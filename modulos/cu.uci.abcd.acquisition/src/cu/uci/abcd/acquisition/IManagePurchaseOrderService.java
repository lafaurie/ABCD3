package cu.uci.abcd.acquisition;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.management.library.Provider;

public interface IManagePurchaseOrderService{

	//RF_AQ9_Registrar Orden de Compra
		
		//RF_AQ11_Edit Purchase Order

		public PurchaseOrder setPurchaseOrder(PurchaseOrder purchaseRequest);
		 
		//RF_AQ9.2_Select Orders
		public PurchaseOrder readPurchaseOrder(Long idPurchaseOrder);

		//Delete Purchase Order
		public void deletePurchaseOrder(Long id);
		
		//RF_AQ13_RF_AQ13_ List all Purchase Orders
		public List<PurchaseOrder> findAll();
		
		
		//Search all Purchase Orders
		public Page<PurchaseOrder> findAllPurchaseOrders(Specification<PurchaseOrder> specification, Pageable pageable);

		//Search all Providers
		public List<Provider> findAllProviders();
		
		// Search Purchase Order by order number
		public PurchaseOrder findPurchaseOrderByNumber(String orderNumber);
}
