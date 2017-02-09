package cu.uci.abcd.dao.test.common;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.dao.common.LoanObjectDAO;
import cu.uci.abcd.dao.test.DaoTestException;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.common.LoanObject;

public class LoanObjectDAOImpl extends DaoUtil<LoanObject> implements LoanObjectDAO {

	
	public LoanObjectDAOImpl() {
		super();
		data = new LinkedList<LoanObject>(DataGenerator.getInstance().getLoanObjects());
	}
	
	

	@Override
	public <S extends LoanObject> S save(S value) {
		if (value.getLoanObjectID()==null) {
			value.setLoanObjectID(new Long(data.size()));
		}
		return super.save(value);
	}



	@Override
	public Collection<LoanObject> findDistinctByTitle(String title) {
		List<LoanObject> result = new LinkedList<LoanObject>();
		for (LoanObject loanObject : data) {
			if (title.equals(loanObject.getTitle())) {
				result.add(loanObject);
			}
		}
		return result;
	}

	@Override
	public LoanObject findDistinctLoanObjectByControlNumber(String number) {
		for (LoanObject loanObject : data) {
			if (number.equals(loanObject.getControlNumber())) {
				return loanObject;
			}
		}
		throw new DaoTestException("Not Data Found with number "+number);
	}

}
