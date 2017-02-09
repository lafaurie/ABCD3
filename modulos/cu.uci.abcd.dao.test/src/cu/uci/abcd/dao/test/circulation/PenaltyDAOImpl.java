package cu.uci.abcd.dao.test.circulation;

import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.dao.circulation.PenaltyDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.circulation.Penalty;

public class PenaltyDAOImpl extends DaoUtil<Penalty> implements PenaltyDAO {
	

	public PenaltyDAOImpl() {
		super();
		data= new LinkedList<Penalty>(DataGenerator.getInstance().getPenalties());
	}

	@Override
	public List<Penalty> findDistinctPenaltyByLoanObject_LoanObjectID(Long key) {
		List<Penalty> result = new LinkedList<Penalty>();
		for (Penalty penalty : data) {
			if (key.equals(penalty.getLoanObject().getLoanObjectID())) {
				result.add(penalty);
			}
		}
		return result;
	}

	@Override
	public List<Penalty> findDistinctPenaltyByLoanUser_PersonID(Long key) {
		List<Penalty> result = new LinkedList<Penalty>();
		for (Penalty penalty : data) {
			if (key.equals(penalty.getLoanUser().getPersonID())) {
				result.add(penalty);
			}
		}
		return result;
	}

}
