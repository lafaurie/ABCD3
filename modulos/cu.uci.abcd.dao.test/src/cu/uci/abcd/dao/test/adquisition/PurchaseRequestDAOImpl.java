package cu.uci.abcd.dao.test.adquisition;

import java.util.LinkedList;

import cu.uci.abcd.dao.acquisition.PurchaseOrderDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;

public class PurchaseRequestDAOImpl extends DaoUtil<PurchaseRequest> implements PurchaseOrderDAO {

	public PurchaseRequestDAOImpl() {
		super();
		data= new LinkedList<PurchaseRequest>(DataGenerator.getInstance().getPurchaseRequests());
	}

}
