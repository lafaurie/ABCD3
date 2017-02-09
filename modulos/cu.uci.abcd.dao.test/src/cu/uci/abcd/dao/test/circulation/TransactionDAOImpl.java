package cu.uci.abcd.dao.test.circulation;

import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.dao.circulation.TransactionDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.circulation.Transaction;

public class TransactionDAOImpl extends DaoUtil<Transaction> implements TransactionDAO{

	
	public TransactionDAOImpl() {
		super();
		data= new LinkedList<Transaction>(DataGenerator.getInstance().getTransactions());
	}

	@Override
	public List<Transaction> findDistinctTransactionByLoanObject_LoanObjectID(Long key) {
		List<Transaction> result = new LinkedList<Transaction>();
		for (Transaction transaction : data) {
			if (key.equals(transaction.getLoanObject().getLoanObjectID())) {
				result.add(transaction);
			}
		}
		return result;
	}

	@Override
	public List<Transaction> findDistinctTransactionByLoanUser_PersonID(Long key) {
		List<Transaction> result = new LinkedList<Transaction>();
		for (Transaction transaction : data) {
			if (key.equals(transaction.getLoanUser().getPersonID())) {
				result.add(transaction);
			}
		}
		return result;
	}

}
