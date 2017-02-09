package cu.uci.abcd.dao.test.circulation;

import java.util.LinkedList;

import cu.uci.abcd.dao.circulation.LoanUserDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.circulation.LoanUser;

public class LoanUserDAOImpl extends DaoUtil<LoanUser> implements LoanUserDAO {

	public LoanUserDAOImpl() {
		super();
		data =new LinkedList<LoanUser>(DataGenerator.getInstance().getLoanUsers());
	}

}
